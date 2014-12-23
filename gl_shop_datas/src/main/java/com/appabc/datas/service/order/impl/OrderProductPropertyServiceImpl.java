/**
 *
 */
package com.appabc.datas.service.order.impl;

import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.order.IOrderProductPropertyDao;
import com.appabc.datas.service.order.IOrderProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 交易中的商品属性SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午11:30:56
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrderProductPropertyServiceImpl implements IOrderProductPropertyService{
	
	@Autowired
	private IOrderProductPropertyDao orderProductPropertyDao;

	public void add(TOrderProductProperty entity) {
		this.orderProductPropertyDao.save(entity);
	}

	public void modify(TOrderProductProperty entity) {
		this.orderProductPropertyDao.update(entity);
	}

	public void delete(TOrderProductProperty entity) {
		this.orderProductPropertyDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.orderProductPropertyDao.delete(id);
	}

	public TOrderProductProperty query(TOrderProductProperty entity) {
		return this.orderProductPropertyDao.query(entity);
	}

	public TOrderProductProperty query(Serializable id) {
		return this.orderProductPropertyDao.query(id);
	}

	public List<TOrderProductProperty> queryForList(TOrderProductProperty entity) {
		return this.orderProductPropertyDao.queryForList(entity);
	}

	public List<TOrderProductProperty> queryForList(Map<String, ?> args) {
		return this.orderProductPropertyDao.queryForList(args);
	}

	public QueryContext<TOrderProductProperty> queryListForPagination(
			QueryContext<TOrderProductProperty> qContext) {
		return this.orderProductPropertyDao.queryListForPagination(qContext);
	}

}
