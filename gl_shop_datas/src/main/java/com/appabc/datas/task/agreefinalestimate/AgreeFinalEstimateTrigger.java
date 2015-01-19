package com.appabc.datas.task.agreefinalestimate;

import java.text.ParseException;

import com.appabc.tools.schedule.utils.BaseCronTrigger;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月16日 上午11:49:01
 */

public class AgreeFinalEstimateTrigger extends BaseCronTrigger {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 4043618938549790542L;

	/**
	 * 创建一个新的实例 CancelContractTrigger.
	 * @throws ParseException
	 *
	 */
	public AgreeFinalEstimateTrigger() throws ParseException {
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/5 * * * ?");
	}
	
}
