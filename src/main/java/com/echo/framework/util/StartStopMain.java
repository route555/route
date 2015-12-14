package com.echo.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartStopMain {

	private static AbstractApplicationContext context = null;

	private static final String DEFAULT_CONF = "META-INF/spring/application-context.xml";

	private static void printEnv() {
		System.out.println("INFO, START >>>>>");

		List<String> keyList = new ArrayList<String>();
		keyList.add("db.master.url");
		keyList.add("db.slave.url");

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

	public static void main(String[] args) {

		String method = args[0].trim().toLowerCase();
		if ("start".equals(method)) {
			String conf = null;
			if (args.length > 1)
				conf = args[1];
			else
				conf = DEFAULT_CONF;

			printEnv();

			start(conf);
		}
		else if ("stop".equals(method))
			stop();
		else {
			System.err.println("Not supported value: " + method);
			System.exit(1);
		}

	}

	private static void start(String conf) {
		context = new ClassPathXmlApplicationContext(conf);
		context.registerShutdownHook();
	}

	private static void stop() {
		if (context != null)
			context.close();
	}
}
