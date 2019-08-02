package com.cjh.InventoryMng.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.constants.Constants.EnumOpType;
import com.cjh.InventoryMng.entity.TAccountRecord;
import com.cjh.InventoryMng.entity.TAccountRecordExample;
import com.cjh.InventoryMng.entity.TAccountRecordWithBLOBs;
import com.cjh.InventoryMng.entity.TCostRecord;
import com.cjh.InventoryMng.entity.TCostRecordExample;
import com.cjh.InventoryMng.entity.TFinanicalOpLog;
import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TMemberReduce;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TOrderInfoExample;
import com.cjh.InventoryMng.entity.TStockInfo;
import com.cjh.InventoryMng.entity.TStockInfoExample;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.mapper.TAccountRecordMapper;
import com.cjh.InventoryMng.mapper.TCostRecordMapper;
import com.cjh.InventoryMng.mapper.TFinanicalOpLogMapper;
import com.cjh.InventoryMng.mapper.TGoodsInfoMapper;
import com.cjh.InventoryMng.mapper.TMemberInfoMapper;
import com.cjh.InventoryMng.mapper.TMemberReduceMapper;
import com.cjh.InventoryMng.mapper.TOrderInfoMapper;
import com.cjh.InventoryMng.mapper.TStockInfoMapper;
import com.cjh.InventoryMng.service.FinancialService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class FinancialServiceImpl implements FinancialService {

	@Autowired
	private TGoodsInfoMapper tGoodsInfoMapper;

	@Autowired
	private TOrderInfoMapper tOrderInfoMapper;

	@Autowired
	private TMemberInfoMapper tMemberInfoMapper;

	@Autowired
	private TFinanicalOpLogMapper tFinanicalOpLogMapper;

	@Autowired
	private TMemberReduceMapper tMemberReduceMapper;

	@Autowired
	private TAccountRecordMapper tAccountRecordMapper;

	@Autowired
	private TCostRecordMapper tCostRecordMapper;

	@Autowired
	private TStockInfoMapper tStockMapper;

	@Override
	public boolean newMemberOrder(String operator, Integer memberId, String memberName, Integer goodId, double buyNum,
			String orderDate) throws BusinessException, Exception {
		synchronized (this.getClass()) {
			TGoodsInfo goodsInfo = tGoodsInfoMapper.selectByPrimaryKey(goodId);
			String opDesc = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			if (goodsInfo != null && goodsInfo.getStatus() == 1) {

				TStockInfoExample stockExample = new TStockInfoExample();
				stockExample.createCriteria().andGoodIdEqualTo(goodId);
				Page<TStockInfo> tStockInfoList = tStockMapper.selectByExample(stockExample);
				boolean needCheckStock = false;
				TStockInfo tStockRecord = null;
				if (!CollectionUtils.isEmpty(tStockInfoList)) {
					needCheckStock = true;
					tStockRecord = tStockInfoList.get(0);
					if (tStockRecord.getCount() < buyNum) {
						throw new BusinessException("没有足够的库存");
					}
				}

				TOrderInfoExample example = new TOrderInfoExample();
				example.createCriteria().andGoodIdEqualTo(goodId).andMemberIdEqualTo(memberId)
						.andOrderDateEqualTo(orderDate);
				Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
				TOrderInfo record = null;
				if (CollectionUtils.isEmpty(orders)) {
					record = new TOrderInfo();
					record.setGoodId(goodId);
					record.setMemberId(memberId);
					record.setMemberPrice(goodsInfo.getMemberPrice());
					record.setNum(buyNum);
					record.setSupplierid(goodsInfo.getSupplierId());
					record.setOrderDate(orderDate);
					record.setOrderTime(sdf.parse(orderDate));
					record.setPurchasePrice(goodsInfo.getPurchasePrice()
							+ (goodsInfo.getServicePrice() == null ? 0 : goodsInfo.getServicePrice()));
					record.setStatus(1);
					if (goodsInfo.getServiceId() != null) {
						record.setServiceId(goodsInfo.getServiceId());
					}
					if (goodsInfo.getServicePrice() != null) {
						record.setServicePrice(goodsInfo.getServicePrice());
					}
					int insertSucc = tOrderInfoMapper.insert(record);
					opDesc = orderDate + " " + operator + "代替" + memberName + "新增:" + buyNum + "件"
							+ goodsInfo.getGoodName() + "， 记录id为" + record.getId();
				} else {
					record = orders.get(0);
					double oldNum = record.getNum();
					int oldStatus = record.getStatus();
					record.setOrderDate(orderDate);
					record.setOrderTime(sdf.parse(orderDate));
					record.setNum(buyNum);
					record.setStatus(1);
					tOrderInfoMapper.updateByPrimaryKey(record);
					opDesc = orderDate + " " + operator + "代替" + memberName + "更改:" + goodsInfo.getGoodName() + "由原来"
							+ (oldStatus == 1 ? "生效" : "失效") + oldNum + "件变为" + buyNum + "件" + "， 记录id为"
							+ record.getId();
				}
				TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
				opLogRecord.setOperator(operator);
				opLogRecord.setOpDate(sdf.format(now));
				opLogRecord.setOpTime(now);
				opLogRecord.setOpRecordId(record.getId());
				opLogRecord.setOpType(EnumOpType.NEW_ORDER.ordinal());
				opLogRecord.setOpDesc(opDesc);
				tFinanicalOpLogMapper.insert(opLogRecord);
				if (needCheckStock) {
					tStockRecord.setCount(tStockRecord.getCount() - ((int) buyNum));
					tStockMapper.updateByPrimaryKey(tStockRecord);
				}
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean modifyOrder(String operator, Integer id, Integer purchasePrice, double buyNum, Integer memberPrice,
			Integer servicePrice, String orderDate) throws BusinessException, Exception {
		synchronized (this.getClass()) {
			TOrderInfo orderInfo = tOrderInfoMapper.selectByPrimaryKey(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			if (orderInfo != null) {

				TStockInfoExample stockExample = new TStockInfoExample();
				stockExample.createCriteria().andGoodIdEqualTo(orderInfo.getGoodId());
				Page<TStockInfo> tStockInfoList = tStockMapper.selectByExample(stockExample);
				boolean needCheckStock = false;
				TStockInfo tStockRecord = null;
				if (!CollectionUtils.isEmpty(tStockInfoList)) {
					needCheckStock = true;
					tStockRecord = tStockInfoList.get(0);
					if (tStockRecord.getCount() < buyNum) {
						throw new BusinessException("没有足够的库存");
					}
				}

				int oldStatus = orderInfo.getStatus();
				int oldPurchasePrice = orderInfo.getPurchasePrice();
				int oldMemberPrice = orderInfo.getMemberPrice();
				Integer oldServicePrice = orderInfo.getServicePrice();
				orderInfo.setPurchasePrice(purchasePrice);
				orderInfo.setMemberPrice(memberPrice);
				orderInfo.setServicePrice(servicePrice);
				orderInfo.setNum(buyNum);
				orderInfo.setOrderDate(orderDate);
				orderInfo.setOrderTime(sdf.parse(orderDate));
				if (buyNum == 0) {
					orderInfo.setStatus(0);
				}
				String m = (orderInfo.getStatus() > oldStatus || orderInfo.getStatus() == 1) ? "" : "变失效";
				if (oldPurchasePrice != orderInfo.getPurchasePrice()) {
					m += " 采购价由" + oldPurchasePrice + "变" + orderInfo.getPurchasePrice();
				}
				if (oldMemberPrice != orderInfo.getMemberPrice()) {
					m += " 加盟价由" + oldMemberPrice + "变" + orderInfo.getMemberPrice();
				}
				if (oldServicePrice != null && oldServicePrice != orderInfo.getServicePrice()) {
					m += " 代仓价由" + oldServicePrice + "变" + orderInfo.getServicePrice();
				}
				tOrderInfoMapper.updateByPrimaryKey(orderInfo);

				TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
				opLogRecord.setOperator(operator);
				opLogRecord.setOpDate(sdf.format(now));
				opLogRecord.setOpTime(now);
				opLogRecord.setOpRecordId(orderInfo.getId());
				opLogRecord.setOpType(EnumOpType.NEW_ORDER.ordinal());
				String opDesc = orderDate + " " + operator + "代替"
						+ tMemberInfoMapper.selectByPrimaryKey(orderInfo.getMemberId()).getMemberName() + "更改:"
						+ tGoodsInfoMapper.selectByPrimaryKey(orderInfo.getGoodId()).getGoodName() + m + "， 记录id为"
						+ orderInfo.getId();
				opLogRecord.setOpDesc(opDesc);
				tFinanicalOpLogMapper.insert(opLogRecord);
				if (needCheckStock) {
					tStockRecord.setCount(tStockRecord.getCount() - ((int) buyNum));
					tStockMapper.updateByPrimaryKey(tStockRecord);
				}
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean newMemberReduce(String creator, Integer memberId, String memberName, Integer reduceAmount,
			String reduceDate, String reduceItem, int flag) {
		TMemberReduce record = new TMemberReduce();
		record.setCreator(creator);
		record.setMemberId(memberId);
		record.setReduceAmount(reduceAmount);
		record.setReduceDate(reduceDate);
		record.setManagerCostFlag(flag);
		record.setReduceItem(reduceItem);
		TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
		opLogRecord.setOperator(creator);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		opLogRecord.setOpDate(sdf.format(now));
		opLogRecord.setOpTime(now);
		opLogRecord.setOpRecordId(record.getId());
		opLogRecord.setOpType(EnumOpType.NEW_REDUCE.ordinal());
		String opDesc = reduceDate + " " + creator + "给" + memberName + "新增:" + reduceAmount + "减免/奖励金额，理由:"
				+ reduceItem + "， 记录id为" + record.getId();
		opLogRecord.setOpDesc(opDesc);
		tFinanicalOpLogMapper.insert(opLogRecord);
		return 1 == tMemberReduceMapper.insert(record);
	}

	@Override
	public boolean modifyMemberReduce(String updater, Integer reduceId, String memberName, Integer reduceAmount,
			String reduceDate, String reduceItem, int flag) {
		TMemberReduce record = new TMemberReduce();
		record.setUpdater(updater);
		record.setId(reduceId);
		record.setReduceAmount(reduceAmount);
		record.setReduceItem(reduceItem);
		record.setReduceDate(reduceDate);
		record.setManagerCostFlag(flag);
		tMemberReduceMapper.updateByPrimaryKeySelective(record);
		TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
		opLogRecord.setOperator(updater);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		opLogRecord.setOpDate(sdf.format(now));
		opLogRecord.setOpTime(now);
		opLogRecord.setOpRecordId(record.getId());
		opLogRecord.setOpType(EnumOpType.UPDATE_REDUCE.ordinal());
		String opDesc = reduceDate + " " + updater + "给" + memberName + "修改:" + reduceAmount + "减免/奖励金额，理由:"
				+ reduceItem + "， 记录id为" + record.getId();
		opLogRecord.setOpDesc(opDesc);
		tFinanicalOpLogMapper.insert(opLogRecord);
		return true;
	}

	@Override
	public Page<TAccountRecord> queryTAccountRecord(String beginDate, String endDate, String desc, String userId,
			int pageNo, int pageSize) throws ParseException {
		TAccountRecordExample example = new TAccountRecordExample();
		PageHelper.startPage(pageNo, pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isEmpty(desc)) {
			desc = "";
		}
		example.createCriteria().andApplyDescLike("%" + desc + "%").andCreateStaffEqualTo(userId)
				.andCreateTimeGreaterThanOrEqualTo(sdf.parse(beginDate))
				.andCreateTimeLessThanOrEqualTo(sdf.parse(endDate));
		return tAccountRecordMapper.selectByExample(example);
	}

	@Override
	public boolean newTAccountRecord(String creator, String theDate, String type, String desc, Integer amount,
			String file1Name, byte[] file1, String file2Name, byte[] file2) {
		TAccountRecordWithBLOBs record = new TAccountRecordWithBLOBs();
		record.setAmount(amount);
		record.setApplyDesc(desc);
		record.setCreateStaff(creator);
		record.setCreateTime(new Date());
		record.setFile1Name(file1Name);
		record.setFile1(file1);
		record.setFile2Name(file2Name);
		record.setFile2(file2);
		record.setStatus(0);
		record.setTheDate(theDate);
		record.setType(type);
		return 1 == tAccountRecordMapper.insertSelective(record);
	}

	@Override
	public TAccountRecordWithBLOBs queryTAccountRecord(Integer id) {
		return tAccountRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean approveAccountRecord(Integer id, String approveUserId) {
		TAccountRecordWithBLOBs record = new TAccountRecordWithBLOBs();
		record.setId(id);
		record.setRecordUserId(approveUserId);
		record.setStatus(1);
		return 1 == tAccountRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public boolean rejectAccountRecord(Integer id, String approveUserId, String why) {
		TAccountRecordWithBLOBs record = new TAccountRecordWithBLOBs();
		record.setId(id);
		record.setRecordUserId(approveUserId);
		record.setStatus(2);
		record.setWhy(why);
		return 1 == tAccountRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Page<TCostRecord> queryTCostRecord(String beginDate, String endDate, String type, int pageNo, int pageSize)
			throws ParseException {
		TCostRecordExample example = new TCostRecordExample();
		if ((!StringUtils.isEmpty(type)) && (!type.equals("0"))) {
			example.createCriteria().andTypeEqualTo(type).andCostTimeGreaterThanOrEqualTo(beginDate)
					.andCostTimeLessThanOrEqualTo(endDate);
		} else {
			example.createCriteria().andCostTimeGreaterThanOrEqualTo(beginDate).andCostTimeLessThanOrEqualTo(endDate);
		}
		return tCostRecordMapper.selectByExample(example);
	}

	@Override
	public boolean newCost(String creator, String type, String costDesc, Integer amount, String costDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		TCostRecord record = new TCostRecord();
		record.setAmount(amount);
		record.setCostDesc(costDesc);
		record.setType(type);
		record.setCostTime(costDate);
		record.setCreateTime(now);
		record.setCreator(creator);
		tCostRecordMapper.insert(record);
		TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
		opLogRecord.setOperator(creator);
		opLogRecord.setOpDate(sdf.format(new Date()));
		opLogRecord.setOpTime(now);
		opLogRecord.setOpRecordId(record.getId());
		opLogRecord.setOpType(EnumOpType.NEW_COST.ordinal());
		String opDesc = opLogRecord.getOpDate() + " " + creator + "因" + costDesc + "新增:" + amount + "金额" + type
				+ "的成本, 记录id为" + record.getId();
		opLogRecord.setOpDesc(opDesc);
		tFinanicalOpLogMapper.insert(opLogRecord);
		return true;
	}

	@Override
	public boolean modifyCost(int id, String updater, String type, String costDesc, Integer amount, String costDate) {
		TCostRecord costRecord = tCostRecordMapper.selectByPrimaryKey(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		if (costRecord != null) {
			TCostRecord record = new TCostRecord();
			record.setUpdater(updater);
			record.setUpdateTime(now);
			record.setType(type);
			record.setAmount(amount);
			record.setCostDesc(costDesc);
			record.setCostTime(costDate);
			record.setId(costRecord.getId());
			tCostRecordMapper.updateByPrimaryKeySelective(record);
			String m = updater + "修改成本记录:";
			if (!type.equals(costRecord.getType())) {
				m += " 类型由" + costRecord.getType() + "改变为" + type;
			}
			if (!costDesc.equals(costRecord.getCostDesc())) {
				m += " 原因由" + costRecord.getCostDesc() + "改变为" + costDesc;
			}
			if (amount != (costRecord.getAmount())) {
				m += " 金额由" + costRecord.getAmount() + "改变为" + amount;
			}
			if (!costDate.equals(costRecord.getCostTime())) {
				m += " 时间由" + costRecord.getCostTime() + "改变为" + costDate;
			}

			TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
			opLogRecord.setOperator(updater);
			opLogRecord.setOpDate(sdf.format(now));
			opLogRecord.setOpTime(now);
			opLogRecord.setOpRecordId(costRecord.getId());
			opLogRecord.setOpType(EnumOpType.UPDATE_COST.ordinal());
			opLogRecord.setOpDesc(m);
			tFinanicalOpLogMapper.insert(opLogRecord);
			return true;
		}
		return false;
	}

	@Override
	public Page<TAccountRecord> queryTAccountRecord(String beginDate, String endDate, String desc, int pageNo,
			int pageSize) throws ParseException {
		TAccountRecordExample example = new TAccountRecordExample();
		PageHelper.startPage(pageNo, pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isEmpty(desc)) {
			desc = "";
		}
		example.createCriteria().andApplyDescLike("%" + desc + "%")
				.andCreateTimeGreaterThanOrEqualTo(sdf.parse(beginDate))
				.andCreateTimeLessThanOrEqualTo(sdf.parse(endDate));
		return tAccountRecordMapper.selectByExample(example);
	}
}
