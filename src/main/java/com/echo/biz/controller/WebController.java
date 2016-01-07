package com.echo.biz.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.echo.framework.controller.BaseController;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.type.CommonConst;
import com.echo.framework.type.RightsType;
import com.echo.framework.util.StringUtils;

@Controller
@RequestMapping(value = "/web")
public class WebController extends BaseController {

	
	/**
	 * 거래처관리 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tracctmgt/tracctmgt", method = RequestMethod.GET)
	public String tracctmgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "tracctmgt/tracctmgt";
	}
	
	/**
	 * 프로젝트관리 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prjtmgt/prjtmgt", method = RequestMethod.GET)
	public String prsnmgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "prjtmgt/prjtmgt";
	}
	
	
	/**
	 * 거래처팝업 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tracctmgt/tracctpop", method = RequestMethod.GET)
	public String tracctpop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		String srchType = String.valueOf(params.get("srchType"));
		String trAcctCd = String.valueOf(params.get("trAcctCd"));
		String srchChgSectCd = String.valueOf(params.get("srchChgSectCd"));

		
		model.addAttribute("srchType", srchType);
		model.addAttribute("trAcctCd", trAcctCd);
		model.addAttribute("srchChgSectCd", srchChgSectCd);
		
		log.debug("============>검색구분:"+srchType);
		log.debug("============>거래처코드:"+trAcctCd);
		log.debug("============>담당자구분:"+srchChgSectCd);
		
		return "tracctmgt/tracctpop";
	}
	
	/**
	 * 프로젝트인력배정관리 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prjtprsnmgt/prjtprsnmgt", method = RequestMethod.GET)
	public String prjtprsnmgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		String prjtCd = String.valueOf(params.get("prjtCd"));
		String trAcctCd = String.valueOf(params.get("trAcctCd"));
		
		model.addAttribute("prjtCd", prjtCd);
		model.addAttribute("trAcctCd", trAcctCd);
		
		log.debug("============>프로젝트코드:"+prjtCd);
		log.debug("============>거래처코드:"+trAcctCd);
		
		return "prjtprsnmgt/prjtprsnmgt";
	}
	
	/**
	 * 프로젝트팝업 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prjtmgt/prjtpop", method = RequestMethod.GET)
	public String prjtpop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> params = this.getParameterMap(request);
		
		String prjtCd = String.valueOf(params.get("prjtCd"));
		String trAcctCd = String.valueOf(params.get("trAcctCd"));
		
		model.addAttribute("prjtCd", prjtCd);
		model.addAttribute("trAcctCd", trAcctCd);

		log.debug("============>프로젝트코드:"+prjtCd);
		log.debug("============>거래처코드:"+trAcctCd);

		
		return "prjtmgt/prjtpop";
	}
	
	/**
	 * 매출계약관리 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cntrctmgt/salescntrctmgt", method = RequestMethod.GET)
	public String salescntrctmgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "cntrctmgt/salescntrctmgt";
	}
	
	/**
	 * 매입계약관리 화면 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cntrctmgt/ordercntrctmgt", method = RequestMethod.GET)
	public String ordercntrctmgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "cntrctmgt/ordercntrctmgt";
	}

	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		EchoCookie echoCookie = (EchoCookie) request.getAttribute(CommonConst.COOKIE_KEY);
System.out.println("###############################");
		if (echoCookie != null) {
			return main(request, response, model);
		}
		else {
			return "index/index";
		}
	}
		
	@RequestMapping(value = "board/bugReport", method = RequestMethod.GET)
	public String bugReport(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "board/bugReport";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "index/main";
	}
	
	@RequestMapping(value = "/person/person", method = RequestMethod.GET)
	public String person(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "person/person";
	}
	
	
	@RequestMapping(value = "/person/personPop", method = RequestMethod.GET)
	public String personPop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "person/personPop";
	}
	
	@RequestMapping(value = "/finance/saleMgt", method = RequestMethod.GET)
	public String saleMgt(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "/finance/saleMgt";
	}
	
	@RequestMapping(value = "/sample/blank", method = RequestMethod.GET)
	public String blank(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/blank";
	}

	@RequestMapping(value = "/sample/buttons", method = RequestMethod.GET)
	public String buttons(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/buttons";
	}
	
	@RequestMapping(value = "/sample/flot", method = RequestMethod.GET)
	public String flot(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/flot";
	}
	
	@RequestMapping(value = "/sample/forms", method = RequestMethod.GET)
	public String forms(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/forms";
	}
	
	@RequestMapping(value = "/sample/grid", method = RequestMethod.GET)
	public String grid(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/grid";
	}
	
	@RequestMapping(value = "/sample/icons", method = RequestMethod.GET)
	public String icons(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/icons";
	}
	
	@RequestMapping(value = "/sample/morris", method = RequestMethod.GET)
	public String morris(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/morris";
	}
	
	@RequestMapping(value = "/sample/notifications", method = RequestMethod.GET)
	public String notifications(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/notifications";
	}
	
	@RequestMapping(value = "/sample/panels-wells", method = RequestMethod.GET)
	public String panelswells(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/panels-wells";
	}
	
	@RequestMapping(value = "/sample/tables", method = RequestMethod.GET)
	public String table(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/tables";
	}
	
	@RequestMapping(value = "/sample/typography", method = RequestMethod.GET)
	public String typography(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/typography";
	}
		
	@RequestMapping(value = "/sample/human", method = RequestMethod.GET)
	public String human(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "sample/human";
	}
		
	
	@RequestMapping(value = "/error")
	public String error(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "error/exception";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		EchoCookie signageCookie = (EchoCookie) request.getAttribute(CommonConst.COOKIE_KEY);

		if (signageCookie == null) {
			return "redirect:/web/index";
		} else if (RightsType.USER.isEquals(signageCookie.getRightsType()) == true) {
			return "redirect:/web/contents/historyWallList";
		} else if (RightsType.USER.isEquals(signageCookie.getRightsType()) == true) {
			return "redirect:/web/ad/adList";
		}

		return "redirect:/web/ad/adList";
	}

	// popup
	@RequestMapping(value = "/popup/videoPop", method = RequestMethod.GET)
	public String videoPop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("cdnUrl", request.getParameter("cdnUrl"));
		model.addAttribute("contentsType", request.getParameter("contentsType"));
		return "popup/videoPop";
	}

	@RequestMapping(value = "/popup/imagePop", method = RequestMethod.GET)
	public String imageChromePop(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("cdnUrl", request.getParameter("cdnUrl"));
		return "popup/imagePop";
	}

	// 회원
	@RequestMapping(value = "/basic/userList", method = RequestMethod.GET)
	public String userList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "user/userList";
	}

	@RequestMapping(value = "/basic/userAdd", method = RequestMethod.GET)
	public String userAdd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "user/userAdd";
	}

	@RequestMapping(value = "/basic/userDetail", method = RequestMethod.GET)
	public String userDetail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		EchoCookie signageCookie = (EchoCookie) request.getAttribute(CommonConst.COOKIE_KEY);

		String userId = request.getParameter("userId");
		if (StringUtils.isEmpty(userId) == true) {
			model.addAttribute("id", signageCookie.getValue("userId"));
		} else {
			model.addAttribute("id", userId);
		}

		return "user/userDetail";
	}

	@RequestMapping(value = "/basic/userModify", method = RequestMethod.GET)
	public String userModify(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		EchoCookie signageCookie = (EchoCookie) request.getAttribute(CommonConst.COOKIE_KEY);

		String userId = request.getParameter("userId");
		if (StringUtils.isEmpty(userId) == true) {
			model.addAttribute("id", signageCookie.getValue("userId"));
		} else {
			model.addAttribute("id", userId);
		}

		return "user/userModify";
	}

}