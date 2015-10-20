package com.glshop.net.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

import android.os.Environment;
import android.os.StatFs;

import com.glshop.platform.utils.FileUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * FileName    : SDCardUtils.java
 * Description : SDCard工具类
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 叶跃丰
 * @version    : 1.0
 * Create Date : 2014-8-8 下午2:00:20
 **/
public class SDCardUtils {

	/** 标准外部sdcard路径 */
	public static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();

	/** 系统当前所有sdcard路径(包括内置及外部) */
	private static HashSet<String> sdcards = new HashSet<String>();

	/** 添加一个应用启动的加载SDCard的线程 */
	public static final void initMountSdcards() {
		sdcards.add(SDCardUtils.SDCARD);
		new Thread() {
			public void run() {
				findSdcardByCommand();
			}
		}.start();
	}

	public static boolean isExternalSdcardReadable() {
		String state = android.os.Environment.getExternalStorageState();
		return android.os.Environment.MEDIA_MOUNTED.equals(state);
	}

	public static boolean isExternalSdcardWriteable() {
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state) && android.os.Environment.getExternalStorageDirectory().canWrite()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前可用的sdcard
	 * @return
	 */
	public static String getAvailableSdcard() {
		String external = getExternalSdcard();
		if (external != null) {
			return external;
		} else {
			String internal = getInternalSdcard();
			if (internal != null) {
				return internal;
			}
		}
		return null;
	}

	/**
	 * 获取外部sdcard
	 * @return
	 */
	public static String getExternalSdcard() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return SDCARD;
		} else {
			return null;
		}
	}

	/**
	 * 获取内置sdcard
	 * @return
	 */
	public static String getInternalSdcard() {
		HashSet<String> sdcards = getMountedSdcards();
		if (sdcards.size() > 1) { //说明同时有外部sdcard和内置sdcard
			Iterator<String> iterator = sdcards.iterator();
			while (iterator.hasNext()) {
				String path = iterator.next();
				if (!SDCardUtils.SDCARD.equals(path)) {
					return path;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 获取多个sdcard的路径
	 * @return HashSet<String>
	 */
	public static final HashSet<String> getMountedSdcards() {
		return sdcards;
	}

	/**
	 * 通过命令获取sdcard列表
	 */
	private static void findSdcardByCommand() {
		String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
		String s = "";
		InputStream is = null;
		try {
			//进行命令查询
			Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
			//注释掉下面这行代码是因为在某些模块中会因为阻塞进程而出现问题
			//process.waitFor();
			is = process.getInputStream();
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				s = s + new String(buffer);
			}

			//进行数据解析和过滤
			String[] lines = s.split("\n");
			for (String line : lines) {
				if (!line.toLowerCase(Locale.US).contains("asec") && line.matches(reg)) {
					String[] parts = line.split(" ");
					for (String part : parts) {
						if (part.startsWith("/") && !part.toLowerCase(Locale.US).contains("vold")) {
							sdcards.add(part);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * getAvailableExternalMemorySize
	 * 获取内置sd卡的可用空间
	 * @return long
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return -1;
		}
	}

	/***
	 * 获取内置sdcard卡的总共空间大小
	 * @return
	 */
	public static long getAllExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getBlockCount();
			return availableBlocks * blockSize;
		} else {
			return -1;
		}
	}

	/**计算指定目录的空间是否满了
	 * getAvailableExternalMemorySize
	 * @param sdcardPath sdcardPath
	 * @return long
	 */
	public static long getAvailableExternalMemorySize(String sdcardPath) {
		if (!StringUtils.isEmpty(sdcardPath) && FileUtils.exists(sdcardPath)) {
			StatFs stat = new StatFs(sdcardPath);
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return -1;
		}
	}

	/**
	 * 判断是否连接了sdcard
	 * 
	 * @return boolean
	 */
	public static boolean externalMemoryAvailable() {

		if (getExternalStoragePath() != null) {
			return true;
		}
		return false;
	}

	private static String getExternalStoragePath() {
		// 获取SdCard状态
		String state = android.os.Environment.getExternalStorageState();

		// 判断SdCard是否存在并且是可用的
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
				return android.os.Environment.getExternalStorageDirectory().getPath();
			}
		}
		Logger.i("sdcard", "SD can not be used");
		return null;
	}

}
