/**
 *
 */
package com.appabc.datas.service.product.impl;

import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.product.IProductPriceDao;
import com.appabc.datas.service.product.IProductPriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description : 商品当日价格SERVICE实现类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午5:06:02
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProductPriceServiceImpl implements IProductPriceService{
	
	@Autowired
	private IProductPriceDao productPriceDao;

	public void add(TProductPrice entity) {
		this.productPriceDao.save(entity);
	}

	public void modify(TProductPrice entity) {
		this.productPriceDao.update(entity);
	}

	public void delete(TProductPrice entity) {
		this.productPriceDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.productPriceDao.delete(id);
	}

	public TProductPrice query(TProductPrice entity) {
		return this.productPriceDao.query(entity);
	}

	public TProductPrice query(Serializable id) {
		return this.productPriceDao.query(id);
	}

	public List<TProductPrice> queryForList(TProductPrice entity) {
		return this.productPriceDao.queryForList(entity);
	}

	public List<TProductPrice> queryForList(Map<String, ?> args) {
		return this.productPriceDao.queryForList(args);
	}

	public QueryContext<TProductPrice> queryListForPagination(
			QueryContext<TProductPrice> qContext) {
		return this.productPriceDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.product.IProductPriceService#queryTodayPrice(java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> queryTodayPrice(String area, String pcode) {
		return this.productPriceDao.queryTodayPrice(area, pcode);
	}
	
	@Override
	public List<TProductPrice> queryListByDay(TProductPrice entity, Date day) {
		entity.setDatepoint(null);
		return this.productPriceDao.queryListByDay(entity, day);
	}

}
