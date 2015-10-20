package com.glshop.net.ui.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.GLApplication;
import com.glshop.net.R;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.ReqSendType;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.upgrade.IUpgradeLogic;
import com.glshop.net.logic.upgrade.mgr.UpgradeUtils;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.CommonProgressDialog;
import com.glshop.net.ui.basic.view.dialog.SingleConfirmDialog;
import com.glshop.net.ui.basic.view.dialog.UpgradeDialog;
import com.glshop.net.ui.findbuy.PubModeSelectActivity;
import com.glshop.net.ui.user.LoginActivity;
import com.glshop.net.utils.NetworkUtil;
import com.glshop.net.utils.ToastUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.ui.BaseFragmentActivity;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : BasicFragmentActivity
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:11:05
 */
public abstract class BasicFragmentActivity extends BaseFragmentActivity implements OnClickListener {

	/** 数据状态视图父布局 */
	protected View mLoadContainerView;

	/** 正在加载视图 */
	protected View mLoadingDataView;

	/** 加载完成，显示正常数据视图 */
	protected View mNormalDataView;

	/** 加载完成，显示空数据视图 */
	protected View mEmptyDataView;

	/** 加载失败视图 */
	protected View mLoadErrorView;

	/** 显示空数据视图文案 */
	protected TextView mTvEmtpyData;

	/** 用户未登陆确认对话框 */
	private SingleConfirmDialog mOfflineDialog;

	/** 提交请求对话框 */
	protected CommonProgressDialog mSubmitDialog;

	/** 升级提示对话框 */
	protected UpgradeDialog mUpgradeTipsDialog;

	/** 图片默认背景 */
	protected static final int IMAGE_DEFAULT = R.drawable.ic_default_pic;

	/** 图片加载背景 */
	protected static final int IMAGE_LOADING = R.drawable.ic_default_pic;

	/** 图片加载失败背景 */
	protected static final int IMAGE_FAILED = R.drawable.ic_error_pic;

	/** 当前请求标识 */
	protected String mInvoker = String.valueOf(System.currentTimeMillis());

