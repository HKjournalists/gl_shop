/**
 *
 */
package com.appabc.datas.service.product.impl;

import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.product.IProductPropertyDao;
import com.appabc.datas.service.product.IProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 商品属性SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午9:28:44
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProductPropertyServiceImpl implements IProductPropertyService{
	
	@Autowired
	private IProductPropertyDao productPropertyDao;

	public void add(TProductProperty entity) {
		this.productPropertyDao.save(entity);
	}

	public void modify(TProductProperty entity) {
		this.productPropertyDao.update(entity);
	}

	public void delete(TProductProperty entity) {
		this.productPropertyDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.productPropertyDao.delete(id);
	}

	public TProductProperty query(TProductProperty entity) {
		return this.productPropertyDao.query(entity);
	}

	public TProductProperty query(Serializable id) {
		return this.productPropertyDao.query(id);
	}

	public List<TProductProperty> queryForList(TProductProperty entity) {
		return this.productPropertyDao.queryForList(entity);
	}

	public List<TProductProperty> queryForList(Map<String, ?> args) {
		return this.productPropertyDao.queryForList(args);
	}

	public QueryContext<TProductProperty> queryListForPagination(
			QueryContext<TProductProperty> qContext) {
		return this.productPropertyDao.queryListForPagination(qContext);
	}

}
