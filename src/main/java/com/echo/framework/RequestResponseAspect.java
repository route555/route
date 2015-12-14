package com.echo.framework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.echo.framework.domain.EchoCookie;
import com.echo.framework.dto.BaseDto;
import com.echo.framework.exception.EchoException;
import com.echo.framework.service.AuthService;
import com.echo.framework.service.ValidService;
import com.echo.framework.type.CommonConst;
import com.echo.framework.util.WebUtil;

@Aspect
public class RequestResponseAspect {
	private static Logger log = LoggerFactory
			.getLogger(RequestResponseAspect.class);

	private static Set<String> IGNORE_QUERY_NAME = new HashSet<String>();



	@Autowired
	private ValidService validService;

	@Autowired
	protected MessageSource messageSource;

	static {
		IGNORE_QUERY_NAME.add("passwd");
	}

	@Around("execution(org.springframework.ui.Model com.echo..*.controller..*.*(..))")
	public Object requestResponseProfiling4Model(ProceedingJoinPoint pjp)
			throws Throwable {
		HttpServletRequest request = (HttpServletRequest) getObjectInAspectArgsByType(
				HttpServletRequest.class, pjp);
		BaseDto dto = (BaseDto) getObjectInAspectArgsByType(BaseDto.class, pjp);
		Model model = (Model) getObjectInAspectArgsByType(Model.class, pjp);

		Date start = new Date();
		Object ret = null;

		try {
			if (dto != null) {
				dto.setSessionKey((String) request
						.getAttribute(CommonConst.SESSION_KEY));
			}

			checkValidation(request, dto);

			ret = pjp.proceed();
		}
		catch (Exception e) {
			ret = model.addAttribute("resultData", setErrorStatus(request, e));
		}

		Date end = new Date();
		printLog(request, model, start, end);

		return ret;
	}

	@Around("execution(java.lang.String com.echo..*.controller..*.*(..))")
	public Object requestResponseProfiling4String(ProceedingJoinPoint pjp)
			throws Throwable {
		HttpServletRequest request = (HttpServletRequest) getObjectInAspectArgsByType(
				HttpServletRequest.class, pjp);
		Model model = (Model) getObjectInAspectArgsByType(Model.class, pjp);

		Date start = new Date();
		Object ret = null;

		ret = pjp.proceed();

		Date end = new Date();
		printLog(request, model, start, end);

		return ret;
	}

	@Around("execution(org.springframework.web.servlet.ModelAndView com.echo..*.controller..*.*(..))")
	public Object requestResponseProfiling4ModelAndView(ProceedingJoinPoint pjp)
			throws Throwable {
		HttpServletRequest request = (HttpServletRequest) getObjectInAspectArgsByType(
				HttpServletRequest.class, pjp);
		BaseDto dto = (BaseDto) getObjectInAspectArgsByType(BaseDto.class, pjp);
		Model model = (Model) getObjectInAspectArgsByType(Model.class, pjp);

		Date start = new Date();
		Object ret = null;

		try {
			checkValidation(request, dto);

			ret = pjp.proceed();
		}
		catch (Exception e) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("resultData", setErrorStatus(request, e));

			ret = mav;
		}

		Date end = new Date();
		printLog(request, model, start, end);

