package com.echo.biz.service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.SampleHumanDao;
import com.echo.biz.domain.SampleHuman;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.dto.BaseDto;
import com.echo.framework.service.AbstractService;

@Service("SampleHumanService")
public class SampleHumanService extends AbstractService<SampleHuman, SampleHumanDto> {
	private static Logger log = LoggerFactory.getLogger(SampleHumanService.class);

	@Autowired
	private SampleHumanDao sampleHumanDao;

	public SampleHumanService() {
		super(SampleHuman.class, SampleHumanDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(sampleHumanDao);
	}

	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {
			
		}
	}
}
