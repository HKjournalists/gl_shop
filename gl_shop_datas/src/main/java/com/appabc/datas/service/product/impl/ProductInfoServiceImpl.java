/**
 *
 */
package com.appabc.datas.service.product.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.product.IProductInfoDao;
import com.appabc.datas.dao.product.IProductPropertyDao;
import com.appabc.datas.service.product.IProductInfoService;

/**
 * @Description : 商品基本信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午2:47:31
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProductInfoServiceImpl implements IProductInfoService{

	@Autowired
	private IProductInfoDao productInfoDao;
	
	@Autowired
	private IProductPropertyDao productPropertyDao;
	
	/* (non-Javadoc)添加产品和产品属性
	 * @see com.appabc.datas.service.product.IProductInfoService#add(com.appabc.bean.TProductInfo, java.util.List)
	 */
	public void add(TProductInfo productInfo, List<TProductProperty> productPropertyList) {
		
		this.productInfoDao.save(productInfo);
		
		for(TProductProperty pp : productPropertyList){
			pp.setPid(productInfo.getId());
			
			this.productPropertyDao.save(pp);
		}
	}

	/* (non-Javadoc)删除商品及属性
	 * @see com.appabc.common.base.service.IBaseService#delete(java.lang.String)
	 */
	public void delete(Serializable id) {
		this.productPropertyDao.delByPid(id.toString()); // 删除商品属性
		this.productInfoDao.delete(id);
	}


	/* (non-Javadoc)根据ID查询商品基本信息
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)
	 */
	public TProductInfo query(Serializable id) {
		return this.productInfoDao.queryByid(id);
	}

	/* (non-Javadoc)根据商品code查询商品
	 * @see com.appabc.datas.service.product.IProductInfoService#queryByPcode(java.lang.String)
	 */
	public List<TProductInfo> queryByPcode(String pcode) {
		return this.productInfoDao.queryByPcode(pcode);
	}

	public void add(TProductInfo entity) {
	}

	/* (non-Javadoc)更新商品基本信息
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TProductInfo entity) {
		this.productInfoDao.update(entity);
	}

	public void delete(TProductInfo entity) {
	}

	public TProductInfo query(TProductInfo entity) {
		return null;
	}

	public List<TProductInfo> queryForList(TProductInfo entity) {
		return this.productInfoDao.queryForList(entity);
	}

	public List<TProductInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TProductInfo> queryListForPagination(
			QueryContext<TProductInfo> qContext) {
		return null;
	}

	public void modify(TProductInfo productInfo,
			List<TProductProperty> productPropertyList) {
	}

}
