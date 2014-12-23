package com.glshop.net.utils;

import com.glshop.net.R;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.basic.view.dialog.SingleConfirmDialog;

import android.app.Dialog;
import android.content.Context;

/**
 * @Description : 对话框工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:40:28
 */
public class DialogUtil {

	public static Dialog showConfirmDialog(Context context, String title, String content, IDialogCallback callback) {
		ConfirmDialog dialog = new ConfirmDialog(context, R.style.dialog);
		dialog.setTitle(title);
		dialog.setContent(content);
		dialog.setCallback(callback);
		dialog.show();
		return dialog;
	}

	public static Dialog showConfirmDialog(Context context, String title, String content, String confirmText, String cancelText, IDialogCallback callback) {
		ConfirmDialog dialog = new ConfirmDialog(context, R.style.dialog);
		dialog.setTitle(title);
		dialog.setContent(content);
		dialog.setConfirmText(confirmText);
		dialog.setCancelText(cancelText);
		dialog.setCallback(callback);
		return dialog;
	}

	public static Dialog showConfirmDialog(Context context, boolean isShowNow, String title, String content, IDialogCallback callback) {
		ConfirmDialog dialog = new ConfirmDialog(context, R.style.dialog);
		dialog.setTitle(title);
		dialog.setContent(content);
		dialog.setCallback(callback);
		if (isShowNow && !dialog.isShowing()) {
			dialog.show();
		}
		return dialog;
	}

	public static Dialog showConfirmDialog(Context context, boolean isShowNow, String title, String content, String confirmText, String cancelText, IDialogCallback callback) {
		ConfirmDialog dialog = new ConfirmDialog(context, R.style.dialog);
		dialog.setTitle(title);
		dialog.setContent(content);
		dialog.setConfirmText(confirmText);
		dialog.setCancelText(cancelText);
		dialog.setCallback(callback);
		if (isShowNow && !dialog.isShowing()) {
			dialog.show();
		}
		return dialog;
	}

	public static Dialog showSingleConfirmDialog(Context context, String title, String content, String confirmText, IDialogCallback callback) {
		SingleConfirmDialog dialog = new SingleConfirmDialog(context, R.style.dialog);
		dialog.setTitle(title);
		dialog.setContent(content);
		dialog.setConfirmText(confirmText);
		dialog.setCallback(callback);
		return dialog;
	}

}
