package com.echo.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface IDao<T> {
	public int insert(T obj) throws DataAccessException;

	public T select(T obj) throws DataAccessException;

	@SuppressWarnings("rawtypes")
	public int selectCnt(Map params) throws DataAccessException;

	@SuppressWarnings("rawtypes")
	public List selectList(Map params) throws DataAccessException;

	public int update(T obj) throws DataAccessException;

	public int delete(T obj) throws DataAccessException;
}
