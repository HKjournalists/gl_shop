package com.glshop.platform.api.purse.data.model;

import com.glshop.platform.api.DataConstants.PayeeStatus;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 收款人信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class PayeeInfoModel extends ResultItem implements Cloneable {

	/**
	 * 收款人ID
	 */
	public String id;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 收款人姓名
	 */
	public String name;

	/**
	 * 银行名称
	 */
	public String bankName;

	/**
	 * 银行编码
	 */
	public String bankCode;

	/**
	 * 银行支行名称
	 */
	public String subBank;

	/**
	 * 银行卡号
	 */
	public String card;

	/**
	 * 认证信息图片信息
	 */
	public ImageInfoModel certImgInfo;

	/**
	 * 当前审核状态
	 */
	public PayeeStatus status = PayeeStatus.AUTHED;

	/**
	 * 是否默认收款人
	 */
	public boolean isDefault;

	@Override
	public Object clone() {
		PayeeInfoModel o = null;
		try {
			o = (PayeeInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof PayeeInfoModel) {
			PayeeInfoModel other = (PayeeInfoModel) o;
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
		sb.append("PayeeInfoModel[");
		sb.append("id=" + id);
		sb.append(", companyId=" + companyId);
		sb.append(", name=" + name);
		sb.append(", bankName=" + bankName);
		sb.append(", bankCode=" + bankCode);
		sb.append(", subBank=" + subBank);
		sb.append(", card=" + card);
		sb.append(", certImgInfo=" + certImgInfo);
		sb.append(", isDefault=" + isDefault);
		sb.append("]");
		return sb.toString();
	}
}
