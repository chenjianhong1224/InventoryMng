package com.cjh.InventoryMng.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.entity.VRoleResourceInfo;
import com.cjh.InventoryMng.service.AuthorizationService;
import com.cjh.InventoryMng.service.GoodsService;
import com.cjh.InventoryMng.service.OrderService;
import com.cjh.InventoryMng.service.SupplierService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.vo.GoodsOptionVO;
import com.cjh.InventoryMng.vo.GoodsVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/sys")
public class SysController {

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SysParaService sysParaService;

	@RequestMapping(value = "/queryResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResource(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Page<TResourceInfo> returnList = authorizationService.getAllResourceInfo();
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", returnList.getTotal());
		return t;
	}

	@RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteResource(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer id = (Integer) reqMap.get("id");
		if (id == null) {
			resultMap.setFailed();
			resultMap.setMessage("传参错误");
			return resultMap.toMap();
		}
		if (!authorizationService.deleteResourceInfo(id)) {
			resultMap.setFailed();
			resultMap.setMessage("删除失败");
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/addResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResource(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String resourceName = (String) reqMap.get("resourceName");
		String resourceUrl = (String) reqMap.get("resourceUrl");
		String perms = (String) reqMap.get("perms");
		if (StringUtils.isEmpty(resourceName) || StringUtils.isEmpty(resourceUrl) || StringUtils.isEmpty(perms)) {
			resultMap.setFailed();
			resultMap.setMessage("传参错误");
			return resultMap.toMap();
		}
		if (!authorizationService.addResourceInfo(resourceName, resourceUrl, perms)) {
			resultMap.setFailed();
			resultMap.setMessage("新增失败");
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/queryRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRole(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		Page<TRoleInfo> returnList = authorizationService.getRoleInfos(page, limit);
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", returnList.getTotal());
		return t;
	}

	@RequestMapping(value = "/queryRoles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRoles() {
		ResultMap resultMap = ResultMap.one();
		Page<TRoleInfo> returnList = authorizationService.getRoleInfos(0, 0);
		List<TRoleInfo> roles = Lists.newArrayList();
		for (TRoleInfo tRoleInfo : returnList) {
			if (tRoleInfo.getStatus() == 1) {
				roles.add(tRoleInfo);
			}
		}
		resultMap.setDataList(roles);
		return resultMap.toMap();
	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRole(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer id = (Integer) reqMap.get("id");
		if (id == null) {
			resultMap.setFailed();
			resultMap.setMessage("传参错误");
			return resultMap.toMap();
		}
		if (!authorizationService.deleteRoleInfo(id)) {
			resultMap.setFailed();
			resultMap.setMessage("删除失败");
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String roleName = (String) reqMap.get("RoleName");
		String desc = (String) reqMap.get("RoleDesc");
		if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(desc)) {
			resultMap.setFailed();
			resultMap.setMessage("传参错误");
			return resultMap.toMap();
		}
		if (!authorizationService.addRoleInfo(roleName, desc)) {
			resultMap.setFailed();
			resultMap.setMessage("新增失败");
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/queryResourceOfRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResourceOfRole(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		Integer roleId = new Integer((String) reqMap.get("roleId"));
		if (roleId == null || roleId == 0) {
			List<String> nullList = Lists.newArrayList();
			resultMap.setDataList(nullList);
			return resultMap.toMap();
		}
		Page<VRoleResourceInfo> returnList = authorizationService.queryResourceOfRole(roleId, page, limit);
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", returnList.getTotal());
		return t;
	}

	@RequestMapping(value = "/bindResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bindResource(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer resourceId = (Integer) reqMap.get("resourceId");
		String roleId = (String) reqMap.get("roleId");
		Boolean flag = (Boolean) reqMap.get("flag");
		if (StringUtils.isEmpty(resourceId) || StringUtils.isEmpty(roleId) || flag == null) {
			resultMap.setFailed();
			resultMap.setMessage("传参错误");
			return resultMap.toMap();
		}
		if (flag) {
			flag = authorizationService.bindRoleResource(Integer.valueOf(roleId), Integer.valueOf(resourceId));
		} else {
			flag = authorizationService.unBindRoleResource(Integer.valueOf(roleId), Integer.valueOf(resourceId));
		}
		if (flag) {
			resultMap.setSuccess();
		} else {
			resultMap.setFailed();
			resultMap.setMessage("更新失败");
		}
		return resultMap.toMap();
	}
	

	
	
	
	@RequestMapping(value = "/managerResourcePage")
	public String managerResourcePage(Model model) {
		return "manager/manager_resource";
	}

	@RequestMapping(value = "/managerRolePage")
	public String managerRolePage(Model model) {
		return "manager/manager_role";
	}

	@RequestMapping(value = "/managerGoodsPage")
	public String managerGoodsPage(Model model) {
		return "manager/manager_goods";
	}

	@RequestMapping(value = "/managerSuppliersPage")
	public String managerSupplierPage(Model model) {
		return "manager/manager_suppliers";
	}

	@RequestMapping(value = "/managerMembersPage")
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
