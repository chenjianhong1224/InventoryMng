package com.cjh.InventoryMng.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;

	@SuppressWarnings("serial")
	private Map<String, String> ERR_NAME = new HashMap<String, String>() {
		{
			put("400", "您的请求出错，请联系管理员");
			put("404", "页面未找到，请联系管理员");
			put("403", "您暂无此权限");
			put("500", "系统内部出错，请联系管理员");
		}
	};

	/**
	 * 跳转至失败页面
	 */
	@RequestMapping(value = "/code{errCode}")
	public String toFailed(@PathVariable String errCode, HttpServletRequest request, Model model) {
		String errName = ServletRequestUtils.getStringParameter(request, "errName", "");
		String errMessage = ServletRequestUtils.getStringParameter(request, "errMessage", "");
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		Map<String, Object> s = errorAttributes.getErrorAttributes(requestAttributes, true);
		String messageFound = (String) s.get("message");
		if (StringUtils.isEmpty(errMessage)) {
			errMessage = messageFound;
		}
		String url = ServletRequestUtils.getStringParameter(request, "url", "");
		model.addAttribute("errCode", errCode);
		if (errName != null && !"".equals(errName)) {
			model.addAttribute("errName", errName);
		} else {
			model.addAttribute("errName", ERR_NAME.get(errCode));
		}
		if (errMessage != null && !"".equals(errMessage)) {
			model.addAttribute("errMessage", errMessage);
		}
		if (url != null && !"".equals(url)) {
			model.addAttribute("url", url);
		}
		return "error";
	}
}
