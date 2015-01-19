package com.glshop.net.logic.buy;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.MyBuyFilterType;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 买卖供求业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:07:53
 */
public interface IBuyLogic extends ILogic {

	/**
	 * 获取今日价格列表
	 */
	public void getTodayPrice(ProductType type, String productCode, String areaCode);

	/**
	 * 获取价格趋势列表
	 */
	public void getPriceForecast(ProductType type, String productCode, String areaCode, DataReqType reqType);

	/**
	 * 获取求购/供应信息列表(包括筛选/排序)
	 */
	public void getBuys(BuyFilterInfoModelV2 filterInfo, BuyType type, int pageIndex, int pageSize, DataReqType reqType);

	/**
	 * 获取求购/供应信息详情
	 * @param buyId
	 */
	public void getBuyInfo(String buyId);

	/**
	 * 提交发布供求信息
	 * @param info
	 */
	public void pubBuyInfo(BuyInfoModel info);

	/**
	 * 获取我的供求信息列表(我的供/我的求)
	 */
	public void getMyBuys(MyBuyFilterType filterType, String companyId, int pageIndex, int pageSize, DataReqType reqType);

	/**
	 * 修改我的供求
	 * @param info
	 */
	public void updateBuyInfo(BuyInfoModel info);

	/**
	 * 重新发布
	 * @param buyId
	 */
	public void repubBuyInfo(String buyId);

	/**
	 * 取消发布
	 * @param buyId
	 */
	public void undoPubBuyInfo(String buyId);

	/**
	 * 点击感兴趣(想交易)
	 * @param buyId
	 */
	public void wantToDeal(String buyId);

	/**
	 * 获取黄砂价格趋势列表
	 * @return
	 */
	public Object[] getSandPriceForecastList();

}
