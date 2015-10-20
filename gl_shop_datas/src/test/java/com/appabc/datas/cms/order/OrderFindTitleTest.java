/**
 *
 */
package com.appabc.datas.cms.order;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.order.IOrderFindService;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月19日 下午4:18:29
 */
public class OrderFindTitleTest extends AbstractDatasTest{

	@Autowired
	private IOrderFindService orderFindService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Rollback(value=false)
	@Test
	public void mainTest() {
//		updateTitle();
	}

	void updateTitle(){
		List<TOrderFind> list = orderFindService.queryForList(new TOrderFind());
		for(TOrderFind of : list){
			OrderAllInfor oai = orderFindService.queryInfoById(of.getId(), null);
			System.out.println(oai);
			of.setTitle(orderFindService.spellOrderFindTitle(oai));

			if(of.getType() == null){
				of.setType(OrderTypeEnum.ORDER_TYPE_BUY);
			}
			this.orderFindService.modify(of);
		}

	}

}
