package com.appabc.tools.service.user.impl;

import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.tools.dao.user.IToolUserDao;
import com.appabc.tools.service.user.IToolUserService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月25日 下午12:01:46
 */

@Service(value="IToolUserService")
@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
@Aspect
public class ToolUserServiceImpl extends BaseService<TUser> implements IToolUserService {

	@Autowired
	private IToolUserDao userDao;
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TUser entity) {
		userDao.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TUser entity) {
		userDao.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TUser entity) {
		userDao.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TUser query(TUser entity) {
		return userDao.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TUser> queryForList(TUser entity) {
		return userDao.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TUser> queryForList(Map<String, ?> args) {
		return userDao.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TUser> queryListForPagination(QueryContext<TUser> qContext) {
		return userDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.lang.String)  
	 */
	public void delete(Serializable id) {
		userDao.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)  
	 */
	public TUser query(Serializable id) {
		return userDao.query(id);
	}

	@Override
	public TUser getUserByCid(String cid) {
		if(StringUtils.isNotEmpty(cid)){
			TUser entity = new TUser();
			entity.setCid(cid);
			
			return this.userDao.query(entity);
		}
		return null;
	}

}
