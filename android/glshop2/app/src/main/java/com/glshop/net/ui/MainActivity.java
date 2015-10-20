package com.glshop.net.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.ReqSendType;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.upgrade.IUpgradeLogic;
import com.glshop.net.logic.upgrade.mgr.UpgradeUtils;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.findbuy.BuyListActivity;
import com.glshop.net.ui.findbuy.ShopActivity;
import com.glshop.net.ui.mybuy.MyBuyListActivity;
import com.glshop.net.ui.mycontract.ContractListActivity;
import com.glshop.net.ui.myprofile.MyProfileActivity;
import com.glshop.net.ui.mypurse.PurseActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 长江电商应用主页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class MainActivity extends BasicActivity {

	private static final String TAG = "MainActivity";

	/** 用于判断是否连续按两次Back退出程序  */
	private boolean isNeedToExitApp = false;

	private TabHost mTabHost;

	/** 默认Tab页面  */
	private TabStatus curTabStatus = TabStatus.SHOP;

	private static Map<String, TabStatus> tabActionMap = new HashMap<String, TabStatus>();
	private static Map<TabStatus, Class<?>> tabIntentMap = new HashMap<TabStatus, Class<?>>();
	static {
		tabActionMap.put(GlobalAction.TipsAction.ACTION_VIEW_SHOP, TabStatus.SHOP);
		tabActionMap.put(GlobalAction.TipsAction.ACTION_VIEW_FIND_BUY, TabStatus.FIND_BUY);
		tabActionMap.put(GlobalAction.TipsAction.ACTION_VIEW_MY_BUY, TabStatus.MY_BUY);
		tabActionMap.put(GlobalAction.TipsAction.ACTION_VIEW_MY_CONTRACT, TabStatus.MY_CONTRACT);
		tabActionMap.put(GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE, TabStatus.MY_PURSE);
		tabActionMap.put(GlobalAction.TipsAction.ACTION_VIEW_MY_PROFILE, TabStatus.MY_PROFILE);

		tabIntentMap.put(TabStatus.SHOP, ShopActivity.class);
		tabIntentMap.put(TabStatus.FIND_BUY, BuyListActivity.class);
		tabIntentMap.put(TabStatus.MY_BUY, MyBuyListActivity.class);
		tabIntentMap.put(TabStatus.MY_CONTRACT, ContractListActivity.class);
		tabIntentMap.put(TabStatus.MY_PURSE, PurseActivity.class);
		tabIntentMap.put(TabStatus.MY_PROFILE, MyProfileActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_main);
		initView();
		initData(savedInstanceState);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Logger.e(TAG, "onNewIntent()");
		doAction(intent);
	}

	private void initView() {
		initTabs();
	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			curTabStatus = TabStatus.convert(savedState.getInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, TabStatus.SHOP.toValue()));
			Logger.e(TAG, "TabIndex = " + curTabStatus.toValue());
		}
		switchTabView(curTabStatus);
		checkUpgrade();
	}
	/**
	 * 初始化各个Tab界面
	 */
	private void initTabs() {
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup(getLocalActivityManager());
		mTabHost.addTab(newTab(TabStatus.SHOP));
		mTabHost.addTab(newTab(TabStatus.FIND_BUY));
		mTabHost.addTab(newTab(TabStatus.MY_BUY));
		mTabHost.addTab(newTab(TabStatus.MY_CONTRACT));
		mTabHost.addTab(newTab(TabStatus.MY_PURSE));
		mTabHost.addTab(newTab(TabStatus.MY_PROFILE));
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
			// 暂不处理
			break;
		case GlobalMessageType.UpgradeMessageType.MSG_CHECK_UPGRADE_INFO: // 检测升级信息
			mUpgradeLogic.getUpgradeInfo(ActivityUtil.getAppInfo(this), ReqSendType.BACKGROUND);
			break;
		case UserMessageType.MSG_MODIFY_PASSWORD_SUCCESS: // 密码修改成功
		case DataConstants.GlobalMessageType.MSG_USER_OFFLINE: // 用户离线
			//case DataConstants.GlobalMessageType.MSG_USER_NOT_LOGINED: // 用户未登录(暂不处理，避免界面死循环)
		case DataConstants.GlobalMessageType.MSG_USER_TOKEN_EXPIRE: // 用户Token失效
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_SHOP);
			intent.putExtra(GlobalAction.UserAction.EXTRA_IS_USER_LOGOUT, true);
			doAction(intent);
			break;
		case GlobalMessageType.UpgradeMessageType.MSG_EXIT: // exit
			Logger.d(TAG,"系统退出");
			finish();
			cleanStack();
			break;
		}
	}

	private void doAction(Intent intent) {
		String action = intent.getStringExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION);
		boolean isLogout = intent.getBooleanExtra(GlobalAction.UserAction.EXTRA_IS_USER_LOGOUT, false);
		if (isLogout) {
			curTabStatus = TabStatus.SHOP;
			//mTabHost.setCurrentTabByTag(curTabStatus.toString());
			mTabHost.setCurrentTab(curTabStatus.toValue());
			mTabHost.clearAllTabs();
			getLocalActivityManager().removeAllActivities();
			initTabs();
		}
		if (StringUtils.isNotEmpty(action)) {
			if (tabActionMap.containsKey(action)) {
				TabStatus tab = tabActionMap.get(action);
				Logger.e(TAG, "doAction & TabIndex = " + tab.toValue());
				switchTabView(tab);
			}
		}
	}

	/**
	 * 切换各个Tab界面
	 * @param tabStatus
	 */
	public void switchTabView(TabStatus tabStatus) {
		curTabStatus = tabStatus;
		try {
			//mTabHost.setCurrentTabByTag(curTabStatus.toString());
			mTabHost.setCurrentTab(curTabStatus.toValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切换到我的资料Tab
	 */
	public void switchTabToMyProfile(boolean isFromPurse) {
		switchTabView(TabStatus.MY_PROFILE);
		try {
			((MyProfileActivity) getLocalActivityManager().getCurrentActivity()).setIsFormPurse(isFromPurse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TabSpec newTab(TabStatus tab) {
		TabSpec tabSpec = mTabHost.newTabSpec(tab.toString());
		tabSpec.setIndicator(tab.toString());

		Intent intent = new Intent(this, tabIntentMap.get(tab));
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		tabSpec.setContent(intent);
		return tabSpec;
		//return mTabHost.newTabSpec(tab.toString()).setIndicator(tab.toString()).setContent(new Intent(this, tabIntentMap.get(tab)));
	}

	/**
	 * 检测应用升级信息
	 */
	private void checkUpgrade() {
		//mUpgradeLogic.getUpgradeInfo(ActivityUtil.getAppInfo(this), ReqSendType.BACKGROUND);
		sendEmptyMessageDelayed(GlobalMessageType.UpgradeMessageType.MSG_CHECK_UPGRADE_INFO, 1000);
	}

	private void onGetUpgradeSuccess(RespInfo respInfo) {
		if (isCurrentActivity()) {
			if (respInfo.data != null) {
				UpgradeInfoModel upgradeInfo = (UpgradeInfoModel) respInfo.data;
				if (upgradeInfo != null) {
					if (!UpgradeUtils.isIgnoreVersion(this, upgradeInfo.versionCode) || upgradeInfo.isForceUpgrade) {
						if (StringUtils.isNotEmpty(upgradeInfo.url)) {
							showUpgradeDialog(upgradeInfo, upgradeInfo.isForceUpgrade);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, curTabStatus.toValue());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		isNeedToExitApp = false;
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		//Logger.e(TAG, "KeyCode = " + event.getKeyCode() + ", Action = " + KeyEvent.ACTION_UP);
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if (getCurrentActivity() instanceof ShopActivity) {
				if (!isNeedToExitApp) {
					isNeedToExitApp = true;
					showToast(R.string.exit_app_tips);
					return true;
				} else { // 连续按两次Back,退出客户端
					// 取消升级下载

					IUpgradeLogic mUpgradeLogic = LogicFactory.getLogicByClass(IUpgradeLogic.class);
					if (mUpgradeLogic != null) {
						mUpgradeLogic.cancelDownload();
						Logger.d(TAG, "取消下载升级");
					}
					MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.UpgradeMessageType.MSG_EXIT);
//					DefineNotification.clearById(UpgradeNotifyID.ID_UPGRADE_PKG_DOWNLOAD);
					return super.dispatchKeyEvent(event);
				}
			} else {
				Logger.d(TAG,"switchTabView");
				if (getCurrentActivity() instanceof MyProfileActivity && ((MyProfileActivity) getCurrentActivity()).isFormPurse()) {
					// 若从我的钱包进入到我的资料页面，按back键则回到我的钱包
					switchTabView(TabStatus.MY_PURSE);
				} else {
					switchTabView(TabStatus.SHOP);
				}
				return true;
			}
		} else {
			return super.dispatchKeyEvent(event);
		}
	}

}
