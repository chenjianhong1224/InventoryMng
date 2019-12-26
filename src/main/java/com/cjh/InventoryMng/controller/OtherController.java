package com.cjh.InventoryMng.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.entity.TIntroducer;
import com.cjh.InventoryMng.entity.TIntroducerExample;
import com.cjh.InventoryMng.service.IntroducerService;
import com.cjh.InventoryMng.utils.DateUtils;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/other")
public class OtherController {

	@Autowired
	private IntroducerService introducerService;

	@RequestMapping(value = "/recordIntroducerPage")
	public String recordIntroducer(Model model, HttpServletRequest request) {
		return "other/recordIntroducer";
	}

	@RequestMapping(value = "/queryIntroducerPage")
	public String queryIntroducerPage(Model model, HttpServletRequest request) {
		return "other/queryIntroducer";
	}

	@RequestMapping(value = "/recordIntroducer")
	public String jumpToMeituan(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		String introducer = request.getParameter("introducer");
		if (!StringUtils.isEmpty(introducer)) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateTime = sdf.format(now);
			TIntroducer record = new TIntroducer();
			record.setCustIp(ip);
			record.setIntroducer(introducer);
			record.setdDate(dateTime.substring(0, 10));
			record.setDateTime(dateTime);
			introducerService.insert(record);
		}
		return "redirect:http://i.waimai.meituan.com/external/poi/qrcode/c69723cfc17a0b63ac?utm_source=5801&from=b_qrcode_b";
	}

	@RequestMapping(value = "/queryIntroducer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIntroducer(@RequestBody Map<String, Object> reqMap) throws ParseException {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String introducer = (String) reqMap.get("introducer");
		TIntroducerExample example = new TIntroducerExample();
		example.createCriteria().andIntroducerEqualTo(introducer).andDDateGreaterThanOrEqualTo(beginDate)
				.andDDateLessThanOrEqualTo(endDate);
		Page<TIntroducer> result = introducerService.selectByExample(example, page, limit);
		Map<String, Object> t = resultMap.toMap();
		resultMap.setDataObject(result);
		t.put("count", result.getTotal());
		String nextDateStr = beginDate;
		int ableCount = 0;
		while (true) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nextDate = DateUtils.getTheIntervalDay(sdf.parse(nextDateStr), 1);
			if (DateUtils.getTheIntervalDay(sdf.parse(beginDate), 1).getTime() > sdf.parse(endDate).getTime()) {
				break;
			}
			nextDateStr = sdf.format(nextDate);
			TIntroducerExample dateExample = new TIntroducerExample();
			dateExample.createCriteria().andIntroducerEqualTo(introducer).andDDateEqualTo(nextDateStr);
			Page<TIntroducer> ableResult = introducerService.selectByExample(dateExample, page, 0);
			Map<String, Integer> counter = Maps.newHashMap();
			for (TIntroducer tIntroducer : ableResult) {
				if (StringUtils.isEmpty(tIntroducer.getCustIp())) {
					continue;
				}
				if (counter.containsKey(tIntroducer.getCustIp())) {
					counter.put(tIntroducer.getCustIp(), 1 + counter.get(tIntroducer.getCustIp()));
				} else {
					counter.put(tIntroducer.getCustIp(), 1);
				}
			}
			for (String ip : counter.keySet()) {
				if (counter.get(ip) > 5) {
					ableCount += 5;
				} else {
					ableCount += counter.get(ip);
				}
			}
		}
		t.put("ableCount", ableCount);
		return t;
	}
}
