/**
 *
 */
package com.appabc.tools.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月23日 上午10:26:42
 */
public class PushInfoBean implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private MsgBusinessType businessType; // 业务类型,参照MsgBusinessType
	private String bcode;// 业务类型CODE,对应MsgBusinessType的value

	private String cid; // 企业ID
	
	private String businessId; // 业务ID

	private String status; // 状态

	private String content; // 描述内容

	private Integer pushType; // 0透传，1通知（默认为透传）

	private Map<String, Object> params = new HashMap<String, Object>(); // 其它属性

	private Boolean offline = true; // 设置消息离线,默认为true

	private Long offlineExpireTime; // 离线有效时间，单位为毫秒 可选

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPushType() {
		return pushType;
	}
	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		if(params != null){
			this.params.putAll(params);
		}
	}
	public void setParamsIsNull() {
		this.params = null;
	}
	public Boolean getOffline() {
		return offline;
	}
	public void setOffline(Boolean offline) {
		this.offline = offline;
	}
	public Long getOfflineExpireTime() {
		return offlineExpireTime;
	}
	public void setOfflineExpireTime(Long offlineExpireTime) {
		this.offlineExpireTime = offlineExpireTime;
	}
	public MsgBusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(MsgBusinessType businessType) {
		this.businessType = businessType;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
