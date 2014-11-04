/**  
 * com.appabc.tools.schedule.ScheduleSpServer.java  
 *   
 * 2014年10月29日 下午9:25:42  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appabc.tools.schedule.utils.TaskScheduleManager;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年10月29日 下午9:25:42
 */

public class ScheduleServer {

	protected static final Logger logUtil = Logger
			.getLogger(ScheduleServer.class);

	private static ScheduleServer instance;

	private static ApplicationContext context;

	private String version = "0.1.0";

	private boolean shuttingDown;

	public static ScheduleServer getInstance() {
		// return instance;
		if (instance == null) {
			synchronized (ScheduleServer.class) {
				instance = new ScheduleServer();
			}
		}
		return instance;
	}

	public ScheduleServer() {
		if (instance != null) {
			throw new IllegalStateException("A server is already running");
		}
		instance = this;
		start();
	}

	/**
	 * Starts the server using Spring configuration.
	 */
	public void start() {
		try {
			if (isStandAlone()) {
				Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
			}

			context = new ClassPathXmlApplicationContext(
					"applicationContext-schedule.xml");
			logUtil.info("Spring Configuration loaded.");

			logUtil.info("Schedule Server v" + version);
			TaskScheduleManager.start();
		} catch (Exception e) {
			e.printStackTrace();
			shutdownServer();
		}
	}

	/**
	 * Stops the server.
	 */
	public void stop() {
		shutdownServer();
		Thread shutdownThread = new ShutdownThread();
		shutdownThread.setDaemon(true);
		shutdownThread.start();
	}

	/**
	 * Returns true if the server is being shutdown.
	 * 
	 * @return true if the server is being down, false otherwise.
	 */
	public boolean isShuttingDown() {
		return shuttingDown;
	}

	/**
	 * Returns if the server is running in standalone mode.
	 * 
	 * @return true if the server is running in standalone mode, false
	 *         otherwise.
	 */
	public boolean isStandAlone() {
		boolean standalone;
		try {
			standalone = Class
					.forName("com.appabc.tools.schedule.starter.ScheduleServerStarter") != null;
		} catch (ClassNotFoundException e) {
			standalone = false;
		}
		return standalone;
	}

	private void shutdownServer() {
		shuttingDown = true;
		// Close all connections
		TaskScheduleManager.stop();
		logUtil.info("Schedule Server stopped");
	}

	private class ShutdownHookThread extends Thread {
		public void run() {
			shutdownServer();
			logUtil.info("Server halted");
			// System.err.println("Server halted");
		}
	}

	private class ShutdownThread extends Thread {
		public void run() {
			try {
				Thread.sleep(5000);
				System.exit(0);
			} catch (InterruptedException e) {
				// Ignore
			}
		}
	}

	public static ApplicationContext getApplicatioinContext() {
		return context;
	}

	public static void main(String[] args) {
		new ScheduleServer();
	}

}
