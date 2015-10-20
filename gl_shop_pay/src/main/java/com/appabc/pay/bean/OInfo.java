/**  
 * com.appabc.pay.bean.OInfo.java  
 *   
 * 2014年10月8日 下午5:22:39  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月8日 下午5:22:39
 */

public class OInfo extends BaseBean{

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	private String oid;
	
	private Double amount;
	
	private Double total;

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
	 * amount  
	 *  
	 * @return  the amount  
	 * @since   1.0.0  
	 */
	
	public Double getAmount() {
		return amount;
	}

	/**  
	 * @param amount the amount to set  
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**  
	 * total  
	 *  
	 * @return  the total  
	 * @since   1.0.0  
	 */
	
	public Double getTotal() {
		return total;
	}

	/**  
	 * @param total the total to set  
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	
}
