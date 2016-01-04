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

import com.echo.biz.service.PrjtPrsnService;
import com.echo.biz.service.SalesCntrctMgtService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/salesCntrctMgt")
public class SalesCntrctMgtController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(SalesCntrctMgtController.class);

	@Autowired
	protected SalesCntrctMgtService salesCntrctMgtService;
	
	@Autowired
	protected PrjtPrsnService prjtPrsnService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		log.debug("@@@@@001 SalesCntrctMgtController start");

		result.put("totalCnt", salesCntrctMgtService.selectCnt(param));
		result.put("list", salesCntrctMgtService.selectList(param));

		model.addAttribute("resultData", result);
		log.debug("@@@@@009 SalesCntrctMgtController end");
		return model;
	}
	
	@RequestMapping(value = "/{prjtCd}", method = RequestMethod.GET)
	public Model selectSalesCntrctMgt(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("prjtCd") String prjtCd) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		param.put("prjtCd", prjtCd);
		param.put("cntrctCd", param.get("cntrctCd"));
		
		log.debug("@@@@@001 selectOne start" + param.get("prjtCd"));
		
		result.put("detail", salesCntrctMgtService.selectSalesCntrctMgt(param));
		
		result.put("detail2", prjtPrsnService.selectSalesTotAmt(param));
		
		int totalCnt = 0;
		List<Map<String, Object>> list = salesCntrctMgtService.selectSalesDmndList(param);
		if(list != null)
			totalCnt = list.size();
		
		result.put("totalCnt", totalCnt);
		result.put("list", list);
		
		
		model.addAttribute("resultData", result);
		
		log.debug("@@@@@001 selectOne end" + result);
		return model;
	}
	
	@RequestMapping(value = "/selectSalesDmndList", method = RequestMethod.GET)
	public Model selectSalesDmndList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		int totalCnt = 0;
		List<Map<String, Object>> list = salesCntrctMgtService.selectSalesDmndList(param);
		if(list != null)
			totalCnt = list.size();
		
		result.put("totalCnt", totalCnt);
		result.put("list", list);
		
		
		model.addAttribute("resultData", result);

		return model;
	}
	
	@RequestMapping(value = "/insertSalesCntrctMgt", method = RequestMethod.POST)
	public Model insertSalesCntrctMgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		String[] dmndDate = request.getParameterValues("dmndDate");
		String[] dmndCd = request.getParameterValues("dmndCd");
		String[] dpstYn = request.getParameterValues("dpstYn");
		String[] rmlrkDesc = request.getParameterValues("rmlrkDesc");
		String[] supplyAmt = request.getParameterValues("supplyAmt");
		String[] taxAmt = request.getParameterValues("taxAmt");
		String[] totAmt = request.getParameterValues("totAmt");
		String[] examCfrmYn = request.getParameterValues("examCfrmYn");
		String[] memoDesc = request.getParameterValues("memoDesc");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(dmndDate != null) {
			for(int i=0; i<dmndDate.length; i++) {
				Map<String, Object> params2 = new HashMap<String, Object>();
				
				params2.put("dmndDate", dmndDate[i]);
				params2.put("dmndCd", dmndCd[i]);
				params2.put("dpstYn", dpstYn[i]);
				params2.put("rmlrkDesc", rmlrkDesc[i]);
				params2.put("supplyAmt", supplyAmt[i]);
				params2.put("taxAmt", taxAmt[i]);
				params2.put("totAmt", totAmt[i]);
				
				if(examCfrmYn != null)
					params2.put("examCfrmYn", examCfrmYn[i]);
				else
					params2.put("examCfrmYn", "N");
				params2.put("memoDesc", memoDesc[i]);
				
				
				list.add(params2);
				
				log.debug("============>dmndDate:"+dmndDate[i]);
			}
		}
		
		//프로젝트 배정인력 등록
		salesCntrctMgtService.insertSalesCntrctMgt(params, list);
		
		
		return model;
	}

}
