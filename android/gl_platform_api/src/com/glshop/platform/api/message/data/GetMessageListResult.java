package com.glshop.platform.api.message.data;

import java.util.List;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.message.data.model.MessageInfoModel;

/**
 * @Description : 获取消息列表结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetMessageListResult extends CommonResult {

	/**
	 * 消息列表
	 */
	public List<MessageInfoModel> datas;

	/**
	 * 未读消息总数
	 */
	public int unreadTotalSize;

	/**
	 * 总数
	 */
	public int totalSize;

	@Override
	public String toString() {
		return super.toString() + ", unreadTotalSize = " + unreadTotalSize + ", TotalCount = " + totalSize + ", datas = " + datas;
	}

}
