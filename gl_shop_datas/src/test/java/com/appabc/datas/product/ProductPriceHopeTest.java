/**
 *
 */
package com.appabc.datas.product;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductPriceHope;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.product.IProductPriceHopeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月28日 上午11:56:49
 */
public class ProductPriceHopeTest extends AbstractDatasTest {

	@Autowired
	private IProductInfoService productInfoService;

	@Autowired
	private IProductPriceHopeService productPriceHopeService;


	public void testAdd(){

		List<TProductInfo> piList = productInfoService.queryForList(new TProductInfo());

		// 批量造数据
		for(TProductInfo pi : piList){

			TProductPriceHope e = new TProductPriceHope();

			float price = (float) (Math.random()*100);
			int hopeWeek = 1; // 未来几周

			e.setArea("RS001");
			e.setBaseprice(price+10);
			e.setStarttime(DateUtil.getFirstDayOfNWeek(null, hopeWeek));
			e.setEndtime(DateUtil.getLastDayOfNWeek(null, hopeWeek));
			e.setPid(pi.getId());
			e.setPricemax(price+20);
			e.setPricemin(price+1);
			e.setTimetype(hopeWeek+"");
			e.setUnit("U001");
			e.setUpdater("张三哥");
			e.setUpdatetime(new Date());

			this.productPriceHopeService.add(e);
		}

	}

	public void testUpdate(){
		TProductPriceHope t = this.productPriceHopeService.query(3);

		System.out.println(t);
		t.setBaseprice(53f);

		this.productPriceHopeService.modify(t);
	}

	public void testDel(){
		this.productPriceHopeService.delete(1);
	}

	public void testQuery(){

		TProductPriceHope entity = new TProductPriceHope();
		entity.setArea("RS001");


		List<TProductPriceHope> ppList = this.productPriceHopeService.queryForList(entity);
		for (TProductPriceHope pp : ppList) {
			System.out.println(pp);
		}
	}

	/**
	 * 商品当天价格+未来1到2周价格查询测试
	 */
	public void testHopePrice(){
		List<Map<String, Object>> list = this.productPriceHopeService.queryHopePrice("RS001", "G002");
		System.out.println(list);
	}
	
	public void testQueryListByDay(){
		TProductPriceHope entity = new TProductPriceHope();
		entity.setArea("RS001");
		entity.setPid("201411200000029");
		
		Date day = DateUtil.strToDate("2014-11-17 10:18:14", DateUtil.FORMAT_YYYY_MM_DD_HH_MM);
		
		List<TProductPriceHope> pphList = productPriceHopeService.queryListByDay(entity, day);
		System.out.println(pphList);
		System.out.println(pphList.size());
	}
	

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		testHopePrice();
//		testAdd();
//		testQueryListByDay();

	}



}
