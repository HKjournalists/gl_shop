package com.glshop.platform.api.base;

/**
 * FileName    : ProgressInfo.java
 * Description : 进度信息
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-25 下午4:06:56
 **/
public class ProgressInfo {

	/**进度的总长度*/
	public long totalSize;

	/**进度的完成大小*/
	public long completeSize;

	@Override
	public String toString() {
		return "completeSize=" + completeSize + " totalSize=" + totalSize;
	}

}
