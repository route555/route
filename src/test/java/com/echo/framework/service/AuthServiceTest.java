package com.echo.framework.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.echo.framework.domain.Auth;
import com.echo.framework.domain.PermMetaInfo;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.exception.EchoException;
import com.echo.framework.type.CommonConst;
import com.echo.framework.type.RightsType;
import com.echo.framework.util.CookieUtil;
import com.echo.framework.util.CryptUtil;
import com.echo.framework.util.PropsUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/application-context-test.xml",
		"classpath:/META-INF/spring/application-context-dao-test.xml",
		"classpath:/META-INF/spring/application-context-service-test.xml" })
@Transactional
public class AuthServiceTest {
	@Autowired
	private AuthService authService;

	@Autowired
	private GenerateTestData generateTestData;

	@Before
	public void authServiceInit() {
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
		noLoginRegex.add(".*synctime.*");

		String[] allowRegex = null;
		String[] denyRegex = null;
		Map<String, String[]> allowRegex4Rest = null;
		Map<String, String[]> denyRegex4Rest = null;

		/*
		 * ADVERTISER
		 */
		allowRegex = new String[] { "/login", "/popup[/]?.*", "/monitoring",
				"monitoring/.*" };
		denyRegex = new String[] { "/system[/]?.*", "/user", "user/.*",
				"/contract[/]?.*", "/contents", "/contents/.*",
				"/containers/container.*", "/schedules/schedule.*", "/device",
				"/device/.*", "/device/control[/]?.*" };
		allowRegex4Rest = new HashMap<String, String[]>();
		denyRegex4Rest = new HashMap<String, String[]>();
		denyRegex4Rest.put(".*", new String[] { "POST", "PUT", "DELETE" });

		permMeta.put(RightsType.USER.code(), new PermMetaInfo(allowRegex,
				denyRegex, allowRegex4Rest, denyRegex4Rest));

		authService.setLoginRegex(loginRegex);
		authService.setNoLoginRegex(noLoginRegex);
		authService.setPermMeta(permMeta);
	}

	private Auth userInit() throws Exception {
		Map<String, Object> user = generateTestData
				.generateUser(RightsType.USER);

		Auth auth = new Auth();
		auth.setLoginId((String) user.get("loginId"));
		auth.setLoginPw((String) user.get("loginPw"));

		return auth;
	}

	@Test
	public void userAuth() throws Exception {
		Auth auth = userInit();
		String contextPath = PropsUtil.getValue("context.admin.path");

		MockHttpServletRequest request = new MockHttpServletRequest();

		/*
		 * give url == login
		 */
		request.setRequestURI("/login");

		/*
		 * give not exist loginId on user in DB, then require signup
		 */
		try {
			request.setParameter("loginId", auth.getLoginId() + "00");
			authService.checkAuth(request, contextPath);

			Assert.fail("give url == login && not exist loginId on user in DB, then require signup");
		}
		catch (EchoException e) {
			Assert.assertTrue(
					"give url == login && not exist loginId on user in DB, then require signup",
					e.getMessage().indexOf("error.auth.require.signup") != -1);
		}

		/*
		 * give exist loginId on user in DB, then echoCookie is not null
		 */
		request.setParameter("loginId", auth.getLoginId());
		request.setParameter("loginPw", auth.getLoginPw());
		authService.checkAuth(request, contextPath);
		EchoCookie echoCookie = (EchoCookie) request
				.getAttribute(CommonConst.COOKIE_KEY);
		Assert.assertNotNull(echoCookie);

		/*
		 * give url != login
		 */
		request.setRequestURI("/users");

		/*
		 * give not exist cookie, then require signin
		 */
		request.setAttribute(CommonConst.COOKIE_KEY, null);
		try {
			authService.checkAuth(request, contextPath);
			Assert.fail("give url != login && not exist cookie, then require signin");
		}
		catch (EchoException e) {
			Assert.assertTrue(
					"give url != login && not exist cookie, then require signin",
					e.getMessage().indexOf("error.auth.require.signin") != -1);
		}

		/*
		 * give exist cookie
		 */
		request.setCookies(new Cookie[] { CookieUtil.getCookie(
				CommonConst.COOKIE_KEY, echoCookie.toString(),
				(int) (new Date().getTime() + 10000000) /* maxAge */) });

		/*
		 * give hasPermission == true
		 */
		request.setMethod("GET");
		authService.checkAuth(request, contextPath);

		/*
		 * give hasPermission != true
		 */
		try {
			request.setMethod("DELETE");
			authService.checkAuth(request, contextPath);

			Assert.fail("give hasPermission != true, then unauthorized");
		}
		catch (EchoException e) {
			Assert.assertTrue(
					"give hasPermission != true, then unauthorized",
					e.getMessage()
							.indexOf("error.auth.unauthorized.permission") != -1);

		}
	}

	private Auth deviceInit() throws Exception {
		Map<String, Object> device = generateTestData.generateDevice();

		Auth auth = new Auth();
		auth.setDeviceId((int) (long) device.get("deviceId"));
		auth.setSerial((String) device.get("serial"));
		auth.setMacAddr((String) device.get("macAddr"));

		return auth;
	}

