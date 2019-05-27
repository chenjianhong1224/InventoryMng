package com.cjh.InventoryMng.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.cjh.InventoryMng.bean.UserInfo;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				// 本次用户登陆账号
				Subject subject = this.getSubject(request, response);
				subject.logout();
			}
		}

		return super.isAccessAllowed(request, response, mappedValue);
	}

	/**
	 * 重写FormAuthenticationFilter的onLoginSuccess方法 指定的url传递进去，这样就实现了跳转到指定的页面；
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.getAndClearSavedRequest(request);// 清理了session中保存的请求信息
		WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
		return false;
	}

}
