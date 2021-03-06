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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.biz.dto.PurchaseManagementDto;
import com.echo.biz.service.PurchaseManagementService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/purchaseMgt")
public class PurchaseManagementController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PurchaseManagementController.class);

	@Autowired
	protected PurchaseManagementService purchaseManagementService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("totalCnt", purchaseManagementService.selectCntPurchaseManagement(param));
		result.put("list", purchaseManagementService.selectListPurchaseManagement(param));

		model.addAttribute("resultData", result);		
		return model;
	}

	
	@RequestMapping(method = RequestMethod.PUT)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody PurchaseManagementDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		setReqUserIdAndUpdUserId(request, dto);
		log.debug("@@##" + dto);
		
		result.put("updCnt", purchaseManagementService.updatePurchasePayment(dto));
		model.addAttribute("resultData", result);
		return model;
	}
	

}
