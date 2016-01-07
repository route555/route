package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.framework.dao.AbstractDao;

@Repository("PurchaseManagementDao")
public class PurchaseManagementDao extends AbstractDao<Object> {
	public PurchaseManagementDao() {
		super(Object.class);
	}

	public List selectListPurchaseManagement(Map<String, Object> param) {
		return echoSlave.selectList("purchasemanagement.selectListPurchaseManagement", param);
	}
	
	public int selectCntPurchaseManagement(Map<String, Object> param) {
		return echoSlave.selectOne("purchasemanagement.selectCntPurchaseManagement", param);
	}
}
