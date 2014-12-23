package com.appabc.tools.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月5日 下午3:00:41
 */
public class InitLoader {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SystemParamsManager systemParamsManager;
	
	
	public void init(){
		logger.info("============加载系统配置开始=============");
		systemParamsManager.initSystemParam();
		
		logger.info("============加载系统配置结束=============");
	}
	
	

}
