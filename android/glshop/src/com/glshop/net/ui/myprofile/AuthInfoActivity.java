package com.glshop.net.ui.myprofile;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 认证详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class AuthInfoActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth_info);
		initView();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.auth_detail_info);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

}
