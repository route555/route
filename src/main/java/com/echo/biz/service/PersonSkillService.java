package com.echo.biz.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echo.biz.dao.PersonSkillDao;
import com.echo.biz.domain.PersonSkill;
import com.echo.biz.dto.PersonDto;
import com.echo.biz.dto.PersonSkillDto;
import com.echo.framework.service.AbstractService;

@Service("PersonSkillService")
public class PersonSkillService extends AbstractService<PersonSkill, PersonSkillDto> {
	private static Logger log = LoggerFactory.getLogger(PersonSkillService.class);

	@Autowired
	private PersonSkillDao personSkillDao;

	public PersonSkillService() {
		super(PersonSkill.class, PersonSkillDto.class);
	}

	@Transactional
	public int deletePersonSkillByPrsnNo(Map<String, Object> param) throws Exception {

		return personSkillDao.deletePersonSkillByPrsnNo(param);
	}
	
	
	@PostConstruct
	public void setDao() {
		setDao(personSkillDao);
	}

	protected void validation(String method, PersonDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
