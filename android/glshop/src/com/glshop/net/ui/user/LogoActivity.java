package com.glshop.net.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.glshop.net.R;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.message.IMessageLogic;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 启动页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class LogoActivity extends BasicActivity {

	private static final String TAG = "LogoActivity";

	private boolean needToUpdateUnReadMsgNum = false;

	private IMessageLogic mMessageLogic;
	private IUserLogic mUserLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		showBootPage();
	}

	private void showBootPage() {
		getHandler().sendEmptyMessageDelayed(GlobalMessageType.UserMessageType.MSG_BOOT_COMPLETE, 2000);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		switch (message.what) {
		case GlobalMessageType.UserMessageType.MSG_BOOT_COMPLETE:
			autoLogin();
			gotoMainPage();
			break;
		case UserMessageType.MSG_REFRESH_TOKEN_SUCCESS:
			needToUpdateUnReadMsgNum = true;
			break;
		}
	}

	private void autoLogin() {
		boolean result = mUserLogic.autoLogin(true);
		Logger.d(TAG, "AutoLogin result = " + result);
	}

	private void gotoMainPage() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();

		if (needToUpdateUnReadMsgNum) {
			refreshMessageStatus();
		}
	}

	/**
	 * 刷新未读消息个数
	 */
	private void refreshMessageStatus() {
		if (isLogined() && StringUtils.isNotEmpty(getCompanyId())) {
			mMessageLogic.getUnreadedNumberFromServer(getCompanyId());
		}
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	protected void initLogics() {
		mMessageLogic = LogicFactory.getLogicByClass(IMessageLogic.class);
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
