package com.echo.framework.type;

public enum DeliveryState {
	NOT_DELIVERY("N"),

	SCHEDULE_DOWNLOADED("S"),

	CONTENTS_DOWNLOADED("C"),
	
	SCHEDULE_FINISH("F"),

	;

	private String code;

	private DeliveryState(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}