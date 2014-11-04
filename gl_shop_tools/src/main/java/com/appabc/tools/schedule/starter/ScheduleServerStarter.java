/**  
 * com.appabc.tools.schedule.starter.ScheduleServerStarter.java  
 *   
 * 2014年10月29日 下午9:31:53  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule.starter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年10月29日 下午9:31:53
 */

public class ScheduleServerStarter {

	private static Logger logUtil = Logger.getLogger("ScheduleServerStarter");

	public static void main(String[] args) {
		try {
			StreamHandler sh = new StreamHandler(System.out,
					new SimpleFormatter());
			logUtil.addHandler(sh);
			logUtil.setLevel(Level.ALL);
			new ScheduleServerStarter().start();
			logUtil.info("Schedule server is start....");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void start() throws Exception {
		try {
			ClassLoader parent = findParentClassLoader();

			String baseDirString = System.getProperty("base.dir","..");
			logUtil.info(baseDirString + File.separator + "conf");
			File confDir = new File(baseDirString + File.separator + "conf");
			if (!confDir.exists()) {
				throw new RuntimeException("Conf directory "
						+ confDir.getAbsolutePath() + " does not exist.");
			}
			logUtil.info(baseDirString + File.separator + "lib");
			File libDir = new File(baseDirString + File.separator + "lib");
			if (!libDir.exists()) {
				throw new RuntimeException("Lib directory "
						+ libDir.getAbsolutePath() + " does not exist.");
			}

			ClassLoader loader = new ServerClassLoader(parent, confDir, libDir);

			Thread.currentThread().setContextClassLoader(loader);

			Class<?> containerClass = loader
					.loadClass("com.appabc.tools.schedule.ScheduleServer");
			
			containerClass.newInstance();

		} catch (Exception e) {
			e.printStackTrace();
			logUtil.log(Level.INFO, e.getMessage(), e.getCause());
			throw e;
		}
	}

	private ClassLoader findParentClassLoader() {
		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		if (parent == null) {
			parent = getClass().getClassLoader();
			if (parent == null) {
				parent = ClassLoader.getSystemClassLoader();
			}
		}
		return parent;
	}

}
