package com.glshop.net.logic.upgrade.mgr;

import java.io.File;

import android.content.Context;

import com.glshop.net.common.GlobalConstants;
import com.glshop.net.utils.MD5;
import com.glshop.net.utils.SDCardUtils;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.net.base.IRequestCallBack;
import com.glshop.platform.net.base.IResponseItem;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.net.http.image.DownloadRequest;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 升级模块管理类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:15
 */
public class UpgradeMgr {

	private static final String TAG = "UpgradeMgr";

	private Context mContext;

	private static UpgradeMgr mInstance;

	private UpgradeInfoModel mUpgradeInfo;

	private boolean isDownloadingPkg = false;

	private DownloadRequest mDownloadRequest;

	private UpgradeMgr(Context context) {
		mContext = context;
	}

	public static synchronized UpgradeMgr getIntance(Context context) {
		if (mInstance == null) {
			mInstance = new UpgradeMgr(context);
		}
		return mInstance;
	}

	/**
	 * 执行升级包下载
	 * @param info
	 * @param listener
	 */
	public void download(UpgradeInfoModel info, IUpgradeListener listener) {
		if (info != null && mUpgradeInfo != null && mUpgradeInfo == info) {
			Logger.e(TAG, "当前升级包正在下载中,忽略此次请求!");
		} else {
			mUpgradeInfo = info;
			startDownload(listener);
		}
	}

