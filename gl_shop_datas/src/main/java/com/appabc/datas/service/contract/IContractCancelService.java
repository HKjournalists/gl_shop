package com.appabc.datas.service.contract;

import java.util.List;

import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.common.base.exception.BaseException;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午3:01:12
 */

public interface IContractCancelService extends IBaseService<TOrderCancel> {

	/*最终取消合同*/
	void singleCancelContract(String contractId,String userId,String userName) throws BaseException;
	
	/**
	 * description : 双方多次取消合同
	 * @param contractId,userId,userName
	 * @return 0是一方确认取消合同, 1是双方确认取消合同
	 * @since 
	 * @author Administrator
	 * */
	int multiCancelContract(String contractId,String userId,String userName) throws BaseException;
	
	/*通过合同编号获取取消列表信息*/
	List<TOrderCancel> getCancelContractListByOID(String oid);
	
}
