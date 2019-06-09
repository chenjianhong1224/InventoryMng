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
import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.service.GoodsService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.vo.GoodsVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/manager/goods")
public class ManagerGoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SysParaService sysParaService;

	@RequestMapping(value = "/queryGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGoods(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String goodName = (String) reqMap.get("goodName");
		if (goodName == null) {
			goodName = "";
		}
		Page<TGoodsInfo> goodList = goodsService.queryGoodsByName(page, limit, goodName);
		List<GoodsVO> returnList = Lists.newArrayList();
		for (TGoodsInfo good : goodList) {
			GoodsVO vo = new GoodsVO();
			vo.setId(good.getId());
			vo.setSpecifications(good.getSpecifications());
			vo.setMemberPrice(new DecimalFormat("#.##").format(((double) good.getMemberPrice()) / 100));
			vo.setPurchasePrice(new DecimalFormat("#.##").format(((double) good.getPurchasePrice()) / 100));
			vo.setManufactureName(sysParaService.getManufacturerName(good.getManufacturerId() + ""));
			vo.setSupplierName(supplierService.getSupplier(good.getSupplierId()).getSupplierName());
			String serviceName = "";
			if (good.getServiceId() != null) {
				serviceName = supplierService.getSupplier(good.getServiceId()).getSupplierName();
				if (good.getServicePrice() != null) {
					vo.setServicePrice(new DecimalFormat("#.##").format(((double) good.getServicePrice()) / 100));
				}
			}
			vo.setGoodName(good.getGoodName());
			vo.setServiceName(serviceName);
			if (good.getStatus() == 1) {
				vo.setStatus("有效");
			} else {
				vo.setStatus("无效");
			}
			returnList.add(vo);
		}
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", goodList.getTotal());
		return t;
	}

	@RequestMapping(value = "/deletGood", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletGoods(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer id = (Integer) reqMap.get("id");
		boolean flag = goodsService.deleteGoods(id);
		if (!flag) {
			resultMap.setFailed();
			resultMap.setMessage("删除失败");
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/addGood", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addGoods(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String goodsName = (String) reqMap.get("goodName");
		String specifications = (String) reqMap.get("specifications");
		String manufacturerId = (String) reqMap.get("manufacture");
		String supplierId = (String) reqMap.get("supplier");
		String purchasePrice = (String) reqMap.get("purchasePrice");
		String memberPrice = (String) reqMap.get("memberPrice");
		String service = (String) reqMap.get("service");
		String servicePrice = (String) reqMap.get("servicePrice");
		Integer op = (Integer) reqMap.get("flag");
		Integer id = (Integer) reqMap.get("id");
		Integer serviceId = null;
		Integer servicePriceInt = null;
		if (!service.equals("0")) {
			serviceId = Integer.valueOf(service);
			servicePriceInt = Integer.valueOf(servicePrice);
		}
		String creator = ((UserInfo) SecurityUtils.getSubject().getPrincipal()).gettUserInfo().getUserId();
		if (op == 1) {
			boolean flag = goodsService.addGoods(goodsName, Integer.valueOf(manufacturerId),
					Integer.valueOf(supplierId), specifications, Integer.valueOf(purchasePrice),
					Integer.valueOf(memberPrice), serviceId, servicePriceInt, creator);
			if (!flag) {
				resultMap.setFailed();
				resultMap.setMessage("新增失败");

			}
		} else {
			boolean flag = goodsService.updateGoods(id, goodsName, Integer.valueOf(manufacturerId),
					Integer.valueOf(supplierId), specifications, Integer.valueOf(purchasePrice),
					Integer.valueOf(memberPrice), serviceId, servicePriceInt, creator);
			if (!flag) {
				resultMap.setFailed();
				resultMap.setMessage("更新失败");
			}
		}
		return resultMap.toMap();
	}
}
