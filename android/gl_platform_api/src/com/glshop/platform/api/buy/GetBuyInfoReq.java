package com.glshop.platform.api.buy;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyStatus;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.data.GetBuyInfoResult;
import com.glshop.platform.api.buy.data.model.AreaPriceInfoModel;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取买卖信息详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:25:31
 */
public class GetBuyInfoReq extends BaseRequest<GetBuyInfoResult> {

	/**
	 * 买卖信息ID
	 */
	public String buyInfoId;

	public GetBuyInfoReq(Object invoker, IReturnCallback<GetBuyInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetBuyInfoResult getResultObj() {
		return new GetBuyInfoResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("fid", buyInfoId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetBuyInfoResult result, ResultItem item) {
		ResultItem itemBuy = (ResultItem) item.get("DATA");
		BuyInfoModel info = new BuyInfoModel();
		info.buyType = BuyType.convert(itemBuy.getInt("type"));
		info.buyId = itemBuy.getString("id");
		info.buyStatus = BuyStatus.convert(itemBuy.getInt("status"));

		// 解析货物规格信息
		info.productCode = itemBuy.getString("pcode");
		info.productSubCode = itemBuy.getString("ptype");
		info.specId = itemBuy.getString("pid");
		info.specName = itemBuy.getString("pname");
		info.companyId = itemBuy.getString("cid");

		ProductInfoModel productInfo = new ProductInfoModel();
		productInfo.color = itemBuy.getString("pcolor");
		productInfo.area = itemBuy.getString("paddress");
		productInfo.remarks = itemBuy.getString("premark");
		List<ResultItem> propList = (List<ResultItem>) itemBuy.get("oppList");
		if (propList != null && propList.size() > 0) {
			for (ResultItem prop : propList) {
				getPropInfo(productInfo, prop);
			}
		}

		// 地域
		info.isMoreArea = "2".equals(itemBuy.getString("morearea"));
		info.tradeArea = itemBuy.getString("area");
		info.unitPrice = itemBuy.getFloat("price");
		info.tradeAmount = itemBuy.getFloat("totalnum");

		// 地址指定方式
		ResultItem addrTypeItem = (ResultItem) itemBuy.get("addresstype");
		if (addrTypeItem != null) {
			info.deliveryAddrType = DeliveryAddrType.convert(addrTypeItem.getInt("val"));
		}

		if (info.isMoreArea) {
			List<ResultItem> areaItemList = (List<ResultItem>) itemBuy.get("moreAreaInfos");
			if (BeanUtils.isNotEmpty(areaItemList)) {
				List<AreaPriceInfoModel> areaInfoList = new ArrayList<AreaPriceInfoModel>();
				for (ResultItem area : areaItemList) {
					AreaPriceInfoModel priceInfo = new AreaPriceInfoModel();
					AreaInfoModel areaInfo = new AreaInfoModel();
					areaInfo.code = area.getString("area");
					priceInfo.areaInfo = areaInfo;
					priceInfo.unitPrice = area.getFloat("price");
					areaInfoList.add(priceInfo);
				}
				info.areaInfoList = areaInfoList;
			}
		}

		// 卸货地址
		AddrInfoModel addrInfo = new AddrInfoModel();
		addrInfo.addrId = itemBuy.getString("addressid");
		addrInfo.deliveryAddrDetail = itemBuy.getString("address");
		addrInfo.uploadPortWaterDepth = itemBuy.getFloat("deep");
		addrInfo.uploadPortShippingWaterDepth = itemBuy.getFloat("realdeep");

		// 卸货地址图片信息
		List<ImageInfoModel> addrImageList = new ArrayList<ImageInfoModel>();
		List<ResultItem> addrImageItems = (ArrayList<ResultItem>) itemBuy.get("addressImgList");
		if (BeanUtils.isNotEmpty(addrImageItems)) {
			for (ResultItem imageItem : addrImageItems) {
				ImageInfoModel image = new ImageInfoModel();
				image.cloudId = imageItem.getString("id");
				image.cloudUrl = imageItem.getString("url");
				image.cloudThumbnailUrl = imageItem.getString("thumbnailSmall");
				addrImageList.add(image);
			}
		}
		addrInfo.addrImageList = addrImageList;
		info.addrInfo = addrInfo;

		// 交易时间
		info.tradePubDate = itemBuy.getString("creatime");
		info.tradeBeginDate = itemBuy.getString("starttime");
		info.tradeEndDate = itemBuy.getString("limitime");
		info.buyRemarks = itemBuy.getString("remark");

		// 解析实物照片信息
		List<ImageInfoModel> productImageList = new ArrayList<ImageInfoModel>();
		List<ResultItem> productImageItems = (ArrayList<ResultItem>) itemBuy.get("productImgList");
		if (BeanUtils.isNotEmpty(productImageItems)) {
			for (ResultItem imageItem : productImageItems) {
				ImageInfoModel image = new ImageInfoModel();
				image.cloudId = imageItem.getString("id");
				image.cloudUrl = imageItem.getString("url");
				image.cloudThumbnailUrl = imageItem.getString("thumbnailSmall");
				productImageList.add(image);
			}
		}
		info.productImgList = productImageList;

		// 解析企业信息
		info.publisherCompany = itemBuy.getString("cname");
		ResultItem evaItem = (ResultItem) itemBuy.get("evaluationInfo");
		if (evaItem != null) {
			info.publisherTurnoverRate = evaItem.getFloat("transactionSuccessRate");
			info.publisherSatisfaction = (int) evaItem.getFloat("averageEvaluation");
			info.publisherCredit = (int) evaItem.getFloat("averageCredit");
		}

		info.productInfo = productInfo;

		// 当前用户是否已点击
		info.isClicked = (int) itemBuy.getInt("isApply") == 1;

		result.data = info;
	}

	private ProductPropInfoModel getPropInfo(ProductInfoModel productInfo, ResultItem item) {
		ProductPropInfoModel propItem = new ProductPropInfoModel();
		String propCode = item.getString("code");
		propItem.mPropId = item.getString("id");
		propItem.mPropCode = item.getString("code");
		propItem.mRealSize = item.getFloat("content");
		if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_SEDIMENT)) {
			productInfo.sedimentPercentage = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_SEDIMENT_BLOCK)) {
			productInfo.sedimentBlockPercentage = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_WATER)) {
			productInfo.waterPercentage = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_CRUNCH)) {
			productInfo.crunchPercentage = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_NEEDLE_PLATE)) {
			productInfo.needlePlatePercentage = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_APPEARANCE)) {
			productInfo.appearanceDensity = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_STACKING)) {
			productInfo.stackingPercentage = propItem;
		} else if (propCode.equals(DataConstants.SysCfgCode.TYPE_PROP_STURDINESS)) {
			productInfo.sturdinessPercentage = propItem;
		}
		return propItem;
	}

	@Override
	protected String getTypeURL() {
		return "/order/open/getInfo";
	}

}
