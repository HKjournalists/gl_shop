package com.appabc.bean.bo;

import java.util.List;

import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TOrderInfo;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月16日 下午4:36:12
 */

public class ContractInfoBean extends TOrderInfo{

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	/**
     * 销售类型
     */
	private OrderTypeEnum saleType;
	/**
     * 产品类型
     */
	private String productName;
	/**
     * 卖家名称
     */
	private String sellerName;
	/**
     * 买家名称
     */
	private String buyerName;
	/**
     * 买家状态
     */
	private Object buyerStatus;
	/**
     * 卖家状态
     */
	private Object sellerStatus;
	/**
     * 第一次议价记录(抽样验收)
     */
	private List<?> sampleCheckDisPriceList;
	/**
     * 第二次议价记录(全量检查)
     */
	private List<?> fullTakeoverDisPriceList;
	
	/**  
	 * sampleCheckDisPriceList  
	 *  
	 * @return  the sampleCheckDisPriceList  
	 * @since   1.0.0  
	 */
	
	public List<?> getSampleCheckDisPriceList() {
		return sampleCheckDisPriceList;
	}

	/**  
	 * @param sampleCheckDisPriceList the sampleCheckDisPriceList to set  
	 */
	public void setSampleCheckDisPriceList(List<?> sampleCheckDisPriceList) {
		this.sampleCheckDisPriceList = sampleCheckDisPriceList;
	}

	/**  
	 * fullTakeoverDisPriceList  
	 *  
	 * @return  the fullTakeoverDisPriceList  
	 * @since   1.0.0  
	 */
	
	public List<?> getFullTakeoverDisPriceList() {
		return fullTakeoverDisPriceList;
	}

	/**  
	 * @param fullTakeoverDisPriceList the fullTakeoverDisPriceList to set  
	 */
	public void setFullTakeoverDisPriceList(List<?> fullTakeoverDisPriceList) {
		this.fullTakeoverDisPriceList = fullTakeoverDisPriceList;
	}

	/**  
	 * productName  
	 *  
	 * @return  the productName  
	 * @since   1.0.0  
	 */
	
	public String getProductName() {
		return productName;
	}

	/**  
	 * @param productName the productName to set  
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**  
	 * sellerName  
	 *  
	 * @return  the sellerName  
	 * @since   1.0.0  
	 */
	
	public String getSellerName() {
		return sellerName;
	}

	/**  
	 * @param sellerName the sellerName to set  
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**  
	 * buyerName  
	 *  
	 * @return  the buyerName  
	 * @since   1.0.0  
	 */
	
	public String getBuyerName() {
		return buyerName;
	}

	/**  
	 * @param buyerName the buyerName to set  
	 */
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	/**  
	 * buyerStatus  
	 *  
	 * @return  the buyerStatus  
	 * @since   1.0.0  
	 */
	
	public Object getBuyerStatus() {
		return buyerStatus;
	}

	/**  
	 * @param buyerStatus the buyerStatus to set  
	 */
	public void setBuyerStatus(Object buyerStatus) {
		this.buyerStatus = buyerStatus;
	}

	/**  
	 * sellerStatus  
	 *  
	 * @return  the sellerStatus  
	 * @since   1.0.0  
	 */
	
	public Object getSellerStatus() {
		return sellerStatus;
	}

	/**  
	 * @param sellerStatus the sellerStatus to set  
	 */
	public void setSellerStatus(Object sellerStatus) {
		this.sellerStatus = sellerStatus;
	}

	/**  
	 * saleType  
	 *  
	 * @return  the saleType  
	 * @since   1.0.0  
	*/  
	
	public OrderTypeEnum getSaleType() {
		return saleType;
	}

	/**  
	 * @param saleType the saleType to set  
	 */
	public void setSaleType(OrderTypeEnum saleType) {
		this.saleType = saleType;
	}

}
