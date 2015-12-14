package com.echo.framework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.echo.framework.domain.EchoCookie;
import com.echo.framework.exception.EchoException;
import com.echo.framework.type.CommonConst;
import com.echo.framework.util.CookieUtil;
import com.echo.framework.util.JsonUtil;
import com.echo.framework.util.PropsUtil;
import com.echo.framework.util.WebUtil;

abstract public class BaseInterceptor implements HandlerInterceptor {
	public static final String MESSAGE_SOURCE = "messageSource";

	protected static Logger log = LoggerFactory
			.getLogger(BaseInterceptor.class);

	protected static Set<String> URI_STATIC_RESOURCE_SUFFIX = new HashSet<String>();
	protected static Set<String> URI_STATIC_RESOURCE_REGEX = new HashSet<String>();

	private static String CONTEXT_WEB_PREFIX = "web";
	private static String INDEX = "/web/index";
	private static String REGEX_LOGOUT = ".*logout.*";

	@Autowired
	protected MessageSource messageSource;

	static {
		URI_STATIC_RESOURCE_SUFFIX.add(".js");
		URI_STATIC_RESOURCE_SUFFIX.add(".css");
		URI_STATIC_RESOURCE_SUFFIX.add(".gif");
		URI_STATIC_RESOURCE_SUFFIX.add(".png");
		URI_STATIC_RESOURCE_SUFFIX.add(".jpg");

		URI_STATIC_RESOURCE_REGEX.add(".*[.].*$");
	}

	protected boolean isStaticResourceUri(HttpServletRequest request) {
		String uri = request.getRequestURI();

		for (String suffix : URI_STATIC_RESOURCE_SUFFIX) {
			if (uri.endsWith(suffix) == true) {
				// log.debug("matched suffix={}, uri={}", suffix, uri);
				return true;
			}
		}

		/*
		 * check by regular expression
		 */
		for (String regex : URI_STATIC_RESOURCE_REGEX) {
			if (uri.matches(regex) == true) {
				// log.debug("matched regex={}, uri={}", regex, uri);
				return true;
			}
		}

		return false;
	}

	protected void makeSessionKey(HttpServletRequest request) throws Exception {
		request.setAttribute(CommonConst.SESSION_KEY, UUID.randomUUID().toString());
	}

	protected void setCache(HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 1L);
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
	}

	protected void setCORS(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean isSucc = true;
		String uri = request.getRequestURI();

		/*
		 * for handling exception
		 */
		request.setAttribute(MESSAGE_SOURCE, messageSource);

		setCache(response);
		setCORS(response);

		try {
			if (isStaticResourceUri(request) != true) {
				prePostHandle(request, response, handler);

				log.debug("URI=[{}] passed by interceptor", uri);
			}
		}
		catch (EchoException e) {
			String result = getJsonErrorReport(request, e);

			log.error("URI=[{}] blocked by interceptor, result={}", uri, result);

			if ((e.getMsgSrcParams() != null)
					&& ("error.auth.unauthorized.expired.cookie".equals(e
							.getMsgSrcParams()[0]) == true)) {
				CookieUtil.setCookieValue(response, CommonConst.COOKIE_KEY,
						"", 0, uri);
			}

			if (uri.indexOf(CONTEXT_WEB_PREFIX) != -1) {
				String redirectUrl = PropsUtil.getValue("context.web.path")
						+ INDEX;

				if (redirectUrl.startsWith("/") == false) {
					redirectUrl = "/" + redirectUrl;
				}

				log.debug("redirectUrl={}", redirectUrl);

				response.sendRedirect(redirectUrl);
			}
			else {
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(result);
			}

			isSucc = false;
		}

		return isSucc;
	}

	abstract protected void prePostHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception;

	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {
			Map<String, Object> modelMap = modelAndView.getModel();

			Object resultData = modelMap.get("resultData");
			System.out.println("#########3" + resultData);

			if ((resultData == null) || (resultData instanceof Map)) {
				Map<String, Object> result = (Map<String, Object>) resultData;

				if (result == null) {
					result = new HashMap<String, Object>();
					modelMap.put("resultData", result);
				}

				if (result.get("status") == null) {
					result.put("status", HttpServletResponse.SC_OK);
					result.put("msg", "OK");
				}

				result.put(CommonConst.SESSION_KEY, request
						.getAttribute(CommonConst.SESSION_KEY));
			}
		}

		if (isStaticResourceUri(request) == false) {
			log.debug("URI=[{}]", request.getRequestURI());
		}
		String uri = request.getRequestURI();
		EchoCookie echoCookie = (EchoCookie) request
				.getAttribute(CommonConst.COOKIE_KEY);
		if ((echoCookie != null) && (echoCookie.isNeedCook() == true)) {
			CookieUtil.setCookieValue(response, CommonConst.COOKIE_KEY,
					echoCookie.toString(), uri);
		}
		else if (uri.matches(REGEX_LOGOUT) == true) {
			CookieUtil.setCookieValue(response, CommonConst.COOKIE_KEY, "",
					0, uri);
		}
		
		
		
	}

	protected String getJsonErrorReport(HttpServletRequest request, Exception e)
			throws Exception {
		StringBuffer buff = new StringBuffer();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		int status = getStatus(e);
		Object[] params = null;
		String msgId = null;
		String msg = null;
		String msgExInfo = null;

		if (e instanceof EchoException) {
			params = ((EchoException) e).getMsgSrcParams();
			msgId = String.valueOf(params[0]);
		}
		else {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			params = new Object[] { "error.system.unknown" };
			msgId = String.valueOf(params[0]);
			msgExInfo = e.getMessage();
		}

		msg = messageSource.getMessage(msgId, params, WebUtil.getLocale(request
				.getHeader("Accept-Language")));

		resultMap.put("status", status);
		resultMap.put(CommonConst.SESSION_KEY, request
				.getAttribute(CommonConst.SESSION_KEY));
		resultMap.put("msg", msg);

		buff.append("sessionKey=").append(
				request.getAttribute(CommonConst.SESSION_KEY));
		buff.append(", status=").append(status);
		if (msgId != null) {
			buff.append(", msgId=").append(msgId);
		}
		buff.append(", msg=").append(msg.replaceAll("\n", " "));
		if (msgExInfo != null) {
			buff.append(", msgExInfo=").append(msgExInfo);
		}

		log.error("{}", buff.toString(), e);

		String result = JsonUtil.encode(resultMap).replaceAll("\n", " ");

		return result;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
	}

	protected int getStatus(Exception e) {
		int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

		if (e instanceof IllegalArgumentException) {
			status = HttpServletResponse.SC_BAD_REQUEST;
		}
		else if (e instanceof EchoException) {
			status = ((EchoException) e).getCode();
		}

		return status;
	}
}