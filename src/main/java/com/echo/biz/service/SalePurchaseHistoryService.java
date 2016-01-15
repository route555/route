package com.echo.biz.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.SalePurchaseHistoryDao;
import com.echo.biz.domain.SalePurchaseHistory;
import com.echo.biz.dto.SalePurchaseHistoryDto;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.service.AbstractService;

@Service("SalePurchaseHistoryService")
public class SalePurchaseHistoryService extends AbstractService<SalePurchaseHistory, SalePurchaseHistoryDto> {
	private static Logger log = LoggerFactory.getLogger(SalePurchaseHistoryService.class);

	@Autowired
	private SalePurchaseHistoryDao saleDemandDao;

	public SalePurchaseHistoryService() {
		super(SalePurchaseHistory.class, SalePurchaseHistoryDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(saleDemandDao);
	}

	
	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
