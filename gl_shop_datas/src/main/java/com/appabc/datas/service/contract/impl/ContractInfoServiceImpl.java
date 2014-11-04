package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.enums.ContractInfo;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.enums.ContractInfo.ContractType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.enums.PurseInfo.BusinessType;
import com.appabc.pay.enums.PurseInfo.OnOffLine;
import com.appabc.pay.enums.PurseInfo.PurseType;
import com.appabc.pay.enums.PurseInfo.TradeStatus;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.BaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TOrderInfo entity) {
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTINFOID"));
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
			throw new ServiceException("you had to confirm the contract, can not to confirm the contract again.");
		}
		//step 2, if you to confirm this contract ,you can to do, but here is some condition,
		//if you partner was confirmed,
		int i = 0;
		List<?> r = iContractOperationDAO.queryForList(contractId);
		TOrderInfo bean = this.query(contractId);
		if(r!=null && r.size() > 0){
			i = 1;
			bean.setStatus(ContractStatus.DOING.getValue());
			bean.setOtype(ContractType.SIGNED.getValue());
			bean.setLifecycle(ContractLifeCycle.SINGED.getValue());
		}else{
			bean.setStatus(ContractStatus.DRAFT.getValue());
			bean.setOtype(ContractType.DRAFT.getValue());
			bean.setLifecycle(ContractLifeCycle.DRAFTING.getValue());
		}
		Date now = new Date();
		bean.setUpdatetime(now);
		bean.setUpdater(userId);
		this.modify(bean);
		operator = new TOrderOperations();
		operator.setId(pKGenerator
				.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		if(r!=null && r.size() > 0){
			operator.setType(ContractOperateType.CONFRIM_CONTRACT.getValue());
			operator.setOrderstatus(ContractLifeCycle.SINGED.getValue());
		}else{
			operator.setType(ContractOperateType.CONFRIM_CONTRACT.getValue());
			operator.setOrderstatus(ContractLifeCycle.DRAFTING.getValue());
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
		bean.setStatus(ContractStatus.DOING.getValue());
		bean.setLifecycle(ContractLifeCycle.SENT_GOODS.getValue());
		bean.setUpdater(userId);
		bean.setUpdatetime(new Date());
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(pKGenerator
				.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(new Date());
		operator.setType(ContractOperateType.SEND_GOODS.getValue());
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
		Date now = new Date();
		TOrderInfo bean = this.query(contractId);
		if(!StringUtils.equalsIgnoreCase(userId, bean.getBuyerid())){
			throw new ServiceException("you are not buyer, you can not to confirm receive the goods.");
		}
		bean.setStatus(ContractStatus.DOING.getValue());
		bean.setLifecycle(ContractLifeCycle.RECEIVED_GOODS.getValue());
		bean.setUpdater(userId);
		bean.setUpdatetime(now);
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(pKGenerator
				.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.CONFIRM_RECEIVEGOODS.getValue());
		operator.setOrderstatus(ContractLifeCycle.RECEIVED_GOODS.getValue());
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("VALIDATEGOODSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//这需要去走合同正常的结算过程,放到service里面操作
		//
		float balance = ContractCostDetailUtil.getGuarantyCost(bean.getTotalamount());// bean.getTotalamount()*0.4f;
		iPassPayService.guarantyToUngelation(bean.getBuyerid(), balance, contractId);
		iPassPayService.guarantyToUngelation(bean.getSellerid(), balance, contractId);
		
		TPassbookInfo buyerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT.getValue());
		TPassbookInfo sellerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getSellerid(), PurseType.DEPOSIT.getValue());
		//
		if(bean.getAmount()>0.0f){
			iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), sellerPurseInfo.getId(), bean.getAmount(), PurseType.DEPOSIT.getValue());
			float servieBalance = ContractCostDetailUtil.getServiceCost(bean.getAmount());// bean.getAmount()*0.3f;
			iPassPayService.transferAccounts(sellerPurseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), servieBalance, PurseType.DEPOSIT.getValue());
		}
		//
		if(bean.getAmount()!=bean.getTotalamount()){
			float retAmount = bean.getTotalamount()-bean.getAmount();
			iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), buyerPurseInfo.getId(), retAmount, PurseType.DEPOSIT.getValue());
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractInfoService#payContractFunds(java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Transactional(rollbackFor = Exception.class)
	public TOrderInfo payContractFunds(String contractId, String userId,
			String userName) throws ServiceException{
		TOrderInfo bean = this.query(contractId);
		//if the torderinfo is null or the contract status is not 2, so then can not to update the info.
		if (bean == null
				|| !StringUtils.equalsIgnoreCase(bean.getStatus(),
						ContractStatus.DOING.getValue())
				|| !StringUtils.equalsIgnoreCase(bean.getOtype(),
						ContractType.SIGNED.getValue())
				|| !StringUtils.equalsIgnoreCase(bean.getLifecycle(),
						ContractLifeCycle.SINGED.getValue())) {
			throw new ServiceException(
					"the contract is null or the contract status is not doing and the type is not signed.");
		}
		//
		if(!bean.getBuyerid().equalsIgnoreCase(userId)){
			throw new ServiceException("the user is not buyer, can not to pay the contract.");
		}
		Date now = new Date();
		bean.setStatus(ContractStatus.DOING.getValue());
		bean.setUpdater(userId);
		bean.setLifecycle(ContractLifeCycle.PAYED_FUNDS.getValue());
		bean.setUpdatetime(now);
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(pKGenerator
				.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.PAYED_FUNDS.getValue());
		operator.setOrderstatus(ContractLifeCycle.PAYED_FUNDS.getValue());
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("PAYCONTRACTFUNDSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//pay contract funds
		TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(userId, PurseType.DEPOSIT.getValue());
		boolean ff = iPassPayService.transferAccounts(purseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), bean.getTotalamount(), PurseType.DEPOSIT.getValue());
		if(!ff){
			throw new ServiceException("pay the contract error.");
		}
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
				|| !StringUtils.equalsIgnoreCase(bean.getStatus(),
						ContractStatus.DOING.getValue())
				|| !StringUtils.equalsIgnoreCase(bean.getOtype(),
						ContractType.SIGNED.getValue())
				|| StringUtils.equalsIgnoreCase(bean.getLifecycle(),
						ContractLifeCycle.SINGED.getValue())) {
			throw new ServiceException(
					"the contract is null or the contract status is not doing and the type is not signed.");
		}
		//
		if(!bean.getBuyerid().equalsIgnoreCase(userId)){
			throw new ServiceException("the user is not buyer, can not to pay the contract.");
		}
		Date now = DateUtil.getNowDate();
		bean.setStatus(ContractStatus.DOING.getValue());
		bean.setUpdater(userId);
		bean.setLifecycle(ContractLifeCycle.IN_THE_PAYMENT.getValue());
		bean.setUpdatetime(now);
		this.modify(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(pKGenerator
				.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperator(userId);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.PAYED_FUNDS.getValue());
		operator.setOrderstatus(ContractLifeCycle.IN_THE_PAYMENT.getValue());
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("PAYCONTRACTFUNDSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//offline pay record 
		TOfflinePay tOfflinePay = new TOfflinePay();
		tOfflinePay.setOid(contractId);
		tOfflinePay.setOtype(PurseType.DEPOSIT.getValue());
		tOfflinePay.setPtype(OnOffLine.OFFLINE.getValue());
		tOfflinePay.setCreater(bean.getBuyerid());
		tOfflinePay.setCreattime(DateUtil.getNowDate());
		tOfflinePay.setStatus(TradeStatus.REQUEST.getValue());
		tOfflinePay.setBtype(BusinessType.PAY.getValue());
		tOfflinePay.setAmount(bean.getTotalamount());
		tOfflinePay.setTotal(bean.getTotalamount());
		try {
			iPassPayService.saveOfflinePay(tOfflinePay);
		} catch (com.appabc.pay.exception.ServiceException e) {
			throw new ServiceException(e.getCause());
		}
		return bean;
	}
	
	/* (non-Javadoc)获取企业的交易成功率
	 * @see com.appabc.datas.service.contract.IContractInfoService#getTransactionSuccessRate(java.lang.String)
	 */
	public EvaluationInfoBean getTransactionSuccessRate(String cid){
		
		EvaluationInfoBean ei = new EvaluationInfoBean();
		float allCount = this.iContractInfoDAO.countByCid(cid, null, ContractInfo.ContractStatus.FINISHED.getValue());
		if(allCount>0){
			// 交易成功数
			int successCount = this.iContractInfoDAO.countByCid(cid, ContractInfo.ContractLifeCycle.NORMAL_FINISHED.getValue(), ContractInfo.ContractStatus.FINISHED.getValue());
			
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
		return this.iContractInfoDAO.countByCid(cid, null, null);
	}

}
