package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.echo.biz.domain.TrAcctMgt;
import com.echo.framework.dao.AbstractDao;

@Repository("TrAcctMgtDao")
public class TrAcctMgtDao extends AbstractDao<TrAcctMgt>{

	public TrAcctMgtDao() {
		super(TrAcctMgt.class);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> selectTrAcctMgt(Map params) throws DataAccessException {
		return echoSlave.selectOne("selectTrAcctMgt", params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectTracctChgrList(Map params) throws DataAccessException {
		return echoSlave.selectList("selectTracctChgrList", params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectTracctChgrCdList(Map params) throws DataAccessException {
		return echoSlave.selectList("selectTracctChgrCdList", params);
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> selectMaxTrAcctCd(Map params) throws DataAccessException {
		return echoSlave.selectOne("selectMaxTrAcctCd", params);
	}
	
	public int insertTrAcct(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.insert("insertTrAcct", params);
	}
	
	public int updateTrAcct(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.update("updateTrAcct", params);
	}
	
	public int deleteTrAcct(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.delete("deleteTrAcct", params);
	}
	
	public int insertTrAcctChgr(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.insert("insertTrAcctChgr", params);
	}
	
	public int deleteTrAcctChgr(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.delete("deleteTrAcctChgr", params);
	}
	
	
}
