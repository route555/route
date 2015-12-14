package com.echo.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.domain.EchoCookie;

public class CookieUtil {
	private static Logger log = LoggerFactory.getLogger(CookieUtil.class);

	public static final String COOKIE_PATH = "/";
	public static String COOKIE_DOMAIN = ".echoit.co.kr";
	private static final String COOKIE_ENC_CHARSET = "UTF-8";

	static {
		COOKIE_DOMAIN = PropsUtil.getValue("cookie.domain");

		log.info("COOKIE_DOMAIN={}", COOKIE_DOMAIN);
	}

	private HttpServletResponse resp;

	private HashMap<String, String> cookieMap;

	public CookieUtil(HttpServletRequest req) {
		Cookie cookies[] = req.getCookies();
		if (cookies != null) {
			cookieMap = new HashMap<String, String>();
			try {
				for (int i = 0; i < cookies.length; i++) {
					cookieMap.put(cookies[i].getName(), URLDecoder.decode(
							cookies[i].getValue(), COOKIE_ENC_CHARSET));
				}
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException", e);
			}
		}
	}

	public static Cookie getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(key)) {
					return cookies[i];
				}
			}
		}

		return null;
	}

	public static boolean hasCookieInRequest(HttpServletRequest request,
			String key) {
		return getCookieValue(request, key) != null ? true : false;
	}

	public static EchoCookie getEchoCookie(HttpServletRequest request,
			String key) {
		String cookie = getCookieValue(request, key);

		if (cookie != null) {
			return new EchoCookie(key, cookie);
		}

		return null;
	}

	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie cookie = getCookie(request, key);

		if (cookie != null) {
			try {
				return URLDecoder.decode(cookie.getValue(), COOKIE_ENC_CHARSET);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException", e);
			}
		}

		return null;
	}

	public String getCookieValue(String key) {
		if (cookieMap == null) {
			return null;
		}
		else {
			String tmp = (String) cookieMap.get(key);

			if ((tmp == null) || ("".equals(tmp) == true)) {
				return null;
			}

			return tmp;
		}
	}

	public static Cookie getCookie(String name, String value, int maxAge) {
		try {
			Cookie cookie = new Cookie(name, URLEncoder.encode(value,
					COOKIE_ENC_CHARSET));
			cookie.setDomain(COOKIE_DOMAIN);
			cookie.setPath(COOKIE_PATH);
			cookie.setMaxAge(maxAge);

			return cookie;
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UnsupportedEncodingException", e);
		}
	}

	public static void setCookieValue(HttpServletResponse res, String name,
			String value, int maxAge, String reqUrl) {
		if (res == null || StringUtils.isEmpty(name) == true || (value == null)) {
			return;
		}

		res.addCookie(getCookie(name, value, maxAge));

		log.debug("SAVE COOKIE, req={}, [{}]", reqUrl, name);
	}

	public static void setCookieValue(HttpServletResponse res, String name,
			String value, String url) {
		setCookieValue(res, name, value, -1, url);
	}

	public void setCookieValue(String name, String value, int maxAge) {
		setCookieValue(resp, name, value, maxAge, null);
	}

	public void setCookieValue(String name, String value) {
		setCookieValue(resp, name, value, -1, null);
	}
}
