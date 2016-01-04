package com.echo.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.echo.framework.dao.AbstractDao;

@Repository("SaleManagementDao")
public class SaleManagementDao extends AbstractDao<Object> {
	public SaleManagementDao() {
		super(Object.class);
	}

	public List selectListSaleManagement(Map<String, Object> param) {
		return echoSlave.selectList("salemanagement.selectListSaleManagement", param);
	}
	
	public int selectCntSaleManagement(Map<String, Object> param) {
		return echoSlave.selectOne("salemanagement.selectCntSaleManagement", param);
	}
}
