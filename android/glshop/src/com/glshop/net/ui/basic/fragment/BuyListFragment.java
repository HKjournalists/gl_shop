package com.glshop.net.ui.basic.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.ViewBuyInfoType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.adapter.buy.BuyListAdapter;
import com.glshop.net.ui.basic.timer.GlobalTimerMgr;
import com.glshop.net.ui.basic.timer.GlobalTimerMgr.ITimerListener;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.findbuy.BuyInfoActivity;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.api.buy.data.model.BuySummaryInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 找买找卖列表Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class BuyListFragment extends BasicFragment implements OnItemClickListener, ITimerListener {

	private static final String TAG = "BuyListFragment";

	private boolean isInited;

	private PullRefreshListView mLvBuyList;
	private BuyListAdapter mAdapter;
	private ArrayList<BuySummaryInfoModel> mInitData;
	private boolean isRestored = false;

	private BuyFilterInfoModelV2 mFilterInfo;

	private BuyType type;

	private boolean hasNextPage = true;

	private IBuyLogic mBuyLogic;

	public BuyListFragment() {
		//mInitData = new ArrayList<BuySummaryInfoModel>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mInitData = (ArrayList<BuySummaryInfoModel>) savedInstanceState.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUYS_DATA);
			if (mInitData != null) {
				isRestored = true;
				Logger.e(TAG, "InitData = " + mInitData);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView & Type = " + type.toValue());
		mRootView = inflater.inflate(R.layout.fragment_buy_list, container, false);
		initView();
		initData();
		isInited = true;
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = getArguments();
		type = BuyType.convert(bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_BUY_INFO_TYPE));
		mFilterInfo = (BuyFilterInfoModelV2) bundle.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
	}

	@Override
	public void onResume() {
		super.onResume();
		GlobalTimerMgr.getInstance().addTimerListener(this);
		Logger.e(TAG, "onResume & Type = " + type.toValue());
	}

	@Override
	public void onPause() {
		super.onPause();
		GlobalTimerMgr.getInstance().removeTimerListener(this);
		Logger.e(TAG, "onPause & Type = " + type.toValue());
	}

	private void initView() {
		initLoadView();

		mLvBuyList = getView(R.id.lv_buy_list);
		mLvBuyList.setIsRefreshable(true);
		mLvBuyList.hideFootView();

		setOnRefreshListener(mLvBuyList);
		setOnScrollListener(mLvBuyList);
		mLvBuyList.setOnItemClickListener(this);

		mNormalDataView = mLvBuyList;
	}

	private void initData() {
		mAdapter = new BuyListAdapter(mContext, mInitData);
		mLvBuyList.setAdapter(mAdapter);
		if (!isRestored) {
			updateDataStatus(DataStatus.LOADING);
			mBuyLogic.getBuys(mFilterInfo, type, DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
		} else {
			isRestored = false;
			updateDataStatus(DataStatus.NORMAL);
		}
	}

	@Override
	public void onReloadData() {
		pageIndex = 1;
		updateDataStatus(DataStatus.LOADING);
		mBuyLogic.getBuys(mFilterInfo, type, DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mBuyLogic.getBuys(mFilterInfo, type, DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
	}

	@Override
	protected void onScrollMore() {
		Logger.e(TAG, "---onLoadMore & HasNextPage = " + hasNextPage);
		if (hasNextPage) {
			mLvBuyList.showLoading();
			mBuyLogic.getBuys(mFilterInfo, type, pageIndex, PAGE_SIZE, DataReqType.MORE);
		} else {
			mLvBuyList.showLoadFinish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & Type = " + type.toValue() + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.BuyMessageType.MSG_GET_BUYS_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_GET_BUYS_FAILED:
			onGetFailed(respInfo);
			break;
		case GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_WAIT_TIME:
			if (mAdapter != null) {
				mAdapter.notifyDataSetChanged();
			}
			break;
		case GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST:
			onRefresh();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvBuyList.getHeaderViewsCount()));
		BuySummaryInfoModel info = mAdapter.getItem(position - mLvBuyList.getHeaderViewsCount());
		Intent intent = new Intent(mContext, BuyInfoActivity.class);
		intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_ID, info.publishBuyId);
		intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_VIEW_BUY_INFO_TYPE, ViewBuyInfoType.FINDBUY.toValue());
		startActivity(intent);
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo != null && respInfo.intArg2 == type.toValue()) {
			DataReqType type = DataReqType.convert(respInfo.intArg1);
			int size = (Integer) respInfo.data;

			if (type == DataReqType.INIT && size == 0) {
				updateDataStatus(DataStatus.EMPTY);
			} else {
				updateDataStatus(DataStatus.NORMAL);
				if (type == DataReqType.REFRESH) {
					pageIndex = DEFAULT_INDEX;
					if (BeanUtils.isEmpty(DataCenter.getInstance().getData(getDataType()))) {
						updateDataStatus(DataStatus.EMPTY);
					}
				}
				mAdapter.setList(DataCenter.getInstance().getData(getDataType()));
				mLvBuyList.onRefreshSuccess();
				if (type == DataReqType.INIT) {
					mLvBuyList.setSelection(0);
				}
				//if (type == DataReqType.INIT || type == DataReqType.MORE) {
				if (size > 0) {
					pageIndex++;
				}
				hasNextPage = size >= PAGE_SIZE;
				if (hasNextPage) {
					mLvBuyList.showLoading();
				} else {
					mLvBuyList.showLoadFinish();
				}
				//}
			}
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		if (respInfo != null && respInfo.intArg2 == type.toValue()) {
			updateDataStatus(DataStatus.ERROR);
			handleErrorAction(respInfo);
		}
	}

	public void setType(BuyType type) {
		this.type = type;
	}

	private int getDataType() {
		int dataType = DataType.FINDBUY_BUYER_LIST;
		switch (type) {
		case BUYER:
			dataType = DataType.FINDBUY_BUYER_LIST;
			break;
		case SELLER:
			dataType = DataType.FINDBUY_SELLER_LIST;
			break;
		}
		return dataType;
	}

	@Override
	public void onTimerTick() {
		Logger.e(TAG, "onTimerTick & Type = " + type.toValue());
		if (mAdapter != null) {
			getHandler().sendEmptyMessage(GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_WAIT_TIME);
		}
	}

	public void updateBuyFilterInfo(BuyFilterInfoModelV2 info) {
		if (isAdded() && isInited) {
			mFilterInfo = info;
			onReloadData();
		} else {
			Bundle bundle = getArguments();
			bundle.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, info);
			setArguments(bundle);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mAdapter.getList().size() > 0) {
			outState.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUYS_DATA, (ArrayList<BuySummaryInfoModel>) mAdapter.getList());
		}
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

}
