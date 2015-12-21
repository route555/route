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

import com.echo.biz.dto.UserDto;
import com.echo.biz.service.UserService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	protected UserService userService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		log.debug("@@@@@ UserController start");

		result.put("totalCnt", userService.selectCnt(param));
		result.put("list", userService.selectList(param));

		model.addAttribute("resultData", result);		
		return model;
	}

	@RequestMapping( method = RequestMethod.POST)
	public Model insert(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute UserDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("@@@@@ UserController insert start");
		setReqUserIdAndUpdUserId(request, dto);
		userService.insert(dto, "getUserId", "setUserId");

		result.put("userId", dto.getUserId());
		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.POST)
	public Model update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("userId") int userId, @ModelAttribute UserDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setUserId(userId);

		result.put("userId", userId);
		result.put("updCnt", userService.updateDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public Model delete(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("userId") int userId, @ModelAttribute UserDto dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		setReqUserIdAndUpdUserId(request, dto);

		dto.setUserId(userId);

		result.put("userId", userId);
		result.put("delCnt", userService.deleteDto(dto));

		model.addAttribute("resultData", result);

		return model;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public Model selectOne(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("userId") int userId, @ModelAttribute UserDto dto) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		dto.setUserId(userId);
		result.put("detail", userService.selectDto(dto));
		
		model.addAttribute("resultData", result);		
		return model;
	}
	


}
