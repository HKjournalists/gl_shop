/**
 *
 */
package com.appabc.datas.sms;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.bean.SMSTemplate;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.sms.ISmsSender;
import com.appabc.tools.utils.ValidateCodeManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

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

	public void validateCodeSend(){
		vcm.sendSmsCode("15811822330");
	}

	public void sendMoneyMsg(){

		ShortMsgInfo smi = new ShortMsgInfo();

		smi.setBusinessId("11223");
		smi.setBusinessType(MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY);
		smi.setTel("15811822330");

		SMSTemplate template = SMSTemplate.getTemplateWallet("保证金", new Date(), "冻结", 40000);
		smi.setTemplate(template);
		msgSender.sendMsg(smi);
	}

	public void sendDownAppUrlMsg(){

		ShortMsgInfo smi = new ShortMsgInfo();

		smi.setBusinessId("2200");
		smi.setBusinessType(MsgBusinessType.BUSINESS_TYPE_APP_DOWNLOAD);
		smi.setTel("15811822330");

		SMSTemplate template = SMSTemplate.getTemplateDownApp("http://www.916816.com/downapp.html");
		smi.setTemplate(template);
		msgSender.sendMsg(smi);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		validateCodeSend();
//		sendMoneyMsg();
//		sendDownAppUrlMsg();
	}

}
