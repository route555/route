package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.SalePurchaseHistory;
import com.echo.framework.dao.AbstractDao;

@Repository("SalePurchaseHistoryDao")
public class SalePurchaseHistoryDao extends AbstractDao<SalePurchaseHistory> {
	public SalePurchaseHistoryDao() {
		super(SalePurchaseHistory.class);
	}

	
}
