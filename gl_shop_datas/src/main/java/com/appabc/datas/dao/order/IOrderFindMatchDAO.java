package com.appabc.datas.dao.order;

import java.util.List;

import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年4月30日 下午4:43:53
 */

public interface IOrderFindMatchDAO extends IBaseDao<TOrderFindMatch> {

	/**
	 * @description 根据状态和所有者查询询单匹配信息
	 * @param owner,status
	 * @return List<TOrderFindMatch>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderFindMatch> queryOrderFindMatchInfoWithStatus(String owner,OrderFindMatchStatusEnum status);
	
	/**
	 * @description 根据状态和所有者查询询单匹配信息
	 * @param owner,status
	 * @return List<TOrderFindMatch>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderFindMatchEx> queryOrderFindMatchExInfoWithStatus(String owner,OrderFindMatchStatusEnum status);
	
	/**
	 * @Description : 分页查询撮合保存信息列表
	 * @param qContext
	 * @return QueryContext<TOrderFindMatchEx>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<TOrderFindMatchEx> findOrderFindMatchExInfoForPagination(QueryContext<TOrderFindMatchEx> qContext);
	
	/**
	 * @description 根据父询单ID和状态更新匹配信息
	 * @param parentFId,status
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void updateOrderFindMatchStatusByPFid(String parentFId,OrderFindMatchStatusEnum status) throws ServiceException;
	
	/**
	 * @description 根据rid和状态更新匹配信息
	 * @param rid,status
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void updateOrderFindMatchStatusByRid(String rid,OrderFindMatchStatusEnum status) throws ServiceException;
	
}
