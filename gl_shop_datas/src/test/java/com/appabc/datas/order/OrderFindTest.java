/**
 *
 */
package com.appabc.datas.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.bo.OrderFindInsteadBean;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.order.IOrderFindService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月1日 上午10:48:25
 */
public class OrderFindTest extends AbstractDatasTest {
	
	@Autowired
	private IOrderFindService orderFindService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
		testQueryParentOrderFindOfInsteadListForPagination();
	}
	
	void testQueryParentOrderFindOfInsteadListForPagination(){
		QueryContext<OrderFindInsteadBean> qContent = this.orderFindService.queryParentOrderFindOfInsteadListForPagination(null);
		
		System.out.println(qContent);
	}
	
	

}
