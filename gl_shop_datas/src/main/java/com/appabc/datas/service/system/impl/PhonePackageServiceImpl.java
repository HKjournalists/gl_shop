package com.appabc.datas.service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TPhonePackage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.system.IPhonePackageDAO;
import com.appabc.datas.service.system.IPhonePackageService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月20日 上午10:59:41
 */

@Service(value="IPhonePackageService")
public class PhonePackageServiceImpl extends BaseService<TPhonePackage> implements IPhonePackageService {

	@Autowired
	private IPhonePackageDAO iPhonePackageDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TPhonePackage entity) {
		iPhonePackageDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TPhonePackage entity) {
		iPhonePackageDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPhonePackage entity) {
		iPhonePackageDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iPhonePackageDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TPhonePackage query(TPhonePackage entity) {
		return iPhonePackageDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TPhonePackage query(Serializable id) {
		return iPhonePackageDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPhonePackage> queryForList(TPhonePackage entity) {
		return iPhonePackageDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPhonePackage> queryForList(Map<String, ?> args) {
		return iPhonePackageDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPhonePackage> queryListForPagination(QueryContext<TPhonePackage> qContext) {
		return iPhonePackageDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.system.IPhonePackageService#queryPhonePackageListByPid(java.lang.String)  
	 */
	@Override
	public List<TPhonePackage> queryPhonePackageListByPid(String pid) {
		return iPhonePackageDAO.queryPhonePackageListByPid(pid);
	}

}
