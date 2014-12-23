package check;

import java.util.ArrayList;
import java.util.List;

import base.BaseTestCase;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.GetBuyInfoReq;
import com.glshop.platform.api.buy.GetBuysReq;
import com.glshop.platform.api.buy.GetMyBuysReq;
import com.glshop.platform.api.buy.GetPriceForecastReq;
import com.glshop.platform.api.buy.GetTodayPriceReq;
import com.glshop.platform.api.buy.PubBuyInfoReq;
import com.glshop.platform.api.buy.UndoPubBuyInfoReq;
import com.glshop.platform.api.buy.UpdateBuyInfoReq;
import com.glshop.platform.api.buy.WantToDealReq;
import com.glshop.platform.api.buy.data.model.AreaPriceInfoModel;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;

/**
 * @Description : 买卖信息测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class BuyAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		//addTestClass(GetTodayPriceReq.class, new CallBackBuilder<GetTodayPriceResult>()); // 获取今日价格列表
		//addTestClass(GetPriceForecastReq.class, new CallBackBuilder<GetPriceForecastResult>()); // 获取未来趋势列表
		//addTestClass(PubBuyInfoReq.class, new CallBackBuilder<CommonResult>()); // 发布买卖信息
		//addTestClass(GetBuysReq.class, new CallBackBuilder<GetBuysResult>()); // 获取找买找卖列表
		//addTestClass(GetMyBuysReq.class, new CallBackBuilder<GetMyBuysResult>()); // 获取我的供求列表
		//addTestClass(GetBuyInfoReq.class, new CallBackBuilder<GetBuyInfoResult>()); // 获取买卖信息详情
		//addTestClass(WantToDealReq.class, new CallBackBuilder<CommonResult>()); // 想交易请求
		//addTestClass(UpdateBuyInfoReq.class, new CallBackBuilder<CommonResult>()); // 更新已发布信息
		//addTestClass(UndoPubBuyInfoReq.class, new CallBackBuilder<CommonResult>()); // 取消发布
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof GetTodayPriceReq) { // 获取今日价格列表
			GetTodayPriceReq req = (GetTodayPriceReq) request;
			req.areaCode = "RS001";
			req.productCode = "G001";
		} else if (request instanceof GetPriceForecastReq) { // 获取未来趋势列表
			GetPriceForecastReq req = (GetPriceForecastReq) request;
			req.areaCode = "RS001";
			req.productCode = "G001";
		} else if (request instanceof PubBuyInfoReq) { // 发布买卖信息
			PubBuyInfoReq req = (PubBuyInfoReq) request;
			req.buyInfo = getPubBuyInfo(true);
		} else if (request instanceof GetBuysReq) { // 获取找买找卖列表
			GetBuysReq req = (GetBuysReq) request;
			req.buyType = DataConstants.BuyType.BUYER;
			req.companyId = "CompanyInfoId000000811102014END";
			req.pageIndex = 0;
			req.pageSize = 10;
		} else if (request instanceof GetMyBuysReq) { // 获取我的供求列表
			GetMyBuysReq req = (GetMyBuysReq) request;
			req.filterType = DataConstants.MyBuyFilterType.ALL;
			req.companyId = "CompanyInfoId000000811102014END";
			req.pageIndex = 0;
			req.pageSize = 10;
		} else if (request instanceof GetBuyInfoReq) { // 获取买卖信息详情
			GetBuyInfoReq req = (GetBuyInfoReq) request;
			req.buyInfoId = "000003114102014";
		} else if (request instanceof WantToDealReq) { // 想交易请求
			WantToDealReq req = (WantToDealReq) request;
			req.buyInfoId = "000004515102014";
		} else if (request instanceof UpdateBuyInfoReq) { // 更新已发布信息
			UpdateBuyInfoReq req = (UpdateBuyInfoReq) request;

			BuyInfoModel buyInfo = new BuyInfoModel();
			buyInfo.buyId = "000003114102014";
			buyInfo.companyId = "CompanyInfoId000000811102014END";
			buyInfo.buyType = BuyType.BUYER;

			ProductInfoModel productInfo = new ProductInfoModel();
			productInfo.color = "黄色";
			productInfo.area = "上海";

			buyInfo.unitPrice = 66.6f;
			buyInfo.tradeAmount = 100;
			buyInfo.isMoreArea = false;

			AddrInfoModel addrInfo = new AddrInfoModel();
			addrInfo.addrId = "10";
			buyInfo.addrInfo = addrInfo;
			buyInfo.deliveryAddrType = DeliveryAddrType.ME_DECIDE;

			buyInfo.buyRemarks = "购买信息备注2";

			buyInfo.productInfo = productInfo;
			req.buyInfo = buyInfo;
		} else if (request instanceof UndoPubBuyInfoReq) { // 取消发布
			UndoPubBuyInfoReq req = (UndoPubBuyInfoReq) request;
			req.buyInfoId = "000003114102014";
		}
	}

	/**
	 * 创建发布信息
	 * @param isMoreArea 是否多地域发布
	 * @return
	 */
	private BuyInfoModel getPubBuyInfo(boolean isMoreArea) {
		BuyInfoModel buyInfo = new BuyInfoModel();
		buyInfo.companyId = "CompanyInfoId000000811102014END";
		buyInfo.buyType = BuyType.BUYER;

		ProductInfoModel productInfo = new ProductInfoModel();
		productInfo.color = "灰色";
		productInfo.area = "深圳";

		buyInfo.unitPrice = 66.6f;
		buyInfo.tradeAmount = 100;
		buyInfo.isMoreArea = false;

		AddrInfoModel addrInfo = new AddrInfoModel();
		addrInfo.addrId = "10";
		buyInfo.addrInfo = addrInfo;
		buyInfo.deliveryAddrType = DeliveryAddrType.ME_DECIDE;

		buyInfo.buyRemarks = "购买信息备注";

		buyInfo.productInfo = productInfo;

		if (isMoreArea) {
			buyInfo.isMoreArea = true;
			buyInfo.buyType = BuyType.SELLER;

			List<AreaPriceInfoModel> areaList = new ArrayList<AreaPriceInfoModel>();
			AreaPriceInfoModel area1 = new AreaPriceInfoModel();
			area1.areaInfo = new AreaInfoModel();
			area1.areaInfo.name = "晋江港";
			area1.areaInfo.code = "RS001";
			area1.unitPrice = 88.8f;

			AreaPriceInfoModel area2 = new AreaPriceInfoModel();
			area2.areaInfo.name = "江阴港";
			area2.areaInfo.code = "RS002";
			area2.unitPrice = 99.9f;

			areaList.add(area1);
			areaList.add(area2);

			buyInfo.areaInfoList = areaList;
		}
		return buyInfo;
	}

}
