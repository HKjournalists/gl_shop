package com.appabc.system.web.form;

import com.appabc.system.User;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Nov 3, 2014 11:27:10 AM
 */
public class AccountForm {
	
	private User user;
	private int[] permissions;
	private String mode;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int[] getPermissions() {
		return permissions;
	}
	public void setPermissions(int[] permissions) {
		this.permissions = permissions;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
