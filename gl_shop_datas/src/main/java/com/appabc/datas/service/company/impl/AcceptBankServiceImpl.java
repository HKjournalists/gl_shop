package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.company.IAcceptBankDAO;
import com.appabc.datas.dao.company.IAuthRecordDao;
import com.appabc.datas.enums.AcceptBankInfo;
import com.appabc.datas.enums.AcceptBankInfo.AcceptBankStatus;
import com.appabc.datas.enums.AuthRecordInfo;
import com.appabc.datas.enums.FileInfo;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.pay.bean.TAcceptBank;

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

	@Autowired
	private IAcceptBankDAO acceptBankDAO;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private IAuthRecordDao authRecordDao;
	
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
		acceptBankDAO.delete(id);
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
		List<TUploadImages> uiList = uploadImagesService.getListByOidAndOtype(id.toString(), FileInfo.FileOType.FILE_OTYPE_BANK.getVal());
		if(uiList != null && uiList.size()>0 && uiList.get(0) != null){
			ab.setImgurl(uiList.get(0).getFullpath());
			ab.setImgid(uiList.get(0).getId());
		}
		
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

	/**  
	 * iAcceptBankDAO  
	 *  
	 * @return  the iAcceptBankDAO  
	 * @since   1.0.0  
	*/  
	
	public IAcceptBankDAO getIAcceptBankDAO() {
		return acceptBankDAO;
	}

	/* (non-Javadoc)提款人认证申请
	 * @see com.appabc.pay.service.local.IAcceptBankService#authApply(com.appabc.pay.bean.TAcceptBank)
	 */
	@Override
	public void authApply(TAcceptBank abBean) {
		
		// 添加提款人信息
		Date now = new Date();
		abBean.setCreatetime(now);
		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER.getVal());
		abBean.setAuthstatus(AcceptBankInfo.AcceptAuthStatus.AUTH_STATUS_CHECK_ING.getVal());
		this.acceptBankDAO.save(abBean);
		
		// 认证记录添加
		TAuthRecord ar = new TAuthRecord();
		ar.setType(AuthRecordInfo.AuthRecordType.AUTH_RECORD_TYPE_BANK.getVal());
		ar.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_ING.getVal());
		ar.setCreatedate(now);
		ar.setAbid(abBean.getId());
		authRecordDao.save(ar);
		
		// 更新图片关联
		TUploadImages ui = uploadImagesService.query(abBean.getImgid());
		ui.setOid(abBean.getId());
		ui.setOtype(FileInfo.FileOType.FILE_OTYPE_BANK.getVal());
		this.uploadImagesService.modify(ui);
		
		
	}
	
	/* (non-Javadoc)提款人认证申请
	 * @see com.appabc.pay.service.local.IAcceptBankService#reAuthApply(com.appabc.pay.bean.TAcceptBank)
	 */
	@Override
	public void reAuthApply(TAcceptBank abBean) {
		TAcceptBank entity  = this.acceptBankDAO.query(abBean.getId());
		
		abBean.setUpdatetime(Calendar.getInstance().getTime());
		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER.getVal());
		abBean.setAuthstatus(AcceptBankInfo.AcceptAuthStatus.AUTH_STATUS_CHECK_ING.getVal());
		abBean.setCid(entity.getCid());
		this.acceptBankDAO.update(abBean);
		
		
		// 认证记录添加
		TAuthRecord ar = new TAuthRecord();
		ar.setType(AuthRecordInfo.AuthRecordType.AUTH_RECORD_TYPE_BANK.getVal());
		ar.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_ING.getVal());
		ar.setCreatedate(new Date());
		ar.setAbid(abBean.getId());// 提款人ID
		authRecordDao.save(ar);
		
		// 更新图片关联
		TUploadImages ui = uploadImagesService.query(abBean.getImgid());
		ui.setOid(abBean.getId());
		ui.setOtype(FileInfo.FileOType.FILE_OTYPE_BANK.getVal());
		this.uploadImagesService.modify(ui);
		
		
	}

	/* (non-Javadoc)设置默认提款人
	 * @see com.appabc.datas.service.company.IAcceptBankService#setDefault(java.lang.String)
	 */
	@Override
	public void setDefault(String id) {
		TAcceptBank abBean = this.acceptBankDAO.query(id);
		
		TAcceptBank entity = new TAcceptBank();
		entity.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_DEFAULT.getVal());
		entity.setCid(abBean.getCid());
		List<TAcceptBank> abList = this.acceptBankDAO.queryForList(entity);
		
		for(TAcceptBank ab : abList){
			ab.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER.getVal());
			this.acceptBankDAO.update(ab);
		}
		
		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_DEFAULT.getVal());
		this.acceptBankDAO.update(abBean);
	}

}
