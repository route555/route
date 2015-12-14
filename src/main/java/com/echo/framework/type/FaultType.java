package com.echo.framework.type;

public enum FaultType {
	DEVICE_EVENT("FT000001"),

	DEVICE_MON("FT000002"),

	CTRL_HISTORY("FT000003"), ;

	private String code;

	private FaultType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}