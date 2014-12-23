/**
 *
 */
package com.appabc.datas.dao.order;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 询单（供求信息）DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月10日 下午3:07:45
 */
public interface IOrderFindDao extends IBaseDao<TOrderFind>{
	
	/**
	 * 根据询单ID获取详情信息，包括商品信息和卸货地址
	 * @param fid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid);

	/**
	 * @param cid
	 * @return
	 */
	public int countByCid(String cid);

	/**
	 * 更新当前ID下所有子询单的状态为失效
	 * @param parentId
	 * @param operator
	 * @throws ServiceException
	 */
	public boolean updateChildOrderFindCloseInvalidByParentId(String parentId,String operator);
	
	/**
	 * 询单自动匹配列表
	 * @param fid
	 * @return
	 */
	public QueryContext<TOrderFind> queryMatchingObjectByCidForPagination(QueryContext<TOrderFind> qContext);
	
}
