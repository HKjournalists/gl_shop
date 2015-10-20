package com.appabc.datas.service.contract.impl;

import com.appabc.bean.enums.ContractInfo.ContractCancelType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractCancelDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.service.order.IOrderFindMatchService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.utils.SystemMessageContent;

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
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月3日 下午5:53:55
 */

@Service(value="IContractCancelService")
public class ContractCancelServiceImpl extends ContractBaseService<TOrderCancel> implements
		IContractCancelService {

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private IContractCancelDAO iContractCancelDAO;

	@Autowired
	private ICompanyRankingService iCompanyRankingService;
	
	@Autowired
	private IContractMineService iContractMineService;

	@Autowired
	private IOrderFindService orderFindService;
	
	@Autowired
	private IOrderFindMatchService iOrderFindMatchService;
	
	private boolean checkContractIsCanCancel(TOrderInfo toi){
		if(toi.getStatus() != ContractStatus.DOING){
			return false;
		}
		if(toi.getLifecycle() == ContractLifeCycle.FINALESTIMATEING){
			return false;
		}
		if(toi.getLifecycle() == ContractLifeCycle.FINALESTIMATE_FINISHED){
			return false;
		}
		if(toi.getLifecycle() == ContractLifeCycle.ARBITRATING){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	public void add(TOrderCancel entity) {
		//entity.setId(getKey(DataSystemConstant.CONTRACTCANCELID));
		iContractCancelDAO.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TOrderCancel entity) {
		iContractCancelDAO.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TOrderCancel entity) {
		iContractCancelDAO.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractCancelDAO.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TOrderCancel query(TOrderCancel entity) {
		return iContractCancelDAO.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderCancel query(Serializable id) {
		return iContractCancelDAO.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TOrderCancel> queryForList(TOrderCancel entity) {
		return iContractCancelDAO.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderCancel> queryForList(Map<String, ?> args) {
		return iContractCancelDAO.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderCancel> queryListForPagination(
			QueryContext<TOrderCancel> qContext) {
		return iContractCancelDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractCancelService#finalCancelContract(java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void singleCancelContract(String oid,String cid,String cname) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo toi = iContractInfoDAO.query(oid);
		//如果合同为空,就不能取消操作了.
		if(toi == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		//取消的时候,对操作人是否是买卖双方的判断,放到controller里面.
		//合同在已经卸货或者已经收货或者在结算中或者结算完成,是不能取消合同的.
		//或者合同不在进行中的状态
		boolean isCanCeancelContract = checkContractIsCanCancel(toi);
		if(!isCanCeancelContract){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_ALLOW_CANCEL_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_ALLOW_CANCEL_ERROR));
		}
		ContractLifeCycle clc = toi.getLifecycle();
		
		Date now = DateUtil.getNowDate();
		toi.setStatus(ContractStatus.FINISHED);
		toi.setLifecycle(ContractLifeCycle.SINGLECANCEL_FINISHED);
		toi.setUpdater(cid);
		toi.setUpdatetime(now);
		iContractInfoDAO.update(toi);
		//保存合同操作记录
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(oid);
		operator.setOperationtime(now);
		operator.setOperator(cid);
		operator.setType(ContractOperateType.SINGLE_CANCEL);
		operator.setOrderstatus(ContractLifeCycle.SINGLECANCEL_FINISHED);
		operator.setOldstatus(clc);
		StringBuilder result = new StringBuilder(cname);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CANCELCONTRACTTIPS));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//保存合同取消记录
		TOrderCancel cancel = new TOrderCancel();
		cancel.setId(getKey(DataSystemConstant.CONTRACTCANCELID));
		cancel.setLid(operator.getId());
		cancel.setCanceler(cid);
		cancel.setCanceltime(now);
		cancel.setCanceltype(ContractCancelType.SINGLE_CANCEL);
		this.iContractCancelDAO.save(cancel);
		
		String targetId = "";
		SystemMessageContent smc = null;
		
		if(StringUtils.equalsIgnoreCase(cid, toi.getBuyerid())){//如果是操作者是买家，发系统消息和推送消息的内容不一样
			targetId = toi.getSellerid();
			smc = SystemMessageContent.getMsgContentOfContractBuyerSingleCancelToSeller(toi.getRemark());
		}else if(StringUtils.equalsIgnoreCase(cid, toi.getSellerid())){//如果操作是卖家 ,发系统消息和推送消息的内容不一样
			targetId = toi.getBuyerid();
			smc = SystemMessageContent.getMsgContentOfContractSellerSingleCancelToBuyer(toi.getRemark());
		}
		//单独发送系统消息
		this.sendOnlySystemMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL, oid, targetId, smc);
		
		//合同取消的结算操作
		contractFinalEstimate(toi, cid, cname);
		//更新实际支付货款的值.
		iContractInfoDAO.update(toi);
		//移至到操作人的结束合同列表
		iContractMineDAO.saveOrUpdateMineContractWithCidOid(toi.getId(), cid, toi.getStatus(), toi.getLifecycle(), cid);
		
		//save or update the mine contract with cid or oid.
		//取消不需要移到已经结束列表
		//contractTimeOutMoveToFinishList(toi, cid);
		
		//计算合同相关的成功,失败率.
		iCompanyRankingService.calculateTradeSuccessRate(toi.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(toi.getSellerid());
		
		//单独发送xmpp消息
		this.sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL, oid, targetId, smc);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractCancelService#multiCancelContract(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public int multiCancelContract(String contractId, String userId, String userName) throws ServiceException {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo toi = iContractInfoDAO.query(contractId);
		//如果合同为空,就不能取消操作了.
		if(toi == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		//合同在已经卸货或者已经收货或者在结算中或者结算完成,是不能取消合同的.
		//或者合同不在进行中的状态
		boolean isCanCeancelContract = checkContractIsCanCancel(toi);
		if(!isCanCeancelContract){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_ALLOW_CANCEL_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_ALLOW_CANCEL_ERROR));
		}
		//查看合同确认取消的记录数,根据操作记录数判断是否最后确认取消,还是第一次确认取消
		List<TOrderOperations> operators = iContractOperationDAO.queryForList(contractId,ContractOperateType.CANCEL_CONFIRM.getVal());
		boolean flag = false;
		if(CollectionUtils.isNotEmpty(operators)){
			for(TOrderOperations oper : operators){
				boolean f = oper.getType() == ContractOperateType.CANCEL_CONFIRM
						&& (StringUtils.equalsIgnoreCase(oper.getOperator(),toi.getBuyerid()) 
								|| StringUtils.equalsIgnoreCase(oper.getOperator(), toi.getSellerid()));
				if(f){
					flag = true;
					break;
				}
			}
		}
		Date now = DateUtil.getNowDate();
		StringBuilder result = new StringBuilder(userName);
		result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CANCELCONTRACTTIPS));
		ContractLifeCycle clc = toi.getLifecycle();
		if(flag){//合同是最后确认取消
			toi.setStatus(ContractStatus.FINISHED);
			toi.setLifecycle(ContractLifeCycle.DUPLEXCANCEL_FINISHED);
			toi.setUpdater(userId);
			toi.setUpdatetime(now);
			iContractInfoDAO.update(toi);
			//保存合同操作记录最终确认取消
			TOrderOperations operator = new TOrderOperations();
			operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			operator.setOid(contractId);
			operator.setOperationtime(now);
			operator.setOperator(userId);
			operator.setType(ContractOperateType.CANCEL_CONFIRM);
			operator.setOrderstatus(ContractLifeCycle.DUPLEXCANCEL_FINISHED);
			operator.setOldstatus(clc);
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			if(CollectionUtils.isNotEmpty(operators)){
				operator.setPlid(operators.get(operators.size()-1).getId());
			}
			iContractOperationDAO.save(operator);
			//保存取消记录
			TOrderCancel cancel = new TOrderCancel();
			cancel.setId(getKey(DataSystemConstant.CONTRACTCANCELID));
			cancel.setLid(operator.getId());
			cancel.setCanceler(userId);
			cancel.setCanceltime(now);
			cancel.setCanceltype(ContractCancelType.DUPLEX_CANCEL);
			iContractCancelDAO.save(cancel);
			//解冻双方的保证金,并计算出扣除的手续费用
			//deduct the contract guaranty to gelation
			this.contractFinalEstimate(toi, userId, userName);
			//更新实际支付货款的值.
			iContractInfoDAO.update(toi);
			//计算合同相关的成功,失败率.
			iCompanyRankingService.calculateTradeSuccessRate(toi.getBuyerid());
			iCompanyRankingService.calculateTradeSuccessRate(toi.getSellerid());
			return 1;
		}else{//合同是第一次确认取消
			//合同状态时取消状态
			toi.setStatus(ContractStatus.DOING);
			toi.setLifecycle(ContractLifeCycle.CANCELING);
			toi.setUpdater(userId);
			toi.setUpdatetime(now);
			iContractInfoDAO.update(toi);
			//保存合同操作取消记录
			TOrderOperations operator = new TOrderOperations();
			operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			operator.setOid(contractId);
			operator.setOperationtime(now);
			operator.setOperator(userId);
			operator.setOldstatus(clc);
			operator.setType(ContractOperateType.CANCEL_CONFIRM);
			operator.setOrderstatus(ContractLifeCycle.CANCELING);
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			if(CollectionUtils.isNotEmpty(operators)){
				operator.setPlid(operators.get(operators.size()-1).getId());
			}
			iContractOperationDAO.save(operator);
			
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL, contractId, StringUtils.equalsIgnoreCase(userId, toi.getSellerid()) ? toi.getBuyerid() : toi.getSellerid(), SystemMessageContent.getMsgContentOfContractMutilCanceling(contractId,now));
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractCancelService#getCancelListByOID(java.lang.String)
	 */
	public List<TOrderCancel> getCancelContractListByOID(String oid) {
		return iContractCancelDAO.getCancelContractListByOID(oid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractCancelService#cancelDraftContract(java.lang.String, java.lang.String)  
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void cancelDraftContract(String oid,String cid,String cname,ContractOperateType operateType) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || operateType == null || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		if(ContractOperateType.CANCEL_CONTRACT != operateType && ContractOperateType.MOVE_TO_FINISHED_CONTRACT != operateType && ContractOperateType.DELETE_CONTRACT != operateType){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOTSUPPORT_OPERATETYPE_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_SUPPORT_OPERATETYPE_ERROR));
		}
		TOrderInfo toi = iContractInfoDAO.query(oid);
		//如果合同为空,就不能取消操作了.
		if(toi == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		//合同起草取消只能在合同起草状态和起草的生命周期是才能操作.
		boolean isNotLifeCycle = toi.getStatus() == ContractStatus.DRAFT || toi.getLifecycle() == ContractLifeCycle.DRAFTING || toi.getStatus() == ContractStatus.FINISHED;
		if(!isNotLifeCycle){
			throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR));
		}
		//如果,操作人不是买家或者不是卖家,也不能操作合同. 移植controller进行检查
		/*if(!StringUtils.equalsIgnoreCase(cid, toi.getBuyerid()) && !StringUtils.equalsIgnoreCase(cid, toi.getSellerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR));
		}*/
		if(ContractOperateType.CANCEL_CONTRACT == operateType) {
			Date limitDateTime = DateUtil.getDateMoveByHours(toi.getCreatime(), ContractCostDetailUtil.getContractDraftConfirmLimitNumWD());
			boolean isNotTimeOut = DateUtil.isTargetGtSourceDateNo235959(DateUtil.getNowDate(), limitDateTime);
			if(!isNotTimeOut){
				throw new ServiceException(ServiceErrorCode.CONTRACT_TO_CONFIRM_CONTRACT_TIMEOUT_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
			}
			/*
			Date limitDateTime = DateUtil.getDate(toi.getCreatime(), ContractCostDetailUtil.getContractDraftConfirmLimitNum());
			boolean isNotTimeOut = DateUtil.isTargetGtSourceDateNo235959(DateUtil.getNowDate(), limitDateTime);
			if(!isNotTimeOut){
				throw new ServiceException(ServiceErrorCode.CONTRACT_TO_CONFIRM_CONTRACT_TIMEOUT_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
			}*/
		}
		ContractLifeCycle clc = null;
		ContractStatus cs = null;
		if(ContractOperateType.DELETE_CONTRACT == operateType){
			clc = ContractLifeCycle.DELETE_CONTRACT;
			cs = ContractStatus.DELETION;
		} else {
			clc = ContractLifeCycle.DRAFTING_CANCEL;
			cs = ContractStatus.FINISHED;
		}
		TOrderOperations queryEntity = new TOrderOperations();
		queryEntity.setOid(oid);
		queryEntity.setOperator(cid);
		queryEntity.setType(operateType);
		queryEntity.setOrderstatus(clc);
		List<TOrderOperations> res = iContractOperationDAO.queryForList(queryEntity);
		if(CollectionUtils.isNotEmpty(res)){
			throw new ServiceException(ServiceErrorCode.SUBMIT_OPERATOR_AGAIN,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CANCEL_AGAIN_ERROR));
		}
		String parentId = orderFindService.getFidByContractId(toi.getId());
		//只有当取消合同的时候,才需要更新合同的状态信息.
		//如果是移动到结束合同列表和删除合同是不需要操作合同的状态
		if(operateType == ContractOperateType.CANCEL_CONTRACT){
			ContractLifeCycle oclc = toi.getLifecycle();
			Date now = DateUtil.getNowDate();
			toi.setStatus(cs);
			toi.setLifecycle(clc);
			toi.setUpdater(cid);
			toi.setUpdatetime(now);
			iContractInfoDAO.update(toi);
			
			TOrderOperations operator = new TOrderOperations();
			operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			operator.setOid(oid);
			operator.setOperationtime(now);
			operator.setOperator(cid);
			operator.setType(operateType);
			operator.setOrderstatus(clc);
			operator.setOldstatus(oclc);
			StringBuilder result = new StringBuilder(cname);
			result.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CANCELCONTRACTTIPS));
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			iContractOperationDAO.save(operator);
			String sender = StringUtils.equalsIgnoreCase(cid, toi.getBuyerid()) ? toi.getSellerid() : toi.getBuyerid();
			SystemMessageContent smc = StringUtils.equalsIgnoreCase(cid, toi.getBuyerid()) ? SystemMessageContent.getMsgContentOfContractDraftCancelWithBuyer(toi.getRemark()) : SystemMessageContent.getMsgContentOfContractDraftCancelWithSeller(toi.getRemark());
			sendOnlySystemMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_DAF_CANCEL, oid, sender, smc,new KeyValue("fid", parentId));
			//解冻保证金
			this.guarantyToUngelation(oid, toi.getBuyerid(), toi.getSellerid(), toi.getTotalamount());
			//将相关的询单信息回滚操作
			orderFindService.rollbackOrderFindByContractid(oid);
			//通过合同记录的询单ID找到父询单，看看是否有保存记录信息，直接回滚.
			iOrderFindMatchService.rollbackOrderFindInfoWithContract(toi.getFid());
		}
		
		//the can cancel the contract status lifecycle
		if(ContractOperateType.CANCEL_CONTRACT == operateType){
			clc = ContractLifeCycle.DRAFTING_CANCEL;
			cs = ContractStatus.DELETION;
		}
		iContractMineService.saveOrUpdateMineContractWithCidOid(oid, cid, cs, clc, cid);
		//send some message to canceler.
		if(ContractOperateType.CANCEL_CONTRACT == operateType){
			String sender = StringUtils.equalsIgnoreCase(cid, toi.getBuyerid()) ? toi.getSellerid() : toi.getBuyerid();
			SystemMessageContent smc = StringUtils.equalsIgnoreCase(cid, toi.getBuyerid()) ? SystemMessageContent.getMsgContentOfContractDraftCancelWithBuyer(toi.getRemark()) : SystemMessageContent.getMsgContentOfContractDraftCancelWithSeller(toi.getRemark()); 
			sendOnlyXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_DAF_CANCEL, oid, sender, smc,new KeyValue("fid", parentId));
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractCancelService#jobAutoRollbackCancelContract(com.appabc.bean.pvo.TOrderInfo, java.lang.String, com.appabc.bean.enums.ContractInfo.ContractLifeCycle)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void jobAutoRollbackCancelContract(TOrderInfo bean, String cid,
			ContractLifeCycle lastClc) throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid) || lastClc == null){
			return ;
		}
		ContractLifeCycle clc = bean.getLifecycle();
		//合同回滚到合同取消前的一个状态
		Date now = DateUtil.getNowDate();
		//TOrderOperations lastOperator = operationLists.get(operationLists.size()-2);
		bean.setLifecycle(lastClc);
		bean.setStatus(ContractStatus.DOING);
		bean.setUpdatetime(now);
		bean.setUpdater(cid);
		this.iContractInfoDAO.update(bean);

		//记录回滚合同的操作记录
		TOrderOperations oper = new TOrderOperations();
		oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		oper.setOid(bean.getId());
		oper.setOperator(cid);
		oper.setOperationtime(now);
		oper.setType(ContractOperateType.REPEAL_CANCEL);
		oper.setOrderstatus(lastClc);
		oper.setOldstatus(clc);
		StringBuilder sb = new StringBuilder(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_TO_CANCEL_TIMEOUT_FAILTIPS, Locale.forLanguageTag("datas")));
		oper.setResult(sb.toString());
		oper.setRemark(sb.toString());
		this.iContractOperationDAO.save(oper);
		
		//send system message and xmpp message
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, bean.getId(), bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractCancelFailure(bean.getId()));
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, bean.getId(), bean.getSellerid(), SystemMessageContent.getMsgContentOfContractCancelFailure(bean.getId()));
	}

}
