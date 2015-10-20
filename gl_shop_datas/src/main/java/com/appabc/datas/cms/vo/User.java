package com.appabc.datas.cms.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User implements Serializable, Cloneable, Comparable<User>{

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -9215702557902749207L;
	
	private int id;
	private String userName;
	private String realName;
	private String password;
	private boolean enabled;
	private List<Group> memberOfGroups;
	private List<UserPermission> permissions;
	private Date lastLoginTime;
	private Date createTime;

	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	 */

	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * userName
	 *
	 * @return  the userName
	 * @since   1.0.0
	 */

	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * realName
	 *
	 * @return  the realName
	 * @since   1.0.0
	 */

	public String getRealName() {
		return realName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * password
	 *
	 * @return  the password
	 * @since   1.0.0
	 */

	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * enabled
	 *
	 * @return  the enabled
	 * @since   1.0.0
	 */

	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Group> getMemberOfGroups() {
		return memberOfGroups;
	}
	public void setMemberOfGroups(List<Group> memberOfGroups) {
		this.memberOfGroups = memberOfGroups;
	}

	public List<UserPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }

        return ((User) o).id == this.id;
    }
	/* (non-Javadoc)  
	 * @see java.lang.Comparable#compareTo(java.lang.Object)  
	 */
	@Override
	public int compareTo(User o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}

	/*
	 * global toString method
	 * 
	 * */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
