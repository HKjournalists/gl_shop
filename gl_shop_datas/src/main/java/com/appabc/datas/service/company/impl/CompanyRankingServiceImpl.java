package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TCompanyRanking;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyRankingDao;
import com.appabc.datas.service.company.ICompanyRankingService;

/**
 * @Description : 企业信息统计service实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * @Create_Date : 2014年10月10日 下午6:03:15
 */
@Service
public class CompanyRankingServiceImpl implements ICompanyRankingService {
	
	@Autowired
	private ICompanyRankingDao companyRankingDao;

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TCompanyRanking entity) {
		companyRankingDao.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TCompanyRanking entity) {
		companyRankingDao.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TCompanyRanking entity) {
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		companyRankingDao.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TCompanyRanking query(TCompanyRanking entity) {
		return companyRankingDao.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TCompanyRanking query(Serializable id) {
		return companyRankingDao.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TCompanyRanking> queryForList(TCompanyRanking entity) {
		return companyRankingDao.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TCompanyRanking> queryForList(Map<String, ?> args) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TCompanyRanking> queryListForPagination(
			QueryContext<TCompanyRanking> qContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
