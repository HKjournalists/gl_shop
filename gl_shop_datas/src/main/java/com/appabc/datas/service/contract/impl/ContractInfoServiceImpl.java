package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.bo.MatchOrderInfo;
import com.appabc.bean.enums.ContractInfo;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.PurseInfo.BusinessType;
import com.appabc.bean.enums.PurseInfo.OnOffLine;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.codes.IPublicCodesService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.IOrderProductInfoService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.EveryUtil;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IOfflinePayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : ContractInfoServiceImpl.java
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年9月1日 下午6:35:53
 */

@Service(value="IContractInfoService")
public class ContractInfoServiceImpl extends BaseService<TOrderInfo> implements
		IContractInfoService {

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private IContractOperationDAO iContractOperationDAO;

	@Autowired
	private PrimaryKeyGenerator pKGenerator;

	@Autowired
	private IPassPayService iPassPayService;

	@Autowired
	private IOfflinePayService iOfflinePayService;

	@Autowired
	private MessageSendManager mesgSender;

	@Autowired
	private ICompanyRankingService iCompanyRankingService;

	@Autowired
	private IPublicCodesService iPublicCodesService;

	@Autowired
	private IOrderProductInfoService iOrderProductInfoService;
	
	@Autowired
	private ICompanyInfoService iCompanyInfoService;
	
	@Autowired
	private IOrderFindService orderFindService;

	private String getKey(String bid){
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		return pKGenerator.getPKey(bid);
	}
	
	/**
	 *  generate the order info Id
	 * */
	private String generateOrderInfoId(String fid){
		String id = getKey(DataSystemConstant.CONTRACTINFOID);
		String destStr = StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(fid)){
			TOrderProductInfo topi = new TOrderProductInfo();
			topi.setFid(fid);
			topi = iOrderProductInfoService.query(topi);
			String str = "";
			if(topi != null && topi.getPcode().equals("G001")) str = "11";
			if(topi != null && topi.getPcode().equals("G002")) str = "10";
			destStr = topi != null ? str : StringUtils.EMPTY;
		}
		return pKGenerator.replaceCode("BCODE", id, destStr);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TOrderInfo entity) {
		entity.setId(generateOrderInfoId(entity.getFid()));
		iContractInfoDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#modify(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void modify(TOrderInfo entity) {
		iContractInfoDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#delete(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void delete(TOrderInfo entity) {
		iContractInfoDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iContractInfoDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#query(java.io.Serializable)
	 */
	@Override
	public TOrderInfo query(Serializable id) {
		return iContractInfoDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TOrderInfo query(TOrderInfo entity) {
		return iContractInfoDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TOrderInfo> queryForList(TOrderInfo entity) {
		return iContractInfoDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TOrderInfo> queryForList(Map<String, ?> args) {
		return iContractInfoDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.BaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TOrderInfo> queryListForPagination(
			QueryContext<TOrderInfo> qContext) {
		return iContractInfoDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.datas.service.contract.IContractInfoService#getOrderDetailInfo
	 * (java.lang.String)
	 */
	public TOrderInfo getOrderDetailInfo(String id) {
		return this.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.datas.service.contract.IContractInfoService#toConfirmContract
	 * (java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public int toConfirmContract(String contractId, String userId,
			String updaterName) throws ServiceException{
		//step 1, make sure you operate is never to do
		TOrderOperations operator = new TOrderOperations();
		operator.setOid(contractId);
		operator.setOperator(userId);
		List<TOrderOperations> list = iContractOperationDAO.queryForList(operator);
		if(list!=null && list.size()>0){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_REPEAT_ERROR,"您不能重复确认合同.");
		}
		//step 2, if you to confirm this contract ,you can to do, but here is some condition,
		//if you partner was confirmed,
		TOrderInfo bean = this.query(contractId);
		if(bean == null || (bean.getLifecycle() != ContractLifeCycle.DRAFTING && bean.getOtype() != ContractType.DRAFT && bean.getStatus() != ContractStatus.DRAFT)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOALLOW_ERROR,"合同当前的状态不允许您操作.");
		}
		List<?> r = iContractOperationDAO.queryForList(contractId);
		int i = 0;
		if(r!=null && r.size() > 0){
			i = 1;
			bean.setStatus(ContractStatus.DOING);
			bean.setOtype(ContractType.SIGNED);
			bean.setLifecycle(ContractLifeCycle.SINGED);
		}else{
			bean.setStatus(ContractStatus.DRAFT);
			bean.setOtype(ContractType.DRAFT);
			bean.setLifecycle(ContractLifeCycle.DRAFTING);
		}
		Date now = DateUtil.getNowDate();
		bean.setUpdatetime(now);
		bean.setUpdater(userId);
		this.modify(bean);
		//
		operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		if(r!=null && r.size() > 0){
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.SINGED);
		}else{
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		}
		StringBuffer result = new StringBuffer(updaterName);
		result.append(MessagesUtil.getMessage("TOCONFIRMCONTRACTTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		if(r!=null && r.size() > 0){
			TOrderOperations o = (TOrderOperations)r.get(0);
			operator.setPlid(o.getId());
		}
		iContractOperationDAO.save(operator);
		//这里需要去扣除保证金操作
		//deduct the contract guaranty to gelation
		if(r!=null && r.size() > 0){
			float balance = ContractCostDetailUtil.getGuarantyCost(bean.getTotalamount());
			iPassPayService.guarantyToGelation(bean.getBuyerid(), balance, contractId);
			iPassPayService.guarantyToGelation(bean.getSellerid(), balance, contractId);

			//send the system message and xmpp message.
			//合同签订后,提醒买家付款操作.
			MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,contractId,bean.getBuyerid(),SystemMessageContent.getMsgContentOfContractPayment(contractId));
			mi.setSendSystemMsg(true);
			mi.setSendPushMsg(true);
			mesgSender.msgSend(mi);
			//合同签订后,提醒卖家发货.
			MessageInfoBean sellerMi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,contractId,bean.getSellerid(),SystemMessageContent.getMsgContentOfContractSignSuccess(contractId));
			sellerMi.setSendSystemMsg(true);
			sellerMi.setSendPushMsg(true);
			mesgSender.msgSend(sellerMi);
		}
		return i;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.datas.service.contract.IContractInfoService#noticeShippingGoods
	 * ()
	 */
	@Transactional(rollbackFor = Exception.class)
	public void noticeShippingGoods(String contractId, String userId,
			String updaterName) {
		TOrderInfo bean = this.query(contractId);
		bean.setStatus(ContractStatus.DOING);
		bean.setLifecycle(ContractLifeCycle.SENT_GOODS);
		bean.setUpdater(userId);
		bean.setUpdatetime(new Date());
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(new Date());
		operator.setType(ContractOperateType.SEND_GOODS);
		StringBuffer result = new StringBuffer(updaterName);
		result.append(MessagesUtil.getMessage("NOTICESHIPPINGGOODSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.datas.service.contract.IContractInfoService#validateGoodsInfo
	 * (java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void validateGoodsInfo(String contractId, String userId,
			String userName) throws ServiceException {
		Date now = DateUtil.getNowDate();
		TOrderInfo bean = this.query(contractId);
		if(bean == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,"合同为空,不能进行操作.");
		}
		if(!StringUtils.equalsIgnoreCase(userId, bean.getBuyerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_RECEIVE_GOODS_NOTBUYER_ERROR,"您不是买家，不能进行确认收货操作.");
		}
		bean.setStatus(ContractStatus.DOING);
		bean.setLifecycle(ContractLifeCycle.RECEIVED_GOODS);
		bean.setUpdater(userId);
		bean.setUpdatetime(now);
		this.modify(bean);
		//生成确认收货操作明细
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.CONFIRM_RECEIVEGOODS);
		operator.setOrderstatus(ContractLifeCycle.RECEIVED_GOODS);
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("VALIDATEGOODSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//这需要去走合同正常的结算过程,放到service里面操作
		//相应的解冻保证金
		float balance = ContractCostDetailUtil.getGuarantyCost(bean.getTotalamount());// bean.getTotalamount()*0.4f;
		if(balance > 0){
			iPassPayService.guarantyToUngelation(bean.getBuyerid(), balance, contractId);
			iPassPayService.guarantyToUngelation(bean.getSellerid(), balance, contractId);			
		}

		TPassbookInfo buyerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
		TPassbookInfo sellerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getSellerid(), PurseType.DEPOSIT);
		
		//向支付货款以及扣除手续费服务费
		if(bean.getAmount() > 0.0f){
			iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), sellerPurseInfo.getId(), bean.getAmount(), PurseType.DEPOSIT);
			float servieBalance = ContractCostDetailUtil.getServiceCost(bean.getAmount());// bean.getAmount()*0.3f;
			if(servieBalance > 0){				
				iPassPayService.transferAccounts(sellerPurseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), servieBalance, TradeType.SERVICE_CHARGE);
			}
		}
		
		//支付货款时,发现有多余货款返还给买家
		if(!bean.getAmount().equals(bean.getTotalamount())){
			float retAmount = bean.getTotalamount()-bean.getAmount();
			if(retAmount > 0){				
				iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), buyerPurseInfo.getId(), retAmount, TradeType.PLATFORM_RETURN);
			}
		}
		bean.setStatus(ContractStatus.FINISHED);
		bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		bean.setUpdater(userId);
		bean.setUpdatetime(DateUtil.getNowDate());
		this.modify(bean);
		
		//生成合同结算操作明细
		TOrderOperations estimateOperator = new TOrderOperations();
		estimateOperator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		estimateOperator.setOid(contractId);
		estimateOperator.setOperator(userId);
		estimateOperator.setOperationtime(now);
		estimateOperator.setType(ContractOperateType.CONTRACT_ESTIMATE);
		estimateOperator.setOrderstatus(ContractLifeCycle.FINALESTIMATE_FINISHED);
		StringBuffer estimateResult = new StringBuffer(userName);
		estimateResult.append("进行了合同结算操作.");
		estimateOperator.setResult(estimateResult.toString());
		estimateOperator.setRemark(estimateResult.toString());
		iContractOperationDAO.save(estimateOperator);
		
		//计算交易成功率.
		iCompanyRankingService.calculateTradeSuccessRate(bean.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(bean.getSellerid());

		//买家确认收货,触发条件发送消息告诉买家可以进行评价
		MessageInfoBean mi1 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION,contractId,bean.getBuyerid(),SystemMessageContent.getMsgContentOfContractCompletion(contractId));
		mi1.setSendSystemMsg(true);
		mi1.setSendPushMsg(true);
		mesgSender.msgSend(mi1);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#payContractFunds(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public TOrderInfo payContractFunds(String contractId, String userId,
			String userName) throws ServiceException{
		TOrderInfo bean = this.query(contractId);
		//if the torderinfo is null or the contract status is not 2, so then can not to update the info.
		if (bean == null || bean.getStatus() != ContractStatus.DOING
				|| bean.getOtype() != ContractType.SIGNED
				|| bean.getLifecycle() != ContractLifeCycle.SINGED) {
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_PAYSTATUS_ERROR,"合同当前状态不能进行付款操作.");
		}
		//
		if(!bean.getBuyerid().equalsIgnoreCase(userId)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOBUYER_ERROR,"你不是买家,不能进行付款操作.");
		}
		Date now = new Date();
		bean.setStatus(ContractStatus.DOING);
		bean.setUpdater(userId);
		bean.setLifecycle(ContractLifeCycle.PAYED_FUNDS);
		bean.setUpdatetime(now);
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.PAYED_FUNDS);
		operator.setOrderstatus(ContractLifeCycle.PAYED_FUNDS);
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("PAYCONTRACTFUNDSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//pay contract funds
		TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(userId, PurseType.DEPOSIT);
		boolean ff = iPassPayService.transferAccounts(purseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), bean.getTotalamount(), PurseType.DEPOSIT);
		if(!ff){
			throw new ServiceException(ServiceErrorCode.CONTRACT_PAY_FUNDS_ERROR,"余额不足,请充值,支付合同失败!");
		}
		//合同签订后,买家付款后,提示验货的操作.
		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,contractId,bean.getBuyerid(),SystemMessageContent.getMsgContentOfContractAcceptanceGoods(contractId));
		mi.setSendSystemMsg(true);
		mi.setSendPushMsg(true);
		mesgSender.msgSend(mi);
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#payContractFundsOnline(java.lang.String, java.lang.String, java.lang.String)
	 */
	public TOrderInfo payContractFundsOnline(String contractId, String userId,
			String userName) throws ServiceException {
		return this.payContractFunds(contractId, userId, userName);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#payContractFundsOffline(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public TOrderInfo payContractFundsOffline(String contractId, String userId,
			String userName) throws ServiceException {
		TOrderInfo bean = this.query(contractId);
		//if the torderinfo is null or the contract status is not 2, so then can not to update the info.
		if (bean == null
				|| bean.getStatus() != ContractStatus.DOING
				|| bean.getOtype() != ContractType.SIGNED
				|| bean.getLifecycle() != ContractLifeCycle.SINGED) {
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_PAYSTATUS_ERROR,"合同为空或者不在支付的状态.");
		}
		//
		if(!bean.getBuyerid().equalsIgnoreCase(userId)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOBUYER_ERROR,"您不是买家，不能支付合同货款.");
		}
		TOfflinePay queryInfo = new TOfflinePay();
		queryInfo.setOid(contractId);
		queryInfo.setOtype(PurseType.DEPOSIT);
		queryInfo.setPtype(OnOffLine.OFFLINE);
		queryInfo.setCreater(bean.getBuyerid());
		queryInfo.setStatus(TradeStatus.REQUEST);
		queryInfo.setBtype(BusinessType.PAY);
		List<TOfflinePay> res = iOfflinePayService.queryForList(queryInfo);
		if(CollectionUtils.isNotEmpty(res)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_REPEAT_SUBMIT_ERROR,"你不能重复提交!");
		}

		Date now = DateUtil.getNowDate();
		bean.setStatus(ContractStatus.DOING);
		bean.setUpdater(userId);
		bean.setLifecycle(ContractLifeCycle.IN_THE_PAYMENT);
		bean.setUpdatetime(now);
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.PAYED_FUNDS);
		operator.setOrderstatus(ContractLifeCycle.IN_THE_PAYMENT);
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("PAYCONTRACTFUNDSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//offline pay record
		TOfflinePay tOfflinePay = new TOfflinePay();
		tOfflinePay.setOid(contractId);
		tOfflinePay.setOtype(PurseType.DEPOSIT);
		tOfflinePay.setPtype(OnOffLine.OFFLINE);
		tOfflinePay.setCreater(bean.getBuyerid());
		tOfflinePay.setCreatetime(DateUtil.getNowDate());
		tOfflinePay.setStatus(TradeStatus.REQUEST);
		tOfflinePay.setBtype(BusinessType.PAY);
		tOfflinePay.setAmount(bean.getTotalamount());
		tOfflinePay.setTotal(bean.getTotalamount());
		try {
			iPassPayService.saveOfflinePay(tOfflinePay);
		} catch (com.appabc.pay.exception.ServiceException e) {
			throw new ServiceException(e.getErrorCode(),e.getCause());
		}
		return bean;
	}

	/* (non-Javadoc)获取企业的交易成功率
	 * @see com.appabc.datas.service.contract.IContractInfoService#getTransactionSuccessRate(java.lang.String)
	 */
	public EvaluationInfoBean getTransactionSuccessRate(String cid){

		EvaluationInfoBean ei = new EvaluationInfoBean();
		float allCount = this.iContractInfoDAO.countByCid(cid, null, ContractInfo.ContractStatus.FINISHED.getVal());
		if(allCount>0){
			// 交易成功数
			int successCount = this.iContractInfoDAO.countByCid(cid, ContractInfo.ContractLifeCycle.NORMAL_FINISHED.getVal(), ContractInfo.ContractStatus.FINISHED.getVal());

			ei.setTransactionSuccessNum(successCount);
			ei.setTransactionSuccessRate((float)successCount/allCount);

		}else{
			ei.setTransactionSuccessNum(0);
			ei.setTransactionSuccessRate(0f);
		}

		return ei;
	}

	/* (non-Javadoc)判断2个企业是否交易过
	 * @see com.appabc.datas.service.contract.IContractInfoService#isOldCustomer(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isOldCustomer(String cid1, String cid2) {

		int count = this.iContractInfoDAO.countByBuyerAndSeller(cid1, cid2);
		if(count > 0){
			return true;
		}else{
			count = this.iContractInfoDAO.countByBuyerAndSeller(cid2, cid1);
		}

		if(count > 0){
			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#queryContractInfoListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<ContractInfoBean> queryContractInfoListForPagination(
			QueryContext<ContractInfoBean> qContext) {
		return iContractInfoDAO.queryContractInfoListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#getContractInfoById(java.lang.String)
	 */
	@Override
	public ContractInfoBean getContractInfoById(String cid,String contractId) {
		return iContractInfoDAO.queryContractInfoWithId(cid,contractId);
	}

	/* (non-Javadoc)获取企业的合同数
	 * @see com.appabc.datas.service.contract.IContractInfoService#getTotalByCid(java.lang.String)
	 */
	@Override
	public int getTotalByCid(String cid) {
		if(StringUtils.isEmpty(cid)) return 0;
		return this.iContractInfoDAO.countByCid(cid, null, null);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#toConfirmContractRetOperator(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public List<TOrderOperations> toConfirmContractRetOperator(
			String contractId, String userId, String updaterName)
			throws ServiceException {
		//step 1, make sure you operate is never to do
		TOrderOperations operator = new TOrderOperations();
		operator.setOid(contractId);
		operator.setOperator(userId);
		//operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		operator.setType(ContractOperateType.CONFRIM_CONTRACT);
		List<TOrderOperations> list = iContractOperationDAO.queryForList(operator);
		if(CollectionUtils.isNotEmpty(list)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_REPEAT_ERROR,"您不能重复提交确认合同.");
		}
		//step 2, check the company guarant amount
		float shouldGuarantNum = iCompanyInfoService.getShouldDepositAmountByCid(userId);
		float total = iPassPayService.getGuarantyTotal(userId);
		if(shouldGuarantNum > total){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,"保证金余额不足,请检查您的保证金账户,进行充值.");
		}
		//step 3, if you to confirm this contract ,you can to do, but here is some condition,
		//if you partner was confirmed,
		TOrderInfo bean = this.query(contractId);
		TOrderOperations queryObj = new TOrderOperations();
		queryObj.setType(ContractOperateType.CONFRIM_CONTRACT);
		queryObj.setOrderstatus(ContractLifeCycle.DRAFTING);
		queryObj.setOid(contractId);
		List<TOrderOperations> res = iContractOperationDAO.queryForList(queryObj);
		if(CollectionUtils.isNotEmpty(res)){
			bean.setStatus(ContractStatus.DOING);
			bean.setOtype(ContractType.SIGNED);
			bean.setLifecycle(ContractLifeCycle.SINGED);
		}else{
			bean.setStatus(ContractStatus.DRAFT);
			bean.setOtype(ContractType.DRAFT);
			bean.setLifecycle(ContractLifeCycle.DRAFTING);
		}
		Date now = new Date();
		bean.setUpdatetime(now);
		bean.setUpdater(userId);
		this.modify(bean);
		
		operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		if(CollectionUtils.isNotEmpty(res)){
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.SINGED);
		}else{
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		}
		StringBuffer result = new StringBuffer(updaterName);
		result.append(MessagesUtil.getMessage("TOCONFIRMCONTRACTTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		if(CollectionUtils.isNotEmpty(res)){
			TOrderOperations o = res.get(0);
			operator.setPlid(o.getId());
		}
		iContractOperationDAO.save(operator);
		//这里需要去扣除保证金操作
		//deduct the contract guaranty to gelation
		if(CollectionUtils.isNotEmpty(res)){
			float balance = ContractCostDetailUtil.getGuarantyCost(bean.getTotalamount());
			if(balance > 0){
				boolean f = iPassPayService.guarantyToGelation(bean.getBuyerid(), balance, contractId);
				boolean g = iPassPayService.guarantyToGelation(bean.getSellerid(), balance, contractId);
				if(!f || !g){
					throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,"保证金扣除失败,请检查您的保证金账户.");
				}
			}
			
			//send the system message and xmpp message.
			//合同签订后,提醒买家付款操作.
			MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,contractId,bean.getBuyerid(),SystemMessageContent.getMsgContentOfContractPayment(contractId));
			mi.setSendSystemMsg(true);
			mi.setSendPushMsg(true);
			mesgSender.msgSend(mi);
			//合同签订后,提醒卖家发货.
			MessageInfoBean sellerMi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,contractId,bean.getSellerid(),SystemMessageContent.getMsgContentOfContractSignSuccess(contractId));
			sellerMi.setSendSystemMsg(true);
			sellerMi.setSendPushMsg(true);
			mesgSender.msgSend(sellerMi);
		}else{
			//合同一方确认,发消息通知另一方及时确认.
			MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,contractId,StringUtils.equalsIgnoreCase(userId, bean.getSellerid()) ? bean.getBuyerid() : bean.getSellerid(),SystemMessageContent.getMsgContentOfContractSingleConfirm(contractId,now));
			mi.setSendSystemMsg(true);
			mi.setSendPushMsg(true);
			mesgSender.msgSend(mi);
		}
		return res;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#makeAndMatchATOrderInfo(java.lang.String, java.lang.String, java.lang.Float, java.lang.Float)  
	 */
	@Override
	public TOrderInfo makeAndMatchATOrderInfo(String fid, String cid,String operator,
			float totalNum, float price) throws ServiceException {
		if(StringUtils.isEmpty(cid) || StringUtils.isEmpty(fid) || StringUtils.isEmpty(operator)){
			throw new ServiceException("匹配生成合同信息不能为空.");
		}
		TOrderFind orderFind = orderFindService.query(fid);
		if(orderFind.getMorearea() == OrderMoreAreaEnum.ORDER_MORE_AREA_YES){
			throw new ServiceException("这个是一个父询单,不能进行匹配生成合同.");
		}
		
		TOrderInfo orderInfo = new TOrderInfo();
		//判断子询单和父询单的一些关系
		TOrderFind cloneParentOrderFind = null;
		TOrderFind parentOrderFind = null;
		if(StringUtils.isNotEmpty(orderFind.getParentid())){
			//从父类那详情进行填充
			parentOrderFind = orderFindService.query(orderFind.getParentid());
			cloneParentOrderFind = EveryUtil.cloneOrderFindInfo(parentOrderFind);
			//设置相应的一些值
			cloneParentOrderFind.setId(orderFind.getId());
		} else {
			cloneParentOrderFind = EveryUtil.cloneOrderFindInfo(orderFind);
		}
		
		if(totalNum > 0){
			cloneParentOrderFind.setNum(totalNum);				
		}else{
			cloneParentOrderFind.setNum(orderFind.getNum());
		}
		if(price > 0){
			cloneParentOrderFind.setPrice(price);
		}else{
			cloneParentOrderFind.setPrice(orderFind.getPrice());
		}
		
		//合同详情信息
		Date now = DateUtil.getNowDate();
		orderInfo.setId(getKey(DataSystemConstant.CONTRACTINFOID));
		orderInfo.setFid(cloneParentOrderFind.getId());
		if(cloneParentOrderFind.getType() == OrderTypeEnum.ORDER_TYPE_BUY){ // 询单发布方为买方
			orderInfo.setBuyerid(cloneParentOrderFind.getCid());
			orderInfo.setSellerid(cid);
		} else {// 询单发布方为卖方
			orderInfo.setBuyerid(cid);
			orderInfo.setSellerid(cloneParentOrderFind.getCid());
		}
		orderInfo.setPrice(cloneParentOrderFind.getPrice());
		orderInfo.setCreatime(now);
		orderInfo.setCreater(operator);
		orderInfo.setLimittime(cloneParentOrderFind.getLimitime());
		orderInfo.setTotalnum(cloneParentOrderFind.getTotalnum());
		orderInfo.setTotalamount(cloneParentOrderFind.getPrice()*cloneParentOrderFind.getTotalnum());
		orderInfo.setAmount(cloneParentOrderFind.getPrice()*cloneParentOrderFind.getTotalnum());
		orderInfo.setStatus(ContractInfo.ContractStatus.DRAFT);
		orderInfo.setOtype(ContractInfo.ContractType.DRAFT);
		orderInfo.setUpdater(operator);
		orderInfo.setUpdatetime(now);
		orderInfo.setRemark("remark");
		orderInfo.setLifecycle(ContractLifeCycle.DRAFTING);
		this.add(orderInfo);
		
		//保存操作记录
		TOrderOperations orderOperator = new TOrderOperations();
		orderOperator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		orderOperator.setOid(orderInfo.getId());
		orderOperator.setOperator(operator);
		orderOperator.setOperationtime(now);
		orderOperator.setType(ContractOperateType.MAKE_CONTRACT);
		orderOperator.setOrderstatus(ContractLifeCycle.DRAFTING);
		StringBuffer result = new StringBuffer(operator);
		result.append("撮合了合同操作");
		orderOperator.setResult(result.toString());
		orderOperator.setRemark(result.toString());
		iContractOperationDAO.save(orderOperator);
		
		//更新询单的关系(更新当前的供求信息的状态)
		orderFind.setStatus(OrderStatusEnum.ORDER_STATUS_CLOSE);
		orderFind.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		orderFind.setUpdatetime(now);
		orderFind.setUpdater(operator);
		orderFindService.modify(orderFind);
		
		if(parentOrderFind != null){
			//处理多地域的情况，1 更新多地域的总量， 2 检查多地域的总量是否为0 同时进行子类设置为无效。
			float num = parentOrderFind.getNum() - cloneParentOrderFind.getNum();
			parentOrderFind.setNum(num);
			if(num <= 0){
				parentOrderFind.setStatus(OrderStatusEnum.ORDER_STATUS_CLOSE);
				parentOrderFind.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
				parentOrderFind.setUpdatetime(now);
				parentOrderFind.setUpdater(operator);
				//更新该父询单下所有子询单为无效状态
				orderFindService.updateChildOrderFindCloseInvalidByParentId(parentOrderFind.getId(), operator);
				/*TOrderFind queryObj = new TOrderFind();
				queryObj.setParentid(parentOrderFind.getId());
				List<TOrderFind> res = orderFindService.queryForList(queryObj);
				for(TOrderFind o : res){
					o.setStatus(OrderStatusEnum.ORDER_STATUS_CLOSE);
					o.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
					o.setUpdatetime(now);
					o.setUpdater(operator);
					orderFindService.modify(o);
				}*/
			}
			orderFindService.modify(parentOrderFind);
		}
		
		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,orderInfo.getId(),orderInfo.getBuyerid(),SystemMessageContent.getMsgContentOfContractSign());
		mi.setSendSystemMsg(true);
		mi.setSendPushMsg(true);
		mesgSender.msgSend(mi);

		mi.setCid(orderInfo.getSellerid());
		mesgSender.msgSend(mi);
		
		return orderInfo;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#makeAndMatchATOrderInfo(com.appabc.bean.bo.MatchOrderInfo)  
	 */
	@Override
	public TOrderInfo makeAndMatchATOrderInfo(MatchOrderInfo moi)
			throws ServiceException {
		if(moi == null){
			throw new ServiceException("匹配生成合同信息不能为空.");
		}
		return this.makeAndMatchATOrderInfo(moi.getFid(), moi.getCid(), moi.getOperator(), moi.getTotalNum(), moi.getPrice());
	}
	
	/* (non-Javadoc)获取询单被撮合的次数
	 * @see com.appabc.datas.service.contract.IContractInfoService#getMatchingNumByFid(java.lang.String)
	 */
	@Override
	public int getMatchingNumByFid(String fid) {
		if(StringUtils.isEmpty(fid)) return 0;
		return this.iContractInfoDAO.getMatchingNumByFid(fid);
	}

}
