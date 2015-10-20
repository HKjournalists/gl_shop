package com.appabc.datas.service.order;

import java.util.List;

import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年4月30日 下午5:24:18
 */

public interface IOrderFindMatchService extends IBaseService<TOrderFindMatch> {
	
	/**
	 * @Description : 保存撮合信息的保存列表
	 * @param bean,target,opType,operator
	 * @return TOrderFindMatch
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,String target,OrderFindMatchOpTypeEnum opType,String operator) throws ServiceException;
	
	/**
	 * @Description : 保存撮合信息的保存列表
	 * @param bean,target,opType,operator,strs[0:title,1:tfid]
	 * @return TOrderFindMatch
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,String target,OrderFindMatchOpTypeEnum opType,String operator,String...strs) throws ServiceException;
	
	/**
	 * @Description : 保存撮合信息的保存列表
	 * @param bean,target,opType,status,operator
	 * @return TOrderFindMatch
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,String target,OrderFindMatchOpTypeEnum opType,OrderFindMatchStatusEnum status,String operator) throws ServiceException;
	
	/**
	 * @Description : 保存撮合信息的保存列表
	 * @param bean,target,opType,status,operator,strs[0:title,1:tfid]
	 * @return TOrderFindMatch
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderFindMatch saveOrderFindMatchInfo(OrderFindAllBean bean,String target,OrderFindMatchOpTypeEnum opType,OrderFindMatchStatusEnum status,String operator,String...strs) throws ServiceException;
	
	/**
	 * @Description : 保存或者更新撮合信息的保存列表
	 * @param bean,target,opType,status,operator,strs[0:title,1:tfid]
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void updateFindMatchInfo(OrderFindAllBean bean,TOrderFindMatch tofm) throws ServiceException;
	
	/**
	 * @Description : 根据状态获取撮合保存信息列表
	 * @param status
	 * @return List<TOrderFindMatch>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderFindMatch> findOrderFindMatchInfo(String owner,OrderFindMatchStatusEnum status);
	
	/**
	 * @Description : 根据状态获取撮合保存信息列表
	 * @param owner,status
	 * @return List<TOrderFindMatchEx>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderFindMatchEx> findOrderFindMatchExInfo(String owner,OrderFindMatchStatusEnum status);
	
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
	 * @Description : 根据父询单ID回滚保存信息和询单的信息
	 * @param parentFid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void rollbackOrderFindMatchInfoByFid(String parentFid) throws ServiceException;
	
	/**
	 * @Description : 通过合同记录的询单ID找到父询单，看看是否有保存记录信息，有就 直接回滚.
	 * @param parentFid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void rollbackOrderFindInfoWithContract(String contractFid) throws ServiceException;
	
	/**
	 * @Description : 根据保存列表ID回滚保存信息和询单的信息
	 * @param rid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void rollbackOrderFindMatchInfoByRid(String rid) throws ServiceException;
	
	/**
	 * @Description : 根据父询单ID批量失效保存信息列表
	 * @param parentFid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void vitiateOrderFindMatchInfoByFid(String parentFid) throws ServiceException;
	
	/**
	 * @Description : 根据保存列表ID单个失效保存信息列表
	 * @param rid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void vitiateOrderFindMatchInfoByRid(String rid) throws ServiceException;
	
	/**
	 * @Description : 根据保存列表ID取消单个保存信息
	 * @param rid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void cancelOrderFindMatchInfoByRid(String rid) throws ServiceException;
	
	/**
	 * @Description : 根据父询单ID查询是否有保存列表记录
	 * @param rid
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	boolean hasOrderFindMatchInfoWithParentFid(String parentFid);
	
}
