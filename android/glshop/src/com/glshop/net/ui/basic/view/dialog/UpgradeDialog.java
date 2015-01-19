package com.glshop.net.ui.basic.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;

/**
 * @Description : 升级对话框。
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public class UpgradeDialog extends BaseDialog {

	private TextView mTvVersion;
	private TextView mTvContent;
	private Button mBtnConfirm;
	private Button mBtnCancel;

	/**
	 * @param context
	 */
	public UpgradeDialog(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public UpgradeDialog(Context context, int theme) {
		super(context, theme);
		initView(context);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public UpgradeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
		mView = View.inflate(context, R.layout.dialog_upgrade, null);

		mTvVersion = (TextView) mView.findViewById(R.id.dialog_tv_version);
		mTvContent = (TextView) mView.findViewById(R.id.dialog_tv_content);
		mBtnConfirm = (Button) mView.findViewById(R.id.dialog_btn_confirm);
		mBtnCancel = (Button) mView.findViewById(R.id.dialog_btn_cancel);

		mBtnConfirm.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);
	}

	public void setUpgradeInfo(UpgradeInfoModel info) {
		mTvVersion.setText("V" + info.versionName);
		mTvContent.setText(info.description);
	}

	public void setContent(String text) {
		mTvContent.setText(text);
	}

	public void setConfirmText(String text) {
		mBtnConfirm.setText(text);
	}

	public void setCancelText(String text) {
		mBtnCancel.setText(text);
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

		case R.id.dialog_btn_cancel:
			mBtnCancel.setClickable(false);
			if (callback != null) {
				callback.onCancel(mDialogType);
			}
			closeDialog();
			break;
		}
	}

}
