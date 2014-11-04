package com.appabc.datas;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.common.test.spring.SpringTransactionalTestCase;
import com.appabc.common.utils.LogUtil;

/**
 * @Description : AbstractDatasTest
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午5:55:49
 */
@ContextConfiguration(locations = {"/applicationContext-redis.xml","/applicationContext-tools.xml","/applicationContext-datas.xml","/applicationContext-pay.xml","/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback=true)  
@ActiveProfiles("develop")
@Transactional
public abstract class AbstractDatasTest extends SpringTransactionalTestCase {

	protected LogUtil log = LogUtil.getLogUtil(this.getClass());
	
	public abstract void mainTest();
	
}
