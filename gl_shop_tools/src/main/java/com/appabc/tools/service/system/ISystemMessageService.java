package com.appabc.tools.service.system;

import com.appabc.bean.bo.SystemMessageEx;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月22日 上午10:51:07
 */

public interface ISystemMessageService extends IBaseService<TSystemMessage> {
	
	/**
	 * 获取企业未读消息的数
	 * @param cid
	 * @return
	 */
	public int getUnreadMsgCountByCid(String cid);
	
	/**  
	 * queryMessageExListForPagination (根据查询对象查询实体列表从数据库中)  
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
