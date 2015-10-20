package com.appabc.datas.cms.vo;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 31, 2014 11:37:08 AM
 */
public class UserPermission {

	private int id;
	private int userId;
	private Permission perm;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Permission getPermission() {
		return perm;
	}
	public void setPermission(Permission perm) {
		this.perm = perm;
	}
	public void setPermission(int perm) {
		this.perm = Permission.valueOf(perm);
	}

}
