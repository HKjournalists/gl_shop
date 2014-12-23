package com.appabc.datas.system;

import java.util.Date;
import java.util.List;

public class User {

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
	/**  
	 * registerTime  
	 *  
	 * @return  the registerTime  
	 * @since   1.0.0  
	 */
	
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * @param registerTime the registerTime to set  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
