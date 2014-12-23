/**
 *
 */
package com.appabc.datas.system;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.bean.SMSTemplate;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月21日 上午10:23:24
 */
public class MsgSendTest extends AbstractDatasTest {

	@Autowired
	private MessageSendManager messageSendManager;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		sendTest();
//		getContent();
	}

	public void getContent(){
		System.out.println(SystemMessageContent.getMsgContentOfUserRegister().getContent());
	}

	void sendTest() {
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(PurseType.GUARANTY, Calendar.getInstance().getTime(), TradeType.PLATFORM_SUBSIDY, 14000);

		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY, "9900", "181120140000013", content);

		mi.setSendPushMsg(true);
		mi.setSendShotMsg(true);
		mi.setSendSystemMsg(true);
		mi.setPhone("15811822330");
		System.out.println(SMSTemplate.getTemplateWallet(PurseType.GUARANTY, Calendar.getInstance().getTime(), TradeType.PLATFORM_SUBSIDY, 14000).toJsonStr());
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(PurseType.GUARANTY, Calendar.getInstance().getTime(), TradeType.PLATFORM_SUBSIDY, 14000));
		messageSendManager.msgSend(mi);
	}

}
