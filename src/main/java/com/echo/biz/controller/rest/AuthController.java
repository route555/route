package com.echo.biz.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Model login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Model logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		model.addAttribute("resultData", result);
		return model;
	}

	@RequestMapping(value = "/logoutWithRedirect", method = RequestMethod.GET)
	public String logoutWithRedirect(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String redirect = request.getParameter("redirect");

		if (StringUtils.isEmpty(redirect) == true) {
			redirect = "/api/web/index";
		}

		return "redirect:" + redirect;
	}
}