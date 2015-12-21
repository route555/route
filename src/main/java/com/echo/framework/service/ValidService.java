package com.echo.framework.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.framework.dao.ValidDao;
import com.echo.framework.domain.Valid;
import com.echo.framework.dto.BaseDto;
import com.echo.framework.exception.EchoException;

@Service("ValidService")
public class ValidService extends AbstractService<Valid, BaseDto> {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ValidService.class);
	private static String[][] idInfos = null;

	@Autowired
	private ValidDao validDao;

	static {
		idInfos = new String[][] {

		/* table, column, param */

		{ "business", "businessId", "businessId" },

		{ "user1", "userId1", "userId1" },

		{ "ad", "adId", "adId" },

		};
	}

	public ValidService() {
		super(Valid.class, BaseDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(validDao);
	}

	public void checkDBRelation(HttpServletRequest request) {
		for (String[] idInfo : idInfos) {
			String table = idInfo[0];
			String column = idInfo[1];
			String paramKey = idInfo[2];
			String paramVal = request.getParameter(paramKey);

			if (StringUtils.isEmpty(paramVal) != true && !"0".equals(paramVal)
					&& !"-1".equals(paramVal)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("table", table);
				map.put("column", column);
				map.put("id", paramVal);

				if (validDao.selectCnt(map) < 1) {
					throw new EchoException(
							HttpServletResponse.SC_BAD_REQUEST, new Object[] {
									"error.param.not.exist.id", paramKey,
									paramVal });
				}
			}
		}
	}

}