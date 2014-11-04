/**
 *
 */
package com.appabc.datas.sms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.enums.SmsInfo.SmsTypeEnum;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.sms.ISmsSender;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月5日 下午1:51:36
 */

public class SmsSendTest extends AbstractDatasTest {
	
	@Autowired
	private ISmsSender msgSender;
	@Autowired
	private ValidateCodeManager vcm;
	
	public void testSend(){
			
		ShortMsgInfo smi = new ShortMsgInfo();
		smi.setBusinessId("abc001234");
		smi.setBusinessType("注册");
		smi.setContent("9886");
		smi.setTel("15811822330");
		smi.setType(SmsTypeEnum.SMS_TEMP_TYPE_PIN.getVal());
		smi.setUser("杨洋洋");
		
		msgSender.sendMsg(smi);
		
	}
	
	public void validateCodeSend(){
		vcm.sendSmsCode("11111111111", "张三");
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		testSend();
		
	}

}
