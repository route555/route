package com.echo.framework;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.util.WebUtil;

public class XSSFilter implements Filter {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(XSSFilter.class);

	private class XSSRequestWrapper extends HttpServletRequestWrapper {
		private Map<String, String[]> parameterMap = null;

		public XSSRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(getParameterMap().keySet());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map<String, String[]> getParameterMap() {
			if (parameterMap == null) {
				Map<String, String[]> tmpParameterMap = new HashMap<String, String[]>();
				Map<String, String[]> orgParameterMap = super.getParameterMap();

				if (orgParameterMap != null) {
					for (String key : (Set<String>) orgParameterMap.keySet()) {
						String[] orgVals = orgParameterMap.get(key);
						String[] cleanVals = new String[orgVals.length];

						for (int i = 0; i < orgVals.length; i++) {
							cleanVals[i] = WebUtil.cleanXSS(orgVals[i]);
						}

						tmpParameterMap.put(key, cleanVals);
					}
				}

				parameterMap = tmpParameterMap;
			}

			return parameterMap;
		}

		@Override
		public String[] getParameterValues(String parameter) {
			return getParameterMap().get(parameter);
		}

		@Override
		public String getParameter(String parameter) {
			String[] params = getParameterValues(parameter);
			return params != null && params.length > 0 ? params[0] : null;
		}

		@Override
		public String getHeader(String name) {
			String value = super.getHeader(name);
			return WebUtil.cleanXSS(value);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (ServletFileUpload.isMultipartContent((HttpServletRequest) request) == false) {
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request),
					response);
		}
		else {
			chain.doFilter(request, response);
		}
	}
}
