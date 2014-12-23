/**
 *
 */
package com.appabc.datas.service.system.impl;

import com.appabc.bean.pvo.TBlackList;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.system.IBlackListDao;
import com.appabc.datas.service.system.IBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 黑名单
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 下午5:33:23
 */
@Service
public class BlackListServiceImpl implements IBlackListService {
	
	@Autowired
	private IBlackListDao blackListDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	public void add(TBlackList entity) {
		blackListDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TBlackList entity) {
		blackListDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TBlackList entity) {
		this.blackListDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		blackListDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TBlackList query(TBlackList entity) {
		return blackListDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TBlackList query(Serializable id) {
		return blackListDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TBlackList> queryForList(TBlackList entity) {
		return blackListDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TBlackList> queryForList(Map<String, ?> args) {
		return this.blackListDao.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TBlackList> queryListForPagination(
			QueryContext<TBlackList> qContext) {
		return this.blackListDao.queryListForPagination(qContext);
	}

}
