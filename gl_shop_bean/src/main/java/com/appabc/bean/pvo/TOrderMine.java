package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月24日 下午5:19:05
 */

public class TOrderMine extends BaseBean {

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -2107590699536772453L;

	/**
     * 企业编号
     */
	private String  cid;
	
	/** 
	 * 订单编号
	 */
	private String  oid;
	
	/** 
	 * 订单状态(订单的进行中的状态)
	 */
	private ContractStatus status;

    /**
     * 订单生命周期
     */
    private ContractLifeCycle lifecycle;
	
	/**
	 * 备注
	 */
	private String  remark;
	
	/** 
	 * 创建人
	 */
	private String  creator;
	
	/** 
	 * 创建时间
	 */
	private Date   createtime;
	
	/** 
	 * 修改人
	 */
	private String  updater;  
	
	/** 
	 * 修改时间
	 */
	private Date   updatetime;

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
	 * oid  
	 *  
	 * @return  the oid  
	 * @since   1.0.0  
	 */
	
	public String getOid() {
		return oid;
	}

	/**  
	 * @param oid the oid to set  
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	 */
	
	public ContractStatus getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(ContractStatus status) {
		this.status = status;
	}

	/**  
	 * lifecycle  
	 *  
	 * @return  the lifecycle  
	 * @since   1.0.0  
	 */
	
	public ContractLifeCycle getLifecycle() {
		return lifecycle;
	}

	/**  
	 * @param lifecycle the lifecycle to set  
	 */
	public void setLifecycle(ContractLifeCycle lifecycle) {
		this.lifecycle = lifecycle;
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
	
}
