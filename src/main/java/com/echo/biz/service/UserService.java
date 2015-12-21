package com.echo.biz.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.UserDao;
import com.echo.biz.domain.User;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.biz.dto.UserDto;
import com.echo.framework.service.AbstractService;

@Service("UserService")
public class UserService extends AbstractService<User, UserDto> {
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	public UserService() {
		super(User.class, UserDto.class);
	}

	/*
	public Object selectListCommonCode(String grpCd) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("grpCd", grpCd);

		log.debug("#$#$#" + param);
		List<Map<String, Object>> list = groupCodeDao.selectListCommonCode(param);

		return list;
	}
	*/

	@PostConstruct
	public void setDao() {
		setDao(userDao);
	}

	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
