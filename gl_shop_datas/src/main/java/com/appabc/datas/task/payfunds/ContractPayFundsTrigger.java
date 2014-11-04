/**  
 * com.appabc.quartz.tool.trigger.ContractPayFundsTrigger.java  
 *   
 * 2014年10月29日 上午11:20:32  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.payfunds;

import java.text.ParseException;

import com.appabc.tools.schedule.utils.BaseCronTrigger;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月29日 上午11:20:32
 */

public class ContractPayFundsTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	private static final long serialVersionUID = 1L;

	public ContractPayFundsTrigger() throws ParseException{
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/10 14 18 * ?");
	}
	
	public ContractPayFundsTrigger(String jobName,String jobGroup,String cronExpression) throws ParseException{
		this();
		this.setJobName(jobName);
		this.setJobGroup(jobGroup);
		this.setCronExpression(cronExpression);
	}
	
	public ContractPayFundsTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup, cronExpression);
	}
	
}
