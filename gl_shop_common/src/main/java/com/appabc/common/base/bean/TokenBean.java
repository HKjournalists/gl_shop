/**
 *
 */
package com.appabc.common.base.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月15日 上午9:56:56
 */
public class TokenBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String token;
	
	private Date expTime; // 过期时间
	
	private int effTimeLength; // 有效时长（单位:秒）
	
	private String userName; // 用户名

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

	public int getEffTimeLength() {
		return effTimeLength;
	}

	public void setEffTimeLength(int effTimeLength) {
		this.effTimeLength = effTimeLength;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
