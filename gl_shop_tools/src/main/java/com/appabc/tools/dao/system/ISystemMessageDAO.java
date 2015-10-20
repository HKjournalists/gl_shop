package com.appabc.tools.dao.system;

import com.appabc.bean.bo.SystemMessageEx;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午2:38:02
 */

public interface ISystemMessageDAO extends IBaseDao<TSystemMessage> {

	/**
	 * 获取消息数量
	 * @param entity
	 * @return
	 */
	int getCountByEntity(TSystemMessage entity);
	
	/**  
	 * queryMessageExListForPagination  
	 * @param qContext  
	 * @author Bill huang 
	 * @return QueryContext<SystemMessageEx>  
	 * @exception   
	 * @since  1.0.0  
	 */
	public QueryContext<SystemMessageEx> queryMessageExListForPagination(QueryContext<SystemMessageEx> qContext);

	/**
	 * 根据消息类型获取企业未删除消息列表
	 * @param cid
	 * @return
	 */
	public QueryContext<TSystemMessage> queryListByTypeForPagination(QueryContext<TSystemMessage> qContext);
}