		return ret;
	}

	private void checkValidation(HttpServletRequest request, BaseDto dto)
			throws Exception {
		if ((dto != null)
				&& (request.getHeader(AuthService.X_ECHO_DEVICEID) == null)) {
			dto.validate(request.getMethod());
		}

		validService.checkDBRelation(request);
	}

	private Object getObjectInAspectArgsByType(Class<?> clazz,
			ProceedingJoinPoint pjp) {
		Object[] args = pjp.getArgs();
		for (int i = 0; i < args.length; i++) {
			if (clazz.isInstance(args[i])) {
				return args[i];
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private void printLog(HttpServletRequest request, Model model, Date start,
			Date end) {
		StringBuffer buff = new StringBuffer();
		Object resultData = model != null ? model.asMap().get("resultData")
				: "null";
		long elapsedMsec = end.getTime() - start.getTime();

		buff.append("sessionKey=").append(
				request.getAttribute(CommonConst.SESSION_KEY));
		buff.append(", ").append(request.getMethod());
		buff.append(getHeaderString(request));
		buff.append(", url=").append(request.getRequestURL()).append("?")
				.append(getQueryString(request));

		boolean isErrorLog = false;

		if (resultData instanceof Map) {
			Map r = (Map) resultData;

			if ((r.get("list") != null) || (r.get("detail") != null)) {
				buff.append(", resultData=list(or detail)");
			}
			else {
				buff.append(", resultData=").append(resultData);
			}

			if ((r.get("status") != null)
					&& (((Integer) r.get("status")) != HttpServletResponse.SC_OK)) {
				isErrorLog = true;
			}
		}
		else {
			buff.append(", resultData=").append(resultData);
		}

		Object echoCookie = request.getAttribute(CommonConst.COOKIE_KEY);
		if ((echoCookie != null) && (echoCookie instanceof EchoCookie)) {
			buff.append(", Cookie=(").append(
					((EchoCookie) echoCookie).toString4Log()).append(")");
		}

		buff.append(", elapsedTime(sec)=").append((elapsedMsec / 1000.0F));

		if (isErrorLog == true) {
			log.error("{}", buff.toString().replaceAll("\n", " "));
		}
		else {
			log.info("{}", buff);
		}
	}

	public String getHeaderString(HttpServletRequest request) {
		StringBuffer buff = new StringBuffer();

		if (request.getHeader(AuthService.X_ECHO_DEVICEID) != null) {
			buff.append(", HEADER(").append(AuthService.X_ECHO_DEVICEID)
					.append("=").append(request.getAttribute("deviceId"))
					.append(")");
		}

		return buff.toString();
	}

	public StringBuffer getQueryString(HttpServletRequest request) {
		@SuppressWarnings({ "unchecked" })
		List<String> names = Collections.list(request.getParameterNames());
		StringBuffer queryString = new StringBuffer();

		String[] values = null;
		for (String name : names) {
			values = request.getParameterValues(name);
			try {
				queryString.append(getNameValue(name, values));
			}
			catch (UnsupportedEncodingException e) {
				log.error("error", e);
			}
		}

		return queryString;
	}

	public static String getNameValue(String name, String[] values)
			throws UnsupportedEncodingException {
		StringBuffer nameValue = new StringBuffer();
		nameValue.append(name).append("=");

		for (int i = 0; i < values.length; i++) {
			if (i > 0) {
				nameValue.append(",");
			}

			if (IGNORE_QUERY_NAME.contains(name) == false) {
				nameValue.append(URLEncoder.encode(values[i], "UTF-8"));
			}
			else if (StringUtils.isEmpty(values[i]) == true) {
				/* nothing todo */
			}
			else {
				nameValue.append(values[i].substring(0, 5)).append("*****")
						.append(values[i].length());
			}
		}

		nameValue.append("&");

		return nameValue.toString();
	}

	public Map<String, Object> setErrorStatus(HttpServletRequest req,
			Exception e) {
		StringBuffer buff = new StringBuffer();
		Map<String, Object> result = new HashMap<String, Object>();

		int status = -1;
		Object[] params = null;
		String msgId = null;
		String msg = null;
		String msgExInfo = null;

		if (e instanceof EchoException) {
			status = ((EchoException) e).getCode();
			params = ((EchoException) e).getMsgSrcParams();
			msgId = String.valueOf(params[0]);
		}
		else if (e instanceof IllegalArgumentException) {
			status = HttpServletResponse.SC_BAD_REQUEST;
			params = new Object[] { "error.system.unknown" };
			msgId = String.valueOf(params[0]);
			msgExInfo = e.getMessage();
		}
		else {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			params = new Object[] { "error.system.unknown" };
			msgId = String.valueOf(params[0]);
			msgExInfo = e.getMessage();
		}

		msg = messageSource.getMessage(msgId, params, WebUtil.getLocale(req
				.getHeader("Accept-Language")));

		result.put("status", status);
		result.put("msg", msg);

		buff.append("sessionKey=")
				.append(req.getAttribute(CommonConst.SESSION_KEY));
		buff.append(", status=").append(status);
		if (msgId != null) {
			buff.append(", msgId=").append(msgId);
		}
		buff.append(", msg=").append(msg.replaceAll("\n", " "));
		if (msgExInfo != null) {
			buff.append(", msgExInfo=").append(msgExInfo);
		}

		log.error("{}", buff.toString(), e);

		return result;
	}
}