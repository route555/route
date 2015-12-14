package com.echo.framework.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.framework.dao.AuthDao;
import com.echo.framework.domain.Auth;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.domain.PermMetaInfo;
import com.echo.framework.dto.BaseDto;
import com.echo.framework.exception.EchoException;
import com.echo.framework.type.CommonConst;
import com.echo.framework.util.CookieUtil;
import com.echo.framework.util.CryptUtil;
import com.echo.framework.util.PropsUtil;

@Service("AuthService")
public class AuthService extends AbstractService<Auth, BaseDto> {
	private static Logger log = LoggerFactory.getLogger(AuthService.class);

	public static String X_ECHO_DEVICEID = "X-ECHO-DEVICEID";

	public static final int SYS_USERID = -1;
	public static final int SYS_BUSINESSID = 0;

	
	private static int COOKIE_EXPIRE_MSEC = 3600000;
	private static int COOKIE_RECOOK_MSEC = 3600000 - 600000;

	private static final String IP_REGEX_ALL = "*";

	static {
		try {
			COOKIE_EXPIRE_MSEC = PropsUtil.getIntValue("cookie.expire.msec");
		} catch (Exception e) {
			log.warn("cookie.expire.msec is null, default is " + COOKIE_EXPIRE_MSEC);
		}
	}

	private Set<String> URI_LOGIN_REGEX = null;
	private Set<String> URI_NO_LOGIN_REGEX = null;
	private Map<String, PermMetaInfo> permMeta = null;

	@Autowired
	private AuthDao authDao;

