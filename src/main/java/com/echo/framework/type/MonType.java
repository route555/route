package com.echo.framework.type;

public enum MonType {
	RESOURCE("MON00001"),

	SCHEDULE("MON00002"),

	;

	private String code;

	private MonType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}