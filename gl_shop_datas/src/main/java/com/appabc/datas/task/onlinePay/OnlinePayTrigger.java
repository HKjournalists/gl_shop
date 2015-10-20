package com.appabc.datas.task.onlinePay;

import java.text.ParseException;

import com.appabc.tools.schedule.utils.BaseCronTrigger;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月4日 下午4:45:26
 */

public class OnlinePayTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

	public OnlinePayTrigger() throws ParseException{
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/2 * * * ?");
	}
	
	public OnlinePayTrigger(String jobName,String jobGroup,String cronExpression) throws ParseException{
		this();
		this.setJobName(jobName);
		this.setJobGroup(jobGroup);
		this.setCronExpression(cronExpression);
	}
	
	public OnlinePayTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup, cronExpression);
	}
	
}
