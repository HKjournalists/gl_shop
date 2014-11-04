/**
 *
 */
package com.appabc.datas.system;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.enums.MsgInfo;
import com.appabc.datas.service.system.ISystemMessageService;

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
		msg.setBusinesstype(MsgInfo.MsgBusinessType.MSG_BUSINESS_TYPE_001.getVal());
		msg.setContent("出海捕鱼");
		msg.setCreatetime(Calendar.getInstance().getTime());
		msg.setQyid("000000915102014");
		msg.setType(MsgInfo.MsgType.MSG_TYPE_002.getVal());
		msg.setStatus(MsgInfo.MsgStatus.STATUS_IS_READ_NO.getVal());
		
		systemMessageService.add(msg);
		
	}
	

}
