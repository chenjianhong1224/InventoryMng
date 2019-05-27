package com.cjh.InventoryMng.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/manager/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

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

	@RequestMapping(value = "/deletSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupplier(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer id = (Integer) reqMap.get("id");
		boolean flag = supplierService.deleteSupplier(id);
		if (!flag) {
			resultMap.setFailed();
			resultMap.setMessage("删除失败");

		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/addSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupplier(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String suppliersName = (String) reqMap.get("supplierName");
		String phone = (String) reqMap.get("phone");
		String bossName = (String) reqMap.get("bossName");
		String address = (String) reqMap.get("address");
		Integer id = (Integer) reqMap.get("id");

		Integer op = (Integer) reqMap.get("flag");
		if (op == 1) {
			boolean flag = supplierService.addSupplier(suppliersName, phone, bossName, address);
			if (!flag) {
				resultMap.setFailed();
				resultMap.setMessage("新增失败");

			}
		} else {
			boolean flag = supplierService.updateSupplier(id, suppliersName, phone, bossName, address);
			if (!flag) {
				resultMap.setFailed();
				resultMap.setMessage("更新失败");
			}
		}
		return resultMap.toMap();
	}

}
