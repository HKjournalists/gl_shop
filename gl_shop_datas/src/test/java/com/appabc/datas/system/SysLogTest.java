/**
 *
 */
package com.appabc.datas.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.enums.SysLogEnum.LogBusinessType;
import com.appabc.bean.enums.SysLogEnum.LogLevel;
import com.appabc.bean.pvo.TSystemLog;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.system.ISystemLogService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月16日 下午5:21:45
 */
public class SysLogTest extends AbstractDatasTest {
	
	@Autowired
	private ISystemLogService systemLogService;
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		saveLog();
	}
	
	public void saveLog(){
		for (int i = 0; i < 100; i++) {
			TSystemLog entity = new TSystemLog();
			entity.setBusinessid("111");
			entity.setBusinesstype(LogBusinessType.BUSINESS_TYPE_USER_LOGIN);
			entity.setCreater("admin");
			entity.setLogcontent("张三登录");
			entity.setLoglevel(LogLevel.LOG_LEVEL_INFO);
			entity.setLogstatus(0);
			entity.setLogtype(0);
			systemLogService.addToCache(entity);
		}
		
	}

}
