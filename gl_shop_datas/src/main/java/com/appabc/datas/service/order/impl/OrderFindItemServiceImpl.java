/**
 *
 */
package com.appabc.datas.service.order.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.OrderFindInfo.OrderItemEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.order.IOrderFindItemDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.datas.service.order.IOrderFindService;

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
	@Autowired
	private IOrderFindService orderFindService;

	public void add(TOrderFindItem entity) {
		this.orderFindItemDao.save(entity);
	}

	public void tradeApplication(TOrderFindItem entity) throws ServiceException {
		if(entity == null){
			throw new ServiceException("数据异常");
		}
		if(StringUtils.isEmpty(entity.getFid())){
			TOrderFind of = orderFindService.query(entity.getFid());
			if(of != null && StringUtils.isNotEmpty(of.getCid()) && of.getCid().equals(entity.getUpdater())){
				throw new ServiceException("不能与自己进行交易");
			}
		}
		
		entity.setStatus(OrderItemEnum.ITEM_STATUS_APPLY);
		List<TOrderFindItem> ofiList = this.orderFindItemDao.queryForList(entity);
		if(ofiList==null || ofiList.size()==0){
			entity.setCreatetime(new Date());
			this.orderFindItemDao.save(entity);
		}else{
			logger.info("不能重复申请，updater:"+entity.getUpdater()+"\tfid:"+entity.getFid());
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_ITEM_REPEAT_REQUEST_ERROR,"不能重复申请");
		}

	}

	public void modify(TOrderFindItem entity) {
		this.orderFindItemDao.update(entity);
	}

	public void delete(TOrderFindItem entity) {
		this.orderFindItemDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.orderFindItemDao.delete(id);
	}

	public TOrderFindItem query(TOrderFindItem entity) {
		return this.orderFindItemDao.query(entity);
	}

	public TOrderFindItem query(Serializable id) {
		return this.orderFindItemDao.query(id);
	}

	public List<TOrderFindItem> queryForList(TOrderFindItem entity) {
		return this.orderFindItemDao.queryForList(entity);
	}

	public List<TOrderFindItem> queryForList(Map<String, ?> args) {
		return this.orderFindItemDao.queryForList(args);
	}

	public QueryContext<TOrderFindItem> queryListForPagination(
			QueryContext<TOrderFindItem> qContext) {
		return this.orderFindItemDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindItemService#getIsApplyByCid(java.lang.String, java.lang.String)
	 */
	@Override
	public int getIsApplyByCid(String updater, String fid) {
		TOrderFindItem ofi = new TOrderFindItem();
		ofi.setFid(fid);
		ofi.setUpdater(updater);

		List<TOrderFindItem> ofiList = this.orderFindItemDao.queryForList(ofi);
		if(ofiList != null) return ofiList.size();

		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindItemService#countByFid(java.lang.String)
	 */
	@Override
	public int countByFid(String fid) {
		if(StringUtils.isEmpty(fid)) return 0;
		
		return this.orderFindItemDao.countByFid(fid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindItemService#queryOrderFindItemListByFid(java.lang.String)  
	 */
	@Override
	public List<TOrderFindItem> queryOrderFindItemListByFid(String fid) {
		if(StringUtils.isEmpty(fid)) return null;
		return orderFindItemDao.queryOrderFindItemListByFid(fid);
	}

}
