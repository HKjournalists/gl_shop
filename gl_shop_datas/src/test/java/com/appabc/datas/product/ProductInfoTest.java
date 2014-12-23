/**
 *
 */
package com.appabc.datas.product;

import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.product.IProductInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

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

	public void testDelById(){
		this.productInfoService.delete("111222");
	}


	/**
	 * 砂最大值
	 * @param key
	 * @return
	 */
	public float getMaxValByName(String key){
		switch (key) {
		case "粗砂":
			return 3.7f;
		case "中砂":
			return 3.0f;
		case "细砂":
			return 2.2f;
		case "特细砂":
			return 1.5f;

		default:
			return 0f;
		}
	}

	/**
	 * 砂最小值
	 * @param key
	 * @return
	 */
	public float getMinValByName(String key){
		switch (key) {
		case "粗砂":
			return 3.1f;
		case "中砂":
			return 2.3f;
		case "细砂":
			return 1.6f;
		case "特细砂":
			return 0.7f;

		default:
			return 0f;
		}
	}

	public void testAddProduct(String name,String pcode, String type){

		TProductProperty psize = new TProductProperty(); // 产品规格参数
		psize.setContent((getMinValByName(name)+getMaxValByName(name))/2.0f+"");
		psize.setName(name);
		psize.setStatus(PropertyStatusEnum.PROPERTY_STATUS_1);
		psize.setTypes("float");
		psize.setMinv(getMinValByName(name));
		psize.setMaxv(getMaxValByName(name));

		TProductInfo pi = new TProductInfo();
		pi.setPaddress("小梅沙");
		pi.setPcode(pcode);
		pi.setPcolor("黄色");
		pi.setPname(name);
		pi.setPtype(type); // PUBLIC_CODE表中定义
		pi.setRemark("黄砂——"+name);
		pi.setUnit(ProductInfo.UnitEnum.UNIT_TON);

		List<TProductProperty> ppLsit = new ArrayList<TProductProperty>();

		TProductProperty pp = new TProductProperty();
		pp.setContent("0.1");
		pp.setName("含泥量");
		pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_MUD_CONTENT.getVal());
		pp.setTypes("float");
		pp.setMaxv(0.5f);
		pp.setMinv(0.1f);
		ppLsit.add(pp);

		pp = new TProductProperty();
		pp.setContent("0.2");
		pp.setName("泥块含量");
		pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_CLAY_CONTENT.getVal());
		pp.setTypes("float");
		pp.setMaxv(0.5f);
		pp.setMinv(0.1f);
		ppLsit.add(pp);

		pp = new TProductProperty();
		pp.setContent("0.3");
		pp.setName("表观密度");
		pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_APPARENT_DENSITY.getVal());
		pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		pp.setTypes("float");
		pp.setMaxv(0.5f);
		pp.setMinv(0.1f);
		ppLsit.add(pp);

		pp = new TProductProperty();
		pp.setContent("3.0");
		pp.setName("堆积密度");
		pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_BULK_DENSITY.getVal());
		pp.setTypes("float");
		pp.setMaxv(0.5f);
		pp.setMinv(0.1f);
		ppLsit.add(pp);

		pp = new TProductProperty();
		pp.setContent("0.5");
		pp.setName("坚固性指标");
		pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_CONSISTENCY_INDEX.getVal());
		pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		pp.setTypes("float");
		pp.setMaxv(0.5f);
		pp.setMinv(0.1f);
		ppLsit.add(pp);



		/****黄砂特有******/
		if(pcode.equals("G002")){
			pp = new TProductProperty();
			pp.setContent("0.2");
			pp.setName("含水率");
			pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_MOISTURE_CONTENT.getVal());
			pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
			pp.setTypes("float");
			pp.setMaxv(0.5f);
			pp.setMinv(0.1f);
			ppLsit.add(pp);
		}else{
			/***石头特有********/
			pp = new TProductProperty();
			pp.setContent("0.3");
			pp.setName("压碎值指标");
			pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_CRUSHING_VALUE_INDEX.getVal());
			pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
			pp.setTypes("float");
			pp.setMaxv(0.5f);
			pp.setMaxv(0.1f);
			ppLsit.add(pp);

			pp = new TProductProperty();
			pp.setContent("0.4");
			pp.setName("针片状颗粒含量");
			pp.setCode(ProductInfo.PropertyCodeEnum.PROPERTY_CODE_ELONGATED_PARTICLES.getVal());
			pp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
			pp.setTypes("float");
			pp.setMaxv(0.5f);
			pp.setMinv(0.1f);
			ppLsit.add(pp);
		}

		productInfoService.add(pi, psize, ppLsit);

	}


	public void addHuangSha(){
		String names[] = {"粗砂","中砂","细砂","特细砂"};
		String types[] = {"G002001","G002002","G002003","G002004","G002005","G002006"};
		for(String name : names){
			for(String type : types){
				testAddProduct(name, "G002", type);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		addHuangSha();

	}





}
