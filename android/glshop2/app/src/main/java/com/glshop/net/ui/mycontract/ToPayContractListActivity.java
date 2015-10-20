package com.glshop.net.ui.mycontract;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.contract.ToPayContractListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.platform.api.contract.data.model.ToPayContractInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 待付货款列表页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ToPayContractListActivity extends BasicActivity implements OnItemClickListener {

	private static final String TAG = "ToPayContractListActivity";

	private PullRefreshListView mLvToPayBuyList;
	private ToPayContractListAdapter mAdapter;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_to_pay_contract_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_to_pay_contract);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mLvToPayBuyList = getView(R.id.lv_to_pay_contract_list);
		mLvToPayBuyList.setIsRefreshable(true);
		//mLvToPayBuyList.hideFootView();

		setOnRefreshListener(mLvToPayBuyList);
		setOnScrollListener(mLvToPayBuyList);

		mLvToPayBuyList.setOnItemClickListener(this);

		mAdapter = new ToPayContractListAdapter(this, null);
		mLvToPayBuyList.setAdapter(mAdapter);

		mNormalDataView = mLvToPayBuyList;
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getToPayContracts(DataReqType.INIT);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getToPayContracts(DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mContractLogic.getToPayContracts(DataReqType.REFRESH);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ContractMessageType.MSG_GET_TO_PAY_CONTRACTS_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_GET_TO_PAY_CONTRACTS_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		List data = DataCenter.getInstance().getData(DataType.TO_PAY_CONTRACT_LIST);
		if (BeanUtils.isNotEmpty(data)) {
			mAdapter.setList(data);
			mLvToPayBuyList.onRefreshSuccess();
			mLvToPayBuyList.showLoadFinish();
			mLvToPayBuyList.setSelection(0);
			updateDataStatus(DataStatus.NORMAL);
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.ContractMessageType.MSG_GET_TO_PAY_CONTRACTS_FAILED:
				showToast(R.string.error_req_get_list);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Logger.e(TAG, "onItemClick & Position = " + (position - mLvToPayBuyList.getHeaderViewsCount()));
		ToPayContractInfoModel contractInfo = mAdapter.getItem(position - mLvToPayBuyList.getHeaderViewsCount());
		Intent intent = new Intent(this, ContractInfoActivityV2.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractInfo.contractId);
		startActivity(intent);
	}

	@Override
	protected int[] getDataType() {
		return new int[] { DataType.TO_PAY_CONTRACT_LIST };
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
