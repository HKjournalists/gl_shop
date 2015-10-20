/**
 *
 */
package com.appabc.tools.utils;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.enums.SystemInfo.SystemCategory;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.bean.pvo.TUser;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.service.system.ISystemMessageService;
import com.appabc.tools.service.user.IToolUserService;
import com.appabc.tools.sms.ISmsSender;
import com.appabc.tools.xmpp.IXmppPush;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月20日 下午5:42:14
 */
@Repository(value="MesgSender")
public class MessageSendManager {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ISystemMessageService systemMessageService;
	@Autowired
	private IToolUserService userService;
	@Autowired
	IXmppPush xmppPush;
	@Autowired
	ISmsSender smsSender;
	
	/**
	 * 消息发送
	 * @param mi
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void msgSend(MessageInfoBean mi) {
		if(mi == null){
			logger.error("MessageInfoBean is null");
			return ;
		}
		logger.info("isSendSystemMsg="+mi.isSendSystemMsg()+"\tisSendPushMsg="+mi.isSendPushMsg()+"\tisSendShotMsg="+mi.isSendShotMsg());
		TUser user = null;
		
		if(mi.isSendSystemMsg()){ // 系统消息
			TSystemMessage smsg = new TSystemMessage();
			smsg.setBusinessid(mi.getBusinessId());
			smsg.setBusinesstype(mi.getBusinessType());
			smsg.setContent(mi.getSystemMessageContent().getContent());
			smsg.setCreatetime(Calendar.getInstance().getTime());
			smsg.setQyid(mi.getCid());
			smsg.setStatus(MsgStatus.STATUS_IS_READ_NO);
			smsg.setType(mi.getMsgType());
			smsg.setSystemcategory(SystemCategory.SYSTEM_CATEGORY_HTTP);
			systemMessageService.add(smsg); // 新消息存储
			
			logger.debug("systemMessage="+smsg.getContent());
		}
		
		if(mi.isSendPushMsg()){ // 推送消息
			if(StringUtils.isNotEmpty(mi.getCid())){
				user = this.userService.getUserByCid(mi.getCid());
				if(user != null){
					PushInfoBean piBean  = new PushInfoBean();
					piBean.setBusinessId(mi.getBusinessId());
					piBean.setBusinessType(mi.getBusinessType());
					if(ClientTypeEnum.CLIENT_TYPE_IOS == user.getClienttype()){ // IOS消息精简
						piBean.setContent(mi.getSystemMessageContent().getContentIos());
					}else{
						piBean.setContent(mi.getSystemMessageContent().getContent());
					}
					piBean.setPushType(0);
					piBean.setParams(mi.getParams());
					piBean.setCid(mi.getCid());
					
					xmppPush.pushToSingle(piBean, user);
					logger.debug("xmppPush="+piBean.getContent());
				}else{
					logger.error("该企业ID没有对应的用户信息，无法推送消息；CID="+mi.getCid());
				}
			}else{
				logger.error("企业ID为空，推送无目标");
			}
			
		}
		
		if(mi.isSendShotMsg()) { // 短信
			if(mi.getSmsTemplate() != null){
				ShortMsgInfo smi = new ShortMsgInfo();
				smi.setBusinessId(mi.getBusinessId());
				smi.setBusinessType(mi.getBusinessType());
				smi.setTemplate(mi.getSmsTemplate());
				
				String phone = mi.getPhone();
				if(StringUtils.isEmpty(phone)){
					if(user == null) user = this.userService.getUserByCid(mi.getCid());
					if(user != null && StringUtils.isNotEmpty(user.getPhone())){
						phone = user.getPhone();
					}else{
						logger.error("该企业ID没有对应的用户信息，无法获取企业手机号；CID="+mi.getCid());
						return;
					}
				}
				smi.setTel(phone);
				logger.debug("xmppPush="+mi.getSmsTemplate().toJsonStr());
				smsSender.sendMsg(smi);
			}else{
				logger.error("请设置短信模板");
			}
		}
		
	}
	
}
