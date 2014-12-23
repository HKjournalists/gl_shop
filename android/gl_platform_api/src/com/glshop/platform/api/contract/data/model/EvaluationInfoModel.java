package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 评价信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class EvaluationInfoModel extends ResultItem {

	/**
	 * 评价ID
	 */
	public String id;

	/**
	 * 合同ID
	 */
	public String contractId;
	
	/**
	 * 评论者企业ID
	 */
	public String evaluaterCID;
	
	/**
	 * 被评论者企业ID
	 */
	public String beEvaluaterCID;
	
	/**
	 * 评价的用户
	 */
	public String user;

	/**
	 * 交易满意度(1-5)
	 */
	public int satisfactionPer;

	/**
	 * 交易诚信度(1-5)
	 */
	public int sincerityPer;

	/**
	 * 评价内容
	 */
	public String content;

	/**
	 * 评价时间
	 */
	public String dateTime;

	/**
	 * 是否仅单行显示
	 */
	public boolean isSingleLine;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof EvaluationInfoModel) {
			EvaluationInfoModel other = (EvaluationInfoModel) o;
			if (this.id == null || other.id == null) {
				return false;
			} else {
				return this.id.equals(other.id);
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
		sb.append("EvaluationInfoModel[");
		sb.append("id=" + id);
		sb.append(", contractId=" + contractId);
		sb.append(", evaluaterCID=" + evaluaterCID);
		sb.append(", beEvaluaterCID=" + beEvaluaterCID);
		sb.append(", user=" + user);
		sb.append(", satisfactionPer=" + satisfactionPer);
		sb.append(", sincerityPer=" + sincerityPer);
		sb.append(", content=" + content);
		sb.append(", dateTime=" + dateTime);
		sb.append(", isSingleLine=" + isSingleLine);
		sb.append("]");
		return sb.toString();
	}

}
