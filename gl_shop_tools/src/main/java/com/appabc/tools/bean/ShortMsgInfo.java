package com.appabc.tools.bean;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;

/**
 * @Description : 短信内容bean
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月5日 下午12:59:36
 */
public class ShortMsgInfo implements Cloneable {
	
	private String tel; // 接收者电话号码
	private String businessId; // 业务ID
	private MsgBusinessType businessType; // 业务类型
	private SMSTemplate template;
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public MsgBusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(MsgBusinessType businessType) {
		this.businessType = businessType;
	}
	public SMSTemplate getTemplate() {
		return template;
	}
	public void setTemplate(SMSTemplate template) {
		this.template = template;
	}
	
	public ShortMsgInfo clone() throws CloneNotSupportedException {
		return (ShortMsgInfo) super.clone();
	}

}
