/**
 *
 */
package com.appabc.datas.service.product.impl;

import com.appabc.bean.pvo.TProductPriceHope;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.product.IProductPriceHopeDao;
import com.appabc.datas.service.product.IProductPriceHopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 商品预测SERVICE实现类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月28日 上午10:03:55
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProductPriceHopeServiceImpl implements IProductPriceHopeService{
	
	@Autowired
	private IProductPriceHopeDao productPriceHopeDao;
	
	public void add(TProductPriceHope entity) {
		this.productPriceHopeDao.save(entity);
	}

	public void modify(TProductPriceHope entity) {
		this.productPriceHopeDao.update(entity);
	}

	public void delete(TProductPriceHope entity) {
		this.productPriceHopeDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.productPriceHopeDao.delete(id);
	}

	public TProductPriceHope query(TProductPriceHope entity) {
		return this.productPriceHopeDao.query(entity);
	}

	public TProductPriceHope query(Serializable id) {
		return this.productPriceHopeDao.query(id);
	}

	public List<TProductPriceHope> queryForList(TProductPriceHope entity) {
		return this.productPriceHopeDao.queryForList(entity);
	}

	public List<TProductPriceHope> queryForList(Map<String, ?> args) {
		return this.productPriceHopeDao.queryForList(args);
	}

	public QueryContext<TProductPriceHope> queryListForPagination(
			QueryContext<TProductPriceHope> qContext) {
		return this.productPriceHopeDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.product.IProductPriceHopeService#queryTodayPrice(java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> queryHopePrice(String area, String pcode) {
		return this.productPriceHopeDao.queryHopePrice(area, pcode);
	}

}
