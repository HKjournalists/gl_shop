/**
 *
 */
package com.appabc.tools.xmpp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TPushConfig;
import com.appabc.bean.pvo.TUser;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月29日 下午9:20:21
 */
@Repository(value="IXmppPush")
public class GeTuiXmppPush extends BaseXmppPush {

	/* (non-Javadoc)个推单个透传推送
	 * @see com.appabc.tools.xmpp.BaseXmppPush#doAnroidXmppPushSingle(com.appabc.bean.pvo.TPushConfig, java.lang.String, java.lang.String, int, java.lang.Boolean, java.lang.Long)
	 */
	@Override
	public String doAnroidXmppPushSingle(TPushConfig config, String clientId,
			String content, int pushType, Boolean offline,
			Long offlineExpireTime)  throws Exception {
		IGtPush push = new IGtPush(config.getUrl(), config.getAppkey(), config.getMastersecret());
        push.connect();
  
        SingleMessage message = new SingleMessage();
        
        //设置消息离线，并设置离线时间
        message.setOffline(offline);
        //离线有效时间，单位为毫秒，可选
        if(offlineExpireTime != null){
        	message.setOfflineExpireTime(offlineExpireTime);
        }
        message.setData(getTransmissionTemplate(config, content));
  
        Target target1 = new Target();
        target1.setAppId(config.getAppid());
        target1.setClientId(clientId);
  
        IPushResult ret = push.pushMessageToSingle(message, target1);
        return  ret.getResponse().toString();
	}
	
	/**
	 * 多个指定用户批量推送
	 * @param pc
	 * @param userList
	 * @param content
	 * @param pushType
	 * @param offline
	 * @param offlineExpireTime
	 * @return
	 * @throws Exception
	 */
	public String pushToList(TPushConfig pc, List<TUser> userList, String content, int pushType, Boolean offline, Long offlineExpireTime) throws Exception {
		
		//配置返回每个用户返回用户状态
//		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		IGtPush push = new IGtPush(pc.getUrl(), pc.getAppkey(), pc.getMastersecret());
		
        //建立连接，开始鉴权
        push.connect();
        //透传模板
        TransmissionTemplate template = getTransmissionTemplate(pc, content);
        ListMessage message = new ListMessage();
        message.setData(template);
  
        //设置消息离线，并设置离线时间
        message.setOffline(offline);
        //离线有效时间，单位为毫秒，可选
        if(offlineExpireTime != null){
        	message.setOfflineExpireTime(offlineExpireTime);
        }
		
        List<Target> targetList = new ArrayList<Target>();
        for(TUser user : userList) {
        	Target target = new Target();
        	target.setAppId(pc.getAppid());
        	if(StringUtils.isNotEmpty(user.getClientid())){
        		target.setClientId(user.getClientid());
        	}
        	
        	targetList.add(target);
        }
		
      //获取taskID
        String taskId = push.getContentId(message);
			
		IPushResult ret = push.pushMessageToList(taskId, targetList);
		return  ret.getResponse().toString();
	}
	
	/**
	 * 获取透传模板
	 * @param pc
	 * @param content
	 * @return
	 */
	private static TransmissionTemplate getTransmissionTemplate(TPushConfig pc, String content) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(pc.getAppid());
	    template.setAppkey(pc.getAppkey());
	    template.setTransmissionType(1);
	    template.setTransmissionContent(content);
	    return template;
	}
}
