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

import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyPersonalDao;
import com.appabc.datas.service.company.ICompanyPersonalService;

/**
 * @Description : 个人认证信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午9:44:18
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyPersonalServiceImpl implements ICompanyPersonalService{
	
	@Autowired
	private ICompanyPersonalDao companyPersonalDao;

	public void add(TCompanyPersonal entity) {
		companyPersonalDao.save(entity);
	}

	public void modify(TCompanyPersonal entity) {
		companyPersonalDao.update(entity);
	}

	public void delete(TCompanyPersonal entity) {
	}

	public void delete(Serializable id) {
		companyPersonalDao.delete(id);
	}

	public TCompanyPersonal query(TCompanyPersonal entity) {
		return companyPersonalDao.query(entity);
	}

	public TCompanyPersonal query(Serializable id) {
		return companyPersonalDao.query(id);
	}

	public List<TCompanyPersonal> queryForList(TCompanyPersonal entity) {
		return companyPersonalDao.queryForList(entity);
	}

	public List<TCompanyPersonal> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyPersonal> queryListForPagination(
			QueryContext<TCompanyPersonal> qContext) {
		return null;
	}

}
