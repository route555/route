package com.echo.biz.interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.echo.framework.BaseInterceptor;
import com.echo.framework.domain.PermMetaInfo;
import com.echo.framework.service.AuthService;
import com.echo.framework.type.RightsType;
import com.echo.framework.util.PropsUtil;

public class EchoInterceptor extends BaseInterceptor {
	@Autowired
	private AuthService authService;

	private static Set<String> URI_IGNORE_CHECKIP_REGEX = new HashSet<String>();

	static {
		URI_IGNORE_CHECKIP_REGEX.add(".*confirmAuth.*");
		URI_IGNORE_CHECKIP_REGEX.add(".*ctrlHistory.*");
		URI_IGNORE_CHECKIP_REGEX.add(".*faultHistory.*");
		URI_IGNORE_CHECKIP_REGEX.add(".*versions.*");
	}

	@PostConstruct
	public void init() {
		Set<String> loginRegex = new HashSet<String>();
		Set<String> noLoginRegex = new HashSet<String>();
		Map<String, PermMetaInfo> permMeta = new HashMap<>();

		/*
		 * login uri
		 */
		loginRegex.add(".*login");

		/*
		 * need not login uri
		 */
		noLoginRegex.add(".*index");
		noLoginRegex.add(".*test.*");
		noLoginRegex.add(".*synctime.*");
		noLoginRegex.add(".*logout.*");
		noLoginRegex.add(".*error");
		
		noLoginRegex.add(".*sample.*");

		String[] allowRegex = null;
		String[] denyRegex = null;
		Map<String, String[]> allowRegex4Rest = null;
		Map<String, String[]> denyRegex4Rest = null;

		/*
		 * SYSTEM_ADMIN
		 */
		allowRegex = new String[] { ".*" };
		denyRegex = new String[] {};
		allowRegex4Rest = new HashMap<String, String[]>();
		denyRegex4Rest = new HashMap<String, String[]>();

		permMeta.put(RightsType.SYSTEM_ADMIN.code(), new PermMetaInfo(allowRegex, denyRegex, allowRegex4Rest, denyRegex4Rest));

		permMeta.put(RightsType.OPERATOR.code(), new PermMetaInfo(allowRegex, denyRegex, allowRegex4Rest, denyRegex4Rest));

		permMeta.put(RightsType.USER.code(), new PermMetaInfo(allowRegex, denyRegex, allowRegex4Rest, denyRegex4Rest));

		/*
		 * OPERATOR
		 */
		// allowRegex = new String[] {};
		// denyRegex = new String[] {
		//
		// "/system/business.*"
		//
		// };
		// allowRegex4Rest = new HashMap<String, String[]>();
		// denyRegex4Rest = new HashMap<String, String[]>();
		// denyRegex4Rest.put("/business.*", new String[] { "POST", "PUT",
		// "DELETE" });
		//
		// permMeta.put(RightsType.OPERATOR.code(), new PermMetaInfo(allowRegex,
		// denyRegex, allowRegex4Rest, denyRegex4Rest));

		/*
		 * STAFF
		 */
		// allowRegex = new String[] {
		//
		// "/login",
		//
		// "/popup[/]?.*",
		//
		// "/device",
		//
		// "/device/.*",
		//
		// "/device/control[/]?.*",
		//
		// "/monitoring",
		//
		// "/monitoring/.*"
		//
		// };
		// denyRegex = new String[] {
		//
		// "/system[/]?.*",
		//
		// "/user",
		//
		// "/user/.*",
		//
		// "/contract[/]?.*",
		//
		// "/contents",
		//
		// "/contents/.*",
		//
		// "/containers/container.*",
		//
		// "/schedules/schedule.*"
		//
		// };
		// allowRegex4Rest = new HashMap<String, String[]>();
		// allowRegex4Rest.put("/devices[/]?.*", new String[] { "POST", "PUT",
		// "GET", "DELETE" });
		// allowRegex4Rest.put("/ctrlDevices[/]?.*", new String[] { "POST",
		// "PUT", "GET", "DELETE" });
		// denyRegex4Rest = new HashMap<String, String[]>();
		// denyRegex4Rest.put(".*", new String[] { "POST", "PUT", "DELETE" });
		//
		// permMeta.put(RightsType.STAFF.code(), new PermMetaInfo(allowRegex,
		// denyRegex, allowRegex4Rest, denyRegex4Rest));

		/*
		 * ADVERTISER
		 */
		// allowRegex = new String[] {
		//
		// "/login",
		//
		// "/popup[/]?.*",
		//
		// "/device",
		//
		// "/device/.*",
		//
		// "/monitoring",
		//
		// "/monitoring/.*"
		//
		// };
		// denyRegex = new String[] {
		//
		// "/system[/]?.*",
		//
		// "/user",
		//
		// "/user/.*",
		//
		// "/contract[/]?.*",
		//
		// "/contents",
		//
		// "/contents/.*",
		//
		// "/containers/container.*",
		//
		// "/schedules/schedule.*",
		//
		// "/device/control[/]?.*"
		//
		// };
		// allowRegex4Rest = new HashMap<String, String[]>();
		// denyRegex4Rest = new HashMap<String, String[]>();
		// denyRegex4Rest.put(".*", new String[] { "POST", "PUT", "DELETE" });
		//
		// permMeta.put(RightsType.ADVERTISER.code(), new
		// PermMetaInfo(allowRegex,
		// denyRegex, allowRegex4Rest, denyRegex4Rest));

		authService.setLoginRegex(loginRegex);
		authService.setNoLoginRegex(noLoginRegex);
		authService.setPermMeta(permMeta);
	}

	private boolean isIgnoreCheckIp(HttpServletRequest request) {
		String uri = request.getRequestURI();

		for (String regex : URI_IGNORE_CHECKIP_REGEX) {
			if (uri.matches(regex) == true) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void prePostHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		makeSessionKey(request);
		if (isIgnoreCheckIp(request) == false) {
			authService.checkIp(request);
		}
		//authService.checkAuth(request, PropsUtil.getValue("context.echo.path"));
	}
}
