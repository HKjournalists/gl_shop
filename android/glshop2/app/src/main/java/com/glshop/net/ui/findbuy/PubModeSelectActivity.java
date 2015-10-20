package com.glshop.net.ui.findbuy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.ui.basic.BasicActivity;

/**
 * @Description : 发布模式选择页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PubModeSelectActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_mode_select);
		initView();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.publish_buy_message);
		findViewById(R.id.btn_pub_by_custom_service).setOnClickListener(this);
		findViewById(R.id.btn_pub_by_myself).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pub_by_custom_service:
			Intent dial = new Intent(Intent.ACTION_DIAL);
			dial.setData(Uri.parse("tel:" + GlobalConstants.CfgConstants.PLATFORM_CUSTOM_SERVICE_PHONE));
			startActivity(dial);
			finish();
			break;

		case R.id.btn_pub_by_myself:
			if (GlobalConfig.getInstance().isLogined()) {
				if (checkDepositEnough()) {
					Intent pub = new Intent(this, PubBuyInfoActivityV2.class);
					startActivity(pub);
					finish();
				} else {
					showToast(R.string.user_deposit_not_enough);
				}
			} else {
				showToast(R.string.user_not_login);
			}
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private boolean checkDepositEnough() {
		if (GlobalConstants.CfgConstants.VALIDATE_DEPOSIT_ENOUGH) {
			return GlobalConfig.getInstance().isDepositEnough();
		}
		return true;
	}

}
