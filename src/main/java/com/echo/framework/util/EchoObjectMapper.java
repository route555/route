package com.echo.framework.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;

public class EchoObjectMapper extends ObjectMapper {
	public class NullSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException,
				JsonProcessingException {
			jgen.writeString("");
		}
	}

	public EchoObjectMapper() {
		StdSerializerProvider sp = new StdSerializerProvider();
		sp.setNullValueSerializer(new NullSerializer());

		setSerializerProvider(sp);

		configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY,
				true);
	}
}
