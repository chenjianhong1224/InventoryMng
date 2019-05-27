package com.cjh.InventoryMng.shiro;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.bean.MemberBean;
import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TMemberInfoExample;
import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TResourceInfoExample;
import com.cjh.InventoryMng.entity.TResourceRole;
import com.cjh.InventoryMng.entity.TResourceRoleExample;
import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.entity.TUserRole;
import com.cjh.InventoryMng.entity.TUserRoleExample;
import com.cjh.InventoryMng.mapper.TMemberInfoMapper;
import com.cjh.InventoryMng.mapper.TResourceInfoMapper;
import com.cjh.InventoryMng.mapper.TResourceRoleMapper;
import com.cjh.InventoryMng.mapper.TUserInfoMapper;
import com.cjh.InventoryMng.mapper.TUserRoleMapper;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private TUserInfoMapper tUserInfoMapper;

	@Autowired
	private TUserRoleMapper tUserRoleMapper;

	@Autowired
	private TResourceRoleMapper tResourceRoleMapper;

	@Autowired
	private TResourceInfoMapper tResourceInfoMapper;

	@Autowired
	private TMemberInfoMapper tMemberInfoMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
		List<TResourceInfo> tResourceInfoList = userInfo.gettResourceInfoList();
		if (!CollectionUtils.isEmpty(tResourceInfoList)) {
			for (TResourceInfo tResourceInfo : tResourceInfoList) {
				String perms = tResourceInfo.getPerms();
				if (!StringUtils.isEmpty(perms)) {
					String[] array = perms.split(",");
					if (array != null && array.length > 0) {
						for (String t : array) {
							authorizationInfo.addStringPermission(t);
						}
					}
				}
			}
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userId = (String) token.getPrincipal();
		UserInfo userInfo = getUserInfo(userId);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo,
				userInfo.gettUserInfo().getPassword(), ByteSource.Util.bytes(
						userInfo.gettUserInfo().getSalt() == null ? "" : userInfo.gettUserInfo().getSalt()),
				getName());
		return authenticationInfo;
	}

	private UserInfo getUserInfo(String userId) {
		UserInfo userInfo = new UserInfo();
		TUserInfo tUserInfo = tUserInfoMapper.selectByPrimaryKey(userId);
		if (tUserInfo == null) {
			return null;
		}
		userInfo.settUserInfo(tUserInfo);
		if (tUserInfo.getUserType() == 1) {
			MemberBean userBean = new MemberBean();
			TMemberInfoExample example = new TMemberInfoExample();
			example.createCriteria().andUserIdEqualTo(tUserInfo.getUserId());
			Page<TMemberInfo> memberList = tMemberInfoMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(memberList)) {
				userBean.setMemberId(memberList.get(0).getId());
				userBean.setBrandId(memberList.get(0).getBrand());
				userInfo.setMemberBean(userBean);
			}
		}
		TUserRoleExample example = new TUserRoleExample();
		com.cjh.InventoryMng.entity.TUserRoleExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andUserIdEqualTo(userId);
		List<TUserRole> tUserRoleList = tUserRoleMapper.selectByExample(example);
		List<TResourceInfo> all = Lists.newArrayList();
		for (TUserRole tUserRole : tUserRoleList) {
			TResourceRoleExample resourceRoleExample = new TResourceRoleExample();
			com.cjh.InventoryMng.entity.TResourceRoleExample.Criteria resourceRoleCriteria = resourceRoleExample
					.createCriteria();
			resourceRoleCriteria.andStatusEqualTo(1);
			resourceRoleCriteria.andRoleIdEqualTo(tUserRole.getRoleId());
			List<TResourceRole> tResourceRoleList = tResourceRoleMapper.selectByExample(resourceRoleExample);
			for (TResourceRole tResourceRole : tResourceRoleList) {
				TResourceInfoExample tResourceInfoExample = new TResourceInfoExample();
				com.cjh.InventoryMng.entity.TResourceInfoExample.Criteria resourceInfoCriteria = tResourceInfoExample
						.createCriteria();
				resourceInfoCriteria.andIdEqualTo(tResourceRole.getResourceId());
				List<TResourceInfo> tResourceInfoList = tResourceInfoMapper.selectByExample(tResourceInfoExample);
				all.addAll(tResourceInfoList);
			}
		}
		userInfo.settResourceInfoList(all);
		return userInfo;
	}
}
