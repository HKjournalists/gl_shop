/**
 *
 */
package com.appabc.http.controller.order;

import java.util.ArrayList;
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
import com.appabc.bean.bo.OrderFindQueryParamsBean;
import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
	 * @throws ServiceException 
	 */
	@ResponseBody
	@RequestMapping(value = "/publish",method=RequestMethod.POST)
	public Object addOrderFindInfos(HttpServletRequest request,HttpServletResponse response, 
			TOrderFind ofBean) throws ServiceException {
		
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
		if(ofBean.getType() == null){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单发布类型错误");
		}
		if(StringUtils.isEmpty(opiBean.getPid())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品ID不能为空");
		}
		if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "价格未输入");
		}
		
		/*********商品属性解析****************************************/
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		Gson gson = new Gson();
		try {
			if(StringUtils.isNotEmpty(opiBean.getProductPropertys())){
				JsonParser jsonParser = new JsonParser();
				JsonElement jsonElement =  jsonParser.parse(opiBean.getProductPropertys());
				JsonArray jsonArray = jsonElement.getAsJsonArray();

				for(JsonElement je : jsonArray){ // 商品属性保存
					ProductPropertyContentBean pcc = gson.fromJson(je, ProductPropertyContentBean.class);
					if(pcc != null && StringUtils.isNotEmpty(pcc.getId()) ){
						pcc.setPpid(pcc.getId());
					}else{
						return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品属性解析异常,id="+pcc);
					}
					ppcList.add(pcc);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品属性解析异常");
		}
		
		ofBean.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO);
		String cid = this.getCurrentUserCid(request);
		ofBean.setCid(cid);
		ofBean.setCreater(cid);
		ofBean.setCreatime(Calendar.getInstance().getTime());
		
		TOrderAddress oa = new TOrderAddress();
		oa.setId(addressid);
		
		String originalFid = request.getParameter("originalFid"); // 原始询单ID，重发布时填写
		this.orderFindService.orderPublish(ofBean, opiBean, ppcList, oa, originalFid);
		
		return buildSuccessResult("发布成功");
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
		String startTimeStr =  request.getParameter("startTime");// yyyy-MM-dd
		String endTimeStr =  request.getParameter("endTime");// yyyy-MM-dd
		String areaCodeProvinceStr =  request.getParameter("areaCodeProvince");// 地区代码-省,多个用逗号间隔
		String areaCodeAreaStr =  request.getParameter("areaCodeArea"); // 地区代码-区,多个用逗号间隔
		String pidsStr =  request.getParameter("pids"); // 商品ID,多个用逗号间隔
		String typeStr =  request.getParameter("type"); // 求购，出售
		
		OrderFindQueryParamsBean ofqParam = new OrderFindQueryParamsBean();
		
		if(StringUtils.isNotEmpty(startTimeStr) && StringUtils.isNotEmpty(endTimeStr)){
			try {
				ofqParam.setStartTime(DateUtil.strToDate(startTimeStr, DateUtil.FORMAT_YYYY_MM_DD));
				ofqParam.setEndTime(DateUtil.strToDate(endTimeStr, DateUtil.FORMAT_YYYY_MM_DD));
			} catch (Exception e) {
				e.printStackTrace();
				return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "日期格式错误;startTime="+startTimeStr+",endTime="+endTimeStr);
			}
		}
		if(StringUtils.isNotEmpty(areaCodeAreaStr)) ofqParam.setAreaCodeArea(areaCodeAreaStr.split(","));
		if(StringUtils.isNotEmpty(areaCodeProvinceStr)) ofqParam.setAreaCodeProvince(areaCodeProvinceStr.split(","));
		if(StringUtils.isNotEmpty(pidsStr)) ofqParam.setPids(pidsStr.split(","));
		try {
			if(StringUtils.isNotEmpty(typeStr)) ofqParam.setType(OrderTypeEnum.enumOf(Integer.parseInt(typeStr)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "type="+typeStr);
		}
		
		String requestCid = null;
		try {
			requestCid = this.getCurrentUserCid(request);
		} catch (Exception e) {
		}
		
		qContext = orderFindService.queryOrderListForPagination(qContext, ofqParam, requestCid);
		return qContext.getQueryResult();
	}
	
	/**
	 * 我的供求信息列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException 
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyList",method=RequestMethod.GET)
	public Object getMyOrderList(HttpServletRequest request,HttpServletResponse response) throws ServiceException {
		
		String cid = this.getCurrentUserCid(request);
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业ID不能为空");
		}
		
		QueryContext<TOrderFind> qContext = initializeQueryContext(request);
		qContext = orderFindService.queryMyListForPagination(qContext, cid);
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
		if(StringUtils.isEmpty(fid)){
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
		
		if(StringUtils.isEmpty(ofi.getFid())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		ofi.setUpdater(getCurrentUserCid(request));
		try {
			this.orderFindItemService.tradeApplication(ofi);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("申请已发送");
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
		if(StringUtils.isEmpty(fid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		TOrderInfo queryEntity = new TOrderInfo();
		queryEntity.setFid(fid);
		List<TOrderInfo> oiList = this.contractInfoService.queryForList(queryEntity);
		if(oiList != null && oiList.size() > 0){
			return buildFailResult(HttpApplicationErrorCode.OPERATING_RESTRICTIONS, "操作受限制，已产生过合同的询单不能取消");
		}
		
		String userid = getCurrentUserCid(request);
		String message;
		try {
			message = this.orderFindService.cancel(fid, userid);
			return buildSuccessResult(message);
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
		if(StringUtils.isEmpty(ofBean.getId())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		if(StringUtils.isEmpty(opiBean.getPid())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品ID不能为空");
		}
		
		String addresstype = request.getParameter("addresstypeValue");
		ofBean.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO);
		if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "价格未输入");
		}
		
		String addressid =  request.getParameter("addressid"); // 指定卸货地址ID
		
		/*********商品属性解析****************************************/
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		Gson gson = new Gson();
		try {
			if(StringUtils.isNotEmpty(opiBean.getProductPropertys())){
				JsonParser jsonParser = new JsonParser();
				JsonElement jsonElement =  jsonParser.parse(opiBean.getProductPropertys());
				JsonArray jsonArray = jsonElement.getAsJsonArray();

				for(JsonElement je : jsonArray){ // 商品属性保存
					ProductPropertyContentBean pcc = gson.fromJson(je, ProductPropertyContentBean.class);
					if(pcc != null && StringUtils.isNotEmpty(pcc.getId()) ){
						pcc.setPpid(pcc.getId());
					}else{
						return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品属性解析异常,id="+pcc);
					}
					ppcList.add(pcc);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "商品属性解析异常");
		}
		
		try {
			if(StringUtils.isNotEmpty(addresstype)){
				ofBean.setAddresstype(OrderAddressTypeEnum.enumOf(Integer.valueOf(addresstype)));
			}
			ofBean.setUpdater(this.getCurrentUserCid(request));
			this.orderFindService.updateOrderAllInfo(ofBean, opiBean, ppcList, addressid);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("更新成功");
		
	}
	
	/**
	 * 询单设置删除标记
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException 
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public Object orderDelMark(HttpServletRequest request,HttpServletResponse response) throws ServiceException {
		
		String fid = request.getParameter("fid");
		if(StringUtils.isEmpty(fid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		String userid = getCurrentUserCid(request);
		this.orderFindService.delMark(fid, userid);
		return buildSuccessResult("删除成功");
		
	}
	
}
