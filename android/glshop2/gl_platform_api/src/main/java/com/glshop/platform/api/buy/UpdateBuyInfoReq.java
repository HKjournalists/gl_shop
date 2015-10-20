package com.glshop.platform.api.buy;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.buy.data.model.AreaPriceInfoModel;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 修改发布信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:19:29
 */
public class UpdateBuyInfoReq extends BaseRequest<CommonResult> {

	/**
	 * 修改的发布信息
	 */
	public BuyInfoModel buyInfo;

	public UpdateBuyInfoReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		if (buyInfo != null) {
			request.addParam("id", buyInfo.buyId);
			request.addParam("typeValue", buyInfo.buyType.toValue());
			request.addParam("cid", buyInfo.companyId);
			request.addParam("originalFid", buyInfo.oriBuyId);

			request.addParam("pid", buyInfo.productSpecId);
			request.addParam("pname", buyInfo.productSpecName);
			//request.addParam("ptype", "");
			//request.addParam("psize", "");
			request.addParam("pcolor", buyInfo.productColor);
			request.addParam("paddress", buyInfo.productArea);
			request.addParam("productPropertys", convertPropInfo2JSON(buyInfo.productTypeCode, buyInfo.productPropInfo));
			request.addParam("premark", buyInfo.productRemarks);
			request.addParam("remark", buyInfo.buyRemarks);

			request.addParam("price", buyInfo.unitPrice);
			request.addParam("totalnum", buyInfo.tradeAmount);
			request.addParam("unit", buyInfo.unitType.toValue());
			request.addParam("moreareaValue", buyInfo.isMoreArea ? "2" : "1");
			request.addParam("area", buyInfo.tradeAreaCode);

			// 设置多地域信息
			if (buyInfo.isMoreArea) {
				request.addParam("moreAreaInfos", createMoreAreaJson(buyInfo.areaInfoList));
			}

			// 卸货地址
			if (buyInfo.addrInfo != null) {
				request.addParam("addressid", buyInfo.addrInfo.addrId);
			}

			request.addParam("starttime", buyInfo.tradeBeginDate);
			request.addParam("endtime", buyInfo.tradeEndDate);

			// 卸货地址指定方式
			request.addParam("addresstypeValue", buyInfo.deliveryAddrType.toValue());

			// 设置实物照片信息
			if (buyInfo.buyType == BuyType.SELLER) {
				if (BeanUtils.isNotEmpty(buyInfo.productImgList)) {
					StringBuffer imgId = new StringBuffer();
					for (int i = 0; i < buyInfo.productImgList.size(); i++) {
						imgId.append(buyInfo.productImgList.get(i).cloudId + (i == buyInfo.productImgList.size() - 1 ? "" : ","));
					}
					request.addParam("productImgIds", imgId.toString());
				}
			}

		}
	}

	private String createMoreAreaJson(List<AreaPriceInfoModel> data) {
		JSONArray array = new JSONArray();
		if (data != null && data.size() > 0) {
			for (AreaPriceInfoModel area : data) {
				JSONObject object = new JSONObject();
				try {
					object.put("area", area.areaInfo.code);
					object.put("price", area.unitPrice);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				array.put(object);
			}
		}
		Logger.i(TAG, "AreaList = " + array.toString());
		return array.toString();
	}

	private String convertPropInfo2JSON(String productType, ProductInfoModel info) {
		String json = "";
		if (info != null) {
			JSONArray array = new JSONArray();
			addJsonItem(array, info.sedimentPercentage);
			addJsonItem(array, info.sedimentBlockPercentage);
			if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(productType)) {
				addJsonItem(array, info.waterPercentage);
			} else if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(productType)) {
				addJsonItem(array, info.crunchPercentage);
				addJsonItem(array, info.needlePlatePercentage);
			}
			addJsonItem(array, info.appearanceDensity);
			addJsonItem(array, info.stackingPercentage);
			addJsonItem(array, info.sturdinessPercentage);
			json = array.toString();
			Logger.i(TAG, "PropList = " + array.toString());
		}
		return json;
	}

	private void addJsonItem(JSONArray array, ProductPropInfoModel info) {
		JSONObject object = new JSONObject();
		if (info != null) {
			try {
				object.put("id", info.mPropId);
				object.put("content", info.mRealSize);
				array.put(object);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/order/mdy";
	}

}
