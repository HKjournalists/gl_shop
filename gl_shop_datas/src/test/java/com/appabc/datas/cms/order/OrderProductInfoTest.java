/**
 *
 */
package com.appabc.datas.cms.order;

import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.order.IOrderProductInfoService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午10:02:29
 */
public class OrderProductInfoTest extends AbstractDatasTest{

	@Autowired
	private IOrderProductInfoService orderProductInfoService;

	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		addTest();
	}

	public void addTest(){
		TOrderProductInfo t = new TOrderProductInfo();

		t.setFid("StartId000000111092014END");
		t.setPaddress("CD001");
		t.setPcolor("red");
		t.setPid("1641feec-a4b8-45c5-8c44-b7face7f6984");
		t.setPname("碎石1-2");
		t.setPsize("11");
		t.setPtype(null);
		t.setPremark("这个碎石不太碎");
		t.setSid(null);
		t.setUnit(UnitEnum.UNIT_TON);

		this.orderProductInfoService.add(t);

	}


}
