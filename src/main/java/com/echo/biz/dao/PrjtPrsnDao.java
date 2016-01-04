package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.echo.biz.domain.PrjtPrsn;
import com.echo.framework.dao.AbstractDao;

@Repository("PrjtPrsnDao")
public class PrjtPrsnDao extends AbstractDao<PrjtPrsn> {
	public PrjtPrsnDao() {
		super(PrjtPrsn.class);
	}
	
	public List<Map<String, Object>> selectPrjtPrsnList(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectList("selectPrjtPrsnList", params);
	}
	
	public int insertPrjtPrsn(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertPrjtPrsn", params);
	}
	
	
	public int deletePrjtPrsn(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("deletePrjtPrsnAll", params);
	}
	
	public Map<String, Object> selectSalesTotAmt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectOne("selectSalesTotAmt", params);
	}
	
	
}
