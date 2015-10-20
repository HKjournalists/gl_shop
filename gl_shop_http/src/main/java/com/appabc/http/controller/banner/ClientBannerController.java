package com.appabc.http.controller.banner;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.ClientBannerInfo;
import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.bean.pvo.TClientBanner;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.banner.IClientBannerService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年8月19日 下午5:09:12
 */

@Controller
@RequestMapping(value="/clientBanner")
public class ClientBannerController extends BaseController<TClientBanner> {

	@Autowired
	private IClientBannerService clientBannerService;
	
	@ResponseBody
	@RequestMapping(value = "/getBannerList",method=RequestMethod.GET)
	public Object getList(HttpServletRequest request,HttpServletResponse response) {
		
		String btype = request.getParameter("btype").toUpperCase();
		if(StringUtils.isEmpty(btype)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "客户端类型不能为空");
		}		
		List<ClientBannerInfo> list=clientBannerService.queryBannerLittleInfoList(ClientType.valueOf(btype).getVal());
		return buildFilterResultWithString(list);
	}
}
