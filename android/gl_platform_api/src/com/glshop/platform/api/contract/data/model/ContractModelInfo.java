package com.glshop.platform.api.contract.data.model;

/**
 * @Description : 合同模板信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class ContractModelInfo extends ContractSummaryInfoModel {

	/**
	 * 合同名称
	 */
	public String contractName;

	/**
	 * 合同正文
	 */
	public String content;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ContractModelInfo[");
		sb.append("contractId=" + contractId);
		sb.append(", buyCompanyId=" + buyCompanyId);
		sb.append(", contractName=" + contractName);
		sb.append(", buyCompanyName=" + buyCompanyName);
		sb.append(", sellCompanyId=" + sellCompanyId);
		sb.append(", sellCompanyName=" + sellCompanyName);
		sb.append(", content=" + content);
		sb.append("]");
		return sb.toString();
	}

}
