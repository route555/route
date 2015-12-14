package com.echo.framework.type;

public enum CtrlType {
	REBOOT("CT000001"),

	RESET_CONF("CT000002"),

	VNC_ON("CT000003"),

	VNC_OFF("CT000004"),

	DISPLAY_ON("CT000005"),

	DISPLAY_OFF("CT000006"),

	VOLUME_SET("CT000007"),

	UPDATE_VERSION("CT000008"),

	REGIST_SCHEDULE("CT000009"),

	CONFIRM_AUTH("CT000010"),

	POWER_OFF("CT000011"),

	MODIFY_SCHEDULE("CT000012"),

	DELETE_SCHEDULE("CT000013"),

	;

	private String code;

	private CtrlType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}