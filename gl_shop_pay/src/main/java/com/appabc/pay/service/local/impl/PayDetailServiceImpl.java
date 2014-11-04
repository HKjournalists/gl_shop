/**  
 * com.appabc.pay.service.impl.PayDetailServiceImpl.java  
 *   
 * 2014年9月17日 上午11:38:26  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPayDetail;
import com.appabc.pay.dao.IPayDetailDAO;
import com.appabc.pay.service.local.IPayDetailService;

/**
 * @Description : Pay Detail Service IMPL
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月17日 上午11:38:26
 */

public class PayDetailServiceImpl extends BaseService<TPayDetail> implements
		IPayDetailService {

	private IPayDetailDAO iPayDetailDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TPayDetail entity) {
		iPayDetailDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TPayDetail entity) {
		iPayDetailDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TPayDetail entity) {
		iPayDetailDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iPayDetailDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TPayDetail query(TPayDetail entity) {
		return iPayDetailDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TPayDetail query(Serializable id) {
		return iPayDetailDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TPayDetail> queryForList(TPayDetail entity) {
		return iPayDetailDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TPayDetail> queryForList(Map<String, ?> args) {
		return iPayDetailDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TPayDetail> queryListForPagination(
			QueryContext<TPayDetail> qContext) {
		return iPayDetailDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPayDetailDAO  
	 *  
	 * @return  the iPayDetailDAO  
	 * @since   1.0.0  
	 */
	
	public IPayDetailDAO getIPayDetailDAO() {
		return iPayDetailDAO;
	}

	/**  
	 * @param iPayDetailDAO the iPayDetailDAO to set  
	 */
	public void setIPayDetailDAO(IPayDetailDAO iPayDetailDAO) {
		this.iPayDetailDAO = iPayDetailDAO;
	}

}
