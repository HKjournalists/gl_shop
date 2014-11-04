/**  
 * com.appabc.pay.service.local.impl.PassbookThirdCheckServiceImpl.java  
 *   
 * 2014年9月18日 下午2:16:08  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPassbookThirdCheck;
import com.appabc.pay.dao.IPassbookThirdCheckDAO;
import com.appabc.pay.service.local.IPassbookThirdCheckService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月18日 下午2:16:08
 */

public class PassbookThirdCheckServiceImpl extends BaseService<TPassbookThirdCheck> implements
		IPassbookThirdCheckService {

	private IPassbookThirdCheckDAO iPassbookThirdCheckDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TPassbookThirdCheck entity) {
		iPassbookThirdCheckDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TPassbookThirdCheck entity) {
		iPassbookThirdCheckDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPassbookThirdCheck entity) {
		iPassbookThirdCheckDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iPassbookThirdCheckDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TPassbookThirdCheck query(TPassbookThirdCheck entity) {
		return iPassbookThirdCheckDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TPassbookThirdCheck query(Serializable id) {
		return iPassbookThirdCheckDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPassbookThirdCheck> queryForList(TPassbookThirdCheck entity) {
		return iPassbookThirdCheckDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPassbookThirdCheck> queryForList(Map<String, ?> args) {
		return iPassbookThirdCheckDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookThirdCheck> queryListForPagination(
			QueryContext<TPassbookThirdCheck> qContext) {
		return iPassbookThirdCheckDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPassbookThirdCheckDAO  
	 *  
	 * @return  the iPassbookThirdCheckDAO  
	 * @since   1.0.0  
	 */
	
	public IPassbookThirdCheckDAO getIPassbookThirdCheckDAO() {
		return iPassbookThirdCheckDAO;
	}

	/**  
	 * @param iPassbookThirdCheckDAO the iPassbookThirdCheckDAO to set  
	 */
	public void setIPassbookThirdCheckDAO(
			IPassbookThirdCheckDAO iPassbookThirdCheckDAO) {
		this.iPassbookThirdCheckDAO = iPassbookThirdCheckDAO;
	}

}
