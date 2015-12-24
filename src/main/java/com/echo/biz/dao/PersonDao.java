package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.Person;
import com.echo.framework.dao.AbstractDao;

@Repository("PersonDao")
public class PersonDao extends AbstractDao<Person> {
	public PersonDao() {
		super(Person.class);
	}

	/*
	public List selectListCommonCode(Map<String, Object> param) {
		return echoSlave.selectList("groupcode.selectListCommonCode", param);
	}
	*/
}
