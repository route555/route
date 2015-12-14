package com.echo.framework.util;

import static org.fusesource.hawtbuf.Buffer.utf8;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/application-context-test.xml" })
public class MqttUtilTest {

	private static final String BROKER_HOST = "echoit.iptime.org";
	private static final int BROKER_PORT = 6883;
	private static final String TOPIC = "test/1";
	private static final String MSG;

	private MQTT mqtt = null;
	private FutureConnection connection = null;

	static {
		MSG = "Hi..." + new Date();
	}

	@Before
	public void init() throws Exception {
		mqtt = new MQTT();
		mqtt.setHost(BROKER_HOST, BROKER_PORT);
		mqtt.setUserName(PropsUtil.getValue("mqtt.user"));
		mqtt.setPassword(PropsUtil.getValue("mqtt.password"));

		connection = mqtt.futureConnection();
		Future<Void> fConn = connection.connect();
		fConn.await();
	}

	@After
	public void releaseConn() throws Exception {
		connection.disconnect().await();
	}

	@Test
	public void mqttUtilTest() throws Exception {
		try {
			Future<byte[]> fSub = connection.subscribe(new Topic[] { new Topic(
					utf8(TOPIC), QoS.AT_LEAST_ONCE) });
			fSub.await();
			Future<Message> receive = connection.receive();

			MqttUtil.publish(BROKER_HOST, BROKER_PORT, Arrays
					.asList(new String[] { TOPIC }), MSG);

			Message message = receive.await();
			Assert.assertEquals("received msg", MSG, new String(message
					.getPayload()));
			message.ack();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void mqttTestBecauseHutec() throws Exception {
		try {
			Future<byte[]> fSub = connection.subscribe(new Topic[] { new Topic(
					utf8(TOPIC), QoS.AT_LEAST_ONCE) });
			fSub.await();
			Future<Message> receive = connection.receive();

			Map<String, Object> datum = new HashMap<>();
			datum.put("str", "String");
			datum.put("int", 100);

			MqttUtil.publish(BROKER_HOST, BROKER_PORT, Arrays
					.asList(new String[] { TOPIC }), JsonUtil.encode(datum));

			Message message = receive.await();
			Map<String, Object> resultDatum = JsonUtil.decodeValue(new String(
					message.getPayload()), Map.class);

			System.out.println("str : " + resultDatum.get("str"));
			System.out.println("int : " + resultDatum.get("int"));
			message.ack();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}