	protected IUpgradeLogic mUpgradeLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isAddToStack()) {
			((GLApplication) getApplication()).addActivity(this);
		}
		if (needLogin() && !isLogined()) {
			IUserLogic mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
			if (mUserLogic != null) {
				mUserLogic.autoLogin(false);
			}
		}
	}

	/**
	 * 初始化加载状态视图
	 */
	protected void initLoadView() {
		mLoadContainerView = getView(R.id.ll_load_data_status);
		mLoadingDataView = getView(R.id.ll_loading_data);
		mLoadErrorView = getView(R.id.ll_load_data_error);
		mEmptyDataView = getView(R.id.ll_empty_data);
		mTvEmtpyData = getView(R.id.tv_empty_data);

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				onReloadData();
			}
		};

		mEmptyDataView.setOnClickListener(listener);
		mLoadErrorView.setOnClickListener(listener);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		switch (message.what) {
		case DataConstants.GlobalMessageType.MSG_USER_NOT_LOGINED: // 用户未登陆
		case DataConstants.GlobalMessageType.MSG_USER_OFFLINE: // 用户离线
		case DataConstants.GlobalMessageType.MSG_USER_TOKEN_EXPIRE: // 用户Token过期
			GlobalConfig.getInstance().cleanLoginInfo(); // 清除当前登录用户信息
			closeSubmitDialog(); // 关闭当前请求进度对话框
			if (isCurrentActivity()) {
				String content = "";
				if (message.what == DataConstants.GlobalMessageType.MSG_USER_TOKEN_EXPIRE) {
					content = getString(R.string.user_token_expire);
				} else if (message.what == DataConstants.GlobalMessageType.MSG_USER_NOT_LOGINED) {
					content = getString(R.string.user_login_invalid);
				} else if (message.what == DataConstants.GlobalMessageType.MSG_USER_OFFLINE) {
					content = getString(R.string.user_offline);
				} else {
					content = getString(R.string.user_login_invalid);
				}
				showOfflineDialog(content);
			}
			break;
		}
	}

	/**
	 * 判断是否为当前页面
	 * @return
	 */
	protected boolean isCurrentActivity() {
		return ((GLApplication) getApplication()).isCurrentActivity(this);
	}

	/**
	 * 判断是否为当前页面的请求
	 * @param reqInvoker
	 * @param respInvoker
	 * @return
	 */
	protected boolean isCurRequest(String reqInvoker, String respInvoker) {
		if (StringUtils.isNotEmpty(reqInvoker) && StringUtils.isNotEmpty(respInvoker)) {
			return reqInvoker.equals(respInvoker);
		} else {
			return false;
		}
	}

	/**
	 * 用户是否已登录
	 * @return
	 */
	protected boolean isLogined() {
		return GlobalConfig.getInstance().isLogined();
	}

	/**
	 * 网络是否已连接
	 * @return
	 */
	protected boolean isNetConnected() {
		return NetworkUtil.isNetworkConnected(this);
	}

	/**
	 * 当前用户ID
	 * @return
	 */
	protected String getUserID() {
		return GlobalConfig.getInstance().getUserID();
	}

	/**
	 * 当前用户名称
	 * @return
	 */
	protected String getUserAccount() {
		return GlobalConfig.getInstance().getUserAccount();
	}

	/**
	 * 当前用户手机号
	 * @return
	 */
	protected String getUserPhone() {
		return GlobalConfig.getInstance().getUserPhone();
	}

	/**
	 * 当前用户企业ID
	 * @return
	 */
	protected String getCompanyId() {
		return GlobalConfig.getInstance().getCompanyId();
	}

	/**
	 * 当前用户企业名称
	 * @return
	 */
	protected String getCompanyName() {
		return GlobalConfig.getInstance().getCompanyName();
	}

	/**
	 * 关闭对话框
	 * @param dialog
	 */
	protected void closeDialog(Dialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 执行多个Fragment间的切换。
	 * @param containerId
	 * @param show
	 * @param showTag
	 * @param hideList
	 */
	protected void switchFragment(int containerId, Fragment show, String showTag, Fragment... hideList) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (BeanUtils.isNotEmpty(hideList)) {
			for (Fragment fragment : hideList) {
				fragmentTransaction.hide(fragment);
			}
		}

		if (!show.isAdded()) {
			fragmentTransaction.add(containerId, show, showTag);
		}

		fragmentTransaction.show(show);
		fragmentTransaction.commitAllowingStateLoss();
	}

	/**
	 * 显示Fragment
	 * @param containerId
	 * @param fragment
	 * @param tag
	 */
	protected void showFragment(int containerId, Fragment fragment, String tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (!fragment.isAdded()) {
			fragmentTransaction.add(containerId, fragment, tag);
		}

		fragmentTransaction.show(fragment);
		fragmentTransaction.commitAllowingStateLoss();
	}

	@SuppressWarnings("unchecked")
	public <T extends Fragment> T getFragment(int id) {
		T result = (T) getSupportFragmentManager().findFragmentById(id);
		if (result == null) {
			throw new IllegalArgumentException("fragment 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends Fragment> T getFragment(String tag) {
		T result = (T) getSupportFragmentManager().findFragmentByTag(tag);
		if (result == null) {
			//throw new IllegalArgumentException("fragment tag:" + tag + " doesn't exist");
		}
		return result;
	}

	/**
	 * 焦点定位文本末尾
	 * @param view
	 */
	protected void requestSelection(EditText view) {
		if (view != null) {
			view.setSelection(view.getText().toString().length());
		}
	}

	/**
	 * 添加文本监听，限制文本输入
	 * @param View
	 */
	protected void setTextWatcher(final EditText view) {
		if (view != null) {
			view.addTextChangedListener(new TextWatcher() {

				private int editStart;
				private int editEnd;

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					editStart = view.getSelectionStart();
					editEnd = view.getSelectionEnd();
					String data = view.getEditableText().toString();
					if (StringUtils.isDouble(data) && !StringUtils.checkDecimal(data, 2)) {
						s.delete(editStart - 1, editEnd);
						int tempSelection = editStart;
						view.setText(s);
						view.setSelection(tempSelection);
					}
				}
			});
		}
	}

	/**
	 * 是否为前台请求
	 * @param respInfo
	 * @return
	 */
	protected boolean isForegroudReq(RespInfo respInfo) {
		return respInfo != null && respInfo.reqSendType == ReqSendType.FOREGROUND;
	}

	/**
	 * 是否为后台请求
	 * @param respInfo
	 * @return
	 */
	protected boolean isBackgroudReq(RespInfo respInfo) {
		return respInfo != null && respInfo.reqSendType == ReqSendType.BACKGROUND;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	@Override
	public void finish() {
		if (isAddToStack()) {
			((GLApplication) getApplication()).removeActivty(this);
		}
		super.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DataCenter.getInstance().cleanData(getDataType());
	}

	protected int[] getDataType() {
		return new int[] {};
	}

	/**
	 * 清除堆栈(忽略ignoreList中定义类型)
	 * @param ignoreList
	 */
	protected void cleanStack(List<Class<?>> ignoreList) {
		((GLApplication) getApplication()).cleanStack(ignoreList);
	}

	/**
	 * 清除堆栈
	 * @param ignoreList
	 */
	protected void cleanStack() {
		((GLApplication) getApplication()).cleanStack(new ArrayList<Class<?>>(Arrays.asList(MainActivity.class)));
	}

	/**
	 * 是否添加至堆栈
	 * @return
	 */
	protected boolean isAddToStack() {
		return true;
	}

	/**
	 * 当前页面是否需要登录
	 * @return
	 */
	protected boolean needLogin() {
		return true;
	}

	/**
	 * 更新加载数据视图状态
	 * @param status
	 */
	protected void updateDataStatus(DataStatus status) {
		if (status == DataStatus.NORMAL) {
			mLoadContainerView.setVisibility(View.GONE);
			mNormalDataView.setVisibility(View.VISIBLE);
		} else {
			mLoadContainerView.setVisibility(View.VISIBLE);
			mNormalDataView.setVisibility(View.GONE);
			mLoadingDataView.setVisibility(status == DataStatus.LOADING ? View.VISIBLE : View.GONE);
			mEmptyDataView.setVisibility(status == DataStatus.EMPTY ? View.VISIBLE : View.GONE);
			mLoadErrorView.setVisibility(status == DataStatus.ERROR ? View.VISIBLE : View.GONE);
		}
	}

	/**
	 * 设置加载内容为空文案显示
	 */
	protected void updateEmptyDataMessage(String message) {
		if (mTvEmtpyData != null) {
			mTvEmtpyData.setText(message);
		} else {
			throw new IllegalArgumentException("You must to call initLoadView() before updateEmptyDataMessage()!");
		}
	}

	/**
	 * 重新加载数据
	 */
	protected void onReloadData() {
		// TODO reload data
	}

	/**
	 * 显示全局请求进度对话框
	 */
	protected void showSubmitDialog() {
		showSubmitDialog(getString(R.string.do_request_ing));
	}

	/**
	 * 显示全局请求进度对话框
	 */
	protected void showSubmitDialog(String message) {
		closeSubmitDialog();
		mSubmitDialog = new CommonProgressDialog(this, StringUtils.isNotEmpty(message) ? message : getString(R.string.do_request_ing));
		mSubmitDialog.show();
	}

	/**
	 * 关闭全局请求进度对话框
	 */
	protected void closeSubmitDialog() {
		if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
			mSubmitDialog.dismiss();
		}
	}

	/**
	 * 显示离线对话框
	 * @param content
	 */
	private void showOfflineDialog(String content) {
		if (mOfflineDialog != null && mOfflineDialog.isShowing()) {
			mOfflineDialog.dismiss();
		}

		mOfflineDialog = new SingleConfirmDialog(this, R.style.dialog);
		mOfflineDialog.setContent(content);
		mOfflineDialog.setCanBack(false);
		mOfflineDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				gotoLogin();
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mOfflineDialog.show();
	}

	/**
	 * 显示登录界面
	 */
	protected void gotoLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/**
	 * 显示升级提示对话框
	 * @param upgradeInfo
	 */
	protected void showUpgradeDialog(final UpgradeInfoModel upgradeInfo, final boolean canIgnore) {
		closeDialog(mUpgradeTipsDialog);

		mUpgradeTipsDialog = new UpgradeDialog(this, R.style.dialog);
		//mUpgradeTipsDialog.setContent(getString(R.string.upgrade_pkg_notify_title));
		mUpgradeTipsDialog.setUpgradeInfo(upgradeInfo);
		mUpgradeTipsDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				// test data
				/*UpgradeInfoModel info = new UpgradeInfoModel();
				info.url = "http://www.916816.com/glshop_test4_20141205_3.apk";
				info.md5 = "178DD5320A83954AFA4949E93A1FCCEE";
				info.size = "3904893";*/
				mUpgradeLogic.downloadApp(upgradeInfo);
			}

			@Override
			public void onCancel(int type) {
				if (canIgnore) {
					UpgradeUtils.saveIgnoreVersion(BasicFragmentActivity.this, upgradeInfo.versionCode);
				}
			}
		});
		mUpgradeTipsDialog.show();
	}

	/**
	 * 解析RespInfo
	 * @param message
	 * @return
	 */
	protected RespInfo getRespInfo(Message message) {
		if (message != null && message.obj instanceof RespInfo) {
			RespInfo info = (RespInfo) message.obj;
			info.respMsgType = message.what;
			return info;
		}
		return null;
	}

	/**
	 * 统一处理请求错误信息
	 * @param info
	 */
	protected void handleErrorAction(RespInfo info) {
		if (info != null) {
			Logger.e(TAG, "MetaErrorMsg = " + info.errorMsg);
			if (GlobalErrorMessage.isFilterErrorCode(info.errorCode)) {
				// 暂时不做任何处理
			} else {
				//showToast(GlobalErrorMessage.getErrorMsg(this, info.errorCode, info.errorMsg));
				if (!GlobalErrorMessage.handleErrorMsg(this, info.errorCode)) {
					showErrorMsg(info);
				}
			}
		}
	}

	protected void showErrorMsg(RespInfo respInfo) {
		showToast(R.string.error_req_error); // 显示统一默认错误信息
	}

	/**
	 * 发布信息
	 */
	protected void pubBuyInfo() {
		if (isLogined()) {
			Intent intent = new Intent(this, PubModeSelectActivity.class);
			startActivity(intent);
		} else {
			showToast(R.string.user_not_login);
		}
	}

	@Override
	protected void showToast(String msg) {
		//super.showToast(msg);
		ToastUtil.showDefaultToast(this, msg);
	}

	@Override
	protected void showToast(int resId) {
		//super.showToast(resId);
		ToastUtil.showDefaultToast(this, resId);
	}

	@Override
	protected void initLogics() {
		mUpgradeLogic = LogicFactory.getLogicByClass(IUpgradeLogic.class);
	}

}
