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

import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyShippingDao;
import com.appabc.datas.service.company.ICompanyShippingService;

/**
 * @Description : 船舶认证信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午8:40:43
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyShippingServiceImpl implements ICompanyShippingService {
	
	@Autowired
	private ICompanyShippingDao companyShippingDao;

	public void add(TCompanyShipping entity) {
		companyShippingDao.save(entity);
	}

	public void modify(TCompanyShipping entity) {
		companyShippingDao.update(entity);
	}

	public void delete(TCompanyShipping entity) {
	}

	public void delete(Serializable id) {
		companyShippingDao.delete(id);
	}

	public TCompanyShipping query(TCompanyShipping entity) {
		return companyShippingDao.query(entity);
	}

	public TCompanyShipping query(Serializable id) {
		return companyShippingDao.query(id);
	}

	public List<TCompanyShipping> queryForList(TCompanyShipping entity) {
		return companyShippingDao.queryForList(entity);
	}

	public List<TCompanyShipping> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyShipping> queryListForPagination(
			QueryContext<TCompanyShipping> qContext) {
		return null;
	}
	
	

}
