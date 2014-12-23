/**
 *
 */
package com.appabc.datas.service.order.impl;

import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.order.IOrderAddressDao;
import com.appabc.datas.service.order.IOrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private IOrderAddressDao orderAddressDao;

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
		return this.orderAddressDao.query(id);
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

}
