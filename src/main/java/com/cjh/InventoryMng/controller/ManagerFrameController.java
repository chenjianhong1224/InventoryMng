package com.cjh.InventoryMng.controller;

import java.util.List;

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

	@RequestMapping(value = "")
	public String manager(Model model) {
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		model.addAttribute("userName", userInfo.gettUserInfo().getUserName());
		return "manager";
	}

	@RequestMapping(value = "/memberOrderReport")
	public String hisOrderReport(Model model) {
		List<TSysParam> brandList = sysParaService.getAllBrand();
		TSysParam all = new TSysParam();
		all.setParamValue("全部");
		all.setParamKey("0");
		List<TSysParam> returnBrandList = Lists.newArrayList();
		returnBrandList.add(all);
		for (TSysParam e : brandList) {
			returnBrandList.add(e);
		}
		model.addAttribute("brandList", returnBrandList);
		List<TMemberInfo> memberList = memberService.getAllEffectiveMember();
		List<TMemberInfo> returnMemberList = Lists.newArrayList();
		TMemberInfo memberAll = new TMemberInfo();
		memberAll.setId(0);
		memberAll.setMemberName("全部");
		returnMemberList.add(memberAll);
		for (TMemberInfo member : memberList) {
			returnMemberList.add(member);
		}
		model.addAttribute("memberList", returnMemberList);
		return "manager/member_order_report";
	}

	

	@RequestMapping(value = "/memberBillReport")
	public String memberBillReport(Model model) {
		List<TSysParam> brandList = sysParaService.getAllBrand();
		TSysParam all = new TSysParam();
		all.setParamValue("全部");
		all.setParamKey("0");
		List<TSysParam> returnBrandList = Lists.newArrayList();
		returnBrandList.add(all);
		for (TSysParam e : brandList) {
			returnBrandList.add(e);
		}
		model.addAttribute("brandList", returnBrandList);
		List<TMemberInfo> memberList = memberService.getAllEffectiveMember();
		List<TMemberInfo> returnMemberList = Lists.newArrayList();
		TMemberInfo memberAll = new TMemberInfo();
		memberAll.setId(0);
		memberAll.setMemberName("全部");
		returnMemberList.add(memberAll);
		for (TMemberInfo member : memberList) {
			returnMemberList.add(member);
		}
		model.addAttribute("memberList", returnMemberList);
		return "manager/member_bill_report";
	}

	@RequestMapping(value = "/supplierBillReport")
	public String supplierBillReport(Model model) {
		List<TSupplier> returnList = supplierService.getAllSupplier();
		TSupplier supplierAll = new TSupplier();
		supplierAll.setId(0);
		supplierAll.setSupplierName("全部");
		List<TSupplier> returnSupplierList = Lists.newArrayList();
		returnSupplierList.add(supplierAll);
		for (TSupplier tSupplier : returnList) {
			returnSupplierList.add(tSupplier);
		}
		model.addAttribute("supplierList", returnSupplierList);
		return "manager/supplier_bill_report";
	}

	@RequestMapping(value = "/managerResource")
	public String managerResourcePage(Model model) {
		return "manager/manager_resource";
	}

	@RequestMapping(value = "/managerRole")
	public String managerRolePage(Model model) {
		return "manager/manager_role";
	}

	@RequestMapping(value = "/managerGoods")
	public String managerGoodsPage(Model model) {
		return "manager/manager_goods";
	}

	@RequestMapping(value = "/managerSuppliers")
	public String managerSupplierPage(Model model) {
		return "manager/manager_suppliers";
	}

	@RequestMapping(value = "/managerMembers")
	public String managerGroupMapPage(Model model) {
		List<TSysParam> brandList = sysParaService.getAllBrand();
		TSysParam all = new TSysParam();
		all.setParamValue("全部");
		all.setParamKey("0");
		List<TSysParam> returnBrandList = Lists.newArrayList();
		returnBrandList.add(all);
		for (TSysParam e : brandList) {
			returnBrandList.add(e);
		}
		model.addAttribute("brandList", returnBrandList);
		Page<TRoleInfo> roleList = authorizationService.getRoleInfos(0, 0);
		model.addAttribute("roleList", roleList);
		return "manager/manager_members";
	}
}
