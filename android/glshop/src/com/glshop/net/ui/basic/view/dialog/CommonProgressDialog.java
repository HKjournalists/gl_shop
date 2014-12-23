package com.glshop.net.ui.basic.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 通用进度对话框
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午9:53:54
 */
public class CommonProgressDialog extends ProgressDialog {

	private String progressText;

	public CommonProgressDialog(Context context) {
		super(context);
	}

	public CommonProgressDialog(Context context, String text) {
		super(context);
		this.progressText = text;
	}

	public CommonProgressDialog(Context context, String text, boolean canBack) {
		super(context);
		this.progressText = text;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress);

		if (StringUtils.isNotEmpty(progressText)) {
			TextView textView = (TextView) findViewById(R.id.dialog_progress_text);
			textView.setText(progressText);
		}
	}

	public void setDismissListener(OnDismissListener listener) {
		setOnDismissListener(listener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
}
