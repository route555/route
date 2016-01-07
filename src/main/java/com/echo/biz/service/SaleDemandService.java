package com.echo.biz.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.SaleDemandDao;
import com.echo.biz.domain.SaleDemand;
import com.echo.biz.dto.SaleDemandDto;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.service.AbstractService;

@Service("SaleDemandService")
public class SaleDemandService extends AbstractService<SaleDemand, SaleDemandDto> {
	private static Logger log = LoggerFactory.getLogger(SaleDemandService.class);

	@Autowired
	private SaleDemandDao saleDemandDao;

	public SaleDemandService() {
		super(SaleDemand.class, SaleDemandDto.class);
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
