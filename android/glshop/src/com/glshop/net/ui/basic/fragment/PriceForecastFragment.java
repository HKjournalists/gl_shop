package com.glshop.net.ui.basic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.adapter.buy.ForecastPriceAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.DataConstants.SysCfgCode;
import com.glshop.platform.api.buy.data.model.ForecastPriceModel;
import com.glshop.platform.base.manager.LogicFactory;
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
public class PriceForecastFragment extends BasicFragment {

	private static final String TAG = "PriceForecastFragment";

	private PullRefreshListView mLvPriceForecast;
	private ForecastPriceAdapter mAdapter;
	private ArrayList<ForecastPriceModel> mInitData;
	private boolean isRestored = false;

	protected ProductType type;

	protected String mAreaCode = "";

	protected IBuyLogic mBuyLogic;

	public PriceForecastFragment() {
		//mInitData = new ArrayList<ForecastPriceModel>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mInitData = (ArrayList<ForecastPriceModel>) savedInstanceState.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_PRICE_FORECAST_DATA);
			if (mInitData != null) {
				isRestored = true;
				Logger.e(TAG, "InitData = " + mInitData);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView & Type = " + type.toValue());
		mRootView = inflater.inflate(R.layout.fragment_price_forecast, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = getArguments();
		type = ProductType.convert(bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_TYPE));
	}

	private void initView() {
		initLoadView();

		mLvPriceForecast = getView(R.id.lv_price_forecast);
		mLvPriceForecast.setIsRefreshable(true);
		//mLvPriceForecast.hideFootView();

		setOnRefreshListener(mLvPriceForecast);
		setOnScrollListener(mLvPriceForecast);

		mNormalDataView = mLvPriceForecast;
	}

	private void initData() {
		mAdapter = new ForecastPriceAdapter(mContext, mInitData);
		mLvPriceForecast.setAdapter(mAdapter);
		if (!isRestored) {
			updateDataStatus(DataStatus.LOADING);
			mBuyLogic.getPriceForecast(type, getProductCode(), mAreaCode, DataReqType.INIT);
		} else {
			isRestored = false;
			updateDataStatus(DataStatus.NORMAL);
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getPriceForecast(type, getProductCode(), mAreaCode, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mBuyLogic.getPriceForecast(type, getProductCode(), mAreaCode, DataReqType.REFRESH);
	}

	public void refresh() {
		Logger.e(TAG, "---do refresh---");
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
			List data = DataCenter.getInstance().getData(getDataType());
			if (BeanUtils.isNotEmpty(data)) {
				mAdapter.setList(data);
				mLvPriceForecast.onRefreshSuccess();
				mLvPriceForecast.showLoadFinish();
				mLvPriceForecast.setSelection(0);
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

	public void setAreaCode(String area) {
		this.mAreaCode = area;
	}

	public String getAreaCode() {
		return this.mAreaCode;
	}

	protected int getDataType() {
		int dataType = DataType.SAND_FORECAST_PRICE_LIST;
		switch (type) {
		case SAND:
			dataType = DataType.SAND_FORECAST_PRICE_LIST;
			break;
		case STONE:
			dataType = DataType.STONE_FORECAST_PRICE_LIST;
			break;
		}
		return dataType;
	}

	protected String getProductCode() {
		String productCode = SysCfgCode.TYPE_PRODUCT_SAND;
		switch (type) {
		case SAND:
			productCode = SysCfgCode.TYPE_PRODUCT_SAND;
			break;
		case STONE:
			productCode = SysCfgCode.TYPE_PRODUCT_STONE;
			break;
		}
		return productCode;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mAdapter.getList().size() > 0) {
			outState.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_PRICE_FORECAST_DATA, (ArrayList<ForecastPriceModel>) mAdapter.getList());
		}
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

}
