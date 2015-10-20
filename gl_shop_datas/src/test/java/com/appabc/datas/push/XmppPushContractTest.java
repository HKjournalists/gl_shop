package com.appabc.datas.push;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月29日 下午1:55:52
 */

public class XmppPushContractTest extends AbstractDatasTest {

	@Autowired
	protected MessageSendManager mesgSender;
	
	@Autowired
	private IContractDisPriceService iContractDisPriceService;
	
	@Autowired
	private IContractInfoService iContractInfoService;
	
	private String oid = "1120150306319";
	
	private String seller = "CompanyInfoId000000811102014END";
	
	private String buyer = "CompanyInfoId000000811102014END";
	
	private String title = "出售黄砂·山砂·中砂(2.3-3.0)mm 500.0吨 24.6元/吨";
	
	/**
	 * @Description : 发送消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg){
		MessageInfoBean mi = new MessageInfoBean(businessType,businessId,cid,content);
		mi.setSendSystemMsg(systemMsg);
		mi.setSendShotMsg(shortMsg);
		mi.setSendPushMsg(xmppMsg);
		mesgSender.msgSend(mi);
	}
	
	/**
	 * @Description : 发送消息接口,包含系统消息,短消息,xmpp消息
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendAllMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, true, true, true);
	}
	
	/**
	 * @Description : 发送系统消息和xmpp消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendSystemXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, true, false, true);
	}
	
	/**
	 * @Description : 发送xmpp消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendOnlyXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, false, false, true);
	};
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		//testMakeAndMatchContract();
		/*TOrderInfo bean = iContractInfoService.query(oid);
		try {
			iContractDisPriceService.jobAutoAgreeContractFinalEstimate(bean, ToolsConstant.SYSTEMCID, ToolsConstant.SYSTEMCNAME, StringUtils.EMPTY);
		} catch (ServiceException e) {
			e.printStackTrace();
		}*/
	}
	
	//撮合合同的信息
	public void testMakeAndMatchContract(){
		MsgBusinessType type = MsgBusinessType.BUSINESS_TYPE_CONTRACT_MAKE_MATCH;
		SystemMessageContent message = SystemMessageContent.getMsgContentOfContractSign();
		
		this.sendOnlyXmppMessage(type, oid, buyer, message);
		this.sendOnlyXmppMessage(type, oid, seller, message);
	}
	
	//单方取消确认
	public void testSingleDafCancleConfirm(){
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SINGLE_DAF_CONFIRM, oid, buyer, SystemMessageContent.getMsgContentOfContractToWaitForYouConfirm(title));
	}
	
	//起草取消给对方
	public void testDafratCancelToYou(){
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_DAF_CANCEL, oid, seller, SystemMessageContent.getMsgContentOfContractDraftCancelWithBuyer(title));
	}
	
	//双方起草确认(给对方)
	public void testsDafConfirmToYou(){
		Date now = DateUtil.getNowDate();
		String userId = "CompanyInfoId000000811102014END";
		if(StringUtils.equalsIgnoreCase(userId, buyer)){
			//合同签订后,提醒卖家发货.
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN, oid, seller, SystemMessageContent.getMsgContentOfContractSignedToTipsSellerSendGoods(title,ContractCostDetailUtil.getContractPayGoodsLimitNum()));
		}else if(StringUtils.equalsIgnoreCase(userId, seller)){
			//合同签订后,提醒买家付款操作.
			Date d = DateUtil.getDate(now, ContractCostDetailUtil.getContractPayGoodsLimitNum());
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN, oid, buyer, SystemMessageContent.getMsgContentOfContractSignedToTipsBuyerPayFunds(title,d));
		}
	}
	
	//起草超时
	public void testContractDafTimeout(){
		//send the system message and xmpp message
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_DAF_TIMEOUT, oid, seller, SystemMessageContent.getMsgContentOfContractBuyerSellerNotConfirm(title));
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_DAF_TIMEOUT, oid, buyer, SystemMessageContent.getMsgContentOfContractBuyerSellerNotConfirm(title));

	}
	
	//买方付款
	public void testContractBuyerPayFundsToSeller(){
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_BUYER_PAYFUNDS, oid, seller, SystemMessageContent.getMsgContentOfContractBuyerPayFundsTipsSellerSendGoods(title));
	}
	
	//买方申请结算
	public void testContractBuyerApplyFinalEstimate(){
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE, oid, seller, SystemMessageContent.getMsgContentOfContractApplyFundsGoodsConfirmWithBuyer(title,ContractCostDetailUtil.getContractAgreeFinalEstimateLimitNum()));
	}
	
	//卖方同意结算
	public void testContractSellerAgreeFinalEstimate(){
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, oid, seller, SystemMessageContent.getMsgContentOfContractToEvaluation(title));
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, oid, buyer, SystemMessageContent.getMsgContentOfContractToEvaluation(title));
	}
	
	//申请仲裁（给对方）
	public void testContractApplyArbitrationToYou(){
		String cid = "CompanyInfoId000000811102014END";
		if(StringUtils.equalsIgnoreCase(cid, buyer)){
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_APPLY_ARBITRATION, oid, seller, SystemMessageContent.getMsgContentOfContractBuyerApplyArbitration(title));
		} else {
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_APPLY_ARBITRATION, oid, buyer, SystemMessageContent.getMsgContentOfContractSellerApplyArbitration(title));
		}
	}
	
	//仲裁结算（给双方）
	public void testContractArbitratedFinalEstimate(){
		//合同仲裁完成,提醒买家和卖家仲裁结束
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ARBITRATED_FINALESTIMATE, oid, buyer, SystemMessageContent.getMsgContentOfContractArbitratedResult(title));
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ARBITRATED_FINALESTIMATE, oid, seller, SystemMessageContent.getMsgContentOfContractArbitratedResult(title));
	}
	
	//取消合同（给对方）
	public void testContractSingleCancelToYou(){
		String cid = "CompanyInfoId000000811102014END";
		if(StringUtils.equalsIgnoreCase(cid, buyer)){//如果是操作者是买家，发系统消息和推送消息的内容不一样
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL, oid, seller, SystemMessageContent.getMsgContentOfContractBuyerSingleCancelToSeller(title));
		}else if(StringUtils.equalsIgnoreCase(cid, seller)){//如果操作是卖家 ,发系统消息和推送消息的内容不一样
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL, oid, buyer, SystemMessageContent.getMsgContentOfContractSellerSingleCancelToBuyer(title));
		}
	}
	
	//预期未付款（给双方）
	public void testContractPayFundsTimeout(){
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_PAYFUNDS_TIMEOUT, oid, seller, SystemMessageContent.getMsgContentOfContractBuyerUnpayfundsToSeller(title));
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_PAYFUNDS_TIMEOUT, oid, buyer, SystemMessageContent.getMsgContentOfContractBuyerUnpayfundsToBuyer(title));
	}
	
	//其他（给双方）
	public void testContractOthers(){
		//send the system message and xmpp message
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_OTHERS, oid, seller, new SystemMessageContent(title));
		sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_OTHERS, oid, buyer, new SystemMessageContent(title));
	}
	
}
