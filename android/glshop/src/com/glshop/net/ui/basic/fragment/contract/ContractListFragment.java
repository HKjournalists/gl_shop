package com.glshop.net.ui.basic.fragment.contract;

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
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.adapter.ContractListAdapter;
import com.glshop.net.ui.basic.timer.GlobalTimerMgr;
import com.glshop.net.ui.basic.timer.GlobalTimerMgr.ITimerListener;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.mycontract.ContractInfoActivity;
import com.glshop.net.ui.mycontract.UfmContractInfoActivity;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 我的合同列表Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class ContractListFragment extends BasicFragment implements OnItemClickListener, ITimerListener {

	private static final String TAG = "ContractListFragment";

	private PullRefreshListView mLvContractList;
	private ContractListAdapter mAdapter;
	private ArrayList<ContractSummaryInfoModel> mInitData;
	private boolean isRestored = false;

	private ContractType type;

	private boolean hasNextPage = true;

	private IContractLogic mContractLogic;

	public ContractListFragment() {
		//mInitData = new ArrayList<ContractSummaryInfoModel>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mInitData = (ArrayList<ContractSummaryInfoModel>) savedInstanceState.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACTS_DATA);
			if (mInitData != null) {
				isRestored = true;
				Logger.e(TAG, "InitData = " + mInitData);
			}
		}
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		type = ContractType.convert(bundle.getInt(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_TYPE));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView & Type = " + type.toValue());
		mRootView = inflater.inflate(R.layout.fragment_contract_list, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		GlobalTimerMgr.getInstance().addTimerListener(this);
		//Logger.e(TAG, "onResume & Type = " + type.toValue());
	}

	@Override
	public void onPause() {
		super.onPause();
		GlobalTimerMgr.getInstance().removeTimerListener(this);
		//Logger.e(TAG, "onPause & Type = " + type.toValue());
	}

	private void initView() {
		initLoadView();

		mLvContractList = getView(R.id.lv_contract_list);
		mLvContractList.setIsRefreshable(true);
		mLvContractList.hideFootView();

		setOnRefreshListener(mLvContractList);
		setOnScrollListener(mLvContractList);
		mLvContractList.setOnItemClickListener(this);

		mNormalDataView = mLvContractList;
	}

	private void initData() {
		mAdapter = new ContractListAdapter(mContext, mInitData, type);
		mLvContractList.setAdapter(mAdapter);
		if (!isRestored) {
			updateDataStatus(DataStatus.LOADING);
			mContractLogic.getContracts(type, DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
		} else {
			isRestored = false;
			updateDataStatus(DataStatus.NORMAL);
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContracts(type, DEFAULT_INDEX, PAGE_SIZE, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mContractLogic.getContracts(type, DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
	}

	@Override
	protected void onScrollMore() {
		Logger.e(TAG, "---onLoadMore---");
		if (hasNextPage) {
			mLvContractList.showLoading();
			mContractLogic.getContracts(type, pageIndex, PAGE_SIZE, DataReqType.MORE);
		} else {
			mLvContractList.showLoadFinish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what + " & Type = " + type.toValue() + " & RespInfo = " + message.obj);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACTS_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACTS_FAILED:
			onGetFailed(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_WAIT_TIME:
			if (mAdapter != null) {
				mAdapter.notifyDataSetChanged();
			}
			break;
		case GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_LIST:
			if (message.arg2 == type.toValue()) {
				onRefresh();
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvBuyList.getHeaderViewsCount()));
		ContractSummaryInfoModel info = mAdapter.getItem(position - mLvContractList.getHeaderViewsCount());
		Intent intent = null;
		switch (type) {
		case UNCONFIRMED:
			intent = new Intent(mContext, UfmContractInfoActivity.class);
			break;
		case ONGOING:
		case ENDED:
			intent = new Intent(mContext, ContractInfoActivity.class);
			break;
		default:
			intent = new Intent(mContext, ContractInfoActivity.class);
			break;
		}
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, info.contractId);
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
				mLvContractList.onRefreshSuccess();
				if (type == DataReqType.INIT) {
					mLvContractList.setSelection(0);
				}
				//if (type == DataReqType.INIT || type == DataReqType.MORE) {
				if (size > 0) {
					pageIndex++;
				}
				hasNextPage = size >= PAGE_SIZE;
				if (hasNextPage) {
					mLvContractList.showLoading();
				} else {
					mLvContractList.showLoadFinish();
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

	@Override
	public void onTimerTick() {
		//Logger.e(TAG, "onTimerTick & Type = " + type.toValue());
		if (mAdapter != null) {
			getHandler().sendEmptyMessage(GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_WAIT_TIME);
		}
	}

	private int getDataType() {
		int dataType = DataType.UFM_CONTRACT_LIST;
		switch (type) {
		case UNCONFIRMED:
			dataType = DataType.UFM_CONTRACT_LIST;
			break;
		case ONGOING:
			dataType = DataType.ONGOING_CONTRACT_LIST;
			break;
		case ENDED:
			dataType = DataType.ENDED_CONTRACT_LIST;
			break;
		}
		return dataType;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mAdapter.getList().size() > 0) {
			outState.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACTS_DATA, (ArrayList<ContractSummaryInfoModel>) mAdapter.getList());
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
