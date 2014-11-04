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

import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyAuthDao;
import com.appabc.datas.service.company.ICompanyAuthService;

/**
 * @Description : 企业认证信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午9:27:42
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyAuthServiceImpl implements ICompanyAuthService {
	
	@Autowired
	private ICompanyAuthDao companyAuthDao;

	public void add(TCompanyAuth entity) {
		companyAuthDao.save(entity);
	}

	public void modify(TCompanyAuth entity) {
		companyAuthDao.update(entity);
	}

	public void delete(TCompanyAuth entity) {
	}

	public void delete(Serializable id) {
		companyAuthDao.delete(id);
	}

	public TCompanyAuth query(TCompanyAuth entity) {
		return companyAuthDao.query(entity);
	}

	public TCompanyAuth query(Serializable id) {
		return companyAuthDao.query(id);
	}

	public List<TCompanyAuth> queryForList(TCompanyAuth entity) {
		return companyAuthDao.queryForList(entity);
	}

	public List<TCompanyAuth> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyAuth> queryListForPagination(
			QueryContext<TCompanyAuth> qContext) {
		return null;
	}

}
