/**
 *
 */
package com.appabc.bean.bo;

import java.io.Serializable;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;

/**
 * @Description : 企业认证对象
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月10日 上午11:14:45
 */
public class AuthCmpInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String authid; // 认证记录ID（T_AUTH_RECORD, AUTHID）(必填)
	private AuthRecordStatus authStatus; // 认证状态 (必填)
    private String author; // 认证人
    private String authresult; // 认证结果
    private String remark; // 备注
	
	private TCompanyAuth companyAuth; // 企业认证后的信息
    private TCompanyShipping shippingAuth; // 船舶认证后的信息
    private TCompanyPersonal personalAuth; // 个人认证后的信息
    
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthresult() {
		return authresult;
	}
	public void setAuthresult(String authresult) {
		this.authresult = authresult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuthid() {
		return authid;
	}
	public void setAuthid(String authid) {
		this.authid = authid;
	}
	public AuthRecordStatus getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(AuthRecordStatus authStatus) {
		this.authStatus = authStatus;
	}
	public TCompanyAuth getCompanyAuth() {
		return companyAuth;
	}
	public void setCompanyAuth(TCompanyAuth companyAuth) {
		this.companyAuth = companyAuth;
	}
	public TCompanyShipping getShippingAuth() {
		return shippingAuth;
	}
	public void setShippingAuth(TCompanyShipping shippingAuth) {
		this.shippingAuth = shippingAuth;
	}
	public TCompanyPersonal getPersonalAuth() {
		return personalAuth;
	}
	public void setPersonalAuth(TCompanyPersonal personalAuth) {
		this.personalAuth = personalAuth;
	}
    
}
