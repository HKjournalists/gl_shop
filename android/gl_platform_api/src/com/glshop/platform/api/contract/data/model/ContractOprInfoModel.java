package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 合同操作记录信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class ContractOprInfoModel extends ResultItem {

	/**
	 * 操作记录ID
	 */
	public String id;

	/**
	 * 合同编号
	 */
	public String contractId;

	/**
	 * 操作者ID
	 */
	public String oprId;

	/**
	 * 操作时间
	 */
	public String oprDatetime;

	/**
	 * 操作摘要
	 */
	public String summary;

	/**
	 * 操作时间
	 */
	public String dateTime;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof ContractOprInfoModel) {
			ContractOprInfoModel other = (ContractOprInfoModel) o;
			if (this.contractId == null || other.contractId == null) {
				return false;
			} else {
				return this.contractId.equals(other.contractId);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ContractOprInfoModel[");
		sb.append("id=" + id);
		sb.append(", contractId=" + contractId);
		sb.append(", oprId=" + oprId);
		sb.append(", oprDatetime=" + oprDatetime);
		sb.append(", summary=" + summary);
		sb.append(", dateTime=" + dateTime);
		sb.append("]");
		return sb.toString();
	}

}
