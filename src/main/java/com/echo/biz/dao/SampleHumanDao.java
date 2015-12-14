package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.SampleHuman;
import com.echo.framework.dao.AbstractDao;

@Repository("SampleHumanDao")
public class SampleHumanDao extends AbstractDao<SampleHuman> {
	public SampleHumanDao() {
		super(SampleHuman.class);
	}

	
}
