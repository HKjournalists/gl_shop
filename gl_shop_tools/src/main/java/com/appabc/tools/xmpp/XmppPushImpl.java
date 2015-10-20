/**
 *
 */
package com.appabc.tools.xmpp;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TUser;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.utils.taskmanager.XmppFetchCenterManager;
import com.appabc.tools.utils.taskmanager.XmppFetchTask;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年3月18日 下午4:40:48
 */
@Repository
public class XmppPushImpl implements IXmppPush{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean pushToSingle(PushInfoBean piBean, TUser user) {
		XmppFetchTask task = new XmppFetchTask();
		task.setPiBean(piBean);
		task.setUser(user);
		
		try {
			Thread.sleep(1); // 把相同时间添加的任务也延迟1毫秒，避免线程同时醒来，而改变执行顺序
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		XmppFetchCenterManager.getIntance().addTask(task);
		return true;
	}

	@Override
	public boolean pushToList(PushInfoBean piBean, List<TUser> userList) {
		if(CollectionUtils.isNotEmpty(userList) && piBean != null){
			for(TUser user : userList){
				pushToSingle(piBean, user);
			}
			return true;
		}else{
			logger.error("push error，userList="+userList+" ,piBean="+piBean);
			return false;
		}
	}

}
