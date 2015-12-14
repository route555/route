package com.echo.framework.util;

import static org.fusesource.hawtbuf.Buffer.utf8;

import java.util.HashMap;
import java.util.Map;

import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.type.CommonConst;
import com.echo.framework.type.CtrlType;
import com.echo.framework.type.DeliveryState;

public class AllMqttListener implements Runnable {

	private static Logger log = LoggerFactory.getLogger(AllMqttListener.class);
	// private static final String REST_URL =
	private static final String REST_URL = "http://127.0.0.1:8080/echo-admin/ctrlHistory/{controlId}";
	// private static final String REST_URL =
	// "http://1.234.90.100:8080/echo-admin/ctrlHistory/{controlId}";

	private static final String BROKER_HOST = "echoit.iptime.org";
	// private static final String BROKER_HOST = "127.0.0.1";
	private static final int BROKER_PORT = 6883;
	// private static final int BROKER_PORT = 1883;
	private static final String USER = "mqtt";
	private static final String PASSWORD = "mqtt1234!@#$";

	private static final String TOPIC = "#";

	private String brokerHost;
	private int brokerPort;
	private String topic;
	private String user;
	private String password;

	private Integer businessId;
	private Integer serviceId;

	private MQTT mqtt = null;
	private FutureConnection connection = null;

	public AllMqttListener() throws Exception {
		this(BROKER_HOST, BROKER_PORT, TOPIC, USER, PASSWORD);
	}

	public AllMqttListener(String brokerHost, int brokerPort, String topic,
			String user, String password) throws Exception {
		this.brokerHost = brokerHost;
		this.brokerPort = brokerPort;
		this.topic = topic;
		this.user = user;
		this.password = password;

		init();
	}

	private void init() throws Exception {
		mqtt = new MQTT();
		mqtt.setHost(this.brokerHost, this.brokerPort);

		if (this.user != null && this.user.length() > 0) {
			mqtt.setUserName(this.user);
			mqtt.setPassword(this.password);
		}

		connection = mqtt.futureConnection();
		Future<Void> fConn = connection.connect();
		fConn.await();

		Future<byte[]> fSub = connection.subscribe(new Topic[] { new Topic(
				utf8(this.topic), QoS.AT_LEAST_ONCE) });
		fSub.await();

	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public void run() {

		log.debug("Mqtt Listener is running..");
		while (true) {
			Future<Message> receive = connection.receive();

			Message message;
			try {
				message = receive.await();
				String payload = new String(message.getPayload());

				log.debug("Control Message payload: {}", payload);

				Map<String, Object> ctrlMsg = convertMap(payload);

				sendResultCtrlMsg(message.getTopic(), ctrlMsg);
				message.ack();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, Object> convertMap(String payload) {
		Map<String, Object> map = new HashMap<>();
		String tmp = payload;

		tmp = tmp.replace("\"", "");
		tmp = tmp.replace("\\", "");
		tmp = tmp.replace("{", "");
		tmp = tmp.replace("}", "");

		String[] list = tmp.split(",");
		for (String each : list) {
			String[] kv = each.split("\\:");
			map.put(kv[0], kv[1]);
		}

		return map;
	}

	private void sendResultCtrlMsg(String topic, Map<String, Object> ctrlMsg)
			throws Exception {

		String[] split = topic.split(MqttUtil.TOPIC_SEPERATOR);
		setBusinessId(Integer.parseInt(split[0]));
		setServiceId(Integer.parseInt(split[1]));

		String deviceId = split[2];

		String url = REST_URL.replace("{controlId}", String.valueOf(ctrlMsg
				.get("controlId")));

		Map<String, String> header = new HashMap<>();
		header.put("X-ECHO-DEVICEID", CryptUtil.base64EncryptDES(deviceId,
				CommonConst.ECHO_CRYPT_KEY));
		RestUtil.putRequest(url, header, makeResultMsg(ctrlMsg));
	}

	private String makeResultMsg(Map<String, Object> ctrlMsg) {
		String ctrlType = (String) ctrlMsg.get("ctrlType");

		StringBuffer result = new StringBuffer();
		result.append("businessId=").append(getBusinessId()).append("&");
		result.append("serviceId=").append(getServiceId()).append("&");
		result.append("ctrlType=").append(ctrlType);

		if (CtrlType.REBOOT.code().equals(ctrlType)
				|| CtrlType.DISPLAY_ON.code().equals(ctrlType)
				|| CtrlType.DISPLAY_OFF.code().equals(ctrlType)
				|| CtrlType.POWER_OFF.code().equals(ctrlType)) {
			result.append("&").append("result=ok");
		}
		else if (CtrlType.RESET_CONF.code().equals(ctrlType)) {
			result.append("&").append("openTime=").append(
					ctrlMsg.get("openTime"));
			result.append("&").append("closeTime=").append(
					ctrlMsg.get("closeTime"));
			result.append("&").append("soundVolume=").append(
					ctrlMsg.get("soundVolume"));
			result.append("&").append("brightness=").append(
					ctrlMsg.get("brightness"));
			result.append("&").append("holiday=")
					.append(ctrlMsg.get("holiday"));
		}
		else if (CtrlType.VNC_ON.code().equals(ctrlType)
				|| CtrlType.VNC_OFF.code().equals(ctrlType)) {
			result.append("&").append("result=").append(
					ctrlMsg.get("sshRemoteForwardingPort"));
		}
		else if (CtrlType.VOLUME_SET.code().equals(ctrlType)) {
			result.append("&").append("result=").append(
					ctrlMsg.get("soundVolume"));
		}
		else if (CtrlType.UPDATE_VERSION.code().equals(ctrlType)) {
			result.append("&").append("result=").append(ctrlMsg.get("version"));
		}
		else if (CtrlType.REGIST_SCHEDULE.code().equals(ctrlType)
				|| CtrlType.MODIFY_SCHEDULE.code().equals(ctrlType)) {
			result.append("&").append("result=").append(
					String.valueOf(ctrlMsg.get("adScheduleId")));
			result.append("&").append("deliveryState=").append(
					DeliveryState.CONTENTS_DOWNLOADED.code());
		}
		else if (CtrlType.DELETE_SCHEDULE.code().equals(ctrlType)) {
			result.append("&").append("result=").append(
					ctrlMsg.get("adScheduleId"));
			result.append("&").append("deliveryState=F");
		}

		log.debug("makeResultMsg={}", result.toString());
		return result.toString();
	}

	public static void main(String args[]) throws Exception {
		AllMqttListener aml = new AllMqttListener();

		/*
		 * for response controlMsg
		 */

		Thread subscriber = new Thread(aml);
		subscriber.setDaemon(true);
		subscriber.start();

		while (true) {
			Thread.sleep(1000);
		}
	}

}