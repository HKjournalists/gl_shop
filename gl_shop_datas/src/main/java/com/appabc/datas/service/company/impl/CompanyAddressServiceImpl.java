/**
 *
 */
package com.appabc.datas.service.company.impl;

import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import com.appabc.datas.service.company.ICompanyAddressService;
import com.appabc.datas.service.system.IUploadImagesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

			for(String imgid : addressImgIds){
				this.uploadImagesService.updateOtypeAndOid(entity.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS, imgid);
			}
		}
	}

	public void modify(TCompanyAddress entity) {
		companyAddressDao.update(entity);

		/******卸货地址图片处理*****************/
		String[] imgIds = null;
		if(StringUtils.isNotEmpty(entity.getAddressImgIds())){
			imgIds = entity.getAddressImgIds().split(",");
		}
		this.uploadImagesService.updateOtypeAndOidBatch(entity.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS, imgIds);

	}

	public void delete(TCompanyAddress entity) {
		this.companyAddressDao.delete(entity);
	}

	public void delete(Serializable id) {
		// 删除关联图片
		this.uploadImagesService.delByOidAndOtype(id.toString(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal());
		// 删除记录
		companyAddressDao.delete(id);

	}

	public TCompanyAddress query(TCompanyAddress entity) {
		return this.companyAddressDao.query(entity);
	}

	public TCompanyAddress query(Serializable id) {
		TCompanyAddress ca = companyAddressDao.query(id);
		if (ca != null){
			// 图片信息添加
			ca.setvImgList(this.uploadImagesService.getViewImgsByOidAndOtype(ca.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal()));
		}

		return ca;
	}

	public List<TCompanyAddress> queryForList(TCompanyAddress entity) {
		return companyAddressDao.queryForList(entity);
	}

	/* (non-Javadoc)带图片的列表信息
	 * @see com.appabc.datas.service.company.ICompanyAddressService#queryForListHaveImgs(com.appabc.bean.pvo.TCompanyAddress)
	 */
	public List<TCompanyAddress> queryForListHaveImgs(TCompanyAddress entity) {

		List<TCompanyAddress> caList = companyAddressDao.queryForList(entity);
		for(TCompanyAddress ca : caList){
			ca.setvImgList(this.uploadImagesService.getViewImgsByOidAndOtype(ca.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal()));
		}

		return caList;
	}

	public List<TCompanyAddress> queryForList(Map<String, ?> args) {
		return this.companyAddressDao.queryForList(args);
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
			queryEntity.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_DEFULT);

			List<TCompanyAddress> caList = this.companyAddressDao.queryForList(queryEntity);
			for(TCompanyAddress ca : caList){
				ca.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_OTHER);
				this.companyAddressDao.update(ca);
			}

			// 设置默认
			entity.setStatus(CompanyInfo.AddressStatus.ADDRESS_STATUS_DEFULT);
			companyAddressDao.update(entity);
		}


	}


}
