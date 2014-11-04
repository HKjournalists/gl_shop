/**  
 * com.appabc.quartz.tool.trigger.ContractConfirmGoodsInfoTrigger.java  
 *   
 * 2014年10月29日 下午2:55:22  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmgoodsinfo;

import java.text.ParseException;

import com.appabc.tools.schedule.utils.BaseCronTrigger;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月29日 下午2:55:22
 */

public class ContractConfirmGoodsInfoTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -428032290767832116L;

	public ContractConfirmGoodsInfoTrigger() throws ParseException{
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		setCronExpression("0 0/10 14 18 * ?");
	}
	
	public ContractConfirmGoodsInfoTrigger(String jobName,String jobGroup,String cronExpression) throws ParseException{
		this();
		this.setJobName(jobName);
		this.setJobGroup(jobGroup);
		this.setCronExpression(cronExpression);
	}
	
	public ContractConfirmGoodsInfoTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup, cronExpression);
	}
	
}
