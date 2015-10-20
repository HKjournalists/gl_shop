package com.appabc.bean.pvo;

import java.util.Date;
import java.util.List;

import com.appabc.bean.enums.UrgeInfo.UrgeStatus;
import com.appabc.bean.enums.UrgeInfo.UrgeType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月27日 下午2:08:58
 */

public class TUrgeVerify extends BaseBean {

	private static final long serialVersionUID = -2790957623362435256L;
	private String uid;
	private UrgeType utype;
	private String creater;
	private Date createtime;
	private String updater;
	private Date updatetime;
	private String userrealname;
	private String cid;
	private String usertype;
	private String registreason;
	private String remark;
	private String record;
	private UrgeStatus urgestatus;
	private List<String> recordList;
	private String owner;
	/**  
	 * uid  
	 *  
	 * @return  the uid  
	 * @since   1.0.0  
	 */
	
	public String getUid() {
		return uid;
	}
	/**  
	 * @param uid the uid to set  
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**  
	 * utype  
	 *  
	 * @return  the utype  
	 * @since   1.0.0  
	 */
	
	public UrgeType getUtype() {
		return utype;
	}
	/**  
	 * @param utype the utype to set  
	 */
	public void setUtype(UrgeType utype) {
		this.utype = utype;
	}
	/**  
	 * creater  
	 *  
	 * @return  the creater  
	 * @since   1.0.0  
	 */
	
	public String getCreater() {
		return creater;
	}
	/**  
	 * @param creater the creater to set  
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
	/**  
	 * createtime  
	 *  
	 * @return  the createtime  
	 * @since   1.0.0  
	 */
	
	public Date getCreatetime() {
		return createtime;
	}
	/**  
	 * @param createtime the createtime to set  
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**  
	 * updater  
	 *  
	 * @return  the updater  
	 * @since   1.0.0  
	 */
	
	public String getUpdater() {
		return updater;
	}
	/**  
	 * @param updater the updater to set  
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	/**  
	 * updatetime  
	 *  
	 * @return  the updatetime  
	 * @since   1.0.0  
	 */
	
	public Date getUpdatetime() {
		return updatetime;
	}
	/**  
	 * @param updatetime the updatetime to set  
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	/**  
	 * userrealname  
	 *  
	 * @return  the userrealname  
	 * @since   1.0.0  
	 */
	
	public String getUserrealname() {
		return userrealname;
	}
	/**  
	 * @param userrealname the userrealname to set  
	 */
	public void setUserrealname(String userrealname) {
		this.userrealname = userrealname;
	}
	/**  
	 * cid  
	 *  
	 * @return  the cid  
	 * @since   1.0.0  
	 */
	
	public String getCid() {
		return cid;
	}
	/**  
	 * @param cid the cid to set  
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**  
	 * usertype  
	 *  
	 * @return  the usertype  
	 * @since   1.0.0  
	 */
	
	public String getUsertype() {
		return usertype;
	}
	/**  
	 * @param usertype the usertype to set  
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	/**  
	 * registreason  
	 *  
	 * @return  the registreason  
	 * @since   1.0.0  
	 */
	
	public String getRegistreason() {
		return registreason;
	}
	/**  
	 * @param registreason the registreason to set  
	 */
	public void setRegistreason(String registreason) {
		this.registreason = registreason;
	}
	/**  
	 * remark  
	 *  
	 * @return  the remark  
	 * @since   1.0.0  
	 */
	
	public String getRemark() {
		return remark;
	}
	/**  
	 * @param remark the remark to set  
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**  
	 * record  
	 *  
	 * @return  the record  
	 * @since   1.0.0  
	 */
	
	public String getRecord() {
		return record;
	}
	/**  
	 * @param record the record to set  
	 */
	public void setRecord(String record) {
		this.record = record;
	}
	/**  
	 * urgestatus  
	 *  
	 * @return  the urgestatus  
	 * @since   1.0.0  
	 */
	
	public UrgeStatus getUrgestatus() {
		return urgestatus;
	}
	/**  
	 * @param urgestatus the urgestatus to set  
	 */
	public void setUrgestatus(UrgeStatus urgestatus) {
		this.urgestatus = urgestatus;
	}
	/**  
	 * recordList  
	 *  
	 * @return  the recordList  
	 * @since   1.0.0  
	 */
	
	public List<String> getRecordList() {
		return recordList;
	}
	/**  
	 * @param recordList the recordList to set  
	 */
	public void setRecordList(List<String> recordList) {
		this.recordList = recordList;
	}
	/**  
	 * owner  
	 *  
	 * @return  the owner  
	 * @since   1.0.0  
	 */
	
	public String getOwner() {
		return owner;
	}
	/**  
	 * @param owner the owner to set  
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
