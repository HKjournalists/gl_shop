/**  
 * com.appabc.pay.service.impl.PassbookInfoServiceImpl.java  
 *   
 * 2014年9月17日 上午10:34:10  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.dao.IPassbookInfoDAO;
import com.appabc.pay.service.local.IPassbookInfoService;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月17日 上午10:34:10
 */

public class PassbookInfoServiceImpl extends BaseService<TPassbookInfo>
		implements IPassbookInfoService {

	private IPassbookInfoDAO iPassbookInfoDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TPassbookInfo entity) {
		iPassbookInfoDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TPassbookInfo entity) {
		iPassbookInfoDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TPassbookInfo entity) {
		iPassbookInfoDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iPassbookInfoDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TPassbookInfo query(TPassbookInfo entity) {
		return iPassbookInfoDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TPassbookInfo query(Serializable id) {
		return iPassbookInfoDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TPassbookInfo> queryForList(TPassbookInfo entity) {
		return iPassbookInfoDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TPassbookInfo> queryForList(Map<String, ?> args) {
		return iPassbookInfoDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TPassbookInfo> queryListForPagination(
			QueryContext<TPassbookInfo> qContext) {
		return iPassbookInfoDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPassbookInfoDAO  
	 *  
	 * @return  the iPassbookInfoDAO  
	 * @since   1.0.0  
	 */
	
	public IPassbookInfoDAO getIPassbookInfoDAO() {
		return iPassbookInfoDAO;
	}

	/**  
	 * @param iPassbookInfoDAO the iPassbookInfoDAO to set  
	 */
	public void setIPassbookInfoDAO(IPassbookInfoDAO iPassbookInfoDAO) {
		this.iPassbookInfoDAO = iPassbookInfoDAO;
	}
	
}
