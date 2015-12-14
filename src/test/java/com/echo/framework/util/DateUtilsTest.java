package com.echo.framework.util;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class DateUtilsTest {
	@Test
	public void cryptUtilTest() throws Exception {
		Date org = DateUtils.DATE_FORMAT_YYYYMMDD_HHMISS_DASH
				.parse("2015-01-11 00:00:00");

		System.out.println("org="
				+ DateUtils.DATE_FORMAT_YYYYMMDD_HHMISS_DASH.format(org));

		Date gap = DateUtils.getGapDate(org, 0, 1, 0).getTime();
		Assert.assertEquals("org + month(" + 1 + ")", "2015-02-11 00:00:00",
				DateUtils.DATE_FORMAT_YYYYMMDD_HHMISS_DASH.format(gap));

		gap = DateUtils.getGapDate(org, 0, 0, 14).getTime();
		Assert.assertEquals("org + day(" + 14 + ")", "2015-01-25 00:00:00",
				DateUtils.DATE_FORMAT_YYYYMMDD_HHMISS_DASH.format(gap));
	}
}