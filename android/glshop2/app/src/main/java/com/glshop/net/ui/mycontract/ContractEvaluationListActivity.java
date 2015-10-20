package com.glshop.net.ui.mycontract;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同评价列表界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractEvaluationListActivity extends BasicActivity {

	private TextView mTvMyEvaluation;
	private TextView mTvAnotherEvaluation;

	private Button mBtnMyEvaluation;
	private Button mBtnAnotherEvaluation;

	private RatingBar mRbMySatisfaction;
	private RatingBar mRbOtherSatisfaction;
	private RatingBar mRbMySincerity;
	private RatingBar mRbOtherSincerity;

	private boolean isMyEvaSingleLine = true;
	private boolean isAnotherEvaSingleLine = true;

	private List<EvaluationInfoModel> data;

	private ContractInfoModel mContractInfo;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_evaluation_list);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_contract_evaluation_list);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.contract_evaluation);

		mTvMyEvaluation = getView(R.id.tv_my_evaluation_detail);
		mTvAnotherEvaluation = getView(R.id.tv_another_evaluation_detail);
		mBtnMyEvaluation = getView(R.id.btn_my_evaluation_detail);
		mBtnAnotherEvaluation = getView(R.id.btn_another_evaluation_detail);

		mRbMySatisfaction = getView(R.id.my_rb_satisfaction);
		mRbOtherSatisfaction = getView(R.id.other_rb_satisfaction);
		mRbMySincerity = getView(R.id.my_rb_sincerity);
		mRbOtherSincerity = getView(R.id.other_rb_sincerity);

		getView(R.id.btn_my_evaluation_detail).setOnClickListener(this);
		getView(R.id.btn_another_evaluation_detail).setOnClickListener(this);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
		getView(R.id.ll_contract_model_info).setOnClickListener(this);
		getView(R.id.btn_to_evaluate).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

	}

	private void initData() {
		mContractInfo = (ContractInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
		if (mContractInfo != null) {
			((TextView) getView(R.id.tv_contract_id)).setText(mContractInfo.contractId);
			updateDataStatus(DataStatus.LOADING);
			mContractLogic.getContractEvaluationList(mContractInfo.contractId);
		} else {
			showToast("合同信息不能为空!");
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContractEvaluationList(mContractInfo.contractId);
	}

	private void updateUI() {
		updateBuyInfo();

		if (BeanUtils.isNotEmpty(data)) {
			EvaluationInfoModel myEvaInfo = null;
			EvaluationInfoModel anotherEvaInfo = null;
			for (EvaluationInfoModel info : data) {
				if (getCompanyId().equals(info.evaluaterCID)) {
					myEvaInfo = info;
				} else {
					anotherEvaInfo = info;
				}
			}

			if (myEvaInfo != null) {
				getView(R.id.ll_my_evaluation_empty).setVisibility(View.GONE);
				getView(R.id.ll_my_evaluation_not_empty).setVisibility(View.VISIBLE);
				getView(R.id.btn_to_evaluate).setVisibility(View.GONE);
				mRbMySatisfaction.setRating(myEvaInfo.satisfactionPer);
				mRbMySincerity.setRating(myEvaInfo.sincerityPer);
				mTvMyEvaluation.setText(myEvaInfo.content);
				getView(R.id.btn_to_evaluate).setVisibility(View.GONE);
			} else {
				getView(R.id.ll_my_evaluation_empty).setVisibility(View.VISIBLE);
				getView(R.id.ll_my_evaluation_not_empty).setVisibility(View.GONE);
				getView(R.id.btn_to_evaluate).setVisibility(View.VISIBLE);
			}

			if (anotherEvaInfo != null) {
				getView(R.id.ll_another_evaluation_empty).setVisibility(View.GONE);
				getView(R.id.ll_another_evaluation_not_empty).setVisibility(View.VISIBLE);
				mRbOtherSatisfaction.setRating(anotherEvaInfo.satisfactionPer);
				mRbOtherSincerity.setRating(anotherEvaInfo.sincerityPer);
				mTvAnotherEvaluation.setText(anotherEvaInfo.content);
			} else {
				getView(R.id.ll_another_evaluation_empty).setVisibility(View.VISIBLE);
				getView(R.id.ll_another_evaluation_not_empty).setVisibility(View.GONE);
			}

		} else {
			getView(R.id.ll_another_evaluation_empty).setVisibility(View.VISIBLE);
			getView(R.id.ll_another_evaluation_not_empty).setVisibility(View.GONE);
			getView(R.id.ll_my_evaluation_empty).setVisibility(View.VISIBLE);
			getView(R.id.ll_my_evaluation_not_empty).setVisibility(View.GONE);
			getView(R.id.btn_to_evaluate).setVisibility(View.VISIBLE);
		}
	}

	private void updateBuyInfo() {
		TextView tvProductSpec = getView(R.id.tv_buy_product_spec);
		tvProductSpec.setText(mContractInfo.productName);

		TextView tvUnitPrice = getView(R.id.tv_buy_unit_price);
		tvUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));

		TextView mTvBuyAmount = getView(R.id.tv_buy_amount);
		mTvBuyAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACTS_EVALUATION_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACTS_EVALUATION_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			data = (ArrayList<EvaluationInfoModel>) respInfo.data;
			updateDataStatus(DataStatus.NORMAL);
			updateUI();
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
			case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACTS_EVALUATION_FAILED:
				showToast(R.string.error_req_get_info);
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
		Drawable icon = null;
		switch (v.getId()) {
		case R.id.btn_contract_info_detail:
			intent = new Intent(this, ContractModelInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
			startActivity(intent);
			break;
		case R.id.ll_contract_model_info:
			intent = new Intent(this, ContractModelInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
			startActivity(intent);
			break;
		case R.id.btn_to_evaluate:
			intent = new Intent(this, ToEvaluateActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivity(intent);
			break;
		case R.id.btn_my_evaluation_detail:
			isMyEvaSingleLine = !isMyEvaSingleLine;
			mTvMyEvaluation.setSingleLine(isMyEvaSingleLine);
			icon = isMyEvaSingleLine ? getResources().getDrawable(R.drawable.ic_shrink) : getResources().getDrawable(R.drawable.ic_expand);
			icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
			mBtnMyEvaluation.setCompoundDrawables(icon, null, null, null);
			mBtnMyEvaluation.setText(isMyEvaSingleLine ? getString(R.string.expand) : getString(R.string.shrink));
			break;
		case R.id.btn_another_evaluation_detail:
			isAnotherEvaSingleLine = !isAnotherEvaSingleLine;
			mTvAnotherEvaluation.setSingleLine(isAnotherEvaSingleLine);
			icon = isAnotherEvaSingleLine ? getResources().getDrawable(R.drawable.ic_shrink) : getResources().getDrawable(R.drawable.ic_expand);
			icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
			mBtnAnotherEvaluation.setCompoundDrawables(icon, null, null, null);
			mBtnAnotherEvaluation.setText(isAnotherEvaSingleLine ? getString(R.string.expand) : getString(R.string.shrink));
			break;
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
