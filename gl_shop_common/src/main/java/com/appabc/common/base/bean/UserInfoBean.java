package com.appabc.common.base.bean;

import java.util.Date;

/**
 * @Description : 登录用户信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月18日 下午4:35:35
 */
public class UserInfoBean extends BaseBean {

	private static final long serialVersionUID = 4754089673318677521L;

    private String userName;// 账号
    private String token;
    private String phone; // 手机号
	private Date expTime; // 过期时间
	private int effTimeLength; // 有效时长（单位:秒）
	private String nick; // 用户昵称
	private String logo; // 头像
	
	private String cid; // 企业ID
	private String cname; //企业名称
	private String ctype; // 企业类型
	private String authstatus; // 企业认证状态
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getEffTimeLength() {
		return effTimeLength;
	}
	public void setEffTimeLength(int effTimeLength) {
		this.effTimeLength = effTimeLength;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpTime() {
		return expTime;
	}
	public void setExpTime(Date expTime) {
		this.expTime = expTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getAuthstatus() {
		return authstatus;
	}
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
