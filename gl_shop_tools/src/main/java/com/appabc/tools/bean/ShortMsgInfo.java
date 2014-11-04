package com.appabc.tools.bean;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 短信内容bean
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月5日 下午12:59:36
 */
public class ShortMsgInfo extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2635847472376738046L;
	private String tel; // 接收者电话号码
	private String user; // 收信人姓名
	private String content; // 信息内容
	private String type; // 短信类型(验证码、通知等……)
	private String businessId; // 业务ID
	private String businessType; // 业务类型
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	

}
