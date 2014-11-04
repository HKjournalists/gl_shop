/**  
 * com.appabc.tools.schedule.task.orderfind.OrderFindTrigger.java  
 *   
 * 2014年10月31日 下午1:50:03  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.orderfind;

import java.text.ParseException;

import com.appabc.tools.schedule.utils.BaseCronTrigger;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月31日 下午1:50:03
 */

public class OrderFindTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 5340683562219154026L;

	public OrderFindTrigger() throws ParseException{
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/5 14 18 * ?");
	}
	
	public OrderFindTrigger(String jobName,String jobGroup,String cronExpression) throws ParseException{
		this();
		this.setJobName(jobName);
		this.setJobGroup(jobGroup);
		this.setCronExpression(cronExpression);
	}
	
	public OrderFindTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup, cronExpression);
	}
	
}
