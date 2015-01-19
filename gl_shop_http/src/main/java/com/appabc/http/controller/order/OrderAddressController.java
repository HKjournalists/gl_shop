/**
 *
 */
package com.appabc.http.controller.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.order.IOrderAddressService;

/**
 * @Description : 询单卸货地址
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月13日 上午11:00:53
 */
@Controller
@RequestMapping(value="/order/address")
public class OrderAddressController extends BaseController<TOrderAddress> {
	
	@Autowired
	private IOrderAddressService orderAddressService;
	
	/**
	 * 获取询单的卸货地址
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@ResponseBody
	@RequestMapping(value = "/getAddress",method=RequestMethod.GET)
	public Object getOrderFindAddress(HttpServletRequest request,HttpServletResponse response) throws ServiceException {
		String fid = request.getParameter("fid");
		if(StringUtils.isEmpty(fid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "询单ID不能为空");
		}
		
		TOrderAddress oa = orderAddressService.queryByFid(fid);
		
		return oa;
	}

}
