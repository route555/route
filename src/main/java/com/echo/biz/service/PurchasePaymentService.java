package com.echo.biz.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.PurchasePaymentDao;
import com.echo.biz.domain.PurchasePayment;
import com.echo.biz.dto.PurchasePaymentDto;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.service.AbstractService;

@Service("PurchasePaymentService")
public class PurchasePaymentService extends AbstractService<PurchasePayment, PurchasePaymentDto> {
	private static Logger log = LoggerFactory.getLogger(PurchasePaymentService.class);

	@Autowired
	private PurchasePaymentDao purchasePaymentDao;

	public PurchasePaymentService() {
		super(PurchasePayment.class, PurchasePaymentDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(purchasePaymentDao);
	}

	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
