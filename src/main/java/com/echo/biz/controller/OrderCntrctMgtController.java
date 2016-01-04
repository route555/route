package com.echo.biz.controller;

import java.util.ArrayList;
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

import com.echo.biz.service.OrderCntrctMgtService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/orderCntrctMgt")
public class OrderCntrctMgtController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(OrderCntrctMgtController.class);

	@Autowired
	protected OrderCntrctMgtService orderCntrctMgtService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		log.debug("@@@@@001 OrderCntrctMgtController start");

		result.put("totalCnt", orderCntrctMgtService.selectCnt(param));
		result.put("list", orderCntrctMgtService.selectList(param));

		model.addAttribute("resultData", result);
		log.debug("@@@@@009 OrderCntrctMgtController end");
		return model;
	}
	
	@RequestMapping(value = "/{prjtCd}", method = RequestMethod.GET)
	public Model selectSalesCntrctMgt(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prjtCd") String prjtCd) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		param.put("prjtCd", prjtCd);
		param.put("cntrctCd", param.get("cntrctCd"));
		
		log.debug("@@@@@001 selectOne start" + prjtCd);
		log.debug("@@@@@001 selectOne start" + param.get("dstrbtSeqNo"));
		
		result.put("detail", orderCntrctMgtService.selectOrderCntrctMgt(param));
		
		/**
		int totalCnt = 0;
		List<Map<String, Object>> list = orderCntrctMgtService.selectOrderDpstList(param);
		if(list != null)
			totalCnt = list.size();
		
		result.put("totalCnt", totalCnt);
		result.put("list", list);
		**/
		
		model.addAttribute("resultData", result);
		
		log.debug("@@@@@001 selectOne end" + result);
		return model;
	}
	
	@RequestMapping(value = "/selectOrderDpstList", method = RequestMethod.POST)
	public Model selectOrderDpstList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		int totalCnt = 0;
		List<Map<String, Object>> list = orderCntrctMgtService.selectOrderDpstList(param);
		if(list != null)
			totalCnt = list.size();
		
		result.put("totalCnt", totalCnt);
		result.put("list", list);
		
		
		model.addAttribute("resultData", result);
		
		log.debug("@@@@@001 selectOne end" + result);
		return model;
	}
	
	@RequestMapping(value = "/insertOrderCntrctMgt", method = RequestMethod.POST)
	public Model insertOrderCntrctMgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		String[] dpstDt = request.getParameterValues("dpstDt");
		String[] dpstCd = request.getParameterValues("dpstCd");
		String[] dpstYn = request.getParameterValues("dpstYn");
		String[] rmlrkDesc = request.getParameterValues("rmlrkDesc");
		String[] supplyAmt = request.getParameterValues("supplyAmt");
		String[] taxAmt = request.getParameterValues("taxAmt");
		String[] totAmt = request.getParameterValues("totAmt");
		String[] memoDesc = request.getParameterValues("memoDesc");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(dpstDt != null) {
			for(int i=0; i<dpstDt.length; i++) {
				Map<String, Object> params2 = new HashMap<String, Object>();
				
				params2.put("dpstDt", dpstDt[i]);
				params2.put("dpstCd", dpstCd[i]);
				params2.put("dpstYn", dpstYn[i]);
				params2.put("rmlrkDesc", rmlrkDesc[i]);
				params2.put("supplyAmt", supplyAmt[i]);
				params2.put("taxAmt", taxAmt[i]);
				params2.put("totAmt", totAmt[i]);
				params2.put("memoDesc", memoDesc[i]);
				
				
				list.add(params2);
				
				log.debug("============>dmndDate:"+dpstDt[i]);
			}
		}
		
		//매입계약정보 등록
		orderCntrctMgtService.insertOrderCntrctMgt(params, list);
		
		
		return model;
	}
}
