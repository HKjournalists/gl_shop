/**  
 * com.appabc.tools.schedule.starter.ServerClassLoader.java  
 *   
 * 2014年10月30日 下午2:51:29  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule.starter;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年10月30日 下午2:51:29
 */

public class ServerClassLoader extends URLClassLoader {

	private static Logger logUtil = Logger.getLogger("ServerClassLoader");
	/**
	 * 创建一个新的实例 ServerClassLoader.
	 * 
	 * @param urls
	 */
	/*public ServerClassLoader(URL[] urls) {
		super(urls);
	}*/

	public ServerClassLoader(ClassLoader parent, File confDir, File libDir)
			throws MalformedURLException {
		super(new URL[] { confDir.toURI().toURL(), libDir.toURI().toURL() },parent);
		File[] jars = libDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				boolean accept = false;
				String smallName = name.toLowerCase();
				if (smallName.endsWith(".jar"))
					accept = true;
				else if (smallName.endsWith(".zip")) {
					accept = true;
				}
				return accept;
			}
		});
		if (jars == null) {
			return;
		}
		int size = 0;
		for (int i = 0; i < jars.length; i++){
			if (jars[i].isFile()){size++;
				logUtil.info(jars[i].getName());
				addURL(jars[i].toURI().toURL());
			}
		}
		logUtil.info("load jar file size is : "+size);
	}

}
