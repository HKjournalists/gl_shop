package com.glshop.net.ui.basic.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.glshop.net.R;

/**
 * @Description : 全局通用输入对话框。
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public class InputDialog extends BaseDialog {

	private TextView mTvTitle;
	private EditText mEtContent;
	private Button mBtnConfirm;
	private Button mBtnCancel;

	/**
	 * @param context
	 */
	public InputDialog(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public InputDialog(Context context, int theme) {
		super(context, theme);
		initView(context);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public InputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		setContentView(mView, lp);
	}

	private void initView(Context context) {
		mView = View.inflate(context, R.layout.dialog_input, null);

		mTvTitle = (TextView) mView.findViewById(R.id.dialog_tv_title);
		mEtContent = (EditText) mView.findViewById(R.id.dialog_et_content);
		mBtnConfirm = (Button) mView.findViewById(R.id.dialog_btn_confirm);
		mBtnCancel = (Button) mView.findViewById(R.id.dialog_btn_cancel);

		mBtnConfirm.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);
	}

	public void setTitle(String text) {
		mTvTitle.setText(text);
	}

	public void setContent(String text) {
		mEtContent.setText(text);
	}

	public void setConfirmText(String text) {
		mBtnConfirm.setText(text);
	}

	public void setCancelText(String text) {
		mBtnCancel.setText(text);
	}

	@Override
	public void onClick(View v) {
		hideKeyboard();
		switch (v.getId()) {
		case R.id.dialog_btn_confirm:
			mBtnConfirm.setClickable(false);
			if (callback != null) {
				callback.onConfirm(mDialogType, mEtContent.getText().toString());
			}
			closeDialog();
			break;
		case R.id.dialog_btn_cancel:
			mBtnCancel.setClickable(false);
			if (callback != null) {
				callback.onCancel(mDialogType);
			}
			closeDialog();
			break;
		}
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0);
	}

	public void setInputType(int type) {
		mEtContent.setInputType(type);
	}

	public void setPasswordEnable(boolean enable) {
		if (enable) {
			mEtContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
		} else {
			//mEtContent.setTransformationMethod(null);
		}
	}

}
