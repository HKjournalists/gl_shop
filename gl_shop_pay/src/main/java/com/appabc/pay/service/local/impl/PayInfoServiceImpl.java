/**  
 * com.appabc.pay.service.impl.PayInfoServiceImpl.java  
 *   
 * 2014年9月17日 上午11:36:49  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPayInfo;
import com.appabc.pay.dao.IPayInfoDAO;
import com.appabc.pay.service.local.IPayInfoService;

/**
 * @Description : PayInfo Service IMPL
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月17日 上午11:36:49
 */

public class PayInfoServiceImpl extends BaseService<TPayInfo> implements
		IPayInfoService {

	private IPayInfoDAO iPayInfoDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TPayInfo entity) {
		iPayInfoDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TPayInfo entity) {
		iPayInfoDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TPayInfo entity) {
		iPayInfoDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iPayInfoDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TPayInfo query(TPayInfo entity) {
		return iPayInfoDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TPayInfo query(Serializable id) {
		return iPayInfoDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TPayInfo> queryForList(TPayInfo entity) {
		return iPayInfoDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TPayInfo> queryForList(Map<String, ?> args) {
		return iPayInfoDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TPayInfo> queryListForPagination(
			QueryContext<TPayInfo> qContext) {
		return iPayInfoDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPayInfoDAO  
	 *  
	 * @return  the iPayInfoDAO  
	 * @since   1.0.0  
	 */
	
	public IPayInfoDAO getIPayInfoDAO() {
		return iPayInfoDAO;
	}

	/**  
	 * @param iPayInfoDAO the iPayInfoDAO to set  
	 */
	public void setIPayInfoDAO(IPayInfoDAO iPayInfoDAO) {
		this.iPayInfoDAO = iPayInfoDAO;
	}
	
}
