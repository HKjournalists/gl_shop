package com.glshop.net.ui.mybuy;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 我发布的买卖信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MyBuyInfoActivity extends BasicActivity {

	private static final String TAG = "MyBuyInfoActivity";

	private long pubBuyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_buy_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.publish_type_buy);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		pubBuyId = getIntent().getLongExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_ID, 0);
		Logger.e(TAG, "PubBuyId = " + pubBuyId);
		/*if (pubBuyId > 0) {

		} else {
			showToast("PubBuyId error...");
			finish();
		}*/
	}

	@Override
	protected void handleStateMessage(Message message) {

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
