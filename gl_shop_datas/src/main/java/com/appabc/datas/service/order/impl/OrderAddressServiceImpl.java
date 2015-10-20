/**
 *
 */
package com.appabc.datas.service.order.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.order.IOrderAddressDao;
import com.appabc.datas.service.order.IOrderAddressService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.tools.utils.AreaManager;

/**
 * @Description : 询单或合同卸货地址service实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午3:49:23
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrderAddressServiceImpl implements IOrderAddressService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOrderAddressDao orderAddressDao;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private AreaManager areaManager;

	public void add(TOrderAddress entity) {
		this.orderAddressDao.save(entity);
	}

	public void modify(TOrderAddress entity) {
		this.orderAddressDao.update(entity);
	}

	public void delete(TOrderAddress entity) {
		this.orderAddressDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.orderAddressDao.delete(id);
	}

	public TOrderAddress query(TOrderAddress entity) {
		return this.orderAddressDao.query(entity);
	}

	public TOrderAddress query(Serializable id) {
		TOrderAddress oa = orderAddressDao.query(id);
		if(StringUtils.isNotEmpty(oa.getAreacode())){
			oa.setAreaFullName(areaManager.getFullAreaName(oa.getAreacode()));
		}
		// 图片信息添加
		oa.setvImgList(this.uploadImagesService.getViewImgsByOidAndOtype(oa.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER.getVal()));
		return oa;
	}

	public List<TOrderAddress> queryForList(TOrderAddress entity) {
		return this.orderAddressDao.queryForList(entity);
	}

	public List<TOrderAddress> queryForList(Map<String, ?> args) {
		return this.orderAddressDao.queryForList(args);
	}

	public QueryContext<TOrderAddress> queryListForPagination(
			QueryContext<TOrderAddress> qContext) {
		return this.orderAddressDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderAddressService#copyTOrderAddressRelationshipsFid(java.lang.String, com.appabc.bean.pvo.TOrderAddress)
	 */
	@Override
	public int copyTOrderAddressRelationshipsFid(String newFid, TOrderAddress oa) {
		logger.debug("询单的卸货地址复制,newFid="+newFid);
		if(StringUtils.isNotEmpty(newFid) && oa != null){
			try {
				/*****询单卸货地址信息复制***********************/
				TOrderAddress oaNew = (TOrderAddress) oa.clone();
				oaNew.setId(null);
				oaNew.setFid(newFid);
				
				this.orderAddressDao.save(oaNew);
				/*****询单卸货地址图片复制***********************/
				List<TUploadImages> uiList = this.uploadImagesService.getListByOidAndOtype(oa.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER.getVal());
				this.uploadImagesService.copyUploadImagesRelationshipsObject(oaNew.getId(), FileOType.FILE_OTYPE_ADDRESS_ORDER, uiList);
				
				return Integer.parseInt(oaNew.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			logger.error("数据不完整，newFid="+newFid+"TOrderAddress="+oa);
		}
		
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderAddressService#delByFid(java.lang.String)
	 */
	@Override
	public void delByFid(String fid) {
		if(StringUtils.isNotEmpty(fid)){
			TOrderAddress caEntity  = new TOrderAddress();
			caEntity.setFid(fid);
			List<TOrderAddress> caList = this.orderAddressDao.queryForList(caEntity);
			if(caList != null && caList.size()>0 && caList.get(0) != null){
				this.orderAddressDao.delete(caList.get(0).getId());
				try {
					this.uploadImagesService.delByOidAndOtype(caList.get(0).getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("询单的卸货地址的图片删除错误，fid="+fid);
				}
			}
			
		}else{
			logger.info("无法删除询单的卸货地址，fid="+fid);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderAddressService#queryByFid(java.lang.String)
	 */
	@Override
	public TOrderAddress queryByFid(String fid) {
		
		if(StringUtils.isNotEmpty(fid)){
			try {
				TOrderAddress entity = new TOrderAddress();
				entity.setFid(fid);
				
				entity = orderAddressDao.query(entity);
				if (entity != null){
					// 图片信息添加
					entity.setvImgList(this.uploadImagesService.getViewImgsByOidAndOtype(entity.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER.getVal()));
					
					if(StringUtils.isNotEmpty(entity.getAreacode())){
						entity.setAreaFullName(areaManager.getFullAreaName(entity.getAreacode()));
					}
				}
				return entity;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			logger.error("询单ID为空");
		}
		
		return null;
	}
	
}
