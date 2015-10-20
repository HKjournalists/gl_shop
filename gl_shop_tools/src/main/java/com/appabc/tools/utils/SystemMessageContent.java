/**
 *
 */
package com.appabc.tools.utils;

import java.util.Date;

import com.appabc.bean.enums.MsgInfo.MsgContent;
import com.appabc.bean.enums.MsgInfo.MsgPushIosContent;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.spring.BeanLocator;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月20日 下午9:20:03
 */
public class SystemMessageContent extends BaseBean{
	
	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	/**
	 * 消息内容
	 */
	private String content; // 默认消息
	private String contentIos; // IOS消息
	private static SystemParamsManager spm = (SystemParamsManager) BeanLocator.getBean("SystemParamsManager");
	
	public SystemMessageContent() {
	}
	
	public SystemMessageContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getContentIos() {
		return contentIos;
	}
	
	public SystemMessageContent setContentIos(String contentIos) {
		this.contentIos = contentIos;
		return this;
	}
	
	public void removeContentIos(){
		this.contentIos = null;
	}
	
	/**
	 * 其它设备登录的推送消息
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfUserLoginOtherDivce(ClientTypeEnum clientType) {
		String clientName = "另一台";
		if(clientType == null){
			clientName = "其它";
		}else{
			clientName += clientType.getText();
		}
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_000.getVal().replace("#Client#", clientName)).setContentIos(MsgPushIosContent.MSG_CONTENT_COMPANY_000.getVal().replace("#Client#", clientName));
	}
	
	/**
	 * 注册消息内容
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfUserRegister() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_001.getVal().replace("#TEL#", spm.getString(SystemConstant.CUSTOMER_SERVICE_TEL)));
	}
	
	/**
	 * 认证申请提交后系统消息
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfCompanyAuthIng() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_002.getVal());
	}
	
	/**
	 * 认证审核通过后消息
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfCompanyAuthYes() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_003.getVal()).setContentIos(MsgPushIosContent.MSG_CONTENT_COMPANY_003.getVal());
	}
	
	/**
	 * 认证审核不通过后消息推送
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfCompanyAuthNo() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_004.getVal()).setContentIos(MsgPushIosContent.MSG_CONTENT_COMPANY_004.getVal());
	}
	
	/**
	 * 合同签订通知
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractSign() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_001.getVal()).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_001.getVal());
	}
	
	/**
	 * 发货通知（合同签订成功，卖家）
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractSignSuccess(String contractId) {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_008.getVal().replace("#Number#", contractId).replace("#Num#", "48"));
	}
	
	/**
	 * 付款通知（合同签订成功，买家）
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractPayment(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_002.getVal().replace("#Number#", contractId).replace("#Num#", "48")).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_002.getVal());
	}
	
	/**
	 * 合同签订失败消息(缺少)
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractSignFailure() {
		return new SystemMessageContent(null);
	}
	
	/**
	 * 货物验收
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractAcceptanceGoods(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_003.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 合同议价，对方提出的议价通知(目前业务已不用)
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractNewBargaining(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_004.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 合同议价，对方回复通知(目前业务已不用)
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractReplyBargaining(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_010.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 确认收货
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractReceiptGoods(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_005.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 确认收货，超时通知
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractReceiptGoodsTimeout(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_006.getVal().replace("#Number#", contractId).replace("#Num#", "48"));
	}
	
	/**
	 * 合同完成提示消息
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractCompletion(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_007.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 买家确认收货后，卖家收到的提示
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractReceivingEndToBuyer(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_009.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 合同双方协商取消通知
	 * 触发条件 ：协商取消，客服发起合同需确认（买方，卖方）
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractCancel(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_011.getVal().replace("#Number#", contractId).replace("#TEL#", spm.getString(SystemConstant.CUSTOMER_SERVICE_TEL)));
	}
	
	/**
	 * 合同双方协商取消中通知
	 * 触发条件 ：协商取消，客服发起合同需确认（买方，卖方）
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractMutilCanceling(String contractId,Date time){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_016.getVal().replace("#Number#", contractId).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)));
	}
	/**
	 * 双方协商取消失败通知
	 * 触发条件 ：协商取消，其中一方未确认，合同状态为正常（买方，卖方）
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractCancelFailure(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_012.getVal().replace("#Number#", contractId).replace("#TEL#", spm.getString(SystemConstant.CUSTOMER_SERVICE_TEL)));
	}
	
	/**
	 * 合同的被取消方通知
	 * 触发条件 ：合同（对方）单方取消成功
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractCancelSuccessPassive(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_013.getVal().replace("#Number#", contractId).replace("#TEL#", spm.getString(SystemConstant.CUSTOMER_SERVICE_TEL)));
	}
	
	/**
	 * 合同的取消方通知
	 * 触发条件 ：合同（自己）单方取消成功
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractCancelSuccessActive(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_014.getVal().replace("#Number#", contractId).replace("#TEL#", spm.getString(SystemConstant.CUSTOMER_SERVICE_TEL)));
	}
	
	/**
	 * 合同双方未及时确认失效
	 * 触发条件 ：合同（自己）单方取消成功
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractBuyerSellerNotConfirm(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_BUYERSELLER_NOTCONFIRM.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_BUYERSELLER_NOTCONFIRM.getVal());
	}
	
	/**
	 * 合同单方确认等待对方确认
	 * 触发条件 ：合同单方确认等待对方确认
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractToWaitForYouConfirm(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_TO_WAITFOR_YOUCONFIRM.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_TO_WAITFOR_YOUCONFIRM.getVal());
	}
	
	/**
	 * 合同被卖方单方取消
	 * 触发条件 ：合同单方取消确认
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractDraftCancelWithSeller(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_DRAFTCANCEL_BYSELLER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_DRAFTCANCEL_BYSELLER.getVal());
	}
	
	/**
	 * 合同被买方单方取消
	 * 触发条件 ：合同单方取消确认
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractDraftCancelWithBuyer(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_DRAFTCANCEL_BYBUYER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_DRAFTCANCEL_BYBUYER.getVal());
	}
	
	/**
	 * 合同双方超时未确认失效
	 * 触发条件 ：合同单方取消确认
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
//	public static SystemMessageContent getMsgContentOfContractTimeoutconfirmWithBuyerOrSeller(String title){
//		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_TIMEOUTCONFIRM_BYSELLERORBUYER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_TIMEOUTCONFIRM_BYSELLERORBUYER.getVal());
//	}
	
	/**
	 * 合同买卖单方超时未确认失效
	 * 触发条件 ：合同单方取消确认
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractTimeoutconfirmWithBuyerOrSellerYouIsBuyer(String title,boolean isBuyer){
		String msg = "";
		if(isBuyer){
			msg = "（买方）";
		}else{
			msg = "（卖方）";
		}
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_TIMEOUTCONFIRM_BYSELLERORBUYERYOUISBUYER.getVal().replace("#Title#", title).replace("#isBuyer#", msg)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_TIMEOUTCONFIRM_BYSELLERORBUYERYOUISBUYER.getVal().replace("#isBuyer#", msg));
	}
	
	/**
	 * 合同买卖单方超时未确认失效
	 * 触发条件 ：合同单方取消确认
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractTimeoutconfirmWithBuyerOrSellerIsBuyer(String title,boolean isBuyer){
		String msg = "";
		if(isBuyer){
			msg = "买方";
		}else{
			msg = "卖方";
		}
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_TIMEOUTCONFIRM_BYSELLERORBUYERISBUYER.getVal().replace("#Title#", title).replace("#isBuyer#", msg)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_TIMEOUTCONFIRM_BYSELLERORBUYERISBUYER.getVal().replace("#isBuyer#", msg));
	}
	
	/**
	 * 合同签订提醒卖家去发货
	 * 触发条件 ：合同签订提醒
	 * @param title,hours 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractSignedToTipsSellerSendGoods(String title,int hours){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_SIGNED_TO_SELLERSENDGOODS.getVal().replace("#Title#", title).replace("#Hours#", String.valueOf(hours))).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_SIGNED_TO_SELLERSENDGOODS.getVal());
	}
	
	/**
	 * 合同签订提醒买家在多少小时内去支付货款
	 * 触发条件 ：合同签订提醒
	 * @param title,time 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractSignedToTipsBuyerPayFunds(String title,Date time){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_SIGNED_TO_BUYERPAYFUNDS.getVal().replace("#Title#", title).replace("#time#", DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS))).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_SIGNED_TO_BUYERPAYFUNDS.getVal());
	}
	
	/**
	 * 合同买家付款提醒卖家发货
	 * 触发条件 ：合同签订提醒
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractBuyerPayFundsTipsSellerSendGoods(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_BUYER_PAYFUNDS_TOTIPSSELLER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_BUYER_PAYFUNDS_TOTIPSSELLER.getVal());
	}
	
	/**
	 * 合同买家申请货物和货款确认
	 * 触发条件 ：合同买家申请货物和货款确认
	 * @param title,hours 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractApplyFundsGoodsConfirmWithBuyer(String title,int hours){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_APPLY_FUNDSGOODS_CONFIRM_BYBUYER.getVal().replace("#Title#", title).replaceAll("#Hours#", String.valueOf(hours))).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_APPLY_FUNDSGOODS_CONFIRM_BYBUYER.getVal());
	}
	
	/**
	 * 合同买家未支付货款提醒卖家
	 * 触发条件 ：合同买家未按时支付货款
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractBuyerUnpayfundsToSeller(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_BUYER_UNPAYFUNDS_TOSELLER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_BUYER_UNPAYFUNDS_TOSELLER.getVal());
	}
	
	/**
	 * 合同买家未支付货款提醒买家
	 * 触发条件 ：合同买家未按时支付货款
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractBuyerUnpayfundsToBuyer(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_BUYER_UNPAYFUNDS_TOBUYER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_BUYER_UNPAYFUNDS_TOBUYER.getVal());
	}
	
	/**
	 * 合同买方单方取消提示卖方
	 * 触发条件 ：合同买方单方取消
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractBuyerSingleCancelToSeller(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_BUYER_SINGLECANCEL_TOSELLER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_BUYER_SINGLECANCEL_TOSELLER.getVal());
	}
	
	/**
	 * 合同卖方单方取消提示买方
	 * 触发条件 ：合同卖方单方取消
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractSellerSingleCancelToBuyer(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_SELLER_SINGLECANCLE_TOBUYER.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_SELLER_SINGLECANCLE_TOBUYER.getVal());
	}
	
	/**
	 * 合同进行结束处理
	 * 触发条件 ：合同结束
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractFinished(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_FINISHED.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_FINISHED.getVal());
	}
	
	/**
	 * 合同买方申请了仲裁
	 * 触发条件 ：合同申请了仲裁
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractBuyerApplyArbitration(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_BUYER_APPLY_ARBITRATION.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_BUYER_APPLY_ARBITRATION.getVal());
	}
	
	/**
	 * 合同卖方申请了仲裁
	 * 触发条件 ：合同申请了仲裁
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractSellerApplyArbitration(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_SELLER_APPLY_ARBITRATION.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_SELLER_APPLY_ARBITRATION.getVal());
	}
	
	/**
	 * 合同进行了仲裁结束
	 * 触发条件 ：合同结束
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractArbitratedResult(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_ARBITRATED_RESULT.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_ARBITRATED_RESULT.getVal());
	}
	
	/**
	 * 合同评价进行了评价处理
	 * 触发条件 ：合同结束
	 * @param title 合同标题
	 * @return SystemMessageContent
	 */
	public static SystemMessageContent getMsgContentOfContractToEvaluation(String title){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_TO_EVALUATION.getVal().replace("#Title#", title)).setContentIos(MsgPushIosContent.MSG_CONTENT_CONTRACT_TO_EVALUATION.getVal());
	}
	
	/**
	 * 合同的单方确认通知
	 * 触发条件 ：合同（自己）单方确认成功
	 * @param contractId 合同编号
	 * @param time 确认时间
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractSingleConfirm(String contractId,Date time){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_015.getVal().replace("#Number#", contractId).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)));
	}
	
	/**
	 * 询单超时失效的消息
	 * 触发条件 ：询单失效
	 * @param title 询单标题
	 * @param time 时间
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfOrderFindTimeout(String title,Date time){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_ORDER_FIND_TIMEOUT.getVal().replace("#Title#", title).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM))).setContentIos(MsgPushIosContent.MSG_CONTENT_ORDER_FIND_TIMEOUT.getVal().replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM)));
	}
	
	/**
	 * 包钱金额变动消息
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoney(String accountName, Date time,String transactionType, float money) {
		return getMsgContentOfMoney(accountName, time, transactionType, String.valueOf(RandomUtil.round(money)));
		/*return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", accountName).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS))
				.replace("{transactionType}", transactionType).replace("{money}", money+""));*/
	}
	
	/**
	 * 包钱金额变动消息
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoney(String accountName, Date time,String transactionType, String money) {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", accountName).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS))
				.replace("{transactionType}", transactionType).replace("{money}", money))
				.setContentIos(MsgPushIosContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", accountName).replace("{transactionType}", transactionType).replace("{money}", money));
	}
	
	/**
	 * 包钱金额变动消息
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoney(PurseType purseType, Date time,TradeType tradeType, float money) {
		return getMsgContentOfMoney(purseType, time, tradeType, String.valueOf(RandomUtil.round(money)));
		/*return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", purseType.getText()).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS))
				.replace("{transactionType}", tradeType.getText()).replace("{money}", money+""));*/
	}
	
	/**
	 * 钱包货款转保证金的消息
	 * @param time 时间
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoneyOther(Date time,float money){
		return getMsgContentOfMoneyOther(time,String.valueOf(RandomUtil.round(money)));
	}
	
	/**
	 * 钱包货款转保证金的消息
	 * @param time 时间
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoneyOther(Date time,String money){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_002.getVal().replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)).replace("{money}", money)).setContentIos(MsgPushIosContent.MSG_CONTENT_MONEY_002.getVal().replace("{money}", money));
	}
	
	/**
	 * 包钱金额变动消息
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoney(PurseType purseType, Date time,TradeType tradeType, String money) {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", purseType.getText()).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS))
				.replace("{transactionType}", tradeType.getText()).replace("{money}", money))
				.setContentIos(MsgPushIosContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", purseType.getText()).replace("{transactionType}", tradeType.getText()).replace("{money}", money));
	}
	
}

