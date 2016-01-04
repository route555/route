package com.echo.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.biz.service.PrjtMgtService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/prjtMgt")
public class PrjtMgtController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(PrjtMgtController.class);

	@Autowired
	protected PrjtMgtService prjtMgtService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		log.debug("@@@@@001 PrjtMgtController start");
		
		log.debug("================>프로젝트명:"+param);

		result.put("totalCnt", prjtMgtService.selectCnt(param));
		result.put("list", prjtMgtService.selectList(param));

		model.addAttribute("resultData", result);
		log.debug("@@@@@009 PrjtMgtController end");
		return model;
	}
	
	@RequestMapping(value = "/{prjtCd}", method = RequestMethod.GET)
	public Model selectPrjtMgt(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prjtCd") String prjtCd) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		param.put("prjtCd", prjtCd);
		
		log.debug("@@@@@001 selectOne start" + param.get("prjtCd"));
		
		result.put("detail", prjtMgtService.selectPrjtMgt(param));
		
		
		result.put("detail2", prjtMgtService.selectPrjtChgrList1(param));
		
		result.put("detail3", prjtMgtService.selectPrjtChgrList2(param));
		
		model.addAttribute("resultData", result);
		
		log.debug("@@@@@001 selectOne end" + result);
		return model;
	}
	
	@RequestMapping(value = "/insertPrjt", method = RequestMethod.POST)
	public Model insertPrjt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		//프로젝트 등록
		prjtMgtService.insertPrjt(params);

				
		return model;
	}
	
	@RequestMapping(value = "/{prjtCd}", method = RequestMethod.PUT)
	public Model updateTrAcct(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prjtCd") String prjtCd) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		params.put("prjtCd", prjtCd);

		//프로젝트 수정
		prjtMgtService.updatePrjt(params);

				

		return model;
	}
	
	@RequestMapping(value = "/{prjtCd}", method = RequestMethod.DELETE)
	public Model deletePrjt(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prjtCd") String prjtCd) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		params.put("prjtCd", prjtCd);
		
		//프로젝트 삭제
		prjtMgtService.deletePrjt(params);
		
		return model;
	}
	
	@RequestMapping(value = "/updatePrjtCfrm", method = RequestMethod.POST)
	public Model updatePrjtCfrm(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		//프로젝트 확정 등록
		prjtMgtService.updatePrjtCfrm(params);

				
		return model;
	}
	
	@RequestMapping(value = "/updatePrjtCfrmCancel", method = RequestMethod.POST)
	public Model updatePrjtCfrmCancel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		//프로젝트 확정취소 등록
		prjtMgtService.updatePrjtCfrmCancel(params);

				
		return model;
	}
	
	@RequestMapping(value = "/updatePrjtEnd", method = RequestMethod.POST)
	public Model updatePrjtEnd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		//프로젝트 종료 등록
		prjtMgtService.updatePrjtEnd(params);

				
		return model;
	}
	
	@RequestMapping(value = "/updatePrjtEndCancel", method = RequestMethod.POST)
	public Model updatePrjtEndCancel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		//프로젝트 종료취소 등록
		prjtMgtService.updatePrjtEndCancel(params);

				
		return model;
	}
}
