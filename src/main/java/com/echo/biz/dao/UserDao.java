package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.User;
import com.echo.framework.dao.AbstractDao;

@Repository("UserDao")
public class UserDao extends AbstractDao<User> {
	public UserDao() {
		super(User.class);
	}

	/*
	public List selectListCommonCode(Map<String, Object> param) {
		return echoSlave.selectList("groupcode.selectListCommonCode", param);
	}
	*/
}
