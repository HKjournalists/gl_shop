package com.glshop.platform.api.message.data;

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
public class GetMessageInfoResult extends CommonResult {

	/**
	 * 消息详情
	 */
	public MessageInfoModel data;

	@Override
	public String toString() {
		return super.toString() + ", data = " + data;
	}

}
