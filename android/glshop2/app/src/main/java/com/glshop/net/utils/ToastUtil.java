package com.glshop.net.utils;

import com.glshop.net.R;
import com.glshop.platform.utils.Logger;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Description : Toast实用类 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:45:39
 */
public class ToastUtil {

	private static final String TAG = "ToastUtil";

	/**默认ID*/
	public static final int TOAST_ID_DEFAULT = 0;

	/**传输管理ID*/
	public static final int TOAST_ID_TRRANSFER = 1;

	/**context*/
	private static Context sAppContext;

	private static SparseArray<Toast> mToast = new SparseArray<Toast>();

	private static Handler sHandler;

	private static final int FREQUENCY = 2000;

	private static String msgMsg;

	private static long lastShowTime;

	/**
	 * 初始化Context
	 * @param context 上下文
	 */
	public static void init(Context context) {
		if (!isMainThread()) {
			throw new RuntimeException("must call in main thread!");
		}
		sAppContext = context;
		sHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int toastId = msg.arg1;
				String textMsg = (String) msg.obj;
				boolean global = msg.arg2 == 1;
				showToast(toastId, textMsg, global);
			}
		};
	}

	private static boolean isMainThread() {
		Looper currLooper = Looper.myLooper();
		if (currLooper == null || currLooper != Looper.getMainLooper()) {
			return false;
		}
		return true;
	}

	private static void showToast(int toastId, String msg, boolean global) {
		//添加一个逻辑，不是前台不提醒
		if (!global && !ActivityUtil.isForegroundV2(sAppContext)) {
			Logger.e(TAG, "showToast & app is background");
			return;
		}
		//两秒内，连续相同的msg不显示
		Long time = System.currentTimeMillis() - lastShowTime;
		if (time < FREQUENCY && msg != null && msg.equals(msgMsg)) {
			return;
		}

		if (!isMainThread()) {
			Message msg1 = sHandler.obtainMessage();
			msg1.arg1 = toastId;
			msg1.arg2 = 0;
			if (global) {
				msg1.arg2 = 1;
			}
			msg1.obj = msg;
			msg1.sendToTarget();
			return;
		}

		msgMsg = msg;
		lastShowTime = System.currentTimeMillis();

		int duration = (msg.length() > 50) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
		if (toastId > 0) {
			Toast toast = mToast.get(toastId);

			if (toast == null) {
				synchronized (ToastUtil.class) {
					//使用Application Context来防止内存泄露
					//Context appContext = context.getApplicationContext();
					toast = Toast.makeText(sAppContext, msg, duration);
					mToast.put(toastId, toast);
				}
			} else {
				//			toast.cancel();
				toast.setText(msg);
				toast.setDuration(duration);

			}
			toast.show();
		} else {
			Toast.makeText(sAppContext, msg, duration).show();
		}

	}

	/***
	 * 展示toast
	 * @param context context
	 * @param toastId toastId
	 * @param msgResId msgResId
	 */
	public static void showToast(Context context, int toastId, int msgResId) {
		showToast(toastId, context.getString(msgResId), false);
	}

	/***
	 * 展示toast
	 * @param context context
	 * @param msgResId msgResId
	 */
	public static void showDefaultToast(Context context, int msgResId) {
		showToast(TOAST_ID_DEFAULT, context.getString(msgResId), false);
	}

	/***
	 * 展示toast
	 * @param context context
	 * @param msg msg
	 */
	public static void showDefaultToast(Context context, String msg) {
		showToast(TOAST_ID_DEFAULT, msg, false);
	}

	/**
	 * 传输管理的Toast
	 * @param context context
	 * @param msgResId msgResId
	 */
	public static void showTransferToast(Context context, int msgResId) {
		showToast(TOAST_ID_TRRANSFER, context.getString(msgResId), false);
	}

	/**
	 * 程序在后台也显示toast
	 * @param context context
	 * @param msg msg
	 */
	public static void showGlobalToast(Context context, String msg) {
		showToast(TOAST_ID_DEFAULT, msg, true);
	}

	/**
	 * 带有背景的Toast
	 * @param context
	 * @param msg
	 */
	public static void showToastWithBg(Context context, int msg) {
		showToastWithBg(context, context.getResources().getString(msg));
	}

	/**
	 * 带有背景的Toast
	 * @param context
	 * @param msg
	 */
	public static void showToastWithBg(Context context, String msg) {
		Toast toast = new Toast(context);
		View toastView = View.inflate(context, R.layout.custom_toast, null);
		TextView message = (TextView) toastView.findViewById(R.id.toast_tv);
		message.setText(msg);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setView(toastView);
		toast.show();
	}
}
