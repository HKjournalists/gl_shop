/**
 *
 */
package com.appabc.tools.bean;

import java.util.HashMap;
import java.util.Map;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.MsgInfo.MsgType;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月21日 上午10:35:04
 */
public class MessageInfoBean {
	
	/**
	 * @param businessType 业务类型
	 * @param businessId 业务ID
	 * @param cid 企业ID
	 * @param content 消息内容
	 */
	public MessageInfoBean(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content) {
		this.businessId = businessId;
		this.businessType = businessType;
		this.cid = cid;
		this.systemMessageContent = content;
	}
	
	/**
	 * 业务类型,参照MsgBusinessType
	 */
	private MsgBusinessType businessType;
	/**
	 * 业务ID
	 */
	private String businessId;
	/**
	 * 企业ID
	 */
	private String cid;
	/**
	 * 消息内容
	 */
	private SystemMessageContent systemMessageContent;
	/**
	 * 其它属性
	 */
	private Map<String, Object> params = new HashMap<String, Object>();
	/**
	 * 是否发短信
	 */
	private boolean isSendShotMsg;
	/**
	 * 是否发系统消息
	 */
	private boolean isSendSystemMsg;
	/**
	 * 是否发推送消息
	 */
	private boolean isSendPushMsg;
	/**
	 * 短信模板
	 */
	private SMSTemplate smsTemplate;
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 消息类型，默认为系统消息
	 */
	private MsgType msgType = MsgType.MSG_TYPE_001;
	
	public MsgBusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(MsgBusinessType businessType) {
		this.businessType = businessType;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public SystemMessageContent getSystemMessageContent() {
		return systemMessageContent;
	}
	public void setSystemMessageContent(SystemMessageContent systemMessageContent) {
		this.systemMessageContent = systemMessageContent;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		if(params != null) this.params.putAll(params);
	}
	public void clearParams(){
		this.params.clear();
	}
	public void addParam(String key,Object value){
		this.params.put(key, value);
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public SMSTemplate getSmsTemplate() {
		return smsTemplate;
	}
	public void setSmsTemplate(SMSTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}
	public boolean isSendShotMsg() {
		return isSendShotMsg;
	}
	public void setSendShotMsg(boolean isSendShotMsg) {
		this.isSendShotMsg = isSendShotMsg;
	}
	public boolean isSendSystemMsg() {
		return isSendSystemMsg;
	}
	public void setSendSystemMsg(boolean isSendSystemMsg) {
		this.isSendSystemMsg = isSendSystemMsg;
	}
	public boolean isSendPushMsg() {
		return isSendPushMsg;
	}
	public void setSendPushMsg(boolean isSendPushMsg) {
		this.isSendPushMsg = isSendPushMsg;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public MsgType getMsgType() {
		return msgType;
	}
	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}
	
}
