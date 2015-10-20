package com.glshop.net.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.ReqSendType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 设置主界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class SettingActivity extends BasicActivity {

	private TextView mTvAppVersion;

	private ImageView mIvNewVersion;

	private UpgradeInfoModel mUpgradeInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		initData();
	}

	private void initView() {
		mTvAppVersion = getView(R.id.tv_app_version);
		mIvNewVersion = getView(R.id.iv_new_version);

		getView(R.id.ll_about_us).setOnClickListener(this);
		getView(R.id.ll_platform_intro).setOnClickListener(this);
		getView(R.id.ll_platform_qa_list).setOnClickListener(this);
		getView(R.id.ll_platform_custom_service_phone).setOnClickListener(this);
		getView(R.id.ll_app_upgrade).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mTvAppVersion.setText(getString(R.string.main_page_title) + "V" + ActivityUtil.getVersionName(this));
		mUpgradeLogic.getUpgradeInfo(ActivityUtil.getAppInfo(this), ReqSendType.BACKGROUND);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.UpgradeMessageType.MSG_GET_UPGRADE_INFO_SUCCESS:
			onGetUpgradeSuccess(respInfo);
			break;
		case GlobalMessageType.UpgradeMessageType.MSG_GET_UPGRADE_INFO_FAILED:
			onGetUpgradeFailed(respInfo);
			break;
		case GlobalMessageType.UpgradeMessageType.MSG_EXIT: // exit
			finish();
			break;
		}
	}

	private void onGetUpgradeSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		if (isCurrentActivity()) {
			if (respInfo.data != null) {
				mUpgradeInfo = (UpgradeInfoModel) respInfo.data;
				if (StringUtils.isNotEmpty(mUpgradeInfo.url)) {
					mIvNewVersion.setVisibility(View.VISIBLE);
					if (isForegroudReq(respInfo)) {
						showUpgradeDialog(mUpgradeInfo, mUpgradeInfo.isForceUpgrade);
					}
				} else {
					if (isForegroudReq(respInfo)) {
						showToast(getString(R.string.new_app_warning));
					}
				}
			} else {
				if (isForegroudReq(respInfo)) {
					showToast(getString(R.string.new_app_warning));
				}
			}
		}
	}

	private void onGetUpgradeFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (isForegroudReq(respInfo)) {
			handleErrorAction(respInfo);
		}
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.UpgradeMessageType.MSG_GET_UPGRADE_INFO_FAILED:
				showToast(R.string.error_req_get_upgrade_info);
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
		switch (v.getId()) {
		case R.id.ll_about_us: // 关于我们
			intent = new Intent(this, AboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_platform_intro: // 平台介绍
			intent = new Intent(this, PlatformIntroActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_platform_qa_list: // 常见问题与列表
			intent = new Intent(this, PlatformQAListActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_platform_custom_service_phone: // 客服电话
			intent = new Intent(this, CustomServiceIntroActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_app_upgrade: // 版本升级
			if (!mUpgradeLogic.isDownloadingUpgradePkg()) {
				showSubmitDialog();
				mUpgradeLogic.getUpgradeInfo(ActivityUtil.getAppInfo(this), ReqSendType.FOREGROUND);
			} else {
				showToast(R.string.new_app_downloading);
			}
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

}
