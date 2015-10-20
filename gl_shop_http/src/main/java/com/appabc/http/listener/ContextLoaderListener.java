/**
 *
 */
package com.appabc.http.listener;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.appabc.common.spring.BeanLocator;
import com.appabc.datas.service.system.ISystemLogService;

/**
 * @Description : 在Spring ContextLoaderListener的基础功能上，增加了系统注销前进行缓存中的日志存储功能
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月19日 下午2:36:38
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("------System Destroy,Save System Logs-------");
		
		ISystemLogService logService = (ISystemLogService) BeanLocator.getBean("ISystemLogService");
		if(logService != null){
			logService.saveTheLogsInTheCache();
		}else{
			logger.error("logService is null");
		}
			
		super.contextDestroyed(event);
	}


}
