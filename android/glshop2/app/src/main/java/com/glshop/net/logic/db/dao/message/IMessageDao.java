package com.glshop.net.logic.db.dao.message;

import java.util.List;

import android.content.Context;

import com.glshop.platform.api.message.data.model.MessageInfoModel;

/**
 * @Description : 消息中心管理接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:02:02
 */
public interface IMessageDao {

	/**
	 * 获取未读的消息数目
	 * @param account
	 */
	int queryUnreadedNumber(Context context, String account);

	/**
	 * 查询所有消息列表
	 * @param context
	 * @param account
	 * @return
	 */
	List<MessageInfoModel> queryMessageInfo(Context context, String account);

	/**
	 * 获取未上报的已读消息列表
	 * @param account
	 */
	List<MessageInfoModel> queryUnreportedMessageList(Context context, String account);

	/**
	 * 添加消息数据
	 * @param context
	 * @param info
	 * @return
	 */
	long insertMessageInfo(Context context, MessageInfoModel info);

	/**
	 * 批量添加消息数据
	 * @param context
	 * @param info
	 * @return
	 */
	boolean insertMessageInfo(Context context, List<MessageInfoModel> list);

	/**
	 * 更新消息数据
	 * @param context
	 * @param info
	 * @return
	 */
	boolean updateMessageInfo(Context context, MessageInfoModel info);

	/**
	 * 批量更新消息数据
	 * @param context
	 * @param info
	 * @return
	 */
	boolean updateMessageInfo(Context context, List<MessageInfoModel> list);

	/**
	 * 删除消息数据
	 * @param context
	 * @param id
	 * @return
	 */
	boolean deleteMessageInfo(Context context, String id);

}
