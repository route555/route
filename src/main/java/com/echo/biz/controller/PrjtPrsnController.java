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

import com.echo.biz.service.PrjtMgtService;
import com.echo.biz.service.PrjtPrsnService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/prjtPrsn")
public class PrjtPrsnController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(PrjtPrsnController.class);

	@Autowired
	protected PrjtPrsnService prjtPrsnService;
	
	@Autowired
	protected PrjtMgtService prjtMgtService;
	
	
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
	
	@RequestMapping(value = "/selectPrjtPrsnList", method = RequestMethod.GET)
	public Model selectPrjtPrsnList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		int totalCnt = 0;
		@SuppressWarnings("rawtypes")
		List prjtPrsnList = prjtPrsnService.selectPrjtPrsnList(param);
		if(prjtPrsnList != null)
			totalCnt = prjtPrsnList.size();
		
		log.debug("@@@@@001 PrjtPrsnController start totalCnt:"+totalCnt);
		
		result.put("totalCnt", totalCnt);
		result.put("list", prjtPrsnList);

		model.addAttribute("resultData", result);
		
		return model;
	}
	
	@RequestMapping(value = "/selectSalesTotAmt", method = RequestMethod.POST)
	public Model selectSalesTotAmt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("detail", prjtPrsnService.selectSalesTotAmt(param));
		
		
		model.addAttribute("resultData", result);
		
		return model;
	}
	
	
	
	@RequestMapping(value = "/insertPrjtPrsn", method = RequestMethod.POST)
	public Model insertPrjtPrsn(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);

		String[] prsnNo = request.getParameterValues("prsnNo");
		String[] dstrbtSectCd = request.getParameterValues("dstrbtSectCd");
		String[] prsnNm = request.getParameterValues("prsnNm");
		String[] cntrctSectCdNm = request.getParameterValues("cntrctSectCdNm");
		String[] skillSectCd = request.getParameterValues("skillSectCd");
		String[] workStartDt = request.getParameterValues("_workStartDt");
		String[] workEndDt = request.getParameterValues("_workEndDt");
		
		String[] prsnMm = request.getParameterValues("prsnMm");
		String[] salesUnitCostAmt = request.getParameterValues("salesUnitCostAmt");
		String[] ordrUnitCstAmt = request.getParameterValues("ordrUnitCstAmt");
		String[] memoDesc = request.getParameterValues("_memoDesc");
		
		
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(dstrbtSectCd != null) {
			for(int i=0; i<dstrbtSectCd.length; i++) {
				Map<String, Object> params2 = new HashMap<String, Object>();
				//params2.put("dstrbtSeqNo", dstrbtSeqNo[i]);
				params2.put("prsnNo", prsnNo[i]);
				params2.put("dstrbtSectCd", dstrbtSectCd[i]);
				params2.put("prsnNm", prsnNm[i]);
				params2.put("cntrctSectCdNm", cntrctSectCdNm[i]);
				params2.put("skillSectCd", skillSectCd[i]);
				params2.put("workStartDt", workStartDt[i]);
				params2.put("workEndDt", workEndDt[i]);
				
				params2.put("prsnMm", prsnMm[i]);
				params2.put("salesUnitCostAmt", salesUnitCostAmt[i]);
				params2.put("ordrUnitCstAmt", ordrUnitCstAmt[i]);
				params2.put("memoDesc", memoDesc[i]);
				
				list.add(params2);
				
				log.debug("============>dstrbtSectCd:"+dstrbtSectCd[i]);
			}
		}
		
		//프로젝트 배정인력 등록
		prjtPrsnService.insertPrjtPrsn(params, list);

		

		return model;
	}
	

}
