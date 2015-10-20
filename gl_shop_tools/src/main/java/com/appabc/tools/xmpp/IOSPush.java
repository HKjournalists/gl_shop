/**
 *
 */
package com.appabc.tools.xmpp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;

import com.appabc.bean.enums.ClientEnum.ChannelType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TClient;
import com.appabc.common.spring.BeanLocator;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.bean.IosConnectionConfigBean;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.service.user.IClientService;
import com.appabc.tools.service.user.impl.ClientServiceImpl;
import com.appabc.tools.utils.SystemParamsManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 杨跃红
 * @version : 1.0 Create Date : 2015年3月31日 下午12:03:33
 */
public class IOSPush {
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * IOS推送
	 * @param icc
	 * @param deviceToken
	 * @param piBean
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unused")
	public Map<String, Object> doIphoneXmppPushSingle(
			IosConnectionConfigBean icc, String deviceToken, PushInfoBean piBean, String username) {

		Map<String, Object> result = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		PushInfoBean pi = null;

		try {
			pi = (PushInfoBean) piBean.clone();
		} catch (CloneNotSupportedException e2) {
			e2.printStackTrace();
		}

		String alert = piBean.getContent();
		ChannelType channelType = null; // 客户下载渠道
		int badge = 1; // 图标小红圈的数值
		if(StringUtils.isNotEmpty(username)){
			IClientService clientService = (IClientService) BeanLocator.getBean(ClientServiceImpl.class);
			if(clientService != null){
				TClient clientBean = clientService.getNextBadgeBean(deviceToken, ClientTypeEnum.CLIENT_TYPE_IOS, username);
				if(clientBean != null){
					badge = clientBean.getBadge();
					channelType = clientBean.getChanneltype();
				}
				
			}
		}

		String content = null;
		boolean isAddSound = true;
		try {
			content = mapper.writeValueAsString(pi);
			logger.info("content=" + content);
			logger.info(content.getBytes().length);
			logger.info("===========================");
			content = getJsonByBiBean(pi);
			logger.info("content=" + content);
			logger.info(content.getBytes().length);
			logger.info("all length=" + (content + alert).getBytes().length);

			result.put("content", content);

		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		if ((content + alert).length() > 165) {
			isAddSound = false;
		}

		if (StringUtils.isNotEmpty(deviceToken)
				&& StringUtils.isNotEmpty(content) && icc != null) {
			List<String> tokens = new ArrayList<String>();
			deviceToken = deviceToken.replace(" ", "");
			tokens.add(deviceToken);
			String certificatePath = getIOSPushFilePath(channelType);
			String certificatePassword = icc.getCertificatePassword();// 此处注意导出的证书密码不能为空因为空密码会报错
			boolean sendCount = true;
			try {
				PushNotificationPayload payLoad = new PushNotificationPayload();
				payLoad.addAlert(alert); // 消息内容
				payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
				if (isAddSound) payLoad.addSound("default"); // 铃音
				
				payLoad.addCustomDictionary("DATA", content);

				PushNotificationManager pushManager = new PushNotificationManager();
				// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
				boolean isProduction = true;
				
				/**系统配置开发环境,测试环境,生产应用,默认为生产环境begin*********************************/
				SystemParamsManager spm = BeanLocator.getBean(SystemParamsManager.class);
				if(spm != null){
					String serverEnvironment = spm.getString(SystemConstant.SERVER_ENVIRONMENT);
					if(StringUtils.isNotEmpty(serverEnvironment)){
						switch (serverEnvironment) {
						case "1":
							isProduction = false;
							logger.info("serverEnvironment = 1，  系统配置为开发环境");
							break;
						case "2":
							isProduction = false;
							logger.info("serverEnvironment = 2，  系统配置为测试环境");
							break;
						}
					}else{
						logger.info("serverEnvironment = 0， 无配置或 系统默认配置为生产环境");
					}
				}
				/**系统配置开发环境,测试环境,生产应用,默认为生产环境end*********************************/
				
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
								certificatePath, certificatePassword, isProduction));
				List<PushedNotification> notifications = new ArrayList<PushedNotification>();
				// 发送push消息
				if (sendCount) {
					Device device = new BasicDevice();
					device.setToken(tokens.get(0));
					PushedNotification notification = pushManager
							.sendNotification(device, payLoad, true);
					notifications.add(notification);
				} else {
					List<Device> device = new ArrayList<Device>();
					for (String token : tokens) {
						device.add(new BasicDevice(token));
					}
					notifications = pushManager.sendNotifications(payLoad,
							device);
				}
				List<PushedNotification> failedNotifications = PushedNotification
						.findFailedNotifications(notifications);
				List<PushedNotification> successfulNotifications = PushedNotification
						.findSuccessfulNotifications(notifications);
				int failed = failedNotifications.size();
				 int successful = successfulNotifications.size();
				pushManager.stopConnection();
				result.put("result", "ok");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;

	}

	/**
	 * IOS推送参数格式化
	 * @param piBean
	 * @return
	 */
	private String getJsonByBiBean(PushInfoBean piBean) {
		Gson gson = new Gson();
		piBean.setContent(null);
		piBean.setOffline(null);
		piBean.setOfflineExpireTime(null);
		piBean.setPushType(null);
		piBean.setStatus(null);
		piBean.setBcode(piBean.getBusinessType().getVal());
		piBean.setBusinessType(null);
		piBean.setParamsIsNull();

		return gson.toJson(piBean);
	}

	private String getIOSPushFilePath(ChannelType channelType) {
		
		String pushFileName = "push.p12";
		if(channelType == ChannelType.CHANNEL_TYPE_APPSTORE){
			pushFileName = "push_appstore.p12";
		}
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		logger.debug(path + pushFileName);
		return path + pushFileName;
	}

//	public static void main(String[] args) {
//		
//		DOMConfigurator.configure("D:\\projects\\workspace\\gl_shop\\gl_shop_tools\\src\\main\\resources\\log4j.xml");//加载.xml文件
//
//		IOSPush push = new IOSPush();
////		System.out.println(push.getIOSPushFilePath());
//		
////
//		String deviceToken = "64b8a50cea36a1ca55ca2ae94d9ce756ad986a2384a880959a20402acc9d60c4";
//		IosConnectionConfigBean icc = new IosConnectionConfigBean();
//		icc.setCertificatePassword("123321");
//		icc.setCertificatePath(push.getIOSPushFilePath(ChannelType.CHANNEL_TYPE_APPSTORE));
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
//		piBean.setContent("测试alert");
//
//		push.doIphoneXmppPushSingle(icc, deviceToken, piBean, null);
////
//		System.out.println("==========end");
//	}

}
