/**
 *
 */
package com.appabc.datas.order;

import com.appabc.bean.enums.OrderFindInfo.OrderItemEnum;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.order.IOrderFindItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午3:24:35
 */
public class OrderFindItemTest  extends AbstractDatasTest{

	@Autowired
	private IOrderFindItemService orderFindItemService;

	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		add();

	}

	public void add(){

		TOrderFindItem t = new TOrderFindItem();
		t.setCreatetime(new Date());
		t.setDealer("000111");
		t.setDealtime(new Date());
		t.setFid("OrderFindId000000311092014END");
		t.setRemark("我想买这个");
		t.setResult("卖家嫌弃买家，不做生意");
		t.setStatus(OrderItemEnum.enumOf(1));
		t.setUpdater("admin");

		this.orderFindItemService.add(t);

	}

}
