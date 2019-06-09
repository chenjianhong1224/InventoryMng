package com.cjh.InventoryMng.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TResourceInfoExample;
import com.cjh.InventoryMng.entity.TResourceRole;
import com.cjh.InventoryMng.entity.TResourceRoleExample;
import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.entity.TRoleInfoExample;
import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.entity.TUserRole;
import com.cjh.InventoryMng.entity.TUserRoleExample;
import com.cjh.InventoryMng.entity.VRoleResourceInfo;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TResourceInfoMapper;
import com.cjh.InventoryMng.mapper.TResourceRoleMapper;
import com.cjh.InventoryMng.mapper.TRoleInfoMapper;
import com.cjh.InventoryMng.mapper.TUserInfoMapper;
import com.cjh.InventoryMng.mapper.TUserRoleMapper;
import com.cjh.InventoryMng.service.AuthorizationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;

@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private TResourceInfoMapper tResourceInfoMapper;

	@Autowired
	private TResourceRoleMapper tResourceRoleMapper;

	@Autowired
	private TRoleInfoMapper tRoleInfoMapper;

	@Autowired
	private TUserRoleMapper tUserRoleMapper;

	@Autowired
	private TUserInfoMapper tUserMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Override
	public Page<TResourceInfo> getAllResourceInfo() {
		TResourceInfoExample example = new TResourceInfoExample();
		PageHelper.startPage(0, 0);
		return tResourceInfoMapper.selectByExample(example);
	}

	@Override
	public boolean deleteResourceInfo(Integer resourceId) {
		int i = tResourceInfoMapper.deleteByPrimaryKey(resourceId);
		TResourceRoleExample example = new TResourceRoleExample();
		example.createCriteria().andResourceIdEqualTo(resourceId);
		List<TResourceRole> returnList = tResourceRoleMapper.selectByExample(example);
		for (TResourceRole tResourceRole : returnList) {
			tResourceRoleMapper.deleteByPrimaryKey(tResourceRole.getId());
		}
		return i == 1;
	}

	@Override
	public boolean addResourceInfo(String resourceName, String resourceUrl, String resourcePerms) {
		TResourceInfo record = new TResourceInfo();
		record.setPerms(resourcePerms);
		record.setResourceName(resourceName);
		record.setResourceUrl(resourceUrl);
		int i = tResourceInfoMapper.insert(record);
		return i == 1;
	}

	@Override
	public Page<TRoleInfo> getRoleInfos(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TRoleInfoExample TRoleInfo = new TRoleInfoExample();
		return tRoleInfoMapper.selectByExample(TRoleInfo);
	}

	@Override
	public boolean deleteRoleInfo(Integer id) {
		int i = tRoleInfoMapper.deleteByPrimaryKey(id);
		TResourceRoleExample example = new TResourceRoleExample();
		example.createCriteria().andRoleIdEqualTo(id);
		List<TResourceRole> returnList = tResourceRoleMapper.selectByExample(example);
		for (TResourceRole tResourceRole : returnList) {
			tResourceRoleMapper.deleteByPrimaryKey(tResourceRole.getId());
		}
		return i == 1;
	}

	@Override
	public boolean addRoleInfo(String roleName, String desc) {
		TRoleInfo tRoleInfo = new TRoleInfo();
		tRoleInfo.setCreateTime(new Date());
		tRoleInfo.setRoleName(roleName);
		tRoleInfo.setDescription(desc);
		tRoleInfo.setStatus(1);
		return 1 == tRoleInfoMapper.insert(tRoleInfo);
	}

	@Override
	public Page<VRoleResourceInfo> queryResourceOfRole(Integer roleId, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return customQueryMapper.queryResourceOfRole(roleId);
	}

	@Override
	public boolean bindRoleResource(Integer roleId, Integer resourceId) {
		TResourceRoleExample example = new TResourceRoleExample();
		example.createCriteria().andRoleIdEqualTo(roleId).andResourceIdEqualTo(resourceId);
		List<TResourceRole> resList = tResourceRoleMapper.selectByExample(example);
		int i = 0;
		if (CollectionUtils.isEmpty(resList)) {
			TResourceRole record = new TResourceRole();
			record.setResourceId(resourceId);
			record.setRoleId(roleId);
			record.setStatus(1);
			i = tResourceRoleMapper.insert(record);
		} else {
			TResourceRole record = resList.get(0);
			record.setStatus(1);
			i = tResourceRoleMapper.updateByPrimaryKey(record);
		}
		return i == 1;
	}

	@Override
	public boolean unBindRoleResource(Integer roleId, Integer resourceId) {
		TResourceRoleExample example = new TResourceRoleExample();
		example.createCriteria().andRoleIdEqualTo(roleId).andResourceIdEqualTo(resourceId);
		List<TResourceRole> resList = tResourceRoleMapper.selectByExample(example);
		int i = 0;
		if (CollectionUtils.isEmpty(resList)) {
			return true;
		} else {
			TResourceRole record = resList.get(0);
			record.setStatus(0);
			i = tResourceRoleMapper.updateByPrimaryKey(record);
		}
		return i == 1;
	}

	@Override
	public List<TRoleInfo> getRoleInfo(String userId) {
		TUserRoleExample example = new TUserRoleExample();
		example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(1);
		List<TUserRole> userRoleList = tUserRoleMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(userRoleList)) {
			return null;
		}
		List<TRoleInfo> returnList = Lists.newArrayList();
		for (TUserRole tUserRole : userRoleList) {
			TRoleInfo r = tRoleInfoMapper.selectByPrimaryKey(tUserRole.getRoleId());
			if (r != null) {
				returnList.add(r);
			}
		}
		return returnList;
	}

	@Override
	public boolean updatePassword(String userId, String password) {
		TUserInfo record = new TUserInfo();
		record.setUserId(userId);
		record.setPassword(password);
		return 1 == tUserMapper.updateByPrimaryKeySelective(record);
	}

}
