package com.echo.framework.type;

public enum FaultState {
	OPEN("O"),

	CLOSE("C"),

	;

	private String code;

	private FaultState(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}