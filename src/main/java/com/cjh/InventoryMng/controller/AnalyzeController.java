package com.cjh.InventoryMng.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.entity.VStockInfo;
import com.cjh.InventoryMng.service.StockInfoService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.vo.ManjianVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.cjh.InventoryMng.vo.TejiaVO;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/analyze")
public class AnalyzeController {

	@Autowired
	private StockInfoService stockService;

	@Autowired
	private SupplierService supplierService;

	@RequestMapping(value = "/analyzeManjianPage")
	public String analyzeManjianPage(Model model) {
		return "analyze/analyze_manjian";
	}

	@RequestMapping(value = "/analyzeTejiaPage")
	public String analyzeTejiaPage(Model model) {
		return "analyze/analyze_tejia";
	}

	@RequestMapping(value = "/analyzeManjian", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> analyzeManjian(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String distributionFee = (String) reqMap.get("distributionFee");
		String atLeast = (String) reqMap.get("atLeast");
		String percent = (String) reqMap.get("percent");
		String isAgent = (String) reqMap.get("isAgent");
		String costRatio = (String) reqMap.get("costRatio");
		String profitRatio = (String) reqMap.get("profitRatio");
		List<ManjianVO> returnList = Lists.newArrayList();
		int size = 60;
		if (StringUtils.isEmpty(profitRatio)) {
			profitRatio = "1";
		}
		if (StringUtils.isEmpty(distributionFee)) {
			returnList = null;
			size = 0;
		} else {
			int beginGear = 15;
			int length = 12;
			for (int gear = beginGear + (page - 1) * limit; gear < beginGear + page * limit; gear++) {
				ManjianVO vo = new ManjianVO();
				if (isAgent.equals("0")) {
					// Z=(atLeast>(gear-distributionFee-X)*costRatio)?atLeast:(gear-distributionFee-X)*costRatio
					// (gear-distributionFee-Z-X)/gear>percent
					int i = 1;
					int tmpGear = gear;
					do {
						double tmp = Double.valueOf(atLeast) / Double.valueOf(costRatio);
						double x = 0.0;
						x = tmpGear - Double.valueOf(distributionFee)
								- (tmpGear * Double.valueOf(costRatio) * Double.valueOf(profitRatio))
										/ (1 - Double.valueOf(percent));
						if (tmpGear - Double.valueOf(distributionFee) - x < tmp) {
							x = tmpGear - Double.valueOf(distributionFee) - Double.valueOf(atLeast)
									- tmpGear * Double.valueOf(costRatio) * Double.valueOf(profitRatio);
						}
						if (i == 1) {
							vo.setGear1(tmpGear + "");
							vo.setReduce1(new DecimalFormat("#.##").format(x));
							tmpGear += length;
						} else {
							vo.setGear2(tmpGear + "");
							vo.setReduce2(new DecimalFormat("#.##").format(x));
						}
					} while (i++ < 2);
				} else {
					int i = 1;
					int tmpGear = gear;
					do {
						double x = 0.0;
						x = tmpGear * (1 - Double.valueOf(percent)) - Double.valueOf(distributionFee)
								- (tmpGear * Double.valueOf(costRatio) * Double.valueOf(profitRatio));
						if (i == 1) {
							vo.setGear1(tmpGear + "");
							vo.setReduce1(new DecimalFormat("#.##").format(x));
							tmpGear += length;
						} else {
							vo.setGear2(tmpGear + "");
							vo.setReduce2(new DecimalFormat("#.##").format(x));
						}
					} while (i++ < 2);
				}
				returnList.add(vo);
			}
		}
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", size);
		return t;
	}

	@RequestMapping(value = "/analyzeTejia", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> analyzeTejia(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String distributionFee = (String) reqMap.get("distributionFee");
		String atLeast = (String) reqMap.get("atLeast");
		String primeCost = (String) reqMap.get("primeCost");
		String percent = (String) reqMap.get("percent");
		String isAgent = (String) reqMap.get("isAgent");
		String profitRatio = (String) reqMap.get("profitRatio");
		String discount = (String) reqMap.get("discount");
		String orginPriceStr = (String) reqMap.get("orginPrice");
		if (StringUtils.isEmpty(distributionFee)) {
			return resultMap.toMap();
		} else {
			if ("1".equals(isAgent)) {
				// 特价-特价*配送费减免/起送价-原价*点数=成本*毛利
				// 特价/原价=折扣
				// 即原价=成本*毛利/(折扣*(1-配送费减免/起送价)-点数)
				double orginPrice = Double.valueOf(primeCost) * Double.valueOf(profitRatio)
						/ (Double.valueOf(discount) * (1 - Double.valueOf(distributionFee) / Double.valueOf(atLeast))
								- Double.valueOf(percent));
				double specialOffer = orginPrice * Double.valueOf(discount);
				TejiaVO vo = new TejiaVO();
				vo.setOrginPrice((new DecimalFormat("#.##").format(orginPrice)));
				vo.setSpecialOffer((new DecimalFormat("#.##").format(specialOffer)));
				List<TejiaVO> data = Lists.newArrayList();
				data.add(vo);
				resultMap.setDataList(data);
			} else {
				// 特价-原价*(配送费减免+保底)/起送价=成本*毛利
				// 特价/原价=折扣
				// 即折扣=成本*毛利/原价+(配送费减免+保底)/起送价
				double specialOffer = Double.valueOf(primeCost) * Double.valueOf(profitRatio)
						/ Double.valueOf(orginPriceStr)
						+ (Double.valueOf(distributionFee) + Double.valueOf(percent))
								* (Double.valueOf(orginPriceStr) > Double.valueOf(atLeast) ? Double.valueOf(atLeast)
										: Double.valueOf(orginPriceStr))
								/ Double.valueOf(atLeast) / Double.valueOf(orginPriceStr);
				TejiaVO vo = new TejiaVO();
				vo.setOrginPrice(orginPriceStr);
				vo.setSpecialOffer((new DecimalFormat("#.##").format(specialOffer * Double.valueOf(orginPriceStr))));
				List<TejiaVO> data = Lists.newArrayList();
				data.add(vo);
				resultMap.setDataList(data);
			}
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/analyzeStockPage")
	public String analyzeStockPage(Model model) {
		List<TSupplier> list = supplierService.getAllSupplier();
		TSupplier all = new TSupplier();
		all.setSupplierName("全部");
		all.setId(0);
		List<TSupplier> returnSupplierList = Lists.newArrayList();
		returnSupplierList.add(all);
		for (TSupplier e : list) {
			returnSupplierList.add(e);
		}
		model.addAttribute("supplierList", returnSupplierList);
		return "analyze/analyze_stock";
	}

	@RequestMapping(value = "/queryStockInfos")
	@ResponseBody
	public Map<String, Object> queryStockInfos(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String goodsName = (String) reqMap.get("goodsName");
		if(StringUtils.isEmpty(goodsName)){
			goodsName = "";
		}
		Page<VStockInfo> returnList = stockService.queryStockInfo(goodsName, page, limit);
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", returnList.getTotal());
		return t;
	}

	@RequestMapping(value = "/newStock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newOrder(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String goodsId = (String) reqMap.get("goodsId");
		String count = (String) reqMap.get("count");
		String creator = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).gettUserInfo().getUserId();
		try {
			if (null != stockService.queryStockInfoByGoodsId(Integer.valueOf(goodsId))) {
				resultMap.setFailed();
				resultMap.setMessage("该商品已经有库存记录了，请修改，不必新增");
				return resultMap.toMap();
			}
			if (!stockService.newStockInfo(creator, Integer.valueOf(goodsId), Integer.valueOf(count))) {
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
}
