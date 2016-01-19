package com.echo.biz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.DashBoardDao;

@Service("DashBoardService")
public class DashBoardService {
	private static Logger log = LoggerFactory.getLogger(DashBoardService.class);

	@Autowired
	private DashBoardDao dashBoardDao;

	public DashBoardService() {
	}

	public Object selectPrjt() throws Exception {
		return dashBoardDao.selectPrjt();
	}
	
	public Object selectSaleCont() throws Exception {
		return dashBoardDao.selectSaleCont();
	}
	
	public Object selectPurchaseCont() throws Exception {
		return dashBoardDao.selectPurchaseCont();
	}
	
	public Object selectSaleMgt() throws Exception {
		return dashBoardDao.selectSaleMgt();
	}
	
	public Object selectPurchaseMgt() throws Exception {
		return dashBoardDao.selectPurchaseMgt();
	}
		
	
}
