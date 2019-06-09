package com.cjh.InventoryMng.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.service.ProfitService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.vo.ResultMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@EnableAutoConfiguration
public class LoginController {

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private ProfitService profitService;

	@RequestMapping(value = "/login")
	public String login(Model model, HttpServletRequest request) {
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if ("kaptchaValidateFailed".equals(exceptionClassName)) {
			error = "验证码错误";
		} else if (exceptionClassName != null) {
			error = "登录失败";
		}
		model.addAttribute("error", error);
		return "login";
	}

	@RequestMapping(value = "/index")
	public String index(ModelMap map, HttpSession httpSession) {
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		map.put("userName", userInfo.gettUserInfo().getUserName());
		if (userInfo.gettUserInfo().getUserType() == 1) {
			List<TSysParam> manufacturers = sysParaService.getAllManufacturer();
			List<TSysParam> returnManufacturers = Lists.newArrayList();
			TSysParam tSysParam = new TSysParam();
			tSysParam.setParamKey("0");
			tSysParam.setParamValue("全部");
			returnManufacturers.add(tSysParam);
			for (TSysParam manufacturer : manufacturers) {
				returnManufacturers.add(manufacturer);
			}
			map.addAttribute("manufacturers", returnManufacturers);
			return "index";
		} else {
			TreeMap<String, String> profitMap = profitService.getLast7DaysProfit();
			String dates = "";
			String profits = "";
			String key = null;
			String integ = null;
			Iterator iter = profitMap.keySet().iterator();
			while (iter.hasNext()) {
				// 获取key
				key = (String) iter.next();
				// 根据key，获取value
				integ = (String) profitMap.get(key);
				dates += key + ",";
				profits += integ + ",";
			}
			dates = dates.substring(0, dates.length() - 1);
			profits = profits.substring(0, profits.length() - 1);
			map.addAttribute("dates", dates);
			map.addAttribute("profits", profits);
			return "manager";
		}
	}

	@RequestMapping(value = "/")
	public String base(ModelMap map, HttpSession httpSession) {
		return "forward:index";
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}

	@RequestMapping(value = "/userinfo")
	@ResponseBody
	public Map userinfo() {
		ResultMap resultMap = ResultMap.one();
		try {
			UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			resultMap.setDataObject(userInfo.gettUserInfo());
		} catch (Exception e) {
			resultMap.setFailed();
			resultMap.setMessage(e.getMessage());
		}
		return resultMap.toMap();
	}
}
