/**
 *
 */
package com.appabc.datas.service.order;

import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 询单交易申请记录SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午11:55:32
 */
public interface IOrderFindItemService extends IBaseService<TOrderFindItem> {
	
	/**
	 * 交易申请
	 * @param entity
	 * @throws ServiceException
	 */
	public void tradeApplication(TOrderFindItem entity) throws ServiceException;
	
	/**
	 * 获取是否已申请交易过
	 * @param cid
	 * @param fid
	 * @return
	 */
	public int getIsApplyByCid(String cid, String fid);

}
