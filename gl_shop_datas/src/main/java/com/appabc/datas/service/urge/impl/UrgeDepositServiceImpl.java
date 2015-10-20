package com.appabc.datas.service.urge.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.urge.IUrgeDepositDao;
import com.appabc.datas.service.urge.IUrgeDepositService;

/**
 * @Description : 催促保证金service类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月14日 上午10:15:21
 */

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UrgeDepositServiceImpl implements IUrgeDepositService {

	@Autowired
	private IUrgeDepositDao urgeDepositDao;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		urgeDepositDao.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TUrgeVerify query(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TUrgeVerify query(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TUrgeVerify> queryForList(TUrgeVerify entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TUrgeVerify> queryForList(Map<String, ?> args) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TUrgeVerify> queryListForPagination(
			QueryContext<TUrgeVerify> qContext) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.urge.IUrgeDepositService#getDepositList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<UrgeVerifyInfo> getDepositList(
			QueryContext<UrgeVerifyInfo> qContext) {
		// TODO Auto-generated method stub
		return urgeDepositDao.getDepositList(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.urge.IUrgeDepositService#getNoDepositList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<UrgeVerifyInfo> getNoDepositList(
			QueryContext<UrgeVerifyInfo> qContext) {
		// TODO Auto-generated method stub
		return urgeDepositDao.getNoDepositList(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.urge.IUrgeDepositService#queryDepositInfoByTaskId(java.lang.String)  
	 */
	@Override
	public UrgeVerifyInfo queryDepositInfoByTaskId(String taskid) {
		// TODO Auto-generated method stub
		return urgeDepositDao.queryDepositInfoByTaskId(taskid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.urge.IUrgeDepositService#queryRecordByTypeAndId(java.lang.String, java.lang.String)  
	 */
	@Override
	public List<TUrgeVerify> queryRecordByTypeAndId(String utype, String id) {
		// TODO Auto-generated method stub
		return urgeDepositDao.queryRecordByTypeAndId(utype, id);
	}
}
