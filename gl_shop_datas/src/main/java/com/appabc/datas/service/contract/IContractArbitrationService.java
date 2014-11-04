package com.appabc.datas.service.contract;

import java.util.List;

import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午3:04:06
 */

public interface IContractArbitrationService extends IBaseService<TOrderArbitration> {

	/*合同仲裁操作*/
	TOrderArbitration toContractArbitration(String contractId,String operator,String operatorName);
	
	List<?> getContractArbitrationHistroy(String contractId);
	
}
