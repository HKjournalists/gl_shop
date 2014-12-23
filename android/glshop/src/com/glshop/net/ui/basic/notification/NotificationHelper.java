package com.glshop.net.ui.basic.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @Description : 通知栏辅助类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午4:04:50
 */
public final class NotificationHelper {

	/**
	 * PendingIntent跳转类型定义
	 */
	public static interface PendingIntentType {

		/**
		 * PendingIntent跳转类型为不跳转
		 */
		public static final int NONE = 0;

		/**
		 * PendingIntent跳转类型为Activity
		 */
		public static final int ACTIVITY = 1;

		/**
		 * PendingIntent跳转类型为Broadcast
		 */
		public static final int BROADCAST = 2;

		/**
		 * PendingIntent跳转类型为Service
		 */
		public static final int SERVICE = 3;

	}

	public static void notify(Context context, int id, String title, String text, String action) {
		Notification notification = DefineNotification.getDefaultNotification(context, title, text, title);
		Intent intent = new Intent(action);
		notification.contentIntent = getDefaultPendingIntent(context, PendingIntentType.ACTIVITY, id, intent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(context, id, notification);
	}

	/**
	 * 全局通用通知
	 * @param context 上下文
	 * @param id 通知id
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @param action aciton
	 * @param flags 通知标识
	 * @param intentType PendingIntent跳转类型
	 */
	public static void notify(Context context, int id, String title, String text, String ticker, String action, int flags, int intentType) {
		Notification notification = DefineNotification.getDefaultNotification(context, title, text, ticker);
		Intent intent = new Intent(action);
		notification.contentIntent = getDefaultPendingIntent(context, intentType, id, intent);
		notification.flags = flags;
		DefineNotification.notifyCommon(context, id, notification);
	}

	/**
	 * 全局通用通知
	 * @param context 上下文
	 * @param id 通知id
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @param Intent intent
	 * @param flags 通知标识
	 * @param intentType PendingIntent跳转类型, {@link #PendingIntentType}
	 */
	public static void notify(Context context, int id, String title, String text, String ticker, Intent intent, int flags, int intentType) {
		Notification notification = DefineNotification.getDefaultNotification(context, title, text, ticker);
		notification.contentIntent = getDefaultPendingIntent(context, intentType, id, intent);
		notification.flags = flags;
		DefineNotification.notifyCommon(context, id, notification);

	}

	/**
	 * 扩展全局通用通知，兼容4.1版本以上通知栏大视图支持多行显示
	 * @param context 上下文
	 * @param id 通知id
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @param bigTitle 大视图标题
	 * @param bigText 大视图内容
	 * @param bigSummary 大视图概要
	 * @param action aciton
	 * @param flags 通知标识
	 * @param intentType PendingIntent跳转类型, {@link #PendingIntentType}
	 */
	public static void notifyEx(Context context, int id, String title, String text, String ticker, String bigTitle, String[] bigText, String bigSummary, String action, int flags, int intentType) {
		Notification notification = DefineNotification.getDefaultNotification(context, title, text, ticker, bigTitle, bigText, bigSummary);
		Intent intent = new Intent(action);
		notification.contentIntent = getDefaultPendingIntent(context, intentType, id, intent);
		notification.flags = flags;
		DefineNotification.notifyCommon(context, id, notification);
	}

	/**
	 * 扩展全局通用通知，兼容4.1版本以上通知栏大视图支持多行显示
	 * @param context 上下文
	 * @param id 通知id
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @param bigTitle 大视图标题
	 * @param bigText 大视图内容
	 * @param bigSummary 大视图概要
	 * @param intent intent
	 * @param flags 通知标识
	 * @param intentType PendingIntent跳转类型, {@link #PendingIntentType}
	 */
	public static void notifyEx(Context context, int id, String title, String text, String ticker, String bigTitle, String[] bigText, String bigSummary, Intent intent, int flags, int intentType) {
		Notification notification = DefineNotification.getDefaultNotification(context, title, text, ticker, bigTitle, bigText, bigSummary);
		notification.contentIntent = getDefaultPendingIntent(context, intentType, id, intent);
		notification.flags = flags;
		DefineNotification.notifyCommon(context, id, notification);
	}

	/**
	 * 根据PendingIntent type创建PendingIntent
	 * @param context 上下文
	 * @param type PendingIntent类型
	 * @param id request ID
	 * @param intent intent
	 * @return
	 */
	public static PendingIntent getDefaultPendingIntent(Context context, int type, int id, Intent intent) {
		PendingIntent contentIntent = null;
		if (PendingIntentType.ACTIVITY == type) {
			contentIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		} else if (PendingIntentType.BROADCAST == type) {
			contentIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		} else if (PendingIntentType.SERVICE == type) {
			contentIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		return contentIntent;
	}

	public static void clearAll() {
		DefineNotification.clearAll();
	}

	public static void clearById(int id) {
		DefineNotification.clearById(id);
	}

}
