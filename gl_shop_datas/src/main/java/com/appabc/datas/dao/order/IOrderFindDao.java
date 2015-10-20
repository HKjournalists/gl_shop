/**
 *
 */
package com.appabc.datas.dao.order;

import java.util.List;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindQueryParamsBean;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
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
	 * 根据状态查询父询单信息
	 * @param ose
	 * @param oose
	 * @throws null
	 * @return List<TOrderFind>
	 */
	public List<TOrderFind> queryParentOrderFindByStatusAndOverallStatus(OrderStatusEnum ose,OrderOverallStatusEnum oose);
	
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
	 * @param qContext
	 * @param isApply
	 * @param isOrderFid
	 * @param isIdentity
	 * @return
	 */
	public QueryContext<TOrderFind> queryMatchingObjectByCidForPagination(QueryContext<TOrderFind> qContext, boolean isApply, boolean isOrderFid, boolean isIdentity);
	
	
	/**
	 * 找买找卖(供求)列表，分页查询
	 * @param qContext
	 * @return
	 */
	public QueryContext<TOrderFind> queryOrderListForPagination(
			QueryContext<TOrderFind> qContext, OrderFindQueryParamsBean ofqParam);

	/**
	 * 新发布的询单列表
	 * @return
	 */
	public List<TOrderFind> queryNewListForTask();

	/**
	 * 后台任务列表中无效的询单
	 * @return
	 */
	public List<TOrderFind> queryInvalidListForTask();

	/**
	 * 撮合次数增加1
	 * @param fid 询单ID
	 * @return
	 */
	int addMatchingNum(String fid);

	/**
	 * 分页查询已代发供求列表
	 * @param qContext
	 * @return
	 */
	QueryContext<TOrderFind> queryParentOrderFindOfInsteadListForPagination(
			QueryContext<TOrderFind> qContext);
	
	public int queryCount(TOrderFind entity);
	
}
