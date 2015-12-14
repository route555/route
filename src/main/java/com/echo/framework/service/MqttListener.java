package com.echo.framework.service;

import org.fusesource.mqtt.client.Message;

public interface MqttListener {

	void onMessage(Message message);

}
