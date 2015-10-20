package com.appabc.datas.service.company.impl;

import com.appabc.bean.enums.AcceptBankInfo.AcceptBankStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.MsgInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.company.IAcceptBankDAO;
import com.appabc.datas.dao.company.IAuthRecordDao;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 下午9:21:13
 */
@Service
public class AcceptBankServiceImpl extends BaseService<TAcceptBank> implements
		IAcceptBankService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IAcceptBankDAO acceptBankDAO;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private IAuthRecordDao authRecordDao;
	@Autowired
	private MessageSendManager messageSender;

	public AcceptBankServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void add(TAcceptBank entity) {
		acceptBankDAO.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void modify(TAcceptBank entity) {
		acceptBankDAO.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TAcceptBank entity) {
		acceptBankDAO.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		try {
			acceptBankDAO.delete(id);
		} catch (Exception e) {
			logger.info("企业提款人删除失败, error message="+e.getMessage());
			TAcceptBank ab = this.acceptBankDAO.query(id);
			if(ab != null){
				logger.info("系统将进行软删除处理, update status="+AcceptBankStatus.ACCEPT_BANK_STATUS_DEL);
				ab.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_DEL);
				this.acceptBankDAO.update(ab);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TAcceptBank query(TAcceptBank entity) {
		return acceptBankDAO.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TAcceptBank query(Serializable id) {
		TAcceptBank ab = acceptBankDAO.query(id);

		// 	图片获取
		ab.setvImgList(this.uploadImagesService.getViewImgsByOidAndOtype(id.toString(), FileInfo.FileOType.FILE_OTYPE_BANK.getVal()));
		return ab;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TAcceptBank> queryForList(TAcceptBank entity) {
		return acceptBankDAO.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TAcceptBank> queryForList(Map<String, ?> args) {
		return acceptBankDAO.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TAcceptBank> queryListForPagination(
			QueryContext<TAcceptBank> qContext) {
		return acceptBankDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)提款人认证申请
	 * @see com.appabc.pay.service.local.IAcceptBankService#authApply(com.appabc.pay.bean.TAcceptBank)
	 */
	@Override
	public void authApply(TAcceptBank abBean) {

		// 添加提款人信息
		Date now = new Date();
		abBean.setCreatetime(now);
		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER);
		abBean.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
		this.acceptBankDAO.save(abBean);

		// 认证记录添加
		TAuthRecord ar = new TAuthRecord();
		ar.setType(AuthRecordType.AUTH_RECORD_TYPE_BANK);
		ar.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
		ar.setCreatedate(now);
		ar.setAbid(abBean.getId());
		authRecordDao.save(ar);

		// 更新图片关联，个人用户提款人没有图片ID
		if(StringUtils.isNotEmpty(abBean.getImgid())){
			this.uploadImagesService.updateOtypeAndOid(abBean.getId(), FileInfo.FileOType.FILE_OTYPE_BANK, abBean.getImgid());
		}

	}

	/* (non-Javadoc)提款人认证申请
	 * @see com.appabc.pay.service.local.IAcceptBankService#reAuthApply(com.appabc.pay.bean.TAcceptBank)
	 */
	@Override
	public void reAuthApply(TAcceptBank abBean) {
		TAcceptBank entity  = this.acceptBankDAO.query(abBean.getId());

		abBean.setUpdatetime(Calendar.getInstance().getTime());
		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER);
		abBean.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
		abBean.setCid(entity.getCid());
		this.acceptBankDAO.update(abBean);

		// 认证记录添加
		if(!AuthRecordStatus.AUTH_STATUS_CHECK_ING.equals(entity.getAuthstatus())){
			TAuthRecord ar = new TAuthRecord();
			ar.setType(AuthRecordType.AUTH_RECORD_TYPE_BANK);
			ar.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
			ar.setCreatedate(new Date());
			ar.setAbid(abBean.getId());// 提款人ID
			authRecordDao.save(ar);
		}

		// 更新图片关联
		if(StringUtils.isNotEmpty(abBean.getImgid())){
			this.uploadImagesService.updateOtypeAndOid(abBean.getId(), FileInfo.FileOType.FILE_OTYPE_BANK, abBean.getImgid());
		}

	}

	/* (non-Javadoc)设置默认提款人
     * @see com.appabc.datas.service.company.IAcceptBankService#setDefault(java.lang.String)
     */
	@Override
	public void setDefault(String id) {
		TAcceptBank abBean = this.acceptBankDAO.query(id);

		TAcceptBank entity = new TAcceptBank();
		entity.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_DEFAULT);
		entity.setCid(abBean.getCid());
		List<TAcceptBank> abList = this.acceptBankDAO.queryForList(entity);

		for(TAcceptBank ab : abList){
			ab.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER);
			this.acceptBankDAO.update(ab);
		}

		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_DEFAULT);
		this.acceptBankDAO.update(abBean);
	}

	@Override
	public void authPass(TAcceptBank abBean, String auditor) {
		authAction(abBean, auditor, AuthRecordStatus.AUTH_STATUS_CHECK_YES,
				AuthRecordStatus.AUTH_STATUS_CHECK_YES, MsgInfo.MsgContent.MSG_CONTENT_MONEY_003);
	}

	@Override
	public void authFail(TAcceptBank abBean, String auditor) {
		authAction(abBean, auditor, AuthRecordStatus.AUTH_STATUS_CHECK_NO,
				AuthRecordStatus.AUTH_STATUS_CHECK_NO, MsgInfo.MsgContent.MSG_CONTENT_MONEY_004);
	}

	private void authAction(TAcceptBank abBean, String auditor, AuthRecordStatus accountStatus,
							AuthRecordStatus authStatus, MsgInfo.MsgContent msg) {
		TAuthRecord ar = new TAuthRecord();
		ar.setAbid(abBean.getId());
		ar = authRecordDao.query(ar);

		abBean.setAuthstatus(accountStatus);
		abBean.setUpdatetime(new Date());
		ar.setAuthstatus(authStatus);
		ar.setAuthor(auditor);

		acceptBankDAO.update(abBean);
		authRecordDao.update(ar);

		SystemMessageContent mc = new SystemMessageContent(msg.getVal());
		MessageInfoBean mi = new MessageInfoBean(MsgInfo.MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH,
				abBean.getId(), abBean.getCid(), mc);
		mi.setSendPushMsg(true);
		mi.setSendShotMsg(true);
		messageSender.msgSend(mi);
	}

	@Override
	public QueryContext<TAcceptBank> queryListForAuthFinished(QueryContext<TAcceptBank> queryContext) {
		return acceptBankDAO.queryListForAuditFinished(queryContext);
	}
}
