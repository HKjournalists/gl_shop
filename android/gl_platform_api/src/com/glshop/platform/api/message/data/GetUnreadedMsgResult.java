package com.glshop.platform.api.message.data;

import com.glshop.platform.api.base.CommonResult;

/**
 * @Description : 获取未读消息数目
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetUnreadedMsgResult extends CommonResult {

	/**
	 * 总数
	 */
	public int totalSize;

	@Override
	public String toString() {
		return super.toString() + ", TotalCount = " + totalSize;
	}

}
