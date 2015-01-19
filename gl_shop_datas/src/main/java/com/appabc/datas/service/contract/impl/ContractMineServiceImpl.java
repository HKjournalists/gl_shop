package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TOrderMine;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.contract.IContractMineDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月24日 下午6:21:07
 */

@Service(value="IContractMineService")
public class ContractMineServiceImpl extends ContractBaseService<TOrderMine> implements
		IContractMineService {

	@Autowired
	private IContractMineDAO iContractMineDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TOrderMine entity) {
		iContractMineDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TOrderMine entity) {
		iContractMineDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TOrderMine entity) {
		iContractMineDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iContractMineDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TOrderMine query(TOrderMine entity) {
		return iContractMineDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TOrderMine query(Serializable id) {
		return iContractMineDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TOrderMine> queryForList(TOrderMine entity) {
		return iContractMineDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TOrderMine> queryForList(Map<String, ?> args) {
		return iContractMineDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOrderMine> queryListForPagination(
			QueryContext<TOrderMine> qContext) {
		return iContractMineDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractMineService#queryOrderMineWithCidOid(java.lang.String, java.lang.String)  
	 */
	@Override
	public TOrderMine queryOrderMineWithCidOid(String oid, String cid) {
		return iContractMineDAO.queryOrderMineWithCidOid(oid, cid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractMineService#updateMineContractWithCid(java.lang.String, java.lang.String, com.appabc.bean.enums.ContractInfo.ContractStatus, com.appabc.bean.enums.ContractInfo.ContractLifeCycle, java.lang.String)  
	 */
	@Override
	public boolean saveOrUpdateMineContractWithCidOid(String oid, String cid,
			ContractStatus status, ContractLifeCycle lifeCycle, String operator)
			throws ServiceException {
		if(StringUtils.isEmpty(cid) || StringUtils.isEmpty(oid) || status == null || lifeCycle == null || StringUtils.isEmpty(operator)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		return iContractMineDAO.saveOrUpdateMineContractWithCidOid(oid, cid, status, lifeCycle, operator);
	}

}
