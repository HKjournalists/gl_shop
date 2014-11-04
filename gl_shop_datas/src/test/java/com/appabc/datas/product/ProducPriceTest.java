/**
 *
 */
package com.appabc.datas.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductPrice;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.enums.ProductInfo.UnitEnum;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.product.IProductPriceService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午5:52:34
 */
public class ProducPriceTest extends AbstractDatasTest{
	
	@Autowired
	private IProductPriceService productPriceService;
	
	@Autowired
	private IProductInfoService productInfoService;
	
//	@Test
//	@Rollback(value=false)
	public void testAdd(){
		
		List<TProductInfo> piList = productInfoService.queryForList(new TProductInfo());
		
		// 批量造数据
		for(TProductInfo pi : piList){
			
			Date d = new Date();
//			d.setDate(d.getDate()-1); // 前一天
			
			
			TProductPrice entity = new TProductPrice();
			entity.setArea("RS001");
			entity.setDatepoint(d);
			entity.setPid(pi.getId());
			float price = (float) (Math.random()*100);
			entity.setPrice(price+1);
			entity.setUnit(UnitEnum.UNIT_TON.getVal());
			entity.setUpdater("王五");
			entity.setUpdatetime(d);
			
			this.productPriceService.add(entity);
		}
		
		
		
	}
	
//	@Test
//	@Rollback(value=false)
	public void testUpdate(){
		TProductPrice pp = this.productPriceService.query(7);
		
		System.out.println(pp);
		pp.setPrice(56.4f);
		
		this.productPriceService.modify(pp);
	}
	
//	@Test
//	@Rollback(value=false)
	public void testDel(){
		this.productPriceService.delete(1);
	}
	
//	@Test
	public void testQuery(){
		
		TProductPrice entity = new TProductPrice();
		entity.setArea("RS001");
		
		
		List<TProductPrice> ppList = this.productPriceService.queryForList(entity);
		for (TProductPrice pp : ppList) {
			System.out.println(pp);
		}
	}
	
	/**
	 * 商品当天价格查询测试
	 */
//	@Test
	public void testTodayPrice(){
		List<Map<String, Object>> list = this.productPriceService.queryTodayPrice("RS001", "G002");
		System.out.println(list);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		// TODO Auto-generated method stub
//		testTodayPrice();
	}
	
}
