package com.cjh.InventoryMng.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.service.AuthorizationService;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.service.OrderService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.vo.GoodsOptionVO;
import com.cjh.InventoryMng.vo.MemberVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/public")
public class PublicController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SysParaService sysParaService;
	
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(value = "/queryMemberAvailableGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMemberAvailableGoods(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String memberId = (String) reqMap.get("memberId");
		Page<TGoodsInfo> goodInfoList = orderService.queryMemberAvailableGoods(Integer.valueOf(memberId), 0, 0);
		List<GoodsOptionVO> returnList = Lists.newArrayList();
		for (TGoodsInfo g : goodInfoList) {
			GoodsOptionVO vo = new GoodsOptionVO();
			BeanUtils.copyProperties(g, vo);
			returnList.add(vo);
		}
		resultMap.setDataList(returnList);
		return resultMap.toMap();
	}
	
	@RequestMapping(value = "/queryMemberByBrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMemberByBrand(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String brandId = (String) reqMap.get("brandId");
		if (brandId.equals("0")) {
			List<TMemberInfo> resultList = memberService.getAllEffectiveMember();
			resultMap.setDataObject(resultList);
		} else {
			List<TMemberInfo> resultList = memberService.getEffectiveMember(brandId);
			resultMap.setDataObject(resultList);
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/queryManufacturers")
	@ResponseBody
	public Map<String, Object> getManufacturers() {
		ResultMap resultMap = ResultMap.one();
		List<TSysParam> list = sysParaService.getAllManufacturer();
		resultMap.setDataList(list);
		return resultMap.toMap();
	}
	

	@RequestMapping(value = "/querySuppliers")
	@ResponseBody
	public Map<String, Object> getSuppliers() {
		ResultMap resultMap = ResultMap.one();
		List<TSupplier> list = supplierService.getAllSupplier();
		resultMap.setDataList(list);
		return resultMap.toMap();
	}

	@RequestMapping(value = "/querySupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupplier(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String supplierName = (String) reqMap.get("supplierName");
		if (supplierName == null) {
			supplierName = "";
		}
		Page<TSupplier> supplierList = supplierService.querySupplierByName(page, limit, supplierName);
		resultMap.setDataList(supplierList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", supplierList.getTotal());
		return t;
	}
	
	@RequestMapping(value = "/queryMember", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMember(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String brandId = (String) reqMap.get("brandId");
		String memberName = (String) reqMap.get("memberName");
		Page<TMemberInfo> memberInfoList = memberService.getAllMember(memberName, brandId, page, limit);
		List<MemberVO> returnList = Lists.newArrayList();
		for (TMemberInfo tMemberInfo : memberInfoList) {
			MemberVO memberVO = new MemberVO();
			BeanUtils.copyProperties(tMemberInfo, memberVO);
			memberVO.setBrand(sysParaService.getBrandName(memberVO.getBrand()));
			List<TRoleInfo> r = authorizationService.getRoleInfo(tMemberInfo.getUserId());
			if (!CollectionUtils.isEmpty(r)) {
				memberVO.setRole(r.get(0).getRoleName());
			}
			returnList.add(memberVO);
		}
		resultMap.setDataObject(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", memberInfoList.getTotal());
		return t;
	}
}
