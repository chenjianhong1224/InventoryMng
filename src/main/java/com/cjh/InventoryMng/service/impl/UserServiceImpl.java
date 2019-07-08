package com.cjh.InventoryMng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.mapper.TUserInfoMapper;
import com.cjh.InventoryMng.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private TUserInfoMapper tUserInfoMapper;

	@Override
	public String getUserName(String userId) {
		TUserInfo usr = tUserInfoMapper.selectByPrimaryKey(userId);
		return usr == null ? null : usr.getUserName();
	}

}
