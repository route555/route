package com.echo.framework.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.framework.dao.CodeDao;
import com.echo.framework.domain.Code;
import com.echo.framework.dto.CodeDto;

@Service("CodeService")
public class CodeService extends AbstractService<Code, CodeDto> {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(CodeService.class);

	@Autowired
	private CodeDao codeDao;

	public CodeService() {
		super(Code.class, CodeDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(codeDao);
	}
}