package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 电话号码包
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月16日 下午2:20:36
 */

public class TPhonePackage extends BaseBean {

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -2509002707930475157L;

	/**
     * 包编号
     */
	private String pid;
	
	/**
     * 包文件名称
     */
	private String pname;
	
	/**
     * 手机号码
     */
	private String phone;
	
	/**
     * 企业类型（区分企业、船舶、个人）
     */
    private CompanyType ctype;
    
    /**
     * 企业编号
     */
    private String cid;
    
    /**
     * 企业名称
     */
    private String cname;
    
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
	 * pid  
	 *  
	 * @return  the pid  
	 * @since   1.0.0  
	 */
	
	public String getPid() {
		return pid;
	}

	/**  
	 * @param pid the pid to set  
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**  
	 * pname  
	 *  
	 * @return  the pname  
	 * @since   1.0.0  
	 */
	
	public String getPname() {
		return pname;
	}

	/**  
	 * @param pname the pname to set  
	 */
	public void setPname(String pname) {
		this.pname = pname;
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
	 * ctype  
	 *  
	 * @return  the ctype  
	 * @since   1.0.0  
	 */
	
	public CompanyType getCtype() {
		return ctype;
	}

	/**  
	 * @param ctype the ctype to set  
	 */
	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
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
	 * cname  
	 *  
	 * @return  the cname  
	 * @since   1.0.0  
	 */
	
	public String getCname() {
		return cname;
	}

	/**  
	 * @param cname the cname to set  
	 */
	public void setCname(String cname) {
		this.cname = cname;
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
    
}
