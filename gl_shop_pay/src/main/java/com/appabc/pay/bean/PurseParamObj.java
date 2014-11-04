/**  
 * com.appabc.pay.bean.PurseParamObj.java  
 *   
 * 2014年9月29日 下午1:59:06  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.bean;

import java.util.HashMap;
import java.util.Map;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月29日 下午1:59:06
 */

public class PurseParamObj extends BaseBean {

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	/*企业编号*/
	private String cid;
	/*钱包类型*/
	private String type;
	/*源地址*/
	private String sourPassId;
	/*目标地址*/
	private String destPassId;
	/*金额*/
	private Float balance;
	/*合同编号*/
	private String contractId;
	/*提取ID*/
	private String tid;
	/*支付流水号*/
	private String pid;
	/*收款人账户ID*/
	private String acceptId;
	/*银行流水号*/
	private String payNo;
	/*其他多余的参数集合*/
	private Map<String,Object> params = new HashMap<String,Object>();

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
	 * type  
	 *  
	 * @return  the type  
	 * @since   1.0.0  
	 */
	
	public String getType() {
		return type;
	}

	/**  
	 * @param type the type to set  
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**  
	 * sourPassId  
	 *  
	 * @return  the sourPassId  
	 * @since   1.0.0  
	 */
	
	public String getSourPassId() {
		return sourPassId;
	}

	/**  
	 * @param sourPassId the sourPassId to set  
	 */
	public void setSourPassId(String sourPassId) {
		this.sourPassId = sourPassId;
	}

	/**  
	 * destPassId  
	 *  
	 * @return  the destPassId  
	 * @since   1.0.0  
	 */
	
	public String getDestPassId() {
		return destPassId;
	}

	/**  
	 * @param destPassId the destPassId to set  
	 */
	public void setDestPassId(String destPassId) {
		this.destPassId = destPassId;
	}

	/**  
	 * balance  
	 *  
	 * @return  the balance  
	 * @since   1.0.0  
	 */
	
	public Float getBalance() {
		return balance;
	}

	/**  
	 * @param balance the balance to set  
	 */
	public void setBalance(Float balance) {
		this.balance = balance;
	}

	/**  
	 * acceptId  
	 *  
	 * @return  the acceptId  
	 * @since   1.0.0  
	 */
	
	public String getAcceptId() {
		return acceptId;
	}

	/**  
	 * @param acceptId the acceptId to set  
	 */
	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}

	/**  
	 * contractId  
	 *  
	 * @return  the contractId  
	 * @since   1.0.0  
	 */
	
	public String getContractId() {
		return contractId;
	}

	/**  
	 * @param contractId the contractId to set  
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**  
	 * tid  
	 *  
	 * @return  the tid  
	 * @since   1.0.0  
	 */
	
	public String getTid() {
		return tid;
	}

	/**  
	 * @param tid the tid to set  
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

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
	 * params  
	 *  
	 * @return  the params  
	 * @since   1.0.0  
	 */
	
	public Map<String, Object> getParams() {
		return params;
	}

	/**  
	 * @param params the params to set  
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	/**  
	 * @param key,value the params to set  
	 */
	public void addParams(String key,Object value){
		this.params.put(key, value);
	}
	
	/**  
	 * @param key the params to get  
	 */
	public Object getParams(String key){
		return this.params.get(key);
	}

	/**  
	 * payNo  
	 *  
	 * @return  the payNo  
	 * @since   1.0.0  
	 */
	
	public String getPayNo() {
		return payNo;
	}

	/**  
	 * @param payNo the payNo to set  
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	
}
