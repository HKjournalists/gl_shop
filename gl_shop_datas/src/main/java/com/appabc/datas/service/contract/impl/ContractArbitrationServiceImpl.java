package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractArbitrationDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年9月3日 下午5:57:49
 */

@Service
public class ContractArbitrationServiceImpl extends
		BaseService<TOrderArbitration> implements IContractArbitrationService {

	@Autowired
	private IContractArbitrationDAO iContractArbitrationDAO;

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private IContractOperationDAO iContractOperationDAO;

	@Autowired
	private PrimaryKeyGenerator pKGenerator;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderArbitration entity) {
		entity.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTARBITRATIONID"));
		iContractArbitrationDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void modify(TOrderArbitration entity) {
		iContractArbitrationDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void delete(TOrderArbitration entity) {
		iContractArbitrationDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractArbitrationDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public TOrderArbitration query(TOrderArbitration entity) {
		return iContractArbitrationDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderArbitration query(Serializable id) {
		return iContractArbitrationDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public List<TOrderArbitration> queryForList(TOrderArbitration entity) {
		return iContractArbitrationDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderArbitration> queryForList(Map<String, ?> args) {
		return iContractArbitrationDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderArbitration> queryListForPagination(
			QueryContext<TOrderArbitration> qContext) {
		return iContractArbitrationDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#
	 * toContractArbitration(java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public TOrderArbitration toContractArbitration(String contractId,
			String operator,String operatorName) {
		// step 1 : update the contract info;
		TOrderInfo contract = iContractInfoDAO.query(contractId);
		Date now = new Date();
		contract.setStatus(ContractStatus.DOING.getValue());
		contract.setLifecycle(ContractLifeCycle.ARBITRATION.getValue());
		contract.setUpdater(operator);
		contract.setUpdatetime(now);
		iContractInfoDAO.update(contract);
		// step 2 : save the operation info to operation table;
		StringBuffer result = new StringBuffer(operatorName);
		result.append(MessagesUtil.getMessage("TOCONTRACTARBITRATIONTIPS"));
		TOrderOperations operate = new TOrderOperations();
		operate.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operate.setOid(contractId);
		operate.setOperator(operator);
		operate.setOperationtime(now);
		operate.setType(ContractOperateType.ARBITRATION_CONTRACT.getValue());
		operate.setOrderstatus(ContractLifeCycle.ARBITRATION.getValue());
		operate.setResult(result.toString());
		operate.setRemark(result.toString());
		iContractOperationDAO.save(operate);
		// step 3 : save the arbitration to the arbitration table;
		TOrderArbitration arbitration = new TOrderArbitration();
		arbitration.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTARBITRATIONID"));
		arbitration.setLid(operate.getId());
		arbitration.setCreater(operator);
		arbitration.setCreatetime(now);
		arbitration.setStatus(0);
		arbitration.setRemark(result.toString());
		this.add(arbitration);
		return arbitration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#
	 * getContractArbitrationHistroy(java.lang.String)
	 */
	public List<?> getContractArbitrationHistroy(String contractId) {
		return iContractArbitrationDAO.queryArbitrationForList(contractId);
	}

}
