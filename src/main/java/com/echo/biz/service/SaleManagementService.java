package com.echo.biz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.SaleManagementDao;
import com.echo.biz.domain.Dummy;
import com.echo.biz.dto.DummyDto;
import com.echo.framework.service.AbstractService;

@Service("SaleManagementService")
public class SaleManagementService extends AbstractService<Object, Object> {
	private static Logger log = LoggerFactory.getLogger(SaleManagementService.class);

	@Autowired
	private SaleManagementDao saleManagementDao;

	public SaleManagementService() {
		super(Object.class, Object.class);
	}
	
	
	public Object selectListSaleManagement(Map<String, Object> param) throws Exception {

		//Map<String, Object> param = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = saleManagementDao.selectListSaleManagement(param);

		return list;
	}
	
	public int selectCntSaleManagement(Map<String, Object> param) throws Exception {

		//Map<String, Object> param = new HashMap<String, Object>();
		//param.put("grpCd", grpCd);

		log.debug("#$#$#" + param);
		int cnt = saleManagementDao.selectCntSaleManagement(param);

		return cnt;
	}
	
	@PostConstruct
	public void setDao() {
		setDao(saleManagementDao);
	}

	protected void validation(String method, DummyDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
