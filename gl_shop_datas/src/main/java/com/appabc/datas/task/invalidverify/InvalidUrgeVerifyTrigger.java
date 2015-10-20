package com.appabc.datas.task.invalidverify;

import java.text.ParseException;
import com.appabc.tools.schedule.utils.BaseCronTrigger;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月7日 下午7:23:16
 */

public class InvalidUrgeVerifyTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1372558502078065330L;

	public InvalidUrgeVerifyTrigger() throws ParseException{
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/3 * * * ?");
	}
	
	public InvalidUrgeVerifyTrigger(String jobName,String jobGroup,String cronExpression) throws ParseException{
		this();
		this.setJobName(jobName);
		this.setJobGroup(jobGroup);
		this.setCronExpression(cronExpression);
	}
	
	public InvalidUrgeVerifyTrigger(String name,String group,String jobName,String jobGroup,String cronExpression) throws ParseException{
		super(name, group, jobName, jobGroup, cronExpression);
	}
	
}
