package com.echo.framework.type;

public enum OpCodeType {
	SCENARIO_START("OP000001"),

	SCENARIO_STOP("OP000002"),

	SCENARIO_TOUCH("OP000003"),

	DEVICE_WAKEUP("OP000004"),

	DEVICE_SLEEP("OP000005"),

	;

	private String code;

	private OpCodeType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}