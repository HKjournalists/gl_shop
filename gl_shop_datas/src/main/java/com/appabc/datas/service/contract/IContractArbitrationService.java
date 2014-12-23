package com.appabc.datas.service.contract;

import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午3:04:06
 */

public interface IContractArbitrationService extends IBaseService<TOrderArbitration> {

	/**
	 * @Description 合同仲裁操作
	 * @param contractId,operator,operatorName
	 * @return TOrderArbitration
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderArbitration toContractArbitration(String contractId,String operator,String operatorName) throws ServiceException;
	
	/**
	 * @Description 获取合同仲裁历史
	 * @param contractId
	 * @return List<?>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<?> getContractArbitrationHistroy(String contractId);
	
}
