package com.echo.biz.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.biz.service.GroupCodeService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/codes")
public class GroupCodeController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(GroupCodeController.class);

	@Autowired
	protected GroupCodeService groupCodeService;

	@RequestMapping(value = "/{grpCd}", method = RequestMethod.GET)
	public Model selectListCommonCode(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("grpCd") String grpCd) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("list", groupCodeService.selectListCommonCode(grpCd));

		model.addAttribute("resultData", result);

		return model;
	}

}
