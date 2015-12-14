package com.echo.framework.type;

public class CommonConst {
	public static final String SESSION_KEY = "sessionKey";

	public static final String FLAG_Y = "Y";
	public static final String FLAG_N = "N";
	public static final String FLAG_F = "F"; /* for ad stop */
	public static final String DEFAULT_SEPARATOR = ",";
	public static final String VERSION_SEPARATOR = "[.]";

	public static final String REGEX_FLAG = "[YN]";
	public static final String REGEX_SPECIAL_CHARS = ".*[\\s~!@#$%^&*()_+`\\-={}|\\[\\]:\\\\\";'<>?,./].*";

	public static final String REGEX_DATETIME_FORMAT = "[1-2][0-9][1-9][0-9]-[01][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
	public static final String REGEX_DATE_FORMAT = "2[0-1][1-9][0-9][01][0-9][0-3][0-9]";
	public static final String REGEX_DATE_FORMAT_YYYYMM = "2[0-1][1-9][0-9][01][0-9]";
	public static final String REGEX_TIME_FORMAT = "[0-2][0-9][0-5][0-9][0-5][0-9]";

	public static final String KEY_ORDER_ELEMENT = "orderElement";
	public static final String KEY_ORDER_TYPE = "orderType";

	public static final int MIN_DEFAULT_INT = 0;
	public static final int MAX_DEFAULT_INT = Integer.MAX_VALUE;



	/*
	 * code
	 */
	public static final int MIN_CODEID = 0;
	public static final int MAX_CODEID = Integer.MAX_VALUE;

	public static final int MIN_CODE = 1;
	public static final int MAX_CODE = 8;

	public static final int MIN_CODEDESC = 0;
	public static final int MAX_CODEDESC = 200;

	public static final String REGEX_CODETYPE = "CODE[0-9]{4}";

	public static final String REGEX_IP = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
			+ "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	
	
	
	public static final byte ECHO_CRYPT_KEY[] = { 1, 8, 12, 11, 80, 38, 12, 67 };
	public static final String COOKIE_KEY = "echo";
}
