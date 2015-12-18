package com.echo.biz.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echo.biz.dao.DetailCodeDao;
import com.echo.biz.domain.DetailCode;
import com.echo.biz.dto.DetailCodeDto;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.service.AbstractService;

@Service("DetailCodeService")
public class DetailCodeService extends AbstractService<DetailCode, DetailCodeDto> {
	private static Logger log = LoggerFactory.getLogger(DetailCodeService.class);

	@Autowired
	private DetailCodeDao detailCodeDao;

	public DetailCodeService() {
		super(DetailCode.class, DetailCodeDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(detailCodeDao);
	}

	@Transactional
	public int deleteDetailCodeByGrpCd(Map<String, Object> param) throws Exception {

		return detailCodeDao.deleteDetailCodeByGrpCd(param);
	}
	
	
	
	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
