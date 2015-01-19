package com.glshop.net.ui.findbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PubBuyIndicatorType;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.buy.pub.BasePubInfoFragmentV2;
import com.glshop.net.ui.basic.fragment.buy.pub.PubBuyInfoPrevFragmentV2;
import com.glshop.net.ui.basic.fragment.buy.pub.PubProductInfoFragmentV2;
import com.glshop.net.ui.basic.fragment.buy.pub.PubTradeInfoFragmentV2;
import com.glshop.net.ui.basic.fragment.buy.pub.BasePubInfoFragmentV2.Callback;
import com.glshop.net.ui.basic.view.PubBuyIndicator;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 发布买卖信息页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PubBuyInfoActivityV2 extends BasicFragmentActivity implements Callback {

	private static final String TAG = "PubBuyInfoActivityV2";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private static final String TAB_PRODCUT = "fragment-tab-product";
	private static final String TAB_TRADE = "fragment-tab-trade";
	private static final String TAB_PREVIEW = "fragment-tab-preview";

	private PubProductInfoFragmentV2 mPubProductFragment;
	private PubTradeInfoFragmentV2 mPubTradeFragment;
	private PubBuyInfoPrevFragmentV2 mPubPreviewFragment;

	private PubBuyIndicator mPubIndicator;
	private PubBuyIndicatorType mIndicatorType = PubBuyIndicatorType.PRODUCT;

	private BuyType pubType = BuyType.BUYER;

	private boolean isModifyMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_buy_info_v2);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.publish_buy_message);
		mPubIndicator = getView(R.id.ll_pub_buy_indicator);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData(Bundle savedInstanceState) {
		boolean isRestore = false;
		if (savedInstanceState != null) {
			mIndicatorType = PubBuyIndicatorType.convert(savedInstanceState.getInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB));
			if (mIndicatorType == null) {
				mIndicatorType = PubBuyIndicatorType.PRODUCT;
			}
			isRestore = true;
			Logger.e(TAG, "TabIndex = " + mIndicatorType.toValue());
		}

		mPubProductFragment = getFragment(TAB_PRODCUT);
		mPubTradeFragment = getFragment(TAB_TRADE);
		mPubPreviewFragment = getFragment(TAB_PREVIEW);

		isModifyMode = getIntent().getBooleanExtra(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, false);
		BuyInfoModel info = null;
		if (isModifyMode) {
			info = (BuyInfoModel) getIntent().getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO);
			pubType = info.buyType;
		}

		if (mPubProductFragment == null) {
			mPubProductFragment = new PubProductInfoFragmentV2();
			mPubProductFragment.setArguments(createFragmentArgs(info, R.layout.fragment_pub_product_info_v2));
		}
		if (mPubTradeFragment == null) {
			mPubTradeFragment = new PubTradeInfoFragmentV2();
			mPubTradeFragment.setArguments(createFragmentArgs(info, R.layout.fragment_pub_trade_info_v2));
		}
		if (mPubPreviewFragment == null) {
			mPubPreviewFragment = new PubBuyInfoPrevFragmentV2();
			mPubPreviewFragment.setArguments(createFragmentArgs(info, R.layout.fragment_pub_preview_v2));
		}

		if (isRestore) {
			mPubIndicator.setIndicatorType(mIndicatorType);
		} else {
			switchPubIndicator(PubBuyIndicatorType.PRODUCT, true);
		}
	}

	private Bundle createFragmentArgs(BuyInfoModel info, int layoutID) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, layoutID);
		args.putBoolean(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, isModifyMode);
		args.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, info);
		return args;
	}

	@Override
	public void switchPubIndicator(PubBuyIndicatorType type, boolean sequence) {
		mIndicatorType = type;
		mPubIndicator.setIndicatorType(mIndicatorType);
		if (mIndicatorType != null) {
			String tabTag = "";
			BuyInfoModel buyInfo = null;
			BasePubInfoFragmentV2 srcFragment = null;
			BasePubInfoFragmentV2 descFragment = null;

			switch (mIndicatorType) {
			case PRODUCT:
				tabTag = TAB_PRODCUT;
				srcFragment = mPubTradeFragment;
				descFragment = mPubProductFragment;
				buyInfo = srcFragment.getBuyInfo(false);
				//switchFragment(FRAGMENT_CONTAINER, mPubProductFragment, TAB_PRODCUT, mPubTradeFragment);
				break;
			case TRADE:
				tabTag = TAB_TRADE;
				srcFragment = sequence ? mPubProductFragment : mPubPreviewFragment;
				descFragment = mPubTradeFragment;
				buyInfo = srcFragment.getBuyInfo(sequence);
				//switchFragment(FRAGMENT_CONTAINER, mPubTradeFragment, TAB_TRADE, sequence ? mPubProductFragment : mPubPreviewFragment);
				break;
			case PREVIEW:
				tabTag = TAB_PREVIEW;
				srcFragment = mPubTradeFragment;
				descFragment = mPubPreviewFragment;
				buyInfo = srcFragment.getBuyInfo(true);
				//switchFragment(FRAGMENT_CONTAINER, mPubPreviewFragment, TAB_PREVIEW, mPubTradeFragment);
				break;
			}

			boolean isAdded = descFragment.isAdded();
			if (!isAdded) {
				Bundle buyArgs = descFragment.getArguments();
				buyArgs.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO, buyInfo);
				descFragment.setArguments(buyArgs);
			}

			switchFragment(FRAGMENT_CONTAINER, descFragment, tabTag, srcFragment);

			if (isAdded) {
				descFragment.updateBuyInfo(buyInfo);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			doBackAction();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		doBackAction();
	}

	private void doBackAction() {
		if (mIndicatorType != null) {
			if (mIndicatorType == PubBuyIndicatorType.TRADE) {
				switchPubIndicator(PubBuyIndicatorType.PRODUCT, false);
				return;
			} else if (mIndicatorType == PubBuyIndicatorType.PREVIEW) {
				switchPubIndicator(PubBuyIndicatorType.TRADE, false);
				return;
			}
		}
		finish();
	}

	@Override
	public void switchPubBuyType(BuyType type) {

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, mIndicatorType.toValue());
	}

}
