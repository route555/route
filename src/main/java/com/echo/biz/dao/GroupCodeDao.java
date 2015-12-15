package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.GroupCode;
import com.echo.framework.dao.AbstractDao;

@SuppressWarnings("rawtypes")
@Repository("GroupCodeDao")
public class GroupCodeDao extends AbstractDao<GroupCode> {
	public GroupCodeDao() {
		super(GroupCode.class);
	}

	public List selectListCommonCode(Map<String, Object> param) {
		return echoSlave.selectList("groupcode.selectListCommonCode", param);
	}
}
