/**
 *
 */
package com.appabc.datas.push;

import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.xmpp.IXmppPush;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	void pushOne(){
		Map<String, Object> params = new HashMap<String, Object>(); // 其它属性
		String clientId = "06e8c8c51928c1ad9429dc36008a0483";
		TUser user  = new TUser();
		user.setClientid(clientId);
		user.setClienttype(ClientTypeEnum.CLIENT_TYPE_ANDROID);

		params.put("aa", "报纸");
		params.put("bb", "大米");

		PushInfoBean piBean = new PushInfoBean();
		piBean.setPushType(0);
		piBean.setParams(params);

		push.pushToSingle(piBean, user);
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
