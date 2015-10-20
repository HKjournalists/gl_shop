package com.glshop.net.logic.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.glshop.net.utils.NetworkUtil;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 网络变化监听
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午5:14:40
 */
public final class NetObserver extends BroadcastReceiver {

	private static final String TAG = "NetObserver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (null != intent && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
			if (NetworkUtil.isNetworkConnected(context)) {
				Logger.d(TAG, "network is connnected.");
			} else {
				Logger.d(TAG, "network is disconnnected.");
			}
		}
	}

}
