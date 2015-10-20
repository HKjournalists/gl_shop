/**  
 * com.appabc.pay.dao.IPassbookPayDAO.java  
 *   
 * 2014年9月17日 上午11:14:46  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao;

import java.util.List;

import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.dao.IBaseDao;
import com.appabc.pay.bean.TPassbookPay;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:14:46
 */

public interface IPassbookPayDAO extends IBaseDao<TPassbookPay> {

	List<TPassbookPay> getPayRecordListWithOid(String cid, PurseType type, String oid, PayDirection payDirection);
	
	List<TPassbookPay> queryPayListWithParams(String cid,PurseType pType, String oid, TradeType tType, PayDirection direction);
	
}
