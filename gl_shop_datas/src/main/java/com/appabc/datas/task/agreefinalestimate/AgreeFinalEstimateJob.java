package com.appabc.datas.task.agreefinalestimate;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月16日 上午11:47:52
 */

public class AgreeFinalEstimateJob extends BaseJob {
	
	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");
	
	private IContractDisPriceService iContractDisPriceService = (IContractDisPriceService)ac.getBean("IContractDisPriceService");

	private ICompanyRankingService iCompanyRankingService = (ICompanyRankingService)ac.getBean("ICompanyRankingService");
	
	private IContractMineService iContractMineService = (IContractMineService)ac.getBean("IContractMineService");
	
	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private ICompanyInfoService iCompanyInfoService = (ICompanyInfoService)ac.getBean("ICompanyInfoService");

	private IPassPayService iPassPayService = (IPassPayService)ac.getBean("IPassPayLocalService");
	
	private PrimaryKeyGenerator pkGenerator = ac.getBean(PrimaryKeyGenerator.class);

	private MessageSendManager mesgSender = ac.getBean(MessageSendManager.class);
	
	private void sendMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg){
		MessageInfoBean mi = new MessageInfoBean(businessType,businessId,cid,content);
		mi.setSendSystemMsg(systemMsg);
		mi.setSendShotMsg(shortMsg);
		mi.setSendPushMsg(xmppMsg);
		mesgSender.msgSend(mi);
	}
	
	private void sendSystemXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, true, false, true);
	}
	
	private float guarantyToUngelation(String oid,String buyerId,String sellerId,float totalAmount) throws ServiceException{
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(buyerId) || StringUtils.isEmpty(sellerId) || totalAmount <= 0f){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas")));
			
		}
		float balance = ContractCostDetailUtil.getGuarantyCost(totalAmount);
		if(balance > 0){
			boolean f = iPassPayService.guarantyToUngelation(buyerId, balance, oid);
			boolean g = iPassPayService.guarantyToUngelation(sellerId, balance, oid);
			if(!f || !g){
				throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR, Locale.forLanguageTag("datas")));
			}
		}
		return balance;
	}
	
	private void contractTimeOutMoveToFinishList(TOrderInfo bean,String operator,String...other) throws ServiceException{
		if(bean == null){
			return ;
		}
		Date limittime = bean.getLimittime();
		if(limittime == null){
			return ;
		}
		if(bean.getStatus() != ContractStatus.FINISHED){
			return ;
		}
		boolean isFlag = false;
		if(other != null && other.length > 0){
			isFlag = true;
		}
		//save or update the mine contract with cid or oid.
		Date now = DateUtil.getNowDate();
		int diffDays = DateUtil.getDifferDayWithTwoDate(bean.getLimittime(), now);
		if(diffDays >= 0){
			StringBuilder sbm = new StringBuilder(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_OUT_OF_LIMITTIMETIPS, Locale.forLanguageTag("datas")));
			TOrderOperations buyerOper = new TOrderOperations();
			buyerOper.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
			buyerOper.setOid(bean.getId());
			buyerOper.setOperator(bean.getBuyerid());
			buyerOper.setOperationtime(now);
			buyerOper.setType(ContractOperateType.MOVE_TO_FINISHED_CONTRACT);
			buyerOper.setOrderstatus(bean.getLifecycle());
			buyerOper.setResult(sbm.toString());
			buyerOper.setRemark(sbm.toString());
			iContractOperationService.add(buyerOper);
			boolean b = iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), bean.getStatus(), bean.getLifecycle(), operator);
			if(!b){
				throw new ServiceException(ServiceErrorCode.CONTRACT_MOVETO_MYORDERLIST_ERROR,StringUtils.EMPTY);
			}
			
			TOrderOperations sellerOper = new TOrderOperations();
			sellerOper.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
			sellerOper.setOid(bean.getId());
			sellerOper.setOperator(bean.getSellerid());
			sellerOper.setOperationtime(now);
			sellerOper.setType(ContractOperateType.MOVE_TO_FINISHED_CONTRACT);
			sellerOper.setOrderstatus(bean.getLifecycle());
			sellerOper.setResult(sbm.toString());
			sellerOper.setRemark(sbm.toString());
			iContractOperationService.add(sellerOper);
			boolean c = iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), bean.getStatus(), bean.getLifecycle(), isFlag == true ? other[0] : operator);
			if(!c){
				throw new ServiceException(ServiceErrorCode.CONTRACT_MOVETO_MYORDERLIST_ERROR,StringUtils.EMPTY);
			}
		}
	}
	
	private void contractFinalEstimate(TOrderInfo bean,String cid,String cname,String... others) throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas")));
		}
		if(bean.getStatus() == ContractStatus.FINISHED && (bean.getLifecycle() == ContractLifeCycle.NORMAL_FINISHED || bean.getLifecycle() == ContractLifeCycle.FINALESTIMATE_FINISHED)){
			//如果这里有议价过程,请加上议价的过程.
			//相应的解冻保证金
			guarantyToUngelation(bean.getId(), bean.getBuyerid(), bean.getSellerid(), bean.getTotalamount());

			TPassbookInfo buyerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
			TPassbookInfo sellerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getSellerid(), PurseType.DEPOSIT);
			//查看是否有议价记录,如果有,就计算议价后的货款,进行结算.
			//如果没有议价记录,就直接用订单价格总量进行计算
			float contractTotalAmount = 0f;
			List<TContractDisPriceOperation> result = iContractDisPriceService.getGoodsDisPriceHisList(bean.getId(), StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.FUNDGOODS_DISPRICE.getVal());
			if(CollectionUtils.isNotEmpty(result)){
				TContractDisPriceOperation disEntity = result.get(0);
				contractTotalAmount = RandomUtil.mulRound(disEntity.getEndnum(), disEntity.getEndamount());
				//contractTotalAmount = disEntity.getEndnum() * disEntity.getEndamount();
			} else {
				contractTotalAmount = bean.getTotalnum();
			}
			//把买家支付货款付给卖家,扣除卖家的手术服务费用
			if(contractTotalAmount > 0f){
				iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), sellerPurseInfo.getId(), contractTotalAmount, TradeType.PAYMENT_FOR_GOODS,bean.getId());
				float serviceBalance = ContractCostDetailUtil.getServiceCost(contractTotalAmount);
				if(serviceBalance > 0f){				
					iPassPayService.transferAccounts(sellerPurseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), serviceBalance, TradeType.SERVICE_CHARGE,bean.getId());
				}
			}
			
			//支付货款时,发现有多余货款返还给买家
			if(bean.getTotalamount() > contractTotalAmount){
				float retAmount = RandomUtil.subRound(bean.getTotalamount(), contractTotalAmount);
				if(retAmount > 0){				
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), buyerPurseInfo.getId(), retAmount, TradeType.PLATFORM_RETURN,bean.getId());
				}
			}
			
			//设置支付金额
			bean.setAmount(contractTotalAmount);
		//单方取消结算：1,解冻双方的保证金; 2,扣除违约方的保证金给对方, 3,如买家已经付款,返还货款给买家.	
		}
	}
	
	private void autoAgreeFinalEstimate(TOrderInfo bean){
		try{
			if (bean == null) {
				return;
			}
			Date now = DateUtil.getNowDate();
			int hours = DateUtil.getDifferHoursWithTwoDate(
					bean.getUpdatetime(), now);
			if (hours >= ContractCostDetailUtil
					.getContractAgreeFinalEstimateLimitNum()) {
				String oid = bean.getId();
				String cid = bean.getSellerid();
				TCompanyInfo companyInfo = iCompanyInfoService.query(cid);
				String cname = companyInfo.getCname();

				logUtil.info(" Contract is "+oid+"; enter the auto agree final estimate. ");
				
				bean.setUpdater(cid);
				bean.setUpdatetime(now);
				bean.setLifecycle(ContractLifeCycle.FINALESTIMATE_FINISHED);
				bean.setStatus(ContractStatus.FINISHED);
				iContractInfoService.modify(bean);
				// 这需要去走合同正常的结算过程,放到service里面操作
				contractFinalEstimate(bean, cid, cname);
				logUtil.info(" Contract is "+oid+"; finished the final estimate . ");
				
				// 生成合同结算操作明细
				TOrderOperations estimateOperator = new TOrderOperations();
				estimateOperator.setId(pkGenerator
						.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
				estimateOperator.setOid(oid);
				estimateOperator.setOperator(cid);
				estimateOperator.setOperationtime(now);
				estimateOperator
						.setType(ContractOperateType.AGREE_FUND_GOODS_CONFIRM);
				estimateOperator
						.setOrderstatus(ContractLifeCycle.FINALESTIMATE_FINISHED);
				StringBuilder estimateResult = new StringBuilder(cname);
				estimateResult
						.append(MessagesUtil
								.getMessage(
										DataSystemConstant.MESSAGEKEY_FINALESTIMATEFINISHEDTIPS,
										Locale.forLanguageTag("datas")));
				estimateOperator.setResult(estimateResult.toString());
				estimateOperator.setRemark(estimateResult.toString());
				iContractOperationService.add(estimateOperator);

				// 更新合同的状态为正常结束
				bean.setUpdater(cid);
				bean.setUpdatetime(now);
				bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
				bean.setStatus(ContractStatus.FINISHED);
				iContractInfoService.modify(bean);
				logUtil.info(" Contract is "+oid+"; finished the Contract. ");
				
				// 合同完成结束的操作记录
				TOrderOperations finishOper = new TOrderOperations();
				finishOper.setId(pkGenerator
						.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
				finishOper.setOid(oid);
				finishOper.setOperator(cid);
				finishOper.setOperationtime(now);
				finishOper.setType(ContractOperateType.CONTRACT_FINISHED);
				finishOper.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED);
				StringBuilder finishMesg = new StringBuilder(cname);
				finishMesg.append(MessagesUtil.getMessage(
						DataSystemConstant.MESSAGEKEY_FINISH_CONTRACT_TIPS,
						Locale.forLanguageTag("datas")));
				finishOper.setResult(finishMesg.toString());
				finishOper.setRemark(finishMesg.toString());
				iContractOperationService.add(finishOper);
				// save or update the mine contract with cid or oid.
				contractTimeOutMoveToFinishList(bean, cid);
				logUtil.info(" Contract is "+oid+"; do the time out move to the finish list. ");
				
				// 计算交易成功率.
				iCompanyRankingService.calculateTradeSuccessRate(bean
						.getBuyerid());
				iCompanyRankingService.calculateTradeSuccessRate(bean
						.getSellerid());
				logUtil.info(" Contract is "+oid+"; do the rank . ");
				// 买家确认收货,触发条件发送消息告诉买家可以进行评价
				sendSystemXmppMessage(
						MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, oid,
						bean.getBuyerid(),
						SystemMessageContent
								.getMsgContentOfContractCompletion(oid));
			}
		}catch(Exception e){
			logUtil.error(e);
		}
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.CONFIRMING_GOODS_FUNDS);
		entity.setStatus(ContractStatus.DOING);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		if(CollectionUtils.isNotEmpty(result)){
			for(TOrderInfo bean : result){
				autoAgreeFinalEstimate(bean);
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
	}

}
