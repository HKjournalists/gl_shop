/**
 *
 */
package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyContactDao;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.enums.CompanyInfo;
import com.appabc.datas.service.company.ICompanyContactService;

/**
 * @Description : 企业联系人service实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午7:46:37
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyContactServiceImpl implements ICompanyContactService {
	
	@Autowired
	private ICompanyContactDao companyContactDao;
	@Autowired
	private ICompanyInfoDao companyInfoDao;

	public void add(TCompanyContact entity) {
		companyContactDao.save(entity);
	}

	public void modify(TCompanyContact entity) {
		if(StringUtils.isNotEmpty(entity.getId())){
			TCompanyContact cc = this.companyContactDao.query(entity.getId());
			if(cc != null){
				cc.setCname(entity.getCname());
				cc.setCphone(entity.getCphone());
				cc.setTel(entity.getTel());
				companyContactDao.update(cc);
				
				// 将默认联系人更新企业信息表中
				if(cc.getStatus() == CompanyInfo.ContactStatus.CONTACT_STATUS_DEFULT.getVal()){
					TCompanyInfo ci = this.companyInfoDao.query(cc.getCid());
					ci.setContact(entity.getCname());
					ci.setCphone(entity.getCphone());
					ci.setTel(entity.getTel());
					this.companyInfoDao.update(ci);
				}
				
			}
			
		}
		
	}

	public void delete(TCompanyContact entity) {
	}

	public void delete(Serializable id) {
		companyContactDao.delete(id);
	}

	public TCompanyContact query(TCompanyContact entity) {
		return companyContactDao.query(entity);
	}

	public TCompanyContact query(Serializable id) {
		return companyContactDao.query(id);
	}

	public List<TCompanyContact> queryForList(TCompanyContact entity) {
		return companyContactDao.queryForList(entity);
	}

	public List<TCompanyContact> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyContact> queryListForPagination(
			QueryContext<TCompanyContact> qContext) {
		return null;
	}

}
