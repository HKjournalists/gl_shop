package com.glshop.net.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 客服电话界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class CustomServiceIntroActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_procedure_intro);
		initView();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.platform_custom_service_phone);
		getView(R.id.btn_call_custom_service).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		case R.id.btn_call_custom_service:
			Intent dial = new Intent(Intent.ACTION_DIAL);
			dial.setData(Uri.parse("tel:" + GlobalConstants.Common.PLATFORM_CUSTOM_SERVICE_PHONE));
			startActivity(dial);
			break;
		}
	}

}
