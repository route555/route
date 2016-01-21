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

import com.echo.biz.dto.MenuDto;
import com.echo.biz.service.MenuService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	protected MenuService menuService;

	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("totalCnt", menuService.selectCnt(param));
		result.put("list", menuService.selectList(param));

		model.addAttribute("resultData", result);
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Model insert(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute MenuDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);
		menuService.insert(dto, "getMenuId", "setMenuId");

		result.put("menuId", dto.getMenuId());
		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{menuId}/modify", method = RequestMethod.POST)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("menuId") int menuId, @ModelAttribute MenuDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setMenuId(menuId);

		result.put("menuId", dto.getMenuId());
		result.put("updCnt", menuService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
	public Model delete(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("menuId") int menuId, @ModelAttribute MenuDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setMenuId(menuId);

		result.put("menuId", menuId);
		result.put("delCnt", menuService.deleteDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
	public Model selectOne(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("menuId") int menuId, @ModelAttribute MenuDto dto) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		log.debug("@@@@@@@2" + param);
		dto.setMenuId(menuId);
		result.put("detail", menuService.selectDto(dto));

		model.addAttribute("resultData", result);
		return model;
	}

}
