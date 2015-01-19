package com.glshop.net.ui.findbuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.BuyFilterType;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.buy.filter.ProductFilterFragment;
import com.glshop.net.ui.basic.fragment.buy.filter.TradeAreaFilterFragment;
import com.glshop.net.ui.basic.fragment.buy.filter.TradeDateFilterFragment;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 买卖信息筛选页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class BuyFilterActivity extends BasicFragmentActivity {

	private static final String TAG = "BuyFilterActivity";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private static final String TAB_PRODCUT = "fragment-tab-product";
	private static final String TAB_AREA = "fragment-tab-area";
	private static final String TAB_DATETIME = "fragment-tab-datetime";

	private ProductFilterFragment mProductFilterFragment;
	private TradeAreaFilterFragment mTradeAreaFilterFragment;
	private TradeDateFilterFragment mTradeDateFilterFragment;

	private RadioButton mRdoBtnProduct;
	private RadioButton mRdoBtnArea;
	private RadioButton mRdoBtnDatetime;

	private BuyFilterType mFilterType = BuyFilterType.TRADE_PRODUCT;

	private BuyFilterInfoModelV2 mFilterInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_filter);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {
		mRdoBtnProduct = getView(R.id.rdoBtn_product);
		mRdoBtnArea = getView(R.id.rdoBtn_area);
		mRdoBtnDatetime = getView(R.id.rdoBtn_datetime);

		getView(R.id.rdoBtn_product).setOnClickListener(this);
		getView(R.id.rdoBtn_area).setOnClickListener(this);
		getView(R.id.rdoBtn_datetime).setOnClickListener(this);
		getView(R.id.btn_filter_confirm).setOnClickListener(this);
		getView(R.id.btn_filter_reset).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			mFilterType = BuyFilterType.convert(savedState.getInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB));
		}
		Logger.e(TAG, "TabIndex = " + mFilterType.toValue());
		updateTabState();

		mFilterInfo = (BuyFilterInfoModelV2) getIntent().getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);

		mProductFilterFragment = getFragment(TAB_PRODCUT);
		mTradeAreaFilterFragment = getFragment(TAB_AREA);
		mTradeDateFilterFragment = getFragment(TAB_DATETIME);

		if (mProductFilterFragment == null) {
			mProductFilterFragment = new ProductFilterFragment();
			mProductFilterFragment.setArguments(createFragmentArgs(R.layout.fragment_product_filter));
		}
		if (mTradeAreaFilterFragment == null) {
			mTradeAreaFilterFragment = new TradeAreaFilterFragment();
			mTradeAreaFilterFragment.setArguments(createFragmentArgs(R.layout.fragment_trade_area_filter));
		}
		if (mTradeDateFilterFragment == null) {
			mTradeDateFilterFragment = new TradeDateFilterFragment();
			mTradeDateFilterFragment.setArguments(createFragmentArgs(R.layout.fragment_trade_date_filter));
		}
		switchView();
	}

	private Bundle createFragmentArgs(int layoutID) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, layoutID);
		args.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mFilterInfo);
		return args;
	}

	private void updateTabState() {
		mRdoBtnProduct.setChecked(mFilterType == BuyFilterType.TRADE_PRODUCT);
		mRdoBtnArea.setChecked(mFilterType == BuyFilterType.TRADE_AREA);
		mRdoBtnDatetime.setChecked(mFilterType == BuyFilterType.TRADE_DATE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rdoBtn_product:
			if (mFilterType != BuyFilterType.TRADE_PRODUCT) {
				mFilterType = BuyFilterType.TRADE_PRODUCT;
				switchView();
			}
			break;
		case R.id.rdoBtn_area:
			if (mFilterType != BuyFilterType.TRADE_AREA) {
				mFilterType = BuyFilterType.TRADE_AREA;
				switchView();
			}
			break;
		case R.id.rdoBtn_datetime:
			if (mFilterType != BuyFilterType.TRADE_DATE) {
				mFilterType = BuyFilterType.TRADE_DATE;
				switchView();
			}
			break;
		case R.id.btn_filter_reset:
			resetFilter();
			break;
		case R.id.btn_filter_confirm:
			submitFilter();
			break;
		case R.id.iv_common_back:
			cancelFilter();
			finish();
			break;
		}
	}

	private void switchView() {
		switch (mFilterType) {
		case TRADE_PRODUCT:
			switchFragment(FRAGMENT_CONTAINER, mProductFilterFragment, TAB_PRODCUT, mTradeAreaFilterFragment, mTradeDateFilterFragment);
			break;
		case TRADE_AREA:
			switchFragment(FRAGMENT_CONTAINER, mTradeAreaFilterFragment, TAB_AREA, mProductFilterFragment, mTradeDateFilterFragment);
			break;
		case TRADE_DATE:
			switchFragment(FRAGMENT_CONTAINER, mTradeDateFilterFragment, TAB_DATETIME, mProductFilterFragment, mTradeAreaFilterFragment);
			break;
		}
	}

	private void resetFilter() {
		if (mProductFilterFragment != null) {
			mProductFilterFragment.onFilterReset();
		}
		if (mTradeAreaFilterFragment != null) {
			mTradeAreaFilterFragment.onFilterReset();
		}
		if (mTradeDateFilterFragment != null) {
			mTradeDateFilterFragment.onFilterReset();
		}
	}

	private void cancelFilter() {
		if (mProductFilterFragment != null) {
			mProductFilterFragment.onFilterCancel();
		}
		if (mTradeAreaFilterFragment != null) {
			mTradeAreaFilterFragment.onFilterCancel();
		}
		if (mTradeDateFilterFragment != null) {
			mTradeDateFilterFragment.onFilterCancel();
		}
	}

	private void submitFilter() {
		if (mFilterInfo != null) {
			if (mTradeDateFilterFragment != null) {
				if (mTradeDateFilterFragment.checkArgs()) {
					mTradeDateFilterFragment.onFilterSave();
					BuyFilterInfoModelV2 newFilterInfo = mTradeDateFilterFragment.getFilterInfo(true);
					if (newFilterInfo != null) {
						mFilterInfo.tradeStartDate = newFilterInfo.tradeStartDate;
						mFilterInfo.tradeEndDate = newFilterInfo.tradeEndDate;
					}
				} else {
					return;
				}
			}
			if (mProductFilterFragment != null) {
				mProductFilterFragment.onFilterSave();
				BuyFilterInfoModelV2 newFilterInfo = mProductFilterFragment.getFilterInfo(true);
				if (newFilterInfo != null) {
					mFilterInfo.productIdList = newFilterInfo.productIdList;
				}
			}
			if (mTradeAreaFilterFragment != null) {
				mTradeAreaFilterFragment.onFilterSave();
				BuyFilterInfoModelV2 newFilterInfo = mTradeAreaFilterFragment.getFilterInfo(true);
				if (newFilterInfo != null) {
					mFilterInfo.provinceCodeList = newFilterInfo.provinceCodeList;
					mFilterInfo.districtCodeList = newFilterInfo.districtCodeList;
				}
			}
		}
		Intent intent = new Intent();
		intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mFilterInfo);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, mFilterType.toValue());
	}

}
