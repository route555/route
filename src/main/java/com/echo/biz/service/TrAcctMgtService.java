package com.echo.biz.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.TrAcctMgtDao;
import com.echo.biz.domain.TrAcctMgt;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.biz.dto.TrAcctMgtDto;
import com.echo.framework.service.AbstractService;

@Service("TrAcctMgtService")
public class TrAcctMgtService extends AbstractService<TrAcctMgt, TrAcctMgtDto>{
	private static Logger log = LoggerFactory.getLogger(TrAcctMgtService.class);
	
	@Autowired
	private TrAcctMgtDao trAcctMgtDao;
	
	public TrAcctMgtService() {
		super(TrAcctMgt.class, TrAcctMgtDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(trAcctMgtDao);
	}
	
	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map selectTrAcctMgt(Map params) throws Exception {
		return trAcctMgtDao.selectTrAcctMgt(params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectTracctChgrList(Map params) throws Exception {
		return trAcctMgtDao.selectTracctChgrList(params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectTracctChgrCdList(Map params) throws Exception {
		return trAcctMgtDao.selectTracctChgrCdList(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertTrAcct(Map params, List<Map<String, Object>> paramList) throws Exception {
		
		//세션정보
		params.put("rgtrId", "SYSTEM");
		params.put("uptrId", "SYSTEM");
		
		Map map = trAcctMgtDao.selectMaxTrAcctCd(params);
		String trAcctCd = String.valueOf(map.get("trAcctCd")); //거래처코드 채번
		
		//거래처정보 등록
		params.put("trAcctCd", trAcctCd);//거래처코드
		trAcctMgtDao.insertTrAcct(params);
		
		//담당자 정보 삭제
		//trAcctMgtDao.deleteTrAcctChgr(params);
		
		//담당자 정보 등록
		if(paramList != null) {
			for(int i=0; i<paramList.size(); i++) {
				Map<String, Object> params1 = paramList.get(i);
				params1.put("trAcctCd", trAcctCd);//거래처코드
				params1.put("rgtrId", "SYSTEM");
				params1.put("uptrId", "SYSTEM");
				trAcctMgtDao.insertTrAcctChgr(params1); //담당자 등록
			}
		}
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateTrAcct(Map params, List<Map<String, Object>> paramList) throws Exception {
		
		//세션정보
		params.put("rgtrId", "SYSTEM");
		params.put("uptrId", "SYSTEM");

		//거래처정보 수정
		log.debug("거래처코드:"+params.get("trAcctCd"));
		trAcctMgtDao.updateTrAcct(params);
		
		//담당자 정보 삭제
		trAcctMgtDao.deleteTrAcctChgr(params);
		
		//담당자 정보 등록
		if(paramList != null) {
			log.debug("paramList.size():"+paramList.size());
			for(int i=0; i<paramList.size(); i++) {
				Map<String, Object> params1 = paramList.get(i);
				params1.put("trAcctCd", params.get("trAcctCd"));//거래처코드
				params1.put("rgtrId", "SYSTEM");
				params1.put("uptrId", "SYSTEM");
				trAcctMgtDao.insertTrAcctChgr(params1); //담당자 등록
			}
		}
		
		
	}
	
	public void deleteTrAcct(Map params) throws Exception {
		//거래처정보 삭제
		trAcctMgtDao.deleteTrAcct(params);
		
		//담당자정보 삭제
		trAcctMgtDao.deleteTrAcctChgr(params);
	}
	
}
