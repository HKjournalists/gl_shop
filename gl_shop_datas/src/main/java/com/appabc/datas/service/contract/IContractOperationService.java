package com.appabc.datas.service.contract;

import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午2:57:38
 */

public interface IContractOperationService extends IBaseService<TOrderOperations> {
	
	/**
	 * @Description 确认合同信息
	 * @param contractId,type,operator,result,pid
	 * @return TOrderOperations
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderOperations applyOrNotGoodsInfo(String contractId,String type,String operator,String result,String pid);
	
	/**
	 * @Description 验收通过和同意的接口
	 * @param contractId,operType,pid,operator,operatorName
	 * @return TOrderOperations
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderOperations applyOrPassGoodsInfo(String contractId,String operType,String pid,String operator,String operatorName) throws ServiceException;
	
	/**
	 * @Description 确认卸货信息
	 * @param contractId,confirmer,confirmerName
	 * @return TOrderOperations
	 * @since 1.0 
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void confirmUninstallGoods(String contractId,String confirmer,String confirmerName) throws ServiceException;
	
	/**
	 * @Description 获取合同变更历史
	 * @param contractId
	 * @return List<?>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<?> getContractChangeHistory(String contractId);
	
	/**
	 * @Description 获取合同操作记录
	 * @param contractId,type
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForList(String contractId, String type);
	
	/**
	 * @Description 获取合同操作记录
	 * @param contractId,operator
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForListWithOIDAndOper(String contractId,String operator);
	
	/**
	 * @Description 获取合同操作记录
	 * @param contractId
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForList(String contractId);
	
}
