/**  
 * com.appabc.pay.service.local.IPayThirdInfoService.java  
 *   
 * 2015年3月3日 上午10:28:21  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local;

import java.util.List;

import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.common.base.service.IBaseService;
import com.appabc.pay.bean.TPayThirdOrgInfo;
import com.appabc.pay.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 上午10:28:21
 */

public interface IPayThirdInfoService extends IBaseService<TPayThirdOrgInfo> {

	TPayThirdOrgInfo savePayThirdOrgInfo(String cid,double balance,PurseType pt);
	
	TPayThirdOrgInfo getUnionPayTnOrderId(String cid,double balance,PurseType pt);
	
	Object getUnionPayTradeStatus(String oid);
	
	@Deprecated
	void reportToUnionPayTradeResult(String oid) throws ServiceException;
	
	List<TPayThirdOrgInfo> queryForListWithStatus(TradeStatus status);
	
}
