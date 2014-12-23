package com.glshop.net.ui.findbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.BasePubInfoFragment;
import com.glshop.net.ui.basic.fragment.BasePubInfoFragment.PubFragmentCallback;
import com.glshop.net.ui.basic.fragment.PubBuyInfoFragment;
import com.glshop.net.ui.basic.fragment.PubSellInfoFragment;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;

/**
 * @Description : 发布买卖信息页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PubBuyInfoActivity extends BasicFragmentActivity implements PubFragmentCallback {

	private static final String TAG = "PubBuyInfoActivity";

	private static final String TAB_BUYER = "fragment-tab-buyer";
	private static final String TAB_SELLER = "fragment-tab-seller";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private PubBuyInfoFragment mPubBuyFragment;
	private PubSellInfoFragment mPubSellFragment;

	private BuyType pubType = BuyType.BUYER;

	private boolean isModifyMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_buy_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.publish_buy_message);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mPubBuyFragment = getFragment(TAB_BUYER);
		mPubSellFragment = getFragment(TAB_SELLER);

		isModifyMode = getIntent().getBooleanExtra(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, false);
		BuyInfoModel info = null;
		if (isModifyMode) {
			info = (BuyInfoModel) getIntent().getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO);
			pubType = info.buyType;
		}

		if (mPubBuyFragment == null) {
			mPubBuyFragment = new PubBuyInfoFragment();
			Bundle buyArgs = new Bundle();
			buyArgs.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, R.layout.fragment_pub_buy_info);
			buyArgs.putBoolean(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, isModifyMode);
			buyArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, info);
			mPubBuyFragment.setArguments(buyArgs);
		}
		if (mPubSellFragment == null) {
			mPubSellFragment = new PubSellInfoFragment();
			Bundle sellArgs = new Bundle();
			sellArgs.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, R.layout.fragment_pub_sell_info);
			sellArgs.putBoolean(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, isModifyMode);
			sellArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, info);
			mPubSellFragment.setArguments(sellArgs);
		}

		switchPubType(pubType, true);
	}

	@Override
	public void switchPubType(BuyType type, boolean init) {
		pubType = type;
		BasePubInfoFragment srcFragment = pubType == BuyType.BUYER ? mPubSellFragment : mPubBuyFragment;
		BasePubInfoFragment descFragment = pubType == BuyType.BUYER ? mPubBuyFragment : mPubSellFragment;
		BuyInfoModel buyInfo = srcFragment.getBuyInfo();
		boolean isAdded = descFragment.isAdded();
		if (!init) {
			if (!isAdded) {
				Bundle buyArgs = descFragment.getArguments();
				buyArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, buyInfo);
				descFragment.setArguments(buyArgs);
			}
		}

		if (pubType == BuyType.BUYER) {
			switchFragment(FRAGMENT_CONTAINER, mPubBuyFragment, TAB_BUYER, mPubSellFragment);
		} else {
			switchFragment(FRAGMENT_CONTAINER, mPubSellFragment, TAB_SELLER, mPubBuyFragment);
		}
		if (!init) {
			if (isAdded) {
				descFragment.updateBuyInfo(buyInfo);
			}
		}
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
