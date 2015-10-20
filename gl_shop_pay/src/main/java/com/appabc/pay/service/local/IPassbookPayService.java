/**  
 * com.appabc.pay.service.IPassbookPayService.java  
 *   
 * 2014年9月17日 上午11:27:58  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local;

import java.util.List;

import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.service.IBaseService;
import com.appabc.pay.bean.TPassbookPay;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:27:58
 */

public interface IPassbookPayService extends IBaseService<TPassbookPay> {

	List<TPassbookPay> getPayRecordListWithOid(String cid, PurseType type, String oid, PayDirection payDirection); 
	
	List<TPassbookPay> getPayListWithParams(String cid, PurseType pType,String oid, TradeType tType, PayDirection direction);
	
}
