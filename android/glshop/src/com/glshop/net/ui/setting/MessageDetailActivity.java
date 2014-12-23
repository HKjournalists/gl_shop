package com.glshop.net.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.message.IMessageLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.mycontract.ContractInfoActivity;
import com.glshop.platform.api.DataConstants.MessageStatus;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 消息详情页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MessageDetailActivity extends BasicActivity {

	private static final String TAG = "MessageDetailActivity";

	private MessageInfoModel info;
	private String msgId;

	private TextView mTvType;
	private TextView mTvTime;
	private TextView mTvContent;
	private Button mBtnAction;

	private IMessageLogic mMessageLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_message_detail);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_msg_info);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.message_center);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.btn_action).setOnClickListener(this);

		mTvType = getView(R.id.iv_message_type);
		mTvTime = getView(R.id.iv_message_time);
		mTvContent = getView(R.id.iv_message_content);
		mBtnAction = getView(R.id.btn_action);
	}

	private void initData() {
		info = (MessageInfoModel) getIntent().getSerializableExtra(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_INFO);
		if (info != null) {
			mTvTime.setText(info.dateTime);
			mTvContent.setText(info.content);
			updateDataStatus(DataStatus.NORMAL);
			updateUI();
		} else {
			msgId = getIntent().getStringExtra(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_ID);
			if (StringUtils.isNotEmpty(msgId)) {
				updateDataStatus(DataStatus.LOADING);
				mMessageLogic.getMessageInfo(msgId);
			} else {
				showToast("消息ID不能为空!");
				finish();
			}
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mMessageLogic.getMessageInfo(msgId);
	}

	private void reportMsg() {
		if (info != null) {
			if (!info.isReported) {
				mMessageLogic.reportMessage(info);
			}
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_INFO_SUCCESS:
			onGetMessageInfoSuccess(respInfo);
			break;
		case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_INFO_FAILED:
			onGetMessageInfoFailed(respInfo);
			break;
		case GlobalMessageType.MsgCenterMessageType.MSG_REPORT_MESSAGE_READED_SUCCESS:
			onReportSuccess(respInfo);
			break;
		case GlobalMessageType.MsgCenterMessageType.MSG_REPORT_MESSAGE_READED_FAILED:
			onReportFailed(respInfo);
			break;
		}
	}

	private void onGetMessageInfoSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			info = (MessageInfoModel) respInfo.data;
			if (info != null) {
				updateUI();
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetMessageInfoFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	private void onReportSuccess(RespInfo respInfo) {
		Logger.e(TAG, "Report message success");
		if (info != null) {
			info.status = MessageStatus.READED;
			info.isReported = true;
		}
	}

	private void onReportFailed(RespInfo respInfo) {
		Logger.e(TAG, "Report message failed");
	}

	private void updateUI() {
		Logger.i(TAG, "Info = " + info);
		mTvTime.setText(info.dateTime);
		mTvContent.setText(info.content);
		switch (info.type) {
		case SYSTEM:
			mTvType.setText(getString(R.string.message_type_system));
			break;
		case ACTIVE:
			mTvType.setText(getString(R.string.message_type_active));
			break;
		}
		switch (info.businessType) {
		case TYPE_COMPANY_AUTH:
			//mBtnAction.setText(getString(R.string.message_action_repeat_auth));
			break;
		case TYPE_ORDER_FIND:

			break;
		case TYPE_CONTRACT_SIGN:
		case TYPE_CONTRACT_ING:
		case TYPE_CONTRACT_EVALUATION:
			mBtnAction.setVisibility(View.VISIBLE);
			mBtnAction.setText(getString(R.string.message_action_contract_info));
			break;
		}

		reportMsg();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_action:
			doAction();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void doAction() {
		Intent intent;
		switch (info.businessType) {
		case TYPE_COMPANY_AUTH:
			/*intent = new Intent(this, ProfileAuthActivity.class);
			startActivity(intent);*/
			break;
		case TYPE_ORDER_FIND:
			/*intent = new Intent(this, MainActivity.class);
			intent.setAction(GlobalAction.TipsAction.ACTION_VIEW_FIND_BUY);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();*/
			break;
		case TYPE_CONTRACT_SIGN:
		case TYPE_CONTRACT_ING:
		case TYPE_CONTRACT_EVALUATION:
			if (StringUtils.isNotEmpty(info.businessID)) {
				intent = new Intent(this, ContractInfoActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, info.businessID);
				startActivity(intent);
			}
			break;
		}
	}

	@Override
	protected void initLogics() {
		mMessageLogic = LogicFactory.getLogicByClass(IMessageLogic.class);
	}

}
