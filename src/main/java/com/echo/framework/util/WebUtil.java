package com.echo.framework.util;

import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 공통 유틸리티
 * 
 * @author kimminsu
 * 
 */
public class WebUtil {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(WebUtil.class);

	/*
	 * See XSS Code
	 * 
	 * http://keep.egloos.com/viewer/1030642
	 */
	private static final String[][] XSS = new String[][] { { "<", "&lt;" },
			{ ">", "&gt;" } };

	/**
	 * \n을 <br>
	 * 태그로 변환
	 * 
	 * @param length
	 * @return
	 */
	public static String toTagBR(String str) {
		String toBr = "";
		if (str != null && !str.equals("")) {
			toBr = str.replaceAll("\n", "<br />");
		}
		return toBr;
	}

	public static String cleanXSS(String value) {
		if (value == null) {
			return value;
		}

		for (String[] xss : XSS) {
			String tar = xss[0];
			String rep = xss[1];

			value = value.replaceAll(tar, rep);
		}

		return value;
	}

	public static String replaceXSS(String value) {
		for (String[] xss : XSS) {
			String tar = xss[1];
			String rep = xss[0];

			if (StringUtils.isEmpty(tar) == false) {
				value = value.replaceAll(tar, rep);
			}
		}

		return value;
	}

	public static Locale getLocale(String acceptLanguage) {
		Locale locale = null;

		if (StringUtils.isEmpty(acceptLanguage) != true) {
			StringTokenizer langToken = new StringTokenizer(acceptLanguage,
					",; ");
			String language = langToken.nextToken().replace('-', '_');

			StringTokenizer localeToken = new StringTokenizer(language, "_");
			switch (localeToken.countTokens()) {
				case 1:
					locale = new Locale(localeToken.nextToken());
					break;
				case 2:
					locale = new Locale(localeToken.nextToken(), localeToken
							.nextToken());
					break;
				case 3:
					locale = new Locale(localeToken.nextToken(), localeToken
							.nextToken(), localeToken.nextToken());
					break;
			}
		}

		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}
}
