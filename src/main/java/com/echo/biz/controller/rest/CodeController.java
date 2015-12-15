package com.echo.biz.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.framework.controller.BaseController;
import com.echo.framework.dto.CodeDto;
import com.echo.framework.service.CodeService;

@Controller
@RequestMapping(value = "/codesold")
public class CodeController extends BaseController {
	@Autowired
	@Qualifier("CodeService")
	protected CodeService codeService;

	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("totalCnt", codeService.selectCnt(param));
		result.put("list", codeService.selectList(param));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Model insert(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute CodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		setReqUserIdAndUpdUserId(request, dto);

		codeService.insert(dto, "getCodeId", "setCodeId");

		result.put("codeId", dto.getCodeId());
		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{codeId}", method = RequestMethod.PUT)
	public Model update(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable("codeId") Integer codeId, @ModelAttribute CodeDto dto)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		setReqUserIdAndUpdUserId(request, dto);

		dto.setCodeId(codeId);

		result.put("codeId", codeId);
		result.put("updCnt", codeService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}
}