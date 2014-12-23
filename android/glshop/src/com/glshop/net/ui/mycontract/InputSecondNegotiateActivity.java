package com.glshop.net.ui.mycontract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.basic.view.listitem.BuyEditItemView;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 第二次议价内容编辑界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class InputSecondNegotiateActivity extends BaseContractInfoActivity {

	private BuyEditItemView mItemEtNegAmount;

	private float simpleCheckNegUnitPrice;

	private float modifyUnitPrice;

	private NegotiateInfoModel preNegInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_second_negotiate);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		((TextView) getView(R.id.tv_contract_status_info)).setText(getString(R.string.contract_to_second_negotiate_warning_tip));
		mItemEtNegAmount = getView(R.id.item_neg_amount);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		//mContractStatus = getView(R.id.ll_contract_status);

		getView(R.id.btn_commmon_action).setOnClickListener(this);
		getView(R.id.btn_contract_action_discuss).setOnClickListener(this);
		getView(R.id.btn_contract_action_cancel).setOnClickListener(this);
		getView(R.id.btn_modify_final_unit_price).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mItemEtNegAmount.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String negAmount = mItemEtNegAmount.getEditText().getText().toString().trim();
				if (StringUtils.isNotEmpty(negAmount)) {
					float negTotalPrice = Float.parseFloat(negAmount) * mContractInfo.tradeAmount;
					((TextView) getView(R.id.tv_neg_amount)).setText(negAmount);
					((TextView) getView(R.id.tv_neg_total_price)).setText(String.valueOf(negTotalPrice));
				} else {
					((TextView) getView(R.id.tv_neg_amount)).setText("0");
					((TextView) getView(R.id.tv_neg_total_price)).setText("0");
				}
			}
		});
	}

	private void initData() {
		mContractInfo = (ContractInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
		if (mContractInfo != null) {
			mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(mContractInfo.expireTime)));
			//mContractStatus.setContractInfo(mContractInfo);
			if (BeanUtils.isNotEmpty(mContractInfo.firstNegotiateList)) {
				NegotiateInfoModel preNegInfo = mContractInfo.firstNegotiateList.get(0);
				if (preNegInfo != null) {
					simpleCheckNegUnitPrice = preNegInfo.negUnitPrice;
				}
			}

			if (simpleCheckNegUnitPrice == 0) {
				simpleCheckNegUnitPrice = mContractInfo.unitPrice;
			}

			if (BeanUtils.isNotEmpty(mContractInfo.secondNegotiateList)) {
				preNegInfo = mContractInfo.secondNegotiateList.get(0);
			}

			((BuyTextItemView) getView(R.id.tv_trade_amount)).setContentText(String.valueOf(mContractInfo.tradeAmount)); // 合同原始总量
			((BuyTextItemView) getView(R.id.tv_unit_price)).setContentText(String.valueOf(mContractInfo.unitPrice)); // 合同原始单价
			((BuyTextItemView) getView(R.id.tv_pre_neg_unit_price)).setContentText(String.valueOf(simpleCheckNegUnitPrice)); // 第一次抽样后单价
			((BuyTextItemView) getView(R.id.tv_neg_unit_price)).setContentText(String.valueOf(simpleCheckNegUnitPrice)); // 结算单价
			// 结算账单
			((TextView) getView(R.id.tv_final_neg_unit_price)).setText(String.valueOf(simpleCheckNegUnitPrice)); // 结算单价
			((TextView) getView(R.id.tv_neg_amount)).setText("0"); // 实际总卸货量
			((TextView) getView(R.id.tv_total_price)).setText(String.valueOf(mContractInfo.finalPayMoney)); // 合同总价
			((TextView) getView(R.id.tv_neg_total_price)).setText("0"); // 最终价格

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
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_modify_final_unit_price:
			intent = new Intent(this, ModifyFinalUnitPriceActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_EDIT_UNIT_PRICE, modifyUnitPrice);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_EDIT_CONTRACT_FINAL_UNIT_PRICE);
			break;
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
		if (StringUtils.isEmpty(mItemEtNegAmount.getContentText())) {
			showToast("实际卸货总量不能为空!");
			return false;
		} else if (!StringUtils.checkNumber(mItemEtNegAmount.getContentText())) {
			showToast("实际卸货总量不能为0!");
			return false;
		} else {
			return true;
		}
	}

	private NegotiateInfoModel getNegotiateInfo() {
		NegotiateInfoModel info = new NegotiateInfoModel();
		info.contractId = mContractInfo.contractId;
		if (BeanUtils.isNotEmpty(mContractInfo.secondNegotiateList)) {
			info.pid = mContractInfo.secondNegotiateList.get(0).pid;
		}
		if (modifyUnitPrice != 0) {
			info.negUnitPrice = modifyUnitPrice;
		} else {
			info.negUnitPrice = simpleCheckNegUnitPrice;
		}
		info.negAmount = Float.parseFloat(mItemEtNegAmount.getContentText());
		Logger.e(TAG, "NegInfo = " + info);
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_CONTRACT_FINAL_UNIT_PRICE:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					modifyUnitPrice = data.getFloatExtra(GlobalAction.ContractAction.EXTRA_KEY_EDIT_UNIT_PRICE, 0);
					if (modifyUnitPrice != 0) {
						((BuyTextItemView) getView(R.id.tv_neg_unit_price)).setContentText(String.valueOf(modifyUnitPrice));
					}
				}
			}
			break;
		}
	}

}
