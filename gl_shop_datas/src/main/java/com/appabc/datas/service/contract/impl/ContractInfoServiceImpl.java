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
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.enums.ContractInfo;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallTypeEnum;
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
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.IOrderProductInfoService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.local.IOfflinePayService;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : ContractInfoServiceImpl.java
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年9月1日 下午6:35:53
 */

@Service(value="IContractInfoService")
public class ContractInfoServiceImpl extends ContractBaseService<TOrderInfo> implements IContractInfoService {

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private IOfflinePayService iOfflinePayService;

	@Autowired
	private ICompanyRankingService iCompanyRankingService;

	@Autowired
	private IOrderProductInfoService iOrderProductInfoService;
	
	@Autowired
	private ICompanyInfoService iCompanyInfoService;
	
	@Autowired
	private IOrderFindService orderFindService;
	
	@Autowired
	private IContractMineService iContractMineService;

	
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
			if(topi != null && StringUtils.equalsIgnoreCase("G001", topi.getPcode())) str = "11";
			if(topi != null && StringUtils.equalsIgnoreCase("G002", topi.getPcode())) str = "10";
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
	@Deprecated
	public int toConfirmContract(String contractId, String userId,
			String updaterName) throws ServiceException{
		//step 1, make sure you operate is never to do
		TOrderOperations operator = new TOrderOperations();
		operator.setOid(contractId);
		operator.setOperator(userId);
		List<TOrderOperations> list = iContractOperationDAO.queryForList(operator);
		if(CollectionUtils.isNotEmpty(list)){
			throw new ServiceException(ServiceErrorCode.SUBMIT_OPERATOR_AGAIN, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_REPEAT_ERROR));
		}
		//step 2, if you to confirm this contract ,you can to do, but here is some condition,
		//if you partner was confirmed,
		TOrderInfo bean = this.query(contractId);
		if(bean == null || !(bean.getLifecycle() == ContractLifeCycle.DRAFTING && bean.getOtype() == ContractType.DRAFT && bean.getStatus() == ContractStatus.DRAFT)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOALLOW_ERROR));
		}
		ContractLifeCycle clc = bean.getLifecycle();
		List<?> r = iContractOperationDAO.queryForList(contractId);
		int i = 0;
		if(CollectionUtils.isNotEmpty(r)){
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
		if(CollectionUtils.isNotEmpty(r)){
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.SINGED);
		}else{
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		}
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(updaterName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_TOCONFIRMCONTRACTTIPS));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		if(CollectionUtils.isNotEmpty(r)){
			TOrderOperations o = (TOrderOperations)r.get(0);
			operator.setPlid(o.getId());
		}
		iContractOperationDAO.save(operator);
		//这里需要去扣除保证金操作
		//deduct the contract guaranty to gelation
		if(CollectionUtils.isNotEmpty(r)){
			//冻结保证金
			guarantyToGelation(contractId, bean.getBuyerid(), bean.getSellerid(), bean.getTotalamount());

			//send the system message and xmpp message.
			//合同签订后,提醒买家付款操作.
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN, contractId, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractPayment(contractId));
			//合同签订后,提醒卖家发货.
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN, contractId, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractSignSuccess(contractId));
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
		if(bean==null){
			throw new RuntimeException();
		}
		ContractLifeCycle clc = bean.getLifecycle();
		Date date = DateUtil.getNowDate();
		bean.setStatus(ContractStatus.DOING);
		bean.setLifecycle(ContractLifeCycle.SENT_GOODS);
		bean.setUpdater(userId);
		bean.setUpdatetime(date);
		this.modify(bean);
		
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(date);
		operator.setType(ContractOperateType.SEND_GOODS);
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(updaterName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_NOTICESHIPPINGGOODSTIPS));
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
	public void validateGoodsInfo(String contractId, String userId,String userName) throws ServiceException {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		Date now = DateUtil.getNowDate();
		TOrderInfo bean = this.query(contractId);
		if(bean == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		if(!StringUtils.equalsIgnoreCase(userId, bean.getBuyerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_RECEIVE_GOODS_NOTBUYER_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_RECEIVE_GOODS_NOTBUYER_ERROR));
		}
		ContractLifeCycle clc = bean.getLifecycle();
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
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(userName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_VALIDATEGOODSTIPS));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		
		bean.setStatus(ContractStatus.FINISHED);
		bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		bean.setUpdater(userId);
		bean.setUpdatetime(now);
		this.modify(bean);
		
		//这需要去走合同正常的结算过程,放到service里面操作
		contractFinalEstimate(bean, userId, userName);
		//更新支付货款的值
		this.modify(bean);
		//生成合同结算操作明细
		TOrderOperations estimateOperator = new TOrderOperations();
		estimateOperator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		estimateOperator.setOid(contractId);
		estimateOperator.setOperator(userId);
		estimateOperator.setOperationtime(now);
		estimateOperator.setType(ContractOperateType.CONTRACT_ESTIMATE);
		estimateOperator.setOrderstatus(ContractLifeCycle.FINALESTIMATE_FINISHED);
		estimateOperator.setOldstatus(ContractLifeCycle.RECEIVED_GOODS);
		StringBuilder estimateResult = new StringBuilder(userName);
		estimateResult.append(getMessage(DataSystemConstant.MESSAGEKEY_FINALESTIMATEFINISHEDTIPS));
		estimateOperator.setResult(estimateResult.toString());
		estimateOperator.setRemark(estimateResult.toString());
		iContractOperationDAO.save(estimateOperator);
		
		//save or update the mine contract with cid or oid.
		contractTimeOutMoveToFinishList(bean, userId);
		
		//计算交易成功率.
		iCompanyRankingService.calculateTradeSuccessRate(bean.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(bean.getSellerid());

		//买家确认收货,触发条件发送消息告诉买家可以进行评价
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, contractId, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractCompletion(contractId));
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#payContractFunds(java.lang.String, java.lang.String, java.lang.String, float)  
	 */
	@Override
	public TOrderInfo payContractFunds(String oid, String cid, String cname,double amount) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo bean = this.query(oid);
		//if the torderinfo is null or the contract status is not 2, so then can not to update the info.
		if (bean == null || bean.getStatus() != ContractStatus.DOING
				|| bean.getOtype() != ContractType.SIGNED
				|| bean.getLifecycle() != ContractLifeCycle.SINGED) {
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_PAYSTATUS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_PAYSTATUS_ERROR));
		}
		//超出付款时限,后不能进行付款操作.
		//Date limitDateTime = DateUtil.getDate(bean.getUpdatetime(), ContractCostDetailUtil.getContractPayGoodsLimitNum());
		Date limitDateTime = ContractCostDetailUtil.getPayGoodsLimitTime(bean.getUpdatetime(), bean.getLimittime());
		boolean isNotTimeOut = DateUtil.isTargetGtSourceDateNo235959(DateUtil.getNowDate(), limitDateTime);
		if(!isNotTimeOut){
			throw new ServiceException(ServiceErrorCode.CONTRACT_PAY_FUNDS_CONTRACT_TIMEOUT_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//如果,不是买家不能进行付款操作
		if(!bean.getBuyerid().equalsIgnoreCase(cid)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOBUYER_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOBUYER_ERROR));
		}
		if(amount <= 0f){
			amount = bean.getTotalamount();
		}
		//pay contract funds
		TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(cid, PurseType.DEPOSIT);
		boolean ff = iPassPayService.transferAccounts(purseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), amount, TradeType.PAYMENT_FOR_GOODS,oid);
		if(!ff){
			throw new ServiceException(ServiceErrorCode.CONTRACT_PAY_FUNDS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_PAY_FUNDS_ERROR));
		}
		//更改合同信息
		ContractLifeCycle clc = bean.getLifecycle();
		ContractLifeCycle currentClc = null;
		double payedFundAmount = iPassPayService.getContractPayFundsAmount(cid, oid);
		double totalPayFundAmount = 0.0;
		if(amount == bean.getTotalamount()){
			totalPayFundAmount = amount;
		}else{
			totalPayFundAmount = RandomUtil.addRound(payedFundAmount, amount);
		}
		if(totalPayFundAmount < bean.getTotalamount()){
			currentClc = ContractLifeCycle.IN_THE_PAYMENT;//ContractLifeCycle.PAYED_FUNDS;//
		}else{
			currentClc = ContractLifeCycle.PAYED_FUNDS;
		}
		Date now = DateUtil.getNowDate();
		bean.setStatus(ContractStatus.DOING);
		bean.setUpdater(cid);
		bean.setLifecycle(currentClc);
		bean.setUpdatetime(now);
		bean.setAmount(totalPayFundAmount);
		bean.setSettleamount(totalPayFundAmount);
		this.modify(bean);
		
		//增加操作记录信息
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(oid);
		operator.setOperator(cid);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.PAYED_FUNDS);
		operator.setOrderstatus(currentClc);
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(cname);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_PAYCONTRACTFUNDSTIPS));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		
		//合同签订后,买家付款后,提示卖家操作.
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_BUYER_PAYFUNDS, oid, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractBuyerPayFundsTipsSellerSendGoods(bean.getRemark()));
		return bean;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#payContractFunds(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public TOrderInfo payContractFunds(String contractId, String userId, String userName) throws ServiceException{
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		return payContractFunds(contractId, userId, userName, 0);
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
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo bean = this.query(contractId);
		//if the torderinfo is null or the contract status is not 2, so then can not to update the info.
		if (bean == null
				|| bean.getStatus() != ContractStatus.DOING
				|| bean.getOtype() != ContractType.SIGNED
				|| bean.getLifecycle() != ContractLifeCycle.SINGED) {
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_PAYSTATUS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_PAYSTATUS_ERROR));
		}
		//
		if(!bean.getBuyerid().equalsIgnoreCase(userId)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOBUYER_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOBUYER_ERROR));
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
			throw new ServiceException(ServiceErrorCode.SUBMIT_OPERATOR_AGAIN, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_REPEAT_SUBMIT_ERROR));
		}

		ContractLifeCycle clc = bean.getLifecycle();
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
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(userName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_PAYCONTRACTFUNDSTIPS));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//offline pay record
		TOfflinePay tOfflinePay = new TOfflinePay();
		tOfflinePay.setOid(contractId);
		tOfflinePay.setOtype(PurseType.DEPOSIT);
		tOfflinePay.setPtype(OnOffLine.OFFLINE);
		tOfflinePay.setCreater(bean.getBuyerid());
		tOfflinePay.setCreatetime(now);
		tOfflinePay.setStatus(TradeStatus.REQUEST);
		tOfflinePay.setBtype(BusinessType.PAY);
		tOfflinePay.setAmount(bean.getTotalamount());
		tOfflinePay.setTotal(bean.getTotalamount());
		try {
			iPassPayService.saveOfflinePay(tOfflinePay);
		} catch (com.appabc.pay.exception.ServiceException e) {
			throw new ServiceException(e.getErrorCode(),e.getCause());
		}
		//合同签订后,买家付款后,提示卖家操作.
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_BUYER_PAYFUNDS, bean.getId(), bean.getSellerid(), SystemMessageContent.getMsgContentOfContractBuyerPayFundsTipsSellerSendGoods(bean.getRemark()));
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
	public List<TOrderOperations> toConfirmContractRetOperator(String contractId, String userId, String updaterName)
			throws ServiceException {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(updaterName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//step 1, make sure you operate is never to do
		TOrderOperations operator = new TOrderOperations();
		operator.setOid(contractId);
		operator.setOperator(userId);
		//operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		operator.setType(ContractOperateType.CONFRIM_CONTRACT);
		List<TOrderOperations> list = iContractOperationDAO.queryForList(operator);
		if(CollectionUtils.isNotEmpty(list)){
			throw new ServiceException(ServiceErrorCode.SUBMIT_OPERATOR_AGAIN, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_REPEAT_ERROR));
		}
		//step 2, check the company guarant amount
		boolean isEnough = this.checkCashGuarantyEnough(userId);
		if(!isEnough){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR));
		}
		
		//step 3, if you to confirm this contract ,you can to do, but here is some condition,
		//if you partner was confirmed,
		TOrderInfo bean = this.query(contractId);
		Date limitDateTime = DateUtil.getDateMoveByHours(bean.getCreatime(), ContractCostDetailUtil.getContractDraftConfirmLimitNumWD());
		boolean isNotTimeOut = DateUtil.isTargetGtSourceDateNo235959(DateUtil.getNowDate(), limitDateTime);
		if(!isNotTimeOut){
			throw new ServiceException(ServiceErrorCode.CONTRACT_TO_CONFIRM_CONTRACT_TIMEOUT_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		ContractLifeCycle clc = bean.getLifecycle();
		TOrderOperations queryObj = new TOrderOperations();
		queryObj.setType(ContractOperateType.CONFRIM_CONTRACT);
		queryObj.setOrderstatus(ContractLifeCycle.DRAFTING);
		if(StringUtils.equalsIgnoreCase(userId, bean.getBuyerid())){
			queryObj.setOperator(bean.getSellerid());
		}else if(StringUtils.equalsIgnoreCase(userId, bean.getSellerid())){
			queryObj.setOperator(bean.getBuyerid());
		}
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
		} else {
			operator.setType(ContractOperateType.CONFRIM_CONTRACT);
			operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		}
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(updaterName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_TOCONFIRMCONTRACTTIPS)); 
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		if(CollectionUtils.isNotEmpty(res)){
			TOrderOperations o = res.get(0);
			operator.setPlid(o.getId());
		}
		iContractOperationDAO.save(operator);
		
		//这里需要去扣除保证金操作
		//deduct the contract guaranty to gelation
		//冻结保证金
		this.guarantyToGelation(contractId, userId, bean.getTotalamount());
		
		if(CollectionUtils.isNotEmpty(res)){
			//update the seller's contract info : status,lifecycle
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), ContractStatus.DOING, ContractLifeCycle.SINGED, userId);
			//update the seller's contract info : status,lifecycle
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), ContractStatus.DOING, ContractLifeCycle.SINGED, userId);
			//send the system message and xmpp message.
			if(StringUtils.equalsIgnoreCase(userId, bean.getBuyerid())){
				//合同签订后,提醒卖家发货.
				sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN, contractId, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractSignedToTipsSellerSendGoods(bean.getRemark(),ContractCostDetailUtil.getContractPayGoodsLimitNum()));
			}else if(StringUtils.equalsIgnoreCase(userId, bean.getSellerid())){
				//合同签订后,提醒买家付款操作.
				Date d = DateUtil.getDate(now, ContractCostDetailUtil.getContractPayGoodsLimitNum());
				sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN, contractId, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractSignedToTipsBuyerPayFunds(bean.getRemark(),d));
			}
		}else{
			//合同一方确认,发消息通知另一方及时确认.
			String sender = StringUtils.equalsIgnoreCase(userId, bean.getSellerid()) ? bean.getBuyerid() : bean.getSellerid();
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SINGLE_DAF_CONFIRM, contractId, sender, SystemMessageContent.getMsgContentOfContractToWaitForYouConfirm(bean.getRemark()));
		}
		return res;
	}
	
	/* (non-Javadoc)获取询单被撮合的次数
	 * @see com.appabc.datas.service.contract.IContractInfoService#getMatchingNumByFid(java.lang.String)
	 */
	@Override
	public int getMatchingNumByFid(String fid) {
		if(StringUtils.isEmpty(fid)) return 0;
		return this.iContractInfoDAO.getMatchingNumByFid(fid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#queryContractListOfMineForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<ContractInfoBean> queryContractListOfMineForPagination(QueryContext<ContractInfoBean> qContext) {
		return this.iContractInfoDAO.queryContractListOfMineForPagination(qContext);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#makeAndMatchATOrderInfo(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, java.lang.String)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public TOrderInfo makeAndMatchATOrderInfo(OrderFindAllBean bean,String cid, String operator) throws ServiceException {
		if(StringUtils.isEmpty(cid) || bean == null || StringUtils.isEmpty(operator)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderFind orderFind = bean.getOfBean();
		if(orderFind == null){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//这里需要去检查用户的认证信息,是否已经认证.
		/*AuthRecordStatus cArs = this.iCompanyInfoService.getAuthStatusByCid(cid);
		if(cArs!=AuthRecordStatus.AUTH_STATUS_CHECK_YES){
			throw new ServiceException(ServiceErrorCode.COMPANY_AUTH_STATUS_CHECK_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		AuthRecordStatus fcArs = this.iCompanyInfoService.getAuthStatusByCid(orderFind.getCid());
		if(fcArs!=AuthRecordStatus.AUTH_STATUS_CHECK_YES){
			throw new ServiceException(ServiceErrorCode.COMPANY_AUTH_STATUS_CHECK_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}*/
		if(bean.getOfBean().getMorearea() == OrderMoreAreaEnum.ORDER_MORE_AREA_YES){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_MORE_AREA_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_IS_PARENT_ORDER));
		}
		if(StringUtils.equalsIgnoreCase(cid, orderFind.getCid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_SELLER_BUYER_IS_SAME_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTACT_SELLER_BUYER_IS_SAME_ERROR));
		}
		String parentId = orderFind.getId();
		if(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_DRAFT == orderFind.getOveralltype()){
			parentId = orderFind.getParentid();
			//orderFind.setId(orderFind.getParentid());
			bean.getOfBean().setId(bean.getOfBean().getParentid());
		}
		//1,先将询单,商品信息,卸货地址等信息给询单模块进行复制操作,生成新的询单ID.
		String fid = orderFindService.contractMatchingOfOrderFindSave(bean);
		//检查双方的保证金额度是否足额.
		boolean isE = this.checkCashGuarantyEnough(cid);
		if(!isE){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR));
		}
		boolean isN = this.checkCashGuarantyEnough(orderFind.getCid());
		if(!isN){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR));
		}
		//2,通过新的询单信息生成合同信息和操作记录信息,以及关联询单和合同信息.
		TOrderInfo orderInfo = new TOrderInfo();
		Date now = DateUtil.getNowDate();
		orderInfo.setFid(fid);
		if(orderFind.getType() == OrderTypeEnum.ORDER_TYPE_BUY){ // 询单发布方为买方
			orderInfo.setBuyerid(orderFind.getCid());
			orderInfo.setSellerid(cid);
		} else {// 询单发布方为卖方
			orderInfo.setBuyerid(cid);
			orderInfo.setSellerid(orderFind.getCid());
		}
		orderInfo.setPrice(orderFind.getPrice().doubleValue());
		orderInfo.setCreatime(now);
		orderInfo.setCreater(operator);
		orderInfo.setLimittime(orderFind.getEndtime());
		orderInfo.setTotalnum(orderFind.getTotalnum());
		orderInfo.setTotalamount(RandomUtil.mulRound(orderFind.getPrice().doubleValue(), orderFind.getTotalnum().doubleValue()));
		orderInfo.setAmount(0.0);
		orderInfo.setStatus(ContractInfo.ContractStatus.DRAFT);
		orderInfo.setOtype(ContractInfo.ContractType.DRAFT);
		orderInfo.setUpdater(operator);
		orderInfo.setUpdatetime(now);
		orderInfo.setRemark(orderFind.getTitle());
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
		StringBuilder result = new StringBuilder(operator);
		result.append(getMessage(DataSystemConstant.MESSAGEKEY_MAKEANDMATCHAORDERTIPS));
		orderOperator.setResult(result.toString());
		orderOperator.setRemark(result.toString());
		iContractOperationDAO.save(orderOperator);
		
		//3,调用询单模块的接口将询单置为失效,并用合同ID关联询单.
		//如果parentId是为空,有可能是客服撮合的合同
		if(StringUtils.isNotEmpty(parentId)){			
			orderFindService.buildRelationshipsOfContractAndOrderFind(orderInfo.getId(), parentId);
		}
		//4,更新其他操作信息和发送消息.
		//add the contract info to my contract list
		iContractMineService.saveOrUpdateMineContractWithCidOid(orderInfo.getId(), orderInfo.getBuyerid(), ContractStatus.DRAFT, ContractLifeCycle.DRAFTING, operator);
		//add the contract info to my contract list
		iContractMineService.saveOrUpdateMineContractWithCidOid(orderInfo.getId(), orderInfo.getSellerid(), ContractStatus.DRAFT, ContractLifeCycle.DRAFTING, operator);
		
		SystemMessageContent smc = SystemMessageContent.getMsgContentOfContractSign();
		this.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_MAKE_MATCH, orderInfo.getId(), orderInfo.getBuyerid(), smc,new KeyValue("fid", parentId));
		this.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_MAKE_MATCH, orderInfo.getId(), orderInfo.getSellerid(), smc,new KeyValue("fid", parentId));
		
		return orderInfo;
	}
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#makeAndMatchTOrderWithCustomService(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public TOrderInfo makeAndMatchTOrderWithCustomService(OrderFindAllBean bean, String sellerCid, String buyerCid,
			String operator) throws ServiceException {
		if( bean == null || StringUtils.isEmpty(sellerCid) || StringUtils.isEmpty(buyerCid) || StringUtils.isEmpty(operator) || bean.getOfBean() == null){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//指定询单属于卖家,而且指定为出售类型.
		bean.getOfBean().setCid(sellerCid);
		bean.getOfBean().setType(OrderTypeEnum.ORDER_TYPE_SELL);
		return this.makeAndMatchATOrderInfo(bean, buyerCid, operator);
	}
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#queryContractListOfMineForPaginationToWebCms(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOrderInfo> queryContractListOfMineForPaginationToWebCms(QueryContext<TOrderInfo> qContext) {
		if(qContext == null){
			return null;
		}
		return iContractInfoDAO.queryContractListForPaginationOfUserToWebCms(qContext);
	}
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#queryCount(com.appabc.bean.pvo.TOrderInfo)
	 */
	@Override
	public int queryCount(TOrderInfo entity) {
		if(entity == null) entity = new TOrderInfo();
		return this.iContractInfoDAO.queryCount(entity);
	}
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractInfoService#queryCountOfFinished()
	 */
	@Override
	public int queryCountOfFinished() {
		return this.iContractInfoDAO.queryCountOfFinished();
	}
	
	

}
