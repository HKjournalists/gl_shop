package com.appabc.bean.bo;

import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 撮合合同的相关信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月12日 下午2:54:55
 */

public class MatchOrderInfo extends BaseBean {
	
	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 200476117816235895L;

	/**
	 * 询单ID
	 * */
	private String fid;
	
	/**
	 * 询单信息
	 * */
	private TOrderFind orderFind;
	
	/**
	 * 撮合的用户的CID
	 * */
	private String cid;
	
	/**
	 * 撮合生成合同的货物总量
	 * */
	private Float totalNum;
	
	/**
	 * 撮合生成合同的货物价格
	 * */
	private Float price;

	/**
	 * 撮合生成合同的操作人
	 * */
	private String operator;
	/**  
	 * fid  
	 *  
	 * @return  the fid  
	 * @since   1.0.0  
	 */
	
	public String getFid() {
		return fid;
	}

	/**  
	 * @param fid the fid to set  
	 */
	public void setFid(String fid) {
		this.fid = fid;
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
	 * totalNum  
	 *  
	 * @return  the totalNum  
	 * @since   1.0.0  
	 */
	
	public Float getTotalNum() {
		return totalNum;
	}

	/**  
	 * @param totalNum the totalNum to set  
	 */
	public void setTotalNum(Float totalNum) {
		this.totalNum = totalNum;
	}

	/**  
	 * price  
	 *  
	 * @return  the price  
	 * @since   1.0.0  
	 */
	
	public Float getPrice() {
		return price;
	}

	/**  
	 * @param price the price to set  
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/**  
	 * operator  
	 *  
	 * @return  the operator  
	 * @since   1.0.0  
	*/  
	
	public String getOperator() {
		return operator;
	}

	/**  
	 * @param operator the operator to set  
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**  
	 * orderFind  
	 *  
	 * @return  the orderFind  
	 * @since   1.0.0  
	*/  
	
	public TOrderFind getOrderFind() {
		return orderFind;
	}

	/**  
	 * @param orderFind the orderFind to set  
	 */
	public void setOrderFind(TOrderFind orderFind) {
		this.orderFind = orderFind;
	}
	
}
