package com.glshop.net.logic.message;

import java.util.List;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 消息中心业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:15
 */
public interface IMessageLogic extends ILogic {

	/**
	 * 获取未读的消息数目
	 * @param account
	 * @param companyId
	 */
	int getUnreadedNumber(String account, String companyId);

	/**
	 * 获取本地未读的消息数目
	 * @param account
	 * @param companyId
	 */
	int getUnreadedNumberFromLocal(String account);

	/**
	 * 获取云端未读的消息数目
	 * @param account
	 * @param companyId
	 */
	void getUnreadedNumberFromServer(String companyId);

	/**
	 * 获取消息列表
	 * @param companyId
	 */
	List<MessageInfoModel> getMessageList(String account, String companyId);

	/**
	 * 获取本地消息列表
	 * @param companyId
	 */
	List<MessageInfoModel> getMessageListFromLocal(String account);

	/**
	 * 获取云端消息列表
	 * @param companyId
	 */
	void getMessageListFromServer(String companyId, int pageIndex, int pageSize, DataReqType reqType);

	/**
	 * 获取消息详情
	 * @param companyId
	 */
	void getMessageInfo(String id);

	/**
	 * 获取未上报的已读消息列表
	 * @param account
	 */
	List<MessageInfoModel> getUnreportedMessageList(String account);

	/**
	 * 更新消息已读状态
	 * @param account
	 */
	boolean updateMesageStatus(MessageInfoModel info);

	/**
	 * 批量更新消息已读状态
	 * @param account
	 */
	boolean updateMesageStatus(List<MessageInfoModel> list);

	/**
	 * 上报已读消息
	 * @param account
	 */
	void reportMessage(MessageInfoModel info);

	/**
	 * 上报已读消息列表
	 * @param account
	 */
	void reportMessage(List<MessageInfoModel> list);

}
