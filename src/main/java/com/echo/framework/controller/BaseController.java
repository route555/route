package com.echo.framework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.domain.EchoCookie;
import com.echo.framework.dto.BaseDto;
import com.echo.framework.type.CommonConst;
import com.echo.framework.util.WebUtil;

public abstract class BaseController {
	protected static Logger log;

	@PostConstruct
	public void init() {
		log = LoggerFactory.getLogger(this.getClass());
	}

	protected <T> List<T> transList(T t) {
		List<T> list = new ArrayList<T>();

		list.add(t);

		return list;
	}

	private void setDefault(HttpServletRequest request, Map<String, Object> map) {
		/*
		 * for page
		 */
		if (map.get("page") == null) {
			map.put("page", 1);
		}

		if (map.get("pageSize") == null) {
			map.put("pageSize", 10);
		}
		
		
		if (map.get("iDisplayStart") != null) {
			map.put("start",Integer.parseInt( (String) map.get("iDisplayStart")));
		}
		if (map.get("iDisplayLength") != null) {
			map.put("pageSize",Integer.parseInt( (String) map.get("iDisplayLength")));
		}
		
		//DisplayStart=0&iDisplayLength=10
		
		

		if ((map.get("page") != null) && (map.get("pageSize") != null)) {
			try {
				int page = Integer.parseInt(String.valueOf(map.get("page")));
				int pageSize = Integer.parseInt(String.valueOf(map
						.get("pageSize")));

				//map.put("start", (page - 1) * pageSize);
				//request.setAttribute("start", map.get("start"));
			}
			catch (Exception e) {

			}
		}

		/*
		 * for order
		 */
		if (map.get("orderType") == null) {
			map.put("orderType", "DESC");
		}
	}

	private void setEchoCookieInfo(HttpServletRequest request,
			Map<String, Object> map) {
		EchoCookie echoCookie = (EchoCookie) request
				.getAttribute(CommonConst.COOKIE_KEY);

		if (echoCookie != null) {
			map.put(CommonConst.COOKIE_KEY, echoCookie);

			// if (RightsType.SYSTEM_ADMIN.code().equals(
			// echoCookie.getRightsType()) == false) {
			// map.put("regUserId", echoCookie.getValue("userId"));
			// }

			// reserved
			// map.put("businessId", echoCookie.getValue("businessId"));
			// map.put("serviceId", echoCookie.getValue("serviceId"));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> getParameterMap(HttpServletRequest request)
			throws ServletException, IOException {

		Map pMap = new HashMap();

		setEchoCookieInfo(request, pMap);

		Map parameterMap = request.getParameterMap();
		Iterator itr = parameterMap.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			Object obj = parameterMap.get(key);

			if (obj instanceof String) {
				pMap.put(key, WebUtil.cleanXSS(String.valueOf(obj)));
			}
			else if (obj instanceof String[]) {
				String[] values = (String[]) obj;

				if (values.length == 1) {
					pMap.put(key, WebUtil.cleanXSS(String.valueOf(values[0])));
				}
				else {
					for (int i = 0; i < values.length; i++) {
						values[i] = WebUtil.cleanXSS(String.valueOf(values[i]));
					}

					pMap.put(key, values);
				}
			}
			else {
				pMap.put(key, obj);
			}
		}

		setDefault(request, pMap);

		return pMap;
	}

	protected void setReqUserIdAndUpdUserId(HttpServletRequest request,
			BaseDto dto) {
		EchoCookie echoCookie = (EchoCookie) request
				.getAttribute(CommonConst.COOKIE_KEY);
		dto.setEchoCookie(echoCookie);
	}
}
