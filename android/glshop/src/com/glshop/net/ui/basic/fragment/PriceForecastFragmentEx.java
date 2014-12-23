package com.glshop.net.ui.basic.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.adapter.ForecastPriceExAdapter;
import com.glshop.platform.api.buy.data.model.ForecastPriceModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 价格预测Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class PriceForecastFragmentEx extends PriceForecastFragment implements OnGroupClickListener {

	private static final String TAG = "PriceForecastFragmentEx";

	private ExpandableListView mLvPriceForecast;
	private ForecastPriceExAdapter mAdapter;

	public PriceForecastFragmentEx() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView & Type = " + type.toValue());
		mRootView = inflater.inflate(R.layout.fragment_price_forecast_v2, container, false);
		initView();
		initData();
		return mRootView;
	}

	private void initView() {
		initLoadView();
		mLvPriceForecast = getView(R.id.exlv_price_forecast);
		mNormalDataView = mLvPriceForecast;
	}

	private void initData() {
		mAdapter = new ForecastPriceExAdapter(mContext, null, null);
		mLvPriceForecast.setAdapter(mAdapter);
		mLvPriceForecast.setOnGroupClickListener(this);
		mLvPriceForecast.setGroupIndicator(null);
		//mLvPriceForecast.setGroupIndicator(mContext.getResources().getDrawable(R.drawable.selector_expand_listview_icon));
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getPriceForecast(type, getProductCode(), mAreaCode, DataReqType.INIT);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getPriceForecast(type, getProductCode(), mAreaCode, DataReqType.INIT);
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & Type = " + type.toValue() + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.BuyMessageType.MSG_GET_PRICE_FORECAST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_GET_PRICE_FORECAST_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo != null && respInfo.intArg1 == type.toValue()) {
			Object[] priceList = mBuyLogic.getSandPriceForecastList();
			if (BeanUtils.isNotEmpty(priceList) && priceList.length == 2) {
				mAdapter.setGroupData((List<MenuItemInfo>) priceList[0], (Map<MenuItemInfo, ArrayList<ForecastPriceModel>>) priceList[1], true);
				expandAll();
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		if (respInfo != null && respInfo.intArg1 == type.toValue()) {
			updateDataStatus(DataStatus.ERROR);
			handleErrorAction(respInfo);
		}
	}

	private void expandAll() {
		int groupCount = mAdapter.getGroupCount();
		for (int i = 0; i < groupCount; i++) {
			mLvPriceForecast.expandGroup(i);
		}
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
		if (parent.isGroupExpanded(groupPosition)) {
			parent.collapseGroup(groupPosition);
		} else {
			parent.expandGroup(groupPosition);
		}
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// nothing todo
	}

}
