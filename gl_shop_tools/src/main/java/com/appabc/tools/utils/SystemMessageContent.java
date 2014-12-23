/**
 *
 */
package com.appabc.tools.utils;

import java.util.Date;

import com.appabc.bean.enums.MsgInfo.MsgContent;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.common.spring.BeanLocator;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.SystemConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月20日 下午9:20:03
 */
public class SystemMessageContent {
	
	/**
	 * 消息内容
	 */
	private String content;
	private static SystemParamsManager spm = (SystemParamsManager) BeanLocator.getBean("SystemParamsManager");
	
	public SystemMessageContent() {
	}
	public SystemMessageContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
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
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_000.getVal().replace("#Client#", clientName));
	}
	
	/**
	 * 注册消息内容
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfUserRegister() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_001.getVal().replace("#TEL#", spm.getString(SystemConstant.CUSTOMER_SERVICE_TEL)));
	}
	
	/**
	 * 认证申请提交后消息推送
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
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_003.getVal());
	}
	
	/**
	 * 认证审核通过后消息推送
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfCompanyAuthNo() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_COMPANY_004.getVal());
	}
	
	/**
	 * 合同签订通知
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractSign() {
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_001.getVal());
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
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_002.getVal().replace("#Number#", contractId).replace("#Num#", "48"));
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
	 * 合同议价，对方提出的议价通知
	 * @param contractId 合同编号
	 * @return
	 */
	public static SystemMessageContent getMsgContentOfContractNewBargaining(String contractId){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_CONTRACT_004.getVal().replace("#Number#", contractId));
	}
	
	/**
	 * 合同议价，对方回复通知
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
	 * 包钱金额变动消息
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoney(String accountName, Date time,String transactionType, float money) {
		return getMsgContentOfMoney(accountName, time, transactionType, String.valueOf(money));
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
				.replace("{transactionType}", transactionType).replace("{money}", money));
	}
	
	/**
	 * 包钱金额变动消息
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoney(PurseType purseType, Date time,TradeType tradeType, float money) {
		return getMsgContentOfMoney(purseType, time, tradeType, String.valueOf(money));
		/*return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_001.getVal().replace("{accountName}", purseType.getText()).replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS))
				.replace("{transactionType}", tradeType.getText()).replace("{money}", money+""));*/
	}
	
	/**
	 * 钱包货款转保证金的消息
	 * @param time 时间
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoneyOther(Date time,float money){
		return getMsgContentOfMoneyOther(time,String.valueOf(money));
	}
	
	/**
	 * 钱包货款转保证金的消息
	 * @param time 时间
	 * @param money 金额；例：50000
	 */
	public static SystemMessageContent getMsgContentOfMoneyOther(Date time,String money){
		return new SystemMessageContent(MsgContent.MSG_CONTENT_MONEY_002.getVal().replace("{time}",DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)).replace("{money}", money));
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
				.replace("{transactionType}", tradeType.getText()).replace("{money}", money));
	}
	
}

