package com.echo.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptUtil {
	private static Logger log = LoggerFactory.getLogger(CryptUtil.class);

	public static final String DEFAULT_CHARSET = "UTF-8";

	private static final String CRYPT_MODE_DES = "DES";
	private static final int DEFAULT_MAX_DECRYPT_BYTE_SIZE = 8192;
	private static final byte DEFAULT_CRYPT_KEY[] = { 12, 89, 121, 11, 80, 38,
			121, 67 };

	public static String encryptHmacSha256(String data) throws Exception {
		return Hex.encodeHexString(encryptHmacSha256(data, new String(
				DEFAULT_CRYPT_KEY), DEFAULT_CHARSET));
	}

	public static byte[] encryptHmacSha256(String data, String key,
			String charSet) throws Exception {
		Mac sha256 = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(),
				"HmacSHA256");
		sha256.init(secretKey);

		return sha256.doFinal(data.getBytes(charSet));
	}

	private static byte[] copyArray(byte[] src, int size) {
		if (src == null) {
			return null;
		}

		byte dst[] = new byte[size];
		for (int i = 0; i < size; i++) {
			dst[i] = src[i];
		}

		return dst;
	}

	public static byte[] encryptDES(byte[] data, byte[] cryptKey) {
		if ((data == null) || (data.length == 0)) {
			return null;
		}

		try {
			byte byteArray[] = data;
			Cipher cipher = Cipher.getInstance(CRYPT_MODE_DES);
			SecretKeySpec spec = new SecretKeySpec(cryptKey, CRYPT_MODE_DES);
			cipher.init(1, spec);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			CipherOutputStream cos = new CipherOutputStream(baos, cipher);
			cos.write(byteArray);
			cos.flush();
			cos.close();

			return baos.toByteArray();
		}
		catch (Exception e) {
			log.error("encrypt, ", e);
			return null;
		}
	}

	public static byte[] encryptDES(String data, byte[] cryptKey, String charSet) {
		if ((data == null) || ("".equals(data) == true)) {
			return null;
		}

		try {
			byte[] byteArray = data.getBytes(charSet);
			byte[] ret = encryptDES(byteArray, cryptKey);

			if (ret != null) {
				return ret;
			}
			else {
				return null;
			}
		}
		catch (UnsupportedEncodingException e) {
			log.error("encrypt, ", e);
			return null;
		}
	}

	public static byte[] encryptDES(String data, byte[] cryptoKey) {
		return encryptDES(data, cryptoKey, DEFAULT_CHARSET);
	}

	public static byte[] encryptDES(byte[] data) {
		return encryptDES(data, DEFAULT_CRYPT_KEY);
	}

	public static String base64EncryptDES(String data, byte[] cryptoKey)
			throws Exception {
		return new String(Base64.encodeBase64(encryptDES(data, cryptoKey)),
				DEFAULT_CHARSET);
	}

	public static String urlEncodeCryptDES(String data, byte[] cryptoKey)
			throws Exception {
		return URLEncoder.encode(base64EncryptDES(data, cryptoKey),
				DEFAULT_CHARSET);
	}

	public static byte[] encryptDES(String data) {
		return encryptDES(data, DEFAULT_CRYPT_KEY, DEFAULT_CHARSET);
	}

	public static byte[] decryptDES(byte[] data, byte[] cryptKey) {
		if ((data == null) || (data.length == 0)) {
			return null;
		}

		try {
			Cipher cipher = Cipher.getInstance(CRYPT_MODE_DES);
			SecretKeySpec spec = new SecretKeySpec(cryptKey, CRYPT_MODE_DES);
			cipher.init(2, spec);
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			CipherInputStream cis = new CipherInputStream(bais, cipher);
			byte byteArray[] = new byte[DEFAULT_MAX_DECRYPT_BYTE_SIZE];
			int size;
			int n;
			for (size = 0; (n = cis.read(byteArray, size, byteArray.length
					- size)) > 0; size += n)
				;
			cis.close();
			return copyArray(byteArray, size);
		}
		catch (Exception e) {
			log.error("decrypt, ", e);
			return null;
		}
	}

	public static byte[] decryptDES(String data, byte[] cryptKey, String charSet) {
		if ((data == null) || ("".equals(data) == true)) {
			return null;
		}

		try {
			return decryptDES(data.getBytes(charSet), cryptKey);
		}
		catch (UnsupportedEncodingException e) {
			log.error("decrypt, ", e);
			return null;
		}
	}

	public static String decryptDES(String data, byte[] cryptKey)
			throws UnsupportedEncodingException {
		byte[] decrypt = decryptDES(data, cryptKey, DEFAULT_CHARSET);

		if (decrypt != null) {
			return new String(decrypt, DEFAULT_CHARSET);
		}
		else {
			return null;
		}
	}

	public static byte[] decryptDES(byte[] data) {
		return decryptDES(data, DEFAULT_CRYPT_KEY);
	}

	public static byte[] decryptDES(String data) throws Exception {
		return decryptDES(data, DEFAULT_CRYPT_KEY, DEFAULT_CHARSET);
	}

	public static String base64DecryptDES(String data, byte[] cryptoKey)
			throws Exception {
		return new String(decryptDES(Base64.decodeBase64(data
				.getBytes(DEFAULT_CHARSET)), cryptoKey), DEFAULT_CHARSET);
	}

	public static String urlDecodeCryptDES(String data, byte[] cryptoKey)
			throws Exception {
		return base64DecryptDES(URLDecoder.decode(data, DEFAULT_CHARSET),
				cryptoKey);
	}

	public static String getMD5(String text) {
		return DigestUtils.md5Hex(text);
	}

	public static void main(String[] args) throws Exception {
		String key = "ajdjflad";
		String data = "echo1234!@#$";
		String enc = null, dec = null;

		System.out.println("TRACE, org=" + data);

		enc = encryptHmacSha256(data);
		System.out.println("TRACE, HmacSha256(for passwd), enc=" + enc);

		enc = new String(Base64.encodeBase64(encryptDES(data, key.getBytes(),
				DEFAULT_CHARSET)), DEFAULT_CHARSET);
		System.out.println("TRACE, DES, enc=" + enc);

		dec = new String(decryptDES(Base64.decodeBase64(enc
				.getBytes(DEFAULT_CHARSET)), key.getBytes()), DEFAULT_CHARSET);
		System.out.println("TRACE, DES, dec=" + dec);

		System.out.println("TRACE, MD5, hash=" + getMD5(data));
	}
}