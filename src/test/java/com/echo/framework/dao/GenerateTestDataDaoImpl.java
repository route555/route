package com.echo.framework.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class GenerateTestDataDaoImpl extends BaseDao implements
		GenerateTestDataDao {
	@Override
	public int insertBusiness(Map<String, Object> map) {
		return echoMaster.insert("generatetestdata.insertBusiness", map);
	}

	@Override
	public int insertUser(Map<String, Object> map) {
		return echoMaster.insert("generatetestdata.insertUser", map);
	}

	@Override
	public int insertDevice(Map<String, Object> map) {
		return echoMaster.insert("generatetestdata.insertDevice", map);
	}

	@Override
	public int insertCtrlHistory(Map<String, Object> map) {
		return echoMaster.insert("generatetestdata.insertCtrlHistory", map);
	}

	@Override
	public int updateBusiness(Map<String, Object> map) {
		return echoMaster.update("generatetestdata.updateBusiness", map);
	}

	@Override
	public int updateUser(Map<String, Object> map) {
		return echoMaster.update("generatetestdata.updateUser", map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map selectUser(long userId) {
		return echoSlave.selectOne("generatetestdata.selectUser", userId);
	}
}
