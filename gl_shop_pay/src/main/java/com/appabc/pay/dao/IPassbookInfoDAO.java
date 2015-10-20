/**  
 * com.appabc.pay.dao.IPassbookInfoDAO.java  
 *   
 * 2014年9月17日 上午10:24:30  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao;

import java.util.List;
import com.appabc.common.base.dao.IBaseDao;
import com.appabc.pay.bean.TPassbookInfo;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午10:24:30
 */

public interface IPassbookInfoDAO extends IBaseDao<TPassbookInfo> {

	/**
	 * 统计已缴纳保证金用户个数
	 * @return
	 */
	public int queryCountAllUsersGuaranty();
	
	/**
	 * 查询保证金小于3000并且不在任务列表中的用户
	 * @return
	 */
	List<TPassbookInfo> queryNewListForTask();
	/**
	 *  查询存在后台任务列表中，保证金已经超过3000的用户
	 */
	List<TPassbookInfo> queryInvalidListForTask();

}
