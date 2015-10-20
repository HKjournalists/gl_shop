/**
 *
 */
package com.appabc.datas.cms.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
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
	
	@Autowired
	private TaskService taskService;

	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
//		queryById();
//		add();
//		testQueryMatchingObjectByFidForPagination();
//		testContractMatchingOfOrderFindSave();
//		testRollbackOrderFind();
		//testTask();
	}
	
	public void testTask(){
		Date now = DateUtil.getNowDate();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("finished", false);
		List<Task> tasks = taskService.queryForList(params, TaskType.MatchOrderRequest);
		for(Task t : tasks){
			int h = DateUtil.getDifferHoursWithTwoDate(t.getUpdateTime(), now);
			try{
				if(h>=24){				
					taskService.releaseTask(t.getId(), TaskType.MatchOrderRequest);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void testRollbackOrderFind(){
		this.orderFindService.rollbackOrderFindByContractid("1020150202227");
	}

	public void testContractMatchingOfOrderFindSave(){

		OrderFindAllBean ofaBean = new OrderFindAllBean();
		ofaBean.setOaBean(null);
		TOrderFind ofBean = new TOrderFind();
		ofaBean.setOfBean(ofBean);
		TOrderProductInfo opiBean = new TOrderProductInfo();
		ofaBean.setOpiBean(opiBean);
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		ofaBean.setPpcList(ppcList);

		try {
			this.orderFindService.contractMatchingOfOrderFindSave(ofaBean);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void testQueryMatchingObjectByFidForPagination(){
		QueryContext<MatchingBean> qContext = new QueryContext<MatchingBean>();
		qContext.addParameter("fid", "201412090000091");
		try {
			qContext = orderFindService.queryMatchingObjectByFidForPagination(qContext, null, true, false, true);
			System.out.println(qContext.getQueryResult().getResult().size());
			System.out.println(qContext.getQueryResult().getResult());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void add(){
		TOrderFind t = new TOrderFind();
		t.setAddresstype(OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_OWN);
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
