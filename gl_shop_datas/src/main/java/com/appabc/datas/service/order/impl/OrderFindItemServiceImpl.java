/**
 *
 */
package com.appabc.datas.service.order.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.order.IOrderFindItemDao;
import com.appabc.datas.enums.OrderFindInfo.OrderItemEnum;
import com.appabc.datas.service.order.IOrderFindItemService;

/**
 * @Description : 询单交易申请SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午11:55:49
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrderFindItemServiceImpl implements IOrderFindItemService {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOrderFindItemDao orderFindItemDao;

	public void add(TOrderFindItem entity) {
		entity.setStatus(OrderItemEnum.ITEM_STATUS_APPLY.getVal());
		List<TOrderFindItem> ofiList = this.orderFindItemDao.queryForList(entity);
		if(ofiList==null || ofiList.size()==0){
			entity.setCreatetime(new Date());
			this.orderFindItemDao.save(entity);
		}else{
			logger.info("不能重复申请，updater:"+entity.getUpdater()+"\tfid:"+entity.getFid());
		}
		
	}

	public void modify(TOrderFindItem entity) {
		this.orderFindItemDao.update(entity);
	}

	public void delete(TOrderFindItem entity) {
	}

	public void delete(Serializable id) {
		this.orderFindItemDao.delete(id);
	}

	public TOrderFindItem query(TOrderFindItem entity) {
		return null;
	}

	public TOrderFindItem query(Serializable id) {
		return this.orderFindItemDao.query(id);
	}

	public List<TOrderFindItem> queryForList(TOrderFindItem entity) {
		return this.orderFindItemDao.queryForList(entity);
	}

	public List<TOrderFindItem> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TOrderFindItem> queryListForPagination(
			QueryContext<TOrderFindItem> qContext) {
		return null;
	}

}
