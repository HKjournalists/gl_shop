/**
 *
 */
package com.appabc.http.controller.order;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.http.utils.HttpApplicationErrorCode;

/**
 * @Description : 供求信息接口Controller
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午5:10:59
 */
@Controller
@RequestMapping(value="/order")
public class OrderController extends BaseController<TOrderFind> {
	
	@Autowired
	private IOrderFindService orderFindService;
	@Autowired
	private IOrderFindItemService orderFindItemService;
	@Autowired
	private IContractInfoService contractInfoService;
	
	/**
	 * 供求信息发布
	 * @param request
	 * @param response
	 * @param ofBean
	 * @param oaBean
	 * @param opiBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/publish",method=RequestMethod.POST)
	public Object addOrderFindInfos(HttpServletRequest request,HttpServletResponse response, 
			TOrderFind ofBean) {
		
	    TOrderProductInfo opiBean = new TOrderProductInfo();
	    opiBean.setPcolor(request.getParameter("pcolor"));
	    opiBean.setPaddress(request.getParameter("paddress")); // 产地
	    opiBean.setPid(request.getParameter("pid"));
	    opiBean.setPremark(request.getParameter("premark"));
	    opiBean.setProductImgIds(request.getParameter("productImgIds"));
	    opiBean.setProductPropertys(request.getParameter("productPropertys"));
		
		String addressid =  request.getParameter("addressid"); // 指定卸货地址ID
		String typeValue = request.getParameter("typeValue"); // 1：买，2：卖
		String addresstypeValue = request.getParameter("addresstypeValue"); // 1：买家，2：卖家
		String unit = request.getParameter("unit"); // 单位
		
		try {
			if(StringUtils.isNotEmpty(unit)){
				opiBean.setUnit(UnitEnum.enumOf(unit));
			}
			if(StringUtils.isNotEmpty(addresstypeValue)){
				ofBean.setAddresstype(OrderAddressTypeEnum.enumOf(Integer.parseInt(addresstypeValue)));
			}
			if(StringUtils.isEmpty(typeValue)){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单发布类型不能为空");
			}else{
				ofBean.setType(OrderTypeEnum.enumOf(Integer.parseInt(typeValue)));
			}
			if(ofBean.getCid() == null || ofBean.getCid().trim().equals("")){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
			}
			if(ofBean.getType() == null || !(ofBean.getType().getVal()==OrderTypeEnum.ORDER_TYPE_BUY.getVal() || ofBean.getType().getVal()==OrderTypeEnum.ORDER_TYPE_SELL.getVal()) ){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单发布类型错误");
			}
			if(opiBean.getPid() == null || opiBean.getPid().trim().equals("")){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品ID不能为空");
			}
			if(opiBean.getPcolor() == null || opiBean.getPcolor().trim().equals("")){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品颜色不能为空");
			}
			if(opiBean.getPaddress() == null || opiBean.getPaddress().trim().equals("")){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品产地不能为空");
			}
			if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "价格未输入");
			}
			
			ofBean.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO);
			ofBean.setCreater(getCurrentUserId(request));
			ofBean.setCreatime(Calendar.getInstance().getTime());
			this.orderFindService.orderPublish(ofBean, opiBean, addressid);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("发布成功", "");
	}
	
	/**
	 * 找买找卖查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/open/getOrderList",method=RequestMethod.GET)
	public Object getOrderList(HttpServletRequest request,HttpServletResponse response) {
		QueryContext<TOrderFind> qContext = initializeQueryContext(request);
		qContext.addParameter("overallstatus", OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE.getVal());// 有效询单
		qContext.addParameter("queryMethod", "getOrderList");// 判断条件
		
		String year =  request.getParameter("year");
		String month =  request.getParameter("month");
		String day =  request.getParameter("day");
		
		
		if(StringUtils.isNotEmpty(year) || StringUtils.isNotEmpty(month) || StringUtils.isNotEmpty(day)){
			String queryDate = "";
			if(StringUtils.isEmpty(year)){
				year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			if(StringUtils.isEmpty(month)){
				month = "01";
			}
			if(StringUtils.isEmpty(day)){
				day = "01";
			}
			
			queryDate = year + "-" + month + "-" + day;
			qContext.addParameter("queryDate", queryDate);
		}
		
		qContext = orderFindService.queryListForPagination(qContext);
		return qContext.getQueryResult();
	}
	
	/**
	 * 我的供求信息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyList",method=RequestMethod.GET)
	public Object getMyOrderList(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid");
		if(cid == null || cid.trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业ID不能为空");
		}
		
		QueryContext<TOrderFind> qContext = initializeQueryContext(request);
		qContext.addParameter("queryMethod", "getMyList");// 判断条件
		qContext = orderFindService.queryMyListForPagination(qContext);
		return qContext.getQueryResult();
	}
	
	/**
	 * 获取询单详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/open/getInfo",method=RequestMethod.GET)
	public Object getOrderInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String fid = request.getParameter("fid");
		if(fid == null || fid.trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		String requestCid=null;
		try {
			requestCid = this.getCurrentUserCid(request);
		} catch (Exception e) {
			requestCid="otherid";
		}
		OrderAllInfor oai = orderFindService.queryInfoById(fid, requestCid);
		
		return oai;
	}
	
	/**
	 * 交易意向申请
	 * @param request
	 * @param response
	 * @param ofi
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/item/dealApply",method=RequestMethod.POST)
	public Object dealApply(HttpServletRequest request,HttpServletResponse response, TOrderFindItem ofi) {
		
		if(ofi.getFid() == null || ofi.getFid().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		ofi.setUpdater(getCurrentUserCid(request));
		try {
			this.orderFindItemService.tradeApplication(ofi);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("申请已发送", "");
	}
	
	/**
	 * 询单取消
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel",method=RequestMethod.POST)
	public Object orderCancel(HttpServletRequest request,HttpServletResponse response) {
		
		String fid = request.getParameter("fid");
		if(fid == null || fid.trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		TOrderInfo queryEntity = new TOrderInfo();
		queryEntity.setFid(fid);
		List<TOrderInfo> oiList = this.contractInfoService.queryForList(queryEntity);
		if(oiList != null && oiList.size() > 0){
			return buildFailResult(HttpApplicationErrorCode.OPERATING_RESTRICTIONS, "操作受限制，已产生过合同的询单不能取消");
		}
		
		String userid = getCurrentUserId(request);
		String message;
		try {
			message = this.orderFindService.cancel(fid, userid);
			return buildSuccessResult(message, "");
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
	}
	
	/**
	 * 询单修改
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mdy",method=RequestMethod.POST)
	public Object orderModify(HttpServletRequest request,HttpServletResponse response,
			TOrderFind ofBean, TOrderAddress oaBean, TOrderProductInfo opiBean) {
		if(ofBean.getId()==null || ofBean.getId().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		TOrderInfo queryEntity = new TOrderInfo();
		queryEntity.setFid(ofBean.getId());
		List<TOrderInfo> oiList = this.contractInfoService.queryForList(queryEntity);
		if(oiList != null && oiList.size() > 0){
			return buildFailResult(HttpApplicationErrorCode.OPERATING_RESTRICTIONS, "操作受限制，已产生过合同的询单不能修改");
		}
		
		if(opiBean.getPid() == null || opiBean.getPid().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品ID不能为空");
		}
		if(opiBean.getPcolor() == null || opiBean.getPcolor().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品颜色不能为空");
		}
		if(opiBean.getPaddress() == null || opiBean.getPaddress().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品产地不能为空");
		}
		
		String moreareaValue = request.getParameter("moreareaValue"); // 1：单地发布，2：多地发布
		if(moreareaValue != null){
			if(moreareaValue.equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())){
				if(ofBean.getMoreAreaInfos()==null || ofBean.getMoreAreaInfos().trim().equals("")){
					return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "多地域发布信息不全");
				}
				ofBean.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_YES);
			}else{
				ofBean.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO);
			}
		}else if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "价格未输入");
		}
		ofBean.setUpdater(this.getCurrentUserId(request));
		String addressid =  request.getParameter("addressid"); // 指定卸货地址ID
		try {
			this.orderFindService.updateOrderAllInfo(ofBean, opiBean, addressid);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("更新成功", "");
		
	}
	
	
	

}
