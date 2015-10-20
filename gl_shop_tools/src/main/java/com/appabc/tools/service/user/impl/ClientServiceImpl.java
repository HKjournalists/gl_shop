/**
 *
 */
package com.appabc.tools.service.user.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TClient;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.tools.dao.user.IClientDao;
import com.appabc.tools.service.user.IClientService;

/**
 * @Description : 客户端SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月11日 下午6:08:52
 */
@Service
public class ClientServiceImpl extends BaseService<TClient> implements IClientService {
	
	@Autowired
	private IClientDao clientDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void add(TClient entity) {
		clientDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void modify(TClient entity) {
		clientDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TClient entity) {
		clientDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		clientDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TClient query(TClient entity) {
		return clientDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TClient query(Serializable id) {
		return clientDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TClient> queryForList(TClient entity) {
		return clientDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TClient> queryForList(Map<String, ?> args) {
		return clientDao.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TClient> queryListForPagination(
			QueryContext<TClient> qContext) {
		return clientDao.queryListForPagination(qContext);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int getNextBadge(String clientid, ClientTypeEnum clienttype, String username) {
		if(StringUtils.isEmpty(clientid)){
			return 0;
		}
		
		TClient entity = new TClient();
		entity.setClientid(clientid);
		entity = this.clientDao.query(entity);
		
		// 首次新增
		if(entity == null) {
			entity = new TClient();
			entity.setClientid(clientid);
			entity.setBadge(1);
			entity.setClienttype(clienttype);
			entity.setUsername(username);
			entity.setUpdatetime(Calendar.getInstance().getTime());
			
			this.clientDao.save(entity);
			
			return 1;
		}
		
		if(StringUtils.isNotEmpty(username) && username.equals(entity.getUsername())){
			int badge = entity.getBadge() + 1;
			entity.setBadge(badge);
			entity.setUpdatetime(Calendar.getInstance().getTime());
			this.clientDao.update(entity);
			
			return badge;
		}else{
			entity.setBadge(1);
			entity.setUsername(username);
			entity.setUpdatetime(Calendar.getInstance().getTime());
			this.clientDao.update(entity);
			return 1;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public TClient getNextBadgeBean(String clientid, ClientTypeEnum clienttype, String username) {
		if(StringUtils.isEmpty(clientid)){
			return null;
		}
		
		TClient entity = new TClient();
		entity.setClientid(clientid);
		entity = this.clientDao.query(entity);
		
		// 首次新增
		if(entity == null) {
			entity = new TClient();
			entity.setClientid(clientid);
			entity.setBadge(1);
			entity.setClienttype(clienttype);
			entity.setUsername(username);
			entity.setUpdatetime(Calendar.getInstance().getTime());
			
			this.clientDao.save(entity);
			
			return entity;
		}
		
		if(StringUtils.isNotEmpty(username) && username.equals(entity.getUsername())){
			int badge = entity.getBadge() + 1;
			entity.setBadge(badge);
			entity.setUpdatetime(Calendar.getInstance().getTime());
			this.clientDao.update(entity);
			
			return entity;
		}else{
			entity.setBadge(1);
			entity.setUsername(username);
			entity.setUpdatetime(Calendar.getInstance().getTime());
			this.clientDao.update(entity);
			return entity;
		}
		
	}
	
	@Override
	public void rmBadge(String clientid){
		TClient entity = new TClient();
		entity.setClientid(clientid);
		entity = this.clientDao.query(entity);
		
		if(entity != null){
			entity.setBadge(0);
			this.clientDao.update(entity);
		}
	}

}
