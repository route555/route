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

import com.echo.biz.dto.GroupCodeDto;
import com.echo.biz.service.GroupCodeService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/groupCode")
public class GroupCodeController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(GroupCodeController.class);

	@Autowired
	protected GroupCodeService groupCodeService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		log.debug("@@@@@ GroupCodeController start");

		result.put("totalCnt", groupCodeService.selectCnt(param));
		result.put("list", groupCodeService.selectList(param));

		model.addAttribute("resultData", result);		
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Model insert(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute GroupCodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);
		groupCodeService.insert(dto, "getGrpCd", "setGrpCd");

		result.put("grpCd", dto.getGrpCd());
		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{grpCd}", method = RequestMethod.PUT)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("grpCd") String grpCd, @ModelAttribute GroupCodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setGrpCd(grpCd);

		result.put("grpCd", grpCd);
		result.put("updCnt", groupCodeService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{grpCd}", method = RequestMethod.DELETE)
	public Model delete(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("grpCd") String grpCd, @ModelAttribute GroupCodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setGrpCd(grpCd);

		result.put("grpCd", grpCd);
		result.put("delCnt", groupCodeService.deleteDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{grpCd}", method = RequestMethod.GET)
	public Model selectOne(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("grpCd") String grpCd, @ModelAttribute GroupCodeDto dto) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		dto.setGrpCd(grpCd);
		result.put("detail", groupCodeService.selectDto(dto));
		
		model.addAttribute("resultData", result);		
		return model;
	}
	


}
