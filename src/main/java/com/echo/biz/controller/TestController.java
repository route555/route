package com.echo.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.framework.controller.BaseController;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.type.CommonConst;

@Controller
@RequestMapping(value = "/web/test")
public class TestController extends BaseController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		EchoCookie signageCookie = (EchoCookie) request.getAttribute(CommonConst.COOKIE_KEY);

		if (signageCookie != null) {
			//return "redirect:/web/index";
		} else {
			//return "test/web/index";
		}
		
		return "test";
	}
}