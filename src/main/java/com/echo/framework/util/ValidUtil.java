package com.echo.framework.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.exception.EchoException;


public class ValidUtil {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ValidUtil.class);

	public static enum LENGTH {
		STRING, BYTE, INT, LONG
	}

	public static enum INVALID {
		SUCC, VALUE_NULL, ENCODE_ERR, MIN_LENGTH, MAX_LENGTH, REGEX, BROKEN_HANGUL
	}

	public static enum MANDATORY {
		TRUE, FALSE
	}

	public static INVALID isValidDetail(String value, Integer min, Integer max,
			LENGTH type, String regex, Boolean isMatches, String charSet) {
		int len;

		if (value == null) {
			return INVALID.VALUE_NULL;
		}

		if (type == LENGTH.BYTE) {
			try {
				len = value.getBytes(charSet).length;
			}
			catch (UnsupportedEncodingException e) {
				return INVALID.ENCODE_ERR;
			}
		}
		else {
			len = value.length();
		}

		if ((min != null) && (min != -1) && (len < min)) {
			return INVALID.MIN_LENGTH;
		}
		else if ((max != null) && (max != -1) && (len > max)) {
			return INVALID.MAX_LENGTH;
		}
		else if ((regex != null) && (value.matches(regex) == isMatches)) {
			return INVALID.REGEX;
		}

		return INVALID.SUCC;
	}

	public static INVALID isValidDetail(Integer value, Integer min, Integer max) {
		if (value == null) {
			return INVALID.VALUE_NULL;
		}

		if (value < min) {
			return INVALID.MIN_LENGTH;
		}
		else if (value > max) {
			return INVALID.MAX_LENGTH;
		}

		return INVALID.SUCC;
	}

	public static INVALID isValidDetail(Long value, Long min, Long max) {
		if (value == null) {
			return INVALID.VALUE_NULL;
		}

		if (value < min) {
			return INVALID.MIN_LENGTH;
		}
		else if (value > max) {
			return INVALID.MAX_LENGTH;
		}

		return INVALID.SUCC;
	}

	public static boolean isValid(Integer value, Integer min, Integer max) {
		return isValidDetail(value, min, max) == INVALID.SUCC ? true : false;
	}

	public static INVALID isBrokenHangul(String value) {
		INVALID invalid = INVALID.SUCC;

		if (StringUtils.isEmpty(value) == true) {
			invalid = INVALID.VALUE_NULL;
		}
		else {
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);

				if (c > 0x001f && c < 0x007f) {
					/* nothing todo, because can print as character */
				}
				else if (c < 0xAC00 || c > 0xD7A3) {
					invalid = INVALID.BROKEN_HANGUL;
					break;
				}
			}
		}

		return invalid;
	}

	public static void isBrokenHangul(String key, String value,
			MANDATORY isMandatory) {
		INVALID invalid = isBrokenHangul(value);

		if ((invalid != INVALID.SUCC)
				&& (isMandatory == MANDATORY.TRUE)) {
			throw new EchoException(HttpServletResponse.SC_BAD_REQUEST,
					new Object[] { "error.param.broken.hangul", key, value });
		}
	}

	public static int getByteSizeWithHangul(String value) {
		int byteSize = 0;

		char[] chStr = value.toCharArray();

		for (int i = 0; i < chStr.length; i++) {
			if (chStr[i] >= 'A' && chStr[i] <= 'Z')
				byteSize += 1;
			else if (chStr[i] >= '\uAC00' && chStr[i] <= '\uD7A3')
				byteSize += 2;
			else
				byteSize += 1;
		}

		return byteSize;
	}

	public static void isValid(Map<String, Object[]> validMeta,
			Map<String, Object[]> valueMap) {
		for (String key : valueMap.keySet()) {
			/*
			 * set valid meta
			 */
			Object[] validInfo = validMeta.get(key);
			if ((validInfo == null) || (validInfo.length < 5)
					|| (validInfo.length > 6)) {
				throw new EchoException(HttpServletResponse.SC_BAD_REQUEST,
						new Object[] { "error.param.valid.info", key });
			}

			Object min = validInfo[0];
			Object max = validInfo[1];
			LENGTH lengthType = (LENGTH) validInfo[2];
			String regex = (String) validInfo[3];
			Boolean isMatches = (Boolean) validInfo[4];
			String charSet = "utf-8";
			if (validInfo.length == 6)
				charSet = (String) validInfo[5];

			/*
			 * set value
			 */
			Object[] valueInfo = valueMap.get(key);
			Object value = valueInfo[0];
			MANDATORY isMandatory = (MANDATORY) valueInfo[1];

			INVALID invalid = null;

			if (((value == null) || ((value instanceof String) && (((String) value)
					.length() == 0)))
					&& (isMandatory != MANDATORY.TRUE)) {
				/* nothing to to */
			}
			else if ((value == null) && (isMandatory == MANDATORY.TRUE)) {
				throw new EchoException(HttpServletResponse.SC_BAD_REQUEST,
						new Object[] { "error.param.value.null", key });
			}
			else if (lengthType == LENGTH.INT) {
				if ((invalid = ValidUtil.isValidDetail((Integer) value,
						(Integer) min, (Integer) max)) != INVALID.SUCC) {
					String msgId = null;

					if (invalid == INVALID.MIN_LENGTH) {
						msgId = "error.param.value.min";
					}
					else if (invalid == INVALID.MAX_LENGTH) {
						msgId = "error.param.value.max";
					}

					throw new EchoException(
							HttpServletResponse.SC_BAD_REQUEST, new Object[] {
									msgId, key, value, min, max });
				}
			}
			else if (lengthType == LENGTH.LONG) {
				if ((invalid = ValidUtil.isValidDetail((Long) value,
						(Long) min, (Long) max)) != INVALID.SUCC) {
					String msgId = null;

					if (invalid == INVALID.MIN_LENGTH) {
						msgId = "error.param.value.min";
					}
					else if (invalid == INVALID.MAX_LENGTH) {
						msgId = "error.param.value.max";
					}

					throw new EchoException(
							HttpServletResponse.SC_BAD_REQUEST, new Object[] {
									msgId, key, value, min, max });
				}
			}
			else {
				if (((invalid = ValidUtil.isValidDetail((String) value,
						(Integer) min, (Integer) max, lengthType, regex,
						isMatches, charSet)) != null)
						&& (invalid != INVALID.SUCC)) {
					int len = 0;
					String val = "";

					if ((invalid == INVALID.MIN_LENGTH)
							|| (invalid == INVALID.MAX_LENGTH)) {
						if (lengthType == LENGTH.BYTE) {
							try {
								len = ((String) value).getBytes(charSet).length;
							}
							catch (UnsupportedEncodingException e) {
							}
						}
						else {
							len = ((String) value).length();
						}

						val = ", lengthType=" + lengthType + ", val(" + len
								+ ")=" + value;
					}
					else {
						val = ", val=" + value;
					}

					String msgId = null;

					if (invalid == INVALID.MIN_LENGTH) {
						msgId = "error.param.value.min";
					}
					else if (invalid == INVALID.MAX_LENGTH) {
						msgId = "error.param.value.max";
					}
					else if (invalid == INVALID.ENCODE_ERR) {
						msgId = "error.param.value.encode";
					}
					else if (invalid == INVALID.REGEX) {
						msgId = "error.param.value.regex." + key;
					}

					throw new EchoException(
							HttpServletResponse.SC_BAD_REQUEST, new Object[] {
									msgId, key, val, min, max, regex,
									isMatches });
				}
			} /* end of if */

			// log.debug("key={}, invalid={}, value={}, isMandatory={}"
			// + ", min={}, max={}, lengthType={}"
			// + ", regex={}, isMatches={}",
			// new Object[] { key, invalid, value, isMandatory, min, max,
			// lengthType, regex, isMatches });

		} /* end of for (String key : valueMap.keySet()) */
	}
}
