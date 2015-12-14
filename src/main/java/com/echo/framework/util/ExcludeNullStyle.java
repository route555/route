package com.echo.framework.util;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExcludeNullStyle extends ToStringStyle {
	private static final long serialVersionUID = -9063020414810145186L;

	public ExcludeNullStyle(boolean isMultiLine) {
		super();

		this.setUseShortClassName(true);
		this.setUseIdentityHashCode(false);

		if (isMultiLine == true) {
			this.setContentStart("[");
			this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
			this.setFieldSeparatorAtStart(true);
			this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
		}
	}

	public void append(StringBuffer buffer, String fieldName, Object value,
			Boolean fullDetail) {
		if (value == null) {
			return;
		}

		appendFieldStart(buffer, fieldName);

		appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));

		appendFieldEnd(buffer, fieldName);
	}
}
