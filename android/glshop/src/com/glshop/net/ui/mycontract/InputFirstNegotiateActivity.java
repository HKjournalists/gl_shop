package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 第一次议价内容编辑界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class InputFirstNegotiateActivity extends BaseContractInfoActivity {

	private EditText mEtNegUnitPirce;
	private EditText mEtNegReason;
	private TextView mTvNegTotalPrice;
	private TextView mTvNegChangeTotalPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_first_negotiate);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		((TextView) getView(R.id.tv_contract_status_info)).setText(getString(R.string.contract_to_first_negotiate_warning_tip));
		mTvContractId = getView(R.id.tv_contract_id);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		mContractStatus = getView(R.id.ll_contract_status);
		mEtNegUnitPirce = getView(R.id.et_negotiate_price);
		mEtNegReason = getView(R.id.et_negotiate_price_reason);
		mTvNegTotalPrice = getView(R.id.tv_neg_total_price);
		mTvNegChangeTotalPrice = getView(R.id.tv_neg_change_total_price);

		getView(R.id.btn_commmon_action).setOnClickListener(this);
		getView(R.id.btn_contract_action_discuss).setOnClickListener(this);
		getView(R.id.btn_contract_action_cancel).setOnClickListener(this);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mEtNegUnitPirce.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String negUnitPrice = mEtNegUnitPirce.getText().toString().trim();
				if (StringUtils.isNotEmpty(negUnitPrice)) {
					float negTotalPrice = Float.parseFloat(negUnitPrice) * mContractInfo.tradeAmount;
					mTvNegTotalPrice.setText(String.valueOf(negTotalPrice));
					mTvNegChangeTotalPrice.setText(String.valueOf(mContractInfo.finalPayMoney - negTotalPrice));
				} else {
					mTvNegTotalPrice.setText(String.valueOf(mContractInfo.finalPayMoney));
					mTvNegChangeTotalPrice.setText("0");
				}
			}
		});
	}

	private void initData() {
		mContractInfo = (ContractInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
		if (mContractInfo != null) {
			mTvContractId.setText(mContractInfo.contractId);
			mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(mContractInfo.expireTime)));
			mContractStatus.setContractInfo(mContractInfo);

			((TextView) getView(R.id.tv_unit_price)).setText(String.valueOf(mContractInfo.unitPrice));
			((TextView) getView(R.id.tv_trade_amount)).setText(String.valueOf(mContractInfo.tradeAmount));
			((TextView) getView(R.id.tv_total_price)).setText(String.valueOf(mContractInfo.finalPayMoney));
			((TextView) getView(R.id.tv_neg_total_price)).setText(String.valueOf(mContractInfo.finalPayMoney));
		} else {
			showToast("合同信息不能为空!");
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_NEGOTIATE_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_NEGOTIATE_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_contract_action_discuss:
			submitNegotiate();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void submitNegotiate() {
		if (checkArgs()) {
			showSubmitDialog();
			mContractLogic.contractNegotiate(getNegotiateInfo());
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isEmpty(mEtNegUnitPirce.getText().toString())) {
			showToast("单价不能为空!");
			return false;
		} else if (!StringUtils.checkNumber(mEtNegUnitPirce.getText().toString())) {
			showToast("单价不能为0!");
			return false;
		} else {
			return true;
		}
	}

	private NegotiateInfoModel getNegotiateInfo() {
		NegotiateInfoModel info = new NegotiateInfoModel();
		info.contractId = mContractInfo.contractId;
		if (BeanUtils.isNotEmpty(mContractInfo.firstNegotiateList)) {
			info.pid = mContractInfo.firstNegotiateList.get(0).pid;
		}
		info.negUnitPrice = Float.parseFloat(mEtNegUnitPirce.getText().toString());
		info.negAmount = mContractInfo.tradeAmount;
		info.reason = mEtNegReason.getText().toString();
		return info;
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);
		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_submit_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_submit_negotiate_success);
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

}
