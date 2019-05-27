package com.cjh.InventoryMng.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@EnableAutoConfiguration
public class PageController {

	@RequestMapping(value = "/pages/{page}")
	public String index(@PathVariable String page, HttpSession httpSession, Model model) {
		return "pages/" + page;
	}

	@RequestMapping(value = "/pages/{level1}/{page}")
	public String index1(@PathVariable String level1, @PathVariable String page, HttpSession httpSession, Model model) {
		return "pages/" + level1 + "/" + page;
	}
}
