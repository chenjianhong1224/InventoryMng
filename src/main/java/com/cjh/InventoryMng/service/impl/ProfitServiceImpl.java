package com.cjh.InventoryMng.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.bean.PlatformProfitImportBean;
import com.cjh.InventoryMng.bean.PlatformProfitImportBean.ProfitBean;
import com.cjh.InventoryMng.entity.TAccountRecord;
import com.cjh.InventoryMng.entity.TAccountRecordExample;
import com.cjh.InventoryMng.entity.TCompanyProfit;
import com.cjh.InventoryMng.entity.TCompanyProfitExample;
import com.cjh.InventoryMng.entity.TCostRecord;
import com.cjh.InventoryMng.entity.TCostRecordExample;
import com.cjh.InventoryMng.entity.TMemberReduce;
import com.cjh.InventoryMng.entity.TMemberReduceExample;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TOrderInfoExample;
import com.cjh.InventoryMng.entity.TPlatformProfit;
import com.cjh.InventoryMng.entity.TPlatformProfitExample;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.mapper.CustomInsertMapper;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TAccountRecordMapper;
import com.cjh.InventoryMng.mapper.TCompanyProfitMapper;
import com.cjh.InventoryMng.mapper.TCostRecordMapper;
import com.cjh.InventoryMng.mapper.TMemberReduceMapper;
import com.cjh.InventoryMng.mapper.TOrderInfoMapper;
import com.cjh.InventoryMng.mapper.TPlatformProfitMapper;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.service.ProfitService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;

