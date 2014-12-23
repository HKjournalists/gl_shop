/**
 *
 */
package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.company.ICompanyContactDao;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAddressService;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyShippingService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.CompanyUtil;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.datas.tool.ViewInfoEncryptUtil;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.SystemParamsManager;

/**
 * @Description : 公司信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午5:30:44
 */
@Service(value="ICompanyInfoService")
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyInfoServiceImpl implements ICompanyInfoService{

	@Autowired
	private ICompanyInfoDao companyInfoDao;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private ICompanyAddressService companyAddressService;
	@Autowired
	private ICompanyContactDao companyContactDao;
	@Autowired
	private SystemParamsManager spm;
	@Autowired
	private ICompanyEvaluationService companyEvaluationService;
	@Autowired
	private IContractInfoService contractInfoService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPassPayService passPayLocalService;
	@Autowired
	private CompanyUtil companyUtil;
	@Autowired
	private ICompanyShippingService companyShippingService;
	@Autowired
	private MessageSendManager messageSendManager;
	@Autowired
	private IAuthRecordService authRecordService;


	public void add(TCompanyInfo entity) {
		this.companyInfoDao.save(entity);
	}

	public void modify(TCompanyInfo entity) {
		this.companyInfoDao.update(entity);
	}

	public void delete(TCompanyInfo entity) {
		this.companyInfoDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.companyInfoDao.delete(id);
	}

	public TCompanyInfo query(TCompanyInfo entity) {
		return this.companyInfoDao.query(entity);
	}

	public TCompanyInfo query(Serializable id) {
		return this.companyInfoDao.query(id);
	}

	public List<TCompanyInfo> queryForList(TCompanyInfo entity) {
		return this.companyInfoDao.queryForList(entity);
	}

	public List<TCompanyInfo> queryForList(Map<String, ?> args) {
		return this.companyInfoDao.queryForList(args);
	}

	public QueryContext<TCompanyInfo> queryListForPagination(
			QueryContext<TCompanyInfo> qContext) {
		return this.companyInfoDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)获取公司信息
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryAuthCompanyInfo(java.lang.String, java.lang.String)
	 */
	public CompanyAllInfo queryAuthCompanyInfo(String cid, String requestCid) {

		CompanyAllInfo cai = this.companyInfoDao.queryAuthCompanyInfo(cid, AuthRecordStatus.AUTH_STATUS_CHECK_YES);
		if(cai == null){ // 未认证通过的企业，获取历史认证信息（已过期，重新认证后原认证信息被设置为已过期）
			cai = this.companyInfoDao.queryAuthCompanyInfo(cid, AuthRecordStatus.AUTH_STATUS_EXPIRE);
		}
		if(cai != null){
			// 认证图片
			if(StringUtils.isNotEmpty(cai.getAuthimgid())){
				cai.getAuthImgList().add(uploadImagesService.getViewImgsById(cai.getAuthimgid()));
			}

			// 企业照片
			cai.setCompanyImgList(this.uploadImagesService.getViewImgsByOidAndOtype(cid, FileInfo.FileOType.FILE_OTYPE_COMPANY.getVal()));

			// 默认联系人
			TCompanyContact cc = new TCompanyContact();
			cc.setCid(cid);
			cc.setStatus(CompanyInfo.ContactStatus.CONTACT_STATUS_DEFULT.getVal());
			cc = companyContactDao.query(cc);
			if(cc != null){
				cai.setContact(cc.getCname());
				cai.setCphone(cc.getCphone());
				cai.setTel(cc.getTel());
			}

			// 卸货地址信息
			TCompanyAddress caEntity  = new TCompanyAddress();
			caEntity.setCid(cid);
			caEntity.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_DEFULT);
			List<TCompanyAddress> caList = this.companyAddressService.queryForList(caEntity);
			if(caList != null && caList.size()>0 && caList.get(0) != null){
				cai.setAddress(caList.get(0).getAddress());
				cai.setAreacode(caList.get(0).getAreacode());
				cai.setDeep(caList.get(0).getDeep());
				cai.setRealdeep(caList.get(0).getRealdeep());

				// 卸货地址图片URL
				cai.setAddressImgList(this.uploadImagesService.getViewImgsByOidAndOtype(caList.get(0).getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal()));
			}

			// 企业评价信息
			cai.setEvaluationInfo(this.getEvaluationByCid(cid));

			// 企业保证金缴纳状态，时时查询，不以企业表中的为准
			float monay = passPayLocalService.getGuarantyTotal(cid); // 保证金总金额
			cai.setBailstatus(companyUtil.checkCashDeposit(cai.getCtype(), 0, monay));

			if(StringUtils.isNotEmpty(requestCid) && !cid.equals(requestCid)){ // 其它用户查看
				if(!contractInfoService.isOldCustomer(requestCid, cid)){ // 2个企业未发生过交易，进行企业加密处理
					ViewInfoEncryptUtil.encryptCompanyInfo(cai);
				}
			}
		}

		return cai;
	}

	/* (non-Javadoc)  根据企业ID获取该企业应缴纳的保证金额度
	 * @see com.appabc.datas.service.company.ICompanyInfoService#getShouldDepositAmountByCid(java.lang.String)
	 */
	public float getShouldDepositAmountByCid(String cid) {
		TCompanyInfo ci = this.companyInfoDao.query(cid);
		try {
			if(ci != null && ci.getCtype() != null){
				if(ci.getCtype().equals(CompanyInfo.CompanyType.COMPANY_TYPE_ENTERPRISE)){ // 企业
					return spm.getFloat(SystemConstant.BOND_ENTERPRISE);
				}else if(ci.getCtype().equals(CompanyInfo.CompanyType.COMPANY_TYPE_PERSONAL)){ // 个人
					return spm.getFloat(SystemConstant.BOND_PERSONAL);
				}else if(ci.getCtype().equals(CompanyInfo.CompanyType.COMPANY_TYPE_SHIP)){ // 船舶

					TCompanyShipping cs = this.companyShippingService.queryByCid(cid);
					if(cs != null){
						if(cs.getStotal() <= 1000){
							return this.spm.getInt(SystemConstant.BOND_SHIP_0_1000);
						}else if(cs.getStotal() <= 5000){
							return this.spm.getInt(SystemConstant.BOND_SHIP_1001_5000);
						}else if(cs.getStotal() <= 10000){
							return this.spm.getInt(SystemConstant.BOND_SHIP_5001_10000);
						}else { // 大于10000
							return this.spm.getInt(SystemConstant.BOND_SHIP_10001_15000);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyInfoService#updateIntroduction(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateIntroduction(String cid, String mark, String companyImgIds) {

		TCompanyInfo ci = this.companyInfoDao.query(cid);
		if(ci != null){
			ci.setMark(mark);
			this.companyInfoDao.update(ci);

			/******企业图片处理*****************/
			String[] imgIds = null;
			if(StringUtils.isNotEmpty(companyImgIds)){
				imgIds = companyImgIds.split(",");
			}
			this.uploadImagesService.updateOtypeAndOidBatch(cid, FileOType.FILE_OTYPE_COMPANY, imgIds);
		}

	}

	/* (non-Javadoc)认证申请
	 * @see com.appabc.datas.service.company.IAuthRecordService#authApply(com.appabc.bean.pvo.TCompanyInfo, com.appabc.bean.pvo.TAuthRecord, com.appabc.bean.pvo.TCompanyAddress, java.lang.String)
	 */
	public void authApply(TCompanyInfo ciBean, TAuthRecord arBean,
			String addressid, String userid) throws ServiceException {

		if(this.authRecordService.getCountByCidAndAuthstauts(ciBean.getId(), AuthRecordStatus.AUTH_STATUS_CHECK_ING)>0){// 查询是否有认证中的记录
			throw new ServiceException(ServiceErrorCode.COMPANY_REPEAT_APPLY_ERROR,"请不要重复提交认证信息");
		}
		if(this.authRecordService.getCountByCidAndAuthstauts(ciBean.getId(), AuthRecordStatus.AUTH_STATUS_CHECK_YES)>0){// 查询是否有通过的记录
			throw new ServiceException(ServiceErrorCode.COMPANY_REPEAT_APPLY_ERROR,"已认证通过，不能二次认证");
		}

		Date date = Calendar.getInstance().getTime();

		ciBean.setAuthstatus(CompanyInfo.CompanyAuthStatus.AUTH_STATUS_ING);
		ciBean.setBailstatus(CompanyInfo.CompanyBailStatus.BAIL_STATUS_NO);
		ciBean.setCreatedate(date);
		this.companyInfoDao.update(ciBean);

		if(StringUtils.isNotEmpty(ciBean.getCompanyImgIds())){ // 企业照片关联更新
			String ids[] = ciBean.getCompanyImgIds().split(",");
			for(String id : ids){
				this.uploadImagesService.updateOtypeAndOid(ciBean.getId(), FileInfo.FileOType.FILE_OTYPE_COMPANY, id);
			}
		}

		arBean.setCid(ciBean.getId());
		arBean.setType(AuthRecordType.AUTH_RECORD_TYPE_COMPANY);
		arBean.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
		arBean.setCreatedate(date);
		this.authRecordService.add(arBean); // 认证记录新增

		if(arBean.getImgid()!=null && arBean.getImgid()>0){ // 认证图片关联,将认证记录ID更新到图片表
			this.uploadImagesService.updateOtypeAndOid(arBean.getId(), FileInfo.FileOType.FILE_OTYPE_COMPANY_AUTH, arBean.getImgid()+"");
		}

		// 设置默认卸货地址
//		if(StringUtils.isNotEmpty(addressid)){
//			this.companyAddressService.setDefault(addressid);
//		}

		/*****消息发送********************************/

		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH, arBean.getId(), ciBean.getId(), SystemMessageContent.getMsgContentOfCompanyAuthIng());
		mi.setSendSystemMsg(true);
		this.messageSendManager.msgSend(mi);


	}

	/* (non-Javadoc)获取企业评价信息
	 * @see com.appabc.datas.service.company.ICompanyInfoService#getEvaluationByCid(java.lang.String)
	 */
	public EvaluationInfoBean getEvaluationByCid(String cid){
		TCompanyEvaluation ceEntity = new TCompanyEvaluation();
		ceEntity.setCid(cid);
		List<TCompanyEvaluation> ceList = companyEvaluationService.queryForList(ceEntity);

		int credit = 0; // 信用分
		int satisfaction = 0; // 满意度分
		for(TCompanyEvaluation ce : ceList){
			credit += ce.getCredit();
			satisfaction += ce.getSatisfaction();
		}

		int ceSize = ceList.size() == 0? 1 :ceList.size();

		EvaluationInfoBean ei = this.contractInfoService.getTransactionSuccessRate(cid);
		ei.setAverageCredit((float)credit/ceSize);
		ei.setAverageEvaluation((float)satisfaction/ceSize);
		return ei;
	}

}
