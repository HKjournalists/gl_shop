/**
 *
 */
package com.appabc.datas.cms.order;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TProductPrice;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.product.IProductPriceService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月4日 下午4:11:30
 */
public class OrderPriceTest extends AbstractDatasTest{
	
	@Autowired
	private IProductPriceService productPriceService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
		
//		testQueryListByDay();
	}
	
	@SuppressWarnings("deprecation")
	public void testQueryListByDay(){
		Date date = DateUtil.strToDate("2014-11-24 10:17:01", DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
		
		TProductPrice entity = new TProductPrice();
		entity.setArea("RS001");
		List<TProductPrice> dayPriceList = productPriceService.queryListByDay(entity, date);
		System.out.println(dayPriceList.size());
		for (TProductPrice pp : dayPriceList) {
			System.out.println(pp.getId()+"\t"+pp.getPrice()+"\t"+pp.getDatepoint().toLocaleString());
		}
	}

}
