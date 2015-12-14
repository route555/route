package com.echo.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
	private static Logger log = LoggerFactory.getLogger(RestUtil.class);

	private static RestTemplate restTemplate;
	private static Map<String, String> header = new HashMap<>();
	private static int connTimeout = PropsUtil.getIntValue("rest.connTimeout");
	private static int readTimeout = PropsUtil.getIntValue("rest.readTimeout");

	static {
		try {
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setConnectTimeout(connTimeout);
			requestFactory.setReadTimeout(readTimeout);
			restTemplate = new RestTemplate(requestFactory);

			header.put("Cookie", "echo="
					+ URLEncoder.encode(EchoUtil.getEchoCookie4System()
							.toString(), CryptUtil.DEFAULT_CHARSET));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Map restRequest(String method, String url,
			Map<String, String> param) {
		try {
			header.put("Cookie", "echo="
					+ URLEncoder.encode(EchoUtil.getEchoCookie4System()
							.toString(), CryptUtil.DEFAULT_CHARSET));
		}
		catch (Exception e) {
			log.error("{}", e.getMessage(), e);
		}

		return restRequest(method, url, header, param);
	}

	@SuppressWarnings("rawtypes")
	public static Map restRequest(String method, String url,
			Map<String, String> header, Map<String, String> param) {

		try {
			if ("POST".equals(method)) {
				return postRequest(url, header, param);
			}
			else if ("PUT".equals(method)) {
				return putRequest(url, header, param);
			}
			else if ("GET".equals(method)) {
				return getRequest(url, header, param);
			}
		}
		catch (Exception e) {
			log.error(
					"RestUtil request error={}, method={}, url={}, header={}, param={}",
					new Object[] { e.getMessage(), method, url,
							header.toString(), param.toString() });
		}

		return Collections.emptyMap();
	}

	@SuppressWarnings("rawtypes")
	public static Map postRequest(String url, Map<String, String> header,
			Map<String, String> param) throws Exception {
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		addHeader(headers, header);

		MultiValueMap<String, String> body = makeFormParam(param);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				body, headers);

		ResponseEntity<Map> result = restTemplate.exchange(url,
				HttpMethod.POST, request, Map.class);

		if (isSucceed(result))
			return result.getBody();

		return Collections.emptyMap();
	}

	@SuppressWarnings("rawtypes")
	public static Map putRequest(String url, Map<String, String> header,
			Map<String, String> param) throws Exception {
		return putRequest(url, header, makeQueryParam(param));
	}

	@SuppressWarnings("rawtypes")
	public static Map putRequest(String url, Map<String, String> header,
			String param) throws Exception {
		HttpHeaders headers = new HttpHeaders();

		addHeader(headers, header);

		HttpEntity<?> request = new HttpEntity<Object>(headers);

		ResponseEntity<Map> result = restTemplate.exchange(url + "?" + param,
				HttpMethod.PUT, request, Map.class, param);

		if (isSucceed(result))
			return result.getBody();

		return Collections.emptyMap();
	}

	@SuppressWarnings("rawtypes")
	public static Map getRequest(String url, Map<String, String> header,
			Map<String, String> param) throws Exception {
		return getRequest(url, header, makeQueryParam(param));
	}

	@SuppressWarnings("rawtypes")
	public static Map getRequest(String url, Map<String, String> header,
			String param) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		addHeader(headers, header);

		HttpEntity<?> request = new HttpEntity<Object>(headers);

		ResponseEntity<Map> result = restTemplate.exchange(url + "?" + param,
				HttpMethod.GET, request, Map.class, param);

		if (isSucceed(result))
			return result.getBody();

		return Collections.emptyMap();
	}

	@SuppressWarnings("rawtypes")
	private static boolean isSucceed(ResponseEntity<Map> result) {
		boolean rtn = false;
		if (result.getStatusCode() == HttpStatus.OK
				&& result.getBody().containsKey("status"))
			rtn = true;

		return rtn;
	}

	private static void addHeader(HttpHeaders headers,
			Map<String, String> headerParam) {
		if (headerParam == null)
			return;

		for (String key : headerParam.keySet())
			headers.add(key, headerParam.get(key));
	}

	private static MultiValueMap<String, String> makeFormParam(
			Map<String, String> param) {
		if (param == null)
			return null;

		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

		Object value;
		for (String key : param.keySet()) {
			value = param.get(key);
			if (value instanceof Integer)
				form.add(key, String.valueOf((Integer) value));
			else if (value instanceof Long)
				form.add(key, String.valueOf((Long) value));
			else if (value instanceof String)
				form.add(key, (String) value);
		}

		return form;
	}

	private static String makeQueryParam(Map<String, String> param) {
		if (param == null)
			return "";

		StringBuffer queryString = new StringBuffer();

		for (String queryParam : param.keySet())
			queryString.append(queryParam).append("=").append(
					param.get(queryParam)).append("&");
		return queryString.toString();
	}

	public static Map<String, String> splitQueryParam(String query) {
		if (query == null || "".equals(query.trim()))
			return Collections.emptyMap();

		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String[] nv = param.split("=");
			if (nv == null || nv.length != 2)
				continue;

			map.put(nv[0], nv[1]);
		}
		return map;
	}

	public static Map<String, String> rawHttpRequest(String siteUrl,
			String method, Map<String, String> param, String charset) {
		Map<String, String> result = new HashMap<>();
		result.put("code", "0");
		result.put("body", "client error");

		java.net.URL url = null;
		java.net.HttpURLConnection connection = null;
		try {
			url = new URL(siteUrl);
			connection = (java.net.HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(connTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setRequestMethod(method.toUpperCase());
			connection.setDoOutput(true);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bao.write(makeQueryParam(param).getBytes(charset));
			bao.flush();
			connection.getOutputStream().write(bao.toByteArray());
			connection.getOutputStream().flush();
			bao.close();

			int responseCode = connection.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuffer responseBody = new StringBuffer();
			String tmp = null;
			while ((tmp = in.readLine()) != null)
				responseBody.append(tmp);
			in.close();

			result.put("code", String.valueOf(responseCode));
			result.put("body", responseBody.toString());
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
			result.put("body", e.getMessage());
		}
		finally {
			if (connection != null)
				connection.disconnect();
		}

		return result;
	}
}
