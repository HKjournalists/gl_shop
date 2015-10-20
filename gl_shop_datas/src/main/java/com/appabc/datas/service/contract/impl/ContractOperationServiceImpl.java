package com.appabc.datas.service.contract.impl;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.ToolsConstant;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Description : 合同操作记录服务service
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月3日 下午5:40:36
 */

@Service(value="IContractOperationService")
public class ContractOperationServiceImpl extends ContractBaseService<TOrderOperations>
		implements IContractOperationService {

	@Autowired
	private IContractInfoDAO iContractInfoDAO;
	
	@Autowired
	private IOrderFindService orderFindService;
	
	@Autowired
	private IContractMineService iContractMineService;
	
	@Autowired
	private ICompanyRankingService iCompanyRankingService;
	
	@Autowired
	private ICompanyEvaluationService iCompanyEvaluationService;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderOperations entity) {
		iContractOperationDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void modify(TOrderOperations entity) {
		iContractOperationDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void delete(TOrderOperations entity) {
		iContractOperationDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractOperationDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public TOrderOperations query(TOrderOperations entity) {
		return iContractOperationDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderOperations query(Serializable id) {
		return iContractOperationDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public List<TOrderOperations> queryForList(TOrderOperations entity) {
		return iContractOperationDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderOperations> queryForList(Map<String, ?> args) {
		return iContractOperationDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderOperations> queryListForPagination(
			QueryContext<TOrderOperations> qContext) {
		return iContractOperationDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForList(java.lang.String, java.lang.String)
	 */
	public List<TOrderOperations> queryForList(String contractId, String type) {
		return iContractOperationDAO.queryForList(contractId, type);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForList(java.lang.String)
	 */
	public List<TOrderOperations> queryForList(String contractId) {
		return this.iContractOperationDAO.queryForList(contractId);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForListWithOIDAndOper(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TOrderOperations> queryForListWithOIDAndOper(String contractId,
			String operator) {
		return iContractOperationDAO.queryForListWithOIDAndOper(contractId, operator);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.datas.service.contract.IContractOperationService#
	 * applyOrNotGoodsInfo(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public TOrderOperations applyOrNotGoodsInfo(String contractId, String type,
			String operator, String result, String pid) {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(type) || StringUtils.isEmpty(operator)){
			return null;
		}
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		ContractLifeCycle clc = bean.getLifecycle();
		TOrderOperations entity = new TOrderOperations();
		entity.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		entity.setOid(contractId);
		entity.setOperator(operator);
		entity.setOperationtime(DateUtil.getNowDate());
		entity.setPlid(pid);
		entity.setType(ContractOperateType.enumOf(type));
		entity.setResult(result);
		entity.setOrderstatus(ContractLifeCycle.RECEIVED_GOODS);
		entity.setOldstatus(clc);
		entity.setRemark(result);
		iContractOperationDAO.save(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#confirmUninstallGoods(java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void confirmUninstallGoods(String contractId,String confirmer,String confirmerName) throws ServiceException {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(confirmer) || StringUtils.isEmpty(confirmerName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		if(!StringUtils.equalsIgnoreCase(bean.getSellerid(), confirmer)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_UNINSTALLS_GOODS_NOTSELLER_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_UNINSTALLS_GOODS_NOTSELLER_ERROR));
		}
		Date now = DateUtil.getNowDate();
		ContractLifeCycle clc = bean.getLifecycle();
		bean.setStatus(ContractStatus.DOING);
		bean.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS);
		bean.setUpdater(confirmer);
		bean.setUpdatetime(now);
		iContractInfoDAO.update(bean);
		
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(confirmer);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.CONFIRM_UNINSTALLGOODS);
		operator.setOrderstatus(ContractLifeCycle.UNINSTALLED_GOODS);
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(confirmerName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_UNINSTALLGOODSTIPS));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		this.add(operator);
		
		//议价完成,卖家确认卸货后，提醒买家确认收货.
		this.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, contractId, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractReceiptGoodsTimeout(contractId));
		//卖家确认卸货,触发条件发送消息告诉卖家可以进行评价
		this.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, contractId, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractCompletion(contractId));
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#getContractChangeHistory(java.lang.String)
	 */
	public List<?> getContractChangeHistory(String contractId) {
		return iContractOperationDAO.queryForList(contractId);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#applyOrPassGoodsInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public TOrderOperations applyOrPassGoodsInfo(String contractId,String operType, String pid, String operator,String operatorName) throws ServiceException {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(operType) || StringUtils.isEmpty(operator) || StringUtils.isEmpty(operatorName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		Date now = DateUtil.getNowDate();
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		bean.setStatus(ContractStatus.DOING);
		ContractLifeCycle lifeCycle = bean.getLifecycle();
		boolean isFullTakeOvered = false;
		//ContractLifeCycle.PAYED_FUNDS.getValue()
		ContractOperateType cot = ContractOperateType.enumOf(operType);
		if (lifeCycle == ContractLifeCycle.PAYED_FUNDS || lifeCycle == ContractLifeCycle.SENT_GOODS || lifeCycle == ContractLifeCycle.SIMPLE_CHECKING){
			if(cot == ContractOperateType.VALIDATE_PASS){
				if(lifeCycle == ContractLifeCycle.SIMPLE_CHECKING){
					throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR));
				}
				bean.setLifecycle(ContractLifeCycle.SIMPLE_CHECKED);
			}else if(cot == ContractOperateType.APPLY_DISPRICE){
				//ContractOperateType.enumOf(operType) == ContractOperateType.APPLY_DISPRICE;
				bean.setLifecycle(ContractLifeCycle.SIMPLE_CHECKED);
			} else {
				throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR));
			}
		}else if(lifeCycle == ContractLifeCycle.SIMPLE_CHECKED || lifeCycle == ContractLifeCycle.FULL_TAKEOVERING){
			//bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERED);
			//处理第二次议价的同意议价的操作（正常情况下同意则导致第二次议价通过，但是第二次议价的买发起第一次也是调同意接口因此这时候不能将议价结束）
			if(cot == ContractOperateType.APPLY_DISPRICE){
				//ContractOperateType.enumOf(operType) == ContractOperateType.APPLY_DISPRICE;
				if(lifeCycle == ContractLifeCycle.SIMPLE_CHECKED){
					//刚好第二次议价的买家第一次的同意，必须等待卖的同意才能算议价通过
					bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERING);
				} else {
					//其他安装正常的逻辑结束议价
					bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERED);
					isFullTakeOvered =  true;
				}
			} else {
				throw new ServiceException(ServiceErrorCode.CONTRACT_JUST_ALLOW_APPLY_DISPRICE_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_JUST_ALLOW_APPLY_DISPRICE_ERROR));
			}
		} else {
			throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR));
		}
		iContractInfoDAO.update(bean);
		
		TOrderOperations entity = new TOrderOperations();
		entity.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		entity.setOid(contractId);
		entity.setOperator(operator);
		entity.setOperationtime(now);
		entity.setOrderstatus(bean.getLifecycle());
		entity.setPlid(pid);
		entity.setType(ContractOperateType.enumOf(operType));
		entity.setOldstatus(lifeCycle);
		StringBuilder result = new StringBuilder(operatorName);
		if(ContractOperateType.enumOf(operType) == ContractOperateType.APPLY_DISPRICE){
			result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_APPLY_DISPRICE_TIPS));
		}else if(ContractOperateType.enumOf(operType) == ContractOperateType.VALIDATE_PASS){
			result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_VALIDATE_PASS_TIPS));
		}
		entity.setResult(result.toString());
		entity.setRemark(result.toString());
		iContractOperationDAO.save(entity);
		
		//买家同意全量验收议价后，告诉卖家.
		if(isFullTakeOvered){
			this.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, contractId, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractReceivingEndToBuyer(contractId));
			this.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, contractId, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractReceiptGoods(contractId));
		}
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#getIsContractPayRecord(java.lang.String, java.lang.String)  
	 */
	@Override
	public boolean getIsContractPayRecord(String oid, String cid) {
		return this.iContractOperationDAO.getIsContractPayRecord(oid, cid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForListWithOidAndOperAndTypeAndOrderLifeCycle(java.lang.String, java.lang.String, com.appabc.bean.enums.ContractInfo.ContractOperateType, com.appabc.bean.enums.ContractInfo.ContractLifeCycle)  
	 */
	@Override
	public List<TOrderOperations> queryForListWithOidAndOperAndTypeAndOrderLifeCycle(
			String oid, String cid, ContractOperateType type,
			ContractLifeCycle lifeCycle) {
		return this.iContractOperationDAO.queryForListWithOidAndOperAndTypeAndOrderLifeCycle(oid, cid, type, lifeCycle);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForListWithOidAndCidAndType(java.lang.String, java.lang.String, com.appabc.bean.enums.ContractInfo.ContractOperateType)  
	 */
	@Override
	public TOrderOperations queryForListWithOidAndCidAndType(String oid,
			String cid, ContractOperateType type) {
		return this.iContractOperationDAO.queryForListWithOidAndCidAndType(oid, cid, type);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#jobAutoDraftConfirmTimeoutFinish(TOrderInfo bean,String cid)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void jobAutoDraftConfirmTimeoutFinish(TOrderInfo bean,String cid) throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid)){
			return ;
		}
		Date now = DateUtil.getNowDate();
		log.info(" Contract is "+bean.getId()+"; enter the timeout finish the Contract. ");
		ContractLifeCycle clc = bean.getLifecycle();
		bean.setLifecycle(ContractLifeCycle.TIMEOUT_FINISHED);
		bean.setStatus(ContractStatus.FINISHED);
		bean.setUpdater(cid);
		bean.setUpdatetime(now);
		
		SystemMessageContent bSmc = null;
		SystemMessageContent sSmc = null;
		List<?> res = queryForListWithOidAndOperAndTypeAndOrderLifeCycle(bean.getId(), bean.getBuyerid(), ContractOperateType.CONFRIM_CONTRACT, ContractLifeCycle.DRAFTING);
		if(CollectionUtils.isNotEmpty(res)){
			bSmc = SystemMessageContent.getMsgContentOfContractTimeoutconfirmWithBuyerOrSellerIsBuyer(bean.getRemark(),false);
			sSmc = SystemMessageContent.getMsgContentOfContractTimeoutconfirmWithBuyerOrSellerYouIsBuyer(bean.getRemark(),false);
		}else{
			res = queryForListWithOidAndOperAndTypeAndOrderLifeCycle(bean.getId(), bean.getSellerid(), ContractOperateType.CONFRIM_CONTRACT, ContractLifeCycle.DRAFTING);
			if(CollectionUtils.isNotEmpty(res)){
				bSmc = SystemMessageContent.getMsgContentOfContractTimeoutconfirmWithBuyerOrSellerYouIsBuyer(bean.getRemark(),true);
				sSmc = SystemMessageContent.getMsgContentOfContractTimeoutconfirmWithBuyerOrSellerIsBuyer(bean.getRemark(),true);
			} else {
				bSmc = SystemMessageContent.getMsgContentOfContractBuyerSellerNotConfirm(bean.getRemark());
				sSmc = SystemMessageContent.getMsgContentOfContractBuyerSellerNotConfirm(bean.getRemark());
			}
		}
		log.info(" QueryForListWithOidAndOperAndTypeAndOrderLifeCycle result is : "+res);
		MsgBusinessType mbt = MsgBusinessType.BUSINESS_TYPE_CONTRACT_DAF_TIMEOUT;
		log.info(" Buyer SystemMessageContent is : "+bSmc);
		log.info(" Seller SystemMessageContent is : "+sSmc);
		this.iContractInfoDAO.update(bean);
		log.info(" Contract is "+bean.getId()+"; roll back OrderFind with the ContractId. ");
		//将合同的相关询单信息回滚.
		orderFindService.rollbackOrderFindByContractid(bean.getId());
		//send the system message
		sendOnlySystemMessage(mbt, bean.getId(), bean.getSellerid(), sSmc);
		sendOnlySystemMessage(mbt, bean.getId(), bean.getBuyerid(), bSmc);
		//解冻保证金
		guarantyToUngelation(bean.getId(), bean.getBuyerid(), bean.getSellerid(), bean.getTotalamount());
		
		String parentFid = orderFindService.getFidByContractId(bean.getId());
		
		TOrderOperations oper = new TOrderOperations();
		oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		oper.setOid(bean.getId());
		oper.setOperator(cid);
		oper.setOperationtime(now);
		oper.setType(ContractOperateType.CONFRIM_CONTRACT);
		oper.setOldstatus(clc);
		oper.setOrderstatus(ContractLifeCycle.TIMEOUT_FINISHED);
		StringBuilder sb = new StringBuilder(getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_CONFIRM_TIMEOUT_FAILTIPS));
		oper.setResult(sb.toString());
		oper.setRemark(sb.toString());
		add(oper);
		log.info(" Contract is "+bean.getId()+"; end the timeout finish the Contract. ");
		//send the xmpp message
		sendOnlyXmppMessage(mbt, bean.getId(), bean.getSellerid(), sSmc, new KeyValue("fid", parentFid));
		sendOnlyXmppMessage(mbt, bean.getId(), bean.getBuyerid(), bSmc, new KeyValue("fid", parentFid));
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#jobAutoConfirmGoodsInfoContract()  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void jobAutoConfirmGoodsInfoContract(TOrderInfo bean,String cid) throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid)){
			return ;
		}
		Date now = DateUtil.getNowDate();
		ContractLifeCycle clc = bean.getLifecycle();
		bean.setLifecycle(ContractLifeCycle.RECEIVED_GOODS);
		bean.setStatus(ContractStatus.DOING);
		bean.setUpdater(ToolsConstant.SYSTEMCID);
		bean.setUpdatetime(now);
		this.iContractInfoDAO.update(bean);
		
		TOrderOperations oper = new TOrderOperations();
		oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		oper.setOid(bean.getId());
		oper.setOperator(ToolsConstant.SYSTEMCID);
		oper.setOperationtime(now);
		oper.setType(ContractOperateType.CONFIRM_RECEIVEGOODS);
		oper.setOrderstatus(ContractLifeCycle.RECEIVED_GOODS);
		oper.setOldstatus(clc);
		StringBuilder sb = new StringBuilder(getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_CONFIRM_GOODS_TIPS));
		oper.setResult(sb.toString());
		oper.setRemark(sb.toString());
		add(oper);
		//send the system message and xmpp message
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, bean.getId(), bean.getSellerid(), new SystemMessageContent(sb.toString()));
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, bean.getId(), bean.getBuyerid(), new SystemMessageContent(sb.toString()));
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#jobAutoEvaluationContract(com.appabc.bean.pvo.TOrderInfo, java.lang.String)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void jobAutoEvaluationContract(TOrderInfo bean, String cid)
			throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid)){
			return ;
		}
		Date now = DateUtil.getNowDate();
		
		TOrderOperations result = iContractOperationDAO.queryForListWithOidAndCidAndType(bean.getId(), bean.getSellerid(), ContractOperateType.AGREE_FUND_GOODS_CONFIRM);
		Date d = null;
		if(result != null){
			d = result.getOperationtime();
		} else {
			d = bean.getUpdatetime();
		}
		int hours = DateUtil.getDifferHoursWithTwoDate(d, now);
		
		//int hours = DateUtil.getDifferHoursWithTwoDate(bean.getUpdatetime(), now);
		boolean isTimeout = hours >= ContractCostDetailUtil.getContractEvaluatioinLimitNum();
		TCompanyEvaluation tce = new TCompanyEvaluation();
		tce.setOid(bean.getId());
		tce.setCid(bean.getBuyerid());
		tce.setCreater(bean.getSellerid());
		List<TCompanyEvaluation> res = iCompanyEvaluationService.queryForList(tce);
		StringBuilder sb = new StringBuilder(getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_DEFAULT_EVALUATION_TIPS));
		if(isTimeout && CollectionUtils.isEmpty(res)){
			log.info(" Contract is "+bean.getId()+"; was be evaluation start..... ");
			TOrderOperations oper = new TOrderOperations();
			oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			oper.setOid(bean.getId());
			oper.setOperator(bean.getSellerid());
			oper.setOperationtime(now);
			oper.setType(ContractOperateType.EVALUATION_CONTRACT);
			oper.setOrderstatus(bean.getLifecycle());
			oper.setOldstatus(bean.getLifecycle());
			oper.setResult(sb.toString());
			oper.setRemark(sb.toString());
			add(oper);

			TCompanyEvaluation t = new TCompanyEvaluation();
			t.setEvaluation(sb.toString());
			t.setOid(bean.getId());
			t.setCid(bean.getBuyerid());
			t.setCredit(5);
			t.setSatisfaction(5);
			t.setCreater(bean.getSellerid());
			t.setCratedate(now);
			iCompanyEvaluationService.add(t);

			log.info(" Contract is "+bean.getId()+"; was be to calculate the trade evaluation rate. ");
			//计算评价相关的交易满意度,交易诚信度
			iCompanyRankingService.calculateTradeEvaluationRate(t.getCid());
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, bean.getId(), bean.getSellerid(), new SystemMessageContent(sb.toString()));
		}
		
		TCompanyEvaluation tce1 = new TCompanyEvaluation();
		tce1.setOid(bean.getId());
		tce1.setCid(bean.getSellerid());
		tce1.setCreater(bean.getBuyerid());
		List<TCompanyEvaluation> res1 = iCompanyEvaluationService.queryForList(tce1);
		if(isTimeout && CollectionUtils.isEmpty(res1)){
			log.info(" Contract is "+bean.getId()+"; was be evaluation start..... ");
			TOrderOperations oper = new TOrderOperations();
			oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			oper.setOid(bean.getId());
			oper.setOperator(bean.getBuyerid());
			oper.setOperationtime(now);
			oper.setType(ContractOperateType.EVALUATION_CONTRACT);
			oper.setOrderstatus(bean.getLifecycle());
			oper.setOldstatus(bean.getLifecycle());
			oper.setResult(sb.toString());
			oper.setRemark(sb.toString());
			add(oper);

			TCompanyEvaluation t = new TCompanyEvaluation();
			t.setEvaluation(sb.toString());
			t.setOid(bean.getId());
			t.setCid(bean.getSellerid());
			t.setCredit(5);
			t.setSatisfaction(5);
			t.setCreater(bean.getBuyerid());
			t.setCratedate(now);
			iCompanyEvaluationService.add(t);

			log.info(" Contract is "+bean.getId()+"; was be to calculate the trade evaluation rate. ");
			//计算评价相关的交易满意度,交易诚信度
			iCompanyRankingService.calculateTradeEvaluationRate(t.getCid());

			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION, bean.getId(), bean.getBuyerid(), new SystemMessageContent(sb.toString()));
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#jobAutoTimeoutContractPayFundFinish()  
	 */
	@Override
	public void jobAutoTimeoutContractPayFundFinish(TOrderInfo bean,String cid,String cname) throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			return ;
		}
		Date now = DateUtil.getNowDate();
		Date payGoodsLimitTime = ContractCostDetailUtil.getPayGoodsLimitTime(bean.getUpdatetime(), bean.getLimittime());
		long timeMillis = DateUtil.getDifferTimeMillisTwoDate(payGoodsLimitTime, now);
		if(timeMillis>=0){
			ContractLifeCycle clc = bean.getLifecycle();
			bean.setLifecycle(ContractLifeCycle.BUYER_UNPAY_FINISHED);
			bean.setStatus(ContractStatus.FINISHED);
			bean.setUpdater(cid);
			bean.setUpdatetime(now);
			this.iContractInfoDAO.update(bean);
			log.info(" set the Contract id is : "+bean.getId()+" Life Cycle is buyer unpay finished. ");
			
			TOrderOperations oper = new TOrderOperations();
			oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			oper.setOid(bean.getId());
			oper.setOperator(ToolsConstant.SYSTEMCID);
			oper.setOperationtime(now);
			oper.setType(ContractOperateType.BUYER_UNPAY_FINISHED);
			oper.setOrderstatus(ContractLifeCycle.BUYER_UNPAY_FINISHED);
			oper.setOldstatus(clc);
			StringBuilder sb = new StringBuilder(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_BUYER_UNPAY_FINISHED_TIPS, Locale.forLanguageTag("datas")));
			oper.setResult(sb.toString());
			oper.setRemark(sb.toString());
			add(oper);
			log.info(" set the Contract id is : "+bean.getId()+" Life Cycle is buyer unpay finish the operations . ");
			
			//这需要去走合同买家未付款结束结算过程,放到service里面操作
			log.info(" The Contract is : "+bean);
			contractFinalEstimate(bean, cid, cname);
			this.iContractInfoDAO.update(bean);
			log.info(" set the Contract id is : "+bean.getId()+" Life Cycle is buyer unpay doing final estimate . ");
			log.info(" set the Contract id is : "+bean.getId()+" Life Cycle is buyer unpay finish the final estimate . ");
			
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_PAYFUNDS_TIMEOUT, bean.getId(), bean.getSellerid(), SystemMessageContent.getMsgContentOfContractBuyerUnpayfundsToSeller(bean.getRemark()));
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_PAYFUNDS_TIMEOUT, bean.getId(), bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractBuyerUnpayfundsToBuyer(bean.getRemark()));
			//
			int diffDays = DateUtil.getDifferDayWithTwoDate(bean.getLimittime(), now);
			if(diffDays >= 0){
				log.info(" set the Contract id is : "+bean.getId()+" Life Cycle is buyer unpay finish to finished list . ");
				iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), ContractStatus.FINISHED, bean.getLifecycle(), cid);
				iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), ContractStatus.FINISHED, bean.getLifecycle(), cid);
			}
		}
	}

}
