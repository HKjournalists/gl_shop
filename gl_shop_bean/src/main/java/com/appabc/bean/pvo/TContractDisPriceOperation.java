package com.appabc.bean.pvo;

import java.util.Date;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月11日 下午4:30:14
 */

public class TContractDisPriceOperation extends TOrderOperations {

	/**  
	 * serialVersionUID  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

	private String cid;
	/**
     * 操作编号
     */
    private String dlid;

    /**
     * 抽样验收、全部验收
     */
    private Integer dtype;

    /**
     * 变更人
     */
    private String canceler;

    /**
     * 变更时间
     */
    private Date canceltime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 操作金额起始
     */
    private Float beginamount;

    /**
     * 操作金额结束
     */
    private Float endamount;

    /**
     * 操作数量起始
     */
    private Float beginnum;

    /**
     * 操作数量结束
     */
    private Float endnum;

    /**
     * 处罚原因
     */
    private String punreason;

    /**
     * 处罚天数
     */
    private Integer punday;

    /**
     * 备注
     */
    private String dremark;

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
	 * dlid  
	 *  
	 * @return  the dlid  
	 * @since   1.0.0  
	 */
	
	public String getDlid() {
		return dlid;
	}

	/**  
	 * @param dlid the dlid to set  
	 */
	public void setDlid(String dlid) {
		this.dlid = dlid;
	}

	/**  
	 * dtype  
	 *  
	 * @return  the dtype  
	 * @since   1.0.0  
	 */
	
	public Integer getDtype() {
		return dtype;
	}

	/**  
	 * @param dtype the dtype to set  
	 */
	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}

	/**  
	 * canceler  
	 *  
	 * @return  the canceler  
	 * @since   1.0.0  
	 */
	
	public String getCanceler() {
		return canceler;
	}

	/**  
	 * @param canceler the canceler to set  
	 */
	public void setCanceler(String canceler) {
		this.canceler = canceler;
	}

	/**  
	 * canceltime  
	 *  
	 * @return  the canceltime  
	 * @since   1.0.0  
	 */
	
	public Date getCanceltime() {
		return canceltime;
	}

	/**  
	 * @param canceltime the canceltime to set  
	 */
	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}

	/**  
	 * reason  
	 *  
	 * @return  the reason  
	 * @since   1.0.0  
	 */
	
	public String getReason() {
		return reason;
	}

	/**  
	 * @param reason the reason to set  
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**  
	 * beginamount  
	 *  
	 * @return  the beginamount  
	 * @since   1.0.0  
	 */
	
	public Float getBeginamount() {
		return beginamount;
	}

	/**  
	 * @param beginamount the beginamount to set  
	 */
	public void setBeginamount(Float beginamount) {
		this.beginamount = beginamount;
	}

	/**  
	 * endamount  
	 *  
	 * @return  the endamount  
	 * @since   1.0.0  
	 */
	
	public Float getEndamount() {
		return endamount;
	}

	/**  
	 * @param endamount the endamount to set  
	 */
	public void setEndamount(Float endamount) {
		this.endamount = endamount;
	}

	/**  
	 * beginnum  
	 *  
	 * @return  the beginnum  
	 * @since   1.0.0  
	 */
	
	public Float getBeginnum() {
		return beginnum;
	}

	/**  
	 * @param beginnum the beginnum to set  
	 */
	public void setBeginnum(Float beginnum) {
		this.beginnum = beginnum;
	}

	/**  
	 * endnum  
	 *  
	 * @return  the endnum  
	 * @since   1.0.0  
	 */
	
	public Float getEndnum() {
		return endnum;
	}

	/**  
	 * @param endnum the endnum to set  
	 */
	public void setEndnum(Float endnum) {
		this.endnum = endnum;
	}

	/**  
	 * punreason  
	 *  
	 * @return  the punreason  
	 * @since   1.0.0  
	 */
	
	public String getPunreason() {
		return punreason;
	}

	/**  
	 * @param punreason the punreason to set  
	 */
	public void setPunreason(String punreason) {
		this.punreason = punreason;
	}

	/**  
	 * punday  
	 *  
	 * @return  the punday  
	 * @since   1.0.0  
	 */
	
	public Integer getPunday() {
		return punday;
	}

	/**  
	 * @param punday the punday to set  
	 */
	public void setPunday(Integer punday) {
		this.punday = punday;
	}

	/**  
	 * dremark  
	 *  
	 * @return  the dremark  
	 * @since   1.0.0  
	 */
	
	public String getDremark() {
		return dremark;
	}

	/**  
	 * @param dremark the dremark to set  
	 */
	public void setDremark(String dremark) {
		this.dremark = dremark;
	}
	
}
