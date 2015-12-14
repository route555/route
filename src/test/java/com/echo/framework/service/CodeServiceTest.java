package com.echo.framework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.echo.framework.domain.Code;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.dto.CodeDto;
import com.echo.framework.type.CodeType;
import com.echo.framework.type.RightsType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/application-context-test.xml",
		"classpath:/META-INF/spring/application-context-dao-test.xml",
		"classpath:/META-INF/spring/application-context-service-test.xml" })
@Transactional
public class CodeServiceTest {
	@Autowired
	private CodeService codeService;

	@Autowired
	private GenerateTestData generateTestData;

	public static CodeDto getCodeDto(EchoCookie echoCookie) {
		CodeDto dto = new CodeDto();

		dto.setEchoCookie(echoCookie);

		dto.setCodeType(CodeType.ORIENTATION.code());
		dto.setCode("99");
		dto.setCodeDesc("테스트");

		return dto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void code() throws Exception {
		EchoCookie echoCookie = generateTestData
				.generateEchoCookie(RightsType.SYSTEM_ADMIN);

		CodeDto dto = getCodeDto(echoCookie);
		Code code = null;

		/*
		 * insert
		 */
		dto.validate("POST");

		codeService.insert(dto, "getCodeId", "setCodeId");
		code = codeService.selectDto(dto);
		Assert.assertEquals("insert", dto.getCodeType(), code.getCodeType());
		Assert.assertEquals("insert", dto.getCode(), code.getCode());

		try {
			codeService.insert(dto, "getCodeId", "setCodeId");
			Assert.fail("insert, checking duplicate UK_CODE_01");
		}
		catch (Exception e) {
		}

		/*
		 * update
		 */
		dto.validate("PUT");

		int updCnt = codeService.updateDto(dto);
		code = codeService.selectDto(dto);
		Assert.assertEquals("update, updCnt=" + updCnt, dto.getCodeType(), code
				.getCodeType());
		Assert.assertEquals("update, updCnt=" + updCnt, dto.getCode(), code
				.getCode());

		/*
		 * select list
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", "0");
		map.put("pageSize", "10");
		
		List<Map> list = codeService.selectList(map);
		long totalCnt = codeService.selectCnt(map);
		Assert.assertNotNull("selectList", list);
		Assert.assertTrue("selectCnt", list.size() <= totalCnt);

		/*
		 * delete
		 */
		int delCnt = codeService.deleteDto(dto);
		code = codeService.selectDto(dto);
		Assert.assertNull("delete, delCnt=" + delCnt, code);
	}
}