/**
 *
 */
package com.appabc.http.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.http.AbstractHttpControllerTest;
import com.google.gson.Gson;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午8:25:37
 */
public class OrderTest extends AbstractHttpControllerTest {

	@Override
	@Test
	public void mainTest() {
//		loginSimulation(null, null, null);
//		publish();
//		getOderList();
//		getMyList(); 
//		getOrderInfo();
//		dealApply();
//		orderCancel();
//		updateOrderAllInfo();
		
	}
	
	public void publish(){
		List<TOrderProductProperty> list = new ArrayList<TOrderProductProperty>();

		TOrderProductProperty opp = new TOrderProductProperty();
		opp.setName("含泥量");
		opp.setTypes("");
		opp.setContent("2.3");
		opp.setPproid("061120140000001");
		opp.setMaxv(2.1f);
		opp.setMinv(0.1f);
		opp.setOrderno(1);
		opp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		opp.setTypes("float");
		list.add(opp);
		
		opp = new TOrderProductProperty();
		opp.setName("含水量");
		opp.setTypes("");
		opp.setContent("2.5");
		opp.setPproid("061120140000001");
		opp.setMaxv(2.2f);
		opp.setMinv(1.1f);
		opp.setOrderno(2);
		opp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		opp.setTypes("float");
		list.add(opp);
		
		Gson gson = new Gson();
		
		TOrderProductProperty psize = new TOrderProductProperty(); // 产品规格
		psize.setContent("1.2");
		psize.setMaxv(1.3f);
		psize.setMinv(0.3f);
		psize.setName("特细砂-G002004");
		psize.setPproid("061120140000001");
		psize.setStatus(PropertyStatusEnum.PROPERTY_STATUS_1);
		psize.setTypes("float");
		
		request.setRequestURI("/order/publish");
        request.setMethod("POST");
        
        //主表信息
        request.addParameter("cid", "qiye001");
        request.addParameter("type", OrderTypeEnum.ORDER_TYPE_BUY.getVal()+"");
        request.addParameter("title", "和田玉");
        request.addParameter("addresstype", OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_BUY.getVal()+"");
        request.addParameter("price", "37"); // 多地或发布时可以为空
        request.addParameter("totalnum", "80");
        request.addParameter("starttime", "2014-9-9 12:12:12");
        request.addParameter("morearea", OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal()+"");
        request.addParameter("area", "AREA_003");
        request.addParameter("limitime", "2014-11-9 12:12:12");
        String moreAreaInfos = "[{\"price\":\"12\",\"area\":\"A_003\"},{\"price\":\"14\",\"area\":\"A_006\"}]";
        request.addParameter("moreAreaInfos", moreAreaInfos);// 多地域 JSON格式
        request.addParameter("remark", "这是一个询单");
        
        
        // 卸货地址表
        request.addParameter("addressid", "");
        
        // 货物信息
        request.addParameter("pid", "1641feec-a4b8-45c5-8c44-b7face7f6984");
        request.addParameter("pname", "碎石1-2");
        request.addParameter("ptype", "");
        request.addParameter("psize", gson.toJson(psize));
        request.addParameter("pcolor", "1001");
        request.addParameter("paddress", "深圳");
        request.addParameter("unit", ProductInfo.UnitEnum.UNIT_TON.getVal());
        request.addParameter("productPropertys", gson.toJson(list));
        
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getOderList(){
		request.setRequestURI("/order/open/getOrderList");
        request.setMethod("GET");
        
        request.addParameter("pageIndex", "0");
        request.addParameter("pageSize", "2");
//        request.addParameter("queryDate", "2014-8-12");
//        request.addParameter("area", "RS001");
//        request.addParameter("pid", "201411200000007");
//        request.addParameter("ptype", "G002004");
//        request.addParameter("orderCredit", "0");
        request.addParameter("orderPrice", "0");
//        request.addParameter("year", "2014");
//        request.addParameter("type", "2");
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getMyList(){
		request.setRequestURI("/order/getMyList");
		request.setMethod("GET");
		
		request.addParameter("pageIndex", "0");
		request.addParameter("pageSize", "1");
		request.addParameter("cid", "CompanyInfoId000000811102014END");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOrderInfo(){
		request.setRequestURI("/order/open/getInfo");
		request.setMethod("GET");
		
		request.addParameter("fid", "201412110000095");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dealApply(){
		request.setRequestURI("/order/item/dealApply");
		request.setMethod("POST");
		
		request.addParameter("fid", "061120140000054");
		request.addParameter("updater", "qiye001");
		 
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void orderCancel(){
		request.setRequestURI("/order/cancel");
		request.setMethod("POST");
		
		request.addParameter("fid", "OrderFindId000000712092014END");
		request.addParameter("updater", "qiye001");
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 更新询单信息
	 */
	public void updateOrderAllInfo(){
		List<TOrderProductProperty> list = new ArrayList<TOrderProductProperty>();

		TOrderProductProperty opp = new TOrderProductProperty();
		opp.setName("含泥量");
		opp.setTypes("");
		opp.setContent("2.8");
		opp.setPproid("28da8bfd-5d12-4bbd-b6c4-e40458a2a6dc");
		opp.setMaxv(2.2f);
		opp.setMinv(0.2f);
		opp.setOrderno(1);
		list.add(opp);
		
		opp = new TOrderProductProperty();
		opp.setName("压碎值指标");
		opp.setTypes("");
		opp.setContent("2.1");
		opp.setPproid("28da8bfd-5d12-4bbd-b6c4-e40458a2a6dc");
		opp.setMaxv(2.3f);
		opp.setMinv(1.2f);
		opp.setOrderno(2);
		list.add(opp);
		
		Gson gson = new Gson();
		
		request.setRequestURI("/order/mdy");
        request.setMethod("POST");
        
        //主表信息
        request.setParameter("id", "OrderFindId000002516092014END");
        request.addParameter("title", "和田玉22");
        request.addParameter("addresstype", OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_SELL.getVal()+"");
        request.addParameter("price", "37.7"); // 多地或发布时可以为空
        request.addParameter("totalnum", "88");
        request.addParameter("starttime", "2014-10-10 13:13:13");
        request.addParameter("morearea", OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal()+"");
        request.addParameter("area", "AREA_004");
        request.addParameter("limitime", "2014-11-11 13:13:13");
        String moreAreaInfos = "[{\"price\":\"12.2\",\"area\":\"A_004\"},{\"price\":\"14.2\",\"area\":\"A_005\"}]";
        request.addParameter("moreAreaInfos", moreAreaInfos);// 多地域 JSON格式
        
        
        // 卸货地址表
        request.addParameter("realdeep", "18");
        request.addParameter("areacode", "AR_0011");
        request.addParameter("longitude", "11-2");
        request.addParameter("latitude", "33-9");
        request.addParameter("deep", "8");
        request.addParameter("address", "深圳南山B1,4C");
        
        // 货物信息
        request.addParameter("pid", "28da8bfd-5d12-4bbd-b6c4-e40458a2a6dc");
        request.addParameter("pname", "瓜子片");
        request.addParameter("ptype", "");
        request.addParameter("psize", "5-16");
        request.addParameter("pcolor", "CL1002");
        request.addParameter("paddress", "深圳，南山");
        request.addParameter("unit", "UNIT002");
        request.addParameter("propertys", gson.toJson(list));
        
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
