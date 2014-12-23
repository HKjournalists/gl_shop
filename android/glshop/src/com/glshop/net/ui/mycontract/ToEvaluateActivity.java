package com.glshop.net.ui.mycontract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.ActivityReqCode;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.basic.view.EvaluationRatingBar;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同评价界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ToEvaluateActivity extends BaseContractInfoActivity {

	private EvaluationRatingBar mSatisfactionPerBar;
	private EvaluationRatingBar mSincerityPerBar;
	private TextView mTvEvaluationDetail;

	private EvaluationInfoModel mEvaInfo = new EvaluationInfoModel();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_evaluate);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.contract_evaluation);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
		getView(R.id.ll_input_detail).setOnClickListener(this);
		getView(R.id.btn_submit).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mTvContractId = getView(R.id.tv_contract_id);
		mTvEvaluationDetail = getView(R.id.tv_evaluation_detail);
		mSatisfactionPerBar = getView(R.id.rb_satisfaction_per);
		mSincerityPerBar = getView(R.id.rb_satisfaction_per);
	}

	private void initData() {
		if (mContractInfo != null) {
			mTvContractId.setText(mContractInfo.contractId);
		} else {
			showToast("合同信息不能为空!");
			finish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_EVALUATE_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_EVALUATE_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_contract_info_detail:
			intent = new Intent(this, ContractDetailActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivity(intent);
			break;
		case R.id.ll_input_detail:
			intent = new Intent(this, InputEvaluationDetailActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_EVALUATION_DESCRIPTION, mEvaInfo);
			startActivityForResult(intent, ActivityReqCode.REQ_EDIT_EVALUATION_DESCRIPTION);
			break;
		case R.id.btn_submit:
			submitEvaluate();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void submitEvaluate() {
		showSubmitDialog();
		mContractLogic.contractEvaluate(getEvaluateInfo());
	}

	private EvaluationInfoModel getEvaluateInfo() {
		if (mContractInfo.buyType == BuyType.BUYER) {
			mEvaInfo.beEvaluaterCID = mContractInfo.sellCompanyId;
		} else {
			mEvaInfo.beEvaluaterCID = mContractInfo.buyCompanyId;
		}
		mEvaInfo.contractId = mContractInfo.contractId;
		mEvaInfo.satisfactionPer = mSatisfactionPerBar.getRate();
		mEvaInfo.sincerityPer = mSincerityPerBar.getRate();
		//mEvaInfo.content = mTvEvaluationDetail.getText().toString();
		return mEvaInfo;
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();

		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_submit_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_evaluation_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_EVALUATION_DESCRIPTION:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					mEvaInfo = (EvaluationInfoModel) data.getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_EVALUATION_DESCRIPTION);
					if (mEvaInfo != null) {
						if (StringUtils.isNotEmpty(mEvaInfo.content)) {
							mTvEvaluationDetail.setText(mEvaInfo.content);
						} else {
							mTvEvaluationDetail.setText("暂无描述");
						}
					}
				}
			}
			break;
		}
	}

}
