package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月3日 下午5:40:36
 */

@Service(value="IContractOperationService")
public class ContractOperationServiceImpl extends BaseService<TOrderOperations>
		implements IContractOperationService {

	@Autowired
	private IContractOperationDAO iContractOperationDAO;
	
	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private PrimaryKeyGenerator pKGenerator;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderOperations entity) {
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.datas.service.contract.IContractOperationService#
	 * applyOrNotGoodsInfo(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public TOrderOperations applyOrNotGoodsInfo(String contractId, String type,
			String operator, String result, String pid) {
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		TOrderOperations entity = new TOrderOperations();
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		entity.setOid(contractId);
		entity.setOperator(operator);
		entity.setOperationtime(new Date());
		entity.setPlid(pid);
		entity.setType(type);
		entity.setResult(result);
		entity.setOrderstatus(bean.getLifecycle());
		entity.setRemark(result);
		iContractOperationDAO.save(entity);
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractOperationService#confirmUninstallGoods(java.lang.String)  
	 */
	@Transactional(rollbackFor=Exception.class)
	public void confirmUninstallGoods(String contractId,String confirmer,String confirmerName) throws ServiceException {
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		if(!StringUtils.equalsIgnoreCase(bean.getSellerid(), confirmer)){
			throw new ServiceException("you are not the seller ,you can not to confirm uninstall goods.");
		}
		Date now = new Date();
		bean.setStatus(ContractStatus.DOING.getValue());
		bean.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS.getValue());
		bean.setUpdater(confirmer);
		bean.setUpdatetime(now);
		iContractInfoDAO.update(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(contractId);
		operator.setOperator(confirmer);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.CONFIRM_UNINSTALLGOODS.getValue());
		operator.setOrderstatus(ContractLifeCycle.UNINSTALLED_GOODS.getValue());
		StringBuffer result = new StringBuffer(confirmerName);
		result.append(MessagesUtil.getMessage("UNINSTALLGOODSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		this.add(operator);
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
	public TOrderOperations applyOrPassGoodsInfo(String contractId,String operType, String pid, String operator,String operatorName) {
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		bean.setStatus(ContractStatus.DOING.getValue());
		if (bean.getLifecycle().equalsIgnoreCase(
				ContractLifeCycle.PAYED_FUNDS.getValue())
				|| StringUtils.equalsIgnoreCase(bean.getLifecycle(),
						ContractLifeCycle.SENT_GOODS.getValue())
				|| bean.getLifecycle().equalsIgnoreCase(
						ContractLifeCycle.SIMPLE_CHECKING.getValue())){
			bean.setLifecycle(ContractLifeCycle.SIMPLE_CHECKED.getValue());
		}else if(bean.getLifecycle().equalsIgnoreCase(ContractLifeCycle.SIMPLE_CHECKED.getValue()) || bean.getLifecycle().equalsIgnoreCase(ContractLifeCycle.FULL_TAKEOVERING.getValue())){
			bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERED.getValue());
		}
		iContractInfoDAO.update(bean);
		TOrderOperations entity = new TOrderOperations();
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		entity.setOid(contractId);
		entity.setOperator(operator);
		entity.setOperationtime(new Date());
		entity.setOrderstatus(bean.getLifecycle());
		entity.setPlid(pid);
		entity.setType(operType);
		StringBuffer result = new StringBuffer(operatorName);
		if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.APPLY_DISPRICE.getValue())){
			result.append(MessagesUtil.getMessage("CONTRACT_APPLY_DISPRICE_TIPS"));
		}else if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.VALIDATE_PASS.getValue())){
			result.append(MessagesUtil.getMessage("CONTRACT_VALIDATE_PASS_TIPS"));
		}
		entity.setResult(result.toString());
		entity.setRemark(result.toString());
		iContractOperationDAO.save(entity);
		return entity;
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

}
