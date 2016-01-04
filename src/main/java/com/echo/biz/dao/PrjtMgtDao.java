package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.echo.biz.domain.PrjtMgt;
import com.echo.framework.dao.AbstractDao;

@Repository("PrjtMgtDao")
public class PrjtMgtDao  extends AbstractDao<PrjtMgt>{

	public PrjtMgtDao() {
		super(PrjtMgt.class);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> selectPrjtMgt(Map params) throws DataAccessException {
		return echoSlave.selectOne("selectPrjtMgt", params);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPrjtChgrList1(Map params) throws DataAccessException {
		return echoSlave.selectList("selectPrjtChgrList1", params);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPrjtChgrList2(Map params) throws DataAccessException {
		return echoSlave.selectList("selectPrjtChgrList2", params);
	}
	
	public int insertPrjt(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.insert("insertPrjt", params);
	}
	
	public int updatePrjt(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.update("updatePrjt", params);
	}
	
	public int deletePrjt(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.delete("deletePrjt", params);
	}
	
	public int updatePrjtCfrm(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.update("updatePrjtCfrm", params);
	}
	
	public int updatePrjtCfrmCancel(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.update("updatePrjtCfrmCancel", params);
	}
	
	public int updatePrjtEnd(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.update("updatePrjtEnd", params);
	}
	
	public int updatePrjtEndCancel(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.update("updatePrjtEndCancel", params);
	}
	
	
	public int deletePrjtPrsn(@SuppressWarnings("rawtypes") Map params) throws DataAccessException {
		return echoMaster.delete("deletePrjtPrsn", params);
	}
}
