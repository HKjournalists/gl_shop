package com.appabc.datas.dao.system;

import java.util.Date;

import com.appabc.bean.pvo.TSystemLog;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午2:40:46
 */

public interface ISystemLogDAO extends IBaseDao<TSystemLog> {
	
	/**
	 * 统计某一天有多少用户登录
	 * @param date
	 * @return
	 */
	public int queryCountLoginUserOfDate(Date date);
	
	
	/**
	 * 获取最近的一次记录
	 * @param entity
	 * @return
	 */
	public TSystemLog queryRecentOfOneRecord(TSystemLog entity);

}
