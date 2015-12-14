package com.echo.framework.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class ThumbnailUtilTest {
	@Test
	public void generateThumbnail() throws Exception {
		String srcFile = "src/test/resources/test.jpg";
		String dstFile = "src/test/resources/test-thumbnail.jpg";

		ThumbnailUtil.generateThumbnail(srcFile, 100, 100, dstFile);
	}
}