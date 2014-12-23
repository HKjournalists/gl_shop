package com.glshop.platform.api.base;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.net.base.ErrorItem;
import com.glshop.platform.net.base.ResultItem;

/**
 * FileName    : BaseResult.java
 * Description : 
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-14 下午3:12:52
 **/
public class CommonResult extends ResultItem {

	public int retcode = 0;
	public String msg;
	public long datetime;
	public ErrorItem error;
	public ProgressInfo progress;

	/***
	 * API业务操作是否成功
	 * @return
	 */
	public boolean isSuccess() {
		return DataConstants.MSG_OK == retcode;
	}

	@Override
	public String toString() {
		return "retCode=" + retcode + " msg=" + msg + " datetime=" + datetime + " error=[" + (error == null ? null : error.toString()) + "] progress=["
				+ (progress == null ? null : progress.toString()) + "]";
	}

}
