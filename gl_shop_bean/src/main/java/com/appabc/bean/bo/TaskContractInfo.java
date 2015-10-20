package com.appabc.bean.bo;

import java.util.Date;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年6月30日 下午6:10:04
 */

public class TaskContractInfo extends BaseBean {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	private String oid;
	
	private String sellerid;
	
	private String sellerPhone;

    private String buyerid;
    
    private String buyerPhone;
    
    private ContractType otype;

    private ContractStatus status;

    private ContractLifeCycle lifecycle;
    
    private Date creatime;
    
    private String limitTimeStr;
    
    private String operUserName;
	
    private String remark;

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
	 * sellerid  
	 *  
	 * @return  the sellerid  
	 * @since   1.0.0  
	 */
	
	public String getSellerid() {
		return sellerid;
	}

	/**  
	 * @param sellerid the sellerid to set  
	 */
	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	/**  
	 * sellerPhone  
	 *  
	 * @return  the sellerPhone  
	 * @since   1.0.0  
	 */
	
	public String getSellerPhone() {
		return sellerPhone;
	}

	/**  
	 * @param sellerPhone the sellerPhone to set  
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	/**  
	 * buyerid  
	 *  
	 * @return  the buyerid  
	 * @since   1.0.0  
	 */
	
	public String getBuyerid() {
		return buyerid;
	}

	/**  
	 * @param buyerid the buyerid to set  
	 */
	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	/**  
	 * buyerPhone  
	 *  
	 * @return  the buyerPhone  
	 * @since   1.0.0  
	 */
	
	public String getBuyerPhone() {
		return buyerPhone;
	}

	/**  
	 * @param buyerPhone the buyerPhone to set  
	 */
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
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
	 * otype  
	 *  
	 * @return  the otype  
	 * @since   1.0.0  
	 */
	
	public ContractType getOtype() {
		return otype;
	}

	/**  
	 * @param otype the otype to set  
	 */
	public void setOtype(ContractType otype) {
		this.otype = otype;
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
	 * creatime  
	 *  
	 * @return  the creatime  
	 * @since   1.0.0  
	 */
	
	public Date getCreatime() {
		return creatime;
	}

	/**  
	 * @param creatime the creatime to set  
	 */
	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	/**  
	 * limitTimeStr  
	 *  
	 * @return  the limitTimeStr  
	 * @since   1.0.0  
	 */
	
	public String getLimitTimeStr() {
		return limitTimeStr;
	}

	/**  
	 * @param limitTimeStr the limitTimeStr to set  
	 */
	public void setLimitTimeStr(String limitTimeStr) {
		this.limitTimeStr = limitTimeStr;
	}

	/**  
	 * operUserName  
	 *  
	 * @return  the operUserName  
	 * @since   1.0.0  
	 */
	
	public String getOperUserName() {
		return operUserName;
	}

	/**  
	 * @param operUserName the operUserName to set  
	 */
	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
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
