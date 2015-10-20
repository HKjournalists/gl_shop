/**
 *
 */
package com.appabc.datas.push;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.xmpp.IXmppPush;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月23日 下午2:40:47
 */
public class PushTest extends AbstractDatasTest {

	@Autowired
	private IXmppPush push;

	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		pushOne();
//		pushList();
	}

	/**
	 * IOS 测试环境需要更换 1，开发push.12；2，修改 IOSPush.java中的 boolean isProduction = false;
	 */
	void pushOne(){
		Map<String, Object> params = new HashMap<String, Object>(); // 其它属性
		String clientId = "d33217b524ae01bad2e2c5bd797f4eba3bba0f17bbccd0c7aead7363c47c945d";
		
		
//		String clientId = "58e8020450d3f78b0c324c29ad2e93f4a874afcb7c319fe7e7014982b396d5ea";
		TUser user  = new TUser();
		user.setClientid(clientId);
		user.setClienttype(ClientTypeEnum.CLIENT_TYPE_IOS);

//		params.put("aaaa", "中国");
		PushInfoBean piBean = new PushInfoBean();
		piBean.setPushType(0);
		piBean.setParams(params);
		piBean.setBusinessType(MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH);
		piBean.setCid("201507210000173");
		piBean.setBusinessId("765432");;
		piBean.setContent(SystemMessageContent.getMsgContentOfMoneyOther(new Date(), "+1231231").getContentIos());
		push.pushToSingle(piBean, user);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void pushList(){
		Map<String, Object> params = new HashMap<String, Object>(); // 其它属性

		List<TUser> userList = new ArrayList<TUser>();
		TUser user  = new TUser();
		user.setClientid("06e8c8c51928c1ad9429dc36008a0483");
		user.setClienttype(ClientTypeEnum.CLIENT_TYPE_ANDROID);
		userList.add(user);
		user  = new TUser();
		user.setClientid("06e8c8c51928c1ad9429dc36008a0483");
		user.setClienttype(ClientTypeEnum.CLIENT_TYPE_ANDROID);
		userList.add(user);
		user  = new TUser();
		user.setClientid("06e8c8c51928c1ad9429dc36008a0482");
		user.setClienttype(ClientTypeEnum.CLIENT_TYPE_ANDROID);
		userList.add(user);

		params.put("aa", "报纸2");
		params.put("bb", "大米2");

		PushInfoBean piBean = new PushInfoBean();
		piBean.setPushType(0);
		piBean.setParams(params);

		push.pushToList(piBean, userList);
	}

}
