package com.glshop.net.ui.basic.fragment.contract;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.adapter.OprHistoryListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.basic.view.PullRefreshListView.OnRefreshListener;
import com.glshop.platform.api.contract.data.model.ContractOprInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同操作历史记录Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class ContractOprHistoryFragment extends BasicFragment {

	private static final String TAG = "ContractOprHistoryFragment";
	private String mContractId;
	private PullRefreshListView mLvOprHistory;
	private OprHistoryListAdapter mAdapter;
	private ArrayList<ContractOprInfoModel> mInitData;
	private boolean isRestored = false;

	private IContractLogic mContractLogic;

	public ContractOprHistoryFragment() {
		//mData = new ArrayList<ContractOprInfoModel>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate");
		if (savedInstanceState != null) {
			mInitData = (ArrayList<ContractOprInfoModel>) savedInstanceState.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_HISTORY_DATA);
			if (mInitData != null) {
				isRestored = true;
				Logger.e(TAG, "InitData = " + mInitData);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView");
		mRootView = inflater.inflate(R.layout.fragment_contact_opr_history, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		mContractId = bundle.getString(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
	}

	private void initView() {
		if (StringUtils.isNotEmpty(mContractId)) {
			((TextView) getView(R.id.tv_contract_id)).setText(mContractId);

			initLoadView();
			mLvOprHistory = getView(R.id.lv_opr_history);
			mLvOprHistory.setIsRefreshable(true);
			mLvOprHistory.hideFootView();
			mLvOprHistory.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					Logger.e(TAG, "---onRefresh---");
					mContractLogic.getContractOprHistory(mContractId);
				}
			});
			mLvOprHistory.setNewScrollerListener(null);

			getView(R.id.btn_contract_info_detail).setVisibility(View.GONE);
			mNormalDataView = mLvOprHistory;
		} else {
			showToast("合同ID不能为空!");
		}
	}

	private void initData() {
		mAdapter = new OprHistoryListAdapter(mContext, mInitData);
		mLvOprHistory.setAdapter(mAdapter);
		if (!isRestored) {
			updateDataStatus(DataStatus.LOADING);
			mContractLogic.getContractOprHistory(mContractId);
		} else {
			isRestored = false;
			updateDataStatus(DataStatus.NORMAL);
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContractModel(mContractId);
	}

	@Override
	protected void onRefresh() {
		mContractLogic.getContractModel(mContractId);
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACT_OPR_HISTORY_SUCCESS:
			onGetOprHistorySuccess(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACT_OPR_HISTORY_FAILED:
			onGetOprHistoryFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetOprHistorySuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			ArrayList<ContractOprInfoModel> data = (ArrayList<ContractOprInfoModel>) respInfo.data;
			if (data.size() > 0) {
				mAdapter.setList(data);
				mLvOprHistory.onRefreshSuccess();
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetOprHistoryFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mAdapter.getList().size() > 0) {
			outState.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_HISTORY_DATA, (ArrayList<ContractOprInfoModel>) mAdapter.getList());
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
