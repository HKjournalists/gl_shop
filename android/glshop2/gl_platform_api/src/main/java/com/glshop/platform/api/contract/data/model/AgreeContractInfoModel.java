package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 合同确认返回信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class AgreeContractInfoModel extends ResultItem {

	/**
	 * 对方是否已签订
	 */
	public boolean isAnotherSigned;

	/**
	 * 对方签订时间
	 */
	public String singedDatetime;

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AgreeContractInfoModel[");
		sb.append("isAnotherSigned=" + isAnotherSigned);
		sb.append(", singedDatetime=" + singedDatetime);
		sb.append("]");
		return sb.toString();
	}

}
