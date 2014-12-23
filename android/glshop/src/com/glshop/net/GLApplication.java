package com.glshop.net;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.logic.buy.BuyLogic;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.contract.ContractLogic;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.message.IMessageLogic;
import com.glshop.net.logic.message.MessageLogic;
import com.glshop.net.logic.pay.IPayLogic;
import com.glshop.net.logic.pay.PayLogic;
import com.glshop.net.logic.profile.IProfileLogic;
import com.glshop.net.logic.profile.ProfileLogic;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.logic.purse.PurseLogic;
import com.glshop.net.logic.setting.ISettingLogic;
import com.glshop.net.logic.setting.SettingLogic;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.logic.syscfg.SysCfgLogic;
import com.glshop.net.logic.transfer.ITransferLogic;
import com.glshop.net.logic.transfer.TransferLogic;
import com.glshop.net.logic.upgrade.IUpgradeLogic;
import com.glshop.net.logic.upgrade.UpgradeLogic;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.logic.user.UserLogic;
import com.glshop.net.logic.xmpp.XmppMgr;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SDCardUtils;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.base.manager.ApplicationExceptionHandler;
import com.glshop.platform.base.manager.BaseApplication;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.FileUtils;
import com.glshop.platform.utils.Logger;
import com.igexin.sdk.PushManager;

/**
 * @Description : 全局Application 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:45:39
 */
public class GLApplication extends BaseApplication {

	private static final String TAG = "GLApplication";

	/** Activity Stack */
	private Stack<Activity> mActivityStack = new Stack<Activity>();

	private Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		// 初始化全局配置
		GlobalConfig.getInstance().init(mContext);

		// 初始化日志
		Logger.initLogger(Logger.VERBOSE, true, GlobalConstants.AppDirConstants.LOG);
		// 异常统一处理
		Thread.setDefaultUncaughtExceptionHandler(new ApplicationExceptionHandler(mContext));

		Logger.e(TAG, "onCreate");
		Logger.e(TAG, "AppVersion = " + ActivityUtil.getVersionName(this));

		// 初始化所有的参数
		PlatformConfig.init(mContext);
		// 初始化服务器URL
		PlatformConfig.setValue(PlatformConfig.SERVICES_URL, GlobalConstants.Common.SERVER_URL);
		PlatformConfig.setValue(PlatformConfig.SERVICES_URL_IMAGE, GlobalConstants.Common.SERVER_URL_IMAGE);

		// 升级管理
		onUpgrade();

		// 推送初始化
		xmppInit();

		// 初始化Logic
		initAllLogics();

		// 初始化目录信息
		initDir();

		// 同步系统配置信息，暂时放在Application调用，后续视情况再调整。
		syncSysCfg();
	}

	/**
	 * 升级管理
	 */
	private void onUpgrade() {
		PlatformConfig.setValue(GlobalConstants.SPKey.CUR_VERSION_NAME, ActivityUtil.getVersionName(mContext));
		PlatformConfig.setValue(GlobalConstants.SPKey.CUR_VERSION_CODE, ActivityUtil.getVersionCode(mContext));
		// TODO 后续实现
	}

	/**
	 * 推送初始化
	 */
	private void xmppInit() {
		XmppMgr.getInstance().initialize(this.getApplicationContext());
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	/**
	 * 初始化全局Logic
	 */
	private void initAllLogics() {
		LogicFactory.registerLogic(IUserLogic.class, new UserLogic(mContext));
		LogicFactory.registerLogic(IBuyLogic.class, new BuyLogic(mContext));
		LogicFactory.registerLogic(IContractLogic.class, new ContractLogic(mContext));
		LogicFactory.registerLogic(IPurseLogic.class, new PurseLogic(mContext));
		LogicFactory.registerLogic(IProfileLogic.class, new ProfileLogic(mContext));
		LogicFactory.registerLogic(IPayLogic.class, new PayLogic(mContext));
		LogicFactory.registerLogic(IMessageLogic.class, new MessageLogic(mContext));
		LogicFactory.registerLogic(ISysCfgLogic.class, new SysCfgLogic(mContext));
		LogicFactory.registerLogic(ISettingLogic.class, new SettingLogic(mContext));
		LogicFactory.registerLogic(IUpgradeLogic.class, new UpgradeLogic(mContext));
		LogicFactory.registerLogic(ITransferLogic.class, new TransferLogic(mContext));
	}

	/**
	 * 初始化目录信息
	 */
	private void initDir() {
		SDCardUtils.initMountSdcards();
		ImageLoaderManager.getIntance().setCachePath(GlobalConstants.AppDirConstants.IMAGE_CACHE);

		// create .nomedia file
		if (FileUtils.isExistSDcard()) {
			String[] nomediaPaths = new String[] {
					// Temp目录
					GlobalConstants.AppDirConstants.TEMP,
					// Download目录
					GlobalConstants.AppDirConstants.DOWNLOAD,
					// Cache目录
					GlobalConstants.AppDirConstants.IMAGE_CACHE };
			for (String path : nomediaPaths) {
				if (FileUtils.createDir(path)) {
					try {
						File file = new File(path + "/" + ".nomedia");
						if (!file.exists()) {
							file.createNewFile();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 同步系统默认参数信息
	 */
	private void syncSysCfg() {
		ISysCfgLogic mSysCfgLogic = LogicFactory.getLogicByClass(ISysCfgLogic.class);
		if (mSysCfgLogic != null) {
			mSysCfgLogic.syncSysCfgInfo();
		}
	}

	/**
	 * 添加Activity至堆栈
	 * @param activity
	 */
	public synchronized void addActivity(Activity activity) {
		mActivityStack.add(activity);
	}

	/**
	 * 删除堆栈中Activity
	 * @param activity
	 */
	public synchronized void removeActivty(Activity activity) {
		mActivityStack.remove(activity);
	}

	/**
	 * 清除堆栈，忽略ignoreList中对应的Activity
	 * @param ignoreList
	 */
	public synchronized void cleanStack(List<Class<?>> ignoreList) {
		final int size = mActivityStack.size();
		for (int i = size - 1; i >= 0; i--) {
			Activity activity = mActivityStack.get(i);
			for (Class<?> classz : ignoreList) {
				if (!classz.isInstance(activity)) {
					//Logger.e(TAG, "Class = " + classz.getName());
					Logger.e(TAG, "Finish Activity = " + activity.getClass().getName());
					mActivityStack.remove(activity);
					activity.finish();
				}
			}
		}
	}

	/**
	 * 判断Activity是否为当前页面
	 * @param activity
	 * @return
	 */
	public boolean isCurrentActivity(Activity activity) {
		if (BeanUtils.isNotEmpty(mActivityStack) && activity != null) {
			return activity == mActivityStack.peek();
		} else {
			return false;
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
