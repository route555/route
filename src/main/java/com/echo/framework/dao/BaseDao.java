package com.echo.framework.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sqlSessionEchoMaster")
	protected SqlSessionTemplate echoMaster;

	@Autowired
	@Qualifier("sqlSessionEchoSlave")
	protected SqlSessionTemplate echoSlave;
}
