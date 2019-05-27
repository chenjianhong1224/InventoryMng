package com.cjh.InventoryMng.controller;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.SecurityUtils;
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
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TPlatformProfit;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.service.FinancialService;
import com.cjh.InventoryMng.service.MemberService;
import com.cjh.InventoryMng.service.ProfitService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.utils.DateUtils;
import com.cjh.InventoryMng.vo.PlatformProfitVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/financial")
public class FinancialController {

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ProfitService profitService;

	@Autowired
	private FinancialService financialService;

	@RequestMapping(value = "/newOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newOrder(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String memberId = (String) reqMap.get("memberId");
		String goodsId = (String) reqMap.get("goodsId");
		String num = (String) reqMap.get("num");
		String orderDate = (String) reqMap.get("orderDate");
		String creator = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).gettUserInfo().getUserId();
		String memberName = memberService.getMemberInfo(Integer.valueOf(memberId)).getMemberName();
		try {
			if (!financialService.newMemberOrder(creator, Integer.valueOf(memberId), memberName,
					Integer.valueOf(goodsId), Integer.valueOf(num), orderDate)) {
				resultMap.setFailed();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.setFailed();
			resultMap.setMessage("异常" + e.getMessage());
			return resultMap.toMap();
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/modifyOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyOrder(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String id = (String) reqMap.get("id");
		String memberPrice = (String) reqMap.get("memberPrice");
		String purchasePrice = (String) reqMap.get("purchasePrice");
		String servicePrice = (String) reqMap.get("servicePrice");
		if (StringUtils.isEmpty(servicePrice)) {
			servicePrice = "0";
		}
		String num = (String) reqMap.get("num");
		String orderDate = (String) reqMap.get("orderDate");
		String creator = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).gettUserInfo().getUserId();
		try {
			if (!financialService.modifyOrder(creator, Integer.valueOf(id), Integer.valueOf(purchasePrice),
					Integer.valueOf(num), Integer.valueOf(memberPrice), Integer.valueOf(servicePrice), orderDate)) {
				resultMap.setFailed();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.setFailed();
			resultMap.setMessage("异常" + e.getMessage());
			return resultMap.toMap();
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/queryPlatformProfitContent")
	public String platformProfitContentPage(Model model) {
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
		return "manager/platform_profit_content";
	}

	@RequestMapping(value = "/queryFinancialMemberOrder")
	public String queryFinancialMemberOrderPage(Model model) {
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
		return "manager/financial_op_order";
	}

	@RequestMapping(value = "/queryPlatformProfit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPlatformProfit(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String memberId = (String) reqMap.get("memberId");
		String brandId = (String) reqMap.get("brandId");
		String nowDate = DateUtils.getCurOrderDate();
		Page<TPlatformProfit> pageOrderInfo = null;
		Page<TPlatformProfit> allPageOrderInfo = null;
		if (!StringUtils.isEmpty(memberId) && !memberId.equals("0")) {
			TMemberInfo tMemberInfo = memberService.getMemberInfo(Integer.valueOf(memberId));
			if (tMemberInfo == null) {
				resultMap.setMessage("查不到该加盟商");
				List<String> nullList = Lists.newArrayList();
				resultMap.setDataList(nullList);
				return resultMap.toMap();
			}
			pageOrderInfo = profitService.queryPlatformProfit(tMemberInfo.getId(), beginDate, endDate, page, limit);
			allPageOrderInfo = profitService.queryPlatformProfit(tMemberInfo.getId(), beginDate, endDate, page, 0);
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
				pageOrderInfo = profitService.queryPlatformProfit(memberIds, beginDate, endDate, page, limit);
				allPageOrderInfo = profitService.queryPlatformProfit(memberIds, beginDate, endDate, page, 0);
			} else {
				pageOrderInfo = profitService.queryPlatformProfit(beginDate, endDate, page, limit);
				allPageOrderInfo = profitService.queryPlatformProfit(beginDate, endDate, page, 0);
			}
		}
		List<PlatformProfitVO> returnVOList = Lists.newArrayList();
		for (TPlatformProfit tPlatformProfit : pageOrderInfo) {
			PlatformProfitVO vo = new PlatformProfitVO();
			String brandName = memberService.getMemberBrand(tPlatformProfit.getMemberId());
			vo.setBrand(brandName);
			vo.setMemberName(memberService.getMemberInfo(tPlatformProfit.getMemberId()).getMemberName());
			vo.setSettleDate(tPlatformProfit.getSettleDate());
			vo.setMeituanProfit(new DecimalFormat("#.##").format(((double) tPlatformProfit.getMeituanProfit()) / 100));
			vo.setElemeProfit(new DecimalFormat("#.##").format(((double) tPlatformProfit.getElemeProfit()) / 100));
			returnVOList.add(vo);
		}
		Integer profitAmount = 0;
		Double managementCost = 0.0;
		for (TPlatformProfit tPlatformProfit : allPageOrderInfo.getResult()) {
			profitAmount += tPlatformProfit.getElemeProfit() + tPlatformProfit.getMeituanProfit();
			managementCost += (tPlatformProfit.getElemeProfit() + tPlatformProfit.getMeituanProfit()) * 0.05;
		}
		resultMap.setDataObject(returnVOList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", pageOrderInfo.getTotal());
		t.put("profitAmount", new DecimalFormat("#.##").format(((double) profitAmount) / 100));
		t.put("managementCost", new DecimalFormat("#.##").format(managementCost / 100));
		return t;
	}

	@RequestMapping("/importPlatformProfit")
	@ResponseBody
	public Map<String, Object> importPlatformProfit(@RequestParam MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 判断文件名是否为空
		ResultMap resultMap = ResultMap.one();
		if (file == null) {
			resultMap.setFailed();
			resultMap.setMessage("上传的文件为空");
			return resultMap.toMap();
		}
		// 获取文件名
		String name = file.getOriginalFilename();
		// 判断文件大小、即名称
		long size = file.getSize();
		if (name == null || ("").equals(name) && size == 0) {
			resultMap.setFailed();
			resultMap.setMessage("上传的文件异常");
			return resultMap.toMap();
		}
		name = name.substring(0, name.indexOf("."));
		ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
		HSSFWorkbook book = new HSSFWorkbook(in);
		int totalRows = book.getSheetAt(0).getPhysicalNumberOfRows();
		if (totalRows < 15) {
			resultMap.setFailed();
			resultMap.setMessage("上传的文件行数异常");
			in.close();
			book.close();
			return resultMap.toMap();
		}
		Row row = book.getSheetAt(0).getRow(0);
		int totalCells = row.getLastCellNum();
		if (totalCells < 9) {
			resultMap.setFailed();
			resultMap.setMessage("上传的文件列数异常");
			in.close();
			book.close();
			return resultMap.toMap();
		}
		int beginRownum = 0;
		int beginCellnum = 0;
		boolean endFlag = false;
		List<PlatformProfitImportBean> platformProfitImportBeans = Lists.newArrayList();
		while (!endFlag) {
			PlatformProfitImportBean bean = new PlatformProfitImportBean();
			List<ProfitBean> profitBeans = Lists.newArrayList();
			for (int i = beginRownum; i < beginRownum + 15; i++) {
				PlatformProfitImportBean.ProfitBean profitBean = new PlatformProfitImportBean.ProfitBean();
				for (int j = beginCellnum; (j < beginCellnum + 9) && (j < totalCells); j++) {
					Row nowRow = book.getSheetAt(0).getRow(i);
					bean.setBrandName(name);
					nowRow.getCell(j).setCellType(CellType.STRING);
					try {
						if (i % 15 == 0 && j % 9 == 0) {
							bean.setMemberName(nowRow.getCell(j).getStringCellValue());
							break;
						} else if (j % 9 == 1) {
							String orderDate = nowRow.getCell(j).getStringCellValue().replace(".", "-");
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							try {
								format.setLenient(false);
								// 设置lenient为false.
								// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
								format.parse(orderDate);
							} catch (ParseException e) {
								break;
							}
							profitBean.setOrderDate(orderDate);
						} else if (j % 9 == 2) {
							String profit = StringUtils.isEmpty(nowRow.getCell(j).getStringCellValue()) ? "0"
									: nowRow.getCell(j).getStringCellValue();
							profitBean.setMeituanProfit((int) (Double.valueOf(profit) * 100));
						} else if (j % 9 == 3) {
							String profit = StringUtils.isEmpty(nowRow.getCell(j).getStringCellValue()) ? "0"
									: nowRow.getCell(j).getStringCellValue();
							profitBean.setElemeProfit((int) (Double.valueOf(profit) * 100));
						}
					} catch (Exception e) {
						resultMap.setFailed();
						resultMap.setMessage("第" + i + "行" + "第" + j + "列格式有误");
						in.close();
						book.close();
						return resultMap.toMap();
					}
				}
				if (i % 15 != 0) {
					profitBeans.add(profitBean);
				}
			}
			bean.setProfitBeans(profitBeans);
			platformProfitImportBeans.add(bean);
			beginCellnum += 9;
			if (beginCellnum > totalCells - 1) {
				beginCellnum = 0;
				beginRownum += 15;
			}
			if (beginRownum + 15 > totalRows) {
				endFlag = true;
			}
		}
		try {
			profitService.importPlatformProfit(platformProfitImportBeans);
		} catch (Exception e) {
			resultMap.setFailed();
			resultMap.setMessage(e.getMessage());
		}
		in.close();
		book.close();
		return resultMap.toMap();
	}
}
