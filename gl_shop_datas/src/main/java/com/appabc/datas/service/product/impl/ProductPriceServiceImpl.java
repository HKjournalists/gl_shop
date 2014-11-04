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

import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.product.IProductPriceDao;
import com.appabc.datas.service.product.IProductPriceService;

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
	}

	public void delete(Serializable id) {
		this.productPriceDao.delete(id);
	}

	public TProductPrice query(TProductPrice entity) {
		return null;
	}

	public TProductPrice query(Serializable id) {
		return this.productPriceDao.query(id);
	}

	public List<TProductPrice> queryForList(TProductPrice entity) {
		return this.productPriceDao.queryForList(entity);
	}

	public List<TProductPrice> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TProductPrice> queryListForPagination(
			QueryContext<TProductPrice> qContext) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.product.IProductPriceService#queryTodayPrice(java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> queryTodayPrice(String area, String pcode) {
		return this.productPriceDao.queryTodayPrice(area, pcode);
	}

}
