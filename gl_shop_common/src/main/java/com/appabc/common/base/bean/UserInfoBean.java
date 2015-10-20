package com.appabc.common.base.bean;

import java.util.LinkedList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
    private String phone; // 手机号
	private String nick; // 用户昵称
	private String logo; // 头像
	
	private String cid; // 企业ID
	private String cname; //企业名称
	private String ctype; // 企业类型
	private String authstatus; // 企业认证状态
	
	private LinkedList<TokenBean> tokenList = new LinkedList<TokenBean>(); // 多TOKEN

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return StringUtils.isEmpty(cname) ? phone : cname;
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

	public LinkedList<TokenBean> getTokenList() {
		return tokenList;
	}

	public void setTokenList(LinkedList<TokenBean> tokenList) {
		if(CollectionUtils.isEmpty(tokenList)){
			tokenList.clear();
		}else{
			this.tokenList = tokenList;
		}
	}
	
}
