package com.glshop.platform.base.config;

import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;

import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.Logger;

/**
 * FileName    : PlatformConfig.java
 * Description : 一些参数的配置项
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午2:28:36
 **/
public class PlatformConfig {

	/**http请求超时时间*/
	public static final String HTTP_CONNECTION_TIMEOUT = "HTTP_CONNECTION_TIMEOUT";
	/**http请求超时时间*/
	public static final String HTTP_CONNECTION_READTIME = "HTTP_CONNECTION_READTIME";
	/**当前用户token*/
	public static final String USER_TOKEN = "USER_TOKEN";
	/**当前用户ID*/
	public static final String USER_ID = "USER_ID";
	/**服务器请求地址*/
	public static final String SERVICES_URL = "SERVICES_URL";
	/**服务器图片请求地址*/
	public static final String SERVICES_URL_IMAGE = "SERVICES_URL_IMAGE";

	public static final String LOGIN_TYPE_USER = "LOGIN_TYPE_USER";
	public static final String USER_ACCOUNT = "USER_ACCOUNT";

	public static final String USER_PASSWORD = "USER_PASSWORD";
	/**token有效期*/
	public static final String TOKEN_EXPIRY = "TOKEN_EXPIRY";

	private static final String TAG = "Config";
	private static final String PLATFORM_CONFIG = "platform_config";
	private static Context mContext;
	private static SharedPreferences mSharedPreferences;
	private static ResultItem configs = new ResultItem();
	
	private static Handler hanlder;

	public static void init(Context context) {
		mContext = context;
		getSharedPreferences();
		//将配置信息全部初始化到内存中
		if (mSharedPreferences != null) {
			try {
				Map<String, String> maps = (Map<String, String>) mSharedPreferences.getAll();
				for (Entry<String, String> properites : maps.entrySet()) {
					configs.put(properites.getKey(), properites.getValue());
				}
			} catch (Exception e) {
				Logger.e(TAG, "init error:", e);
			}
		}
		hanlder = new Handler();
	}

	/**
	 * 获取 SharedPreferences
	 * @return
	 */
	private static synchronized SharedPreferences getSharedPreferences() {
		if (mSharedPreferences == null) {
			if (mContext != null) {
				mSharedPreferences = mContext.getSharedPreferences(PLATFORM_CONFIG, Context.MODE_PRIVATE);
			} else {
				Logger.e(TAG, "getSharedPreferences error:mContext is null");
			}
		}
		return mSharedPreferences;
	}

	/**
	 * 获取上下文，sdk的唯一入口，不提供其他入口
	 * @return
	 */
	public static Context getContext() {
		return mContext;
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return configs.getString(key);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static String getString(String key, String defaultv) {
		return configs.getString(key, defaultv);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return configs.getInt(key);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static int getInt(String key, int defaultv) {
		return configs.getInt(key, defaultv);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static float getFloat(String key) {
		return configs.getFloat(key);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static float getFloat(String key, float defaultv) {
		return configs.getFloat(key, defaultv);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static double getDouble(String key) {
		return configs.getDouble(key);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static double getDouble(String key, double defaultv) {
		return configs.getDouble(key, defaultv);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static long getLong(String key) {
		return configs.getLong(key);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static long getLong(String key, long defaultv) {
		return configs.getLong(key, defaultv);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
		return configs.getBoolean(key);
	}

	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultv) {
		return configs.getBoolean(key, defaultv);
	}

	/***
	 * 设置参数
	 * @param key
	 */
	public static void remove(String key) {
		setValue(key, null);
	}
	
	public final static Handler getHandler(){
		return hanlder;
	}

	/**
	 * 设置参数
	 * @param key
	 * @param value
	 */
	public static void setValue(String key, Object value) {
		configs.put(key, value);
		try {
			//保持到数据
			SharedPreferences mSharedPreferences = getSharedPreferences();
			Editor edit = mSharedPreferences.edit();
			if (null == value) {
				edit.remove(key);
			} else {
				if (value instanceof String) {
					edit.putString(key, value.toString());
				} else if (value instanceof Integer) {
					edit.putInt(key, (Integer) value);
				} else if (value instanceof Long) {
					edit.putLong(key, (Long) value);
				} else if (value instanceof Float) {
					edit.putFloat(key, (Float) value);
				} else if (value instanceof Boolean) {
					edit.putBoolean(key, (Boolean) value);
				}
			}
			edit.commit();
		} catch (Exception e) {
			Logger.e(TAG, "setValue error:", e);
		}
	}
}
