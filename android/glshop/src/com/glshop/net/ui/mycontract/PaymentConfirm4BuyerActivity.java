package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
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
	}

	private void initData() {
		//TODO
		requestSelection(mEtFinalUnitPrice);
		requestSelection(mEtFinalAmount);
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
		case ContractMessageType.MSG_APPLY_ARBITARTE_SUCCESS:
			onArbitarteSuccess(respInfo);
			break;
		case ContractMessageType.MSG_APPLY_ARBITARTE_FAILED:
			onArbitarteFailed(respInfo);
			break;
		}
	}

	private void onConfirmSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_cancel_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_CONTRACT;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onArbitarteSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onArbitarteFailed(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_cancel_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_CONTRACT;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onConfirmFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_contract_model_info:
			//TODO
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
		mConfirmDialog.setContent(getString(R.string.confirmed_contract_payment_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				String disPrice = mEtFinalUnitPrice.getText().toString();
				String disAmount = mEtFinalAmount.getText().toString();
				mContractLogic.multiConfirmContract(mContractId, ContractConfirmType.PAYMENT_APPLY, disPrice, disAmount);
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
				mContractLogic.multiConfirmContract(mContractId, ContractConfirmType.PAYMENT_ARBITRATE, "", "");
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
		}
		return true;
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
