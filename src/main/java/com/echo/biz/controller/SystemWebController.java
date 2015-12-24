package com.echo.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/web")
public class SystemWebController extends BaseController {

	@RequestMapping(value = "/system/commonCode", method = RequestMethod.GET)
	public String commonCode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "system/commonCode";
	}
	
	@RequestMapping(value = "/system/user", method = RequestMethod.GET)
	public String user(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "system/user";
	}

	@RequestMapping(value = "/sample/file", method = RequestMethod.GET)
	public String fileSample(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/file";
	}
	
}