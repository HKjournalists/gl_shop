/**  
 * com.appabc.pay.service.IPassbookDrawService.java  
 *   
 * 2014年9月17日 上午11:26:32  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local;

import java.util.List;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookDrawEx;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:26:32
 */

public interface IPassbookDrawService extends IBaseService<TPassbookDraw> {
	
	List<TPassbookDraw> getTPassbookDrawByPassId(String passId);
	
	QueryContext<TPassbookDrawEx> extractCashRequestListEx(QueryContext<TPassbookDrawEx> qContext);
	
}
