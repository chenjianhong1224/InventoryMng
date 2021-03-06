package com.cjh.InventoryMng.controller;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cjh.InventoryMng.bean.PlatformProfitImportBean;
import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.bean.PlatformProfitImportBean.ProfitBean;
import com.cjh.InventoryMng.entity.TCompanyProfit;
import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TMemberReduce;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TPlatformProfit;
import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.entity.VMemberBillInfo;
import com.cjh.InventoryMng.entity.VSuppplierBillInfo;
import com.cjh.InventoryMng.entity.VUseGoodsCount;
import com.cjh.InventoryMng.service.GoodsService;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.service.OrderService;
import com.cjh.InventoryMng.service.ProfitService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.utils.DateUtils;
import com.cjh.InventoryMng.vo.CompanyProfitReportVO;
import com.cjh.InventoryMng.vo.GoodsUsedCountVO;
import com.cjh.InventoryMng.vo.MemberBillInfoVO;
import com.cjh.InventoryMng.vo.MemberOrderReportInfoVO;
import com.cjh.InventoryMng.vo.MemberReduceInfoVO;
import com.cjh.InventoryMng.vo.PlatformProfitVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.cjh.InventoryMng.vo.SupplierBillInfoVO;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private ProfitService profitService;

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@RequestMapping(value = "/goods/orderQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMemberOrder(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String memberId = (String) reqMap.get("memberId");
		String brandId = (String) reqMap.get("brandId");
		String nowDate = DateUtils.getCurOrderDate();
		Page<TOrderInfo> pageOrderInfo = null;
		Page<TOrderInfo> allPageOrderInfo = null;
		if (!StringUtils.isEmpty(memberId) && !memberId.equals("0")) {
			TMemberInfo tMemberInfo = memberService.getMemberInfo(Integer.valueOf(memberId));
			if (tMemberInfo == null) {
				resultMap.setMessage("查不到该加盟商");
				List<String> nullList = Lists.newArrayList();
				resultMap.setDataList(nullList);
				return resultMap.toMap();
			}
			pageOrderInfo = orderService.queryEffectiveOrders(tMemberInfo.getId(), beginDate, endDate, page, limit);
			allPageOrderInfo = orderService.queryEffectiveOrders(tMemberInfo.getId(), beginDate, endDate);
		} else {
			if (!StringUtils.isEmpty(brandId) && !brandId.equals("0")) {
				List<TMemberInfo> tMemberInfoList = memberService.getEffectiveMember(brandId);
				if (CollectionUtils.isEmpty(tMemberInfoList)) {
					resultMap.setMessage("该品牌下没有加盟商");
					return resultMap.toMap();
				}
				List<Integer> memberIds = Lists.newArrayList();
				for (TMemberInfo tMemberInfo : tMemberInfoList) {
					memberIds.add(tMemberInfo.getId());
				}
				pageOrderInfo = orderService.queryEffectiveOrders(memberIds, beginDate, endDate, page, limit);
				allPageOrderInfo = orderService.queryEffectiveOrders(memberIds, beginDate, endDate, page, 0);
			} else {
				pageOrderInfo = orderService.queryEffectiveOrders(beginDate, endDate, page, limit);
				allPageOrderInfo = orderService.queryEffectiveOrders(beginDate, endDate, page, 0);
			}
		}
		List<MemberOrderReportInfoVO> returnVOList = Lists.newArrayList();
		for (TOrderInfo tOrderInfo : pageOrderInfo.getResult()) {
			TGoodsInfo tGoodsInfo = goodsService.queryGoods(tOrderInfo.getGoodId());
			TMemberInfo tMemberInfo = memberService.getMemberInfo(tOrderInfo.getMemberId());
			TSupplier tSupplier = supplierService.getSupplier(tOrderInfo.getSupplierid());
			if (tGoodsInfo != null) {
				MemberOrderReportInfoVO memberOrderReportInfoVO = new MemberOrderReportInfoVO();
				memberOrderReportInfoVO.setMemberId(tOrderInfo.getMemberId());
				memberOrderReportInfoVO.setBrand(sysParaService.getBrandName(tMemberInfo.getBrand()));
				memberOrderReportInfoVO
						.setManufactor(sysParaService.getManufacturerName(tGoodsInfo.getManufacturerId() + ""));
				memberOrderReportInfoVO.setGoodName(tGoodsInfo.getGoodName());
				memberOrderReportInfoVO.setId(tOrderInfo.getId());
				memberOrderReportInfoVO.setMemberName((tMemberInfo == null ? null : tMemberInfo.getMemberName()));
				memberOrderReportInfoVO
						.setMemberPrice(new DecimalFormat("#.##").format(((double) tOrderInfo.getMemberPrice()) / 100));
				if (tOrderInfo.getServicePrice() == null) {
					memberOrderReportInfoVO.setPurchasePrice(
							new DecimalFormat("#.##").format(((double) tOrderInfo.getPurchasePrice()) / 100));
				} else {
					memberOrderReportInfoVO.setPurchasePrice(
							new DecimalFormat("#.##").format(((double) tOrderInfo.getPurchasePrice()) / 100));
					memberOrderReportInfoVO.setServicePrice(
							new DecimalFormat("#.##").format((double) tOrderInfo.getServicePrice() / 100));
				}
				memberOrderReportInfoVO.setNum(tOrderInfo.getNum());
				memberOrderReportInfoVO.setOrderDate(tOrderInfo.getOrderDate());
				memberOrderReportInfoVO.setSupplierName((tSupplier == null ? null : tSupplier.getSupplierName()));
				returnVOList.add(memberOrderReportInfoVO);
			}
		}
		Double amount = 0.0;
		Double profit = 0.0;
		for (TOrderInfo tOrderInfo : allPageOrderInfo.getResult()) {
			amount += tOrderInfo.getMemberPrice() * tOrderInfo.getNum();
			profit += (tOrderInfo.getMemberPrice() - tOrderInfo.getPurchasePrice()) * tOrderInfo.getNum();
		}
		resultMap.setDataObject(returnVOList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", pageOrderInfo.getTotal());
		t.put("memberAmount", new DecimalFormat("#.##").format(((double) amount) / 100));
		t.put("profit", new DecimalFormat("#.##").format(((double) profit) / 100));
		return t;
	}

	@RequestMapping(value = "/member/billQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMemberBill(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String memberId = (String) reqMap.get("memberId");
		if (!StringUtils.isEmpty(memberId) && memberId.equals("0")) {
			memberId = null;
		}
		String brandId = (String) reqMap.get("brandId");
		if (!StringUtils.isEmpty(brandId) && brandId.equals("0")) {
			brandId = null;
		}
		Page<VMemberBillInfo> memberBillInfos = memberService.getMemberBill(brandId,
				StringUtils.isEmpty(memberId) ? null : Integer.valueOf(memberId), beginDate, endDate, page, limit);
		Page<VMemberBillInfo> allMemberBillInfos = memberService.getMemberBill(brandId,
				StringUtils.isEmpty(memberId) ? null : Integer.valueOf(memberId), beginDate, endDate, page, 0);
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
		Integer reduceAmount = 0;
		Page<TMemberReduce> reduces = memberService.getMemberReduceExceptTuiguang(
				memberId == null ? null : Integer.valueOf(memberId), beginDate, endDate, page, limit);
		for (TMemberReduce tMemberReduce : reduces) {
			reduceAmount += tMemberReduce.getReduceAmount();
		}
		for (VMemberBillInfo vMemberBillInfo : allMemberBillInfos.getResult()) {
			amount += vMemberBillInfo.getElemeProfit() + vMemberBillInfo.getMeituanProfit();
			orderAmount += vMemberBillInfo.getOrderAmount();
		}
		managementCost = amount * 0.05;
		settleAmount = amount - managementCost - orderAmount + reduceAmount;
		resultMap.setDataObject(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", allMemberBillInfos.getTotal());
		t.put("amount", new DecimalFormat("#.##").format(((double) amount) / 100));
		t.put("managementCost", new DecimalFormat("#.##").format(managementCost / 100));
		t.put("orderAmount", new DecimalFormat("#.##").format(((double) orderAmount) / 100));
		t.put("settleAmount", new DecimalFormat("#.##").format(settleAmount / 100));
		t.put("reduceAmount", new DecimalFormat("#.##").format(((double) reduceAmount) / 100));
		return t;
	}

	@RequestMapping(value = "/member/reduceQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMemberReduce(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String memberId = (String) reqMap.get("memberId");
		if (!StringUtils.isEmpty(memberId) && memberId.equals("0")) {
			memberId = null;
		}

		Page<TMemberReduce> memberReduceInfos = memberService.getMemberAllReduce(
				StringUtils.isEmpty(memberId) ? null : Integer.valueOf(memberId), beginDate, endDate, page, limit);
		List<MemberReduceInfoVO> returnList = Lists.newArrayList();
		for (TMemberReduce tMemberReduce : memberReduceInfos) {
			MemberReduceInfoVO vo = new MemberReduceInfoVO();
			vo.setReduceId(tMemberReduce.getId());
			vo.setManagerCostFlag(tMemberReduce.getManagerCostFlag());
			vo.setReduceDate(tMemberReduce.getReduceDate());
			vo.setReduceItem(tMemberReduce.getReduceItem());
			String memberName = memberService.getMemberInfo(Integer.valueOf(tMemberReduce.getMemberId()))
					.getMemberName();
			vo.setMemberName(memberName);
			vo.setMemberId(Integer.valueOf(tMemberReduce.getMemberId()));
			vo.setReduceAmount(new DecimalFormat("#.##").format(((double) tMemberReduce.getReduceAmount()) / 100));
			returnList.add(vo);
		}
		resultMap.setDataObject(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", memberReduceInfos.getTotal());
		return t;
	}

	@RequestMapping(value = "/supplier/billQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupplierBill(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String supplierId = (String) reqMap.get("supplierId");
		String brandId = (String) reqMap.get("brandId");
		String memberName = (String) reqMap.get("memberName");
		if (!StringUtils.isEmpty(supplierId) && supplierId.equals("0")) {
			supplierId = null;
		}
		if (!StringUtils.isEmpty(brandId) && brandId.equals("0")) {
			brandId = null;
		}
		if (StringUtils.isEmpty(memberName)) {
			memberName = null;
		}
		Page<VSuppplierBillInfo> pageSupplierBillInfo = supplierService.querySupplierBill(supplierId, beginDate,
				endDate, brandId, memberName, (page - 1) * limit, limit);
		Page<VSuppplierBillInfo> allSupplierBillInfo = supplierService.querySupplierBill(supplierId, beginDate, endDate,
				brandId, memberName, null, null);
		List<SupplierBillInfoVO> returnList = Lists.newArrayList();
		for (VSuppplierBillInfo vSuppplierBillInfo : pageSupplierBillInfo) {
			SupplierBillInfoVO vo = new SupplierBillInfoVO();
			vo.setBrand(vSuppplierBillInfo.getBrandName());
			vo.setSupplierName(vSuppplierBillInfo.getSupplierName());
			vo.setGoodName(vSuppplierBillInfo.getGoodName());
			vo.setOrderDate(vSuppplierBillInfo.getOrderDate());
			vo.setMemberPrice(new DecimalFormat("#.##").format(((double) vSuppplierBillInfo.getMemberPrice()) / 100));
			vo.setPrice(new DecimalFormat("#.##").format(((double) vSuppplierBillInfo.getPrice()) / 100));
			vo.setServicePrice(new DecimalFormat("#.##").format(((double) vSuppplierBillInfo.getServicePrice()) / 100));
			vo.setNum(vSuppplierBillInfo.getNum());
			vo.setMemberName(vSuppplierBillInfo.getMemberName());
			vo.setManufacturerName(vSuppplierBillInfo.getManufacturerName());
			returnList.add(vo);
		}
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", allSupplierBillInfo.size());
		Double amount = 0.0;
		Double profit = 0.0;
		Double allServicePrice = 0.0;
		for (VSuppplierBillInfo v : allSupplierBillInfo) {
			amount += v.getPrice() * v.getNum();
			profit += (v.getMemberPrice() - v.getPrice() - v.getServicePrice()) * v.getNum();
			allServicePrice += v.getServicePrice() * v.getNum();
		}
		t.put("amount", new DecimalFormat("#.##").format(((double) amount) / 100));
		t.put("profitAmount", new DecimalFormat("#.##").format((double) profit / 100));
		t.put("allServicePrice", new DecimalFormat("#.##").format((double) allServicePrice / 100));
		return t;
	}

	@RequestMapping(value = "/memberOrderReportPage")
	public String hisOrderReportPage(Model model) {
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

	@RequestMapping(value = "/memberBillReportPage")
	public String memberBillReportPage(Model model) {
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

	@RequestMapping(value = "/supplierBillReportPage")
	public String supplierBillReportPage(Model model) {
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
		return "manager/supplier_bill_report";
	}

	@RequestMapping(value = "/useGoodsReportPage")
	public String useGoodsReportPage(Model model) {
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
		return "manager/use_goods_report";
	}

	@RequestMapping(value = "/goods/queryUseGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUseGoods(@RequestBody Map<String, Object> reqMap)
			throws IllegalAccessException, InvocationTargetException {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String memberId = (String) reqMap.get("memberId");
		String brandId = (String) reqMap.get("brandId");
		String goodsName = (String) reqMap.get("goodsName");
		Integer memberIdInt = null;
		if (StringUtils.isEmpty(brandId)) {
			brandId = null;
		}
		if (!StringUtils.isEmpty(memberId) && !memberId.equals("0")) {
			memberIdInt = Integer.valueOf(memberId);
		}
		Page<VUseGoodsCount> useGoodsCountList = orderService.queryUseGoodsCount(memberIdInt, beginDate, endDate,
				goodsName, page, limit);
		List<GoodsUsedCountVO> returnList = Lists.newArrayList();
		for (VUseGoodsCount vo : useGoodsCountList) {
			GoodsUsedCountVO v = new GoodsUsedCountVO();
			BeanUtils.copyProperties(v, vo);
			v.setManufacturerName(sysParaService.getManufacturerName("" + vo.getManufacturerId()));
			returnList.add(v);
		}
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", useGoodsCountList.getTotal());
		return t;
	}

	@RequestMapping(value = "/compantProfitReprotPage")
	public String compantProfitReprotPage(Model model) {
		return "manager/company_profit_report";
	}

	@RequestMapping(value = "/company/queryCompanyProfit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCompanyProfit(@RequestBody Map<String, Object> reqMap)
			throws IllegalAccessException, InvocationTargetException {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		Page<TCompanyProfit> profitList = profitService.queryCompanyProfit(beginDate, endDate, page, limit);
		Page<TCompanyProfit> allProfitList = profitService.queryCompanyProfit(beginDate, endDate, page, 0);

		List<CompanyProfitReportVO> returnList = Lists.newArrayList();
		double companySettle = 0.0;
		double profit = 0.0;
		for (TCompanyProfit t : profitList) {
			CompanyProfitReportVO vo = new CompanyProfitReportVO();
			vo.setMonth(t.getMonth());
			vo.setAllCost(new DecimalFormat("#.##").format(((double) t.getAllCost()) / 100));
			vo.setApprovedAccount(new DecimalFormat("#.##").format(((double) t.getApprovedAccount()) / 100));
			vo.setFreightCost(new DecimalFormat("#.##").format(((double) t.getFreightCost()) / 100));
			vo.setGoodsProfit(new DecimalFormat("#.##").format(((double) t.getGoodsProfit()) / 100));
			vo.setManagerProfit(new DecimalFormat("#.##").format(((double) t.getManagerProfit()) / 100));
			vo.setSalaryCost(new DecimalFormat("#.##").format(((double) t.getSalaryCost()) / 100));
			vo.setTuiguangReduce(new DecimalFormat("#.##").format(((double) t.getTuiguangReduce()) / 100));
			returnList.add(vo);
		}
		for (TCompanyProfit t : allProfitList) {
			companySettle += 2 * (double) (t.getGoodsProfit() + t.getManagerProfit() - t.getFreightCost()) / 100 / 5;
			profit += 3 * (double) (t.getGoodsProfit() + t.getManagerProfit() - t.getFreightCost()) / 100 / 5
					- (double) (t.getAllCost() - t.getFreightCost()) / 100 - (double) t.getApprovedAccount() / 100;
		}
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", profitList.getTotal());
		t.put("companySettle", new DecimalFormat("#.##").format(companySettle));
		t.put("profit", new DecimalFormat("#.##").format(profit));
		return t;
	}

	@RequestMapping(value = "/company/computeCompanyProfit")
	@ResponseBody
	public Map<String, Object> computeCompanyProfit(@RequestParam("month") String month)
			throws IllegalAccessException, InvocationTargetException {
		ResultMap resultMap = ResultMap.one();
		TUserInfo user = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).gettUserInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			sdf.parse(month);
		} catch (Exception e) {
			resultMap.setFailed();
			resultMap.setMessage("月份格式不正确");
			return resultMap.toMap();
		}
		if (user.getUserType() != 2) {
			resultMap.setFailed();
			resultMap.setMessage("无权限");
		} else {
			profitService.computeCompanyProfit(month);
		}
		return resultMap.toMap();
	}
}
