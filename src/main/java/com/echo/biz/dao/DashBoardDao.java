package com.echo.biz.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.framework.dao.AbstractDao;

@Repository("DashBoardDao")
public class DashBoardDao extends AbstractDao<Object> {
	public DashBoardDao() {
		super(Object.class);
	}

	
	public Map<String, Object> selectPrjt() {
		return echoSlave.selectOne("dashboard.selectPrjt");
	}
	
	public Map<String, Object> selectSaleCont() {
		return echoSlave.selectOne("dashboard.selectSaleCont");
	}
	
	public Map<String, Object> selectPurchaseCont() {
		return echoSlave.selectOne("dashboard.selectPurchaseCont");
	}
	
	public Map<String, Object> selectSaleMgt() {
		return echoSlave.selectOne("dashboard.selectSaleMgt");
	}
	
	public Map<String, Object> selectPurchaseMgt() {
		return echoSlave.selectOne("dashboard.selectPurchaseMgt");
	}
	
}
