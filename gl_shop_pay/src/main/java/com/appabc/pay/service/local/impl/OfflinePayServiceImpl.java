/**  
 * com.appabc.pay.service.local.impl.OfflinePayServiceImpl.java  
 *   
 * 2014年9月29日 上午11:51:26  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.dao.IOfflinePayDAO;
import com.appabc.pay.service.local.IOfflinePayService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月29日 上午11:51:26
 */

public class OfflinePayServiceImpl extends BaseService<TOfflinePay> implements
		IOfflinePayService {

	private IOfflinePayDAO iOfflinePayDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TOfflinePay entity) {
		iOfflinePayDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TOfflinePay entity) {
		iOfflinePayDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TOfflinePay entity) {
		iOfflinePayDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iOfflinePayDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TOfflinePay query(TOfflinePay entity) {
		return iOfflinePayDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TOfflinePay query(Serializable id) {
		return iOfflinePayDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TOfflinePay> queryForList(TOfflinePay entity) {
		return iOfflinePayDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TOfflinePay> queryForList(Map<String, ?> args) {
		return iOfflinePayDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOfflinePay> queryListForPagination(
			QueryContext<TOfflinePay> qContext) {
		return iOfflinePayDAO.queryListForPagination(qContext);
	}

	/**  
	 * iOfflinePayDAO  
	 *  
	 * @return  the iOfflinePayDAO  
	 * @since   1.0.0  
	 */
	
	public IOfflinePayDAO getIOfflinePayDAO() {
		return iOfflinePayDAO;
	}

	/**  
	 * @param iOfflinePayDAO the iOfflinePayDAO to set  
	 */
	public void setIOfflinePayDAO(IOfflinePayDAO iOfflinePayDAO) {
		this.iOfflinePayDAO = iOfflinePayDAO;
	}

}