	private void startDownload(final IUpgradeListener listener) {
		isDownloadingPkg = true;
		// 判断下载URL信息
		if (StringUtils.isEmpty(mUpgradeInfo.url)) {
			cleanTaskStatus();
			if (listener != null) {
				listener.onError(UpgradeErrorCode.ERROR_URL);
			}
			return;
		}

		final String upgradeDir = getUpgradeDir();
		// 判断下载目录
		if (StringUtils.isNotEmpty(upgradeDir)) {
			// 判断是否已下载
			final String filePath = getUpgradeFilePath();
			if (checkApk(filePath, mUpgradeInfo.md5, true)) {
				cleanTaskStatus();
				if (listener != null) {
					listener.onResult(filePath);
				}
				return;
			}

			// 校验SD卡剩余存储空间
			try {
				if (StringUtils.isNotEmpty(mUpgradeInfo.size)) {
					if (Long.parseLong(mUpgradeInfo.size) > SDCardUtils.getAvailableExternalMemorySize()) {
						cleanTaskStatus();
						if (listener != null) {
							listener.onError(UpgradeErrorCode.ERROR_SDCARD_SPACE);
						}
						return;
					}
				}
			} catch (Exception e) {

			}

			// 删除下载目录下所有文件
			deleteAllTempFile(upgradeDir);

			// 取消之前下载请求
			if (mDownloadRequest != null) {
				mDownloadRequest.cancel();
			}

			// 开始下载
			if (listener != null) {
				listener.onStart();
			}

			String url = mUpgradeInfo.url;
			final String tempFilePath = filePath + ".tmp";

			File tempFile = new File(tempFilePath);
			if (!tempFile.exists()) {
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdirs();
				}
			}

			mDownloadRequest = new DownloadRequest(url, tempFilePath, new IRequestCallBack() {

				@Override
				public void onResponseEvent(ResponseEvent event, IResponseItem response) {
					Logger.i(TAG, "Event = " + event);
					switch (event) {
					case START:
						if (listener != null) {
							listener.onStart();
						}
						break;
					case PROGRESS:
						if (listener != null) {
							listener.onProgress(response.getCompleteSize(), response.getTotalSize());
						}
						break;
					case SUCCESS:
						File file = new File(filePath);
						File tempFile = new File(tempFilePath);
						if (listener != null) {
							if (!tempFile.exists()) {
								if (listener != null) {
									Logger.i(TAG, "Event = ERROR_FILE_DELETED" + event);
									listener.onError(UpgradeErrorCode.ERROR_FILE_DELETED);
								}
							} else {
								if (StringUtils.isEmpty(mUpgradeInfo.md5) || mUpgradeInfo.md5.equals(MD5.getMD5File(tempFilePath))) {
									tempFile.renameTo(file);
									listener.onResult(filePath);
									Logger.i(TAG, "Event = OK" + event);
								} else {
									tempFile.delete();
									Logger.i(TAG, "Event = ERROR_FILE_MD5_DISMATCH" + event);
									listener.onError(UpgradeErrorCode.ERROR_FILE_MD5_DISMATCH);
								}
							}
						}
						cleanTaskStatus();
						break;
					case CANCEL:
						cleanTaskStatus();
						if (listener != null) {
							listener.onError(UpgradeErrorCode.ERROR_CANCELED);
						}
						break;
					case ERROR:
						cleanTaskStatus();
						if (listener != null) {
							listener.onError(UpgradeErrorCode.ERROR_OTHER);
						}
						break;
					default:
						cleanTaskStatus();
						if (listener != null) {
							listener.onError(UpgradeErrorCode.ERROR_OTHER);
						}
						break;
					}
				}
			});
			mDownloadRequest.exec();
		} else {
			cleanTaskStatus();
			if (listener != null) {
				listener.onError(UpgradeErrorCode.ERROR_SDCARD);
			}
			return;
		}
	}

	/**
	 * 当前是否正在下载中
	 * @return
	 */
	public boolean isDownloadPkg() {
		return isDownloadingPkg;
	}

	/**
	 * 取消当前下载
	 */
	public void cancelDownload() {
		if (mDownloadRequest != null) {
			mDownloadRequest.cancel();
			cleanTaskStatus();
		}
	}

	/**
	 * 清除当前下载状态
	 */
	private void cleanTaskStatus() {
		mUpgradeInfo = null;
		isDownloadingPkg = false;
	}

	private String getUpgradeDir() {
		String fullPath = "";
		if (SDCardUtils.isExternalSdcardWriteable()) {
			fullPath = GlobalConstants.AppDirConstants.UPGRADE;
		}
		return fullPath;
	}

	private String getUpgradeFilePath() {
		String fullPath = "";
		if (mUpgradeInfo != null) {
			String md5 = mUpgradeInfo.md5;
			if (StringUtils.isEmpty(md5)) {
				md5 = MD5.getMD5String(mUpgradeInfo.url);
			}
			if (SDCardUtils.isExternalSdcardWriteable()) {
				fullPath = GlobalConstants.AppDirConstants.UPGRADE + md5 + ".apk";
			}
		}
		return fullPath;
	}

	/**
	 * 检查本地apk是否存在
	 * @param path
	 * @param md5
	 * @param delete
	 * @return
	 */
	private boolean checkApk(String path, String md5, boolean delete) {
		File file = new File(path);
		if (file.exists()) {
			if (StringUtils.isEmpty(md5)) {
				Logger.i(TAG, "升级APK已存在，直接安装！");
				return true;
			} else {
				if (md5.equals(MD5.getMD5File(path))) {
					Logger.i(TAG, "升级APK已存在，直接安装！");
					return true;
				} else {
					if (delete) {
						file.delete();
					}
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 删除所有文件
	 * @param downloadDir
	 */
	private void deleteAllTempFile(String downloadDir) {
		File file = new File(downloadDir);
		if (file.isDirectory()) {
			File[] fileList = file.listFiles();
			if (BeanUtils.isNotEmpty(fileList)) {
				for (File fileItem : fileList) {
					if (fileItem.isFile()) {
						fileItem.delete();
					} else if (fileItem.isDirectory()) {
						deleteAllTempFile(fileItem.getAbsolutePath());
					}
				}
			}
		} else if (file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 升级错误码
	 */
	public interface UpgradeErrorCode {

		/** 其它 */
		public static final int ERROR_OTHER = 0;

		/** SDCARD异常 */
		public static final int ERROR_SDCARD = 1;

		/** 升级异常，http返回码错误 */
		public static final int ERROR_HTTP = 2;

		/** 升级异常，网络原因 */
		public static final int ERROR_NET = 3;

		/** 升级异常，退出*/
		public static final int ERROR_CANCELED = 4;

		/** URL不存在 */
		public static final int ERROR_URL = 5;

		/** 文件MD5不匹配 */
		public static final int ERROR_FILE_MD5_DISMATCH = 6;

		/** 文件大小不匹配 */
		public static final int ERROR_FILE_SIZE_DISMATCH = 7;

		/** 文件被删除 */
		public static final int ERROR_FILE_DELETED = 8;

		/** 文件大小异常 */
		public static final int ERROR_FILE_SIZE_INVALID = 9;

		/** SDCARD空间不足 */
		public static final int ERROR_SDCARD_SPACE = 10;

	}

	/**
	 * 升级回调接口
	 */
	public interface IUpgradeListener {

		public void onStart();

		public void onProgress(long curLen, long totalLen);

		public void onResult(String filePath);

		public void onError(int errorCode);

	}

}
