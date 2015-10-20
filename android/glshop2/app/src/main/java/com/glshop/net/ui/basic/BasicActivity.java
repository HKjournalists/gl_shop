package com.glshop.net.ui.basic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.GLApplication;
import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.ReqSendType;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.upgrade.IUpgradeLogic;
import com.glshop.net.logic.upgrade.mgr.UpgradeUtils;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.net.ui.basic.view.PullRefreshListView.NewScrollerListener;
import com.glshop.net.ui.basic.view.PullRefreshListView.OnRefreshListener;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.CommonProgressDialog;
import com.glshop.net.ui.basic.view.dialog.SingleConfirmDialog;
import com.glshop.net.ui.basic.view.dialog.UpgradeDialog;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.browser.BrowsePictureActivity;
import com.glshop.net.ui.findbuy.PubModeSelectActivity;
import com.glshop.net.ui.user.LoginActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.net.utils.MenuUtil;
import com.glshop.net.utils.NetworkUtil;
import com.glshop.net.utils.SDCardUtils;
import com.glshop.net.utils.ToastUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.ui.BaseActivity;
import com.glshop.platform.utils.FileUtils;
import com.glshop.platform.utils.ImageUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;
import com.image.v2.ImageExUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description : BasicActivity
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午4:04:50
 */
public abstract class BasicActivity extends BaseActivity implements OnClickListener {

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

	/** 列表当前页数 */
	protected int pageIndex = 1;

	/** 列表默认起始页数 */
	protected static final int DEFAULT_INDEX = 1;

	/** 列表分页大小 */
	protected static final int PAGE_SIZE = GlobalConstants.CfgConstants.PAGE_SIZE;

	/** 图片上传方式选择菜单 */
	protected MenuDialog menuUploadPicType;

	/** 拍照图片的Uri */
	protected Uri mImageUri;

	/** 图片默认背景 */
	protected static final int IMAGE_DEFAULT = R.drawable.ic_default_pic;

	/** 图片加载背景 */
	protected static final int IMAGE_LOADING = R.drawable.ic_default_pic;

	/** 图片加载失败背景 */
	protected static final int IMAGE_FAILED = R.drawable.ic_error_pic;

	/** 用户未登陆确认对话框 */
	private SingleConfirmDialog mOfflineDialog;

	/** 提交请求对话框 */
	protected CommonProgressDialog mSubmitDialog;

	/** 升级提示对话框 */
	protected UpgradeDialog mUpgradeTipsDialog;

	/** 当前请求标识 */
	protected String mInvoker = String.valueOf(System.currentTimeMillis());

	protected IUpgradeLogic mUpgradeLogic;


