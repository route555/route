package com.echo.framework.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.echo.framework.service.AuthService;
import com.echo.framework.type.CommonConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class CryptUtilTest {
	@Test
	public void cryptUtilTest() throws Exception {
		String[] orgList = { "1", "4", "100000", "1386571231087",
				"38-99-27-71-89-50" };

		for (String org : orgList) {
			/*
			 * DES + BASE64
			 */
			String base64encrypt = CryptUtil.base64EncryptDES(org,
					CommonConst.ECHO_CRYPT_KEY);
			String base64decrypt = CryptUtil.base64DecryptDES(base64encrypt,
					CommonConst.ECHO_CRYPT_KEY);
			Assert.assertTrue("des + base64", org.equals(base64decrypt));

			/*
			 * DES + BASE64 + URLEncode
			 */
			String urlEncodeEncrypt = CryptUtil.urlEncodeCryptDES(org,
					CommonConst.ECHO_CRYPT_KEY);
			String urlDecodeDecrypt = CryptUtil.urlDecodeCryptDES(
					urlEncodeEncrypt, CommonConst.ECHO_CRYPT_KEY);
			Assert.assertTrue("des + base64 + urlenc", org
					.equals(urlDecodeDecrypt));

			System.out.println(org + " (des + base64) -> " + base64encrypt);
			System.out.println(org + " (des + base64 + urlenc) -> "
					+ urlEncodeEncrypt);
		}
	}

	@Test
	public void cryptDeviceHeaderTest() throws Exception {
		String[] orgList = {
				
				"100001",
				"100002",
				"100011", "100012", "100013",
				"100014",
				"100015",
				"100071", "100072",

		};

		for (String org : orgList) {
			/*
			 * DES + BASE64
			 */
			String base64encrypt = CryptUtil.base64EncryptDES(org,
					CommonConst.ECHO_CRYPT_KEY);
			String base64decrypt = CryptUtil.base64DecryptDES(base64encrypt,
					CommonConst.ECHO_CRYPT_KEY);
			Assert.assertTrue("des + base64", org.equals(base64decrypt));

			/*
			 * DES + BASE64 + URLEncode
			 */
			String urlEncodeEncrypt = CryptUtil.urlEncodeCryptDES(org,
					CommonConst.ECHO_CRYPT_KEY);
			String urlDecodeDecrypt = CryptUtil.urlDecodeCryptDES(
					urlEncodeEncrypt, CommonConst.ECHO_CRYPT_KEY);
			Assert.assertTrue("des + base64 + urlenc", org
					.equals(urlDecodeDecrypt));

			System.out.println(org + ", " + AuthService.X_ECHO_DEVICEID
					+ ": " + urlEncodeEncrypt);
		}
	}
}