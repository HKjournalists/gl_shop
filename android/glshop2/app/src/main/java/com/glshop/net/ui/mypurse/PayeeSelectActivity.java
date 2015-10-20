package com.glshop.net.ui.mypurse;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.profile.PayeeListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.platform.api.DataConstants.PayeeStatus;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 选择收款人界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PayeeSelectActivity extends BasicActivity implements OnItemClickListener {

	private PullRefreshListView mLvPayee;
	private PayeeListAdapter mAdapter;

	private Button mBtnPayeeMgr;
	private View mEmptyDataView2;

	private PurseType purseType;
	private double aviableMoney;

	private IPurseLogic mPurseLogic;
	private boolean isInit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_payee_select);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_payee_list);
		mEmptyDataView2 = getView(R.id.ll_empty_intro);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_selelct_payee);

		mLvPayee = getView(R.id.lv_payee_list);
		mLvPayee.setIsRefreshable(true);
		//mLvPayee.hideFootView();

		setOnRefreshListener(mLvPayee);
		setOnScrollListener(mLvPayee);

		mAdapter = new PayeeListAdapter(this, null);
		mLvPayee.setAdapter(mAdapter);
		mLvPayee.setOnItemClickListener(this);

		mBtnPayeeMgr = getView(R.id.btn_commmon_action);
		mBtnPayeeMgr.setVisibility(View.VISIBLE);
		mBtnPayeeMgr.setText(R.string.manager);
		mBtnPayeeMgr.setOnClickListener(this);

		getView(R.id.btn_add_payee).setOnClickListener(this);
		getView(R.id.btn_next_step).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		aviableMoney = getIntent().getDoubleExtra(GlobalAction.PurseAction.EXTRA_KEY_ROLLOUT_MONEY, 0);
		if (aviableMoney > 0) {
			purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
			updateDataStatus(DataStatus.LOADING);
			mPurseLogic.getPayeeList(getCompanyId(), PayeeStatus.AUTHED, DataReqType.INIT);
		} else {
			showToast("余额不足，无法操作!");
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getPayeeList(getCompanyId(), PayeeStatus.AUTHED, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mPurseLogic.getPayeeList(getCompanyId(), PayeeStatus.AUTHED, DataReqType.REFRESH);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.PurseMessageType.MSG_GET_SELECT_PAYEE_LIST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_GET_SELECT_PAYEE_LIST_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.PurseMessageType.MSG_GET_SELECT_PAYEE_LIST_FAILED:
				showToast(R.string.error_req_get_list);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		List<PayeeInfoModel> data = DataCenter.getInstance().getData(DataType.PAYEE_SELECT_LIST);
		if (BeanUtils.isNotEmpty(data)) {
			mAdapter.setList(data);
			mLvPayee.onRefreshSuccess();
			mLvPayee.showLoadFinish();
			mLvPayee.setSelection(0);
			updateDataStatus(DataStatus.NORMAL);

			PayeeInfoModel defaultInfo = null;
			for (PayeeInfoModel info : data) {
				if (info.isDefault) {
					defaultInfo = info;
					break;
				}
			}
			if (defaultInfo == null) {
				defaultInfo = data.get(0);
			}
			mAdapter.setSelectedPayeeInfo(defaultInfo);
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			intent = new Intent(this, PayeeMgrActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_add_payee:
			intent = new Intent(this, PayeeAddActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_next_step:
			intent = new Intent(this, PurseRollOutSubmitActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO, mAdapter.getSelectedPayeeInfo());
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_ROLLOUT_MONEY, aviableMoney);
			startActivity(intent);
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mAdapter.setSelectedPayeeInfo(mAdapter.getItem(position - mLvPayee.getHeaderViewsCount()));
	}

	@Override
	protected void updateDataStatus(DataStatus status) {
		if (status == DataStatus.NORMAL) {
			mLoadContainerView.setVisibility(View.GONE);
			mEmptyDataView2.setVisibility(View.GONE);
			mNormalDataView.setVisibility(View.VISIBLE);
		} else if (status == DataStatus.EMPTY) {
			mEmptyDataView2.setVisibility(View.VISIBLE);
			mLoadContainerView.setVisibility(View.GONE);
			mNormalDataView.setVisibility(View.GONE);
		} else {
			mLoadContainerView.setVisibility(View.VISIBLE);
			mNormalDataView.setVisibility(View.GONE);
			mLoadingDataView.setVisibility(status == DataStatus.LOADING ? View.VISIBLE : View.GONE);
			mEmptyDataView.setVisibility(View.GONE);
			mLoadErrorView.setVisibility(status == DataStatus.ERROR ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	protected int[] getDataType() {
		return new int[] { DataType.PAYEE_SELECT_LIST };
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isInit){
			onRefresh();
		}else{
			this.isInit = true;
		}
		
	}
	
	

}
