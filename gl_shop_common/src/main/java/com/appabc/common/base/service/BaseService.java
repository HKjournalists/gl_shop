package com.appabc.common.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.utils.LogUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午5:37:23
 */

public abstract class BaseService<T extends BaseBean> implements IBaseService<T> {
	
	protected LogUtil log = LogUtil.getLogUtil(this.getClass());
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(T entity) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the add the entity function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(T entity) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the modify the entity function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(T entity) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the delete by entity function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.lang.String)  
	 */
	public void delete(Serializable id) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the delete by id function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public T query(T entity) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the query by entity function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)  
	 */
	public T query(Serializable id) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the query by id function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<T> queryForList(T entity) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the queryForList by entity function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<T> queryForList(Map<String, ?> args) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the queryForList by args function exception");
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<T> queryListForPagination(QueryContext<T> qContext) {
		// TODO need the child class to implements
		throw new RuntimeException("unimplement the queryListForPagination by QueryContext function exception");
	}
}
