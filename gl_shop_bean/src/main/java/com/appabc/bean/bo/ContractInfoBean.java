package com.appabc.bean.bo;

import java.util.Date;
import java.util.List;

import com.appabc.bean.enums.ContractInfo.ContractOperateType;
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
     * 产品名称
     */
	private String productName;
	/**
     * 产品ID
     */
	private String productId;
	/**
     * 产品CODE
     */
	private String productCode;
	/**
     * 产品类型
     */
	private String productType;
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
     * 买家起草状态
     */
	private Object buyerDraftStatus;
	/**
     * 卖家起草状态
     */
	private Object sellerDraftStatus;
	/**
     * 起草确认期限
     */
	private Date draftLimitTime;
	/**
     * 买家付款期限
     */
	private Date payGoodsLimitTime;
	/**
     * 卖家同意最终确认期限
     */
	private Date agreeFinalEstimeLimitTime;
	/**
	 * 当前生命周期的操作人
	 * */
	private String operator;
	/**
	 * 当前生命周期的操作时间
	 * */
	private Date operationTime;
	/**
	 * 当前生命周期的操作类型
	 * */
	private ContractOperateType operateType;
	/**
     * 第一次议价记录(抽样验收)
     */
	private List<?> sampleCheckDisPriceList;
	/**
     * 第二次议价记录(全量检查)
     */
	private List<?> fullTakeoverDisPriceList;
	/**
	 * 第三次议价记录(货款议价)
	 * */
	private List<?> fundGoodsDisPriceList;
	/**
	 * 第四次议价记录(仲裁议价)
	 * */
	private List<?> arbitrationDisPriceList;
	/**
     * 合同结算清单
     */
	private List<?> finalEstimateList;
	
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

	/**  
	 * finalEstimateList  
	 *  
	 * @return  the finalEstimateList  
	 * @since   1.0.0  
	*/  
	
	public List<?> getFinalEstimateList() {
		return finalEstimateList;
	}

	/**  
	 * @param finalEstimateList the finalEstimateList to set  
	 */
	public void setFinalEstimateList(List<?> finalEstimateList) {
		this.finalEstimateList = finalEstimateList;
	}

	/**  
	 * productType  
	 *  
	 * @return  the productType  
	 * @since   1.0.0  
	*/  
	
	public String getProductType() {
		return productType;
	}

	/**  
	 * @param productType the productType to set  
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**  
	 * productCode  
	 *  
	 * @return  the productCode  
	 * @since   1.0.0  
	*/  
	
	public String getProductCode() {
		return productCode;
	}

	/**  
	 * @param productCode the productCode to set  
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**  
	 * productId  
	 *  
	 * @return  the productId  
	 * @since   1.0.0  
	*/  
	
	public String getProductId() {
		return productId;
	}

	/**  
	 * @param productId the productId to set  
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**  
	 * fundGoodsDisPriceList  
	 *  
	 * @return  the fundGoodsDisPriceList  
	 * @since   1.0.0  
	*/  
	
	public List<?> getFundGoodsDisPriceList() {
		return fundGoodsDisPriceList;
	}

	/**  
	 * @param fundGoodsDisPriceList the fundGoodsDisPriceList to set  
	 */
	public void setFundGoodsDisPriceList(List<?> fundGoodsDisPriceList) {
		this.fundGoodsDisPriceList = fundGoodsDisPriceList;
	}

	/**  
	 * arbitrationDisPriceList  
	 *  
	 * @return  the arbitrationDisPriceList  
	 * @since   1.0.0  
	*/  
	
	public List<?> getArbitrationDisPriceList() {
		return arbitrationDisPriceList;
	}

	/**  
	 * @param arbitrationDisPriceList the arbitrationDisPriceList to set  
	 */
	public void setArbitrationDisPriceList(List<?> arbitrationDisPriceList) {
		this.arbitrationDisPriceList = arbitrationDisPriceList;
	}

	/**  
	 * draftLimitTime  
	 *  
	 * @return  the draftLimitTime  
	 * @since   1.0.0  
	*/  
	
	public Date getDraftLimitTime() {
		return draftLimitTime;
	}

	/**  
	 * @param draftLimitTime the draftLimitTime to set  
	 */
	public void setDraftLimitTime(Date draftLimitTime) {
		this.draftLimitTime = draftLimitTime;
	}

	/**  
	 * payGoodsLimitTime  
	 *  
	 * @return  the payGoodsLimitTime  
	 * @since   1.0.0  
	*/  
	
	public Date getPayGoodsLimitTime() {
		return payGoodsLimitTime;
	}

	/**  
	 * @param payGoodsLimitTime the payGoodsLimitTime to set  
	 */
	public void setPayGoodsLimitTime(Date payGoodsLimitTime) {
		this.payGoodsLimitTime = payGoodsLimitTime;
	}

	/**  
	 * buyerDraftStatus  
	 *  
	 * @return  the buyerDraftStatus  
	 * @since   1.0.0  
	*/  
	
	public Object getBuyerDraftStatus() {
		return buyerDraftStatus;
	}

	/**  
	 * @param buyerDraftStatus the buyerDraftStatus to set  
	 */
	public void setBuyerDraftStatus(Object buyerDraftStatus) {
		this.buyerDraftStatus = buyerDraftStatus;
	}

	/**  
	 * sellerDraftStatus  
	 *  
	 * @return  the sellerDraftStatus  
	 * @since   1.0.0  
	*/  
	
	public Object getSellerDraftStatus() {
		return sellerDraftStatus;
	}

	/**  
	 * @param sellerDraftStatus the sellerDraftStatus to set  
	 */
	public void setSellerDraftStatus(Object sellerDraftStatus) {
		this.sellerDraftStatus = sellerDraftStatus;
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
	 * operationTime  
	 *  
	 * @return  the operationTime  
	 * @since   1.0.0  
	*/  
	
	public Date getOperationTime() {
		return operationTime;
	}

	/**  
	 * @param operationTime the operationTime to set  
	 */
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	/**  
	 * operateType  
	 *  
	 * @return  the operateType  
	 * @since   1.0.0  
	*/  
	
	public ContractOperateType getOperateType() {
		return operateType;
	}

	/**  
	 * @param operateType the operateType to set  
	 */
	public void setOperateType(ContractOperateType operateType) {
		this.operateType = operateType;
	}

	/**  
	 * agreeFinalEstimeLimitTime  
	 *  
	 * @return  the agreeFinalEstimeLimitTime  
	 * @since   1.0.0  
	*/  
	
	public Date getAgreeFinalEstimeLimitTime() {
		return agreeFinalEstimeLimitTime;
	}

	/**  
	 * @param agreeFinalEstimeLimitTime the agreeFinalEstimeLimitTime to set  
	 */
	public void setAgreeFinalEstimeLimitTime(Date agreeFinalEstimeLimitTime) {
		this.agreeFinalEstimeLimitTime = agreeFinalEstimeLimitTime;
	}

}
