/**
 *
 */
package com.appabc.datas.service.company.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyRanking;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.ErrorCode;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.dao.company.ICompanyContactDao;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAddressService;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.company.ICompanyShippingService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.datas.tool.ViewInfoEncryptUtil;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.AreaManager;
import com.appabc.tools.utils.GuarantStatusCheck;
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
	private ICompanyRankingService companyRankingService;
	@Autowired
	private IContractInfoService contractInfoService;
	@Autowired
	private IContractInfoDAO contractInfoDAO;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPassPayService passPayLocalService;
	@Autowired
	private GuarantStatusCheck guarantStatusCheck;
	@Autowired
	private MessageSendManager messageSendManager;
	@Autowired
	private IAuthRecordService authRecordService;
	@Autowired
	private ICompanyAuthService companyAuthService;
	@Autowired 
	private ICompanyShippingService companyShippingService;
	@Autowired
	private ICompanyPersonalService CompanyPersonalService;
	@Autowired
	private AreaManager areaManager;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IPassbookInfoService passbookInfoService;


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
	
	public TCompanyInfo queryAuthCmpInfo(String id) {
		return this.companyInfoDao.queryAuthCmpInfo(id);
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

		CompanyAllInfo cai = this.companyInfoDao.queryAuthCompanyInfo(cid);
		if(cai != null){
			// 认证图片
			if(StringUtils.isNotEmpty(cai.getAuthid())){
				cai.getAuthImgList().addAll(uploadImagesService.getViewImgsByOidAndOtype(cai.getAuthid(), FileOType.FILE_OTYPE_COMPANY_AUTH.getVal()));
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
			if(CollectionUtils.isEmpty(caList)){ // 没有默认卸货地址，随便给一个卸货地址
				caEntity.setStatus(null);
				caList = this.companyAddressService.queryForList(caEntity);
			}
			if(CollectionUtils.isNotEmpty(caList)){
				cai.setAddress(caList.get(0).getAddress());
				cai.setAreacode(caList.get(0).getAreacode());
				cai.setDeep(caList.get(0).getDeep());
				cai.setRealdeep(caList.get(0).getRealdeep());
				cai.setShippington(caList.get(0).getShippington());
				if(StringUtils.isNotEmpty(caList.get(0).getAreacode())){
					cai.setAddrAreaFullName(areaManager.getFullAreaName(caList.get(0).getAreacode()));
				}

				// 卸货地址图片URL
				cai.setAddressImgList(this.uploadImagesService.getViewImgsByOidAndOtype(caList.get(0).getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal()));
			}

			// 企业评价信息
			cai.setEvaluationInfo(this.getEvaluationByCid(cid));

			// 企业保证金缴纳状态，时时查询，不以企业表中的为准
			float monay = passPayLocalService.getGuarantyTotal(cid); // 保证金总金额
			cai.setBailstatus(guarantStatusCheck.checkCashDeposit(cid, monay));

			if(StringUtils.isNotEmpty(requestCid) && !cid.equals(requestCid)){ // 其它用户查看
				if(!contractInfoService.isOldCustomer(requestCid, cid)){ // 2个企业未发生过交易，进行企业加密处理
					ViewInfoEncryptUtil.encryptCompanyInfo(cai);
				}
			}
			
			// 认证后的信息
			if(cai.getCtype()!=null && StringUtils.isNotEmpty(cai.getAuthid())){
				if(CompanyType.COMPANY_TYPE_ENTERPRISE.equals(cai.getCtype())){
					cai.setCompanyAuth(companyAuthService.queryByAuthid(Integer.parseInt(cai.getAuthid())));
				}else if(CompanyType.COMPANY_TYPE_SHIP.equals(cai.getCtype())){
					cai.setShippingAuth(companyShippingService.queryByAuthid(Integer.parseInt(cai.getAuthid())));
				}else if(CompanyType.COMPANY_TYPE_PERSONAL.equals(cai.getCtype())){
					cai.setPersonalAuth(CompanyPersonalService.queryByAuthid(Integer.parseInt(cai.getAuthid())));
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
	public void authApply(TCompanyInfo ciBean, String[] authImgids,
			String addressid, String userid) throws ServiceException {

		if(this.authRecordService.getCountByCidAndAuthstauts(ciBean.getId(), AuthRecordStatus.AUTH_STATUS_CHECK_ING)>0){// 查询是否有认证中的记录
			throw new ServiceException(ServiceErrorCode.COMPANY_REPEAT_APPLY_ERROR,"请不要重复提交认证信息");
		}
		TCompanyInfo ciEntity = this.queryAuthCmpInfo(ciBean.getId());
		if(ciEntity ==  null){
			throw new ServiceException(ErrorCode.RESULT_ERROR_CODE,"不存在的企业ID="+ciBean.getId());
		}
		if(AuthRecordStatus.AUTH_STATUS_CHECK_YES == ciEntity.getAuthstatus() && (ciEntity.getCtype() == CompanyType.COMPANY_TYPE_ENTERPRISE || ciEntity.getCtype() == CompanyType.COMPANY_TYPE_SHIP)){
			throw new ServiceException(ServiceErrorCode.COMPANY_REPEAT_APPLY_ERROR,"企业和船舶不能二次认证，已认证类型:"+ciEntity.getCtype().getText());
		}
//		if(this.authRecordService.getCountByCidAndAuthstauts(ciBean.getId(), AuthRecordStatus.AUTH_STATUS_CHECK_YES)>0){// 查询是否有通过的记录
//			throw new ServiceException(ServiceErrorCode.COMPANY_REPEAT_APPLY_ERROR,"已认证通过，不能二次认证");
//		}
		if(authImgids==null || authImgids.length==0){
			throw new ServiceException(ErrorCode.DATA_IS_NOT_COMPLETE,"认证图片信息不能为空");
		}

		Date date = Calendar.getInstance().getTime();
		
		ciBean.setCreatedate(ciEntity.getCreatedate());
		ciBean.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
//		ciBean.setBailstatus(CompanyInfo.CompanyBailStatus.BAIL_STATUS_NO);
		this.companyInfoDao.update(ciBean);

		if(StringUtils.isNotEmpty(ciBean.getCompanyImgIds())){ // 企业照片关联更新
			String ids[] = ciBean.getCompanyImgIds().split(",");
			for(String id : ids){
				this.uploadImagesService.updateOtypeAndOid(ciBean.getId(), FileInfo.FileOType.FILE_OTYPE_COMPANY, id);
			}
		}

		TAuthRecord arBean = new TAuthRecord();
		arBean.setCid(ciBean.getId());
		arBean.setType(AuthRecordType.AUTH_RECORD_TYPE_COMPANY);
		arBean.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
		arBean.setCreatedate(date);
		this.authRecordService.add(arBean); // 认证记录新增
		
		// 认证图片ID关联
		boolean isExistauthImg = false;
		for (int i = 0; i < authImgids.length; i++) {
			if(StringUtils.isNotEmpty(authImgids[i])){
				this.uploadImagesService.updateOtypeAndOid(arBean.getId(), FileInfo.FileOType.FILE_OTYPE_COMPANY_AUTH, authImgids[i]);
				isExistauthImg = true;
			}
		}
		if(isExistauthImg == false){
			throw new ServiceException(ErrorCode.DATA_IS_NOT_COMPLETE,"认证图片ID不能为空");
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
		EvaluationInfoBean ei = new EvaluationInfoBean(); 
		
		TCompanyRanking crEntity = new TCompanyRanking();
		crEntity.setCid(cid);
		TCompanyRanking cr = companyRankingService.query(crEntity);
		if(cr != null){
			ei.setAverageCredit(cr.getCredit());
			ei.setAverageEvaluation(cr.getDegress());
			ei.setTransactionSuccessRate(cr.getOrderspersent());
			ei.setTransactionSuccessNum(cr.getOrdersnum());
		}else{
			ei.setAverageCredit(0f);
			ei.setAverageEvaluation(0f);
			ei.setTransactionSuccessRate(0f);
			ei.setTransactionSuccessNum(0);
		}

		return ei;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyInfoService#getAuthStatusByCid(java.lang.String)
	 */
	@Override
	public AuthRecordStatus getAuthStatusByCid(String cid) throws ServiceException{
		if(StringUtils.isNotEmpty(cid)){
			TCompanyInfo ci = this.queryAuthCmpInfo(cid);
			if(ci != null){
				return ci.getAuthstatus();
			}else{
				throw new ServiceException("企业不存在,cid="+cid);
			}
			
		}else{
			throw new ServiceException("企业ID不能为空");
		}
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryCmpInfoByUserPhone(java.lang.String)  
	 */
	@Override
	public TCompanyInfo queryCmpInfoByUserPhone(String phone) {
		return this.companyInfoDao.queryCmpInfoByUserPhone(phone);
		//return null;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryCount(com.appabc.bean.pvo.TCompanyInfo)
	 */
	@Override
	public int queryCount(TCompanyInfo entity) {
		if(entity == null) entity = new TCompanyInfo();
		return this.companyInfoDao.queryCount(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryCmpInfoListByUserPhones(java.lang.String)  
	 */
	@Override
	public List<TCompanyInfo> queryCmpInfoListByUserPhones(String phones) {
		return companyInfoDao.queryCmpInfoListByUserPhones(phones);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryInvalidListForTask()  
	 */
	@Override
	public List<TCompanyInfo> queryInvalidListForTask() {
		// TODO Auto-generated method stub
		return companyInfoDao.queryInvalidListForTask();
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryNewListForTask()  
	 */
	@Override
	public List<TCompanyInfo> queryNewListForTask() {
		// TODO Auto-generated method stub
		return companyInfoDao.queryNewListForTask();
	}
	@Transactional(rollbackFor = Exception.class)
	public void jobQueryVerifyListForTask()
	{
		// 将新的未认证信息加入任务列表
		List<TCompanyInfo> ciList = this.queryNewListForTask();
		Task<?> task = null;
		for (TCompanyInfo ci : ciList) {
			task = new Task();
			task.setCreateTime(Calendar.getInstance().getTime());
			task.setType(TaskType.UrgeVerify);
			task.setCompany(this.query(ci.getId()));
			task.setObjectId(ci.getId());
			taskService.createTask(task); // 创建任务			
		}		
		// 将失效的认证，并在任务列表中的任务删除
		List<TCompanyInfo> ciInvalidList = this.queryInvalidListForTask();
		for (TCompanyInfo ci : ciInvalidList) {
			this.taskService.delByTypeAndObjectId(TaskType.UrgeVerify, ci.getId());
		}		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void jobQueryDepositListForTask() {
		// 将新的需要催促缴纳保证金加入任务列表
		List<TPassbookInfo> pbiList = passbookInfoService.queryNewListForTask();
		Task<?> task = null;
		for (TPassbookInfo pbi : pbiList) {
			task = new Task();
			task.setCreateTime(Calendar.getInstance().getTime());
			task.setType(TaskType.UrgeDeposit);
			task.setCompany(this.query(pbi.getCid()));
			task.setObjectId(pbi.getId());
			taskService.createTask(task); // 创建任务
		}		
		// 将不需要催促保证金，并在任务列表中的任务删除
		List<TPassbookInfo> pbiInvalidList = passbookInfoService.queryInvalidListForTask();
		for (TPassbookInfo pbi : pbiInvalidList) {
			this.taskService.delByTypeAndObjectId(TaskType.UrgeDeposit, pbi.getId());
		}		
	}
}
