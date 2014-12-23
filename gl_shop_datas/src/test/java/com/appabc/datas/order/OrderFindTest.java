/**
 *
 */
package com.appabc.datas.order;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.exception.ServiceException;
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
//	@Rollback(value=false)
	public void mainTest() {
//		queryById();
//		add();
//		testQueryMatchingObjectByCidForPagination();
		
	}
	
	public void testQueryMatchingObjectByCidForPagination(){
		QueryContext<MatchingBean> qContext = new QueryContext<MatchingBean>();
		qContext.addParameter("fid", "201412090000091");
		try {
			qContext = orderFindService.queryMatchingObjectByCidForPagination(qContext, "");
			System.out.println(qContext.getQueryResult().getResult().size());
			System.out.println(qContext.getQueryResult().getResult());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void add(){
		TOrderFind t = new TOrderFind();
		t.setAddresstype(OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_BUY);
		t.setArea("101");
		t.setCid("qiye001");
		t.setCreater("admin");
		t.setCreatime(Calendar.getInstance().getTime());
		t.setEndtime(Calendar.getInstance().getTime());
		t.setLimitime(new Date());
		t.setMorearea(OrderMoreAreaEnum.enumOf("1"));
		t.setNum(44f);
		t.setParentid(null);
		t.setPrice(36.8f);
		t.setRemark("测试供应002");
		t.setStarttime(new Date());
		t.setStatus(OrderStatusEnum.enumOf(0));
		t.setTitle("卖黄砂");
		t.setTotalnum(44f);
		t.setType(OrderTypeEnum.enumOf(0));

		this.orderFindService.add(t);

	}


	public void queryById(){
		OrderAllInfor oa  = orderFindService.queryInfoById("201412100000094", null);
		System.out.println(oa);
		System.out.println("=======================");
	}


}
