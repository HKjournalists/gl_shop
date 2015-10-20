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
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.platform.api.DataConstants.ContractConfirmType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 买家货款确认界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PaymentConfirm4BuyerActivity extends BaseContractInfoActivity {

	private EditText mEtFinalUnitPrice;
	private EditText mEtFinalAmount;
	private TextView mTvFinalTotalMoney;
	private TextView mTvOriUnitPrice;
	private TextView mTvOriAmount;
	private TextView mTvOriTotalMoney;

	private ConfirmDialog mConfirmDialog;
	private ConfirmDialog mArbitarteDialog;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_confirm_4_buyer);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.menu_my_contract);

		mEtFinalUnitPrice = getView(R.id.et_final_unit_price);
		mEtFinalAmount = getView(R.id.et_final_amount);
		mTvFinalTotalMoney = getView(R.id.tv_final_total_money);
		mTvOriUnitPrice = getView(R.id.tv_ori_unit_price);
		mTvOriAmount = getView(R.id.tv_ori_amount);
		mTvOriTotalMoney = getView(R.id.tv_ori_total_money);

		getView(R.id.ll_contract_model_info).setOnClickListener(this);
		getView(R.id.btn_buyer_confirm).setOnClickListener(this);
		getView(R.id.btn_buyer_apply_arbitrate).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mEtFinalUnitPrice.addTextChangedListener(mUnitPriceTextWatcher);
		mEtFinalAmount.addTextChangedListener(mAmountTextWatcher);
	}

	private void initData() {
		updateBuyInfo();

		mEtFinalUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));
		mEtFinalAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
		mTvFinalTotalMoney.setText(StringUtils.getCashNumber(mContractInfo.tradeAmount * mContractInfo.unitPrice));

		mTvOriUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));
		mTvOriAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
		mTvOriTotalMoney.setText(StringUtils.getCashNumber(mContractInfo.tradeAmount * mContractInfo.unitPrice));

		requestSelection(mEtFinalUnitPrice);
		requestSelection(mEtFinalAmount);
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
		case ContractMessageType.MSG_MULTI_COMFIRM_CONTRACT_SUCCESS:
			onConfirmSuccess(respInfo);
			break;
		case ContractMessageType.MSG_MULTI_COMFIRM_CONTRACT_FAILED:
			onConfirmFailed(respInfo);
			break;
		}
	}

	private void onConfirmSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING);

		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
		if (respInfo.intArg1 == ContractConfirmType.PAYMENT_APPLY.toValue()) {
			tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_confirm_apply_success);
		} else {
			tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_apply_success_title);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_arbitarte_apply_success);
		}
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onConfirmFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100003005))) {
					showToast(getString(R.string.error_code_100003005, GlobalConstants.CfgConstants.MAX_UNIT_PRICE));
				} else if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100003006))) {
					showToast(getString(R.string.error_code_100003006, GlobalConstants.CfgConstants.MAX_AMOUNT));
				} else {
					handleErrorAction(respInfo);
				}
			} else {
				handleErrorAction(respInfo);
			}
		}
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case ContractMessageType.MSG_MULTI_COMFIRM_CONTRACT_FAILED:
				showToast(R.string.error_req_submit_info);
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
		case R.id.ll_contract_model_info:
			Intent intent = new Intent(this, ContractModelInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
			startActivity(intent);
			break;
		case R.id.btn_buyer_confirm:
			if (checkArgs()) {
				showConfirmDialog();
			}
			break;
		case R.id.btn_buyer_apply_arbitrate:
			showArbitarteDialog();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void showConfirmDialog() {
		closeDialog(mConfirmDialog);
		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.confirmed_contract_buyer_confirm_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				String disPrice = mEtFinalUnitPrice.getText().toString();
				String disAmount = mEtFinalAmount.getText().toString();
				mContractLogic.multiConfirmContract(mContractInfo.contractId, ContractConfirmType.PAYMENT_APPLY, disPrice, disAmount);
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mConfirmDialog.show();
	}

	private void showArbitarteDialog() {
		closeDialog(mConfirmDialog);
		mArbitarteDialog = new ConfirmDialog(this, R.style.dialog);
		mArbitarteDialog.setContent(getString(R.string.contract_arbitarte_warning_tip));
		mArbitarteDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				mContractLogic.multiConfirmContract(mContractInfo.contractId, ContractConfirmType.PAYMENT_ARBITRATE, "", "");
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mArbitarteDialog.show();
	}

	private boolean checkArgs() {
		if (StringUtils.isNEmpty(mEtFinalUnitPrice.getText().toString())) {
			showToast("请输入合同单价!");
			return false;
		} else if (StringUtils.isNEmpty(mEtFinalAmount.getText().toString())) {
			showToast("请输入实际总量!");
			return false;
		} else if (!checkUnitArgs()) {
			return false;
		} else if (!checkAmountArgs()) {
			return false;
		}
		return true;
	}

	private TextWatcher mUnitPriceTextWatcher = new TextWatcher() {

		private int editStart;
		private int editEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = mEtFinalUnitPrice.getSelectionStart();
			editEnd = mEtFinalUnitPrice.getSelectionEnd();
			String data = mEtFinalUnitPrice.getEditableText().toString();
			if (StringUtils.isDouble(data) && !StringUtils.checkDecimal(data, 2)) {
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				mEtFinalUnitPrice.setText(s);
				mEtFinalUnitPrice.setSelection(tempSelection);
			}

			String negUnitPrice = mEtFinalUnitPrice.getText().toString().trim();
			String negAmount = mEtFinalAmount.getText().toString().trim();
			if (StringUtils.isNotEmpty(negUnitPrice) && StringUtils.isNotEmpty(negAmount)) {
				mTvFinalTotalMoney.setText(StringUtils.getCashNumber(Double.parseDouble(negUnitPrice) * Double.parseDouble(negAmount)));
			} else {
				mTvFinalTotalMoney.setText("0.00");
			}
			checkUnitArgs();
		}
	};

	private TextWatcher mAmountTextWatcher = new TextWatcher() {

		private int editStart;
		private int editEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = mEtFinalAmount.getSelectionStart();
			editEnd = mEtFinalAmount.getSelectionEnd();
			String data = mEtFinalAmount.getEditableText().toString();
			if (StringUtils.isDouble(data) && !StringUtils.checkDecimal(data, 2)) {
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				mEtFinalAmount.setText(s);
				mEtFinalAmount.setSelection(tempSelection);
			}

			String negUnitPrice = mEtFinalUnitPrice.getText().toString().trim();
			String negAmount = mEtFinalAmount.getText().toString().trim();
			if (StringUtils.isNotEmpty(negUnitPrice) && StringUtils.isNotEmpty(negAmount)) {
				mTvFinalTotalMoney.setText(StringUtils.getCashNumber(Double.parseDouble(negUnitPrice) * Double.parseDouble(negAmount)));
			} else {
				mTvFinalTotalMoney.setText("0.00");
			}
			checkAmountArgs();
		}
	};

	private boolean checkUnitArgs() {
		String negUnitPrice = mEtFinalUnitPrice.getText().toString().trim();
		if (StringUtils.isNotEmpty(negUnitPrice)) {
			double unitPrice = Double.parseDouble(negUnitPrice);
			if (unitPrice > mContractInfo.unitPrice) {
				showToast("真实价格不能高于合同价格，不能输入!");
				return false;
			} else if (unitPrice > GlobalConstants.CfgConstants.MAX_UNIT_PRICE) {
				showToast(getString(R.string.error_code_100003005, GlobalConstants.CfgConstants.MAX_UNIT_PRICE));
				return false;
			}
		}
		return true;
	}

	private boolean checkAmountArgs() {
		String negAmount = mEtFinalAmount.getText().toString().trim();
		if (StringUtils.isNotEmpty(negAmount)) {
			double amount = Double.parseDouble(negAmount);
			if (amount > mContractInfo.tradeAmount) {
				showToast("真实重量不能高于合同重量，不能输入!");
				return false;
			} else if (amount > GlobalConstants.CfgConstants.MAX_AMOUNT) {
				showToast(getString(R.string.error_code_100003006, GlobalConstants.CfgConstants.MAX_AMOUNT));
				return false;
			}
		}
		return true;
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
