/**  
 * com.appabc.quartz.tool.BaseCronTrigger.java  
 *   
 * 2014年10月28日 上午11:28:14  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule.utils;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;

import java.text.ParseException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月28日 上午11:28:14
 */

public abstract class BaseCronTrigger extends CronTrigger {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	protected final Logger logUtil = Logger.getLogger(this.getClass());

	protected String GROUPAFTERFIX = "_GROUP";
	
	public String NAME = this.getClass().getName();
	
	public String GROUP = NAME + GROUPAFTERFIX;
	
	public BaseCronTrigger(){
		super();
	}
	
	public BaseCronTrigger(String name){
		super(name);
	}
	
	public BaseCronTrigger(String name, String group){
		super(name, group);
	}
	
	public BaseCronTrigger(String name, String group, String cronExpression) throws ParseException{
		super(name, group, cronExpression);
	}
	
	public BaseCronTrigger(String name,String group,String jobName,String jobGroup){
		super(name, group, jobName, jobGroup);
	}
	
	public BaseCronTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup,cronExpression);
	}
	
}
