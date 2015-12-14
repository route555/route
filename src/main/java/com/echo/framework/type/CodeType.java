package com.echo.framework.type;

public enum CodeType {
	ORIENTATION("CODE0001"),

	DEVICE_TYPE("CODE0002"),
	
	CONTENTS_TYPE("CODE0009"),

	RIGHTS_TYPE("CODE0008"),

	;

	private String code;

	private CodeType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}