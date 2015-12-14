package com.echo.framework.util;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@SuppressWarnings("rawtypes")
public class JsonUtil {

	private final static ObjectMapper mapper = new ObjectMapper();
	private final static ObjectMapper prettyMapper = new ObjectMapper();

	static {
		prettyMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}

	public static String encode(Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}

	public static String encodePrettily(Object obj) throws Exception {
		return prettyMapper.writeValueAsString(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T decodeValue(String str, Class<?> clazz)
			throws Exception {
		return (T) mapper.readValue(str, clazz);
	}

	public static Map decodeValue(String str) throws Exception {
		if (StringUtils.isEmpty(str) == true) {
			return new HashMap<String, Object>();
		}

		return mapper.readValue(str, Map.class);
	}
}