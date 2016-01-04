package com.echo.biz.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.PrjtMgtDao;
import com.echo.biz.domain.PrjtMgt;
import com.echo.biz.dto.PrjtMgtDto;
import com.echo.framework.service.AbstractService;

@Service("PrjtMgtService")
public class PrjtMgtService extends AbstractService<PrjtMgt, PrjtMgtDto> {

private static Logger log = LoggerFactory.getLogger(PrjtMgtService.class);
	
	@Autowired
	private PrjtMgtDao prjtMgtDao;
	
	public PrjtMgtService() {
		super(PrjtMgt.class, PrjtMgtDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(prjtMgtDao);
	}
	
	protected void validation(String method, PrjtMgtDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map selectPrjtMgt(Map params) throws Exception {
		return prjtMgtDao.selectPrjtMgt(params);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPrjtChgrList1(Map params) throws DataAccessException {
		return prjtMgtDao.selectPrjtChgrList1(params);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPrjtChgrList2(Map params) throws DataAccessException {
		return prjtMgtDao.selectPrjtChgrList2(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertPrjt(Map params) throws DataAccessException {
		//세션정보
		params.put("rgtrId", "SYSTEM");
		params.put("uptrId", "SYSTEM");
		
		String chgSeqNo1 = String.valueOf(params.get("chgSeqNo1"));
		if(chgSeqNo1 == null || "".equals(chgSeqNo1)) {
			params.put("chgSeqNo1", null);
		}
		
		String chgSeqNo2 = String.valueOf(params.get("chgSeqNo2"));
		if(chgSeqNo2 == null || "".equals(chgSeqNo2)) {
			params.put("chgSeqNo2", null);
		}
				
		prjtMgtDao.insertPrjt(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updatePrjt(Map params) throws DataAccessException {
		//세션정보
		params.put("rgtrId", "SYSTEM");
		params.put("uptrId", "SYSTEM");
		
		String chgSeqNo1 = String.valueOf(params.get("chgSeqNo1"));
		if(chgSeqNo1 == null || "".equals(chgSeqNo1)) {
			params.put("chgSeqNo1", null);
		}
		
		String chgSeqNo2 = String.valueOf(params.get("chgSeqNo2"));
		if(chgSeqNo2 == null || "".equals(chgSeqNo2)) {
			params.put("chgSeqNo2", null);
		}
				
		prjtMgtDao.updatePrjt(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deletePrjt(Map params) throws DataAccessException {
		//세션정보
		params.put("uptrId", "SYSTEM");
		
		//프로젝트 정보 삭제
		prjtMgtDao.deletePrjt(params);
		
		//프로젝트 배정인력 삭제
		prjtMgtDao.deletePrjtPrsn(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updatePrjtCfrm(Map params) throws DataAccessException {
		//세션정보
		params.put("uptrId", "SYSTEM");
				
		prjtMgtDao.updatePrjtCfrm(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updatePrjtCfrmCancel(Map params) throws DataAccessException {
		//세션정보
		params.put("uptrId", "SYSTEM");
				
		prjtMgtDao.updatePrjtCfrmCancel(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updatePrjtEnd(Map params) throws DataAccessException {
		//세션정보
		params.put("uptrId", "SYSTEM");
				
		prjtMgtDao.updatePrjtEnd(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updatePrjtEndCancel(Map params) throws DataAccessException {
		//세션정보
		params.put("uptrId", "SYSTEM");
				
		prjtMgtDao.updatePrjtEndCancel(params);
	}
	
}
