package com.echo.framework.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.echo.framework.BaseInterceptor;
import com.echo.framework.domain.Auth;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.service.AuthService;
import com.echo.framework.type.CommonConst;
import com.echo.framework.type.RightsType;

public class EchoUtil {
	private static Logger log = LoggerFactory.getLogger(EchoUtil.class);

	public static String getFSPath(String businessId, String date, String uuid,
			String ext) {
		String dstPath = businessId + "/" + date + "/" + uuid + "." + ext;

		return dstPath;
	}

	public static String getFSPath(int businessId, Date date, String ext) {
		return getFSPath(String.valueOf(businessId),
				DateUtils.DATE_FORMAT_YYYYMMDD.format(date), UUID.randomUUID()
						.toString(), ext);
	}

	public static String getFSPath(String prefix, Date date, String ext) {
		return prefix + "/" + DateUtils.DATE_FORMAT_YYYYMM.format(date) + "/"
				+ UUID.randomUUID().toString() + "." + ext;
	}

	public static String getFSPath4Bin(String prefix, Date date, String fileName) {
		return prefix + "/" + DateUtils.DATE_FORMAT_YYYYMM.format(date) + "/"
				+ fileName;
	}

	public static Boolean isNullOrZero(Object obj) {
		if (obj == null) {
			return true;
		}

		if ((obj instanceof Integer) && ((Integer) obj == 0)) {
			return true;
		}
		else if ((obj instanceof Long) && ((Long) obj == 0L)) {
			return true;
		}

		return false;
	}

	public static void wrapForInQuery(Map<String, Object> param, String[] keys) {
		for (String key : keys) {
			String val = (String) param.get(key);

			if ((StringUtils.isEmpty(val) == false)
					&& (val.startsWith("'") == false)) {
				val = "'".concat(
						val.replaceAll(CommonConst.DEFAULT_SEPARATOR, "','"))
						.concat("'");

				param.put(key + "Wrap", val);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getValuesAndMapByKeyFromListMap(List<Map> list,
			String key, String separator, Map<Object, Map> valueMap) {
		StringBuffer values = new StringBuffer();

		Object value = null;

		for (Map map : (List<Map>) list) {
			value = map.get(key);
			values.append(value).append(separator);

			if (valueMap != null) {
				valueMap.put(value, map);
			}
		}

		int len = values.length();

		if (len > 0) {
			return values.substring(0, len - 1);
		}
		else {
			return null;
		}
	}

	public static Set<String> getSetByString(String array, String separator) {
		Set<String> set = new HashSet<String>();

		String[] tmp = array.split(separator);
		for (String key : tmp) {
			set.add(key);
		}

		return set;
	}

	public static boolean isGreater(String left, String right, String separator) {
		boolean isGreater = false;

		if ((StringUtils.isEmpty(left) == true)
				&& (StringUtils.isEmpty(right) == true)) {
			/* equal */
		}
		else if ((StringUtils.isEmpty(left) == true)
				&& (StringUtils.isEmpty(right) == false)) {
			/* less */
		}
		else if ((StringUtils.isEmpty(left) == false)
				&& (StringUtils.isEmpty(right) == true)) {
			/* greater */
			isGreater = true;
		}
		else {
			String[] leftList = left.split(separator);
			String[] rightList = right.split(separator);

			int size = leftList.length > rightList.length ? rightList.length
					: leftList.length;

			for (int i = 0; i < size; i++) {
				String leftStr = leftList[i];
				String rightStr = rightList[i];

				if ((StringUtils.isEmpty(leftStr) == true)
						&& (StringUtils.isEmpty(rightStr) == true)) {
					/* equal */
					continue;
				}
				else if ((StringUtils.isEmpty(leftStr) == true)
						&& (StringUtils.isEmpty(rightStr) == false)) {
					/* less */
					break;
				}
				else if ((StringUtils.isEmpty(leftStr) == false)
						&& (StringUtils.isEmpty(rightStr) == true)) {
					/* greater */
					isGreater = true;
					break;
				}

				int leftInt = Integer.parseInt(leftStr);
				int rightInt = Integer.parseInt(rightStr);

				if (leftInt == rightInt) {
					/* equal */
				}
				else if (leftInt < rightInt) {
					/* less */
					break;
				}
				else if (leftInt > rightInt) {
					/* greater */
					isGreater = true;
					break;
				}
			} /* end of for (int i = 0; i < size; i++) */
		}

		return isGreater;
	}

	public static EchoCookie getEchoCookie4System() throws Exception {

		Auth auth = new Auth();
		auth.setUserId(AuthService.SYS_USERID);
		auth.setRightsType(RightsType.SYSTEM_ADMIN.code());
		auth.setTenantId(0);
		auth.setServiceId(0);

		return new EchoCookie(CommonConst.COOKIE_KEY, auth);
	}

	/**
	 * request IP를 반환한다.
	 */
	public static String getRequestIp(HttpServletRequest req) {
		// TODO : L4 Switch 도입 시 변경 필요함
		return req.getRemoteAddr();
	}

	public static String getJsonErrorReport(HttpServletRequest request,
			Object obj) {
		int status = -1;

		try {
			status = Integer.parseInt(request.getParameter("errorCode"));
		}
		catch (Exception e) {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}

		MessageSource messageSource = (MessageSource) request
				.getAttribute(BaseInterceptor.MESSAGE_SOURCE);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msgId = null;
		String msg = null;

		if ((status > 0) && (status < 500)) {
			msgId = "error.system.unsupported";
		}
		else {
			msgId = "error.system.unknown";
		}

		Object[] params = new Object[] { msgId };

		if (messageSource != null) {
			msg = messageSource.getMessage(msgId, params, WebUtil
					.getLocale(request.getHeader("Accept-Language")));
		}
		else {
			msg = "There was a problem at system. Please, try again after a while";
		}

		resultMap.put("status", status);
		resultMap.put(CommonConst.SESSION_KEY, request
				.getAttribute(CommonConst.SESSION_KEY));
		resultMap.put("msg", msg);

		if ((obj != null) && ((obj instanceof Throwable) == true)) {
			resultMap.put("e", ((Throwable) obj).getMessage());
		}

		String result = null;

		try {
			result = JsonUtil.encode(resultMap).replaceAll("\n", " ");
		}
		catch (Exception e) {
			log.error("method={}, status={}", new Object[] {
					request.getMethod(), status, e });

			result = e.getMessage();
		}

		log.error("result={}", result);

		return result;
	}
}
