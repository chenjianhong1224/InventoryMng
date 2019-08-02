package com.cjh.InventoryMng.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TMemberReduce;
import com.cjh.InventoryMng.entity.VMemberBillInfo;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.vo.MemberBillInfoVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/member/bill")
public class MemberBillController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBill(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		Integer memberId = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId();
		String brandId = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getBrandId();
		if (memberId == null || brandId == null) {
			resultMap.setFailed();
			return resultMap.toMap();
		}
		Page<VMemberBillInfo> memberBillInfos = memberService.getMemberBill(brandId, memberId, beginDate, endDate, page,
				limit);
		Page<VMemberBillInfo> allMemberBillInfos = memberService.getMemberBill(brandId, memberId, beginDate, endDate,
				page, 0);
		List<MemberBillInfoVO> returnList = Lists.newArrayList();
		for (VMemberBillInfo vMemberBillInfo : memberBillInfos) {
			MemberBillInfoVO vo = new MemberBillInfoVO();
			vo.setBrand(vMemberBillInfo.getBrandName());
			vo.setMemberName(vMemberBillInfo.getMemberName());
			vo.setSettleDate(vMemberBillInfo.getSettleDate());
			vo.setOrderAmount(new DecimalFormat("#.##").format(((double) vMemberBillInfo.getOrderAmount()) / 100));
			vo.setMeituan(new DecimalFormat("#.##").format(((double) vMemberBillInfo.getMeituanProfit()) / 100));
			vo.setEleme(new DecimalFormat("#.##").format(((double) vMemberBillInfo.getElemeProfit()) / 100));
			returnList.add(vo);
		}
		Integer amount = 0;
		Double managementCost = 0.0;
		Integer orderAmount = 0;
		Double settleAmount = 0.0;
		for (VMemberBillInfo vMemberBillInfo : allMemberBillInfos.getResult()) {
			amount += vMemberBillInfo.getElemeProfit() + vMemberBillInfo.getMeituanProfit();
			orderAmount += vMemberBillInfo.getOrderAmount();
		}
		Integer reduceAmount = 0;
		Page<TMemberReduce> reduces = memberService.getMemberReduceExceptTuiguang(memberId == null ? null : Integer.valueOf(memberId),
				beginDate, endDate, page, limit);
		for (TMemberReduce tMemberReduce : reduces) {
			reduceAmount += tMemberReduce.getReduceAmount();
		}
		managementCost = amount * 0.05;
		settleAmount = amount - managementCost - orderAmount + reduceAmount;
		resultMap.setDataObject(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", allMemberBillInfos.getTotal());
		t.put("profitAmount", new DecimalFormat("#.##").format(((double) amount) / 100));
		t.put("managementCost", new DecimalFormat("#.##").format(managementCost / 100));
		t.put("allOrderAmount", new DecimalFormat("#.##").format(((double) orderAmount) / 100));
		t.put("settleAmount", new DecimalFormat("#.##").format(settleAmount / 100));
		t.put("reduceAmount", new DecimalFormat("#.##").format(((double) reduceAmount) / 100));
		return t;
	}
}
