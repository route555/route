package com.echo.biz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.biz.dto.SampleHumanDto;
import com.echo.biz.dto.TrAcctMgtDto;
import com.echo.biz.service.TrAcctMgtService;
import com.echo.framework.controller.BaseController;
import com.echo.framework.service.CodeService;

@Controller
@RequestMapping(value = "/trAcctMgt")
public class TrAcctMgtController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(TrAcctMgtController.class);

	@Autowired
	protected TrAcctMgtService trAcctMgtService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Model selectList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();

		log.debug("@@@@@001 TrAcctMgtController start");
		
		log.debug("================>거래처명:"+param);

		result.put("totalCnt", trAcctMgtService.selectCnt(param));
		result.put("list", trAcctMgtService.selectList(param));

		model.addAttribute("resultData", result);
		log.debug("@@@@@009 TrAcctMgtController end");
		return model;
	}
	
	@RequestMapping(value = "/{trAcctCd}", method = RequestMethod.GET)
	public Model selectTrAcctMgt(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("trAcctCd") String trAcctCd) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		param.put("trAcctCd", trAcctCd);
		
		log.debug("@@@@@001 selectOne start" + param.get("trAcctCd"));
		
		result.put("detail", trAcctMgtService.selectTrAcctMgt(param));
		
		model.addAttribute("resultData", result);
		
		log.debug("@@@@@001 selectOne end" + result);
		return model;
	}
	
	@RequestMapping(value = "/selectTrAcctChgrList", method = RequestMethod.GET)
	public Model selectTracctChgrList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> param = this.getParameterMap(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		log.debug("@@@@@001 TrAcctMgtController start");
		
		int totalCnt = 0;
		@SuppressWarnings("rawtypes")
		List trAcctChgrList = trAcctMgtService.selectTracctChgrList(param);
		if(trAcctChgrList != null)
			totalCnt = trAcctChgrList.size();
		
		log.debug("@@@@@001 TrAcctMgtController start totalCnt:"+totalCnt);
		
		result.put("totalCnt", totalCnt);
		result.put("list", trAcctMgtService.selectTracctChgrList(param));
		
		//담당자 구분코드 조회
		int totalTracctChgrCdCnt = 0;
		param.put("grpCd", "001");
		
		@SuppressWarnings("rawtypes")
		List trAcctChgrCdList = trAcctMgtService.selectTracctChgrCdList(param);
		if(trAcctChgrCdList != null)
			totalTracctChgrCdCnt = trAcctChgrCdList.size();
		
		result.put("totalTracctChgrCdCnt", totalTracctChgrCdCnt);
		result.put("trAcctChgrCdList", trAcctChgrCdList);

		
		model.addAttribute("resultData", result);
		
		log.debug("@@@@@009 TrAcctMgtController end");
		
		return model;
	}
	
	@RequestMapping(value = "/insertTrAcct", method = RequestMethod.POST)
	public Model insertTrAcct(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);

		String[] chgSectCd = request.getParameterValues("chgSectCd");
		String[] chgrNm = request.getParameterValues("chgrNm");
		String[] pstnNm = request.getParameterValues("pstnNm");
		String[] telNo = request.getParameterValues("telNo");
		String[] hpNo = request.getParameterValues("hpNo");
		String[] emailAddr = request.getParameterValues("emailAddr");
		String[] addr = request.getParameterValues("addr");
		String[] memoDesc = request.getParameterValues("memoDesc");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(chgSectCd != null) {
			for(int i=0; i<chgSectCd.length; i++) {
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("chgSectCd", chgSectCd[i]);
				params2.put("chgrNm", chgrNm[i]);
				params2.put("pstnNm", pstnNm[i]);
				params2.put("telNo", telNo[i]);
				params2.put("hpNo", hpNo[i]);
				params2.put("addr", addr[i]);
				params2.put("emailAddr", emailAddr[i]);
				params2.put("memoDesc", memoDesc[i]);
				list.add(params2);
				
				log.debug("============>chgrSectCd:"+chgSectCd[i]);
			}
		}
		
		//거래처 및 담당자 목록 등록
		trAcctMgtService.insertTrAcct(params, list);

		

		return model;
	}
	
	@RequestMapping(value = "/{trAcctCd}", method = RequestMethod.PUT)
	public Model updateTrAcct(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("trAcctCd") String trAcctCd) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		params.put("trAcctCd", trAcctCd);

		String[] chgSectCd = request.getParameterValues("chgSectCd");
		String[] chgrNm = request.getParameterValues("chgrNm");
		String[] pstnNm = request.getParameterValues("pstnNm");
		String[] telNo = request.getParameterValues("telNo");
		String[] hpNo = request.getParameterValues("hpNo");
		String[] emailAddr = request.getParameterValues("emailAddr");
		String[] addr = request.getParameterValues("addr");
		String[] memoDesc = request.getParameterValues("memoDesc");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(chgSectCd != null) {
			for(int i=0; i<chgSectCd.length; i++) {
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("chgSectCd", chgSectCd[i]);
				params2.put("chgrNm", chgrNm[i]);
				params2.put("pstnNm", pstnNm[i]);
				params2.put("telNo", telNo[i]);
				params2.put("hpNo", hpNo[i]);
				params2.put("addr", addr[i]);
				params2.put("emailAddr", emailAddr[i]);
				params2.put("memoDesc", memoDesc[i]);
				list.add(params2);
				
				log.debug("============>chgrSectCd:"+chgSectCd[i]);
			}
		}
		
		//거래처 및 담당자 목록 수정
		trAcctMgtService.updateTrAcct(params, list);

		

		return model;
	}
	
	@RequestMapping(value = "/{trAcctCd}", method = RequestMethod.DELETE)
	public Model deleteTrAcct(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("trAcctCd") String trAcctCd) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		params.put("trAcctCd", trAcctCd);
		
		//거래처 및 담당자 목록 수정
		trAcctMgtService.deleteTrAcct(params);
		
		return model;
	}
	
}
