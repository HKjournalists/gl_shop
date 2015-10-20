/**
 *
 */
package com.appabc.datas.service.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.base.service.IBaseService;

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
	 * @param area 地区（靖江CODE）
	 * @param pcode 所属商品编码(黄砂、石子一级大类CODE)
	 * @return
	 */
	public List<Map<String, Object>> queryTodayPrice(String area, String pcode);

	/**
	 * 查询某一天的数据
	 * @param entity
	 * @param date
	 * @return
	 */
	List<TProductPrice> queryListByDay(TProductPrice entity, Date date);

	
}
