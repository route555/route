package com.echo.framework.domain;

import com.echo.framework.dto.BaseDto;

public class Valid extends BaseDomain {
	private static final long serialVersionUID = -8545473645233041531L;

	public Valid() {

	}

	public Valid(BaseDto dto) {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);
	}
}
