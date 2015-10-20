package com.appabc.datas.task.cancelcontract;

import com.appabc.tools.schedule.utils.BaseCronTrigger;

import java.text.ParseException;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月13日 下午12:01:21
 */

public class CancelContractTrigger extends BaseCronTrigger {

	/**
	 * serialVersionUID:（用一句话描述这个变量表示什么）
	 *
	 * @since 1.0.0
	 */

	private static final long serialVersionUID = -6152760807733429531L;

	/**
	 * 创建一个新的实例 CancelContractTrigger.
	 * @throws ParseException
	 *
	 */
	public CancelContractTrigger() throws ParseException {
		super();
		this.setName(NAME);
		this.setGroup(GROUP);
		this.setCronExpression("0 0/3 * * * ?");
	}

}
