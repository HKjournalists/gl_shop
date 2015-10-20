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
import com.appabc.datas.dao.urge.IUrgeVerifyDao;
import com.appabc.datas.service.urge.IUrgeVerifyService;

/**
 * @Description : 催促认证service实现类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月27日 下午3:03:31
 */

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UrgeVerifyServiceImpl implements IUrgeVerifyService {

	@Autowired
	private IUrgeVerifyDao urgeVerifyDao;
	
	@Override
	public void add(TUrgeVerify entity) {
		urgeVerifyDao.save(entity);
	}

	@Override
	public void modify(TUrgeVerify entity) {

	}

	@Override
	public void delete(TUrgeVerify entity) {
		
	}

	@Override
	public void delete(Serializable id) {
		
	}

	@Override
	public TUrgeVerify query(TUrgeVerify entity) {
		return null;
	}

	@Override
	public TUrgeVerify query(Serializable id) {
		return null;
	}

	@Override
	public List<TUrgeVerify> queryForList(TUrgeVerify entity) {
		return null;
	}

	@Override
	public List<TUrgeVerify> queryForList(Map<String, ?> args) {
		return null;
	}

	@Override
	public QueryContext<TUrgeVerify> queryListForPagination(
			QueryContext<TUrgeVerify> qContext) {
		return urgeVerifyDao.queryListForPagination(qContext);
	}

	public UrgeVerifyInfo queryVerifyInfoByTaskId(String taskid)
	{
		return urgeVerifyDao.queryVerifyInfoByTaskId(taskid);
	}
	
	public QueryContext<UrgeVerifyInfo> getVerifyList(QueryContext<UrgeVerifyInfo> qContext)
	{
		return urgeVerifyDao.getVerifyList(qContext);
	}

	@Override
	public QueryContext<UrgeVerifyInfo> getVerifyNoList(
			QueryContext<UrgeVerifyInfo> qContext) {
		return urgeVerifyDao.getVerifyNoList(qContext);
	}
	public List<TUrgeVerify> queryRecordByCompanyId(String  companyId)
	{
		return urgeVerifyDao.queryRecordByCompanyId(companyId);
	}
}