	public AuthService() {
		super(Auth.class, BaseDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(authDao);
	}

	public void setLoginRegex(Set<String> loginRegex) {
		URI_LOGIN_REGEX = loginRegex;
	}

	public void setNoLoginRegex(Set<String> noLoginRegex) {
		URI_NO_LOGIN_REGEX = noLoginRegex;
	}

	public void setPermMeta(Map<String, PermMetaInfo> permMeta) {
		this.permMeta = permMeta;
	}

	public boolean hasPermission(String method, String reqUri, EchoCookie echoCookie, String contextPath) {
		boolean hasPermission = true;

		String path = removeContextPath(reqUri, contextPath);

		String rightsType = echoCookie.getRightsType();

		PermMetaInfo permMetaInfo = permMeta.get(rightsType);
		if (permMetaInfo == null) {
			log.debug("rightsType={}, method={}, path={}, hasPermission={}", new Object[] { rightsType, method, path, hasPermission });

			return hasPermission;
		}

		Set<String> allowRegexSet = permMetaInfo.getAllowRegexSet();
		Set<String> denyRegexSet = permMetaInfo.getDenyRegexSet();
		Map<String, Set<String>> allowRegexSet4Rest = permMetaInfo.getAllowRegexSet4Rest();
		Map<String, Set<String>> denyRegexSet4Rest = permMetaInfo.getDenyRegexSet4Rest();

		for (String regex : allowRegexSet) {
			if (path.matches(regex) == true) {
				log.debug("rightsType={}, method={}, path={}, hasPermission={}", new Object[] { rightsType, method, path, hasPermission });

				return hasPermission;
			}
		}

		for (String regex : denyRegexSet) {
			if (path.matches(regex) == true) {
				hasPermission = false;

				log.debug("rightsType={}, method={}, path={}, hasPermission={}", new Object[] { rightsType, method, path, hasPermission });

				return hasPermission;
			}
		}

		for (String regex : allowRegexSet4Rest.keySet()) {
			if ((path.matches(regex) == true) && (allowRegexSet4Rest.get(regex).contains(method) == true)) {
				log.debug("rightsType={}, method={}, path={}, hasPermission={}", new Object[] { rightsType, method, path, hasPermission });

				return hasPermission;
			}
		}

		for (String regex : denyRegexSet4Rest.keySet()) {
			if ((path.matches(regex) == true) && (denyRegexSet4Rest.get(regex).contains(method) == true)) {
				hasPermission = false;

				log.debug("rightsType={}, method={}, path={}, hasPermission={}", new Object[] { rightsType, method, path, hasPermission });

				return hasPermission;
			}
		}

		log.debug("rightsType={}, method={}, path={}, hasPermission={}", new Object[] { rightsType, method, path, hasPermission });

		return hasPermission;
	}

	private String removeContextPath(String orgUri, String contextPath) {
		String path = null;

		if ((StringUtils.isEmpty(contextPath) == false) && (contextPath.startsWith("/") == false)) {
			contextPath = "/" + contextPath;
		}

		if (StringUtils.isEmpty(orgUri) == false) {
			path = orgUri.replace(contextPath, "");
		}

		return path;
	}

	private void checkUserAuth(HttpServletRequest request, String contextPath) {
		String uri = request.getRequestURI();
		EchoCookie echoCookie = null;

		if (isNoLoginUri(uri)) {
			echoCookie = CookieUtil.getEchoCookie(request, CommonConst.COOKIE_KEY);

			if (echoCookie != null) {
				request.setAttribute(CommonConst.COOKIE_KEY, echoCookie);
			}

			return;
		} else if (isLoginUri(uri)) {
			Auth auth = new Auth();
			auth.setLoginId(request.getParameter("loginId"));
			auth.setPasswd(request.getParameter("passwd"));
			auth = authDao.selectUserAuth(auth);

			if (auth == null) {
				throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED, new Object[] { "error.auth.require.signup" });
			}

			echoCookie = new EchoCookie(CommonConst.COOKIE_KEY, auth);

			request.setAttribute(CommonConst.COOKIE_KEY, echoCookie);
		} else if (!CookieUtil.hasCookieInRequest(request, CommonConst.COOKIE_KEY)) {
			throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED, new Object[] { "error.auth.require.signin" });
		} else {
			long now = new Date().getTime();

			echoCookie = CookieUtil.getEchoCookie(request, CommonConst.COOKIE_KEY);
			if (now > (echoCookie.getTimestamp() + COOKIE_EXPIRE_MSEC)) {
				throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED, new Object[] { "error.auth.unauthorized.expired.cookie" });
			} else if (!hasPermission(request.getMethod(), uri, echoCookie, contextPath)) {
				throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED, new Object[] { "error.auth.unauthorized.permission" });
			}

			if (now >= (echoCookie.getTimestamp() + COOKIE_RECOOK_MSEC)) {
				echoCookie.setValue(EchoCookie.KEY_TIMESTAMP, String.valueOf(new Date().getTime()));
			}

			request.setAttribute(CommonConst.COOKIE_KEY, echoCookie);
		}
	}

	private boolean isLoginUri(String uri) {
		for (String regex : URI_LOGIN_REGEX) {
			if (uri.matches(regex) == true) {
				return true;
			}
		}

		return false;
	}

	private boolean isNoLoginUri(String uri) {
		for (String regex : URI_NO_LOGIN_REGEX) {
			if (uri.matches(regex) == true) {
				return true;
			}
		}

		return false;
	}

	private void checkDeviceAuth(HttpServletRequest request) {
		try {
			String cryptDeviceId = request.getHeader(X_ECHO_DEVICEID);
			String deviceId = CryptUtil.urlDecodeCryptDES(cryptDeviceId, CommonConst.ECHO_CRYPT_KEY);

			Auth auth = null, authFromDb = null;
			auth = new Auth();
			auth.setDeviceId(Integer.parseInt(deviceId));

			if ((StringUtils.isEmpty(deviceId) == true) || (authFromDb = authDao.selectDeviceAuth(auth)) == null) {
				throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED, new Object[] { "error.auth.unauthorized.device", deviceId });
			}

			/*
			 * for regUserId, updUserId
			 */
			authFromDb.setUserId(SYS_USERID);
			EchoCookie echoCookie = new EchoCookie(CommonConst.COOKIE_KEY, authFromDb);
			request.setAttribute(CommonConst.COOKIE_KEY, echoCookie);

			request.setAttribute("deviceId", authFromDb.getDeviceId());

			log.debug("deviceId={}, serial={}, macAddr={}, echoCookie={}", new Object[] { deviceId, authFromDb.getSerial(), authFromDb.getMacAddr(), echoCookie });
		} catch (EchoException e) {
			throw e;
		} catch (Exception e) {
			log.error("{}={}, e={}", new Object[] { X_ECHO_DEVICEID, request.getHeader(X_ECHO_DEVICEID), e.getMessage() });

			throw new EchoException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new Object[] { "error.system.unknown" }, e);
		}
	}

	public void checkAuth(HttpServletRequest request, String contextPath) {
		if (request.getHeader(X_ECHO_DEVICEID) != null) {
			checkDeviceAuth(request);
		} else {
			checkUserAuth(request, contextPath);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void checkIp(HttpServletRequest request) throws Exception {
		String ip = request.getRemoteAddr();
		log.debug("@@@@@001 checkIp start");
		List list = authDao.selectSecurityInfo();
		log.debug("@@@@@002 checkIp start" + list);
		if ((list == null) || (list.size() == 0)) {
			return;
		}

		for (Map<String, Object> row : (List<Map<String, Object>>) list) {
			String allowIp = (String) row.get("ip");

			if (StringUtils.isEmpty(allowIp) == true) {
				continue;
			} else if ((IP_REGEX_ALL.equals(allowIp) == true) || (ip.matches(allowIp) == true)) {
				return;
			}
		}
		log.debug("@@@@@001 checkIp end");
		throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED, new Object[] { "error.auth.unauthorized.ip", ip });
	}
}