/**  
 * com.appabc.pay.AbstractPayTest.java  
 *   
 * 2014年10月15日 下午2:14:59  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.common.test.spring.SpringTransactionalTestCase;
import com.appabc.common.utils.LogUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月15日 下午2:14:59
 */

@ContextConfiguration(locations = {"/applicationContext-redis.xml","/applicationContext-datasource.xml","/applicationContext-pay.xml","classpath*:spring-mvc.xml" })
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback=true)  
@Transactional
public abstract class AbstractPayTest extends SpringTransactionalTestCase {

	protected LogUtil log = LogUtil.getLogUtil(this.getClass());
	
	public abstract void mainTest();
	
}
