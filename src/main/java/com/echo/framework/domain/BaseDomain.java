package com.echo.framework.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.echo.framework.dto.BaseDto;
import com.echo.framework.util.ExcludeNullStyle;

@SuppressWarnings("serial")
public abstract class BaseDomain implements Serializable {
	private EchoCookie echo;

	private int regUserId;
	private int updUserId;
	private Date regDateTime;
	private Date updDateTime;
	private String isDel;
	
	private Date rgtTm;
	private String rgtId;
	private Date uptTm;
	private String uptId;
	

	public BaseDomain() {

	}

	public BaseDomain(BaseDto dto) {
		//setCookieInfo(dto.getEchoCookie(), null);
	}

	public void setCookieInfo(EchoCookie echoCookie,
			String forIgnoreMybatisReflectionException /* not use */) {
		if (echoCookie != null) {
			echo = echoCookie;

			int userId = Integer.parseInt(echoCookie
					.getValue(EchoCookie.KEY_USERID));

			regUserId = userId;
			updUserId = userId;
		}
	}

	public EchoCookie getEcho() {
		return echo;
	}

	public void setEcho(EchoCookie echo) {
		this.echo = echo;
	}

	public int getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(int regUserId) {
		this.regUserId = regUserId;
	}

	public int getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(int updUserId) {
		this.updUserId = updUserId;
	}

	public Date getRegDateTime() {
		return regDateTime;
	}

	public void setRegDateTime(Date regDateTime) {
		this.regDateTime = regDateTime;
	}

	public Date getUpdDateTime() {
		return updDateTime;
	}

	public void setUpdDateTime(Date updDateTime) {
		this.updDateTime = updDateTime;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public Date getRgtTm() {
		return rgtTm;
	}

	public void setRgtTm(Date rgtTm) {
		this.rgtTm = rgtTm;
	}

	public String getRgtId() {
		return rgtId;
	}

	public void setRgtId(String rgtId) {
		this.rgtId = rgtId;
	}

	public Date getUptTm() {
		return uptTm;
	}

	public void setUptTm(Date uptTm) {
		this.uptTm = uptTm;
	}

	public String getUptId() {
		return uptId;
	}

	public void setUptId(String uptId) {
		this.uptId = uptId;
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
}
