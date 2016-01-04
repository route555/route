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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.biz.dto.PersonDto;
import com.echo.biz.service.PersonService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/person")
public class PersonController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	protected PersonService personService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		String[] codeArray = request.getParameterValues("skillSectCd");
		
		param.put("skillSectCd", codeArray);
		
		log.debug("@@@@@ PersonController start" + param);
		
		

		result.put("totalCnt", personService.selectCnt(param));
		result.put("list", personService.selectList(param));

		model.addAttribute("resultData", result);		
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Model insert(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute PersonDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);
		personService.insert(dto, "getPrsnNo", "setPrsnNo");

		result.put("prsnNo", dto.getPrsnNo());
		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{prsnNo}/modify", method = RequestMethod.POST)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prsnNo") int prsnNo, @ModelAttribute PersonDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setPrsnNo(prsnNo);

		result.put("prsnNo", dto.getPrsnNo());
		result.put("updCnt", personService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{prsnNo}", method = RequestMethod.DELETE)
	public Model delete(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prsnNo") int prsnNo, @ModelAttribute PersonDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		setReqUserIdAndUpdUserId(request, dto);
		
		dto.setPrsnNo(prsnNo);

		result.put("prsnNo", prsnNo);
		result.put("delCnt", personService.deleteDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{prsnNo}", method = RequestMethod.GET)
	public Model selectOne(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prsnNo") int prsnNo, @ModelAttribute PersonDto dto) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		log.debug("@@@@@@@2" + param);
		dto.setPrsnNo(prsnNo);
		result.put("detail", personService.selectDto(dto));
		
		model.addAttribute("resultData", result);		
		return model;
	}
	


}
