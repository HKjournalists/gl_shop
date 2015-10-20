/**  
 * com.appabc.pay.bean.TPassbookDrawEx.java  
 *   
 * 2015年5月7日 上午10:18:17  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.PurseType;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年5月7日 上午10:18:17
 */

public class TPassbookDrawEx extends TPassbookDraw {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 204685533426880035L;

	/**
     * 钱包编号
     */
    private String passid;
    
    /**
     * 支付时间
     */
    private Date paytime;
    
    /**
	 * 企业编号
	 * */
	private String cid;
	
    /**
     * 保证金 或者 钱包
     */
    private PurseType passtype;

    /**
     * 总额
     */
    private Double balance;
    
    /**
     * 总额
     */
    private String username;
    
    /**
     * 总额
     */
    private String phone;

	/**  
	 * passid  
	 *  
	 * @return  the passid  
	 * @since   1.0.0  
	 */
	
	public String getPassid() {
		return passid;
	}

	/**  
	 * @param passid the passid to set  
	 */
	public void setPassid(String passid) {
		this.passid = passid;
	}

	/**  
	 * paytime  
	 *  
	 * @return  the paytime  
	 * @since   1.0.0  
	 */
	
	public Date getPaytime() {
		return paytime;
	}

	/**  
	 * @param paytime the paytime to set  
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
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
	 * passtype  
	 *  
	 * @return  the passtype  
	 * @since   1.0.0  
	 */
	
	public PurseType getPasstype() {
		return passtype;
	}

	/**  
	 * @param passtype the passtype to set  
	 */
	public void setPasstype(PurseType passtype) {
		this.passtype = passtype;
	}

	/**  
	 * username  
	 *  
	 * @return  the username  
	 * @since   1.0.0  
	 */
	
	public String getUsername() {
		return username;
	}

	/**  
	 * @param username the username to set  
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * balance  
	 *  
	 * @return  the balance  
	 * @since   1.0.0  
	*/  
	
	public Double getBalance() {
		return balance;
	}

	/**  
	 * @param balance the balance to set  
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
