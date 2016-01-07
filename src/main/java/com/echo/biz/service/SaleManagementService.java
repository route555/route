package com.echo.biz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.echo.biz.dao.SaleManagementDao;
import com.echo.biz.dto.AttachFileDto;
import com.echo.biz.dto.DummyDto;
import com.echo.biz.dto.PersonSkillDto;
import com.echo.biz.dto.SaleDemandDto;
import com.echo.biz.dto.SaleManagementDto;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.service.AbstractService;
import com.echo.framework.util.StringUtils;

@Service("SaleManagementService")
public class SaleManagementService extends AbstractService<Object, Object> {
	private static Logger log = LoggerFactory.getLogger(SaleManagementService.class);

	@Autowired
	private SaleManagementDao saleManagementDao;
	
	@Autowired
	private SaleDemandService saleDemandService;
	
	

	public SaleManagementService() {
		super(Object.class, Object.class);
	}
	
	@Transactional
	public int updateSaleDemand(SaleManagementDto dto) throws Exception {

		int cnt=0;
		if(dto.getInData() != null &&  dto.getInData().size() !=0){
			
			for (SaleDemandDto saleDemandDto : dto.getInData()) {
				saleDemandService.updateDto(saleDemandDto);
				cnt++;
			}
			
		}

		return cnt;
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
