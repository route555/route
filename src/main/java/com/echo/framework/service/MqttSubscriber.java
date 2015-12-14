package com.echo.framework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class MqttSubscriber implements Runnable, InitializingBean,
		DisposableBean {

	private static final Logger log = LoggerFactory
			.getLogger(MqttSubscriber.class);

	private MqttListener mqttListener;
	private Thread thread;
	private Map<String, Object> config;
	private boolean isStop = false;
	private MQTT mqtt = null;
	private FutureConnection connection = null;

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

	private void initMqtt() throws Exception {
		try {
			setupConnection();

			startSubscriber();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupConnection() throws Exception {
		mqtt = new MQTT();
		mqtt.setHost((String) config.get("host"), Integer
				.parseInt((String) config.get("port")));
		if ((config.get("userName") != null)
				&& (((String) config.get("userName")).length() > 0)) {
			mqtt.setUserName((String) config.get("userName"));
			mqtt.setPassword((String) config.get("password"));
		}
		mqtt.setReconnectDelay(Integer.parseInt((String) config
				.get("reconnectionDelay")));

		connection = mqtt.futureConnection();
		Future<Void> connFuture = connection.connect();
		connFuture.await();

		Future<byte[]> subFuture = connection
				.subscribe(makeTopic((String) config.get("topicName")));
		subFuture.await();

		mqttListener = (MqttListener) config.get("mqttListener");

		log.debug(
				"mqtt subscriber connected to mqtt server, mqtt broker={}, port={}, topic={}, userName={}, password={}, reconnectDelay={}, finished mqtt initialzation",
				new Object[] { (String) config.get("host"),
						(String) config.get("port"),
						(String) config.get("topicName"),
						(String) config.get("userName"),
						(String) config.get("password"),
						(String) config.get("reconnectionDelay") });

	}

	private void startSubscriber() {
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();

		log.debug("mqtt subscriber thread created, listen topic={}", config
				.get("topicName"));
	}

	private Topic[] makeTopic(String topicName) {
		List<String> topicList = new ArrayList<>();
		topicList.add(topicName);

		return makeTopic(topicList);
	}

	private Topic[] makeTopic(List<String> topicList) {
		Topic[] topics = new Topic[topicList.size()];

		int idx = 0;
		for (String topicName : topicList) {
			topics[idx++] = new Topic(topicName, QoS.AT_LEAST_ONCE);
			log.debug("added topicName=[{}]", topicName);
		}

		return topics;
	}

	@Override
	public void run() {
		log.debug("mqtt listener thread started, listen topic={}", config
				.get("topicName"));
		while (true) {
			try {
				Message message;
				Future<Message> msgFuture = connection.receive();
				message = msgFuture.await();
				mqttListener.onMessage(message);

				if (isStop) {
					releaseConnection();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void requestStop(boolean flag) {
		isStop = flag;
	}

	private void releaseConnection() {
		try {
			if (connection.isConnected()) {
				Future<Void> disconnFuture = connection.disconnect();
				disconnFuture.await();
			}
			log.debug("mqtt listener thread exit, listen topic={}", config
					.get("topicName"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initMqtt();
	}

	@Override
	public void destroy() throws Exception {
		requestStop(true);
	}

}