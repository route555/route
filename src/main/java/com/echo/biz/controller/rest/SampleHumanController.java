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

import com.echo.biz.dto.SampleHumanDto;
import com.echo.biz.service.SampleHumanService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/sampleHuman")
public class SampleHumanController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(SampleHumanController.class);

	@Autowired
	protected SampleHumanService sampleHumanService;

	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		log.debug("@@@@@001 SampleHumanController start");

		result.put("totalCnt", sampleHumanService.selectCnt(param));
		result.put("list", sampleHumanService.selectList(param));

		model.addAttribute("resultData", result);
		log.debug("@@@@@009 SampleHumanController end");
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Model insert(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute SampleHumanDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		sampleHumanService.insert(dto, "getHumanId", "setHumanId");

		result.put("humanId", dto.getHumanId());

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{humanId}", method = RequestMethod.PUT)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("humanId") Integer humanId, @ModelAttribute SampleHumanDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setHumanId(humanId);

		result.put("humanId", humanId);
		result.put("updCnt", sampleHumanService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{humanId}", method = RequestMethod.DELETE)
	public Model delete(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("humanId") Integer humanId, @ModelAttribute SampleHumanDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setHumanId(humanId);

		result.put("humanId", humanId);
		result.put("delCnt", sampleHumanService.deleteDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{humanId}", method = RequestMethod.GET)
	public Model selectOne(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("humanId") Integer humanId, @ModelAttribute SampleHumanDto dto) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("@@@@@001 selectOne start" + humanId);
		dto.setHumanId(humanId);
		result.put("detail", sampleHumanService.selectDto(dto));
		
		model.addAttribute("resultData", result);
		log.debug("@@@@@001 selectOne end" + result);
		return model;
	}

}