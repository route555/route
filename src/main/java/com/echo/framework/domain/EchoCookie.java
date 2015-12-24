package com.echo.framework.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.type.CommonConst;
import com.echo.framework.util.CryptUtil;

public class EchoCookie {
	private static Logger log = LoggerFactory.getLogger(EchoCookie.class);

	private static final String IS_ENCRYPT = CommonConst.FLAG_Y;

	//public static final String COOKIE_KEY = "echo";

	/*
	 * KK is a separator of key and key
	 * 
	 * KV is a separator of key and value
	 * 
	 * KEY1=VALUE1&KEY2=VALUE2...
	 */
	private static final String SEPARATOR_KK = "&";
	private static final String SEPARATOR_KV = "=";

	public static final String KEY_USERID = "userId";
	public static final String KEY_USERNAME = "userName";
	public static final String KEY_LOGINID = "loginId";
	public static final String KEY_RIGHTSTYPE = "rightsType";
	public static final String KEY_TENANTID = "tenantId"; 
	public static final String KEY_TIMESTAMP = "ts";

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final Set<String> NEED_ENCODING = new HashSet<String>();

	static {
		NEED_ENCODING.add(KEY_USERNAME);
	}

	private boolean isNeedCook = false;

	private String cookieKey;
	private Map<String, String> echoCookie = null;

	public EchoCookie() {
	};

	public EchoCookie(String cookieKey, Auth auth) {
		echoCookie = new HashMap<String, String>();

		this.cookieKey = cookieKey;

		setValue(KEY_USERID, String.valueOf(auth.getUserId()), true /* isNeedCook */);
		setValue(KEY_USERNAME, String.valueOf(auth.getUserName()), true /* isNeedCook */);
		setValue(KEY_LOGINID, String.valueOf(auth.getLoginId()), true /* isNeedCook */);
		setValue(KEY_RIGHTSTYPE, auth.getRightsType(), true /* isNeedCook */);
		setValue(KEY_TENANTID, String.valueOf(auth.getTenantId()), true /* isNeedCook */);
		setValue(KEY_TIMESTAMP, String.valueOf(new Date().getTime()), true /* isNeedCook */);
	}

	public EchoCookie(String cookieKey, String cookie) {
		if (StringUtils.isEmpty(cookieKey) == true) {
			return;
		}

		echoCookie = new HashMap<String, String>();

		this.cookieKey = cookieKey;

		if (StringUtils.isEmpty(cookie) == true) {
			return;
		}

		if (CommonConst.FLAG_Y.equals(IS_ENCRYPT) == true) {
			try {
				cookie = CryptUtil.base64DecryptDES(cookie,
						CommonConst.ECHO_CRYPT_KEY);
			}
			catch (Exception e) {
				log.error("cookie={}", cookie, e);
				return;
			}
		}

		String[] tmp = cookie.split(SEPARATOR_KK);
		String val;
		for (int i = 0; i < tmp.length; i++) {
			String[] tmp1 = tmp[i].split(SEPARATOR_KV);
			if (tmp1.length == 2) {
				val = tmp1[1];

				if ((val != null) && (NEED_ENCODING.contains(tmp1[0]) == true)) {
					try {
						val = URLDecoder.decode(tmp1[1], DEFAULT_CHARSET);
					}
					catch (UnsupportedEncodingException e) {
						log.error("{}", e.getMessage(), e);
					}
				}

				setValue(tmp1[0], val, false);
			}
		}
	}

	private StringBuffer getKeyValue(StringBuffer buff, String key, String value) {
		if ((value != null) && (NEED_ENCODING.contains(key) == true)) {
			try {
				value = URLEncoder.encode(value, DEFAULT_CHARSET);
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return buff.append(key).append(SEPARATOR_KV).append(
				value != null ? value : "");
	}

	public String getUserId() {
		return getValue(KEY_USERID);
	}

	public String getTenantIdId() {
		return getValue(KEY_TENANTID);
	}

	public String getRightsType() {
		return getValue(KEY_RIGHTSTYPE);
	}

	public long getTimestamp() {
		String ts = getValue(KEY_TIMESTAMP);

		if (StringUtils.isEmpty(ts) == true) {
			return 0L;
		}
		else {
			return Long.parseLong(ts);
		}
	}

	public boolean isNeedCook() {
		return isNeedCook;
	}

	public void setNeedCook(boolean isNeedCook) {
		this.isNeedCook = isNeedCook;
	}

	public String getCookieKey() {
		return cookieKey;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}

	public String getValue(String key) {
		String val = null;

		val = echoCookie.get(key);

		return val == null ? "" : val;
	}

	public void setValue(String key, String value, boolean isNeedCook) {
		this.isNeedCook = isNeedCook;

		echoCookie.put(key, value);

		if (isNeedCook == true) {
			log.debug("{}, {}={}", new Object[] { cookieKey, key, value });
		}
	}

	public void setValue(String key, String value) {
		setValue(key, value, true);
	}

	public void reset() {
		isNeedCook = true;

		echoCookie.clear();

		log.debug("RESET COOKIE, " + cookieKey + ", " + toString());
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();

		getKeyValue(buff, KEY_USERID, getValue(KEY_USERID));

		buff.append(SEPARATOR_KK);
		getKeyValue(buff, KEY_USERNAME, getValue(KEY_USERNAME));

		buff.append(SEPARATOR_KK);
		getKeyValue(buff, KEY_LOGINID, getValue(KEY_LOGINID));

		buff.append(SEPARATOR_KK);
		getKeyValue(buff, KEY_RIGHTSTYPE, getValue(KEY_RIGHTSTYPE));

		buff.append(SEPARATOR_KK);
		getKeyValue(buff, KEY_TENANTID, getValue(KEY_TENANTID));

		buff.append(SEPARATOR_KK);
		getKeyValue(buff, KEY_TIMESTAMP, getValue(KEY_TIMESTAMP));

		String s = null;

		if (CommonConst.FLAG_Y.equals(IS_ENCRYPT) == true) {
			try {
				s = CryptUtil.base64EncryptDES(buff.toString(),
						CommonConst.ECHO_CRYPT_KEY);
			}
			catch (Exception e) {
				log.error("cookie={}", buff.toString(), e);
			}
		}

		if (s == null) {
			s = buff.toString();
		}

		return s;
	}

	public String toStringInLine() {
		StringBuffer buff = new StringBuffer("INLINE_START, ");
		return buff.append(
				ToStringBuilder.reflectionToString(this,
						ToStringStyle.DEFAULT_STYLE)).append(", INLINE_END")
				.toString();
	}

	public String toString4Log() {
		StringBuffer buff = new StringBuffer();

		buff.append("userId=").append(getValue(KEY_USERID));
		buff.append(", loginId=").append(getValue(KEY_LOGINID));
		buff.append(", rightType=").append(getValue(KEY_RIGHTSTYPE));
		buff.append(", tenantId=").append(getValue(KEY_TENANTID));
		buff.append(", ts=").append(getValue(KEY_TIMESTAMP));

		return buff.toString();
	}
}
