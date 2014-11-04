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

import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import com.appabc.datas.enums.CompanyInfo;
import com.appabc.datas.enums.FileInfo;
import com.appabc.datas.enums.FileInfo.FileOType;
import com.appabc.datas.service.company.ICompanyAddressService;
import com.appabc.datas.service.system.IUploadImagesService;

/**
 * @Description : 公司卸货地址SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 上午11:25:25
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyAddressServiceImpl implements ICompanyAddressService {
	
	@Autowired
	private ICompanyAddressDao companyAddressDao;
	@Autowired
	private IUploadImagesService uploadImagesService;
	
	public void add(TCompanyAddress entity) {
		companyAddressDao.save(entity);
		
		if(StringUtils.isNotEmpty(entity.getAddressImgIds())){ // 关联图片
			String[] addressImgIds = entity.getAddressImgIds().split(",");
			
			TUploadImages ui = null;
			for(String imgid : addressImgIds){
				ui = this.uploadImagesService.query(imgid);
				ui.setOid(entity.getId());
				ui.setOtype(FileOType.FILE_OTYPE_ADDRESS.getVal());
				this.uploadImagesService.modify(ui);
			}
		}
	}

	public void modify(TCompanyAddress entity) {
		companyAddressDao.update(entity);
		
		/******卸货地址图片处理*****************/
		TUploadImages uiBean = new TUploadImages();
		uiBean.setOid(entity.getId());
		uiBean.setOtype(FileOType.FILE_OTYPE_ADDRESS.getVal());
		List<TUploadImages> imgList = this.uploadImagesService.queryForList(uiBean);
		
		if(StringUtils.isNotEmpty(entity.getAddressImgIds())){ // 关联图片
			String[] addressImgIds = entity.getAddressImgIds().split(",");
			
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
				ui.setOid(entity.getId());
				ui.setOtype(FileOType.FILE_OTYPE_ADDRESS.getVal());
				this.uploadImagesService.modify(ui);
			}
			
		}
		
		for(int i=0; i<imgList.size(); i++){ // 删除无用图片
			this.uploadImagesService.delete(imgList.get(i).getId());
		}
	}

	public void delete(TCompanyAddress entity) {
	}

	public void delete(Serializable id) {
		// 删除关联图片
		this.uploadImagesService.delByOidAndOtype(id.toString(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal());
		// 删除记录
		companyAddressDao.delete(id);
		
	}

	public TCompanyAddress query(TCompanyAddress entity) {
		return null;
	}

	public TCompanyAddress query(Serializable id) {
		TCompanyAddress ca = companyAddressDao.query(id);
		if (ca != null){ 
			// 图片信息添加
			ca.setAddressImgUrls(this.uploadImagesService.getUrlsByOidAndOtype(ca.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal()));
		}
		
		return ca;
	}

	public List<TCompanyAddress> queryForList(TCompanyAddress entity) {
		return companyAddressDao.queryForList(entity);
	}

	public List<TCompanyAddress> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TCompanyAddress> queryListForPagination(
			QueryContext<TCompanyAddress> qContext) {
		return null;
	}

	/* (non-Javadoc)设置为默认卸货地址
	 * @see com.appabc.datas.service.company.ICompanyAddressService#setDefault(java.io.Serializable)
	 */
	public void setDefault(Serializable id) {
		
		TCompanyAddress entity = this.companyAddressDao.query(id);
		if(entity != null){
			
			// 先将之前的默认地址改为非默认
			TCompanyAddress queryEntity = new TCompanyAddress();
			queryEntity.setCid(entity.getCid());
			queryEntity.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_DEFULT.getVal());
			
			List<TCompanyAddress> caList = this.companyAddressDao.queryForList(queryEntity);
			for(TCompanyAddress ca : caList){
				ca.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_OTHER.getVal());
				this.companyAddressDao.update(ca);
			}
			
			// 设置默认
			entity.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_DEFULT.getVal());
			companyAddressDao.update(entity);
		}
		
		
	}

}
