/**
 *
 */
package com.appabc.datas.order;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.order.IOrderAddressService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午4:28:46
 */
public class OrderAddressTest extends AbstractDatasTest{

	@Autowired
	private IOrderAddressService orderAddressService;
	
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		add();
	}

	public void add(){
		TOrderAddress t = new TOrderAddress();
		t.setAddress("高新技术园B14C");
		t.setAreacode("AREA0002");
		t.setCid("qiye001");
		t.setCrater("qiye001");
		t.setCreatime(new Date());
		t.setDeep(6.7f);
		t.setFid("OrderFindId000000311092014END");
		t.setLatitude("N42°16′22.38″");
		t.setLongitude("E97°19′35.59″");
		t.setOid(null);
		t.setRealdeep(4.3f);
		t.setType("1");
		
		orderAddressService.add(t);
	}
	
}
