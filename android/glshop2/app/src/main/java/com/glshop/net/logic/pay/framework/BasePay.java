package com.glshop.net.logic.pay.framework;

import android.content.Context;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public abstract class BasePay implements IPay {

	protected Context mContext;

	public BasePay(Context context) {
		this.mContext = context;
	}

}
