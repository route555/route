package com.echo.biz.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.DetailCode;
import com.echo.framework.dao.AbstractDao;

@Repository("DetailCodeDao")
public class DetailCodeDao extends AbstractDao<DetailCode> {
	public DetailCodeDao() {
		super(DetailCode.class);
	}

	public int deleteDetailCodeByGrpCd(Map<String, Object> param ) {
		return echoSlave.delete("detailcode.deleteDetailCodeByGrpCd", param);
	}
	
}
