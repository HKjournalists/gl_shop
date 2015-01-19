package com.glshop.net.ui.basic.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.glshop.net.R;

/**
 * @Description : 全局通用确认对话框，仅提供单个按钮选项操作。
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public class SingleConfirmDialog extends BaseDialog {

	private ImageView mIvTipIcon;
	private TextView mTvContent;
	private Button mBtnConfirm;

	/**
	 * @param context
	 */
	public SingleConfirmDialog(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public SingleConfirmDialog(Context context, int theme) {
		super(context, theme);
		initView(context);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public SingleConfirmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
		mView = getLayoutInflater().inflate(R.layout.dialog_single_confirm, null);

		mIvTipIcon = (ImageView) mView.findViewById(R.id.dialog_iv_tip_ic);
		mTvContent = (TextView) mView.findViewById(R.id.dialog_tv_content);
		mBtnConfirm = (Button) mView.findViewById(R.id.dialog_btn_confirm);

		mBtnConfirm.setOnClickListener(this);
	}

	public void setTipIcon(int icon) {
		mIvTipIcon.setBackgroundResource(icon);
	}

	public void setContent(String text) {
		mTvContent.setText(text);
	}

	public void setConfirmText(String text) {
		mBtnConfirm.setText(text);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_btn_confirm:
			mBtnConfirm.setClickable(false);
			if (callback != null) {
				callback.onConfirm(mDialogType, null);
			}
			closeDialog();
			break;
		}
	}

}
