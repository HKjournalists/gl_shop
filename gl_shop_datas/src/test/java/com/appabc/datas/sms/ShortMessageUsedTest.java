/**
 *
 */
package com.appabc.datas.sms;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TShortMessageUsed;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.service.sms.IShortMessageUsedService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月9日 下午2:45:53
 */
public class ShortMessageUsedTest extends AbstractDatasTest {

	@Autowired
	private IShortMessageUsedService shortMessageUsedService;

//	@Rollback(value=false)
	public void testAdd(){
		TShortMessageUsed t = new TShortMessageUsed();
		t.setBusinessid("111");
		t.setBusinesstype(MsgBusinessType.enumOf("1"));
		t.setPhonenumber("11111111111");
		t.setRemark("test");
		t.setSendstatus(0);
		t.setSendtime(Calendar.getInstance().getTime());
		t.setSmcontent("1240");
		t.setWaittime(Calendar.getInstance().getTime());

		this.shortMessageUsedService.add(t);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
		// TODO Auto-generated method stub

	}

}
