package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.PurchasePayment;
import com.echo.framework.dao.AbstractDao;

@Repository("PurchasePaymentDao")
public class PurchasePaymentDao extends AbstractDao<PurchasePayment> {
	public PurchasePaymentDao() {
		super(PurchasePayment.class);
	}

	
}
