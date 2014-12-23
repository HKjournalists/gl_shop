/**  
 * com.appabc.quartz.tool.BaseJob.java  
 *   
 * 2014年10月27日 下午6:34:59  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule.utils;

import com.appabc.common.spring.BeanLocator;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月27日 下午6:34:59
 */

public abstract class BaseJob implements Job {

	protected final Logger logUtil = Logger.getLogger(this.getClass());
	
	protected ApplicationContext ac = BeanLocator.getApplicationContext();
	
	public String NAME = this.getClass().getName();

	public String GROUPAFTERFIX = "_GROUP";
	
	public String GROUP = NAME + GROUPAFTERFIX;
	
	protected void preDoExecution(JobExecutionContext context){
		logUtil.info("execute the preDoExecution method.");
	}

	protected void afterDoExecution(JobExecutionContext context){
		logUtil.info("execute the afterDoExecution method.");
	}
	
	/* (non-Javadoc)  
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)  
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		this.preDoExecution(context);
		this.doExecutionJob(context);
		this.afterDoExecution(context);
	}
	
	public abstract void doExecutionJob(JobExecutionContext context);

}