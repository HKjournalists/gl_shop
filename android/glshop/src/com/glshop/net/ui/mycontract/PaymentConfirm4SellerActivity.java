package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
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

/**
 * @Description : 卖家货款确认界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PaymentConfirm4SellerActivity extends BaseContractInfoActivity {

	private TextView mTvOriUnitPrice;
	private TextView mTvOriAmount;
	private TextView mTvOriTotalMoney;
	private TextView mTvFinalUnitPrice;
	private TextView mTvFinalAmount;
	private TextView mTvFinalTotalMoney;

	private ConfirmDialog mConfirmDialog;
	private ConfirmDialog mArbitarteDialog;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_confirm_4_seller);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.menu_my_contract);

		mTvOriUnitPrice = getView(R.id.tv_ori_unit_price);
		mTvOriAmount = getView(R.id.tv_ori_amount);
		mTvOriTotalMoney = getView(R.id.tv_ori_total_money);
		mTvFinalUnitPrice = getView(R.id.tv_final_unit_price);
		mTvFinalAmount = getView(R.id.tv_final_amount);
		mTvFinalTotalMoney = getView(R.id.tv_final_total_money);

		getView(R.id.ll_contract_model_info).setOnClickListener(this);
		getView(R.id.btn_seller_confirm).setOnClickListener(this);
		getView(R.id.btn_seller_apply_arbitrate).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {

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
		case R.id.btn_seller_confirm:
			showConfirmDialog();
			break;
		case R.id.btn_seller_apply_arbitrate:
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
				mContractLogic.multiConfirmContract(mContractId, ContractConfirmType.PAYMENT_APPLY, "", "");
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

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
