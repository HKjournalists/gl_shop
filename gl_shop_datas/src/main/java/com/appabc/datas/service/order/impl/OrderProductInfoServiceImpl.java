/**
 *
 */
package com.appabc.datas.service.order.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.order.IOrderProductInfoDao;
import com.appabc.datas.service.order.IOrderProductInfoService;
import com.appabc.datas.service.order.IOrderProductPropertyService;
import com.appabc.datas.service.system.IUploadImagesService;

/**
 * @Description : 交易中的商品信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午9:50:01
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrderProductInfoServiceImpl implements IOrderProductInfoService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOrderProductInfoDao orderProductInfoDao;
	@Autowired
	private IOrderProductPropertyService orderProductPropertyService;
	@Autowired
	private IUploadImagesService uploadImagesService;

	public void add(TOrderProductInfo entity) {
		this.orderProductInfoDao.save(entity);
	}

	public void modify(TOrderProductInfo entity) {
		this.orderProductInfoDao.update(entity);
	}

	public void delete(TOrderProductInfo entity) {
		this.orderProductInfoDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.orderProductInfoDao.delete(id);
	}

	public TOrderProductInfo query(TOrderProductInfo entity) {
		return this.orderProductInfoDao.query(entity);
	}

	public TOrderProductInfo query(Serializable id) {
		return this.orderProductInfoDao.query(id);
	}

	public List<TOrderProductInfo> queryForList(TOrderProductInfo entity) {
		return this.orderProductInfoDao.queryForList(entity);
	}

	public List<TOrderProductInfo> queryForList(Map<String, ?> args) {
		return this.orderProductInfoDao.queryForList(args);
	}

	public QueryContext<TOrderProductInfo> queryListForPagination(
			QueryContext<TOrderProductInfo> qContext) {
		return this.orderProductInfoDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderProductInfoService#copyProductAllInfo(int)
	 */
	@Override
	public int copyOrderProductAllInfo(int opiid) {
		
		logger.debug("商品信息复制,opiid="+opiid);
		
		TOrderProductInfo opi = this.orderProductInfoDao.query(opiid);
		if(opi != null){
			try {
				TOrderProductInfo opiNew = (TOrderProductInfo) opi.clone();
				opiNew.setId(null);
				opiNew.setPsize(null);
				this.orderProductInfoDao.save(opiNew);
				
				/*******商品属性复制***************/
				TOrderProductProperty oppEntity = new TOrderProductProperty();
				oppEntity.setPpid(opiid);
				oppEntity.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
				List<TOrderProductProperty> oppList = orderProductPropertyService.queryForList(oppEntity); // 商品属性
				
				if(oppList != null && oppList.size()>0){
					logger.debug("商品属性复制,oppList.size()="+oppList.size());
					for(TOrderProductProperty opp : oppList){
						TOrderProductProperty oppNew = (TOrderProductProperty) opp.clone();
						oppNew.setId(null);
						oppNew.setPpid(Integer.parseInt(opiNew.getId()));
						this.orderProductPropertyService.add(oppNew);
					}
				}else{
					logger.error("TOrderProductProperty list size is 0, opiid="+opiid);
				}
				
				/*******商品规格复制***************/
				TOrderProductProperty psize = this.orderProductPropertyService.query(opi.getPsize()); // 商品规格
				if(psize != null){
					logger.debug("商品规格复制,psize="+opi.getPsize());
					TOrderProductProperty psizeNew = (TOrderProductProperty) psize.clone();
					psizeNew.setId(null);
					psizeNew.setPpid(Integer.parseInt(opiNew.getId()));
					this.orderProductPropertyService.add(psizeNew);
					
					opiNew.setPsize(psizeNew.getId());
					this.orderProductInfoDao.update(opiNew); // 更新 新的商品规格ID
				}else{
					logger.error("TOrderProductProperty psize is null, opiid="+opiid);
				}
				
				/*******商品图片复制***************/
				List<TUploadImages> uiList = this.uploadImagesService.getListByOidAndOtype(opi.getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal());
				uploadImagesService.copyUploadImagesRelationshipsObject(opiNew.getId(), FileOType.FILE_OTYPE_PRODUCT_ORDER, uiList);
				
				return Integer.parseInt(opiNew.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return 0;
	}

}
