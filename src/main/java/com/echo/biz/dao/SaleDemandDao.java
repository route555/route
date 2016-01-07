package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.SaleDemand;
import com.echo.framework.dao.AbstractDao;

@Repository("SaleDemandDao")
public class SaleDemandDao extends AbstractDao<SaleDemand> {
	public SaleDemandDao() {
		super(SaleDemand.class);
	}

	
}
