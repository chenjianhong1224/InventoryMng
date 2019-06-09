package com.cjh.InventoryMng.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.service.AuthorizationService;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.vo.MemberVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.cjh.InventoryMng.entity.VMemberSupplierMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/manager/member")
public class ManagerMemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(value = "/deleteMember", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMember(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer id = (Integer) reqMap.get("id");
		boolean flag = memberService.deleteMember(id);
		if (!flag) {
			resultMap.setFailed();
			resultMap.setMessage("删除失败");
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMember(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String memberName = (String) reqMap.get("memberName");
		String city = (String) reqMap.get("city");
		String province = (String) reqMap.get("province");
		String phone = (String) reqMap.get("phone");
		String address = (String) reqMap.get("address");
		String brand = (String) reqMap.get("brand");
		String accName = (String) reqMap.get("accName");
		String accNo = (String) reqMap.get("accNo");
		Integer op = (Integer) reqMap.get("flag");
		Integer id = (Integer) reqMap.get("id");
		Integer roleId = Integer.valueOf((String) reqMap.get("roleId"));
		String creator = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).gettUserInfo().getUserId();
		if (op == 1) {
			memberService.newMember(memberName, city, province, phone, address, brand, accNo, accName, roleId);
		} else {
			memberService.updateMember(id, memberName, city, province, phone, address, brand, accNo, accName, roleId);
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/queryMemberSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberSupplierQuery(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String memberId = (String) reqMap.get("memberId");
		if(StringUtils.isEmpty(memberId)){
			return resultMap.toMap();
		}
		Page<VMemberSupplierMap> vMemberSupplierList = memberService.querySupplierOfMember(Integer.valueOf(memberId),
				page, limit);
		resultMap.setDataObject(vMemberSupplierList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", vMemberSupplierList.getTotal());
		return t;
	}

	@RequestMapping(value = "/bindSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bindSupplier(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Boolean flag = (Boolean) reqMap.get("flag");
		Integer supplierId = (Integer) reqMap.get("supplierId");
		String memberId = (String) reqMap.get("memberId");
		if (!memberService.mapSupplier(Integer.valueOf(memberId), supplierId, flag)) {
			resultMap.setFailed();
			resultMap.setMessage("关联失败");
		}
		return resultMap.toMap();
	}
}
