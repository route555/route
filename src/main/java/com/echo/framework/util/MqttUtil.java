package com.echo.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttUtil {
	private static Logger log = LoggerFactory.getLogger(MqttUtil.class);

	public static final String TOPIC_SEPERATOR = "/";

	public static String TOPIC_PREFIX = "ibm";
	public static String BROKER = "echoit.iptime.org";
	public static Integer PORT = 6883;
	public static String USER = "mqtt";
	public static String PASSWORD = "mqtt1234!@#$";
	public static Integer PORT_MAX = 40000;
	public static Integer PORT_MIN = 20000;
	public static Integer TIMEOUT_SEC = 5;

	static {
		try {
			TOPIC_PREFIX = PropsUtil.getValue("mqtt.topic.prefix");
			BROKER = PropsUtil.getValue("mqtt.broker");
			PORT = PropsUtil.getIntValue("mqtt.port");
			USER = PropsUtil.getValue("mqtt.user");
			PASSWORD = PropsUtil.getValue("mqtt.password");
			PORT_MIN = PropsUtil.getIntValue("mqtt.port.min");
			PORT_MAX = PropsUtil.getIntValue("mqtt.port.max");
			TIMEOUT_SEC = PropsUtil.getIntValue("mqtt.timeout.sec");
		}
		catch (Exception e) {
			log.error("invalid mqtt.timeout.sec, {}", e.getMessage(), e);
		}

		String[] keyList = { "mqtt.topic.prefix", "mqtt.broker", "mqtt.port",
				"mqtt.user", "mqtt.password", "mqtt.port.min", "mqtt.port.max",
				"mqtt.timeout.sec" };
		for (String key : keyList) {
			log.info("ENV, {}={}", key, PropsUtil.getValue(key));
		}
	}

	public static void publish(String broker, int port, List<String> topics,
			String message) throws Exception {
		publish(broker, port, topics, message, USER, PASSWORD);
	}

	public static void publish(String broker, int port, String topic,
			String message, String userName, String password) throws Exception {
		List<String> topics = new ArrayList<>();
		topics.add(topic);

		publish(broker, port, topics, message, userName, password);
	}

	public static void publish(String broker, int port, List<String> topics,
			String message, String userName, String password) throws Exception {

		MQTT mqtt = new MQTT();
		mqtt.setHost(broker, port);
		mqtt.setUserName(userName);
		mqtt.setPassword(password);

		FutureConnection connection = mqtt.futureConnection();
		Future<Void> f = connection.connect();
		f.await(TIMEOUT_SEC, TimeUnit.SECONDS);

		for (String topic : topics) {
			connection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE,
					false);
		}

		Future<Void> disconnFuture = connection.disconnect();
		disconnFuture.await(TIMEOUT_SEC, TimeUnit.SECONDS);

		log.info("publishing msg={}, topic.size={}", message, topics.size());
	}
}
