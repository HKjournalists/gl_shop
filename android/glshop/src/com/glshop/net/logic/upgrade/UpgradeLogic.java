package com.glshop.net.logic.upgrade;

import java.io.File;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.ReqSendType;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.UpgradeMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.upgrade.mgr.UpgradeMgr;
import com.glshop.net.logic.upgrade.mgr.UpgradeMgr.IUpgradeListener;
import com.glshop.net.ui.basic.notification.DefineNotification;
import com.glshop.net.ui.basic.notification.NotificationConstants.UpgradeNotifyID;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.setting.GetUpgradeInfoReq;
import com.glshop.platform.api.setting.data.GetUpgradeInfoResult;
import com.glshop.platform.api.setting.data.model.AppInfoModel;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 升级业务逻辑实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:43
 */
public class UpgradeLogic extends BasicLogic implements IUpgradeLogic {

	private int mCurProgress = 0;
	private int mUpdatedProgress = 0;

	private boolean isProgressing = false;

	private UpdateTimer mUpdateTimer;

	public UpgradeLogic(Context context) {
		super(context);
	}

	@Override
	public void getUpgradeInfo(AppInfoModel info, final ReqSendType type) {
		GetUpgradeInfoReq req = new GetUpgradeInfoReq(this, new IReturnCallback<GetUpgradeInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetUpgradeInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetUpgradeInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.reqSendType = type;
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = GlobalMessageType.UpgradeMessageType.MSG_GET_UPGRADE_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = GlobalMessageType.UpgradeMessageType.MSG_GET_UPGRADE_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.info = info;
		req.exec();
	}

	@Override
	public void downloadApp(UpgradeInfoModel info) {
		UpgradeMgr.getIntance(mcontext).download(info, new IUpgradeListener() {

			@Override
			public void onStart() {
				notifyStart();
				startTimer();
			}

			@Override
			public void onResult(String filePath) {
				stopTimer();
				if (ActivityUtil.isForeground(mcontext)) {
					// 若程序在前台，则直接提示安装。
					notifyCancel();
					ActivityUtil.installApk(mcontext, filePath);
				} else {
					// 若程序在后台，则通知栏提示安装。
					notifyComplete(filePath);
				}
			}

			@Override
			public void onProgress(long curLen, long totalLen) {
				//notifyProgress(curLen, totalLen);
				mCurProgress = (int) (curLen * 100 / totalLen);
				Logger.e(TAG, "Download: progress = %" + mCurProgress + ", curLen = " + curLen + ", totalLen = " + totalLen);
			}

			@Override
			public void onError(int errorCode) {
				Logger.e(TAG, "Error msg: [ErrorCode = " + errorCode + ", ErrorDesc = " + getPkgErrorDescription(errorCode) + "]");
				stopTimer();
				notifyError(errorCode);
			}
		});
	}

	@Override
	public void cancelDownload() {
		sendEmptyMesage(UpgradeMessageType.MSG_DOWNLOAD_CANCELED);
		UpgradeMgr.getIntance(mcontext).cancelDownload();
		DefineNotification.clearById(notifyID());
	}

	@Override
	public boolean isDownloadingUpgradePkg() {
		return UpgradeMgr.getIntance(mcontext).isDownloadPkg();
	}

	private void notifyStart() {
		sendEmptyMesage(UpgradeMessageType.MSG_START_DOWNLOAD);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
		builder.setSmallIcon(R.drawable.icon).setTicker(mcontext.getString(R.string.upgrade_pkg_notify_start)).setContentTitle(mcontext.getString(R.string.upgrade_pkg_notify_title))
				.setContentText(mcontext.getString(R.string.upgrade_pkg_notify_start)).setProgress(100, 0, false);
		builder.setOngoing(true);
		setNullIntentForNotification(builder);
		DefineNotification.notifyCommon(mcontext, notifyID(), builder.build());
	}

	private void notifyProgress(int progress) {
		Logger.i(TAG, "notifyProgress: progress = %" + progress);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
		//int progress = (int) (curLen * 100 / totalLen);
		builder.setSmallIcon(R.drawable.icon).setTicker(mcontext.getString(R.string.upgrade_pkg_notify_progress)).setContentTitle(mcontext.getString(R.string.upgrade_pkg_notify_title))
				.setContentText(mcontext.getString(R.string.upgrade_pkg_notify_progress) + " " + progress + "%").setProgress(100, progress, false);
		builder.setOngoing(true);
		setNullIntentForNotification(builder);
		DefineNotification.notifyCommon(mcontext, notifyID(), builder.build());
	}

	private void notifyComplete(String filePath) {
		sendEmptyMesage(UpgradeMessageType.MSG_DOWNLOAD_SUCCESS);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
		builder.setSmallIcon(R.drawable.icon).setTicker(mcontext.getString(R.string.upgrade_pkg_notify_complete)).setContentTitle(mcontext.getString(R.string.upgrade_pkg_notify_title))
				.setContentText(mcontext.getString(R.string.upgrade_pkg_notify_complete));
		builder.setAutoCancel(true);

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");

		PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0, intent, 0);
		builder.setContentIntent(pendingIntent);
		DefineNotification.notifyCommon(mcontext, notifyID(), builder.build());
	}

