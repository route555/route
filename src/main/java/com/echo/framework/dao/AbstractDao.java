package com.echo.framework.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

@SuppressWarnings("rawtypes")
public abstract class AbstractDao<T> extends BaseDao implements IDao<T> {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(AbstractDao.class);
	private String namespace = null;
	private Class<T> type;

	public static final String PREFIX_INSERT_QUERY = "insert";
	public static final String PREFIX_SELECT_QUERY = "select";
	public static final String PREFIX_SELECT_CNT_QUERY = "selectCnt";
	public static final String PREFIX_SELECT_LIST_QUERY = "selectList";
	public static final String PREFIX_UPDATE_QUERY = "update";
	public static final String PREFIX_DELETE_QUERY = "delete";

	public AbstractDao(Class<T> type) {
		this.type = type;
		this.namespace = this.type.getSimpleName().toLowerCase();
	}

	@Override
	public int insert(T obj) throws DataAccessException {
		return echoMaster.insert(getQueryId(PREFIX_INSERT_QUERY), obj);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T select(T obj) throws DataAccessException {
		return (T) echoSlave.selectOne(getQueryId(PREFIX_SELECT_QUERY), obj);
	}

	@Override
	public int selectCnt(Map params) throws DataAccessException {
		return echoSlave.selectOne(getQueryId(PREFIX_SELECT_CNT_QUERY),
				params);
	}

	@Override
	public List selectList(Map params) throws DataAccessException {
		return echoSlave.selectList(getQueryId(PREFIX_SELECT_LIST_QUERY),
				params);
	}

	@Override
	public int update(T obj) throws DataAccessException {
		return echoMaster.update(getQueryId(PREFIX_UPDATE_QUERY), obj);
	}

	@Override
	public int delete(T obj) throws DataAccessException {
		return echoMaster.delete(getQueryId(PREFIX_DELETE_QUERY), obj);
	}

	private String getQueryId(String queryType) {
		return this.namespace + "." + queryType + this.type.getSimpleName();
	}
}
