package com.echo.biz.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.SalesCntrctMgtDao;
import com.echo.biz.domain.SalesCntrctMgt;
import com.echo.biz.dto.SalesCntrctMgtDto;
import com.echo.framework.service.AbstractService;

@Service("SalesCntrctMgtService")
public class SalesCntrctMgtService extends AbstractService<SalesCntrctMgt, SalesCntrctMgtDto> {
	private static Logger log = LoggerFactory.getLogger(SalesCntrctMgtService.class);
	
	@Autowired
	private SalesCntrctMgtDao salesCntrctMgtDao;
	
	public SalesCntrctMgtService() {
		super(SalesCntrctMgt.class, SalesCntrctMgtDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(salesCntrctMgtDao);
	}
	
	protected void validation(String method, SalesCntrctMgtDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {
			
		}
	}
	
	public Map<String, Object> selectSalesCntrctMgt(Map<String, Object> params) throws Exception {
		return salesCntrctMgtDao.selectSalesCntrctMgt(params);
	}
	
	public List<Map<String, Object>> selectSalesDmndList(Map<String, Object> params) throws Exception {
		return salesCntrctMgtDao.selectSalesDmndList(params);
	}
	
	public void insertSalesCntrctMgt(Map<String, Object> params, List<Map<String, Object>> list) throws Exception {
		//세션정보
		params.put("rgtrId", "SYSTEM");
		params.put("uptrId", "SYSTEM");
		
		String prjtCd =  String.valueOf(params.get("prjtCd"));
		
		//계약번호로 등록/수정여부 판단(계약번호 없습면 Insert)
		String cntrctCd = String.valueOf(params.get("cntrctCd"));
		String strMaxCntrctCd = "";
		
		if("".equals(params.get("srvcCostSpplyAmt"))) {
			params.put("srvcCostSpplyAmt", null);
		}
		
		if("".equals(params.get("srvcCostTaxAmt"))) {
			params.put("srvcCostTaxAmt", null);
		}
		
		if(cntrctCd == null || "".equals(cntrctCd)) {
			Map<String, Object> noMap = salesCntrctMgtDao.selectMaxCntrctCd(params); //계약번호 채번
			
			if(noMap != null) {
				strMaxCntrctCd = String.valueOf(noMap.get("maxCntrctCd"));
				params.put("cntrctCd", strMaxCntrctCd);
			}
			
			salesCntrctMgtDao.insertSalesCntrctMgt(params); //매출계약정보 등록
		} else {
			salesCntrctMgtDao.updateSalesCntrctMgt(params); //매출계약정보 수정
		}
		
		//매출계약이력 등록
		salesCntrctMgtDao.insertSalesCntrctHst(params);
		
		//매출청구 정보 삭제
		salesCntrctMgtDao.deleteSalesDmnd(params); //매출계약 청구정보 삭제
		
		
		if(list != null) {
			for(int i=0; i<list.size(); i++) {
				Map<String, Object> map = list.get(i);
				
				if(cntrctCd == null || "".equals(cntrctCd)) {
					map.put("cntrctCd", strMaxCntrctCd);
				} else {
					map.put("cntrctCd", cntrctCd);
				}
				
				if("".equals(map.get("supplyAmt"))) {
					map.put("supplyAmt", null);
				}
				
				if("".equals(map.get("taxAmt"))) {
					map.put("taxAmt", null);
				}
				
				map.put("pjtCd", prjtCd);
				map.put("rgtrId", "SYSTEM");
				map.put("uptrId", "SYSTEM");
				
				log.debug("=================>청구처리여부:"+map.get("dpstYn"));
				
				//청구처리 안된 건만 등록
				if(map.get("dpstYn") == null || "".equals(map.get("dpstYn")) || "N".equals(map.get("dpstYn"))) {
					//청구정보 등록
					salesCntrctMgtDao.insertSalesDmnd(map);
				}
				
			}
		}
	}
}
