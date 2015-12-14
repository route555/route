package com.echo.framework.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.echo.framework.domain.EchoCookie;
import com.echo.framework.type.CommonConst;
import com.echo.framework.util.ExcludeNullStyle;
import com.echo.framework.util.ValidUtil.LENGTH;

@SuppressWarnings("serial")
public abstract class BaseDto implements Serializable {

	private String sessionKey;
	private EchoCookie echoCookie;
	private String isDel;
	private String isUse;

	protected static Map<String, Object[]> validMeta = new HashMap<String, Object[]>();


	public EchoCookie getEchoCookie() {
		return echoCookie;
	}

	public void setEchoCookie(EchoCookie echoCookie) {
		this.echoCookie = echoCookie;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	static {
		/*
		 * NOTE
		 * 
		 * validMeta.put(key, new Object[] { min, max, lengthType, regex,
		 * isMatches});
		 */

		/*
		 * common
		 */
		validMeta.put("isDel", new Object[] { null, null, null, CommonConst.REGEX_FLAG,
				false });
		validMeta.put("isUse", new Object[] { null, null, null, CommonConst.REGEX_FLAG,
				false });

		/*
		 * code
		 */
		validMeta.put("codeId", new Object[] { CommonConst.MIN_CODEID, CommonConst.MAX_CODEID,
				LENGTH.INT, null, null });
		validMeta.put("codeType", new Object[] { null, null, null,
				CommonConst.REGEX_CODETYPE, false });
		validMeta.put("code", new Object[] { CommonConst.MIN_CODE, CommonConst.MAX_CODE, LENGTH.STRING,
				null, null });
		validMeta.put("codeDesc", new Object[] { CommonConst.MIN_CODEDESC, CommonConst.MAX_CODEDESC,
				LENGTH.STRING, null, null });
	}

	@Override
	public String toString() {
		// return toStringMultiLine();
		return toStringInLine();
	}

	public String toStringMultiLine() {
		StringBuffer buff = new StringBuffer("MULTILINE_START, ");
		return buff.append(
				ToStringBuilder.reflectionToString(this, new ExcludeNullStyle(
						true))).append(", MULTINLINE_END").toString();
	}

	public String toStringInLine() {
		StringBuffer buff = new StringBuffer("INLINE_START, ");
		return buff.append(
				ToStringBuilder.reflectionToString(this, new ExcludeNullStyle(
						false))).append(", INLINE_END").toString();
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public static Integer getInteger(Object obj) {
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		else if ((obj instanceof String)
				&& (StringUtils.isEmpty((String) obj) == false)) {
			return Integer.parseInt((String) obj);
		}

		return null;
	}

	abstract public void validate(String method) throws Exception;
}
