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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.echo.biz.dto.SaleManagementDto;
import com.echo.biz.service.SaleManagementService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/saleMgt")
public class SaleManagementController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(SaleManagementController.class);

	@Autowired
	protected SaleManagementService saleManagementService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		/*
		String[] codeArray = request.getParameterValues("skillSectCd");
		param.put("skillSectCd", codeArray);
		log.debug("@@@@@ PersonController start" + param);
		*/
		

		result.put("totalCnt", saleManagementService.selectCntSaleManagement(param));
		result.put("list", saleManagementService.selectListSaleManagement(param));

		model.addAttribute("resultData", result);		
		return model;
	}

	
	@RequestMapping(method = RequestMethod.PUT)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody SaleManagementDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		//'1' AS cntrctCd
		//, '1' AS dmndSeqNo
		//setReqUserIdAndUpdUserId(request, dto);

		log.debug("@@##" + dto);
		//dto.setDtlCd(dtlCd);

		//result.put("dtlCd", dtlCd);
		//result.put("updCnt", detailCodeService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}
	

}
