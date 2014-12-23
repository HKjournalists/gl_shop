package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.dao.IPayAcceptBankDAO;
import com.appabc.pay.service.local.IPayAcceptBankService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 下午9:21:13
 */

public class PayAcceptBankServiceImpl extends BaseService<TAcceptBank> implements
		IPayAcceptBankService {

	private IPayAcceptBankDAO iAcceptBankDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TAcceptBank entity) {
		iAcceptBankDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TAcceptBank entity) {
		iAcceptBankDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TAcceptBank entity) {
		iAcceptBankDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iAcceptBankDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TAcceptBank query(TAcceptBank entity) {
		return iAcceptBankDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TAcceptBank query(Serializable id) {
		return iAcceptBankDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TAcceptBank> queryForList(TAcceptBank entity) {
		return iAcceptBankDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TAcceptBank> queryForList(Map<String, ?> args) {
		return iAcceptBankDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TAcceptBank> queryListForPagination(
			QueryContext<TAcceptBank> qContext) {
		return iAcceptBankDAO.queryListForPagination(qContext);
	}

	/**  
	 * iAcceptBankDAO  
	 *  
	 * @return  the iAcceptBankDAO  
	 * @since   1.0.0  
	*/  
	
	public IPayAcceptBankDAO getIAcceptBankDAO() {
		return iAcceptBankDAO;
	}

	/**  
	 * @param iAcceptBankDAO the iAcceptBankDAO to set  
	 */
	public void setIAcceptBankDAO(IPayAcceptBankDAO iAcceptBankDAO) {
		this.iAcceptBankDAO = iAcceptBankDAO;
	}

}
