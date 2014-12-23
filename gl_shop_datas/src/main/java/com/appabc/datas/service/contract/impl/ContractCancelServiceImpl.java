package com.appabc.datas.service.contract.impl;

import com.appabc.bean.enums.ContractInfo.ContractCancelType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.contract.IContractCancelDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
public class ContractCancelServiceImpl extends BaseService<TOrderCancel> implements
		IContractCancelService {

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private IContractOperationDAO iContractOperationDAO;

	@Autowired
	private IContractCancelDAO iContractCancelDAO;

	@Autowired
	private PrimaryKeyGenerator pKGenerator;

	@Autowired
	private IPassPayService iPassPayService;

	@Autowired
	private ICompanyRankingService iCompanyRankingService;

	@Autowired
	private MessageSendManager mesgSender;

	private String getKey(String bid){
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		return pKGenerator.getPKey(bid);
	}
	
	private boolean checkContractIsCanCancel(TOrderInfo toi){
		if(toi.getStatus() != ContractStatus.DOING){
			return false;
		}
		if(toi.getLifecycle() == ContractLifeCycle.UNINSTALLED_GOODS){
			return false;
		}
		if(toi.getLifecycle() == ContractLifeCycle.RECEIVED_GOODS){
			return false;
		}
		/*if(toi.getLifecycle() == ContractLifeCycle.CANCELING){
			return false;
		}*/
		if(toi.getLifecycle() == ContractLifeCycle.FINALESTIMATEING){
			return false;
		}
		if(toi.getLifecycle() == ContractLifeCycle.FINALESTIMATE_FINISHED){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	public void add(TOrderCancel entity) {
		entity.setId(getKey(DataSystemConstant.CONTRACTCANCELID));
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
	public void singleCancelContract(String contractId,String userId,String userName) throws ServiceException {
		TOrderInfo toi = iContractInfoDAO.query(contractId);
		//如果合同为空,就不能取消操作了.
		if(toi == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,"the contract is null.");
		}
		//如果,操作人不是买家或者不是卖家,也不能操作合同.
		if(!StringUtils.equalsIgnoreCase(userId, toi.getBuyerid()) && !StringUtils.equalsIgnoreCase(userId, toi.getSellerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR,"the user is not contract seller or buyer.");
		}
		//合同在已经卸货或者已经收货或者在结算中或者结算完成,是不能取消合同的.
		//或者合同不在进行中的状态
		boolean isCanCeancelContract = checkContractIsCanCancel(toi);
		if(!isCanCeancelContract){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_ALLOW_CANCEL_ERROR,"the contract status and life cycyle is not allow you to cancel the contract.");
		}
		Date now = DateUtil.getNowDate();
		toi.setStatus(ContractStatus.FINISHED);
		toi.setLifecycle(ContractLifeCycle.SINGLECANCEL_FINISHED);
		toi.setUpdater(userId);
		toi.setUpdatetime(now);
		iContractInfoDAO.update(toi);
		//保存合同操作记录
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperationtime(now);
		operator.setOperator(userId);
		operator.setType(ContractOperateType.SINGLE_CANCEL);
		operator.setOrderstatus(ContractLifeCycle.SINGLECANCEL_FINISHED);
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("CANCELCONTRACTTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		//保存合同取消记录
		TOrderCancel cancel = new TOrderCancel();
		cancel.setId(getKey(DataSystemConstant.CONTRACTCANCELID));
		cancel.setLid(operator.getId());
		cancel.setCanceler(userId);
		cancel.setCanceltime(now);
		cancel.setCanceltype(ContractCancelType.SINGLE_CANCEL);
		this.iContractCancelDAO.save(cancel);
		//解冻双方的保证金,并计算出扣除的手续费用
		float balance = ContractCostDetailUtil.getGuarantyCost(toi.getTotalamount());
		if(balance > 0){
			iPassPayService.guarantyToUngelation(toi.getBuyerid(), balance, contractId);
			iPassPayService.guarantyToUngelation(toi.getSellerid(), balance, contractId);
		}
		//获取操作取消的人的钱包账户信息即源账户信息
		TPassbookInfo sourAcc = iPassPayService.getPurseAccountInfo(userId, PurseType.GUARANTY);
		String olid = "";
		if(!toi.getBuyerid().equalsIgnoreCase(userId)){
			olid = toi.getBuyerid();
		}else{
			olid = toi.getSellerid();
		}
		//获取目标账户信息.
		TPassbookInfo destAcc = iPassPayService.getPurseAccountInfo(olid, PurseType.GUARANTY);
		//将取消方的保证金转给被取消方.
		iPassPayService.transferAccounts(sourAcc.getId(), destAcc.getId(), balance, TradeType.VIOLATION_DEDUCTION);
		//将买家支付的货款返还给买家.[如果合同处理起草,签订,付款中的状态,则不需要返回货款,本来买家就还没有支付货款]
		//业务操作是买家支付过，会在操作记录里面记录支付记录，查询支付记录，有就退还合同款项
		//toi.getLifecycle() != ContractLifeCycle.DRAFTING && toi.getLifecycle() != ContractLifeCycle.SINGED && toi.getLifecycle() != ContractLifeCycle.IN_THE_PAYMENT
		TOrderOperations payRecord = new TOrderOperations();
		payRecord.setOperator(toi.getBuyerid());
		payRecord.setOid(toi.getId());
		payRecord.setType(ContractOperateType.PAYED_FUNDS);
		payRecord.setOrderstatus(ContractLifeCycle.PAYED_FUNDS);
		List<TOrderOperations> res = this.iContractOperationDAO.queryForList(payRecord);
		if(CollectionUtils.isNotEmpty(res)){
			TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(toi.getBuyerid(), PurseType.DEPOSIT);
			if(toi != null && toi.getTotalamount() > 0){
				iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), toi.getTotalamount(), PurseType.DEPOSIT);
			}
		}
		//计算合同相关的成功,失败率.
		iCompanyRankingService.calculateTradeSuccessRate(toi.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(toi.getSellerid());
		if(StringUtils.equalsIgnoreCase(userId, toi.getBuyerid())){//如果是操作者是买家，发系统消息和推送消息的内容不一样
			MessageInfoBean mi1 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL,contractId,toi.getBuyerid(),SystemMessageContent.getMsgContentOfContractCancelSuccessActive(contractId));
			mi1.setSendSystemMsg(true);
			mi1.setSendPushMsg(true);
			mesgSender.msgSend(mi1);

			MessageInfoBean mi2 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL,contractId,toi.getSellerid(),SystemMessageContent.getMsgContentOfContractCancelSuccessPassive(contractId));
			mi2.setSendSystemMsg(true);
			mi2.setSendPushMsg(true);
			mesgSender.msgSend(mi2);
		}else if(StringUtils.equalsIgnoreCase(userId, toi.getSellerid())){//如果操作是卖家 ,发系统消息和推送消息的内容不一样
			MessageInfoBean mi1 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL,contractId,toi.getBuyerid(),SystemMessageContent.getMsgContentOfContractCancelSuccessPassive(contractId));
			mi1.setSendSystemMsg(true);
			mi1.setSendPushMsg(true);
			mesgSender.msgSend(mi1);

			MessageInfoBean mi2 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL,contractId,toi.getSellerid(),SystemMessageContent.getMsgContentOfContractCancelSuccessActive(contractId));
			mi2.setSendSystemMsg(true);
			mi2.setSendPushMsg(true);
			mesgSender.msgSend(mi2);
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractCancelService#multiCancelContract(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public int multiCancelContract(String contractId, String userId, String userName) throws ServiceException {
		TOrderInfo toi = iContractInfoDAO.query(contractId);
		//如果合同为空,就不能取消操作了.
		if(toi == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,"the contract is null.");
		}
		//合同在已经卸货或者已经收货或者在结算中或者结算完成,是不能取消合同的.
		//或者合同不在进行中的状态
		boolean isCanCeancelContract = checkContractIsCanCancel(toi);
		if(!isCanCeancelContract){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_ALLOW_CANCEL_ERROR,"the contract status and life cycyle is not allow you to cancel the contract.");
		}
		//查看合同确认取消的记录数,根据操作记录数判断是否最后确认取消,还是第一次确认取消
		List<TOrderOperations> operators = iContractOperationDAO.queryForList(contractId,ContractOperateType.CANCEL_CONFIRM.getVal());
		boolean flag = false;
		if(operators != null){
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
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("CANCELCONTRACTTIPS"));
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
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			if(operators!=null && operators.size()>0){
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
			float balance = ContractCostDetailUtil.getGuarantyCost(toi.getTotalamount());
			if(balance > 0){
				iPassPayService.guarantyToUngelation(toi.getBuyerid(), balance, contractId);
				iPassPayService.guarantyToUngelation(toi.getSellerid(), balance, contractId);
			}

			//将买家支付的货款返还给买家.[如果合同处理起草,签订,付款中的状态,则不需要返回货款,本来买家就还没有支付货款]
			//业务操作是买家支付过，会在操作记录里面记录支付记录，查询支付记录，有就退还合同款项
			//toi.getLifecycle() != ContractLifeCycle.DRAFTING && toi.getLifecycle() != ContractLifeCycle.SINGED && toi.getLifecycle() != ContractLifeCycle.IN_THE_PAYMENT
			TOrderOperations payRecord = new TOrderOperations();
			payRecord.setOperator(toi.getBuyerid());
			payRecord.setOid(toi.getId());
			payRecord.setType(ContractOperateType.PAYED_FUNDS);
			payRecord.setOrderstatus(ContractLifeCycle.PAYED_FUNDS);
			List<TOrderOperations> res = this.iContractOperationDAO.queryForList(payRecord);
			if(CollectionUtils.isNotEmpty(res)){
				TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(toi.getBuyerid(), PurseType.DEPOSIT);
				if(toi != null && toi.getTotalamount() > 0){
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), toi.getTotalamount(), PurseType.DEPOSIT);
				}
			}
			
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
			operator.setType(ContractOperateType.CANCEL_CONFIRM);
			operator.setOrderstatus(ContractLifeCycle.CANCELING);
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			if(operators!=null && operators.size()>0){
				operator.setPlid(operators.get(operators.size()-1).getId());
			}
			iContractOperationDAO.save(operator);
			
			MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_CANCEL,contractId, StringUtils.equalsIgnoreCase(userId, toi.getSellerid()) ? toi.getBuyerid() : toi.getSellerid(),SystemMessageContent.getMsgContentOfContractMutilCanceling(contractId,now));
			mi.setSendSystemMsg(true);
			mi.setSendPushMsg(true);
			mesgSender.msgSend(mi);
			
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractCancelService#getCancelListByOID(java.lang.String)
	 */
	public List<TOrderCancel> getCancelContractListByOID(String oid) {
		return iContractCancelDAO.getCancelContractListByOID(oid);
	}

}
