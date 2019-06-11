package com.cjh.InventoryMng.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.bean.EmailBean;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.VMemberOrderInfoOrderBy;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TMemberInfoMapper;
import com.cjh.InventoryMng.service.EmailService;
import com.cjh.InventoryMng.service.SysParaService;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;

@Service
public class EmailServiceImpl implements EmailService {

	private Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private TMemberInfoMapper tMemberInfoMapper;

	@Override
	public boolean sendAllOrderEmail(String orderDate) {
		List<String> addressList = sysParaService.getOrderEmailAddress();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		EmailBean sysEmail = sysParaService.getSysEmail();
		sender.setHost(sysEmail.getHost());
		sender.setPassword(sysEmail.getPassword());
		sender.setPort(sysEmail.getPort());
		sender.setUsername(sysEmail.getUserName());
		sender.setDefaultEncoding("Utf-8");
		Properties p = new Properties();
		p.setProperty("mail.smtp.timeout", "25000");
		p.setProperty("mail.smtp.auth", "false");
		sender.setJavaMailProperties(p);
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setFrom(sysEmail.getUserName(), "系统通知");
			messageHelper.setTo(addressList.toArray(new String[addressList.size()]));
			messageHelper.setSubject(orderDate + "订购清单");
			messageHelper.setText(getDateOrderContent(orderDate), true);
			sender.send(mimeMessage);
		} catch (Exception e) {
			log.error("发送订单邮件失败", e);
			return false;
		}
		return true;
	}

	private String getDateOrderContent(String orderDate) {
		Page<VMemberOrderInfoOrderBy> list = customQueryMapper.queryMemberOrderInfoOrderBy(null, null, orderDate);
		int lastMemberId = -1;
		String lastSupplierName = "";
		Map<String, String> sendContentMap = Maps.newHashMap();
		String emailContent = "";
		for (VMemberOrderInfoOrderBy vo : list) {
			if (!lastSupplierName.equals(vo.getSupplierName())) {
				if (!StringUtils.isEmpty(lastSupplierName)) {
					sendContentMap.put(lastSupplierName, emailContent);
				}
				emailContent = "";
				lastSupplierName = vo.getSupplierName();
				emailContent += "<br/>" + vo.getMemberName() + "下单<br/>";
				emailContent += "地址：" + vo.getAddress() + "<br/>";
				emailContent += "联系方式：" + vo.getPhone() + "<br/>";
				lastMemberId = vo.getMemberId();
			} else {
				if (lastMemberId != vo.getMemberId()) {
					emailContent += "<br/>" + vo.getMemberName() + "下单<br/>";
					emailContent += "地址：" + vo.getAddress() + "<br/>";
					emailContent += "联系方式：" + vo.getPhone() + "<br/>";
					lastMemberId = vo.getMemberId();
				}
			}
			emailContent += vo.getGoodsName() + " " + vo.getNum() + "件<br/>";
		}
		sendContentMap.put(lastSupplierName, emailContent);
		String finalContent = "";
		for (String supplierName : sendContentMap.keySet()) {
			finalContent += "========<br/>" + supplierName + "<br/>" + sendContentMap.get(supplierName);
		}
		return finalContent;

	}

	private String getMemberDateOrderContent(String orderDate, Integer memberId) {
		Page<VMemberOrderInfoOrderBy> list = customQueryMapper.queryMemberOrderInfoOrderBy(memberId, null, orderDate);
		int lastMemberId = -1;
		String lastSupplierName = "";
		Map<String, String> sendContentMap = Maps.newHashMap();
		String emailContent = "";
		for (VMemberOrderInfoOrderBy vo : list) {
			if (!lastSupplierName.equals(vo.getSupplierName())) {
				if (!StringUtils.isEmpty(lastSupplierName)) {
					sendContentMap.put(lastSupplierName, emailContent);
				}
				emailContent = "";
				lastSupplierName = vo.getSupplierName();
				emailContent += "<br/>" + vo.getMemberName() + "下单<br/>";
				emailContent += "地址：" + vo.getAddress() + "<br/>";
				emailContent += "联系方式：" + vo.getPhone() + "<br/>";
				lastMemberId = vo.getMemberId();
			} else {
				if (lastMemberId != vo.getMemberId()) {
					emailContent += "<br/>" + vo.getMemberName() + "下单<br/>";
					emailContent += "地址：" + vo.getAddress() + "<br/>";
					emailContent += "联系方式：" + vo.getPhone() + "<br/>";
					lastMemberId = vo.getMemberId();
				}
			}
			emailContent += vo.getGoodsName() + " " + vo.getNum() + "件<br/>";
		}
		sendContentMap.put(lastSupplierName, emailContent);
		String finalContent = "";
		for (String supplierName : sendContentMap.keySet()) {
			finalContent += "========<br/>" + supplierName + "<br/>" + sendContentMap.get(supplierName);
		}
		return finalContent;

	}

	@Override
	public boolean sendMemberOrderEmail(String orderDate, Integer memberId) {
		List<String> addressList = sysParaService.getOrderEmailAddress();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		EmailBean sysEmail = sysParaService.getSysEmail();
		sender.setHost(sysEmail.getHost());
		sender.setPassword(sysEmail.getPassword());
		sender.setPort(sysEmail.getPort());
		sender.setUsername(sysEmail.getUserName());
		sender.setDefaultEncoding("Utf-8");
		Properties p = new Properties();
		p.setProperty("mail.smtp.timeout", "25000");
		p.setProperty("mail.smtp.auth", "false");
		sender.setJavaMailProperties(p);
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper;
		try {
			TMemberInfo member = tMemberInfoMapper.selectByPrimaryKey(memberId);
			messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setFrom(sysEmail.getUserName(), "系统通知");
			messageHelper.setTo(addressList.toArray(new String[addressList.size()]));
			messageHelper.setSubject(member.getMemberName() + " " + orderDate + "订购清单");
			messageHelper.setText(getMemberDateOrderContent(orderDate, memberId), true);
			sender.send(mimeMessage);
		} catch (Exception e) {
			log.error("发送订单邮件失败", e);
			return false;
		}
		return true;
	}

}
