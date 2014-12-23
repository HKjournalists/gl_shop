package com.glshop.net.ui.mycontract;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
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
import com.glshop.net.ui.basic.adapter.EvaluationListAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.basic.view.PullRefreshListView.OnRefreshListener;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 企业评价列表页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class CompanyEvaluationListActivity extends BasicActivity {

	private String companyId;

	private PullRefreshListView mLvEvaluationList;
	private EvaluationListAdapter mAdapter;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_company_evaluation_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();

		((TextView) getView(R.id.tv_commmon_title)).setText("企业评价");
		getView(R.id.iv_common_back).setOnClickListener(this);

		mLvEvaluationList = getView(R.id.lv_evaluation_list);
		mLvEvaluationList.setIsRefreshable(true);
		mLvEvaluationList.hideFootView();
		mLvEvaluationList.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				Logger.e(TAG, "---onRefresh---");
				mContractLogic.getCompanyEvaluationList(companyId, DataReqType.REFRESH);
			}
		});
		mLvEvaluationList.setNewScrollerListener(null);
		mAdapter = new EvaluationListAdapter(this, null);
		mLvEvaluationList.setAdapter(mAdapter);

		mNormalDataView = mLvEvaluationList;
	}

	private void initData() {
		companyId = getIntent().getStringExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_ID);
		if (StringUtils.isNotEmpty(companyId)) {
			updateDataStatus(DataStatus.LOADING);
			mContractLogic.getCompanyEvaluationList(companyId, DataReqType.INIT);
		} else {
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getCompanyEvaluationList(companyId, DataReqType.INIT);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ContractMessageType.MSG_GET_COMPANY_EVALUATION_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_GET_COMPANY_EVALUATION_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		List data = DataCenter.getInstance().getData(DataType.EVALUATION_LIST);
		if (BeanUtils.isNotEmpty(data)) {
			mAdapter.setList(data);
			mLvEvaluationList.onRefreshSuccess();
			mLvEvaluationList.showLoadFinish();
			mLvEvaluationList.setSelection(0);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
