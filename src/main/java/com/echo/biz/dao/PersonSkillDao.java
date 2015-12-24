package com.echo.biz.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.PersonSkill;
import com.echo.framework.dao.AbstractDao;

@Repository("PersonSkillDao")
public class PersonSkillDao extends AbstractDao<PersonSkill> {
	public PersonSkillDao() {
		super(PersonSkill.class);
	}

	
	public int deletePersonSkillByPrsnNo(Map<String, Object> param ) {
		return echoSlave.delete("personskill.deletePersonSkillByPrsnNo", param);
	}
	
}
