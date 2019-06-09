package com.cjh.InventoryMng.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.service.AuthorizationService;
import com.cjh.InventoryMng.vo.ResultMap;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyPassword(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		String oldPassword = (String) reqMap.get("oldPassword");
		String newPassword = (String) reqMap.get("newPassword");
		if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
			resultMap.setFailed();
			resultMap.setMessage("密码不能为空");
			return resultMap.toMap();
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(newPassword);
		if (m.matches()) {
			resultMap.setFailed().setMessage("新密码不合法，不能包含不可见字符");
			return resultMap.toMap();
		}
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		Md5Hash md5Hash = new Md5Hash(oldPassword, "", 1);
		if (!md5Hash.toString().equals(userInfo.gettUserInfo().getPassword())) {
			resultMap.setFailed().setMessage("密码错误");
			return resultMap.toMap();
		}
		md5Hash = new Md5Hash(newPassword, "", 1);
		if (authorizationService.updatePassword(userInfo.gettUserInfo().getUserId(), md5Hash.toString())) {
			resultMap.setMessage("修改成功");
			UsernamePasswordToken token = new UsernamePasswordToken(userInfo.gettUserInfo().getUserId(), newPassword);
			SecurityUtils.getSubject().login(token);
			userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		} else {
			resultMap.setFailed().setMessage("修改失败");
		}
		return resultMap.toMap();
	}

}
