package com.appabc.datas.dao.contract;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.dao.IBaseDao;

import java.util.List;

/**
 * @Description : 合同操作记录信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午2:36:40
 */

public interface IContractOperationDAO extends IBaseDao<TOrderOperations> {
	
	/**
	 * description 根据合同ID和类型查询操作记录信息
	 * @param contractId,type
	 * @return List<TOrderOperations>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForList(String contractId, String type);
	
	/**
	 * description 根据合同ID查询操作记录
	 * @param contractId
	 * @return List<TOrderOperations>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForList(String contractId);
	
	/**
	 * @description 根据合同ID和操作ID查询操作记录
	 * @param contractId,operator
	 * @return List<TOrderOperations>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderOperations> queryForListWithOIDAndOper(String contractId,String operator);
	
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
	 * @description 根据合同ID和企业ID查询合同的付款记录
	 * @param oid,cid
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	boolean getIsContractPayRecord(String oid,String cid);
	
	/**
	 * @description 根据合同ID和合同的生命周期查询合同的操作记录
	 * @param TOrderOperations,lifeCycle
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderOperations queryOperationWithLifeCycleAndOid(String oid,ContractLifeCycle lifeCycle);
	
}
