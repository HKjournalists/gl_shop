package com.glshop.net.ui.basic.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

/**
 * @Description : Dialog基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 下午4:33:41
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener {

	protected Context mContext;

	protected View mView;

	protected boolean canBack = true;

	protected IDialogCallback callback;

	protected int mDialogType = -1;

	/**
	 * @param context
	 */
	public BaseDialog(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * @param context
	 * @param theme
	 */
	public BaseDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCanceledOnTouchOutside(false);
	}

	public void setCanBack(boolean canBack) {
		this.canBack = canBack;
	}

	public void setCallback(IDialogCallback callback) {
		this.callback = callback;
	}

	public void setDialogType(int type) {
		this.mDialogType = type;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (canBack) {
				return super.onKeyDown(keyCode, event);
			} else {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void closeDialog() {
		if (isShowing()) {
			dismiss();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) mView.findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	/**
	 * 对话框回调接口
	 */
	public interface IDialogCallback {

		public void onConfirm(int type, Object obj);

		public void onCancel(int type);

	}

}
