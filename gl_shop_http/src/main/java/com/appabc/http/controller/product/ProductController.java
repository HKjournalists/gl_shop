/**
 *
 */
package com.appabc.http.controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.product.IProductPriceHopeService;
import com.appabc.datas.service.product.IProductPriceService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午9:11:31
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController<TProductInfo> {
	
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IProductPriceService productPriceService;
	@Autowired
	private IProductPriceHopeService productPriceHopeService;
	
	/**
	 * 分类获取商品列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProductList")
	public Object getProductByPcode(HttpServletRequest request,HttpServletResponse response){
		
		String pcode = request.getParameter("pcode");
		if(pcode != null && !pcode.equals("")){
			List<TProductInfo> list = this.productInfoService.queryByPcode(pcode);
			return list;
		}else{
			return this.buildFailResult(ErrorCode.GENERICERRORCODE, "pcode id is null"); 
		}
		
	}
	
	/**
	 * 首页当天商品价格接口	http://localhost:8080/gl_shop_http/product/price/day?pcode=S002&area=AREA_JINGJIANG
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/price/getToday")
	public Object getTodayPrice(HttpServletRequest request,HttpServletResponse response){
		
		String pcode = request.getParameter("pcode");
		String area = request.getParameter("area");
		
		if(pcode != null && !pcode.equals("") && pcode != null && !pcode.equals("")){
			return this.productPriceService.queryTodayPrice(area, pcode);
		}else{
			return this.buildFailResult(ErrorCode.GENERICERRORCODE, "参数值不完整"); 
		}
		
	}
	
	/**
	 * 商品预测价格接口 	http://localhost:8080/gl_shop_http/product/price/hope?pcode=S002&area=AREA_JINGJIANG
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/price/getHope")
	public Object getHopePrice(HttpServletRequest request,HttpServletResponse response){
		
		String pcode = request.getParameter("pcode");
		String area = request.getParameter("area");
		
		if(pcode != null && !pcode.equals("") && pcode != null && !pcode.equals("")){
			return this.productPriceHopeService.queryHopePrice(area, pcode);
		}else{
			return this.buildFailResult(ErrorCode.GENERICERRORCODE, "参数值不完整"); 
		}
		
	}

}
