package com.glshop.net.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.glshop.net.common.GlobalMessageType;

/**
 * @Description : 网络操作工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:40:28
 */
public final class NetworkUtil {

	/**
	 * 判断网络是否已连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conn.getActiveNetworkInfo();
		if (net != null && net.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前已连接网络类型
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		int curState = GlobalMessageType.NetworkMessageType.NET_STATUS_TYPE_DISCONNECTED;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null || connManager.getActiveNetworkInfo() == null) {
			return curState;
		}

		NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isConnected()) {
			return curState;
		}

		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			curState = GlobalMessageType.NetworkMessageType.NET_STATUS_TYPE_WIFI;
		} else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			if (isFastMobileNetwork(context)) {
				curState = GlobalMessageType.NetworkMessageType.NET_STATUS_TYPE_3G;
			} else {
				curState = GlobalMessageType.NetworkMessageType.NET_STATUS_TYPE_2G;
			}
		}

		return curState;
	}

	/**
	 * 判断当前网络是否是Wifi网络
	 * @param context
	 * @return
	 */
	public static boolean isWifiNetwork(Context context) {
		return getNetworkType(context) == GlobalMessageType.NetworkMessageType.NET_STATUS_TYPE_WIFI;
	}

	private static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return false; // ~ 50-100 kbps  
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false; // ~ 14-64 kbps  
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return false; // ~ 50-100 kbps  
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true; // ~ 400-1000 kbps  
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true; // ~ 600-1400 kbps  
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return false; // ~ 100 kbps  
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return true; // ~ 2-14 Mbps  
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return true; // ~ 700-1700 kbps  
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return true; // ~ 1-23 Mbps  
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return true; // ~ 400-7000 kbps  
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}

}