@Service
@Transactional
public class ProfitServiceImpl implements ProfitService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TPlatformProfitMapper tPlatformProfitMapper;

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CustomInsertMapper customInsertMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Autowired
	private TCompanyProfitMapper tCompanyProfitMapper;

	@Autowired
	private TOrderInfoMapper tOrderInfoMapper;

	@Autowired
	private TMemberReduceMapper tMemberReduceMapper;

	@Autowired
	private TCostRecordMapper tCostRecordMapper;

	@Autowired
	private TAccountRecordMapper tAccountRecordMapper;

	@Override
	public void computeProfit(Integer memberId, String settleDate, Integer meituanProfit, Integer elemeProfit) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<TPlatformProfit> queryPlatformProfit(Integer memberId, String beginDate, String endDate, int pageNo,
			int pageSize) {
		TPlatformProfitExample example = new TPlatformProfitExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andSettleDateGreaterThanOrEqualTo(beginDate)
				.andSettleDateLessThanOrEqualTo(endDate);
		PageHelper.startPage(pageNo, pageSize);
		return tPlatformProfitMapper.selectByExample(example);
	}

	@Override
	public Page<TPlatformProfit> queryPlatformProfit(List<Integer> memberIds, String beginDate, String endDate,
			int pageNo, int pageSize) {
		TPlatformProfitExample example = new TPlatformProfitExample();
		example.createCriteria().andMemberIdIn(memberIds).andSettleDateGreaterThanOrEqualTo(beginDate)
				.andSettleDateLessThanOrEqualTo(endDate);
		PageHelper.startPage(pageNo, pageSize);
		return tPlatformProfitMapper.selectByExample(example);
	}

	@Override
	public Page<TPlatformProfit> queryPlatformProfit(String beginDate, String endDate, int pageNo, int pageSize) {
		TPlatformProfitExample example = new TPlatformProfitExample();
		example.createCriteria().andSettleDateGreaterThanOrEqualTo(beginDate).andSettleDateLessThanOrEqualTo(endDate);
		PageHelper.startPage(pageNo, pageSize);
		return tPlatformProfitMapper.selectByExample(example);
	}

	@Override
	public void importPlatformProfit(List<PlatformProfitImportBean> beans) throws BusinessException {
		for (PlatformProfitImportBean bean : beans) {
			String brandId = sysParaService.getBrandId(bean.getBrandName());
			if (brandId == null) {
				throw new BusinessException("品牌不对");
			}
			Integer memberId = memberService.getMemberId(bean.getMemberName(), brandId);
			if (memberId == null) {
				log.error("加盟商名称不对:" + bean.getMemberName());
				throw new BusinessException("加盟商名称不对");
			}
			for (ProfitBean pBean : bean.getProfitBeans()) {
				if (!StringUtils.isEmpty(pBean.getOrderDate())) {
					TPlatformProfit tPlatformProfit = new TPlatformProfit();
					tPlatformProfit.setMeituanProfit(pBean.getMeituanProfit());
					tPlatformProfit.setElemeProfit(pBean.getElemeProfit());
					tPlatformProfit.setSettleDate(pBean.getOrderDate());
					tPlatformProfit.setMemberId(memberId);
					customInsertMapper.insertOrUpdatePlatformProfit(tPlatformProfit);
				}
			}
		}
	}

	@Override
	public TreeMap<String, String> getLast7DaysProfit() {
		String maxSettleDate = customQueryMapper.queryMaxSettleDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (maxSettleDate == null) {
			maxSettleDate = sdf.format(new Date());
		}
		String[] dates = DateUtils.theNearWeek(maxSettleDate);
		TreeMap<String, String> returnMap = Maps.newTreeMap();
		for (int i = 0; i < dates.length; i++) {
			TPlatformProfitExample example = new TPlatformProfitExample();
			example.createCriteria().andSettleDateEqualTo(dates[i]);
			Page<TPlatformProfit> profits = tPlatformProfitMapper.selectByExample(example);
			double sum = 0.0;
			for (TPlatformProfit tPlatformProfit : profits) {
				sum += ((double) (tPlatformProfit.getMeituanProfit() + tPlatformProfit.getElemeProfit())) / 100;
			}
			returnMap.put(dates[i] + " " + DateUtils.dateToWeek(dates[i]), new DecimalFormat("#.##").format(sum));
		}
		return returnMap;
	}

	@Override
	public Page<TCompanyProfit> queryCompanyProfit(String beginDate, String endDate, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TCompanyProfitExample example = new TCompanyProfitExample();
		example.createCriteria().andMonthGreaterThanOrEqualTo(beginDate).andMonthLessThanOrEqualTo(endDate);
		return tCompanyProfitMapper.selectByExample(example);
	}

	@Override
	public void computeCompanyProfit(String month) {
		synchronized (this.getClass()) {
			TCompanyProfitExample example = new TCompanyProfitExample();
			example.createCriteria().andMonthEqualTo(month);
			Page<TCompanyProfit> profits = tCompanyProfitMapper.selectByExample(example);
			int goodsProfit = 0;
			int managerProfit = 0;
			double managerProfitd = 0.0;
			int freightCost = 0;
			int allCost = 0;
			int approvedAccount = 0;
			int salaryCost = 0;
			int tuiguangReduce = 0;
			TOrderInfoExample orderExample = new TOrderInfoExample();
			orderExample.createCriteria().andStatusEqualTo(1).andOrderDateGreaterThanOrEqualTo(month)
					.andOrderDateLessThanOrEqualTo(month + "-31");
			Page<TOrderInfo> orderList = tOrderInfoMapper.selectByExample(orderExample);
			for (TOrderInfo order : orderList) {
				goodsProfit += (order.getMemberPrice() - order.getPurchasePrice()) * order.getNum();
			}
			TPlatformProfitExample platformProfitExample = new TPlatformProfitExample();
			platformProfitExample.createCriteria().andSettleDateGreaterThan(month)
					.andSettleDateLessThanOrEqualTo(month + "-31");
			Page<TPlatformProfit> platformProfitList = tPlatformProfitMapper.selectByExample(platformProfitExample);
			for (TPlatformProfit platformProfit : platformProfitList) {
				managerProfitd += (platformProfit.getElemeProfit() + platformProfit.getMeituanProfit()) * 0.05;
			}
			TMemberReduceExample reduceExample = new TMemberReduceExample();
			reduceExample.createCriteria().andReduceDateGreaterThanOrEqualTo(month)
					.andReduceDateLessThanOrEqualTo(month + "-31");// 管理费减免
			Page<TMemberReduce> reduceList = tMemberReduceMapper.selectByExample(reduceExample);
			for (TMemberReduce tMemberReduce : reduceList) {
				if (tMemberReduce.getManagerCostFlag() == 1) {
					managerProfitd -= tMemberReduce.getReduceAmount();
				}
				if (tMemberReduce.getManagerCostFlag() == 2) {
					tuiguangReduce += tMemberReduce.getReduceAmount();
				}
			}
			managerProfit = (int) managerProfitd;
			TCostRecordExample costExample = new TCostRecordExample();
			costExample.createCriteria().andCostTimeGreaterThanOrEqualTo(month)
					.andCostTimeLessThanOrEqualTo(month + "-31");
			Page<TCostRecord> costList = tCostRecordMapper.selectByExample(costExample);
			for (TCostRecord tCostRecord : costList) {
				if (tCostRecord.getType().equals("1")) {
					freightCost += tCostRecord.getAmount();
				}
				if (tCostRecord.getType().equals("2")) {
					salaryCost += tCostRecord.getAmount();
				}
				allCost += tCostRecord.getAmount();
			}
			TAccountRecordExample accountExample = new TAccountRecordExample();
			accountExample.createCriteria().andStatusEqualTo(1).andTheDateGreaterThanOrEqualTo(month)
					.andTheDateLessThanOrEqualTo(month + "-31");
			Page<TAccountRecord> recordList = tAccountRecordMapper.selectByExample(accountExample);
			for (TAccountRecord record : recordList) {
				approvedAccount += record.getAmount();
			}
			TCompanyProfit profit = null;
			if (CollectionUtils.isEmpty(profits)) {
				profit = new TCompanyProfit();
			} else {
				profit = profits.get(0);
			}
			profit.setAllCost(allCost);
			profit.setApprovedAccount(approvedAccount);
			profit.setFreightCost(freightCost);
			profit.setGoodsProfit(goodsProfit);
			profit.setManagerProfit(managerProfit);
			profit.setMonth(month);
			profit.setSalaryCost(salaryCost);
			profit.setTuiguangReduce(tuiguangReduce);
			if (CollectionUtils.isEmpty(profits)) {
				tCompanyProfitMapper.insert(profit);
			} else {
				tCompanyProfitMapper.updateByPrimaryKey(profit);
			}
		}
	}

	// @Autowired
	// private TOrderInfoMapper tOrderInfoMapper;
	//
	// @Autowired
	// private TProfitMapper tPorfitMapper;
	//
	// @Override
	// public void computeProfit(Integer memberId, String settleDate, Integer
	// meituanProfit, Integer elemeProfit) {
	// TProfit tProfit = new TProfit();
	// tProfit.setSettleDate(settleDate);
	// TProfitExample profitExample = new TProfitExample();
	// profitExample.createCriteria().andMemberIdEqualTo(memberId).andSettleDateEqualTo(settleDate);
	// List<TProfit> tProfits = tPorfitMapper.selectByExample(profitExample);
	// TOrderInfoExample example = new TOrderInfoExample();
	// example.createCriteria().andMemberIdEqualTo(memberId).andOrderDateEqualTo(settleDate);
	// List<TOrderInfo> orderInfos = tOrderInfoMapper.selectByExample(example);
	// Integer memberPayment = 0;
	// Integer purchasePayment = 0;
	// if (!CollectionUtils.isEmpty(orderInfos)) {
	// for (TOrderInfo tOrderInfo : orderInfos) {
	// memberPayment += tOrderInfo.getMemberPrice();
	// purchasePayment += tOrderInfo.getPurchasePrice();
	// }
	// }
	// tProfit.setElemeProfit(elemeProfit);
	// tProfit.setMeituanProfit(meituanProfit);
	// if (!CollectionUtils.isEmpty(tProfits)) {
	// tProfit.setId(tProfits.get(0).getId());
	// tPorfitMapper.updateByPrimaryKey(tProfit);
	// } else {
	// Double managerCost = new Double(Math.ceil((meituanProfit + elemeProfit) *
	// 0.05));
	// tProfit.setManagerCost(managerCost.intValue());
	// tPorfitMapper.insert(tProfit);
	// }
	// }
	//
	// @Override
	// public List<TProfit> queryPeriodProfit(String period) {
	// String month = period.substring(0, 4);
	// String beginDate = "";
	// String endDate = "";
	// if (period.endsWith("03")) {
	// beginDate = month + "21";
	// endDate = month + "01";
	// } else if (period.endsWith("13")) {
	// beginDate = month + "01";
	// endDate = month + "11";
	// } else if (period.endsWith("23")) {
	// beginDate = month + "11";
	// endDate = month + "21";
	// } else {
	// return null;
	// }
	// TProfitExample example = new TProfitExample();
	// example.createCriteria().andSettleDateGreaterThanOrEqualTo(beginDate).andSettleDateLessThan(endDate);
	// return tPorfitMapper.selectByExample(example);
	// }
	//
	// @Override
	// public List<TProfit> queryMonthProfit(String month) {
	// String beginDate = month + "01";
	// String endDate = month + "32";
	// TProfitExample example = new TProfitExample();
	// example.createCriteria().andSettleDateGreaterThanOrEqualTo(beginDate).andSettleDateLessThan(endDate);
	// return tPorfitMapper.selectByExample(example);
	// }
	//
	// @Override
	// public List<TProfit> queryPeriodProfit(String period, Integer memberId) {
	// String month = period.substring(0, 4);
	// String beginDate = "";
	// String endDate = "";
	// if (period.endsWith("03")) {
	// beginDate = month + "21";
	// endDate = month + "01";
	// } else if (period.endsWith("13")) {
	// beginDate = month + "01";
	// endDate = month + "11";
	// } else if (period.endsWith("23")) {
	// beginDate = month + "11";
	// endDate = month + "21";
	// } else {
	// return null;
	// }
	// TProfitExample example = new TProfitExample();
	// example.createCriteria().andMemberIdEqualTo(memberId).andSettleDateGreaterThanOrEqualTo(beginDate)
	// .andSettleDateLessThan(endDate);
	// return tPorfitMapper.selectByExample(example);
	// }
	//
	// @Override
	// public List<TProfit> queryMonthProfit(String month, Integer memberId) {
	// String beginDate = month + "01";
	// String endDate = month + "32";
	// TProfitExample example = new TProfitExample();
	// example.createCriteria().andMemberIdEqualTo(memberId).andSettleDateGreaterThanOrEqualTo(beginDate)
	// .andSettleDateLessThan(endDate);
	// return tPorfitMapper.selectByExample(example);
	// }

}
