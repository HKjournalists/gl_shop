package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.common.GlobalConstants.TipActionBackType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.PurseMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.pay.framework.IPay;
import com.glshop.net.logic.pay.framework.IPayCallback;
import com.glshop.net.logic.pay.framework.OrderInfo;
import com.glshop.net.logic.pay.framework.PayFactory;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.mycontract.BaseContractInfoActivity;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.platform.api.DataConstants.PayResultType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.pay.data.model.TradeNoInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 选择充值或支付方式界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class SelectRechargeTypeActivity extends BaseContractInfoActivity {

	private static final String TAG = "SelectRechargeTypeActivity";

	private TextView mTvTitle;
	private TextView mTvPayMoneyType;
	private TextView mTvPayMoney;
	private TextView mTvPayType;

	private View mItemOnline;
	private View mItemBank;
	private View mItemOffline;

	private double needPayMoney;
	private PurseType purseType = PurseType.DEPOSIT;
	private PursePayType payType;

	private OrderInfo mOrderInfo;

	private IPurseLogic mPurseLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_select_recharge_type);
		initView();
		initData();
	}

	private void initView() {
		mTvTitle = getView(R.id.tv_commmon_title);
		mTvPayMoneyType = getView(R.id.tv_to_pay_money_type);
		mTvPayMoney = getView(R.id.tv_to_pay_money);
		mTvPayType = getView(R.id.tv_select_recharge_type);
		mItemOnline = getView(R.id.ll_recharge_online);
		mItemBank = getView(R.id.ll_recharge_bank);
		mItemOffline = getView(R.id.ll_recharge_offline);

		getView(R.id.iv_common_back).setOnClickListener(this);

		mItemOnline.setOnClickListener(this);
		mItemBank.setOnClickListener(this);
		mItemOffline.setOnClickListener(this);
	}

	private void initData() {
		needPayMoney = getIntent().getDoubleExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, 0);
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		payType = PursePayType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.RECHARGE.toValue()));
		
		
		if(PurseType.PAYMENT == purseType){
			((TextView)findViewById(R.id.recharge_tips_v)).setText(R.string.recharge_payx_security_tips);
		}
		
		switch (payType) {
		case PAYMENT:
			mTvTitle.setText(R.string.purse_payment);
			//mItemOnline.setTitle(getString(R.string.payment_type_online));
			mTvPayMoneyType.setText(getString(R.string.purse_to_pay_payment));
			mTvPayMoney.setText(StringUtils.getCashNumber(needPayMoney));
			break;
		case RECHARGE:
			mTvTitle.setText(R.string.purse_recharge);
			//mItemOnline.setTitle(getString(R.string.recharge_type_online));
			mTvPayMoneyType.setText(getString(R.string.purse_to_recharge));
			mTvPayMoney.setText(StringUtils.getCashNumber(needPayMoney));
			break;
		}
		
		
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case PurseMessageType.MSG_GET_PAY_TN_SUCCESS:
			onGetTnSuccess(respInfo);
			break;
		case PurseMessageType.MSG_GET_PAY_TN_FAILED:
			onGetTnFailed(respInfo);
			break;
		}
	}

	private void onGetTnSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
			TradeNoInfoModel tnInfo = (TradeNoInfoModel) respInfo.data;
			if (tnInfo != null) {
				Logger.e(TAG, "GetTn = " + tnInfo.tn);
				if (StringUtils.isNotEmpty(tnInfo.tn)) {
					mOrderInfo = new OrderInfo();
					mOrderInfo.setTradeNo(tnInfo.tn);
					mOrderInfo.setOrderNo(tnInfo.oid);
					showPayTool(mOrderInfo);
				} else {
					showToast("获取银联交易流水号失败，请稍后重试!");
				}
			}
		}
	}

	private void onGetTnFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			handleErrorAction(respInfo);
		}
	}

	private void showPayTool(OrderInfo orderInfo) {
		IPay payTool = PayFactory.newInstance(this);
		String result = payTool.pay(orderInfo, new IPayCallback() {

			@Override
			public void onPayRespone(int respCode, String result) {
				Log.i(TAG, "Pay Callback: " + result);
			}
		});
		Log.i(TAG, "Pay Result Detail: " + result);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_recharge_online:
			switch (payType) {
			case PAYMENT:
				// 货款账户支付
				intent = new Intent(this, PaymentSubmitActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, needPayMoney);
				startActivity(intent);
				break;
			case RECHARGE:
				// 调用网银API充值
				getPayTradeNo();
				break;
			}
			break;

		case R.id.ll_recharge_bank:
			intent = new Intent(this, RechargeByBankActivity.class);
			if (payType == PursePayType.PAYMENT) {
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
			} else {
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			}
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, needPayMoney);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, payType.toValue());
			startActivity(intent);
			break;

		case R.id.ll_recharge_offline:
			intent = new Intent(this, RechargeByOfflineActivity.class);
			if (payType == PursePayType.PAYMENT) {
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
			} else {
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, purseType.toValue());
			}
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, needPayMoney);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, payType.toValue());
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void getPayTradeNo() {
		closeSubmitDialog();
		showSubmitDialog();
		mInvoker = String.valueOf(System.currentTimeMillis());
		mPurseLogic.getPayTradeNo(mInvoker, purseType, needPayMoney);
	}

	private void rptPayResult() {
		if (mOrderInfo != null) {
			mPurseLogic.rptPayResult("", mOrderInfo.getOrderNo(), PayResultType.SUCCESS);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*************************************************
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 ************************************************/
		if (data == null) {
			return;
		}

		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = "支付成功！";
			onPaySuccess();
		} else if (str.equalsIgnoreCase("fail")) {
			msg = "支付失败！";
			onPayFailed();
		} else if (str.equalsIgnoreCase("cancel")) {
			msg = "用户取消了支付";
		} else {
			msg = "未知";
		}
		Logger.e(TAG, "Union Pay Result = " + msg);
	}

	private void onPaySuccess() {
		rptPayResult();

		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO);

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();

		tipInfo.operatorTipTitle = getString(R.string.pay_success_title);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_online_pay_success_title);
		if (purseType == PurseType.DEPOSIT) {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_online_deposit_pay_success_content, needPayMoney);
		} else {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_online_payment_pay_success_content, needPayMoney);
		}
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_myaccount);

		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE;
		tipInfo.backType = TipActionBackType.DO_ACTION1;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onPayFailed() {
		showToast("支付失败，请稍后重试!");
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
	}
}
