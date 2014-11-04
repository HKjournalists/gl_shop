/**
 *
 */
package com.appabc.datas.order;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TOrderFind;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.order.IOrderFindService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午10:10:49
 */
public class OrderFindTest extends AbstractDatasTest{

	@Autowired
	private IOrderFindService orderFindService;
	
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		add();
	}

	public void add(){
		TOrderFind t = new TOrderFind();
		t.setAddresstype(0);
		t.setArea("101");
		t.setCid("qiye001");
		t.setCreater("admin");
		t.setCreatime(Calendar.getInstance().getTime());
		t.setEndtime(Calendar.getInstance().getTime());
		t.setLimitime(new Date());
		t.setMorearea("1");
		t.setNum(44f);
		t.setParentid(null);
		t.setPrice(36.8f);
		t.setRemark("测试供应002");
		t.setStarttime(new Date());
		t.setStatus(0);
		t.setTitle("卖黄砂");
		t.setTotalnum(44f);
		t.setType(0);
		
		this.orderFindService.add(t);
		
	}
	
	
	
}
