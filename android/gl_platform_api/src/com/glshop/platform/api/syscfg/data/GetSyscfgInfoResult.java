package com.glshop.platform.api.syscfg.data;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.syscfg.data.model.SyncInfoModel;

/**
 * @Description : 获取系统参数信息结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetSyscfgInfoResult extends CommonResult {

	/**
	 * 系统参数时间戳列表
	 */
	public Map<String, String> sysCfgTimestamp;

	/**
	 * 系统参数列表
	 */
	//public Map<String, List<SyscfgInfoModel>> sysCfgData;

	public SyncInfoModel sysSyncInfo;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TimeStamp[");
		Iterator<Entry<String, String>> it = sysCfgTimestamp.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			sb.append("type = " + entry.getKey());
			sb.append(" : timeStamp = " + entry.getValue());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");

		//TODO show data list
		return super.toString() + ", " + sb.toString();
	}

}
