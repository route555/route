package com.echo.framework.type;

public enum RightsType {
	SYSTEM_ADMIN("RT000011"),

	OPERATOR("RT000012"),

	USER("RT000013"),

	;

	private String code;

	private RightsType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}