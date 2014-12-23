package com.appabc.datas.service.contract;

import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午2:58:52
 */

public interface IContractDisPriceService extends IBaseService<TOrderDisPrice> {

	/**
	 * @Description 合同议价信息
	 * @param contractId,operatorName,bean
	 * @return void
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	void validateGoodsDisPrice(String contractId,String operatorName,TOrderDisPrice bean) throws ServiceException;
	
	/**
	 * @Description 获取合同历史议价记录，包括操作信息和议价信息
	 * @param contractId,operateId,disPriceId,disPriceType
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> getGoodsDisPriceHisList(String contractId,String operateId, String disPriceId,String disPriceType);
	
}
