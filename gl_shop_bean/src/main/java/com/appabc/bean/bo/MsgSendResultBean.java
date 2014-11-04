/**
 *
 */
package com.appabc.bean.bo;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 短信发送消息返回实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月9日 下午7:25:43
 */
	
public class MsgSendResultBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7582622832379944830L;
	private String resCode; // 返回编码(0：成功，其它编码失败)
	private String resMessage;  // 返回信息(成功或失败原因)
	private String remark; // 备注信息
	private String tel; // 电话号码
	private String content; // 信息内容
	private String businessId; // 业务ID
	private String businessType; // 业务类型
	
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMessage() {
		return resMessage;
	}
	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
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
	
	
}
