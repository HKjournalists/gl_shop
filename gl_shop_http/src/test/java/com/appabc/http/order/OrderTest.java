/**
 *
 */
package com.appabc.http.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.datas.service.product.IProductPropertyService;
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
	
	@Autowired
	private IProductPropertyService productPropertyService;

	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		loginSimulation("18666668888", null, "201501260000019");
//		publish();
//		getOderList();
//		getMyList(); 
//		getOrderInfo();
//		dealApply();
//		orderCancel();
//		updateOrderAllInfo();
//		del();
		
	}
	
	public void del(){
		request.setRequestURI("/order/del");
		request.setMethod("GET");
		
		request.addParameter("fid", "201501120000140");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void publish(){
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		
		String pid = "201411200000009"; // 商品ID
		
		TProductProperty ppEntity = new TProductProperty();
		ppEntity.setPid(pid);
		ppEntity.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		
		List<TProductProperty> ppList = this.productPropertyService.queryForList(ppEntity);
		for(TProductProperty pp : ppList){
			ProductPropertyContentBean opp = new ProductPropertyContentBean();
			
			opp.setPpid(pp.getId());
			opp.setContent(pp.getContent());
			ppcList.add(opp);
		}
		
		
		Gson gson = new Gson();
		
		request.setRequestURI("/order/publish");
        request.setMethod("POST");
        
        //主表信息
        request.addParameter("typeValue", OrderTypeEnum.ORDER_TYPE_BUY.getVal()+"");
        request.addParameter("addresstypeValue", OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_OWN.getVal()+"");
        request.addParameter("price", "37"); // 多地或发布时可以为空
        request.addParameter("totalnum", "80");
        request.addParameter("starttime", "2015-1-8 12:12:12");
        request.addParameter("area", "120116000000");
        request.addParameter("endtime", "2015-2-9 12:12:12");
//        String moreAreaInfos = "[{\"price\":\"12\",\"area\":\"A_003\"},{\"price\":\"14\",\"area\":\"A_006\"}]";
//        request.addParameter("moreAreaInfos", moreAreaInfos);// 多地域 JSON格式
        request.addParameter("remark", "new order find");
        
        
        // 卸货地址表
        request.addParameter("addressid", "");
        
        // 货物信息
        request.addParameter("pid", pid);
        request.addParameter("pcolor", "黑色");
        request.addParameter("paddress", "深圳南山");
        request.addParameter("unit", ProductInfo.UnitEnum.UNIT_TON.getVal());
        request.addParameter("productPropertys", gson.toJson(ppcList));
        
        
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
        request.addParameter("pageSize", "3");
//        request.addParameter("areaCodeProvince", "140000000000,360000000000");
//        request.addParameter("areaCodeArea", "120116000000,230621000000,360430000000,340523000000,360103000000");
//        request.addParameter("type", OrderFindInfo.OrderTypeEnum.ORDER_TYPE_SELL.getVal()+"");
//        request.addParameter("pids", "201411200000008");
//        request.addParameter("startTime", "2015-01-08");
//        request.addParameter("endTime", "2015-01-10");
        request.addParameter("orderCredit", "0");
        request.addParameter("orderPrice", "1");
        request.addParameter("orderEffTime", "1"); // 有效期
        
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
		request.addParameter("pageSize", "2");
		
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
		
		request.addParameter("fid", "201501260000204");
		
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
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		
		String pid = "201411200000009"; // 商品ID
		
		TProductProperty ppEntity = new TProductProperty();
		ppEntity.setPid(pid);
		ppEntity.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
		
		List<TProductProperty> ppList = this.productPropertyService.queryForList(ppEntity);
		for(TProductProperty pp : ppList){
			ProductPropertyContentBean opp = new ProductPropertyContentBean();
			
			opp.setPpid(pp.getId());
			opp.setContent(pp.getContent());
			ppcList.add(opp);
		}
		
		Gson gson = new Gson();
		
		request.setRequestURI("/order/mdy");
        request.setMethod("POST");
        
        //主表信息
        request.setParameter("id", "201501260000204");
        request.addParameter("addresstypeValue", OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_OWN.getVal()+"");
        request.addParameter("price", "37.7"); 
        request.addParameter("totalnum", "88");
        request.addParameter("starttime", "2015-1-10 13:13:13");
        request.addParameter("endtime", "2015-2-10 13:13:13");
        request.addParameter("morearea", OrderMoreAreaEnum.ORDER_MORE_AREA_NO.getVal()+"");
        request.addParameter("area", "AREA_004");
//        String moreAreaInfos = "[{\"price\":\"12.2\",\"area\":\"A_004\"},{\"price\":\"14.2\",\"area\":\"A_005\"}]";
//        request.addParameter("moreAreaInfos", moreAreaInfos);// 多地域 JSON格式
        
        
        // 卸货地址
        request.addParameter("addressid", "");
        
        // 货物信息
        request.addParameter("pid", pid);
        request.addParameter("pcolor", "黑色");
        request.addParameter("paddress", "深圳南山");
        request.addParameter("unit", ProductInfo.UnitEnum.UNIT_TON.getVal());
        request.addParameter("productPropertys", gson.toJson(ppcList));
        
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
