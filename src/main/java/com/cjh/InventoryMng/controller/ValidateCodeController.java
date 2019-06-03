package com.cjh.InventoryMng.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
@RequestMapping(value = "/validateCode")
public class ValidateCodeController {

	@Autowired
	DefaultKaptcha defaultKaptcha;

	@RequestMapping(value = "/getCode")
	public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] verByte = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到session中
			String createText = defaultKaptcha.createText();
			// request.getSession().setAttribute("validateCode", createText);
			Subject subject = SecurityUtils.getSubject();
			subject.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = defaultKaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		verByte = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(verByte);
		responseOutputStream.flush();
		responseOutputStream.close();
		System.out.println("Session 验证码是：" + request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
	}

}
