package com.cjh.InventoryMng.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjh.InventoryMng.constants.Constants.EnumOpType;
import com.cjh.InventoryMng.entity.TFinanicalOpLog;
import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TOrderInfoExample;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.mapper.TFinanicalOpLogMapper;
import com.cjh.InventoryMng.mapper.TGoodsInfoMapper;
import com.cjh.InventoryMng.mapper.TMemberInfoMapper;
import com.cjh.InventoryMng.mapper.TOrderInfoMapper;
import com.cjh.InventoryMng.service.FinancialService;
import com.github.pagehelper.Page;

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

	@Override
	public boolean newMemberOrder(String operator, Integer memberId, String memberName, Integer goodId, Integer buyNum,
			String orderDate) throws BusinessException, Exception {
		TGoodsInfo goodsInfo = tGoodsInfoMapper.selectByPrimaryKey(goodId);
		String opDesc = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		if (goodsInfo != null && goodsInfo.getStatus() == 1) {
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
				record.setPurchasePrice(goodsInfo.getPurchasePrice() + goodsInfo.getServicePrice());
				record.setStatus(1);
				if (goodsInfo.getServiceId() != null) {
					record.setServiceId(goodsInfo.getServiceId());
				}
				if (goodsInfo.getServicePrice() != null) {
					record.setServicePrice(goodsInfo.getServicePrice());
				}
				int insertSucc = tOrderInfoMapper.insert(record);
				opDesc = orderDate + " " + operator + "代替" + memberName + "新增:" + buyNum + "件" + goodsInfo.getGoodName()
						+ "， 记录id为" + record.getId();
			} else {
				record = orders.get(0);
				int oldNum = record.getNum();
				int oldStatus = record.getStatus();
				record.setOrderDate(orderDate);
				record.setOrderTime(sdf.parse(orderDate));
				record.setNum(buyNum);
				record.setStatus(1);
				tOrderInfoMapper.updateByPrimaryKey(record);
				opDesc = orderDate + " " + operator + "代替" + memberName + "更改:" + goodsInfo.getGoodName() + "由原来"
						+ (oldStatus == 1 ? "生效" : "失效") + oldNum + "件变为" + buyNum + "件" + "， 记录id为" + record.getId();
			}
			TFinanicalOpLog opLogRecord = new TFinanicalOpLog();
			opLogRecord.setOperator(operator);
			opLogRecord.setOpDate(sdf.format(now));
			opLogRecord.setOpTime(now);
			opLogRecord.setOpRecordId(record.getId());
			opLogRecord.setOpType(EnumOpType.NEW_ORDER.ordinal());
			opLogRecord.setOpDesc(opDesc);
			tFinanicalOpLogMapper.insert(opLogRecord);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean modifyOrder(String operator, Integer id, Integer purchasePrice, Integer buyNum, Integer memberPrice,
			Integer servicePrice, String orderDate) throws BusinessException, Exception {
		TOrderInfo orderInfo = tOrderInfoMapper.selectByPrimaryKey(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		if (orderInfo != null) {
			int oldStatus = orderInfo.getStatus();
			int oldPurchasePrice = orderInfo.getPurchasePrice();
			int oldMemberPrice = orderInfo.getMemberPrice();
			int oldServicePrice = orderInfo.getServicePrice();
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
			if (oldServicePrice != orderInfo.getServicePrice()) {
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
			return true;
		}
		return false;
	}

}
