package com.appabc.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.appabc.common.utils.LogUtil;

/**
 * @Description : spring application context setter
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月12日 下午3:59:19
 */

public class SpringServiceInitializer implements ApplicationContextAware,DisposableBean {

	private static LogUtil log = LogUtil.getLogUtil(SpringServiceInitializer.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		log.info("Set the Application Context to Bean Locator.");
		BeanLocator.setApplicationContext(applicationContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.beans.factory.DisposableBean#destroy()  
	 */
	public void destroy() throws Exception {
		log.info("Destroy the Bean Locator's Application Context.");
		BeanLocator.destroyApplicationContext();
	}

}
