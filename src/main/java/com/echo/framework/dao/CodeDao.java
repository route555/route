package com.echo.framework.dao;

import org.springframework.stereotype.Repository;

import com.echo.framework.domain.Code;

@Repository("CodeDao")
public class CodeDao extends AbstractDao<Code> {
	public CodeDao() {
		super(Code.class);
	}
}
