package com.glshop.net.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.advertising.BootPageActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;

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

	private IUserLogic mUserLogic;

	/**是否显示引导页*/
	private boolean showBootpage=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		getHandler().sendEmptyMessageDelayed(GlobalMessageType.UserMessageType.MSG_BOOT_COMPLETE, 3000);

	}

	private void showBootPage() {
		GlobalConfig.getInstance().setTokenUpdatedStatus(false);
		//判断是否需要显示引导页
		showBootpage=GlobalConfig.getInstance().getFirstStarAPP();
		int oldVersioncode=GlobalConfig.getInstance().getAPPVersionCode();
		if (ActivityUtil.getVersionCode(this)!=oldVersioncode) {
			showBootpage = true;
			GlobalConfig.getInstance().setAPPVersionCode(ActivityUtil.getVersionCode(this));
		}

		if (showBootpage) {
			GlobalConfig.getInstance().setFirstStarAPP(false);
			Intent intent = new Intent(this, BootPageActivity.class);
			startActivity(intent);
		}else {
			autoLogin();
			gotoMainPage();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		switch (message.what) {
		case GlobalMessageType.UserMessageType.MSG_BOOT_COMPLETE:
			showBootPage();
			break;
		}
	}

	private void autoLogin() {
		checkUserLogic();
		boolean result = mUserLogic.autoLogin(true);
		Logger.d(TAG, "AutoLogin result = " + result);
	}

	private void gotoMainPage() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	private void checkUserLogic() {
		if (mUserLogic == null) {
			mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
		}
	}

	@Override
	protected void initLogics() {
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
