/**
 *
 */
package com.appabc.tools.xmpp;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.PushInfo.ConfigStatusEnum;
import com.appabc.bean.enums.PushInfo.ConfigTypeEnum;
import com.appabc.bean.enums.PushInfo.MsgTypeEnum;
import com.appabc.bean.enums.PushInfo.PushTypeEnum;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TPushConfig;
import com.appabc.bean.pvo.TPushResult;
import com.appabc.bean.pvo.TUser;
import com.appabc.tools.bean.IosConnectionConfigBean;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.service.push.IPushConfigService;
import com.appabc.tools.service.push.IPushResultService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月29日 下午9:17:55
 */
@Repository
public abstract class BaseXmppPush{
	
	private Logger logger =  Logger.getLogger(this.getClass());
	
	@Autowired
	private IPushResultService pushResultService;
	@Autowired
	private IPushConfigService pushConfigService;
	
	private TPushConfig androidConfig; // Android账号配置信息
	
	private IosConnectionConfigBean iosConfig; // IOS服务器配置信息
	
//	private String defultCertificatePath = "/push.p12"; // IOS密钥文件
	
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
	public abstract Map<String, Object> doAnroidXmppPushSingle(TPushConfig config, String clientId, String content, int pushType, Boolean offline, Long offlineExpireTime) throws Exception;
	
	/* (non-Javadoc)单个用户推送
	 * @see com.appabc.tools.xmpp.IXmppPush#pushToSingle(com.appabc.tools.bean.PushInfoBean, com.appabc.bean.pvo.TUser)
	 */
	public boolean pushToSingle(PushInfoBean piBean, TUser user) {
		
		boolean tf = false; // 返回结果
		if(user != null && piBean != null && StringUtils.isNotEmpty(user.getClientid())){
			TPushResult pr = new TPushResult(); // 推送结果
			pr.setPushtarget(user.getClientid());
			ObjectMapper mapper = new ObjectMapper();
			String msgcontent = null;
			Map<String, Object> resultMap = null;
			try {
				if(user.getClienttype() != null) {
					if(ClientTypeEnum.CLIENT_TYPE_ANDROID.equals(user.getClienttype())) { // Android推送
						msgcontent  = mapper.writeValueAsString(piBean);
						resultMap = doAnroidXmppPushSingle(this.getAndroidConfig(), user.getClientid(), msgcontent, piBean.getPushType(), piBean.getOffline(), piBean.getOfflineExpireTime());
					}else if(ClientTypeEnum.CLIENT_TYPE_IOS.equals(user.getClienttype())) { // IOS推送
						IOSPush ip = new IOSPush();
						resultMap  = ip.doIphoneXmppPushSingle(this.getIosConfig(), user.getClientid(), piBean, user.getUsername());
						msgcontent = resultMap.get("content")+"";
					}else{
						pr.setResultcontent("消息推送失败，未知终端username="+user.getUsername());
						logger.info("消息推送失败，未知终端username="+user.getUsername());
					}
					
					pr.setClienttype(user.getClienttype());
					
					logger.info("push end……");
				}else{
					pr.setResultcontent("消息推送失败，未知终端username="+user.getUsername());
					logger.info("消息推送失败，未知终端username="+user.getUsername());
				}
			} catch (Exception e) {
				pr.setRemark("系统内部错误");
				pr.setResultcontent("消息推送失败，errorMessage=" + e.getMessage());
				logger.info("消息推送失败，errorMessage=" + e.getMessage());
				e.printStackTrace();
			}
			
			if(resultMap != null){
				pr.setResultcontent(resultMap.toString());
				if(resultMap.get("result") != null && resultMap.get("result").toString().equalsIgnoreCase("ok")){
					tf = true;
				}
			}
			
			pr.setMsgcontent(msgcontent);
			pr.setMsgtype(piBean.getPushType() != null ? MsgTypeEnum.enumOf(piBean.getPushType()) : null);
			pr.setPushtype(PushTypeEnum.PUSH_TYPE_SINGLE);
			pr.setMsgtitle(piBean.getBusinessType().getText()); // 类型中 文名
			pr.setPushstatus(tf ? 1:0);
			pr.setCid(user.getCid());
			pr.setBusinessid(piBean.getBusinessId());
			pr.setBusinesstype(piBean.getBusinessType());
			
			savePushResult(pr);
		}else {
			tf = false;
			logger.error("push error，user="+user+" ,piBean="+piBean);
		}
		
		return tf;
	}
	
	/* (non-Javadoc)批量推送
	 * @see com.appabc.tools.xmpp.IXmppPush#pushToSingle(com.appabc.tools.bean.PushInfoBean, java.util.List)
	 */
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
	
	/**
	 * 获取Android推送配置
	 * @return
	 */
	private TPushConfig getAndroidConfig() {
		if(this.androidConfig != null){
			return this.androidConfig;
		}
		
		TPushConfig qEntity = new TPushConfig();
		qEntity.setStatus(ConfigStatusEnum.CONFIG_STATUS_AVAILABLE);
		qEntity.setType(ConfigTypeEnum.CONFIG_TYPE_ANDROID);
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
		qEntity.setStatus(ConfigStatusEnum.CONFIG_STATUS_AVAILABLE);
		qEntity.setType(ConfigTypeEnum.CONFIG_TYPE_IOS);
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
		pr.setPushtime(Calendar.getInstance().getTime());
		this.pushResultService.add(pr);
	}
	
	public static void main(String[] args) {/*
		TUser user = new TUser();
		user.setClienttype(ClientTypeEnum.CLIENT_TYPE_IOS);
		
		boolean a = UserInfo.ClientTypeEnum.CLIENT_TYPE_ANDROID.equals(user.getClienttype());
		System.out.println(a);
		
		MessageInfoBean mi = new  MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH, "1111", "2222", SystemMessageContent.getMsgContentOfCompanyAuthYes());
		mi.setSendPushMsg(true);
		mi.setSendSystemMsg(true);
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(mi));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	*/
		
//		BaseXmppPush push = new GeTuiXmppPush();
//		if(1==1){
//			String path = push.getIOSPushFilePath();
//			return;
//		}
		
		
//		String deviceToken = "8af4f09634a3b4f7682f5f48761ed73248f73291b809df4eacdf1a8ab7ff64cd";
		
//		IosConnectionConfigBean icc = new IosConnectionConfigBean();
//		icc.setCertificatePassword("123321");
//		icc.setCertificatePath(push.getIOSPushFilePath());
//		icc.setHost("gateway.sandbox.push.apple.com");
//		icc.setPort(2195);
//		
//		PushInfoBean piBean = new PushInfoBean();
//		piBean.setBusinessId("43");
//		piBean.setBusinessType(MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH);
//		piBean.setCid("201501150000017");
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("contractid", "201411270000014");
//		params.put("fid", "201411270000014");
//		
//		piBean.setParams(params);
//		
//		push.doIphoneXmppPushSingle(icc, deviceToken, piBean);
//		
//		System.out.println("==========end");
	}
	
	
}
