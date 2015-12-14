package com.echo.framework.util;

import java.net.UnknownHostException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class FtpUtilTest {
	@Test
	public void uploadFile() throws UnknownHostException {
		String srcFile = "src/test/resources/test.jpg";
		String dstFile = "a/b/test.jpg";

		FtpUtil.uploadFile(srcFile, dstFile, 3);
	}
}