package com.glshop.net.ui.myprofile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.AddrListAdapter;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.base.manager.LogicFactory;

/**
 * @Description : 选择卸货地点列表界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class DischargeAddrSelectActivity extends BasicActivity implements OnItemClickListener {

	private ListView mLvAddr;
	private AddrListAdapter mAdapter;

	private Button mBtnAddrMgr;

	private IProfileLogic mProfileLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discharge_addr_select);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_addr_list);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_discharge_address_select);

		mBtnAddrMgr = getView(R.id.btn_commmon_action);
		mBtnAddrMgr.setVisibility(View.VISIBLE);
		mBtnAddrMgr.setText(R.string.discharge_address_mgr);
		mBtnAddrMgr.setOnClickListener(this);

		mLvAddr = getView(R.id.lv_addr_list);
		mAdapter = new AddrListAdapter(this, null);
		mLvAddr.setAdapter(mAdapter);
		mLvAddr.setOnItemClickListener(this);

		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getAddrList(getCompanyId());
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mProfileLogic.getAddrList(getCompanyId());
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ProfileMessageType.MSG_GET_ADDR_LIST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ProfileMessageType.MSG_GET_ADDR_LIST_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			ArrayList<AddrInfoModel> data = (ArrayList<AddrInfoModel>) respInfo.data;
			if (data.size() > 0) {
				mAdapter.setList(data, true);
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Logger.e(TAG, "onItemClick & Position = " + (position - mLvAddr.getHeaderViewsCount()));
		AddrInfoModel info = mAdapter.getItem(position - mLvAddr.getHeaderViewsCount());
		Intent intent = new Intent();
		intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_INFO, info);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			intent = new Intent(this, DischargeAddrMgrActivity.class);
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	protected void initLogics() {
		mProfileLogic = LogicFactory.getLogicByClass(IProfileLogic.class);
	}
}
