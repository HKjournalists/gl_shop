package com.glshop.net.ui.basic.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.adapter.TodayPriceAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.basic.view.PullRefreshListView.OnRefreshListener;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.DataConstants.SysCfgCode;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 今日价格Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class TodayPriceFragment extends BasicFragment {

	private static final String TAG = "TodayPriceFragment";

	private PullRefreshListView mLvTodayPrice;
	private TodayPriceAdapter mAdapter;
	private ArrayList<TodayPriceModel> mInitData;
	private boolean isRestored = false;

	private ProductType type;

	private IBuyLogic mBuyLogic;

	public TodayPriceFragment() {
		//mData = new ArrayList<TodayPriceModel>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate");
		if (savedInstanceState != null) {
			mInitData = (ArrayList<TodayPriceModel>) savedInstanceState.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_TODAY_PRICE_DATA);
			if (mInitData != null) {
				isRestored = true;
				Logger.e(TAG, "InitData = " + mInitData);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView & Type = " + type.toValue());
		mRootView = inflater.inflate(R.layout.fragment_today_price, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		//TODO
	}

	private void initView() {
		mLvTodayPrice = getView(R.id.lv_today_price);
		mLvTodayPrice.setIsRefreshable(true);
		mLvTodayPrice.hideFootView();
		mLvTodayPrice.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				Logger.e(TAG, "---onRefresh---");
				mBuyLogic.getTodayPrice(type, SysCfgCode.TYPE_PRODUCT_SAND, "RS001");
			}
		});
		mLvTodayPrice.setNewScrollerListener(null);
	}

	private void initData() {
		mAdapter = new TodayPriceAdapter(mContext, mInitData);
		mLvTodayPrice.setAdapter(mAdapter);
		if (!isRestored) {
			mBuyLogic.getTodayPrice(type, SysCfgCode.TYPE_PRODUCT_SAND, "RS001");
		} else {
			isRestored = false;
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & Type = " + type.toValue() + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		if (respInfo != null && respInfo.intArg1 == type.toValue()) {
			switch (message.what) {
			case GlobalMessageType.BuyMessageType.MSG_GET_TODAY_PRICE_SUCCESS:
				onGetTodayPriceSuccess(respInfo);
				break;
			case GlobalMessageType.BuyMessageType.MSG_GET_TODAY_PRICE_FAILED:
				onGetTodayPriceFailed(respInfo);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetTodayPriceSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			ArrayList<TodayPriceModel> data = (ArrayList<TodayPriceModel>) respInfo.data;
			if (data.size() > 0) {
				mAdapter.addList(data, true);
				mLvTodayPrice.onRefreshSuccess();
			} else {
				//TODO show empty page
			}
		} else {
			//TODO show empty page
		}
	}

	private void onGetTodayPriceFailed(RespInfo respInfo) {
		handleErrorAction(respInfo);
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mAdapter.getList().size() > 0) {
			outState.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_TODAY_PRICE_DATA, (ArrayList<TodayPriceModel>) mAdapter.getList());
		}
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

}
