package com.echo.biz.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echo.biz.dao.PurchaseManagementDao;
import com.echo.biz.dto.DummyDto;
import com.echo.biz.dto.PurchaseManagementDto;
import com.echo.biz.dto.PurchasePaymentDto;
import com.echo.framework.service.AbstractService;

@Service("PurchaseManagementService")
public class PurchaseManagementService extends AbstractService<Object, Object> {
	private static Logger log = LoggerFactory.getLogger(PurchaseManagementService.class);

	@Autowired
	private PurchaseManagementDao purchaseManagementDao;
	
	@Autowired
	private PurchasePaymentService purchasePaymentService;
	

	public PurchaseManagementService() {
		super(Object.class, Object.class);
	}
	
	@Transactional
	public int updatePurchasePayment(PurchaseManagementDto dto) throws Exception {

		int cnt=0;
		if(dto.getInData() != null &&  dto.getInData().size() !=0){
			
			for (PurchasePaymentDto purchasePaymentDto : dto.getInData()) {
				purchasePaymentService.updateDto(purchasePaymentDto);
				cnt++;
			}
		}
		return cnt;
	}
	
	public Object selectListPurchaseManagement(Map<String, Object> param) throws Exception {
		List<Map<String, Object>> list = purchaseManagementDao.selectListPurchaseManagement(param);
		return list;
	}
	
	public int selectCntPurchaseManagement(Map<String, Object> param) throws Exception {

		log.debug("#$#$#" + param);
		int cnt = purchaseManagementDao.selectCntPurchaseManagement(param);

		return cnt;
	}
	
	@PostConstruct
	public void setDao() {
		setDao(purchaseManagementDao);
	}

	protected void validation(String method, DummyDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
