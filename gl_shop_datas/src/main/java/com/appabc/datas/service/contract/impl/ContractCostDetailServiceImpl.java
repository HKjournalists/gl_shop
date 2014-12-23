package com.appabc.datas.service.contract.impl;

import com.appabc.bean.pvo.TOrderCostdetail;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.contract.IContractCostDetailDAO;
import com.appabc.datas.service.contract.IContractCostDetailService;
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
 * Create Date  : 2014年9月3日 下午5:50:24
 */

@Service
public class ContractCostDetailServiceImpl extends BaseService<TOrderCostdetail> implements
		IContractCostDetailService {

	@Autowired
	private IContractCostDetailDAO iContractCostDetailDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TOrderCostdetail entity) {
		iContractCostDetailDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TOrderCostdetail entity) {
		iContractCostDetailDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TOrderCostdetail entity) {
		iContractCostDetailDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		iContractCostDetailDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TOrderCostdetail query(TOrderCostdetail entity) {
		return iContractCostDetailDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TOrderCostdetail query(Serializable id) {
		return iContractCostDetailDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TOrderCostdetail> queryForList(TOrderCostdetail entity) {
		return iContractCostDetailDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TOrderCostdetail> queryForList(Map<String, ?> args) {
		return iContractCostDetailDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TOrderCostdetail> queryListForPagination(
			QueryContext<TOrderCostdetail> qContext) {
		return iContractCostDetailDAO.queryListForPagination(qContext);
	}

}
