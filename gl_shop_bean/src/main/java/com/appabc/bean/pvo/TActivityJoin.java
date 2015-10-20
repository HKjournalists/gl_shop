package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月22日 下午5:04:08
 */

public class TActivityJoin extends BaseBean {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 3219033661080545742L;
	
	/**
     *  企业编号
     */
	private String cid;
	
	/**
     *  活动规则编号
     */
	private String arid;
	
	/**
     *  申请人手机号码
     */
	private String phone;
	
	/**
     *  申请人姓名
     */
	private String name;
	
	/**
     * 申请数量
     */
    private double reqnum;
    
    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createdate;
    
    /**
     *  更新时间
     */
    private Date updatedate;
    
    /**
     *  备注
     */
    private String remark;

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
	 * arid  
	 *  
	 * @return  the arid  
	 * @since   1.0.0  
	 */
	
	public String getArid() {
		return arid;
	}

	/**  
	 * @param arid the arid to set  
	 */
	public void setArid(String arid) {
		this.arid = arid;
	}

	/**  
	 * phone  
	 *  
	 * @return  the phone  
	 * @since   1.0.0  
	 */
	
	public String getPhone() {
		return phone;
	}

	/**  
	 * @param phone the phone to set  
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**  
	 * name  
	 *  
	 * @return  the name  
	 * @since   1.0.0  
	 */
	
	public String getName() {
		return name;
	}

	/**  
	 * @param name the name to set  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * reqnum  
	 *  
	 * @return  the reqnum  
	 * @since   1.0.0  
	 */
	
	public double getReqnum() {
		return reqnum;
	}

	/**  
	 * @param reqnum the reqnum to set  
	 */
	public void setReqnum(double reqnum) {
		this.reqnum = reqnum;
	}

	/**  
	 * creator  
	 *  
	 * @return  the creator  
	 * @since   1.0.0  
	 */
	
	public String getCreator() {
		return creator;
	}

	/**  
	 * @param creator the creator to set  
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**  
	 * createdate  
	 *  
	 * @return  the createdate  
	 * @since   1.0.0  
	 */
	
	public Date getCreatedate() {
		return createdate;
	}

	/**  
	 * @param createdate the createdate to set  
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**  
	 * updatedate  
	 *  
	 * @return  the updatedate  
	 * @since   1.0.0  
	 */
	
	public Date getUpdatedate() {
		return updatedate;
	}

	/**  
	 * @param updatedate the updatedate to set  
	 */
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
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
    
}
