/**
 *
 */
package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.AuthCmpInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.datas.dao.company.IAuthRecordDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyShippingService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.UserTokenManager;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 认证记录SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午6:00:17
 */
@Service(value="authRecordService")
@Transactional(propagation=Propagation.REQUIRED)
public class AuthRecordServiceImpl implements IAuthRecordService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IAuthRecordDao authRecordDao;
	@Autowired
	private ICompanyAuthService companyAuthService;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private ICompanyShippingService companyShippingService;
	@Autowired
	ICompanyPersonalService companyPersonalService;
	@Autowired
	private MessageSendManager msm;
	@Autowired
	private UserTokenManager userTokenManager;
	@Autowired
	private IUserService userService;

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

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.IAuthRecordService#queryListForPaginationByTypeAndAuthstatus(com.appabc.common.base.QueryContext, com.appabc.bean.enums.AuthRecordInfo.AuthRecordType, com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus)
	 */
	@Override
	public QueryContext<TAuthRecord> queryListForPaginationByTypeAndAuthstatus(
			QueryContext<TAuthRecord> qContext, AuthRecordType authType,
			AuthRecordStatus authStatus) {
		if(qContext==null) qContext = new QueryContext<TAuthRecord>();
		 QueryContext<TAuthRecord> ttt = this.authRecordDao.queryListForPaginationByTypeAndAuthstatus(qContext, authType, authStatus);
		 return ttt;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.IAuthRecordService#queryNewListForCmpAuth()
	 */
	@Override
	public List<TAuthRecord> queryNewListForNotInTask() {
		return this.authRecordDao.queryNewListForNotInTask();
	}

	@Override
	public void authCmp(AuthCmpInfo aci) throws ServiceException {
		if(aci == null){
			throw new ServiceException("AuthAllInfo is null");
		} else if(StringUtils.isEmpty(aci.getAuthid())){
			throw new ServiceException("认证记录ID不能为空,authid="+aci.getAuthid());
		}

		/** 查询认证记录，并验证记录的状态 **/
		TAuthRecord ca = this.authRecordDao.query(aci.getAuthid());
		if(ca ==  null){
			throw new ServiceException("认证记录不存在,authid="+aci.getAuthid());
		}else if(ca.getAuthstatus() != AuthRecordStatus.AUTH_STATUS_CHECK_ING){
			throw new ServiceException("非法认证处理,该记录未在认证中,authid="+aci.getAuthid()+",TABLE AUTHSTATUS="+ca.getAuthstatus());
		}else if(StringUtils.isEmpty(ca.getCid())){
			throw new ServiceException("数据库中的数据不完整,该记录未关联企业ID,authid="+aci.getAuthid());
		}

		/** 企业基本信息查询 **/
		TCompanyInfo ci = this.companyInfoService.query(ca.getCid());
		if(ci == null){
			throw new ServiceException("数据库中的数据不完整,认证记录关联的企业ID不存在，authid="+aci.getAuthid()+",cid="+ca.getCid());
		}
		ci.setUpdatedate(Calendar.getInstance().getTime());
		ci.setUpdater(aci.getAuthor());

		SystemMessageContent msgContent = null; // 推送消息内容

		/** 认证处理 **/
		if(aci.getAuthStatus() == AuthRecordStatus.AUTH_STATUS_CHECK_YES){ // 通过
			ca.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);

			// 认证图片中的信息保存
			if(ci.getCtype() == CompanyType.COMPANY_TYPE_ENTERPRISE && aci.getCompanyAuth() != null){
				aci.getCompanyAuth().setAuthid(Integer.parseInt(aci.getAuthid()));
				this.companyAuthService.add(aci.getCompanyAuth()); // 企业认证信息保存
				ci.setCname(aci.getCompanyAuth().getCname());
			}else if(ci.getCtype() == CompanyType.COMPANY_TYPE_SHIP && aci.getShippingAuth() != null){
				aci.getShippingAuth().setAuthid(Integer.parseInt(aci.getAuthid()));
				this.companyShippingService.add(aci.getShippingAuth()); // 船舶认证信息保存
				ci.setCname(aci.getShippingAuth().getSname());
			}else if(ci.getCtype() == CompanyType.COMPANY_TYPE_PERSONAL && aci.getPersonalAuth() != null){
				aci.getPersonalAuth().setAuthid(Integer.parseInt(aci.getAuthid()));
				this.companyPersonalService.add(aci.getPersonalAuth());// 个人认证信息保存
				ci.setCname(aci.getPersonalAuth().getCpname());
			}else{
				logger.error("认证类型不正确,authStatus="+aci.getAuthStatus());
				throw new ServiceException("认证信息不完整,公司类型="+ci.getCtype()+ci.getCtype().getText()+",aci.getCompanyAuth()="+aci.getCompanyAuth()+",aci.getShippingAuth()="+aci.getShippingAuth()+",aci.getPersonalAuth()="+aci.getPersonalAuth());
			}

			ci.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);
			msgContent = SystemMessageContent.getMsgContentOfCompanyAuthYes(); // 推送消息获取
			logger.info(ci.getCname()+"认证通过,认证类型为"+ci.getCtype().getText());

		}else if(aci.getAuthStatus() == AuthRecordStatus.AUTH_STATUS_CHECK_NO){ // 不通过
			ca.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_NO);

			ci.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_NO);
			msgContent = SystemMessageContent.getMsgContentOfCompanyAuthNo(); // 推送消息获取
			logger.info(ci.getCname()+"认证不通过");
		}else{ // 其它错误状态
			logger.error("认证状态不正确,authStatus="+aci.getAuthStatus());
			throw new ServiceException("认证状态不正确,authStatus="+aci.getAuthStatus());
		}

		/** 更新认证记录 **/
		ca.setAuthresult(aci.getAuthresult());
		ca.setAuthor(aci.getAuthor());
		ca.setAuthdate(Calendar.getInstance().getTime());
		ca.setRemark(aci.getRemark());
		
		this.authRecordDao.update(ca);

		/** 更新企业认证信息 **/
		this.companyInfoService.modify(ci);

		/** 更新redis中的用户信息 **/
		TUser user = this.userService.getUserByCid(ci.getId());
		UserInfoBean ut = userTokenManager.getBeanByUsername(user.getUsername());
		if(ut != null){
			ut.setCid(ci.getId());
			ut.setCname(ci.getCname());
			ut.setCtype(ci.getCtype().getVal());
			userTokenManager.updateUserInfo(ut);
		}

		/** 消息推送 **/
		MessageInfoBean mi = new  MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH, aci.getAuthid(), ci.getId(), msgContent);
		mi.addParam("authStatus", ci.getAuthstatus());
		mi.addParam("ctype", ci.getCtype());
		mi.setSendPushMsg(true);
		mi.setSendSystemMsg(true);
		msm.msgSend(mi);

	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.IAuthRecordService#queryParentAuthRecordOfInsteadListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TAuthRecord> queryParentAuthRecordOfInsteadListForPagination(
			QueryContext<TAuthRecord> qContext) {
		return this.authRecordDao.queryParentAuthRecordOfInsteadListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.IAuthRecordService#queryAuthLogListByCid(java.lang.String)
	 */
	@Override
	public List<TAuthRecord> queryAuthLogListByCid(String cid) {
		if(StringUtils.isEmpty(cid)) return null;
		return this.authRecordDao.queryAuthLogListByCid(cid);
	}


}
