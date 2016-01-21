package com.echo.biz.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.PrjtPrsnDao;
import com.echo.biz.domain.PrjtPrsn;
import com.echo.biz.dto.PrjtPrsnDto;
import com.echo.framework.service.AbstractService;

@Service("PrjtPrsnService")
public class PrjtPrsnService extends AbstractService<PrjtPrsn, PrjtPrsnDto> {
	private static Logger log = LoggerFactory.getLogger(PrjtPrsnService.class);
	
	@Autowired
	private PrjtPrsnDao prjtPrsnDao;
	
	public PrjtPrsnService() {
		super(PrjtPrsn.class, PrjtPrsnDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(prjtPrsnDao);
	}
	
	protected void validation(String method, PrjtPrsnDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {
			
		}
	}
	
	public List<Map<String, Object>> selectPrjtPrsnList(Map<String, Object> params)  throws Exception {
		return prjtPrsnDao.selectPrjtPrsnList(params);
	}
	
	public Map<String, Object> selectSalesTotAmt(Map<String, Object> params) throws Exception {
		return prjtPrsnDao.selectSalesTotAmt(params);
	}
	
	public void insertPrjtPrsn(Map<String, Object> params, List<Map<String, Object>> paramList)  throws Exception {
		
		//프로젝트 인력 삭제
		prjtPrsnDao.deletePrjtPrsn(params);
		
		//프로젝트 인력 등록
		log.debug("=================== 인력등록 시작 ========================");
		if(paramList != null) {
			for(int i=0; i<paramList.size(); i++) {
				Map<String, Object> map = paramList.get(i);
				//세션정보
				map.put("rgtrId", "SYSTEM");
				map.put("uptrId", "SYSTEM");
				
				//프로젝트코드
				map.put("prjtCd", params.get("prjtCd"));
				
				//팝업붙이면, 인력번호 삭제해야함.
				//map.put("prsnNo", params.get("prsnNo"));
				
				if(map.get("salesUnitCostAmt") == null || "".equals(map.get("salesUnitCostAmt")))
					map.put("salesUnitCostAmt", null);
				
				if(map.get("ordrUnitCstAmt") == null || "".equals(map.get("ordrUnitCstAmt")))
					map.put("ordrUnitCstAmt", null);
				
				if(map.get("prsnMm") == null || "".equals(map.get("prsnMm")))
					map.put("prsnMm", null);
				
				
				prjtPrsnDao.insertPrjtPrsn(map);
				
				log.debug("====================== 인력등록:"+map.get("prsnNm"));
			}
		}
		log.debug("==================== 인력등록 종료 =======================");
	}
	
}
