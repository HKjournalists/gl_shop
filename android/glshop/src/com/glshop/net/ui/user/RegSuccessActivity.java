package com.glshop.net.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.myprofile.ProfileAuthActivity;

/**
 * @Description : 用户注册成功页面(已废弃，统一调用系统操作成功页面OperatorTipsActivity)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
@Deprecated
public class RegSuccessActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_success);
		initView();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.register_success);
		getView(R.id.btn_goto_auth).setOnClickListener(this);
		getView(R.id.btn_skip_auth).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_goto_auth:
			// 跳转认证界面
			intent = new Intent(this, ProfileAuthActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_skip_auth:
			// 跳转主界面
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

}
