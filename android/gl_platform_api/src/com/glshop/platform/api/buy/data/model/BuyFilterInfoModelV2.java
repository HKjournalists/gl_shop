package com.glshop.platform.api.buy.data.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.glshop.platform.api.DataConstants.BuyOrderType;
import com.glshop.platform.api.DataConstants.OrderStatus;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 买卖信息过滤实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 下午4:16:39
 */
public class BuyFilterInfoModelV2 implements Serializable {

	/**
	 * ID
	 */
	private static final long serialVersionUID = -6630984664767618722L;

	/**
	 * 排序类别
	 */
	public BuyOrderType orderType = BuyOrderType.EXPIRY;

	/**
	 * 排序类型
	 */
	public OrderStatus orderStatus = OrderStatus.DESC;

	/**
	 * 货物规格编号列表
	 */
	public List<String> productIdList;

	/**
	 * 省级地域编号列表
	 */
	public List<String> provinceCodeList;

	/**
	 * 区级地域编号列表
	 */
	public List<String> districtCodeList;

	/**
	 * 交易起始日期
	 */
	public String tradeStartDate;

	/**
	 * 交易结束日期
	 */
	public String tradeEndDate;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof BuyFilterInfoModelV2) {
			BuyFilterInfoModelV2 other = (BuyFilterInfoModelV2) o;
			if (this.orderType != other.orderType) {
				return false;
			} else if (this.orderStatus != other.orderStatus) {
				return false;
			} else if (!isEqual(this.productIdList, other.productIdList)) {
				return false;
			} else if (!isEqual(this.provinceCodeList, other.provinceCodeList)) {
				return false;
			} else if (!isEqual(this.districtCodeList, other.districtCodeList)) {
				return false;
			} else if (!isEqual(this.tradeStartDate, other.tradeStartDate)) {
				return false;
			} else if (!isEqual(this.tradeEndDate, other.tradeEndDate)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean isEqual(List<String> left, List<String> right) {
		if (BeanUtils.isEmpty(left) && BeanUtils.isEmpty(right)) {
			return true;
		} else if (BeanUtils.isEmpty(left) && BeanUtils.isNotEmpty(right)) {
			return false;
		} else if (BeanUtils.isNotEmpty(left) && BeanUtils.isEmpty(right)) {
			return false;
		} else if (left.size() != right.size()) {
			return false;
		} else {
			sortList(left);
			sortList(right);
			return left.equals(right);
		}
	}

	private boolean isEqual(String left, String right) {
		if (StringUtils.isNEmpty(left) && StringUtils.isNEmpty(right)) {
			return true;
		} else if (StringUtils.isNotEmpty(left) && StringUtils.isNotEmpty(right) && left.equals(right)) {
			return true;
		} else {
			return false;
		}
	}

	private void sortList(List<String> list) {
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String info1, String info2) {
				if (StringUtils.isNEmpty(info1) || StringUtils.isNEmpty(info2)) {
					return 0;
				} else {
					if (StringUtils.isDigital(info1) && StringUtils.isDigital(info2)) {
						long lhs = Long.parseLong(info1);
						long rhs = Long.parseLong(info2);
						if (lhs > rhs) {
							return 1;
						} else if (lhs < rhs) {
							return -1;
						} else {
							return 0;
						}
					} else {
						return info1.compareTo(info2);
					}
				}
			}

		});
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BuyInfoModel[");
		sb.append("orderType=" + orderType);
		sb.append(", orderStatus=" + orderStatus);
		sb.append("]");
		return sb.toString();
	}

}
