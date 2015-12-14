package com.echo.framework.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.echo.framework.dao.AuthDao;
import com.echo.framework.dao.GenerateTestDataDao;
import com.echo.framework.domain.Auth;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.exception.EchoException;
import com.echo.framework.type.CommonConst;
import com.echo.framework.type.CtrlType;
import com.echo.framework.type.RightsType;
import com.echo.framework.util.CryptUtil;

@Repository
public class GenerateTestData {
	@Autowired
	private AuthDao authDao;

	@Autowired
	private GenerateTestDataDao testDao;

	public static String getPhone() {
		String seed = String.valueOf(new Date().getTime());

		StringBuffer buff = new StringBuffer();
		buff.append("010").append("-").append(seed.substring(4, 8)).append("-")
				.append(seed.substring(9));

		return buff.toString();
	}

	public static String getHoliday() {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		StringBuffer buff = new StringBuffer();
		String[] holidays = { "0101", "0301", "0505", "0717", "0815", "1009",
				"1225" };

		for (String day : holidays) {
			buff.append(year).append(day).append(",");
		}

		int len = buff.lastIndexOf(",");
		if (len != -1) {
			return buff.substring(0, len);
		}

		return buff.toString();
	}

	public static String getRandomStr(String prefix, int maxLen) {
		long now = new Date().getTime();

		String seed = String.valueOf(now);
		int len = seed.length();
		int prefixLen = prefix == null ? 0 : prefix.length();

		if (maxLen < (prefixLen + len)) {
			return prefix + seed.substring(len - (maxLen - prefixLen));
		}

		int paddingLen = (maxLen - prefixLen) - len;
		for (int i = 0; i < paddingLen; i++) {
			seed += "0";
		}

		return prefix + seed;
	}

	private long insertBusinessId(String businessName, String businessDesc,
			String openTime, String closeTime, String holiday, long regUserId) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("businessName", businessName);
		map.put("businessDesc", businessDesc);

		map.put("openTime", openTime);
		map.put("closeTime", closeTime);
		map.put("holiday", holiday);
		map.put("soundVolume", 10);
		map.put("brightness", 10);

		map.put("regUserId", regUserId);
		map.put("updUserId", regUserId);

		testDao.insertBusiness(map);

		return (Long) map.get("businessId");
	}

	private long insertUser(long businessId, String loginId, String passwd,
			String userName, String phone, String email, RightsType rightsType,
			String isUse, long regUserId) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("businessId", businessId);
		map.put("loginId", loginId);
		map.put("passwd", passwd);
		try {
			map.put("passwdByCrypt", CryptUtil.encryptHmacSha256(passwd));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		map.put("userName", userName);
		map.put("phone", phone);
		map.put("email", email);
		map.put("rightsType", rightsType.code());
		map.put("isUse", isUse);
		map.put("regUserId", regUserId);
		map.put("updUserId", regUserId);

		testDao.insertUser(map);

		return (Long) map.get("userId");
	}

	private void updateRegUserId(long businessId, long userId) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("businessId", businessId);
		map.put("regUserId", userId);
		map.put("updUserId", userId);
		map.put("userId", userId);

		testDao.updateBusiness(map);
		testDao.updateUser(map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> generateUser(RightsType rightsType)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/*
		 * common info
		 */
		long regUserId = 0;

		/*
		 * insert business
		 */
		String businessName = getRandomStr("bizName_", 25);
		String businessDesc = getRandomStr("bizDesc_", 25);
		String openTime = "090000";
		String closeTime = "220000";
		String holiday = getHoliday();
		long businessId = insertBusinessId(businessName, businessDesc,
				openTime, closeTime, holiday, regUserId);

		/*
		 * insert user
		 */
		String loginId = getRandomStr("", 12);
		String passwd = getRandomStr("", 12);
		String userName = getRandomStr("user_", 10);
		String phone = getPhone();
		String email = map.get("loginId") + "@a.com";
		long userId = insertUser(businessId, loginId, passwd, userName, phone,
				email, rightsType, CommonConst.FLAG_Y, regUserId);

		/*
		 * update business, user
		 */
		updateRegUserId(businessId, userId);

		Map<String, Object> user = testDao.selectUser(userId);
		user.put("passwd", passwd);

		return user;
	}

	private Map<String, Object> insertCtrlHistory(long businessId,
			long deviceId, CtrlType ctrlType, String version, long regUserId) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("businessId", businessId);
		map.put("deviceId", deviceId);
		map.put("ctrlType", ctrlType.code());
		map.put("result", version);
		map.put("regUserId", regUserId);
		map.put("updUserId", regUserId);

		map.put("deviceGroupId", testDao.insertCtrlHistory(map));

		return map;
	}

	private Map<String, Object> insertDevice(int businessId, int regUserId,
			String deviceDesc, String deviceMeta, String openTime,
			String closeTime, String deviceName, String version) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("businessId", businessId);
		map.put("deviceDesc", deviceDesc);
		map.put("deviceMeta", deviceMeta);
		map.put("openTime", openTime);
		map.put("closeTime", closeTime);
		map.put("deviceName", deviceName);
		map.put("holiday", getHoliday());
		map.put("soundVolume", 1);
		map.put("brightness", 1);
		map.put("regUserId", regUserId);
		map.put("updUserId", regUserId);
		map.put("isFault", CommonConst.FLAG_N);
		map.put("faultDateTime", null);

		testDao.insertDevice(map);

		long deviceId = (Long) map.get("deviceId");
		map.put("deviceId", deviceId);

		insertCtrlHistory(businessId, deviceId, CtrlType.UPDATE_VERSION,
				version, regUserId);

		return map;
	}

	public Map<String, Object> generateDevice() throws Exception {
		Map<String, Object> user = generateUser(RightsType.SYSTEM_ADMIN);

		int businessId = (Integer) user.get("businessId");
		int regUserId = (Integer) user.get("userId");
		String deviceDesc = getRandomStr("deviceDesc_", 20);
		String deviceMeta = "{\"inch\":16,\"resolution\":\"1920x768\"}";
		String openTime = "090000";
		String closeTime = "220000";
		String deviceName = getRandomStr("deviceName_", 25);
		String version = "1.0.0";

		return insertDevice(businessId, regUserId, deviceDesc, deviceMeta,
				openTime, closeTime, deviceName, version);

	}

	public EchoCookie generateEchoCookie(RightsType rightsType)
			throws Exception {
		if (rightsType == null) {
			throw new RuntimeException("required rightsType");
		}

		Map<String, Object> user = generateUser(rightsType);

		Auth auth = new Auth();
		auth.setLoginId((String) user.get("loginId"));
		auth.setPasswd((String) user.get("passwd"));
		auth = authDao.selectUserAuth(auth);

		if (auth == null) {
			throw new EchoException(HttpServletResponse.SC_UNAUTHORIZED,
					new Object[] { "error.auth.require.signup" });
		}

		return new EchoCookie(CommonConst.COOKIE_KEY, auth);
	}

	public EchoCookie generateEchoCookie4System() throws Exception {

		Auth auth = new Auth();
		auth.setUserId(AuthService.SYS_USERID);
		auth.setRightsType(RightsType.SYSTEM_ADMIN.code());
		auth.setBusinessId(0);
		auth.setServiceId(0);

		return new EchoCookie(CommonConst.COOKIE_KEY, auth);
	}
}