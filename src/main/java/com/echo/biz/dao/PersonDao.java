package com.echo.biz.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.Person;
import com.echo.framework.dao.AbstractDao;

@Repository("PersonDao")
public class PersonDao extends AbstractDao<Person> {
	public PersonDao() {
		super(Person.class);
	}

	
	public Map<String, Object> selectPersonLastPrjt(Map<String, Object> param) {
		return echoSlave.selectOne("person.selectPersonLastPrjt", param);
	}
	
}
