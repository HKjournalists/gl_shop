/**  
 * com.appabc.quartz.tool.trigger.ContractEvaluationTrigger.java  
 *   
 * 2014年10月29日 下午3:19:14  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.evaluationcontract;

import com.appabc.tools.schedule.utils.BaseCronTrigger;

import java.text.ParseException;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月29日 下午3:19:14
 */

public class ContractEvaluationTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -9206012939638829222L;

	public ContractEvaluationTrigger() throws ParseException{
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/15 * * * ?");
	}
	
	public ContractEvaluationTrigger(String jobName,String jobGroup,String cronExpression) throws ParseException{
		this();
		this.setJobName(jobName);
		this.setJobGroup(jobGroup);
		this.setCronExpression(cronExpression);
	}
	
	public ContractEvaluationTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup, cronExpression);
	}
	
}
