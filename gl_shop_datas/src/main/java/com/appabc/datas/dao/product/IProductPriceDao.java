/**
 *
 */
package com.appabc.datas.dao.product;

import java.util.List;
import java.util.Map;

import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 商品当日价格DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午4:48:51
 */
public interface IProductPriceDao extends IBaseDao<TProductPrice> {
	
	/**
	 * 查询商品当天价格+昨天价格
	 * @param area 地区（靖江）
	 * @param pcode 所属商品编码
	 * @return
	 */
	public List<Map<String, Object>> queryTodayPrice(String area, String pcode);

}
