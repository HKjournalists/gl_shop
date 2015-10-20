package com.appabc.datas.service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TActivityJoin;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.system.ISystemActivityDAO;
import com.appabc.datas.service.system.ISystemActivityService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月22日 下午5:45:34
 */

@Service(value="ISystemActivityService")
public class SystemActivityServiceImpl extends BaseService<TActivityJoin> implements ISystemActivityService {

	@Autowired
	private ISystemActivityDAO iSystemActivityDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TActivityJoin entity) {
		iSystemActivityDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TActivityJoin entity) {
		iSystemActivityDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TActivityJoin entity) {
		iSystemActivityDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iSystemActivityDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TActivityJoin query(TActivityJoin entity) {
		return iSystemActivityDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TActivityJoin query(Serializable id) {
		return iSystemActivityDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TActivityJoin> queryForList(TActivityJoin entity) {
		return iSystemActivityDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TActivityJoin> queryForList(Map<String, ?> args) {
		return iSystemActivityDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TActivityJoin> queryListForPagination(QueryContext<TActivityJoin> qContext) {
		return iSystemActivityDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.system.ISystemActivityService#checkPhoneNumIsJoined(java.lang.String)  
	 */
	@Override
	public boolean checkPhoneNumIsJoined(String phone) {
		if(StringUtils.isEmpty(phone)){
			return false;
		}
		List<TActivityJoin> result = iSystemActivityDAO.querySystemActivityByPhoneNum(phone);
		if(CollectionUtils.isEmpty(result)){
			return false;
		}else{
			return true;
		}
	}

}
