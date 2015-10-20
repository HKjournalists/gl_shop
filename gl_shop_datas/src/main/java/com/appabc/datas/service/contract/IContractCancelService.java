package com.appabc.datas.service.contract;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.exception.BaseException;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午3:01:12
 */

public interface IContractCancelService extends IContractBaseService<TOrderCancel> {

	/**
	 * @Description 取消起草合同
	 * @param oid,cid,cname
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void cancelDraftContract(String oid,String cid,String cname,ContractOperateType operateType) throws ServiceException;
	
	/**
	 * @Description 最终取消合同
	 * @param contractId,userId,userName
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void singleCancelContract(String oid,String cid,String cname) throws ServiceException;
	
	/**
	 * @Description 双方多次取消合同
	 * @param contractId,userId,userName
	 * @return 0是一方确认取消合同, 1是双方确认取消合同
	 * @since 1.0
	 * @throws BaseException
	 * @author Administrator
	 * */
	int multiCancelContract(String oid,String cid,String cname) throws ServiceException;
	
	/**
	 * @Description 通过合同编号获取取消列表信息
	 * @param oid
	 * @return List<TOrderCancel>
	 * @since 1.0
	 * @author Bill Huang
	 * */
	List<TOrderCancel> getCancelContractListByOID(String oid);
	
	/**
	 * @Description JOB自动回滚取消合同的状态
	 * @param oid
	 * @return void
	 * @since 1.0
	 * @author Bill Huang
	 * */
	void jobAutoRollbackCancelContract(TOrderInfo bean,String cid,ContractLifeCycle lastClc) throws ServiceException;
	
}
