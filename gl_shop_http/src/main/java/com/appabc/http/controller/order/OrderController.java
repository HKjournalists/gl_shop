/**
 *
 */
package com.appabc.http.controller.order;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.enums.OrderFindInfo;
import com.appabc.datas.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.datas.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.datas.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindService;

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
			TOrderFind ofBean, TOrderProductInfo opiBean) {
		
		String addressid =  request.getParameter("addressid"); // 指定卸货地址ID
		
		if(ofBean.getCid() == null || ofBean.getCid().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}
		if(ofBean.getType() == null || !(ofBean.getType()==OrderFindInfo.OrderTypeEnum.ORDER_TYPE_BUY.getVal() || ofBean.getType()==OrderFindInfo.OrderTypeEnum.ORDER_TYPE_SELL.getVal()) ){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单发布类型错误");
		}
		if(opiBean.getPid() == null || opiBean.getPid().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品ID不能为空");
		}
		if(opiBean.getPname() == null || opiBean.getPname().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品名称不能为空");
		}
		if(opiBean.getPcolor() == null || opiBean.getPcolor().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品颜色不能为空");
		}
		if(opiBean.getPaddress() == null || opiBean.getPaddress().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品产地不能为空");
		}
//		if(opiBean.getProductPropertys() == null || opiBean.getProductPropertys().trim().equals("")){
//			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品属性不能为空");
//		}
		if(ofBean.getMorearea() != null && ofBean.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())){
			if(ofBean.getMoreAreaInfos()==null || ofBean.getMoreAreaInfos().trim().equals("")){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "多地域发布信息不全");
			}
		}else if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "价格未输入");
		}
		
		String title = opiBean.getPtype()+opiBean.getPname();
		if(ofBean.getType()==OrderTypeEnum.ORDER_TYPE_BUY.getVal()){
			title = "购买" + title;
		}else{
			title = "出售" + title;
		}
		ofBean.setTitle(title);
		ofBean.setCreater(getCurrentUserId(request));
		ofBean.setCreatime(Calendar.getInstance().getTime());
		this.orderFindService.orderPublish(ofBean, opiBean, addressid);
		
		return buildSuccessResult("发布成功", "");
	}
	
	/**
	 * 找买找卖查询接口(按信用排序示实现)
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/open/getOrderList",method=RequestMethod.GET)
	public Object getOrderList(HttpServletRequest request,HttpServletResponse response) {
		
		
		QueryContext<TOrderFind> qContext = initializeQueryContext(request);
		qContext.addParameter("overallstatus", OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE.getVal());// 有效询单
		qContext.addParameter("queryMethod", " getOrderList ");// 判断条件
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
		qContext = orderFindService.queryListForPagination(qContext);
		return qContext.getQueryResult();
	}
	
	/**
	 * 获取询单详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo",method=RequestMethod.GET)
	public Object getOrderInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String fid = request.getParameter("fid");
		if(fid == null || fid.trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		OrderAllInfor oai = orderFindService.queryInfoById(fid);
		
		String requestCid=null;
		try {
			requestCid = this.getCurrentUserCid(request);
		} catch (Exception e) {
		}
		if(!oai.getCid().equals(requestCid)){ // 其它用户查看
			if(!contractInfoService.isOldCustomer(requestCid, oai.getCid())){ // 2个企业未发生过交易，进行企业加密处理
				
			}
		}
		
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
		this.orderFindItemService.add(ofi);
		
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
		
		String userid = getCurrentUserId(request);
		
		String message = this.orderFindService.cancel(fid, userid);
		
		return buildSuccessResult(message, "");
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
		if(ofBean.getId()==null || ofBean.getId().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		if(opiBean.getPid() == null || opiBean.getPid().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品ID不能为空");
		}
		if(opiBean.getPname() == null || opiBean.getPname().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品名称不能为空");
		}
		if(opiBean.getPcolor() == null || opiBean.getPcolor().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品颜色不能为空");
		}
		if(opiBean.getPaddress() == null || opiBean.getPaddress().trim().equals("")){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品产地不能为空");
		}
//		if(opiBean.getProductPropertys() == null || opiBean.getProductPropertys().trim().equals("")){
//			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品属性不能为空");
//		}
		if(ofBean.getMorearea() != null && ofBean.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())){
			if(ofBean.getMoreAreaInfos()==null || ofBean.getMoreAreaInfos().trim().equals("")){
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "多地域发布信息不全");
			}
		}else if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "价格未输入");
		}
		
		ofBean.setUpdater(this.getCurrentUserId(request));
		this.orderFindService.updateOrderAllInfo(ofBean, oaBean, opiBean);
		
		return buildSuccessResult("更新成功", "");
		
	}
	
	
	

}
