package com.echo.biz.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.OrderCntrctMgtDao;
import com.echo.biz.domain.OrderCntrctMgt;
import com.echo.biz.dto.OrderCntrctMgtDto;
import com.echo.biz.dto.SalesCntrctMgtDto;
import com.echo.framework.service.AbstractService;

@Service("OrderCntrctMgtService")
public class OrderCntrctMgtService extends AbstractService<OrderCntrctMgt, OrderCntrctMgtDto> {
	private static Logger log = LoggerFactory.getLogger(OrderCntrctMgtService.class);
	
	@Autowired
	private OrderCntrctMgtDao orderCntrctMgtDao;
	
	public OrderCntrctMgtService() {
		super(OrderCntrctMgt.class, OrderCntrctMgtDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(orderCntrctMgtDao);
	}
	
	protected void validation(String method, SalesCntrctMgtDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {
			
		}
	}
	
	public Map<String, Object> selectOrderCntrctMgt(Map<String, Object> params) throws Exception {
		return orderCntrctMgtDao.selectOrderCntrctMgt(params);
	}
	
	public List<Map<String, Object>> selectOrderDpstList(Map<String, Object> params) throws Exception {
		return orderCntrctMgtDao.selectOrderDpstList(params);
	}

	public void insertOrderCntrctMgt(Map<String, Object> params, List<Map<String, Object>> list) throws Exception {
		//세션정보
		params.put("rgtrId", "SYSTEM");
		params.put("uptrId", "SYSTEM");
		
		String prjtCd =  String.valueOf(params.get("prjtCd"));
		
		//계약번호로 등록/수정여부 판단(계약번호 없습면 Insert)
		String cntrctCd = String.valueOf(params.get("cntrctCd"));
		String strMaxCntrctCd = "";

		if(cntrctCd == null || "".equals(cntrctCd)) {
			Map<String, Object> noMap = orderCntrctMgtDao.selectMaxOrderCntrctCd(params); //계약번호 채번
			
			if(noMap != null) {
				strMaxCntrctCd = String.valueOf(noMap.get("maxCntrctCd"));
				params.put("cntrctCd", strMaxCntrctCd);
			}
			
			orderCntrctMgtDao.insertOrderCntrctMgt(params); //매입계약정보 등록
		} else {
			orderCntrctMgtDao.updateOrderCntrctMgt(params); //매입계약정보 수정
		}
		
		//매입계약이력 등록
		orderCntrctMgtDao.insertOrderCntrctHst(params);
		
		//매입지급 정보 삭제
		orderCntrctMgtDao.deleteOrderDpst(params); //매입계약 지급정보 삭제
		
		
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
				
				log.debug("=================>지급처리여부:"+map.get("dpstYn"));
				
				//지급처리 안된 건만 등록
				if(map.get("dpstYn") == null || "".equals(map.get("dpstYn")) || "N".equals(map.get("dpstYn"))) {
					//지급정보 등록
					orderCntrctMgtDao.insertOrderDpst(map);
				}
				
			}
		}
	}

}
