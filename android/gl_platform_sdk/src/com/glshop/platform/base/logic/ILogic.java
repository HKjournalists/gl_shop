package com.glshop.platform.base.logic;

import android.os.Handler;

/**
 * FileName    : ILogic.java
 * Description : 业务逻辑的统一接口定义
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午10:44:03
 **/
public interface ILogic {

	/** 添加通讯用的Handlers */
	public void addHandler(Handler handler);

	/** 移除通讯用的Handlers */
	public void romoveHander(Handler handler);

	/** 执行异步操作 */
	public void invokeAsync(Runnable runnable);
}
