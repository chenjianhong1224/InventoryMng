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
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TOrderInfoExample;
import com.cjh.InventoryMng.entity.TPlatformProfit;
import com.cjh.InventoryMng.entity.TPlatformProfitExample;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.mapper.CustomInsertMapper;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
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
