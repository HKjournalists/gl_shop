/**  
 * com.appabc.pay.upsdk.LocalConsumeTrade.java  
 *   
 * 2015年2月15日 上午11:38:30  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.upsdk;

import org.junit.Test;

import com.appabc.pay.AbstractPayTest;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年2月15日 上午11:38:30
 */

public class LocalConsumeTrade extends AbstractPayTest {

	/* (non-Javadoc)  
	 * @see com.appabc.pay.AbstractPayTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();
		
		log.info(requestAppUrl);
	}

}
