package com.cjh.InventoryMng.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cjh.InventoryMng.service.EmailService;

@Component
public class SendOrderEmailSchedule {

	@Autowired
	private EmailService emailService;

	private Logger log = LoggerFactory.getLogger(SendOrderEmailSchedule.class);

	@Scheduled(cron = "0 2 15 * * *")
	public void sendAllOrderEmail() {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			String nowDate = sdf1.format(now);
			emailService.sendAllOrderEmail(nowDate);
		} catch (Exception e) {
			log.error("定时发送订单邮件失败", e);
		}
	}
}
