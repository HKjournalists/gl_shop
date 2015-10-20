/**  
 * com.appabc.pay.service.impl.PassbookPayServiceImpl.java  
 *   
 * 2014年9月17日 上午11:32:24  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.dao.IPassbookPayDAO;
import com.appabc.pay.service.local.IPassbookPayService;

/**
 * @Description : Pass Book Pay Service IMPL
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月17日 上午11:32:24
 */

public class PassbookPayServiceImpl extends BaseService<TPassbookPay> implements
		IPassbookPayService {

	private IPassbookPayDAO iPassbookPayDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TPassbookPay entity) {
		iPassbookPayDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TPassbookPay entity) {
		iPassbookPayDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TPassbookPay entity) {
		iPassbookPayDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iPassbookPayDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TPassbookPay query(TPassbookPay entity) {
		return iPassbookPayDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TPassbookPay query(Serializable id) {
		return iPassbookPayDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TPassbookPay> queryForList(TPassbookPay entity) {
		return iPassbookPayDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TPassbookPay> queryForList(Map<String, ?> args) {
		return iPassbookPayDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TPassbookPay> queryListForPagination(
			QueryContext<TPassbookPay> qContext) {
		return iPassbookPayDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPassbookPayDAO  
	 *  
	 * @return  the iPassbookPayDAO  
	 * @since   1.0.0  
	 */
	
	public IPassbookPayDAO getIPassbookPayDAO() {
		return iPassbookPayDAO;
	}

	/**  
	 * @param iPassbookPayDAO the iPassbookPayDAO to set  
	 */
	public void setIPassbookPayDAO(IPassbookPayDAO iPassbookPayDAO) {
		this.iPassbookPayDAO = iPassbookPayDAO;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPassbookPayService#getPayRecordListWithOid(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, java.lang.String, com.appabc.bean.enums.PurseInfo.PayDirection)  
	 */
	@Override
	public List<TPassbookPay> getPayRecordListWithOid(String cid,
			PurseType type, String oid, PayDirection payDirection) {
		return this.iPassbookPayDAO.getPayRecordListWithOid(cid, type, oid, payDirection);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPassbookPayService#queryPayListWithParams(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, java.lang.String, com.appabc.bean.enums.PurseInfo.TradeType, com.appabc.bean.enums.PurseInfo.PayDirection)  
	 */
	@Override
	public List<TPassbookPay> getPayListWithParams(String cid,PurseType pType, String oid, TradeType tType, PayDirection direction) {
		return iPassbookPayDAO.queryPayListWithParams(cid, pType, oid, tType, direction);
	}
	
}
