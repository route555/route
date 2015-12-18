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

import com.echo.biz.dao.GroupCodeDao;
import com.echo.biz.domain.GroupCode;
import com.echo.biz.dto.GroupCodeDto;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.service.AbstractService;

@Service("GroupCodeService")
public class GroupCodeService extends AbstractService<GroupCode, GroupCodeDto> {
	private static Logger log = LoggerFactory.getLogger(GroupCodeService.class);

	@Autowired
	private GroupCodeDao groupCodeDao;
	
	@Autowired
	private DetailCodeService detailCodeService;

	public GroupCodeService() {
		super(GroupCode.class, GroupCodeDto.class);
	}

	public Object selectListCommonCode(String grpCd) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("grpCd", grpCd);

		log.debug("#$#$#" + param);
		List<Map<String, Object>> list = groupCodeDao.selectListCommonCode(param);

		return list;
	}

	
	@Transactional
	@Override
	public int deleteDto(GroupCodeDto dto) throws Exception {
		
		int s = super.deleteDto(dto);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("grpCd", dto.getGrpCd());
		int d = detailCodeService.deleteDetailCodeByGrpCd(param);
		
		return s+d;
	}
	
	@PostConstruct
	public void setDao() {
		setDao(groupCodeDao);
	}

	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
