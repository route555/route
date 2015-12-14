package com.echo.framework.util;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class ContentsUtilTest {
	@Autowired
	public ContentsUtil contentsUtil;

	@Test
	public void getMovieMeta() throws URISyntaxException {
		try {
			String file = "src/test/resources/test.avi";
			String thumbFile = UUID.randomUUID().toString();
			Map<String, String> map = contentsUtil
					.getMovieMeta(file, thumbFile);

			for (String key : map.keySet()) {
				System.out.println(key + "->" + map.get(key));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}