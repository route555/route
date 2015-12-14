package com.echo.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EchoApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		String activeProfile = PropsUtil.getValue("active.profiles");
		applicationContext.getEnvironment().setActiveProfiles(activeProfile);
		List<String> keyList = new ArrayList<String>();

		System.out.println("INFO, START >>>>>");

		System.out.println("INFO, ENV, active.profiles=" + activeProfile);
		if ("jndi".equalsIgnoreCase(activeProfile) == true) {
			System.out.println("INFO, ENV, db.master.url=jdbc/echoMaster");
			System.out.println("INFO, ENV, db.slave.url=jdbc/echoSlave");
		}
		else {
			keyList.add("db.master.url");
			keyList.add("db.slave.url");
		}

		/*
		 * for domain and contextPath
		 */
		keyList.add("context.web.path");
		keyList.add("cookie.domain");

		/*
		 * for ftp
		 */
		keyList.add("ftp.isUpload");
		keyList.add("ftp.server");
		keyList.add("ftp.port");
		keyList.add("ftp.cdn.end.point");

		/*
		 * for mqtt
		 */
		keyList.add("mqtt.broker");
		keyList.add("mqtt.port");

		for (String key : keyList) {
			try {
				System.out.println("INFO, ENV, " + key + "="
						+ PropsUtil.getValue(key));
			}
			catch (Exception e) {
				System.err.println("ERROR, ENV, " + key + " is null");
			}
		}
		System.out.println("INFO, >>>>>>>>>>");
	}
}
