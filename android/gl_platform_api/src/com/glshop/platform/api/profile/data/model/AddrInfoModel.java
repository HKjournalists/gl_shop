package com.glshop.platform.api.profile.data.model;

import java.util.List;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 卸货地址信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class AddrInfoModel extends ResultItem implements Cloneable {

	/**
	 * 地址ID
	 */
	public String addrId;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 交货详细地址信息
	 */
	public String deliveryAddrDetail;

	/**
	 * 卸货地址云端图片链接
	 */
	public List<ImageInfoModel> addrImageList;

	/**
	 * 卸货港口水深
	 */
	public float uploadPortWaterDepth;

	/**
	 * 卸货个港口实际吃水深度
	 */
	public float uploadPortShippingWaterDepth;

	/**
	 * 是否默认卸货地址
	 */
	public boolean isDefaultAddr;

	@Override
	public Object clone() {
		AddrInfoModel o = null;
		try {
			o = (AddrInfoModel) super.clone();
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

		if (o instanceof AddrInfoModel) {
			AddrInfoModel other = (AddrInfoModel) o;
			if (this.addrId == null || other.addrId == null) {
				return false;
			} else {
				return this.addrId.equals(addrId);
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
		sb.append("AddrInfoModel[");
		sb.append("addrId=" + addrId);
		sb.append(", companyId=" + companyId);
		sb.append(", deliveryAddrDetail=" + deliveryAddrDetail);
		sb.append(", addrImageList=" + addrImageList);
		sb.append(", uploadPortWaterDepth=" + uploadPortWaterDepth);
		sb.append(", uploadPortShippingWaterDepth=" + uploadPortShippingWaterDepth);
		sb.append(", isDefaultAddr=" + isDefaultAddr);
		sb.append("]");
		return sb.toString();
	}

}
