package com.echo.framework.exception;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.echo.framework.util.ExcludeNullStyle;

@SuppressWarnings("serial")
public class EchoException extends RuntimeException {
	private int code;
	private Object[] msgSrcParams;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object[] getMsgSrcParams() {
		return msgSrcParams;
	}

	public void setMsgSrcParams(Object[] msgSrcParams) {
		this.msgSrcParams = msgSrcParams.clone();
	}

	public EchoException(int code, Object[] msgSrcParams, Exception e) {
		super(ToStringBuilder.reflectionToString(msgSrcParams,
				new ExcludeNullStyle(false)), e);

		this.code = code;
		this.msgSrcParams = msgSrcParams.clone();
	}

	public EchoException(int code, Object[] msgSrcParams) {
		super(ToStringBuilder.reflectionToString(msgSrcParams,
				new ExcludeNullStyle(false)));

		this.code = code;
		this.msgSrcParams = msgSrcParams.clone();
	}
}
