package com.glshop.net.ui.mypurse;

import java.util.List;

import android.app.Activity;
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
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.adapter.profile.PayeeListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 收款人管理界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PayeeMgrActivity extends BasicActivity implements OnItemClickListener {

	private static final String TAG = "PayeeMgrActivity";

	private PullRefreshListView mLvPayee;
	private PayeeListAdapter mAdapter;

	private IPurseLogic mPurseLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payee_mgr);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_payee_mgr);

		mLvPayee = getView(R.id.lv_payee_list);
		mLvPayee.setIsRefreshable(true);
		//mLvPayee.hideFootView();

		setOnRefreshListener(mLvPayee);
		setOnScrollListener(mLvPayee);

		mAdapter = new PayeeListAdapter(this, null);
		mLvPayee.setAdapter(mAdapter);
		mLvPayee.setOnItemClickListener(this);

		getView(R.id.ll_item_add_payee).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mNormalDataView = mLvPayee;
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getPayeeList(getCompanyId(), null, DataReqType.INIT);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getPayeeList(getCompanyId(), null, DataReqType.INIT);
	}

	@Override
	protected void onRefresh() {
		Logger.e(TAG, "---onRefresh---");
		mPurseLogic.getPayeeList(getCompanyId(), null, DataReqType.REFRESH);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.PurseMessageType.MSG_GET_MGR_PAYEE_LIST_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_GET_MGR_PAYEE_LIST_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		List data = DataCenter.getInstance().getData(DataType.PAYEE_MANAGER_LIST);
		if (BeanUtils.isNotEmpty(data)) {
			mAdapter.setList(data);
			mLvPayee.onRefreshSuccess();
			mLvPayee.showLoadFinish();
			mLvPayee.setSelection(0);
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
			case GlobalMessageType.PurseMessageType.MSG_GET_MGR_PAYEE_LIST_FAILED:
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
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_item_add_payee:
			intent = new Intent(this, PayeeAddActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_next_step:
			intent = new Intent(this, PurseRollOutSubmitActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, PayeeInfoActivity.class);
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO, mAdapter.getItem(position - mLvPayee.getHeaderViewsCount()));
		startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_EDIT_PAYEE_INFO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_PAYEE_INFO:
			if (resultCode == Activity.RESULT_OK) {
				mPurseLogic.getPayeeList(getCompanyId(), null, DataReqType.REFRESH);
			}
			break;
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
}