	public static int width=480;
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
		loadScreen();
	}

	/**
	 * 初始化屏幕信息
	 */
	private void loadScreen() {
		// 分辨率
		DisplayMetrics dm = new DisplayMetrics();
		// 获取分辨率
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width= dm.widthPixels;
		Logger.d("BasicActivity", "屏幕密度 :" + dm.densityDpi + " 分辨率 :" + dm.heightPixels + "*" + dm.widthPixels);
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
					if (isShowUnloginWarning()) {
						content = getString(R.string.user_login_invalid);
					} else {
						return;
					}
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
	 * 判断是否需要显示服务器未登录错误
	 * @return
	 */
	private boolean isShowUnloginWarning() {
		return isCurrentActivity() && !(this instanceof LoginActivity);
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
	 * 设置刷新Listener
	 * @param listView
	 */
	protected void setOnRefreshListener(PullRefreshListView listView) {
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				BasicActivity.this.onRefresh();
			}
		});
	}

	protected void onRefresh() {
		// TODO refresh
	}

	/**
	 * 设置加载更多Listener
	 * @param listView
	 */
	protected void setOnScrollListener(PullRefreshListView listView) {
		listView.setNewScrollerListener(new NewScrollerListener() {

			private boolean isEnd = false;

			@Override
			public void newScrollChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING) {
					if (isEnd) {
						onScrollMore();
					}
				}
			}

			@Override
			public void newScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				isEnd = (firstVisibleItem + visibleItemCount == totalItemCount);
			}
		});
	}

	protected void onScrollMore() {
		//load more
	}

	/**
	 * 显示图片上传方式选择菜单
	 */
	protected void showUploadPicTypeMenu() {
		closeDialog(menuUploadPicType);
		List<MenuItemInfo> menu = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.upload_pic_type)));
		menuUploadPicType = new MenuDialog(this, menu, new IMenuCallback() {

			@Override
			public void onConfirm(int type, Object obj) {

			}

			@Override
			public void onCancel(int type) {

			}

			@Override
			public void onMenuClick(int type, int position, Object obj) {
				// TODO check the sdcard exist ?
				Intent intent = new Intent();
				if (position == 0) {
					if (ImageExUtils.isKitKat()) {
						intent.setAction(/*Intent.ACTION_OPEN_DOCUMENT*/"android.intent.action.OPEN_DOCUMENT");
					} else {
						intent.setAction(Intent.ACTION_GET_CONTENT);
					}
					intent.setType("image/*");
					startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_GET_PHOTO);
				} else {
					mImageUri = getImageUri();
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
					startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_TAKE_PHOTO);
				}
			}

		});
		menuUploadPicType.setMenuType(GlobalMessageType.MenuType.SELECT_UPLOAD_PIC_TYPE);
		menuUploadPicType.setTitle(getString(R.string.upload_pic_type));
		menuUploadPicType.show();
	}

	private Uri getImageUri() {
		File imageDir = new File(SDCardUtils.getAvailableSdcard() + "/gl_shop/temp/");
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		String timeStamp = new SimpleDateFormat(DateUtils.TAKE_PHOTO_NAME_FORMAT).format(new Date());
		File imageFile = new File(imageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
		return Uri.fromFile(imageFile);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_GET_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				Uri uri = data.getData();
				Logger.i(TAG, "GetPhoto: Uri = " + uri.toString());
				Bitmap bitmap = null;
				String file = null;
				if (ImageExUtils.isKitKat()) {
					file = ImageExUtils.getPath(this, uri);
					bitmap = ImageExUtils.getBitmap(file);
				} else {
					bitmap = ImageUtils.getThumbnailImage(this, uri);
					file = ImageUtils.getImagePath(this, uri);
				}
				Logger.i(TAG, "GetPhoto: File = " + file + ", Bitmap = " + bitmap);
				handlePicSelect(bitmap, file);
			}
			break;
		case GlobalMessageType.ActivityReqCode.REQ_TAKE_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				if (mImageUri != null) {
					Logger.i(TAG, "TakePhoto: Uri = " + mImageUri.toString());
					File file = FileUtils.getFileFromUri(mImageUri);
					Bitmap bitmap = ImageUtils.getBitmap(file.getAbsolutePath(), 100 * 100);
					Logger.i(TAG, "TakePhoto: File = " + file + ", Bitmap = " + bitmap);
					handlePicSelect(bitmap, file.getAbsolutePath());
				} else {
					Logger.i(TAG, "TakePhoto: Uri = " + null);
					handlePicSelect(null, null);
				}
			}
			break;
		}
	}

	private void handlePicSelect(Bitmap bitmap, String file) {
		if (StringUtils.isEmpty(file)) {
			showToast("所选图片路径不存在，请重新选择!");
		} else if (bitmap == null || bitmap.getRowBytes() == 0) {
			showToast("所选图片无效，请重新选择!");
		} else {
			onPictureSelect(bitmap, file);
		}
	}

	/**
	 * 显示选中的图片缩略图
	 * @param bitmap
	 * @param file
	 */
	protected void onPictureSelect(Bitmap bitmap, String file) {
		// TODO show the selected picture.
	}

	/**
	 * 大图浏览
	 * @param info
	 */
	protected void browseImage(ImageInfoModel info) {
		List<ImageInfoModel> list = new ArrayList<ImageInfoModel>();
		list.add(info);
		browseImage(list);
	}

	/**
	 * 大图浏览
	 * @param list
	 */
	protected void browseImage(List<ImageInfoModel> list) {
		Intent intent = new Intent(this, BrowsePictureActivity.class);
		intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PICTURE_INFO, (ArrayList) list);
		startActivity(intent);
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
	protected void showUpgradeDialog(final UpgradeInfoModel upgradeInfo, final boolean isForeUpdate) {
		closeDialog(mUpgradeTipsDialog);

		mUpgradeTipsDialog = new UpgradeDialog(this, R.style.dialog,isForeUpdate);
		//mUpgradeTipsDialog.setContent(getString(R.string.upgrade_pkg_notify_title));
		mUpgradeTipsDialog.setUpgradeInfo(upgradeInfo);
		mUpgradeTipsDialog.setCanBack(false);
		mUpgradeTipsDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				// test data
				/*UpgradeInfoModel info = new UpgradeInfoModel();
				info.url = "http://www.916816.com/glshop_test4_20141205_3.apk";
				info.md5 = "178DD5320A83954AFA4949E93A1FCCEE";
				info.size = "3904893";*/
				mUpgradeTipsDialog.setConfirmText("正在下载");
				mUpgradeLogic.downloadApp(upgradeInfo);
			}

			@Override
			public void onCancel(int type) {
				UpgradeUtils.saveIgnoreVersion(BasicActivity.this, upgradeInfo.versionCode);
			}
		});
		mUpgradeTipsDialog.show();
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
	 * @param view
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

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	@Override
	protected void initLogics() {
		mUpgradeLogic = LogicFactory.getLogicByClass(IUpgradeLogic.class);
	}

}
