package com.glshop.net.logic.buy;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Message;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalMessageType.BuyMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.MyBuyFilterType;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.buy.GetBuyInfoReq;
import com.glshop.platform.api.buy.GetBuysReq;
import com.glshop.platform.api.buy.GetMyBuysReq;
import com.glshop.platform.api.buy.GetPriceForecastReq;
import com.glshop.platform.api.buy.GetTodayPriceReq;
import com.glshop.platform.api.buy.PubBuyInfoReq;
import com.glshop.platform.api.buy.UndoPubBuyInfoReq;
import com.glshop.platform.api.buy.UpdateBuyInfoReq;
import com.glshop.platform.api.buy.WantToDealReq;
import com.glshop.platform.api.buy.data.GetBuyInfoResult;
import com.glshop.platform.api.buy.data.GetBuysResult;
import com.glshop.platform.api.buy.data.GetMyBuysResult;
import com.glshop.platform.api.buy.data.GetPriceForecastResult;
import com.glshop.platform.api.buy.data.GetTodayPriceResult;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.BuySummaryInfoModel;
import com.glshop.platform.api.buy.data.model.ForecastPriceModel;
import com.glshop.platform.api.buy.data.model.MyBuySummaryInfoModel;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 买卖供求业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:09:19
 */
public class BuyLogic extends BasicLogic implements IBuyLogic {

	private static final String TAG = "BuyLogic";

	private List<MenuItemInfo> mSandGroupList;

	private Map<MenuItemInfo, List<ForecastPriceModel>> mSandChildList;

	public BuyLogic(Context context) {
		super(context);
	}

