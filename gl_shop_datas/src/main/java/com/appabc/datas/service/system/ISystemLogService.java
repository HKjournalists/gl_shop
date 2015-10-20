package com.appabc.datas.service.system;

import java.util.Date;

import com.appabc.bean.pvo.TSystemLog;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月22日 上午10:52:08
 */

public interface ISystemLogService extends IBaseService<TSystemLog> {

	/**
	 * 添加日志到缓存
	 * @param entity
	 */
	void addToCache(TSystemLog entity);

	/**
	 * 保存缓存中的日志
	 */
	void saveTheLogsInTheCache();

	/**
	 * 统计某一天有多少用户登录
	 * @param date
	 * @return
	 */
	public int queryCountLoginUserOfDate(Date date);
	
	/**
	 * 获取最近一次记录( ORDER BY CREATETIME DESC LIMIT 0,1)
	 * @param entity
	 * @return
	 */
	public TSystemLog queryRecentOfOneRecord(TSystemLog entity);

	/**
	 * 获取用户最后一次登录时间
	 * @param username
	 * @return
	 */
	public Date queryLastLoginTimeByUser(String username);
	
}
