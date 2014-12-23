/**
 *
 */
package com.appabc.datas.service.product;

import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.base.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * @Description : 商品当日价格SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午5:05:01
 */
public interface IProductPriceService extends IBaseService<TProductPrice> {

	/**
	 * 查询商品当天价格+昨天价格
	 * @param area 地区（靖江）
	 * @param pcode 所属商品编码
	 * @return
	 */
	public List<Map<String, Object>> queryTodayPrice(String area, String pcode);

	
}