	@Override
	public void getTodayPrice(final ProductType type, String productCode, String areaCode) {
		GetTodayPriceReq req = new GetTodayPriceReq(this, new IReturnCallback<GetTodayPriceResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetTodayPriceResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetTodayPriceResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {
						// 获取货物属性详情
						if (result.datas != null) {
							for (TodayPriceModel info : result.datas) {
								covertTodayPriceInfo(type, info);
							}
						}

						int dataType = DataType.SAND_TODAY_PRICE_LIST;
						switch (type) {
						case SAND:
							dataType = DataType.SAND_TODAY_PRICE_LIST;
							SysCfgUtils.sortTodayPriceList(ProductType.SAND, result.datas);
							break;
						case STONE:
							dataType = DataType.STONE_TODAY_PRICE_LIST;
							SysCfgUtils.sortTodayPriceList(ProductType.STONE, result.datas);
							break;
						}

						DataCenter.getInstance().addData(result.datas, dataType, DataReqType.INIT);
						message.what = BuyMessageType.MSG_GET_TODAY_PRICE_SUCCESS;
					} else {
						message.what = BuyMessageType.MSG_GET_TODAY_PRICE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.productType = type;
		req.productCode = productCode;
		req.areaCode = areaCode;
		req.exec();
	}

	@Override
	public void getPriceForecast(final ProductType type, String productCode, String areaCode, final DataReqType reqType) {
		GetPriceForecastReq req = new GetPriceForecastReq(this, new IReturnCallback<GetPriceForecastResult>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetPriceForecastResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetPriceForecastResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {
						int dataType = DataType.SAND_FORECAST_PRICE_LIST;
						switch (type) {
						case SAND:
							//dataType = DataType.SAND_FORECAST_PRICE_LIST;
							if (result.datas != null) {
								Object[] sandGroupList = SysCfgUtils.parseSandPriceForecastList(mcontext, result.datas);
								if (sandGroupList == null || sandGroupList.length != 2) {
									mSandGroupList = null;
									mSandChildList = null;
								} else {
									mSandGroupList = (List<MenuItemInfo>) sandGroupList[0];
									mSandChildList = (Map<MenuItemInfo, List<ForecastPriceModel>>) sandGroupList[1];
								}
							}
							break;
						case STONE:
							dataType = DataType.STONE_FORECAST_PRICE_LIST;
							if (result.datas != null) {
								SysCfgUtils.parseStonePriceForecastList(mcontext, result.datas);
								DataCenter.getInstance().addData(result.datas, dataType, reqType);
							}
							break;
						}
						message.what = BuyMessageType.MSG_GET_PRICE_FORECAST_SUCCESS;
					} else {
						message.what = BuyMessageType.MSG_GET_PRICE_FORECAST_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.productType = type;
		req.productCode = productCode;
		req.areaCode = areaCode;
		req.exec();
	}

	@Override
	public void getBuys(BuyFilterInfoModelV2 filterInfo, final BuyType type, int pageIndex, int pageSize, final DataReqType reqType) {
		GetBuysReq req = new GetBuysReq(this, new IReturnCallback<GetBuysResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetBuysResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetBuysResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = reqType.toValue();
					respInfo.intArg2 = type.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {
						if (BeanUtils.isNotEmpty(result.datas)) {
							for (BuySummaryInfoModel info : result.datas) {
								String productName = SysCfgUtils.getProductFullName(mcontext, info.productCode, info.productSubCode, info.productSpecId);
								if (StringUtils.isNotEmpty(productName)) {
									info.productName = productName;
								}
							}
						}

						message.what = BuyMessageType.MSG_GET_BUYS_SUCCESS;
						respInfo.data = result.datas == null ? 0 : result.datas.size();

						int dataType = DataType.FINDBUY_BUYER_LIST;
						switch (type) {
						case BUYER:
							dataType = DataType.FINDBUY_BUYER_LIST;
							break;
						case SELLER:
							dataType = DataType.FINDBUY_SELLER_LIST;
							break;
						}

						DataCenter.getInstance().addData(result.datas, dataType, reqType);
					} else {
						message.what = BuyMessageType.MSG_GET_BUYS_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.filterInfo = filterInfo;
		req.buyType = type;
		req.pageIndex = pageIndex;
		req.pageSize = pageSize;
		req.exec();
	}

	@Override
	public void getBuyInfo(String buyId) {
		GetBuyInfoReq req = new GetBuyInfoReq(this, new IReturnCallback<GetBuyInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetBuyInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetBuyInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						BuyInfoModel info = result.data;
						if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.productTypeCode)) {
							info.productTypeName = mcontext.getString(R.string.product_type_sand);
						} else if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(info.productTypeCode)) {
							info.productTypeName = mcontext.getString(R.string.product_type_stone);
						}
						info.productSepcInfo = SysCfgUtils.getProductCfgInfo(mcontext, info.productTypeCode, info.productCategoryCode, info.productSpecId);
						message.what = BuyMessageType.MSG_GET_BUY_INFO_SUCCESS;
						respInfo.data = result.data;
					} else {
						message.what = BuyMessageType.MSG_GET_BUY_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.buyInfoId = buyId;
		req.exec();
	}

	@Override
	public void getMyBuys(MyBuyFilterType filterType, String companyId, final int pageIndex, final int pageSize, final DataReqType reqType) {
		GetMyBuysReq req = new GetMyBuysReq(this, new IReturnCallback<GetMyBuysResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetMyBuysResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetMyBuysResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					respInfo.intArg1 = reqType.toValue();
					message.obj = respInfo;
					if (result.isSuccess()) {
						if (BeanUtils.isNotEmpty(result.datas)) {
							for (MyBuySummaryInfoModel info : result.datas) {
								String productName = SysCfgUtils.getProductFullName(mcontext, info.productCode, info.productSubCode, info.productSpecId);
								if (StringUtils.isNotEmpty(productName)) {
									info.productName = productName;
								}
							}
						}

						message.what = BuyMessageType.MSG_GET_MYBUYS_SUCCESS;
						respInfo.data = result.datas == null ? 0 : result.datas.size();

						DataCenter.getInstance().addData(result.datas, DataType.MY_BUY_LIST, reqType);
					} else {
						message.what = BuyMessageType.MSG_GET_MYBUYS_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.filterType = filterType;
		req.companyId = companyId;
		req.pageIndex = pageIndex;
		req.pageSize = pageSize;
		req.exec();
	}

	@Override
	public void pubBuyInfo(BuyInfoModel info) {
		PubBuyInfoReq req = new PubBuyInfoReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "PubBuyInfoResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = BuyMessageType.MSG_PUB_BUY_INFO_SUCCESS;
					} else {
						message.what = BuyMessageType.MSG_PUB_BUY_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.buyInfo = info;
		req.exec();
	}

	@Override
	public void updateBuyInfo(BuyInfoModel info) {
		UpdateBuyInfoReq req = new UpdateBuyInfoReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "UpdateBuyInfoResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = BuyMessageType.MSG_UPDATE_PUB_BUY_INFO_SUCCESS;
					} else {
						message.what = BuyMessageType.MSG_UPDATE_PUB_BUY_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.buyInfo = info;
		req.exec();
	}

	@Override
	public void repubBuyInfo(String buyId) {

	}

	@Override
	public void undoPubBuyInfo(String buyId) {
		UndoPubBuyInfoReq req = new UndoPubBuyInfoReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "UndoPubBuyResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = BuyMessageType.MSG_UNDO_PUB_BUY_INFO_SUCCESS;
					} else {
						message.what = BuyMessageType.MSG_UNDO_PUB_BUY_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.buyInfoId = buyId;
		req.exec();
	}

	@Override
	public void wantToDeal(String buyId) {
		WantToDealReq req = new WantToDealReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "WantToDealResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = BuyMessageType.MSG_WANT_TO_DEAL_SUCCESS;
					} else {
						message.what = BuyMessageType.MSG_WANT_TO_DEAL_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.buyInfoId = buyId;
		req.exec();
	}

	private void covertTodayPriceInfo(ProductType type, TodayPriceModel info) {
		if (type == ProductType.SAND) {
			info.productInfo = SysCfgUtils.getProductCfgInfo(mcontext, DataConstants.SysCfgCode.TYPE_PRODUCT_SAND, info.productSepcType, info.productSepcId);
		} else {
			info.productInfo = SysCfgUtils.getProductCfgInfo(mcontext, DataConstants.SysCfgCode.TYPE_PRODUCT_STONE, info.productSepcType, info.productSepcId);
		}
	}

	@Override
	public Object[] getSandPriceForecastList() {
		Object[] sandGroupList = new Object[2];
		sandGroupList[0] = mSandGroupList;
		sandGroupList[1] = mSandChildList;
		return sandGroupList;
	}

}
