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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.biz.service.DashBoardService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/dashBoard")
public class DashBoardController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(DashBoardController.class);

	@Autowired
	protected DashBoardService dashBoardService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("prjt", dashBoardService.selectPrjt());
		result.put("saleCont", dashBoardService.selectSaleCont());
		result.put("purchaseCont", dashBoardService.selectPurchaseCont());
		result.put("saleMgt", dashBoardService.selectSaleMgt());
		result.put("purchaseMgt", dashBoardService.selectPurchaseMgt());
		

		model.addAttribute("resultData", result);		
		return model;
	}

	


}
