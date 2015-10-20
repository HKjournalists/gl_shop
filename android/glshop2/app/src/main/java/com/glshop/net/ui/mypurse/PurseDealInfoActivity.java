package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.mycontract.ContractInfoActivityV2;
import com.glshop.net.utils.DateUtils;
import com.glshop.net.utils.EnumUtil;
import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.DealInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 收支详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseDealInfoActivity extends BasicActivity {

	private static final String TAG = "PurseDealInfoActivity";

	private PurseType purseType;

	private String dealId;

	private DealInfoModel mDealInfoModel;

	private IPurseLogic mPurseLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_deal_info);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_deal_info);

		getView(R.id.view_contracts).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		dealId = getIntent().getStringExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_ID);
		if (StringUtils.isEmpty(dealId)) {
			showToast("交易ID为空！");
			finish();
		}

		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, PurseType.DEPOSIT.toValue()));
		if (purseType == PurseType.DEPOSIT) {
			((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.deposit_deal_info_title);
		} else {
			((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.payment_deal_info_title);
		}

		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getDealInfo(dealId);
	}

	private void updateUI() {
		if (mDealInfoModel != null) {
			((TextView) findViewById(R.id.tv_deal_id)).setText(mDealInfoModel.id);
			((TextView) findViewById(R.id.tv_deal_type)).setText(EnumUtil.parseDealType(mDealInfoModel.oprType));
			((TextView) findViewById(R.id.tv_deal_money)).setText(StringUtils.getCashNumber(String.valueOf(mDealInfoModel.dealMoney)));
			((TextView) findViewById(R.id.tv_pay_type)).setText(EnumUtil.parsePayType(mDealInfoModel.payType));
			((TextView) findViewById(R.id.tv_deal_time)).setText(DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.COMMON_DATE_FORMAT_V2, mDealInfoModel.dealTime));
			((TextView) findViewById(R.id.tv_balance)).setText(StringUtils.getCashNumber(String.valueOf(mDealInfoModel.balance)));

			if (StringUtils.isNotEmpty(mDealInfoModel.contractId)) {
				getView(R.id.view_contracts).setVisibility(View.VISIBLE);
			}

			if (mDealInfoModel.directionType == DealDirectionType.IN) {
				((TextView) findViewById(R.id.tv_deal_direction)).setText("收入金额(元)");
				((TextView) findViewById(R.id.tv_deal_money)).setText("+" + StringUtils.getCashNumber(String.valueOf(mDealInfoModel.dealMoney)));
				((TextView) findViewById(R.id.tv_deal_money)).setTextColor(getResources().getColor(R.color.green_deal));
			} else {
				((TextView) findViewById(R.id.tv_deal_direction)).setText("支出金额(元)");
				((TextView) findViewById(R.id.tv_deal_money)).setText("-" + StringUtils.getCashNumber(String.valueOf(mDealInfoModel.dealMoney)));
				((TextView) findViewById(R.id.tv_deal_money)).setTextColor(getResources().getColor(R.color.orange));
			}
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.PurseMessageType.MSG_GET_DEAL_INFO_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_GET_DEAL_INFO_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mDealInfoModel = (DealInfoModel) respInfo.data;
			updateUI();
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
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.PurseMessageType.MSG_GET_DEAL_INFO_FAILED:
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
		switch (v.getId()) {
		case R.id.view_contracts:
			Intent intent = new Intent(this, ContractInfoActivityV2.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mDealInfoModel.contractId);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_IS_GET_CONTRACT_MODEL, true);
			startActivity(intent);
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
	}

}
