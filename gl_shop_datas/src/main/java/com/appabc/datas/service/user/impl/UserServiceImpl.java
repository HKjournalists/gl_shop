package com.appabc.datas.service.user.impl;

import com.appabc.bean.enums.MsgInfo;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.enums.UserInfo.UserStatus;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.dao.user.IUserDao;
import com.appabc.datas.service.user.IUserService;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
	@Autowired
	private MessageSendManager messageSendManager;

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

		user.setStatus(UserStatus.USER_STATUS_NORMAL);
		user.setCreatedate(new Date());
		user.setCid(ci.getId());
		this.userDao.save(user); // 创建登录用户

		// 企业钱包初始化
		passPayService.initializePurseAccount(ci.getId(), null);

		MessageInfoBean mi = new MessageInfoBean(MsgInfo.MsgBusinessType.BUSINESS_TYPE_USER_REGISTER, user.getId(), ci.getId(), SystemMessageContent.getMsgContentOfUserRegister());
		mi.setSendSystemMsg(true);
		messageSendManager.msgSend(mi);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.user.IUserService#queryUserByCid(java.lang.String)
	 */
	public TUser getUserByCid(String cid) {
		TUser entity = new TUser();
		entity.setCid(cid);

		List<TUser> userList = this.userDao.queryForList(entity);
		if(userList != null && userList.size()>0){
			return userList.get(0);
		}

		return null;
	}
	/* (non-Javadoc)帐号与客户端绑定
	 * @see com.appabc.datas.service.user.IUserService#clientBinding(java.lang.String, java.lang.String)
	 */
	@Override
	public void clientBinding(String userid, String clientid, String clienttype) {
		TUser entity = new TUser();
		entity.setClientid(clientid);
		List<TUser> userList = this.userDao.queryForList(entity);
		boolean isBinding = true;

		if(userList != null && userList.size()>0) { // 解除其它设备的绑定
			for(TUser u : userList){
				if(userid.equals(u.getId())){
					isBinding = false;
					continue;
				}
				u.setClientid(null);
				this.userDao.update(u);
			}
		}

		if(isBinding == true) { // 进行绑定
			TUser user = this.userDao.query(userid);
			user.setClientid(clientid);
			user.setClienttype(ClientTypeEnum.enumOf(clienttype));
			this.userDao.update(user);
		}

	}
}
