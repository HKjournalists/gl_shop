package com.appabc.datas.service.user.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.dao.user.IUserDao;
import com.appabc.datas.enums.UserInfo;
import com.appabc.datas.service.user.IUserService;
import com.appabc.pay.service.IPassPayService;
/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月25日 下午12:01:46
 */

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
@Aspect
public class UserServiceImpl extends BaseService<TUser> implements IUserService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICompanyInfoDao companyInfoDao;
	@Autowired
	private IPassPayService passPayService;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TUser entity) {
		userDao.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TUser entity) {
		userDao.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TUser entity) {
		userDao.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TUser query(TUser entity) {
		return userDao.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TUser> queryForList(TUser entity) {
		return userDao.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TUser> queryForList(Map<String, ?> args) {
		return userDao.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TUser> queryListForPagination(QueryContext<TUser> qContext) {
		return userDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.lang.String)  
	 */
	public void delete(Serializable id) {
		userDao.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)  
	 */
	public TUser query(Serializable id) {
		return userDao.query(id);
	}

	/* (non-Javadoc)根据用户名和密码查询用户
	 * @see com.appabc.datas.service.user.IUserService#queryByNameAndPass(java.lang.String, java.lang.String)
	 */
	public TUser queryByNameAndPass(String username, String password) {
		return userDao.queryByNameAndPass(username, password);
	}

	/* (non-Javadoc)检查是否存在该用户
	 * @see com.appabc.datas.service.user.IUserService#isExistUsername(java.lang.String)
	 */
	public boolean isExistUsername(String username) {
		TUser entity = new TUser();
		entity.setUsername(username);
		
		List<TUser> list = this.userDao.queryForList(entity);
		if(list != null && list.size()>0 && list.get(0)!=null){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)用户注册
	 * @see com.appabc.datas.service.user.IUserService#register(com.appabc.bean.pvo.TUser)
	 */
	public boolean register(TUser user) {
		
		TCompanyInfo ci = new TCompanyInfo();
		this.companyInfoDao.save(ci); // 创建企业记录
		
		user.setStatus(UserInfo.UserStatus.USER_STATUS_NORMAL.getVal());
		user.setCreatedate(new Date());
		user.setCid(ci.getId());
		this.userDao.save(user); // 创建登录用户
		
		// 企业钱包初始化
		passPayService.initializePurseAccount(ci.getId(), null);
		return true;
	}

}
