package com.echo.framework.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.echo.framework.dao.AbstractDao;

public abstract class AbstractService<T, U> {

	private static Logger log = LoggerFactory.getLogger(AbstractService.class);

	protected static final String METHOD_POST = "POST";
	protected static final String METHOD_PUT = "PUT";
	protected static final String METHOD_DELETE = "DELETE";
	protected static final String METHOD_GET = "GET";

	private AbstractDao<T> dao;
	private Class<T> domainClazz;
	private Class<U> dtoClazz;

	public AbstractService(Class<T> domain, Class<U> dto) {
		this.domainClazz = domain;
		this.dtoClazz = dto;
	}

	public void setDao(AbstractDao<T> dao) {
		this.dao = dao;
	}

	protected void validation(String method, U dto) throws Exception {

	}

	@Transactional
	public U insert(U dto, String getField, String setField) throws Exception {
		validation(METHOD_POST, dto);

		T domain = getDomain(dto);

		int insCnt = dao.insert(domain);

		Method method = domainClazz.getMethod(getField, new Class[] {});
		Object result = method.invoke(domain, new Object[] {});

		method = dtoClazz
				.getMethod(setField, new Class[] { result.getClass() });
		method.invoke(dto, new Object[] { result });

		log.debug("insCnt={}, {}", insCnt, dto);

		return dto;
	}

	@Transactional
	public int insertDto(U dto) throws Exception {
		validation(METHOD_POST, dto);

		T domain = getDomain(dto);

		int insCnt = dao.insert(domain);

		log.debug("insCnt={}, {}", insCnt, dto);

		return insCnt;
	}

	@Transactional
	public int insertDomain(T domain) throws Exception {
		int insCnt = dao.insert(domain);

		log.debug("insCnt={}, {}", insCnt, domain);

		return insCnt;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T getDomain(U dto) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Constructor constructor = domainClazz
				.getConstructor(new Class[] { dtoClazz });
		T domain = (T) constructor.newInstance(dto);

		return domain;
	}

	public T selectDto(U dto) throws Exception {
		T domain = getDomain(dto);

		log.debug("dto={}, domain={}", dto, domain);

		return (T) dao.select(domain);
	}

	public T selectDomain(T domain) throws Exception {
		log.debug("query={}, domain={}", domain, domain);

		return (T) dao.select(domain);
	}

	public int selectCnt(Map<String, Object> obj) throws Exception {
		log.debug("@@@@@002 selectCnt start");
		
		int count = dao.selectCnt(obj);

		log.debug("count={}, {}", count, obj);

		return count;
	}

	@SuppressWarnings("rawtypes")
	public List selectList(Map<String, Object> obj) throws Exception {
		return dao.selectList(obj);
	}

	@Transactional
	public int updateDto(U dto) throws Exception {
		validation(METHOD_PUT, dto);

		T domain = getDomain(dto);

		int updCnt = dao.update(domain);

		log.debug("updCnt={}, {}", updCnt, dto);

		return updCnt;
	}

	@Transactional
	public int updateDomain(T domain) throws Exception {
		int updCnt = dao.update(domain);

		log.debug("updCnt={}, {}", updCnt, domain);

		return updCnt;
	}

	@Transactional
	public int deleteDto(U dto) throws Exception {
		validation(METHOD_DELETE, dto);

		T domain = getDomain(dto);

		int delCnt = dao.delete(domain);

		log.debug("delCnt={}, {}", delCnt, dto);

		return delCnt;
	}

	@Transactional
	public int deleteDomain(T domain) throws Exception {
		int delCnt = dao.delete(domain);

		log.debug("delCnt={}, {}", delCnt, domain);

		return delCnt;
	}
}
