package com.appabc.datas.service.contract.impl;

import com.appabc.bean.pvo.TOrderArbitrationResult;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.contract.IContractArbitrationResultDAO;
import com.appabc.datas.service.contract.IContractArbitrationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create Date  : 2014年9月3日 下午6:01:01
 */

@Service
public class ContractArbitrationResultServiceImpl extends ContractBaseService<TOrderArbitrationResult>
		implements IContractArbitrationResultService {

	@Autowired
	private IContractArbitrationResultDAO iContractArbitrationResultDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TOrderArbitrationResult entity) {
		iContractArbitrationResultDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TOrderArbitrationResult entity) {
		iContractArbitrationResultDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TOrderArbitrationResult entity) {
		iContractArbitrationResultDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		iContractArbitrationResultDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TOrderArbitrationResult query(TOrderArbitrationResult entity) {
		return iContractArbitrationResultDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TOrderArbitrationResult query(Serializable id) {
		return iContractArbitrationResultDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TOrderArbitrationResult> queryForList(
			TOrderArbitrationResult entity) {
		return iContractArbitrationResultDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TOrderArbitrationResult> queryForList(Map<String, ?> args) {
		return iContractArbitrationResultDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TOrderArbitrationResult> queryListForPagination(
			QueryContext<TOrderArbitrationResult> qContext) {
		return iContractArbitrationResultDAO.queryListForPagination(qContext);
	}

}
