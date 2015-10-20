package com.appabc.datas.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.order.IOrderAddressService;
import com.appabc.datas.service.order.IOrderFindMatchService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.IOrderProductInfoService;
import com.appabc.datas.service.order.IOrderProductPropertyService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年5月4日 上午10:27:20
 */

public class OrderFindMatchTest extends AbstractDatasTest {

	@Autowired
	private IOrderProductPropertyService iOrderProductPropertyService;
	
	@Autowired
	private IOrderProductInfoService iOrderProductInfoService;
	
	@Autowired
	private IOrderFindMatchService iOrderFindMatchService;
	
	@Autowired
	private IOrderAddressService iOrderAddressService;
	
	@Autowired
	private IOrderFindService orderFindService;
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		//testQueryFindMatchInfo();
		//testVitiateOrderFindMatchInfoByFid();
		//testRollbackOrderFindMatchInfoByRid();
		//testFindOrderFindMatchExInfoForPagination();
	}
	
	public void testFindOrderFindMatchExInfoForPagination(){
		QueryContext<TOrderFindMatchEx> qContext = new QueryContext<>();
		qContext.addParameter("status", OrderFindMatchStatusEnum.SUCCESS);
		qContext.addParameter("opType", OrderFindMatchOpTypeEnum.TRADEINQUIRY);
		qContext = iOrderFindMatchService.findOrderFindMatchExInfoForPagination(qContext);
		log.info(qContext);
	}
	
	public void testVitiateOrderFindMatchInfoByRid(){
		String rid = "5";
		try {
			iOrderFindMatchService.vitiateOrderFindMatchInfoByRid(rid);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testRollbackOrderFindMatchInfoByRid(){
		String rid = "5";
		try {
			iOrderFindMatchService.rollbackOrderFindMatchInfoByRid(rid);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testVitiateOrderFindMatchInfoByFid(){
		try {
			iOrderFindMatchService.vitiateOrderFindMatchInfoByFid("201505040000594");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testQueryFindMatchInfo(){
		List<TOrderFindMatch> result = iOrderFindMatchService.findOrderFindMatchInfo("201503300000024", OrderFindMatchStatusEnum.SAVE);
		log.info(result);
	}
	
	public void testSaveOrderFindMatchInfo(){
		String fid = "201505050000609";
		OrderFindAllBean ofabean = new OrderFindAllBean();
		
		TOrderFind orderFind = orderFindService.query(fid);
		ofabean.setOfBean(orderFind);
		
		TOrderProductInfo queryEntity = new TOrderProductInfo();
		queryEntity.setFid(fid);
		TOrderProductInfo productInfo = iOrderProductInfoService.query(queryEntity);
		ofabean.setOpiBean(productInfo);
		
		TOrderProductProperty propis = new TOrderProductProperty();
		propis.setPpid(Integer.valueOf(productInfo.getId()));
		List<TOrderProductProperty> result = iOrderProductPropertyService.queryForList(propis);
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		for(TOrderProductProperty t : result){
			ProductPropertyContentBean e = new ProductPropertyContentBean();
			e.setPpid(t.getPproid());
			e.setContent(t.getContent());
			ppcList.add(e);
		}
		ofabean.setPpcList(ppcList);
		
		TOrderAddress addressInfo = iOrderAddressService.queryByFid(fid);
		if(addressInfo == null){
			addressInfo = new TOrderAddress();
			addressInfo.setCid(orderFind.getCid());
			addressInfo.setAreacode("130524000000");
			addressInfo.setAddress("DD镇CC村");
			addressInfo.setDeep(6.51f);
			addressInfo.setShippington(2000.63f);
		}
		ofabean.setOaBean(addressInfo);
		
		TOrderFindMatch orderInfo;
		try {
			String[] strs = {"title","fid"};
			orderInfo = iOrderFindMatchService.saveOrderFindMatchInfo(ofabean, "201411270000014", OrderFindMatchOpTypeEnum.MATCHCONTRACT, "admin", strs);
			//orderInfo = iOrderFindMatchService.saveOrderFindMatchInfo(ofabean, "201503300000024", OrderFindMatchOpTypeEnum.MATCHCONTRACT, OrderFindMatchStatusEnum.CREATION, "admin", strs);
			log.debug(orderInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.debug(e);
		}
	}
	
	public void testOrderFindMatch(){
		testOrderFindMatchInsert();
		testOrderFindMatchQueryAndUpdate();
		testOrderFindMatchDelete();
	}
	
	private void testOrderFindMatchInsert(){
		TOrderFindMatch tofm = new TOrderFindMatch();
		tofm.setOwner("201503030000027");
		tofm.setTarget("201503030000026");
		tofm.setGuaranty(5000d);
		tofm.setOpType(OrderFindMatchOpTypeEnum.TRADEINQUIRY);
		tofm.setStatus(OrderFindMatchStatusEnum.SAVE);
		tofm.setTitle("购买黄砂·山砂·粗砂(3.1-3.7)mm 123.3吨 41.21元/吨");
		tofm.setOcfid("201501260000211");
		tofm.setOpfid("201501270000221");
		tofm.setTfid("201501270000224");
		tofm.setRemark("remark");
		iOrderFindMatchService.add(tofm);
	}
	
	private void testOrderFindMatchQueryAndUpdate(){
		TOrderFindMatch tofm = new TOrderFindMatch();
		List<TOrderFindMatch> list = iOrderFindMatchService.queryForList(tofm);
		for(TOrderFindMatch bean : list){
			bean.setStatus(OrderFindMatchStatusEnum.SUCCESS);
			bean.setOpType(OrderFindMatchOpTypeEnum.MATCHCONTRACT);
			iOrderFindMatchService.modify(bean);
		}
	}
	
	private void testOrderFindMatchDelete(){
		TOrderFindMatch tofm = new TOrderFindMatch();
		List<TOrderFindMatch> list = iOrderFindMatchService.queryForList(tofm);
		for(TOrderFindMatch bean : list){
			bean.setStatus(OrderFindMatchStatusEnum.SUCCESS);
			bean.setOpType(OrderFindMatchOpTypeEnum.MATCHCONTRACT);
			iOrderFindMatchService.delete(bean);
		}
	}

}
