/**  
 * com.appabc.pay.service.impl.PassbookDrawServiceImpl.java  
 *   
 * 2014年9月17日 上午11:34:37  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookDrawEx;
import com.appabc.pay.dao.IPassbookDrawDAO;
import com.appabc.pay.service.local.IPassbookDrawService;

/**
 * @Description : PassbookDrawServiceImpl
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月17日 上午11:34:37
 */

public class PassbookDrawServiceImpl extends BaseService<TPassbookDraw>
		implements IPassbookDrawService {

	private IPassbookDrawDAO iPassbookDrawDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TPassbookDraw entity) {
		iPassbookDrawDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TPassbookDraw entity) {
		iPassbookDrawDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TPassbookDraw entity) {
		iPassbookDrawDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iPassbookDrawDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TPassbookDraw query(TPassbookDraw entity) {
		return iPassbookDrawDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TPassbookDraw query(Serializable id) {
		return iPassbookDrawDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TPassbookDraw> queryForList(TPassbookDraw entity) {
		return iPassbookDrawDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TPassbookDraw> queryForList(Map<String, ?> args) {
		return iPassbookDrawDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TPassbookDraw> queryListForPagination(
			QueryContext<TPassbookDraw> qContext) {
		return iPassbookDrawDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPassbookDrawDAO  
	 *  
	 * @return  the iPassbookDrawDAO  
	 * @since   1.0.0  
	 */
	
	public IPassbookDrawDAO getIPassbookDrawDAO() {
		return iPassbookDrawDAO;
	}

	/**  
	 * @param iPassbookDrawDAO the iPassbookDrawDAO to set  
	 */
	public void setIPassbookDrawDAO(IPassbookDrawDAO iPassbookDrawDAO) {
		this.iPassbookDrawDAO = iPassbookDrawDAO;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPassbookDrawService#getTPassbookDrawByPassId(java.lang.String)  
	 */
	public List<TPassbookDraw> getTPassbookDrawByPassId(String passId) {
		return iPassbookDrawDAO.getTPassbookDrawByPassId(passId);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPassbookDrawService#extractCashRequestListEx(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookDrawEx> extractCashRequestListEx(
			QueryContext<TPassbookDrawEx> qContext) {
		if(qContext == null){
			return null;
		}
		return iPassbookDrawDAO.extractCashRequestListEx(qContext);
	}
	
}
