/**
 *
 */
package com.appabc.datas.product;

import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.product.IProductPropertyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月6日 下午2:46:04
 */
public class ProductPropertyTest extends AbstractDatasTest {

	@Autowired
	private IProductPropertyService productPropertyService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
	}

	public void add() {

		TProductProperty psize = new TProductProperty(); // 产品规格参数
		psize.setContent("1.0");
		psize.setName("特细砂");
		psize.setStatus(PropertyStatusEnum.enumOf("1"));
		psize.setTypes("float");
		psize.setMinv(0.7f);
		psize.setMaxv(1.5f);
		productPropertyService.add(psize);

		psize = new TProductProperty(); // 产品规格参数
		psize.setContent("1.9");
		psize.setName("细砂");
		psize.setStatus(PropertyStatusEnum.enumOf("1"));
		psize.setTypes("float");
		psize.setMinv(1.6f);
		psize.setMaxv(2.2f);
		productPropertyService.add(psize);

		psize = new TProductProperty(); // 产品规格参数
		psize.setContent("2.6");
		psize.setName("中砂");
		psize.setStatus(PropertyStatusEnum.enumOf("1"));
		psize.setTypes("float");
		psize.setMinv(2.3f);
		psize.setMaxv(3.0f);
		productPropertyService.add(psize);

		psize = new TProductProperty(); // 产品规格参数
		psize.setContent("3.4");
		psize.setName("粗砂");
		psize.setStatus(PropertyStatusEnum.enumOf("1"));
		psize.setTypes("float");
		psize.setMinv(3.1f);
		psize.setMaxv(3.7f);
		productPropertyService.add(psize);

		psize = new TProductProperty(); // 产品规格参数
		psize.setContent("2.5");
		psize.setName("石粉");
		psize.setStatus(PropertyStatusEnum.enumOf("1"));
		psize.setTypes("float");
		psize.setMinv(0f);
		psize.setMaxv(5f);
		productPropertyService.add(psize);

	}

}
