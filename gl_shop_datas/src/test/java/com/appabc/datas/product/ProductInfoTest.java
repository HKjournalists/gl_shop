/**
 *
 */
package com.appabc.datas.product;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.product.IProductInfoService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午3:16:34
 */
public class ProductInfoTest extends AbstractDatasTest{
	
	@Autowired
	IProductInfoService productInfoService;
	
//	@Test
//	@Rollback(value=false)
	public void testUpdate(){
		TProductInfo pi = productInfoService.query("12");
		System.out.println(pi.getPname());
		pi.setPname("改名商品1");
		pi.setPcolor("无色");
		this.productInfoService.modify(pi);
	}
	
	public void testQueryByPcode(){
		List<TProductInfo> list = this.productInfoService.queryByPcode("12");
		System.out.println(list.size());
		for(TProductInfo pi : list){
			System.out.println(pi);
		}
		
	}
	
//	@Test
//	@Rollback(value=false)
	public void testDelById(){
		this.productInfoService.delete("111222");
	}
 
//	@Test
//	@Rollback(value=false)
	public void testAdd(){
		
		TProductInfo pi = new TProductInfo();
		pi.setPaddress("大梅沙");
		pi.setPcode("G002");
		pi.setPcolor("红色");
		pi.setPname("特细砂");
		pi.setPsize("0.2");
		pi.setPtype("G002004"); // PUBLIC_CODE表中定义
		pi.setRemark("黄砂——特细砂");
		pi.setUnit("立方");
		
		List<TProductProperty> ppLsit = new ArrayList<TProductProperty>();
		
		TProductProperty pp = new TProductProperty();
		pp.setContent("1.7");
		pp.setName("含泥量");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMinv(0.1f);
		
		ppLsit.add(pp);
		
		pp = new TProductProperty();
		pp.setContent("0.8");
		pp.setName("泥块含量");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMaxv(0.1f);
		
		ppLsit.add(pp);
		
		pp = new TProductProperty();
		pp.setContent("2.9");
		pp.setName("表观密度");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMaxv(0.1f);
		
		ppLsit.add(pp);
		
		pp = new TProductProperty();
		pp.setContent("3.0");
		pp.setName("堆积密度");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMinv(0.1f);
		
		ppLsit.add(pp);
		
		pp = new TProductProperty();
		pp.setContent("2.1");
		pp.setName("压碎值指标");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMaxv(0.1f);
		
		ppLsit.add(pp);
		
		pp = new TProductProperty();
		pp.setContent("1.3");
		pp.setName("针片状颗粒含量");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMinv(0.1f);
		
		ppLsit.add(pp);
		
		pp = new TProductProperty();
		pp.setContent("8.5");
		pp.setName("坚固性指标");
		pp.setStatus("0");
		pp.setTypes("float");
		pp.setMaxv(5.5f);
		pp.setMinv(0.1f);
		
		ppLsit.add(pp);
		
		productInfoService.add(pi, ppLsit);
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
