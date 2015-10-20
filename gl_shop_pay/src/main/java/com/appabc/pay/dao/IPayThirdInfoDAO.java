/**  
 * com.appabc.pay.dao.IPayThirdInfoDAO.java  
 *   
 * 2015年3月2日 下午3:13:34  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao;

import java.util.List;

import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.common.base.dao.IBaseDao;
import com.appabc.pay.bean.TPayThirdOrgInfo;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月2日 下午3:13:34
 */

public interface IPayThirdInfoDAO extends IBaseDao<TPayThirdOrgInfo> {
	
	List<TPayThirdOrgInfo> queryForListWithStatus(TradeStatus status);
	
}
