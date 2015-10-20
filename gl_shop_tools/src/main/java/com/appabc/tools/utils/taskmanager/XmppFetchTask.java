package com.appabc.tools.utils.taskmanager;

import org.apache.log4j.Logger;

import com.appabc.bean.pvo.TUser;
import com.appabc.common.spring.BeanLocator;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.xmpp.BaseXmppPush;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年3月18日 下午4:37:42
 */
public class XmppFetchTask implements Runnable{
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	private PushInfoBean piBean;
	private TUser user;
	
	public void setPiBean(PushInfoBean piBean) {
		this.piBean = piBean;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public void run() {
		try{
			revoke();
		}catch(Exception e){
			e.printStackTrace();
		}
		XmppFetchCenterManager.getIntance().finishTask(this);
	}
	
	public void revoke(){
		
		try {
			Thread.sleep(1000); // 所有推送消息延迟1秒
			log.info("XMPP push delay 1 seconds。");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		BaseXmppPush xmpp = BeanLocator.getBean(BaseXmppPush.class);
		xmpp.pushToSingle(piBean, user);
	}
}
