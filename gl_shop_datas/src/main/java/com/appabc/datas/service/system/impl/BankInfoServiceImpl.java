/**  
 * com.appabc.pay.service.local.impl.BankInfoServiceImpl.java  
 *   
 * 2014年9月22日 上午11:33:50  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.service.system.impl;

import com.appabc.bean.pvo.TBankInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.system.IBankInfoDAO;
import com.appabc.datas.service.system.IBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月22日 上午11:33:50
 */

@Service(value = "IBankInfoService")
public class BankInfoServiceImpl extends BaseService<TBankInfo> implements
		IBankInfoService {

	@Autowired
	private IBankInfoDAO iBankInfoDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TBankInfo entity) {
		iBankInfoDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TBankInfo entity) {
		iBankInfoDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TBankInfo entity) {
		iBankInfoDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iBankInfoDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TBankInfo query(TBankInfo entity) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TBankInfo query(Serializable id) {
		return iBankInfoDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TBankInfo> queryForList(TBankInfo entity) {
		return iBankInfoDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TBankInfo> queryForList(Map<String, ?> args) {
		return iBankInfoDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TBankInfo> queryListForPagination(
			QueryContext<TBankInfo> qContext) {
		return iBankInfoDAO.queryListForPagination(qContext);
	}

	/**
	 * iBankInfoDAO
	 * 
	 * @return the iBankInfoDAO
	 * @since 1.0.0
	 */

	public IBankInfoDAO getIBankInfoDAO() {
		return iBankInfoDAO;
	}

	/**
	 * @param iBankInfoDAO
	 *            the iBankInfoDAO to set
	 */
	public void setIBankInfoDAO(IBankInfoDAO iBankInfoDAO) {
		this.iBankInfoDAO = iBankInfoDAO;
	}

}
