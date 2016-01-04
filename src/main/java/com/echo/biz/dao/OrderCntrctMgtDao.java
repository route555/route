package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.echo.biz.domain.OrderCntrctMgt;
import com.echo.framework.dao.AbstractDao;

@Repository("OrderCntrctMgtDao")
public class OrderCntrctMgtDao extends AbstractDao<OrderCntrctMgt> {
	public OrderCntrctMgtDao() {
		super(OrderCntrctMgt.class);
	}
	
	public Map<String, Object> selectOrderCntrctMgt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectOne("selectOrderCntrctMgt", params);
	}
	
	public List<Map<String, Object>> selectOrderDpstList(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectList("selectOrderDpstList", params);
	}
	
	public Map<String, Object> selectMaxOrderCntrctCd(Map<String, Object> params) throws DataAccessException {
		return echoSlave.selectOne("selectMaxOrderCntrctCd", params);
	}
	
	
	public int insertOrderCntrctMgt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertOrderCntrctMgt", params);
	}
	
	public int updateOrderCntrctMgt(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("updateOrderCntrctMgt", params);
	}
	
	public int insertOrderCntrctHst(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertOrderCntrctHst", params);
	}
	
	public int insertOrderDpst(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("insertOrderDpst", params);
	}
	
	public int deleteOrderDpst(Map<String, Object> params) throws DataAccessException {
		return echoSlave.insert("deleteOrderDpst", params);
	}
}
