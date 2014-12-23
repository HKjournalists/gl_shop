/**
 *
 */
package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.IAuthRecordDao;
import com.appabc.datas.service.company.IAuthRecordService;

/**
 * @Description : 认证记录SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午6:00:17
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AuthRecordServiceImpl implements IAuthRecordService {
	
	@Autowired
	private IAuthRecordDao authRecordDao;

	public void add(TAuthRecord entity) {
		authRecordDao.save(entity);
	}

	public void modify(TAuthRecord entity) {
		authRecordDao.update(entity);
	}

	public void delete(TAuthRecord entity) {
	}

	public void delete(Serializable id) {
		authRecordDao.delete(id);
	}

	public TAuthRecord query(TAuthRecord entity) {
		return this.authRecordDao.query(entity);
	}

	public TAuthRecord query(Serializable id) {
		return authRecordDao.query(id);
	}

	public List<TAuthRecord> queryForList(TAuthRecord entity) {
		return authRecordDao.queryForList(entity);
	}

	public List<TAuthRecord> queryForList(Map<String, ?> args) {
		return this.authRecordDao.queryForList(args);
	}

	public QueryContext<TAuthRecord> queryListForPagination(
			QueryContext<TAuthRecord> qContext) {
		return this.authRecordDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.IAuthRecordService#getCountByCidAndAuthstauts(java.lang.String, com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus)
	 */
	@Override
	public int getCountByCidAndAuthstauts(String cid, AuthRecordStatus austatus) {
		return this.authRecordDao.getCountByCidAndAuthstauts(cid, austatus);
	}

}
