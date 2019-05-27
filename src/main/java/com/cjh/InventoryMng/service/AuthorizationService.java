package com.cjh.InventoryMng.service;

import java.util.List;

import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.entity.VRoleResourceInfo;
import com.github.pagehelper.Page;

public interface AuthorizationService {

	Page<TResourceInfo> getAllResourceInfo();

	boolean deleteResourceInfo(Integer resourceid);

	boolean addResourceInfo(String resourceName, String resourceUrl, String resourcePerms);

	Page<TRoleInfo> getRoleInfos(int pageNo, int pageSize);

	boolean deleteRoleInfo(Integer id);

	boolean addRoleInfo(String roleName, String desc);

	Page<VRoleResourceInfo> queryResourceOfRole(Integer roleId, int pageNo, int pageSize);

	boolean bindRoleResource(Integer roleId, Integer resourceId);

	boolean unBindRoleResource(Integer roleId, Integer resourceId);
	
	List<TRoleInfo> getRoleInfo(String userId);
}
