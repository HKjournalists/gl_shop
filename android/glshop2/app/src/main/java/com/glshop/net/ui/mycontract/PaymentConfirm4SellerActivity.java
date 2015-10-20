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
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.platform.api.DataConstants.ContractConfirmType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.StringUtils;

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
	private TextView tv_last_total_money;

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
		tv_last_total_money=getView(R.id.tv_last_total_money);
		getView(R.id.ll_contract_model_info).setOnClickListener(this);
		getView(R.id.btn_seller_confirm).setOnClickListener(this);
		getView(R.id.btn_seller_apply_arbitrate).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		updateBuyInfo();

		if (mContractInfo.finalNegotiateInfo != null) {
			NegotiateInfoModel negInfo = mContractInfo.finalNegotiateInfo;
			mTvFinalUnitPrice.setText(StringUtils.getDefaultNumber(negInfo.negUnitPrice));
			mTvFinalAmount.setText(StringUtils.getDefaultNumber(negInfo.negAmount));
			mTvFinalTotalMoney.setText(StringUtils.getCashNumber(negInfo.negUnitPrice * negInfo.negAmount));

		} else {
			mTvFinalUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));
			mTvFinalAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
			mTvFinalTotalMoney.setText(StringUtils.getCashNumber(mContractInfo.tradeAmount * mContractInfo.unitPrice));
		}

		mTvOriUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));
		mTvOriAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
		mTvOriTotalMoney.setText(StringUtils.getCashNumber(mContractInfo.tradeAmount * mContractInfo.unitPrice));
		tv_last_total_money.setText(StringUtils.getDefaultNumber(mContractInfo.finalyAmount));
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
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);

		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		if (respInfo.intArg1 == ContractConfirmType.PAYMENT_AGREE.toValue()) {
			tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_confirm_agree_success);
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
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_contract_model_info:
			Intent intent = new Intent(this, ContractModelInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
			startActivity(intent);
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
		mConfirmDialog.setContent(getString(R.string.confirmed_contract_seller_confirm_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				mContractLogic.multiConfirmContract(mContractInfo.contractId, ContractConfirmType.PAYMENT_AGREE, "", "","");
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
				mContractLogic.multiConfirmContract(mContractInfo.contractId, ContractConfirmType.PAYMENT_ARBITRATE, "", "","");
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
