package com.echo.framework.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.echo.framework.domain.FileUploadMessage;
import com.echo.framework.dto.FileUploadDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class FileUtilTest {
	@Test
	public void fileUtilTest() throws Exception {
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();

		request.addFile(new MockMultipartFile("test", "orgTest.txt",
				"text/plain", "test".getBytes()));

		FileUploadMessage result = FileUtil.uploadFile(
				(MultipartHttpServletRequest) request, "default");

		Assert.assertTrue("uploadFile", result.isSuccess());

		for (FileUploadDto file : result.getFileUploadList()) {
			System.out.println("INFO, org=" + file.getOriginalFileName()
					+ ", physical=" + file.getPhysicalFilePath() + ", file="
					+ file.getFileName() + ", ext=" + file.getExtension()
					+ ", size=" + file.getFileSize());

			Assert.assertTrue("deleteFile", FileUtil.deleteFile(file.getPhysicalFilePath()));
		}
	}

	@Test
	public void fileValidationTest() throws Exception {

		String[] validExts = { "jpg", "JPG", "Jpg", "jpeg", "png", "bmp",
				"pdf", "doc", "docx", "txt", "zip", "mp4", "avi", "wmv" };

		String[] invalidExts = { "jsp", "php", "rb", "py", "sh" };

		for (String ext : validExts) {
			MultipartFile mFile = new MockMultipartFile("test", "orgTest."
					+ ext, "text/plain", "test".getBytes());

			FileUploadMessage result = FileUtil.validate(mFile, "default");

			Assert.assertTrue("validation, ext=" + ext, result.isSuccess());
		}

		for (String ext : invalidExts) {
			MultipartFile mFile = new MockMultipartFile("test", "orgTest."
					+ ext, "text/plain", "test".getBytes());

			FileUploadMessage result = FileUtil.validate(mFile, "default");

			Assert.assertFalse("validation, ext=" + ext, result.isSuccess());
		}
	}
}