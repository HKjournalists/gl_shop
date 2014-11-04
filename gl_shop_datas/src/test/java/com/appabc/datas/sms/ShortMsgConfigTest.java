/**
 *
 */
package com.appabc.datas.sms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.sms.IShortMessageConfigService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月4日 下午6:51:42
 */

public class ShortMsgConfigTest extends AbstractDatasTest {
	
	@Autowired
	private IShortMessageConfigService shortMessageConfigService;
	
//	@Test
//	@Rollback(value=false)
	public void testAdd(){
		
		// 电信短信
		TShortMessageConfig t = new TShortMessageConfig();
		t.setSpwd("bc9e37087f48b71842669348fa4d59b5"); // appSecret
		t.setSuser("350851340000036712"); //appId
		t.setStatus(0);
		t.setSurl("http://api.189.cn/v2/emp/templateSms/sendSms");
		t.setTemplateparam("尊敬的{param1}用户，您的验证码为{param2}，本验证码{param3}内有效，感谢您的使用！");
		t.setTemplateid("91001914");
		t.setTemplatetype("PIN"); // 短信
		t.setType(189); // 电信
		t.setTokenurl("https://oauth.api.189.cn/emp/oauth2/v3/access_token");
		
		this.shortMessageConfigService.add(t);
		
	}
	
	@Test
	public void testQueryByEnity(){
		TShortMessageConfig t = new TShortMessageConfig();
		t.setType(189); // 189
		t.setTemplatetype("PIN");
		
		t = this.shortMessageConfigService.query(t);
		
		System.out.println(t);
		
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	public void mainTest() {
		// TODO Auto-generated method stub
		
	}
	
	
}
