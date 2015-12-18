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

import com.echo.biz.dto.DetailCodeDto;
import com.echo.biz.service.DetailCodeService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/detailCode")
public class DetailCodeController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(DetailCodeController.class);

	@Autowired
	protected DetailCodeService detailCodeService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		log.debug("@@@@@ DetailCodeController start");

		result.put("totalCnt", detailCodeService.selectCnt(param));
		result.put("list", detailCodeService.selectList(param));

		model.addAttribute("resultData", result);		
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Model insert(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute DetailCodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);
		detailCodeService.insert(dto, "getDtlCd", "setDtlCd");

		result.put("dtlCd", dto.getDtlCd());
		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{dtlCd}", method = RequestMethod.PUT)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("dtlCd") String dtlCd, @ModelAttribute DetailCodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setDtlCd(dtlCd);

		result.put("dtlCd", dtlCd);
		result.put("updCnt", detailCodeService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{grpCd}/{dtlCd}", method = RequestMethod.DELETE)
	public Model delete(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("grpCd") String grpCd, @PathVariable("dtlCd") String dtlCd, @ModelAttribute DetailCodeDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		setReqUserIdAndUpdUserId(request, dto);
		
		dto.setGrpCd(grpCd);
		dto.setDtlCd(dtlCd);

		result.put("dtlCd", dtlCd);
		result.put("delCnt", detailCodeService.deleteDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{dtlCd}", method = RequestMethod.GET)
	public Model selectOne(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("dtlCd") String dtlCd, @ModelAttribute DetailCodeDto dto) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		log.debug("@@@@@@@2" + param);
		dto.setGrpCd((String) param.get("grpCd"));
		dto.setDtlCd(dtlCd);
		result.put("detail", detailCodeService.selectDto(dto));
		
		model.addAttribute("resultData", result);		
		return model;
	}
	


}
