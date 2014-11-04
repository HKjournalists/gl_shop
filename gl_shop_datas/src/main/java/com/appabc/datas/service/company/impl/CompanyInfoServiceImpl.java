/**
 *
 */
package com.appabc.datas.service.company.impl;

import java.io.Serializable;
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
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.IAuthRecordDao;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import com.appabc.datas.dao.company.ICompanyContactDao;
import com.appabc.datas.dao.company.ICompanyInfoDao;
import com.appabc.datas.enums.AuthRecordInfo;
import com.appabc.datas.enums.CompanyInfo;
import com.appabc.datas.enums.FileInfo;
import com.appabc.datas.enums.FileInfo.FileOType;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.system.IUploadImagesService;
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
	private ICompanyAddressDao companyAddressDao;
	@Autowired
	private IAuthRecordDao authRecordDao;
	@Autowired
	private ICompanyContactDao companyContactDao;
	@Autowired
	private SystemParamsManager spm;
	@Autowired
	private ICompanyEvaluationService companyEvaluationService;
	@Autowired
	private IContractInfoService contractInfoService;
	

	public void add(TCompanyInfo entity) {
		this.companyInfoDao.save(entity);
	}

	public void modify(TCompanyInfo entity) {
		this.companyInfoDao.update(entity);
	}

	public void delete(TCompanyInfo entity) {
	}

	public void delete(Serializable id) {
		this.companyInfoDao.delete(id);
	}

	public TCompanyInfo query(TCompanyInfo entity) {
		return null;
	}

	public TCompanyInfo query(Serializable id) {
		return this.companyInfoDao.query(id);
	}

	public List<TCompanyInfo> queryForList(TCompanyInfo entity) {
		return this.companyInfoDao.queryForList(entity);
	}

	public List<TCompanyInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyInfo> queryListForPagination(
			QueryContext<TCompanyInfo> qContext) {
		return null;
	}

	/* (non-Javadoc)获取公司信息
	 * @see com.appabc.datas.service.company.ICompanyInfoService#queryAuthCompanyInfo(java.lang.String)
	 */
	public CompanyAllInfo queryAuthCompanyInfo(String cid) {
		
		CompanyAllInfo cai = this.companyInfoDao.queryAuthCompanyInfo(cid);
		if(cai != null){
			// 认证图片
			cai.setAuthImgUrl(this.uploadImagesService.getUrlByid(cai.getImgid()));
			cai.setImgid(null);
			
			// 企业照片
			cai.setCompanyImgUrls(this.uploadImagesService.getUrlsByOidAndOtype(cid, FileInfo.FileOType.FILE_OTYPE_COMPANY.getVal()));
			
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
			caEntity.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_DEFULT.getVal());
			List<TCompanyAddress> caList = this.companyAddressDao.queryForList(caEntity);
			if(caList != null && caList.size()>0 && caList.get(0) != null){
				cai.setAddress(caList.get(0).getAddress());
				cai.setAreacode(caList.get(0).getAreacode());
				cai.setDeep(caList.get(0).getDeep());
				cai.setRealdeep(caList.get(0).getRealdeep());
				
				// 卸货地址图片URL
				cai.setAddressImgUrls(this.uploadImagesService.getUrlsByOidAndOtype(caList.get(0).getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal()));
			}
			
			// 企业评价信息
			cai.setEvaluationInfo(this.getEvaluationByCid(cid));
			
		}
		
		return cai;
	}

	/* (non-Javadoc)  根据企业ID获取该企业应缴纳的保证金额度
	 * @see com.appabc.datas.service.company.ICompanyInfoService#getShouldDepositAmountByCid(java.lang.String)  
	 */
	public float getShouldDepositAmountByCid(String cid) {
		TCompanyInfo ci = this.companyInfoDao.query(cid);
		if(ci != null){
			if(ci.getCtype().equals(CompanyInfo.CompanyType.COMPANY_TYPE_ENTERPRISE.getVal())){ // 企业
				return spm.getFloat("BOND_ENTERPRISE");
			}else if(ci.getCtype().equals(CompanyInfo.CompanyType.COMPANY_TYPE_PERSONAL.getVal())){ // 个人
				return spm.getFloat("BOND_PERSONAL");
			}else if(ci.getCtype().equals(CompanyInfo.CompanyType.COMPANY_TYPE_SHIP.getVal())){ // 船舶
				
				//需要判断吨位，后续补充
				return 5000f;
			}
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
			TUploadImages uiBean = new TUploadImages();
			uiBean.setOid(cid);
			uiBean.setOtype(FileOType.FILE_OTYPE_COMPANY.getVal());
			List<TUploadImages> imgList = this.uploadImagesService.queryForList(uiBean);
			
			if(StringUtils.isNotEmpty(companyImgIds)){ // 关联图片
				String[] addressImgIds = companyImgIds.split(",");
				
				TUploadImages ui = null;
				for(String imgid : addressImgIds){
					boolean isContinue = false;
					for(int i=0; i<imgList.size(); i++){ // 过滤不需要修改的图片
						if(imgList.get(i).getId().endsWith(imgid)){
							imgList.remove(i);
							isContinue = true;
							continue;
						}
					}
					
					if(isContinue) continue;
					
					ui = this.uploadImagesService.query(imgid);
					ui.setOid(cid);
					ui.setOtype(FileOType.FILE_OTYPE_COMPANY.getVal());
					this.uploadImagesService.modify(ui);
				}
				
			}
			
			for(int i=0; i<imgList.size(); i++){ // 删除无用图片
				this.uploadImagesService.delete(imgList.get(i).getId());
			}
		}
		
	}
	
	/* (non-Javadoc)认证申请
	 * @see com.appabc.datas.service.company.IAuthRecordService#authApply(com.appabc.bean.pvo.TCompanyInfo, com.appabc.bean.pvo.TAuthRecord, com.appabc.bean.pvo.TCompanyAddress, java.lang.String)
	 */
	public void authApply(TCompanyInfo ciBean, TAuthRecord arBean,
			TCompanyAddress caBean, String userid) {
		
		Date date = new Date();
		
		ciBean.setAuthstatus(CompanyInfo.CompanyAuthStatus.AUTH_STATUS_NO.getVal());
		ciBean.setBailstatus(CompanyInfo.CompanyBailStatus.BAIL_STATUS_NO.getVal());
		ciBean.setCreatedate(date);
		this.companyInfoDao.update(ciBean);
		
		if(StringUtils.isNotEmpty(ciBean.getCompanyImgIds())){ // 企业照片关联更新
			String ids[] = ciBean.getCompanyImgIds().split(",");
			for(String id : ids){
				TUploadImages ui = this.uploadImagesService.query(id);
				if(ui != null){
					ui.setOid(ciBean.getId());
					ui.setOtype(FileInfo.FileOType.FILE_OTYPE_COMPANY.getVal());
					this.uploadImagesService.modify(ui);
				}
			}
		}
		
		arBean.setCid(ciBean.getId());
		arBean.setType(AuthRecordInfo.AuthRecordType.AUTH_RECORD_TYPE_COMPANY.getVal());
		arBean.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_ING.getVal());
		arBean.setCreatedate(date);
		this.authRecordDao.save(arBean); // 认证记录新增
		
		if(arBean.getImgid()!=null && arBean.getImgid()>0){ // 认证图片关联,将认证记录ID更新到图片表
			TUploadImages ui = uploadImagesService.query(arBean.getImgid());
			if(ui != null){
				ui.setOid(arBean.getId());
				ui.setOtype(FileInfo.FileOType.FILE_OTYPE_COMPANY_AUTH.getVal());
				this.uploadImagesService.modify(ui);
			}
		}
		
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
