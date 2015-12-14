package com.echo.framework.type;

public enum ScheduleType {
	ROLLING("SCH00001"),

	NESTED("SCH00002"),

	EMERGENCY("SCH00003"),

	TIME("SCH00004"),

	SUBTITLE("SCH00005")

	;

	private String code;

	private ScheduleType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}