package com.cjh.InventoryMng.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cjh.InventoryMng.service.ProfitService;
import com.cjh.InventoryMng.utils.DateUtils;

@Component
public class CompanyProfitComputeSchedule {

	private Logger log = LoggerFactory.getLogger(CompanyProfitComputeSchedule.class);

	@Autowired
	private ProfitService profitService;

	@Scheduled(cron = "0 0 01,12 3,4,5 * *")
	public void computeCompanyProfit() {
		try {
			String format = "yyyy-MM";
			Date now = new Date();
			profitService.computeCompanyProfit(DateUtils.getNextMonth(now, -1, format));
		} catch (Exception e) {
			log.error("定时计算公司利润失败", e);
		}
	}
}
