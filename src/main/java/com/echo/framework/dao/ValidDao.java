package com.echo.framework.dao;

import org.springframework.stereotype.Repository;

import com.echo.framework.domain.Valid;

@Repository
public class ValidDao extends AbstractDao<Valid> {
	public ValidDao() {
		super(Valid.class);
	}
}
