package com.echo.framework.type;

public enum DeliveryType {
	ADD_SCHEDULE("DT000001"),

	RESET_SCHEDULE("DT000002");

	private String code;

	private DeliveryType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}