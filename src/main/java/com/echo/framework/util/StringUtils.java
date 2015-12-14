package com.echo.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * <pre>
 * 기능 : String 유틸
 * 설명 : String 유틸
 * </pre>
 */
public class StringUtils {
	public static String getString(String paramString1, String paramString2) {
		return (((paramString1 == null) || (paramString1.equals(""))) ? paramString2
				: paramString1.trim());
	}

	public static String getString(String paramString) {
		return NVL(paramString, "");
	}

	public static String replace(String paramString1, String paramString2,
			String paramString3) {
		if ((paramString1 == null) || (paramString2 == null)
				|| (paramString3 == null))
			return null;
		StringBuffer localStringBuffer = new StringBuffer();
		int i = paramString2.length();
		int j = 0;
		for (int k = paramString1.indexOf(paramString2); j <= k; k = paramString1
				.indexOf(paramString2, j)) {
			localStringBuffer.append(paramString1.substring(j, k));
			localStringBuffer.append(paramString3);
			j = k + i;
		}
		localStringBuffer.append(paramString1.substring(j));
		return localStringBuffer.toString();
	}

	public static String[] split(String paramString1, String paramString2) {
		if ((paramString1 == null) || (paramString2 == null))
			return new String[0];
		StringTokenizer localStringTokenizer = new StringTokenizer(
				paramString1, paramString2);
		String[] arrayOfString = new String[localStringTokenizer.countTokens()];
		for (int i = 0; localStringTokenizer.hasMoreTokens(); ++i) {
			arrayOfString[i] = localStringTokenizer.nextToken();
		}
		return arrayOfString;
	}

	public static String[] split(String paramString) {
		return split(paramString, ",");
	}

	public static String NVL(String paramString) {
		return NVL(paramString, "");
	}

	public static String NVL(String paramString1, String paramString2) {
		return ((paramString1 == null) ? paramString2 : paramString1);
	}

	public static String byteToHex(byte[] paramArrayOfByte) {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramArrayOfByte.length; ++i)
			localStringBuffer.append(byteToHex(paramArrayOfByte[i]));
		return localStringBuffer.toString();
	}

	public static String byteToHex(byte paramByte) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(hexToChar(paramByte >>> 4 & 0xF));
		localStringBuffer.append(hexToChar(paramByte & 0xF));
		return localStringBuffer.toString();
	}

	public static char hexToChar(int paramInt) {
		if ((paramInt >= 0) && (paramInt <= 9))
			return (char) (48 + paramInt);
		return (char) (97 + paramInt - 10);
	}

	public static int getInt(String paramString) {
		return getInt(paramString, 0);
	}

	public static int getInt(String paramString, int paramInt) {
		try {
			return Integer.parseInt(paramString);
		}
		catch (Exception localException) {
		}
		return paramInt;
	}

	public static boolean getBoolean(String paramString, boolean paramBoolean) {
		try {
			return (((paramString == null) || (paramString.equals(""))) ? paramBoolean
					: Boolean.valueOf(paramString).booleanValue());
		}
		catch (Exception localException) {
		}
		return paramBoolean;
	}

	public static boolean getBoolean(String paramString) {
		boolean bool = false;
		return getBoolean(paramString, bool);
	}

	public static long getLong(String paramString) {
		return getLong(paramString, 0L);
	}

	public static long getLong(String paramString, long paramLong) {
		try {
			return Long.parseLong(paramString);
		}
		catch (Exception localException) {
		}
		return paramLong;
	}

	public static double getDouble(String paramString) {
		return getDouble(paramString, 0.0D);
	}

	public static double getDouble(String paramString, double paramDouble) {
		try {
			return Double.parseDouble(paramString);
		}
		catch (Exception localException) {
		}
		return paramDouble;
	}

	public static String getToDate(String paramString) {
		String str = "";
		if (paramString == null)
			return "";
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString, Locale.KOREA);
		Calendar localCalendar = Calendar.getInstance();
		str = localSimpleDateFormat.format(localCalendar.getTime());
		return str;
	}

	public static boolean isNumber(String paramString) {
		if (paramString == null)
			return false;
		for (int i = 0; i < paramString.length(); ++i)
			if (!(Character.isDigit(paramString.charAt(i))))
				return false;
		return true;
	}

	public static boolean isSemantic(String paramString) {
		if (paramString == null)
			return false;
		for (int i = 0; i < paramString.length(); ++i)
			if (!(Character.isWhitespace(paramString.charAt(i))))
				return true;
		return false;
	}

	public static boolean isEmpty(String paramString) {
		return (!(isSemantic(paramString)));
	}

	public static String secuValue(String paramString) {
		if (paramString == null)
			return null;
		paramString = paramString.replaceAll("\\'", "&#39;");
		paramString = paramString.replaceAll("\\\"", "&quot;");
		paramString = paramString.replaceAll("<", "&lt;");
		return paramString;
	}

	public boolean isStrInListCheck(String paramString,
			String[] paramArrayOfString) {
		if (paramString == null)
			return false;
		for (String str : paramArrayOfString)
			if (str.equals(paramString))
				return true;
		return false;
	}

	public static String convert(String[] array, String separator) {
		StringBuffer sb = new StringBuffer();

		for (String s : array) {
			sb.append(s).append(separator);
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	public static String getLpadding(Object org, String padding, int maxLen) {
		String seed = String.valueOf(org);
		int len = seed.length();

		if (maxLen < len) {
			return seed.substring(len - maxLen);
		}

		int paddingLen = (maxLen) - len;
		for (int i = 0; i < paddingLen; i++) {
			seed = padding + seed;
		}

		return seed;
	}

	public static String buildLocomoMsg(Map<String, String> map) {
		String result = new StringBuffer().append(map).toString();

		result = result.replaceAll("[{} ]", "").replaceAll("=", "\t")
				.replaceAll(",", "\n");

		return result;
	}

	public static String getStr2Array(Object org, String delim, int idx) {
		if (org != null) {
			String str = String.valueOf(org).trim();

			String[] tmp = str.split(delim);
			if ((tmp.length > 0) && (idx < tmp.length)) {
				return tmp[idx];
			}
		}

		return null;
	}

	public static String substring(String str, int sIdx, int eIdx) {
		if ((StringUtils.isEmpty(str) == true) || (str.length() < sIdx)
				|| (str.length() < eIdx)) {
			return "";
		}

		return str.substring(sIdx, eIdx);
	}
}
