package com.cjh.InventoryMng.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.cjh.InventoryMng.bean.UserInfo;
import com.google.code.kaptcha.Constants;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return false;
			}
		}

		return super.isAccessAllowed(request, response, mappedValue);
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 在这里进行验证码的校验
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();

		// 取出验证码
		//String validateCode = (String) session.getAttribute("validateCode");
		Subject subject = SecurityUtils.getSubject();
        String validateCode = (String) subject.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		// 取出页面的验证码
		// 输入的验证和session中的验证进行对比
		String randomcode = httpServletRequest.getParameter("validateCode");
		if (randomcode != null && validateCode != null && !randomcode.equals(validateCode)) {
			// 如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
			httpServletRequest.setAttribute("shiroLoginFailure", "kaptchaValidateFailed");// 自定义登录异常
			// 拒绝访问，不再校验账号和密码
			return true;
		}
		return super.onAccessDenied(request, response);
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
