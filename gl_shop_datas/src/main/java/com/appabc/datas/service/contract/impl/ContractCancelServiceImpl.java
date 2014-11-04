package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.exception.BaseException;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.contract.IContractCancelDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.enums.ContractInfo.ContractCancelType;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.enums.PurseInfo.PurseType;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月3日 下午5:53:55
 */

@Service
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
	
	private boolean checkContractIsCanCancel(TOrderInfo toi){
		if(!StringUtils.equalsIgnoreCase(toi.getStatus(), ContractStatus.DOING.getValue())){
			return false;
		}
		if(StringUtils.equalsIgnoreCase(toi.getLifecycle(), ContractLifeCycle.UNINSTALLED_GOODS.getValue())){
			return false;
		}
		if(StringUtils.equalsIgnoreCase(toi.getLifecycle(), ContractLifeCycle.RECEIVED_GOODS.getValue())){
			return false;
		}
		/*if(StringUtils.equalsIgnoreCase(toi.getLifecycle(), ContractLifeCycle.CANCELING.getValue())){
			return false;
		}*/
		if(StringUtils.equalsIgnoreCase(toi.getLifecycle(), ContractLifeCycle.FINALESTIMATEING.getValue())){
			return false;
		}
		if(StringUtils.equalsIgnoreCase(toi.getLifecycle(), ContractLifeCycle.FINALESTIMATE_FINISHED.getValue())){
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TOrderCancel entity) {
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTCANCELID"));
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
	public void singleCancelContract(String contractId,String userId,String userName) throws BaseException {
		TOrderInfo toi = iContractInfoDAO.query(contractId);
		if(!StringUtils.equalsIgnoreCase(userId, toi.getBuyerid()) && !StringUtils.equalsIgnoreCase(userId, toi.getSellerid())){
			throw new ServiceException("the user is not contract seller or buyer.");
		}
		boolean isCanCeancelContract = checkContractIsCanCancel(toi);
		if(!isCanCeancelContract){
			throw new ServiceException("the contract status and life cycyle is not allow you to cancel the contract.");
		}
		Date now = new Date();
		toi.setStatus(ContractStatus.FINISHED.getValue());
		toi.setLifecycle(ContractLifeCycle.SINGLECANCEL_FINISHED.getValue());
		toi.setUpdater(userId);
		toi.setUpdatetime(now);
		iContractInfoDAO.update(toi);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperationtime(now);
		operator.setOperator(userId);
		operator.setType(ContractOperateType.SINGLE_CANCEL.getValue());
		operator.setOrderstatus(ContractLifeCycle.SINGLECANCEL_FINISHED.getValue());
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("CANCELCONTRACTTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		iContractOperationDAO.save(operator);
		TOrderCancel cancel = new TOrderCancel();
		cancel.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTCANCELID"));
		cancel.setLid(operator.getId());
		cancel.setCanceler(userId);
		cancel.setCanceltime(now);
		cancel.setCanceltype(ContractCancelType.SINGLE_CANCEL.getValue());
		this.iContractCancelDAO.save(cancel);
		//
		float balance = ContractCostDetailUtil.getGuarantyCost(toi.getTotalamount());
		iPassPayService.guarantyToUngelation(toi.getBuyerid(), balance, contractId);
		iPassPayService.guarantyToUngelation(toi.getSellerid(), balance, contractId);
		//
		TPassbookInfo sourAcc = iPassPayService.getPurseAccountInfo(userId, PurseType.GUARANTY.getValue());
		String olid = "";
		if(!toi.getBuyerid().equalsIgnoreCase(userId)){
			olid = toi.getBuyerid();
		}else{
			olid = toi.getSellerid();
		}
		TPassbookInfo destAcc = iPassPayService.getPurseAccountInfo(olid, PurseType.GUARANTY.getValue());
		iPassPayService.transferAccounts(sourAcc.getId(), destAcc.getId(), balance, PurseType.GUARANTY.getValue());
		//
		TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(toi.getBuyerid(), PurseType.DEPOSIT.getValue());
		iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), toi.getTotalamount(), PurseType.DEPOSIT.getValue());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractCancelService#multiCancelContract(java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Transactional(rollbackFor=Exception.class)
	public int multiCancelContract(String contractId, String userId, String userName) throws BaseException {
		TOrderInfo toi = iContractInfoDAO.query(contractId);
		boolean isCanCeancelContract = checkContractIsCanCancel(toi);
		if(!isCanCeancelContract){
			throw new ServiceException("the contract status and life cycyle is not allow you to cancel the contract.");
		}
		List<TOrderOperations> operators = iContractOperationDAO.queryForList(contractId,ContractOperateType.CANCEL_CONFIRM.getValue());
		boolean flag = false;
		Date now = new Date();
		for(TOrderOperations oper : operators){
			boolean f = StringUtils.equalsIgnoreCase(oper.getType(),
					ContractOperateType.CANCEL_CONFIRM.getValue())
					&& (StringUtils.equalsIgnoreCase(oper.getOperator(),
							toi.getBuyerid()) || StringUtils.equalsIgnoreCase(
							oper.getOperator(), toi.getSellerid()));
			if(f){
				flag = true;
				break;
			}
		}
		StringBuffer result = new StringBuffer(userName);
		result.append(MessagesUtil.getMessage("CANCELCONTRACTTIPS"));
		if(flag){
			toi.setStatus(ContractStatus.FINISHED.getValue());
			toi.setLifecycle(ContractLifeCycle.DUPLEXCANCEL_FINISHED.getValue());
			toi.setUpdater(userId);
			toi.setUpdatetime(now);
			iContractInfoDAO.update(toi);
			
			TOrderOperations operator = new TOrderOperations();
			operator.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
			operator.setOid(contractId);
			operator.setOperationtime(now);
			operator.setOperator(userId);
			operator.setType(ContractOperateType.CANCEL_CONFIRM.getValue());
			operator.setOrderstatus(ContractLifeCycle.DUPLEXCANCEL_FINISHED.getValue());
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			if(operators!=null && operators.size()>0){				
				operator.setPlid(operators.get(operators.size()-1).getId());
			}
			iContractOperationDAO.save(operator);
			
			TOrderCancel cancel = new TOrderCancel();
			cancel.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTCANCELID"));
			cancel.setLid(operator.getId());
			cancel.setCanceler(userId);
			cancel.setCanceltime(now);
			cancel.setCanceltype(ContractCancelType.DUPLEX_CANCEL.getValue());
			iContractCancelDAO.save(cancel);
			
			//deduct the contract guaranty to gelation
			float balance = ContractCostDetailUtil.getGuarantyCost(toi.getTotalamount());
			iPassPayService.guarantyToUngelation(toi.getBuyerid(), balance, contractId);
			iPassPayService.guarantyToUngelation(toi.getSellerid(), balance, contractId);
				
			TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(toi.getBuyerid(), PurseType.DEPOSIT.getValue());
			iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), toi.getTotalamount(), PurseType.DEPOSIT.getValue());
			return 1;
		}else{
			toi.setStatus(ContractStatus.DOING.getValue());
			toi.setLifecycle(ContractLifeCycle.CANCELING.getValue());
			toi.setUpdater(userId);
			toi.setUpdatetime(now);
			iContractInfoDAO.update(toi);
			
			TOrderOperations operator = new TOrderOperations();
			operator.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
			operator.setOid(contractId);
			operator.setOperationtime(now);
			operator.setOperator(userId);
			operator.setType(ContractOperateType.CANCEL_CONFIRM.getValue());
			operator.setOrderstatus(ContractLifeCycle.CANCELING.getValue());
			operator.setResult(result.toString());
			operator.setRemark(result.toString());
			if(operators!=null && operators.size()>0){				
				operator.setPlid(operators.get(operators.size()-1).getId());
			}
			iContractOperationDAO.save(operator);
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
