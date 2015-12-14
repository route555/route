package com.echo.framework.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.echo.framework.domain.Auth;

@SuppressWarnings("rawtypes")
@Repository
public class AuthDao extends AbstractDao<Auth> {
	private static Logger log = LoggerFactory.getLogger(AuthDao.class);
	
	public AuthDao() {
		super(Auth.class);
	}

	public Auth selectUserAuth(Auth auth) {
		return echoSlave.selectOne("auth.selectUserAuthInfo", auth);
	}

	public Auth selectDeviceAuth(Auth auth) {
		return echoSlave.selectOne("auth.selectDeviceAuthInfo", auth);
	}

	public List selectSecurityInfo() {
		log.debug("@@@@@001 selectSecurityInfo start");
		return echoSlave.selectList("auth.selectSecurityInfo");
	}
}
