package com.glshop.platform.base.ui;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.glshop.platform.base.manager.MessageCenter;

/**
 * FileName    : BaseActivity.java
 * Description : activity基类
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午11:46:05
 **/
public class BaseActivity extends ActivityGroup {

	private Handler mhanlder;
	protected final String TAG = this.getClass().getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLogics();
		MessageCenter.getInstance().addHandler(getHandler());
	}

	/** 消息处理 */
	protected void handleStateMessage(Message message) {

	}

	/** logic */
	protected void initLogics() {

	}

	/** handler */
	protected Handler getHandler() {
		if (mhanlder == null) {
			mhanlder = new Handler() {
				public void handleMessage(Message msg) {
					handleStateMessage(msg);
				}
			};
		}
		return mhanlder;
	}

	protected void sendMessage(Message message) {
		getHandler().sendMessage(message);
	}

	protected void sendMessage(int what, Object obj) {
		Message message = new Message();
		message.what = what;
		message.obj = obj;
		getHandler().sendMessage(message);
	}

	protected void sendEmptyMessage(int what) {
		getHandler().sendEmptyMessage(what);
	}

	/** 统一的 Toast */
	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/** 统一的 Toast */
	protected void showToast(int resId) {
		showToast(getString(resId));
	}

	/** 统一的 ProgressDialog */
	protected Dialog showProgressDialog(String message) {
		ProgressDialog dialog = new ProgressDialog(getDialogContext());
		dialog.setMessage(message);
		dialog.setCancelable(true);
		return dialog;
	}

	/** 统一的 ProgressDialog */
	protected Dialog showProgressDialog(int resId) {
		return showProgressDialog(getString(resId));
	}

	protected void dimssDialog(Dialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		// 移除回收的handler
		MessageCenter.getInstance().removeHandler(getHandler());
		super.onDestroy();
	}

	/** 获取最上层的Activity负责 弹dialog,防止出现 can't add dialog 出错 */
	protected Context getDialogContext() {
		Activity activity = this;
		while (activity.getParent() != null) {
			activity = activity.getParent();
		}
		Log.d("Dialog", "context:" + activity);
		return activity;
	}

}
