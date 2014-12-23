package com.glshop.net.ui.basic.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.glshop.net.R;
import com.glshop.net.ui.basic.notification.NotificationConstants.PushNotifyID;

/**
 * @Description : DefineNotification
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午4:04:50
 */
public class DefineNotification {

	private static Set<Integer> mNotifiedIDList = new CopyOnWriteArraySet<Integer>();

	private static Set<Integer> mIgnoreNotifyIDList = new HashSet<Integer>();

	private static NotificationManager mNotificationMgr;

	static {
		mIgnoreNotifyIDList.add(PushNotifyID.BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE);
	}

	protected static synchronized NotificationManager getManager(Context context) {
		if (null == mNotificationMgr) {
			mNotificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		return mNotificationMgr;
	}

	public static void notifyCommon(Context context, int id, Notification notification) {
		mNotifiedIDList.add(id);
		getManager(context).notify(id, notification);
	}

	/**
	 * 清除通知栏中指定通知
	 */
	public static synchronized void clearById(int id) {
		if (mNotificationMgr != null) {
			mNotificationMgr.cancel(id);
			mNotifiedIDList.remove(id);
		}
	}

	/**
	 * 清除通知栏
	 */
	public static synchronized void clearAll() {
		if (mNotificationMgr != null) {
			mNotificationMgr.cancelAll();
		}
	}

	/**
	 * 清除通知栏，忽略指定的ID
	 */
	public static synchronized void clearAll(boolean ignore) {
		if (mNotificationMgr != null) {
			if (ignore) {
				Iterator<Integer> it = mNotifiedIDList.iterator();
				List<Integer> removedIds = new ArrayList<Integer>();
				while (it.hasNext()) {
					Integer id = it.next();
					if (!mIgnoreNotifyIDList.contains(id)) {
						removedIds.add(id);
					}
				}
				mNotifiedIDList.remove(removedIds);
			} else {
				clearAll();
			}
		}
	}

	/**
	 * 创建默认通知栏
	 * @param context 上下文
	 * @param title 标题
	 * @param text 内容
	 * @return
	 */
	public static Notification getDefaultNotification(Context context, String title, String text) {
		NotificationCompat.Builder builder = getNotificationBuilder(context, title, text);
		return builder.build();
	}

	/**
	 * 创建默认通知栏
	 * @param context 上下文
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @return
	 */
	public static Notification getDefaultNotification(Context context, String title, String text, String ticker) {
		NotificationCompat.Builder builder = getNotificationBuilder(context, title, text);
		// builder.setTicker(title); // Ticker统一使用Title
		return builder.build();
	}

	/**
	 * 创建默认通知栏
	 * @param context 上下文
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @param progress 进度
	 * @return
	 */
	public static Notification getDefaultNotification(Context context, String title, String text, String ticker, int progress) {
		NotificationCompat.Builder builder = getNotificationBuilder(context);
		builder.setContentTitle(title);
		builder.setContentText(text);
		builder.setTicker(title); // Ticker统一使用Title
		builder.setProgress(100, progress, false);
		return builder.build();
	}

	/**
	 * 创建支持多行文本显示的默认通知栏(4.1版本以上才支持)
	 * @param context 上下文
	 * @param title 标题
	 * @param text 内容
	 * @param ticker ticker
	 * @param bigTitle 大视图标题
	 * @param bigContent 大视图内容
	 * @param bigSummary 大视图概要
	 * @return
	 */
	public static Notification getDefaultNotification(Context context, String title, String text, String ticker, String bigTitle, String[] bigContent, String bigSummary) {
		NotificationCompat.Builder builder = getNotificationBuilder(context, title, text);
		//builder.setTicker(ticker); // Ticker统一使用Title

		NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
		if (bigContent != null) {
			// 4.1以上版本显示多行文本
			for (int i = 0; i < bigContent.length; i++) {
				style.addLine(bigContent[i]);
			}
		}
		style.setBigContentTitle(bigTitle);
		style.setSummaryText(bigSummary);

		builder.setStyle(style);
		return builder.build();
	}

	/**
	 * 创建默认NotificationCompat.Builder
	 * @param context 上下文
	 * @param title 标题
	 * @param text 内容
	 * @return
	 */
	private static NotificationCompat.Builder getNotificationBuilder(Context context, String title, String text) {
		NotificationCompat.Builder builder = getNotificationBuilder(context);
		builder.setContentTitle(title);
		builder.setContentText(text);
		builder.setTicker(title); // Ticker统一使用Title
		return builder;
	}

	/**
	 * 创建默认NotificationCompat.Builder
	 * @param context 上下文
	 * @return
	 */
	private static NotificationCompat.Builder getNotificationBuilder(Context context) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(R.drawable.icon);
		Intent intent = new Intent(context, context.getApplicationContext().getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//Intent intent = new Intent();
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);
		return builder;
	}

}
