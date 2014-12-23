/**
 *
 */
package com.appabc.datas.system;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.enums.MsgInfo.MsgType;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.service.system.ISystemMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;

/**
 * @Description : 系统消息测试
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月16日 下午3:30:27
 */
public class SystemMessageTest extends AbstractDatasTest {

	@Autowired
	private ISystemMessageService systemMessageService;


	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		addMsgTest();
	}


	public void addMsgTest(){

		TSystemMessage msg = new TSystemMessage();
		msg.setBusinessid("aabb00011");
		msg.setBusinesstype(MsgBusinessType.BUSINESS_TYPE_USER_REGISTER);
		msg.setContent("出海捕鱼");
		msg.setCreatetime(Calendar.getInstance().getTime());
		msg.setQyid("000000915102014");
		msg.setType(MsgType.MSG_TYPE_002);
		msg.setStatus(MsgStatus.STATUS_IS_READ_NO);

		systemMessageService.add(msg);

	}


}
