package com.echo.framework.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.echo.framework.exception.EchoException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/application-context-test.xml",
		"classpath:/META-INF/spring/application-context-dao-test.xml",
		"classpath:/META-INF/spring/application-context-service-test.xml" })
@Transactional
public class ValidServiceTest {
	@Autowired
	private ValidService validService;

	@Test
	public void validIds() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("businessId", "1");
		request.setParameter("serviceId", "1");
		request.setParameter("companyId", "1");
		request.setParameter("rightsId", "1");
		request.setParameter("userId", "1");
		request.setParameter("seller", "1");

		validService.checkDBRelation(request);

		request.setParameter("businessId", "999999999");
		request.setParameter("serviceId", "999999999");
		request.setParameter("companyId", "999999999");
		request.setParameter("rightsId", "999999999");
		request.setParameter("userId", "999999999");
		request.setParameter("seller", "999999999");

		try {
			validService.checkDBRelation(request);
			Assert.fail("expect not exist id of param");
		}
		catch (EchoException e) {
			Assert.assertTrue("expect not exist id of param", e.getMessage()
					.indexOf("error.param.not.exist.id") != -1);
		}
	}
}