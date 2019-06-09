package com.cjh.InventoryMng.service;

import java.util.List;
import java.util.TreeMap;

import com.cjh.InventoryMng.bean.PlatformProfitImportBean;
import com.cjh.InventoryMng.entity.TPlatformProfit;
import com.cjh.InventoryMng.exception.BusinessException;
import com.github.pagehelper.Page;

public interface ProfitService {

	Page<TPlatformProfit> queryPlatformProfit(Integer memberId, String beginDate, String endDate, int pageNo,
			int pageSize);

	Page<TPlatformProfit> queryPlatformProfit(List<Integer> memberIds, String beginDate, String endDate, int pageNo,
			int pageSize);

	Page<TPlatformProfit> queryPlatformProfit(String beginDate, String endDate, int pageNo, int pageSize);

	void computeProfit(Integer memberId, String settleDate, Integer meituanProfit, Integer elemeProfit);

	void importPlatformProfit(List<PlatformProfitImportBean> beans) throws BusinessException;
	
	TreeMap<String, String> getLast7DaysProfit();
	
	// List<TProfit> queryPeriodProfit(String period);
	//
	// List<TProfit> queryMonthProfit(String month);
	//
	// List<TProfit> queryPeriodProfit(String period, Integer memberId);
	//
	// List<TProfit> queryMonthProfit(String month, Integer memberId);
}
