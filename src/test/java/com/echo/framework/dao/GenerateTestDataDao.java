package com.echo.framework.dao;

import java.util.Map;

public interface GenerateTestDataDao {
	public int insertBusiness(Map<String, Object> map);

	public int insertUser(Map<String, Object> map);

	public int insertDevice(Map<String, Object> map);

	public int insertCtrlHistory(Map<String, Object> map);

	public int updateBusiness(Map<String, Object> map);

	public int updateUser(Map<String, Object> map);

	@SuppressWarnings("rawtypes")
	public Map selectUser(long userId);
}