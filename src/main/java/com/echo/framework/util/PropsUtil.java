package com.echo.framework.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {
	private static Logger log = LoggerFactory.getLogger(PropsUtil.class);

	private static ResourceBundle res = null;

	static {
		try {
			res = ResourceBundle.getBundle("application-context");
		}
		catch (Exception e) {
			log.error("ResourceBundle.getBundle(), e={}", e.getMessage(), e);
		}
	}

	public static void setProperty(String property) {
		res = ResourceBundle.getBundle(property);
	}

	public static String getValue(String key) {
		return res.getString(key);
	}

	public static String getValue(String key, String key4win) {
		String os = System.getProperty("os.name");

		if ((os != null) && (os.indexOf("Win") != -1)) {
			return res.getString(key4win);
		}
		else {
			return res.getString(key);
		}
	}

	public static int getIntValue(String key) {
		return Integer.parseInt(getValue(key));
	}
}
