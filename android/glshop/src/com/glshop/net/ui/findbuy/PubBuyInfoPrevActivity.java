package com.glshop.net.ui.findbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.PubBuyInfoPrevFragment;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;

/**
 * @Description : 发布找买找卖信息或修改信息预览页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PubBuyInfoPrevActivity extends BasicFragmentActivity {

	private static final String TAG = "PubBuyInfoPrevActivity";

	private static final String TAB_BUY = "fragment-tab-buy";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private PubBuyInfoPrevFragment mPubBuyPrevFragment;

	private boolean isModifyMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_buy_info_preview);
		initView();
		initData();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.publish_buy_message_prev);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mPubBuyPrevFragment = getFragment(TAB_BUY);

		isModifyMode = getIntent().getBooleanExtra(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, false);
		BuyInfoModel info = (BuyInfoModel) getIntent().getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_PREV_BUY_INFO);

		if (mPubBuyPrevFragment == null) {
			mPubBuyPrevFragment = new PubBuyInfoPrevFragment();
			Bundle buyArgs = new Bundle();
			buyArgs.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, R.layout.fragment_pub_buy_prev_info);
			buyArgs.putBoolean(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, isModifyMode);
			buyArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_PREV_BUY_INFO, info);
			mPubBuyPrevFragment.setArguments(buyArgs);
		}

		showFragment(FRAGMENT_CONTAINER, mPubBuyPrevFragment, TAB_BUY);
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
