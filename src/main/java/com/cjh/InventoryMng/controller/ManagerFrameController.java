package com.cjh.InventoryMng.controller;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.service.AuthorizationService;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.service.ProfitService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.service.SysParaService;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/manager")
public class ManagerFrameController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ProfitService profitService;

	@RequestMapping(value = "")
	public String manager(Model model) {
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		model.addAttribute("userName", userInfo.gettUserInfo().getUserName());
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
			dates = "\'" + key + "\',";
			profits = "\'" + integ + "\',";
		}
		dates = dates.substring(0, dates.length() - 1);
		profits = profits.substring(0, profits.length() - 1);
		model.addAttribute("dates", dates);
		model.addAttribute("profits", profits);
		return "manager";
	}
}
