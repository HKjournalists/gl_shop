/**
 *
 */
package com.appabc.datas.service.company.impl;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyShippingDao;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	@Autowired
	IAuthRecordService authRecordService;

	public void add(TCompanyShipping entity) {
		companyShippingDao.save(entity);
	}

	public void modify(TCompanyShipping entity) {
		companyShippingDao.update(entity);
	}

	public void delete(TCompanyShipping entity) {
		this.companyShippingDao.delete(entity);
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
		return this.companyShippingDao.queryForList(args);
	}

	public QueryContext<TCompanyShipping> queryListForPagination(
			QueryContext<TCompanyShipping> qContext) {
		return this.companyShippingDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)根据公司ID查询船舶信息
	 * @see com.appabc.datas.service.company.ICompanyShippingService#queryByCid(java.lang.String)
	 */
	public TCompanyShipping queryByCid(String cid) {

		TAuthRecord ar = new TAuthRecord();
		ar.setCid(cid);
		ar.setType(AuthRecordType.AUTH_RECORD_TYPE_COMPANY);
		ar.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);

		List<TAuthRecord> arList =  this.authRecordService.queryForList(ar);
		if(arList != null && arList.size() > 0 && arList.get(0) != null){

			TCompanyShipping cs = new TCompanyShipping();
			cs.setAuthid(Integer.parseInt(arList.get(0).getId()));
			List<TCompanyShipping> csList = this.companyShippingDao.queryForList(cs);
			if(csList != null && csList.size() > 0){
				return csList.get(0);
			}
		}

		return null;
	}



}