	private void notifyError(int errorCode) {
		sendEmptyMesage(UpgradeMessageType.MSG_DOWNLOAD_FAILED);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
		builder.setSmallIcon(R.drawable.icon).setTicker(mcontext.getString(R.string.upgrade_pkg_notify_fail)).setContentTitle(mcontext.getString(R.string.upgrade_pkg_notify_title))
				.setContentText(mcontext.getString(R.string.upgrade_pkg_notify_fail));
		builder.setAutoCancel(true);
		setNullIntentForNotification(builder);
		DefineNotification.notifyCommon(mcontext, notifyID(), builder.build());
	}

	private void notifyCancel() {
		sendEmptyMesage(UpgradeMessageType.MSG_DOWNLOAD_CANCELED);
		DefineNotification.clearById(notifyID());
	}

	private void setNullIntentForNotification(NotificationCompat.Builder builder) {
		Intent intent = new Intent();
		PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0, intent, 0);
		builder.setContentIntent(pendingIntent);
	}

	private int notifyID() {
		return UpgradeNotifyID.ID_UPGRADE_PKG_DOWNLOAD;
	}

	/**
	 * 下载安装包的错误描述
	 * @param errCode 错误码
	 * @return 错误描述
	 */
	private String getPkgErrorDescription(int errCode) {
		int resID = 0;
		if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_SDCARD) {
			Logger.e(TAG, "SDCARD异常");
			resID = R.string.upgrade_pkg_error_sdcard;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_HTTP) {
			Logger.e(TAG, "升级异常，HTTP返回码错误");
			resID = R.string.upgrade_pkg_error_http;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_NET) {
			Logger.e(TAG, "网络异常");
			resID = R.string.upgrade_pkg_error_net;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_CANCELED) {
			Logger.e(TAG, "用户取消");
			resID = R.string.upgrade_pkg_error_canceled;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_FILE_MD5_DISMATCH) {
			Logger.e(TAG, "文件MD5不匹配");
			resID = R.string.upgrade_pkg_error_file_md5_dismatch;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_FILE_SIZE_DISMATCH) {
			Logger.e(TAG, "文件大小不匹配");
			resID = R.string.upgrade_pkg_error_file_size_dismatch;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_FILE_DELETED) {
			Logger.e(TAG, "文件被删除");
			resID = R.string.upgrade_pkg_error_file_deleted;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_FILE_SIZE_INVALID) {
			Logger.e(TAG, "文件大小异常");
			resID = R.string.upgrade_pkg_error_file_deleted;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_SDCARD_SPACE) {
			Logger.e(TAG, "SDCARD空间不足");
			resID = R.string.upgrade_pkg_error_sdcard_space;
		} else if (errCode == UpgradeMgr.UpgradeErrorCode.ERROR_OTHER) {
			Logger.e(TAG, "其它异常");
			resID = R.string.upgrade_pkg_error_other;
		} else {
			Logger.e(TAG, "其它异常");
			resID = R.string.upgrade_pkg_error_default;
		}
		return mcontext.getString(resID);
	}

	private void startTimer() {
		mCurProgress = 0;
		mUpdatedProgress = 0;
		isProgressing = true;
		if (mUpdateTimer == null) {
			mUpdateTimer = new UpdateTimer();
			mUpdateTimer.start();
		}
	}

	private void stopTimer() {
		isProgressing = false;
	}

	public class UpdateTimer extends Thread {

		@Override
		public void run() {
			while (isProgressing) {
				if (mCurProgress > mUpdatedProgress) {
					mUpdatedProgress = mCurProgress;
					//sendMessage(UpgradeMessageType.MSG_UPDATE_PROGRESS, mUpdatedProgress);
					notifyProgress(mUpdatedProgress);
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
