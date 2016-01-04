package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.echo.biz.domain.SalesCntrctMgt;
import com.echo.framework.dao.AbstractDao;

@Repository("SalesCntrctMgtDao")
public class SalesCntrctMgtDao extends AbstractDao<SalesCntrctMgt>{

	public SalesCntrctMgtDao() {
		super(SalesCntrctMgt.class);
	}
	
	
	public Map<String, Object> selectSalesCntrctMgt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectOne("selectSalesCntrctMgt", params);
	}
	
	public List<Map<String, Object>> selectSalesDmndList(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectList("selectSalesDmndList", params);
	}
	
	public Map<String, Object> selectMaxCntrctCd(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectOne("selectMaxCntrctCd", params);
	}
	
	
	public int insertSalesCntrctMgt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertSalesCntrctMgt", params);
	}
	
	public int updateSalesCntrctMgt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("updateSalesCntrctMgt", params);
	}
	
	public int insertSalesCntrctHst(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertSalesCntrctHst", params);
	}
	
	public int insertSalesDmnd(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertSalesDmnd", params);
	}
	
	public int deleteSalesDmnd(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("deleteSalesDmnd", params);
	}

}
