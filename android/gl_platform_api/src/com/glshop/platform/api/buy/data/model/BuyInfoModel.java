package com.glshop.platform.api.buy.data.model;

import java.util.List;

import com.glshop.platform.api.DataConstants.BuyStatus;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 买卖信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 下午4:16:39
 */
public class BuyInfoModel extends ResultItem implements Cloneable {

	/**
	 * 发布信息ID
	 */
	public String buyId;

	/**
	 * 买卖类型
	 */
	public BuyType buyType = BuyType.BUYER;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 货物大类型编码
	 */
	public String productCode;

	/**
	 * 货物名称
	 */
	public String productName;
	
	/**
	 * 货物子类型编码
	 */
	public String productSubCode;

	/**
	 * 规格ID
	 */
	public String specId;

	/**
	 * 规格名称
	 */
	public String specName;

	/**
	 * 单价
	 */
	public float unitPrice;

	/**
	 * 总体数量
	 */
	public float tradeAmount;

	/**
	 * 已交易的数量
	 */
	public float tradedAmount;

	/**
	 * 交易地域
	 */
	public String tradeArea;

	/**
	 * 是否多地域发布
	 */
	public boolean isMoreArea;

	/**
	 * 多地域发布信息列表
	 */
	public List<AreaPriceInfoModel> areaInfoList;

	/**
	 * 货物规格信息
	 */
	public ProductInfoModel productInfo;

	/**
	 * 实物照片信息
	 */
	public List<ImageInfoModel> productImgList;

	/**
	 * 交易发布时间
	 */
	public String tradePubDate;

	/**
	 * 交易起始时间
	 */
	public String tradeBeginDate;

	/**
	 * 交易结束时间
	 */
	public String tradeEndDate;

	/**
	 * 交货地址方式
	 */
	public DeliveryAddrType deliveryAddrType = DeliveryAddrType.ME_DECIDE;

	/**
	 * 卸货地址信息
	 */
	public AddrInfoModel addrInfo;

	/**
	 * 交易备注
	 */
	public String buyRemarks;

	/**
	 * 信息发布者ID
	 */
	public String publisherID;

	/**
	 * 信息发布者公司名称
	 */
	public String publisherCompany;

	/**
	 * 信息发布者的满意度
	 */
	public float publisherSatisfaction;

	/**
	 * 信息发布者的诚信度
	 */
	public float publisherCredit;

	/**
	 * 信息发布者的成交率
	 */
	public float publisherTurnoverRate;

	/**
	 * 是否已点击感兴趣
	 */
	public boolean isClicked;

	/**
	 * 是否是自己发布
	 */
	public boolean isMyPublish;

	/**
	 * 感兴趣的客户人数
	 */
	public int interestedNumber;

	/**
	 * 发布买卖信息状态：有效、过期、审核未通过、交易已完成、用户取消
	 */
	public BuyStatus buyStatus = BuyStatus.VALID;

	/**
	 * 发布买卖信息状态时间：取消发布的时间、审核未通过的时间、交易已完成的时间等
	 */
	public long pubBuyStatusTime;

	@Override
	public Object clone() {
		BuyInfoModel o = null;
		try {
			o = (BuyInfoModel) super.clone();
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

		if (o instanceof BuyInfoModel) {
			BuyInfoModel other = (BuyInfoModel) o;
			if (this.buyId == null || other.buyId == null) {
				return false;
			} else {
				return this.buyId.equals(other.buyId);
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
		sb.append("BuyInfoModel[");
		sb.append("buyId=" + buyId);
		sb.append(", buyType=" + buyType.toValue());
		sb.append(", unitPrice=" + unitPrice);
		sb.append(", tradeAmount=" + tradeAmount);
		sb.append(", tradePubDate=" + tradePubDate);
		sb.append(", tradeBeginDate=" + tradeBeginDate);
		sb.append(", tradeEndDate=" + tradeEndDate);
		sb.append(", addrInfo=" + addrInfo);
		sb.append(", productImgList=" + productImgList);
		sb.append(", publisherSatisfaction=" + publisherSatisfaction);
		sb.append(", publisherCredit=" + publisherCredit);
		sb.append(", publisherTurnoverRate=" + publisherTurnoverRate);
		sb.append("]");
		return sb.toString();
	}

}
