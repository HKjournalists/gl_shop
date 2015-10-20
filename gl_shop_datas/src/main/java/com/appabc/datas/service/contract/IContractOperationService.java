package com.appabc.datas.service.contract;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
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

public interface IContractOperationService extends IContractBaseService<TOrderOperations> {
	
	/**
	 * @Description 确认合同信息
	 * @param contractId,type,operator,result,pid
	 * @return TOrderOperations
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderOperations applyOrNotGoodsInfo(String oid,String type,String operator,String result,String pid);
	
	/**
	 * @Description 验收通过和同意的接口
	 * @param contractId,operType,pid,operator,operatorName
	 * @return TOrderOperations
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderOperations applyOrPassGoodsInfo(String oid,String operType,String pid,String cid,String cname) throws ServiceException;
	
	/**
	 * @Description 确认卸货信息
	 * @param contractId,confirmer,confirmerName
	 * @return TOrderOperations
	 * @since 1.0 
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void confirmUninstallGoods(String oid,String cid,String cname) throws ServiceException;
	
	/**
	 * @Description 获取合同变更历史
	 * @param contractId
	 * @return List<?>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<?> getContractChangeHistory(String oid);
	
	/**
	 * @Description 获取合同操作记录
	 * @param contractId,type
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForList(String oid, String type);
	
	/**
	 * @Description 获取合同操作记录
	 * @param contractId,operator
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForListWithOIDAndOper(String oid,String cid);
	
	/**
	 * @Description 获取合同操作记录
	 * @param oid,cid,type,lifeCycle
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForListWithOidAndOperAndTypeAndOrderLifeCycle(String oid,String cid,ContractOperateType type,ContractLifeCycle lifeCycle);
	
	/**
	 * @Description 获取合同操作记录
	 * @param oid,cid,type
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderOperations queryForListWithOidAndCidAndType(String oid,String cid,ContractOperateType type);
	
	/**
	 * @Description 获取合同操作记录
	 * @param contractId
	 * @return List<TOrderOperations>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForList(String oid);
	
	/**
	 * @description 根据合同ID和企业ID查询合同的付款记录
	 * @param oid,cid
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	boolean getIsContractPayRecord(String oid,String cid);
	
	/**
	 * @description JOB自动取消起草超时确认
	 * @param oid,cid
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void jobAutoDraftConfirmTimeoutFinish(TOrderInfo bean,String cid) throws ServiceException;
	
	void jobAutoConfirmGoodsInfoContract(TOrderInfo bean,String cid) throws ServiceException;
	
	void jobAutoEvaluationContract(TOrderInfo bean,String cid) throws ServiceException;
	
	void jobAutoTimeoutContractPayFundFinish(TOrderInfo bean,String cid,String cname) throws ServiceException;
	
}
