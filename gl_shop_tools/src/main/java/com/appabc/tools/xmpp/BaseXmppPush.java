/**
 *
 */
package com.appabc.tools.xmpp;

import java.util.List;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TPushConfig;
import com.appabc.bean.pvo.TPushResult;
import com.appabc.bean.pvo.TUser;
import com.appabc.tools.bean.IosConnectionConfigBean;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.enums.PushInfo;
import com.appabc.tools.enums.UserInfo;
import com.appabc.tools.service.push.IPushConfigService;
import com.appabc.tools.service.push.IPushResultService;
import com.google.gson.Gson;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月29日 下午9:17:55
 */
public abstract class BaseXmppPush implements IXmppPush {
	
	@Autowired
	private IPushResultService pushResultService;
	@Autowired
	private IPushConfigService pushConfigService;
	
	private TPushConfig androidConfig; // Android账号配置信息
	
	private IosConnectionConfigBean iosConfig; // IOS服务器配置信息
	
	/**
	 * 单个用户推送
	 * @param config 账户配置信息
	 * @param clientId 客户端ID
	 * @param content 推送内容
	 * @param pushType 推送状态：0透传，2通知
	 * @param offline 是否只推送在线
	 * @param offlineExpireTime 在线时长
	 * @throws Exception
	 * @return
	 */
	public abstract String doAnroidXmppPushSingle(TPushConfig config, String clientId, String content, int pushType, Boolean offline, Long offlineExpireTime) throws Exception;
	
	/* (non-Javadoc)单个用户推送
	 * @see com.appabc.tools.xmpp.IXmppPush#pushToSingle(com.appabc.tools.bean.PushInfoBean, com.appabc.bean.pvo.TUser)
	 */
	@Override
	public boolean pushToSingle(PushInfoBean piBean, TUser user) {
		
		boolean tf = false; // 返回结果
		
		TPushResult pr = new TPushResult(); // 推送结果
		pr.setPushtarget(user.getClientid());
		
		Gson gson = new Gson();
		String msgcontent  = gson.toJson(piBean);
		
		try {
			if(StringUtils.isNotEmpty(user.getClienttype())) {
				if(UserInfo.ClientTypeEnum.CLIENT_TYPE_ANDROID.getVal().equals(user.getClienttype())) { // Android推送
					String result = doAnroidXmppPushSingle(this.getAndroidConfig(), user.getClientid(), msgcontent, piBean.getPushType(), piBean.getOffline(), piBean.getOfflineExpireTime());
					pr.setResultcontent(result);
					tf = true;
				}else if(UserInfo.ClientTypeEnum.CLIENT_TYPE_IOS.getVal().equals(user.getClienttype())) { // IOS推送
					String result = doIphoneXmppPushSingle(this.getIosConfig(), user.getClientid(), msgcontent);
					pr.setResultcontent(result);
					tf = true;
				}else{
					pr.setResultcontent("未知终端username="+user.getUsername());
				}
			}else{
				pr.setResultcontent("未知终端username="+user.getUsername());
			}
		} catch (Exception e) {
			pr.setRemark("系统内部错误");
			pr.setResultcontent("消息推送失败，errorMessage=" + e.getMessage());
			e.printStackTrace();
		}
		
		pr.setMsgcontent(msgcontent);
		pr.setMsgtype(piBean.getPushType());
		pr.setPushtype(PushInfo.PushTypeEnum.PUSH_TYPE_SINGLE.getVal());
		savePushResult(pr);
		
		return tf;
	}
	
	/* (non-Javadoc)批量推送
	 * @see com.appabc.tools.xmpp.IXmppPush#pushToSingle(com.appabc.tools.bean.PushInfoBean, java.util.List)
	 */
	@Override
	public boolean pushToList(PushInfoBean piBean, List<TUser> userList) {
		for(TUser user : userList){
			pushToSingle(piBean, user);
		}
		return true;
	}
	
	/**
	 * IOS推送
	 * @param icc
	 * @param deviceToken
	 * @param content
	 * @return
	 */
	private String doIphoneXmppPushSingle (IosConnectionConfigBean icc, String deviceToken, String content) {

		if (StringUtils.isNotEmpty(deviceToken)) {
			try {
				PayLoad payLoad = new PayLoad();
				payLoad.addAlert(content); //push的内容
				payLoad.addBadge(1); //图标小红圈的数值
				payLoad.addSound("default"); //铃音

				PushNotificationManager pushManager = PushNotificationManager.getInstance();
				pushManager.addDevice("iphone", deviceToken);

				pushManager.initializeConnection(icc.getHost(), icc.getPort(), icc.getCertificatePath(),
						icc.getCertificatePassword(), SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);// 初始化TCP连接

				Device client = pushManager.getDevice("iphone");
				pushManager.sendNotification(client, payLoad); // 推送消息
				pushManager.stopConnection();
				pushManager.removeDevice("iphone");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;

	}
	
	/**
	 * 获取Android推送配置
	 * @return
	 */
	private TPushConfig getAndroidConfig() {
		if(this.androidConfig != null){
			return this.androidConfig;
		}
		
		TPushConfig qEntity = new TPushConfig();
		qEntity.setStatus(PushInfo.ConfigStatusEnum.CONFIG_STATUS_AVAILABLE.getVal());
		qEntity.setType(PushInfo.ConfigTypeEnum.CONFIG_TYPE_ANDROID.getVal());
		List<TPushConfig> pcList = pushConfigService.queryForList(qEntity);
		
		if(pcList != null && pcList.size()>0 && pcList.get(0) != null){
			this.androidConfig = pcList.get(0);
			return pcList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取IOS推送配置
	 * @return
	 */
	private IosConnectionConfigBean getIosConfig() {
		if(this.iosConfig != null){
			return this.iosConfig;
		}
		
		TPushConfig qEntity = new TPushConfig();
		qEntity.setStatus(PushInfo.ConfigStatusEnum.CONFIG_STATUS_AVAILABLE.getVal());
		qEntity.setType(PushInfo.ConfigTypeEnum.CONFIG_TYPE_IOS.getVal());
		List<TPushConfig> pcList = pushConfigService.queryForList(qEntity);
		
		IosConnectionConfigBean icc = new IosConnectionConfigBean();
		if(pcList != null && pcList.size()>0 && pcList.get(0) != null){
			
			icc.setCertificatePassword(pcList.get(0).getAppsecret());
			icc.setCertificatePath(pcList.get(0).getCertificatepath());
			icc.setHost(pcList.get(0).getUrl());
			icc.setPort(pcList.get(0).getPort());
			
			this.iosConfig = icc;
			return icc;
		}
		return null;
	}
	
	/**
	 * 推送结果处理
	 * @param str
	 */
	private void savePushResult(TPushResult pr) {
		this.pushResultService.add(pr);
	}
}