	@Test
	public void deviceAuth() throws Exception {
		Auth auth = deviceInit();

		MockHttpServletRequest request = new MockHttpServletRequest();

		request.setRequestURI("/synctime");
		request.setCharacterEncoding(CryptUtil.DEFAULT_CHARSET);

		authService.checkAuth(request, "");

		request.addHeader(AuthService.X_ECHO_DEVICEID, CryptUtil
				.urlEncodeCryptDES(String.valueOf(auth.getDeviceId()),
						CommonConst.ECHO_CRYPT_KEY));

		request.setRequestURI("/devices");
		authService.checkAuth(request, "");

		EchoCookie echoCookie = (EchoCookie) request
				.getAttribute(CommonConst.COOKIE_KEY);
		Assert.assertNotNull(echoCookie);
	}

	private String[] webUrlList = { "/login", "/system/business",
			"/system/businessAdd", "/system/businessDetail/{id}",
			"/system/businessModify/{id}", "/system/service",
			"/system/serviceList/{id}", "/system/serviceAdd",
			"/system/serviceDetail/{id}", "/system/serviceModify/{id}",
			"/system/deviceGroup", "/system/deviceGroup/serviceList/{id}",
			"/system/deviceGroupList/{id}", "/system/deviceGroupAdd",
			"/system/deviceGroupDetail/{id}", "/system/deviceGroupModify/{id}",
			"/system/adTpl", "/system/adTplList/{id}", "/system/adTplAdd",
			"/system/adTplDetail/{id}", "/system/adTplModify/{id}",
			"/contents/contents", "/contents/textList/{id}",
			"/contents/thumbnailList/{id}", "/contents/contentsAdd",
			"/contents/contentsDetail/{id}", "/contents/contentsModify/{id}",
			"/containers/containers", "/containers/containerList/{id}",
			"/containers/containerAdd", "/containers/containerDetail/{id}",
			"/containers/containerModify/{id}", "/user/user",
			"/user/user/{id}", "/user/userAdd/{id}", "/user/userModify/{id}",
			"/user/userDetail/{id}", "/contract/contractAdd",
			"/contract/contractDetail/{id}", "/contract/contractModify/{id}",
			"/contract", "/contract/contractList/{id}",
			"/contract/contractDevice/{id}", "/device/deviceAdd",
			"/device/deviceAddAll/{id}", "/device", "/device/deviceList/{id}",
			"/device/deviceDetail/{id}", "/device/deviceModify/{id}",
			"/device/deviceModifyAll/{id}", "/device/deviceDis",
			"/device/control", "/device/control/deviceControlList/{id}",
			"/device/control/deviceControl/{id}",
			"/device/control/deviceControlAll/{id}", "/popup/company",
			"/popup/user", "/popup/contract", "/popup/category/{id}",

	};

	private String[] restUrlList = {
			// admin
			"/ads", "/ads/{adId}", "/ads/count", "/ads/stop",
			"/monitor/device", "/monitor/adEvent", "/synctime/{adEventType}",
			"/adtpls", "/adtpls/adTplNameSameCheck", "/adtpls/{adTplId}",
			"/adtpls/search", "/adtpls/count", "/business",
			"/business/{businessId}", "/business/busyNameSameCheck",
			"/companys", "/companys/{companyId}", "/companys/count",
			"/ctrlDevices", "/ctrlDevices/{deviceId}", "/ctrlDevices/count",
			"/ctrlHistory", "/ctrlHistory/{ctrlHistoryId}",
			"/ctrlHistory/count", "/devices", "/devices/{deviceId}",
			"/devices/count", "/devices/confirmAuth", "/deviceGroup",
			"/deviceGroup/{deviceGroupId}",
			"/deviceGroup/deviceGroupNameSameCheck",
			"/test/devices/{deviceId}", "/faultHistory",
			"/faultHistory/{faultHistoryId}", "/rights", "/rights/{rightsId}",
			"/rights/count", "/schedules", "/schedules/{adScheduleId}",
			"/containers", "/containers/{containerId}",
			"/containers/name",
			"/users",
			"/users/{userId}",
			"/users/count",
			"/users/{userId}/reset/passwd",

			// cms
			"/contentsCategory", "/contentsCategory/{contentsCategoryId}",
			"/contents", "/contents/{contentsId}",
			"/contents/contentsNameSameCheck", "/contentsTag",
			"/contentsTag/{contentsTagId}",

			// ad
			"/synctime", "/status/{opcode}", "/monitoring/{monType}",
			"/schedules", "/schedules/{adScheduleId}",

	};

	// @Test
	public void hasPermission() throws Exception {
		authServiceInit();

		String[] methodList = { "POST", "PUT", "GET", "DELETE" };

		for (RightsType rightsType : RightsType.values()) {
			EchoCookie echoCookie = generateTestData
					.generateEchoCookie(rightsType);

			for (String method : methodList) {
				System.out.println("web --------------------");
				for (String url : webUrlList) {
					authService.hasPermission(method, url, echoCookie, "");
				}

				System.out.println("rest --------------------");
				for (String url : restUrlList) {
					authService.hasPermission(method, url, echoCookie, "");
				}
			}
		}
	}
}