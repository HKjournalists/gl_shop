/**
 *
 */
package com.appabc.http.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TClient;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.tools.service.user.IClientService;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月12日 下午4:54:57
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController extends BaseController<TClient> {
	
	@Autowired
	private IClientService clientService;
	
	/**
	 * 删除IOS上数字小标
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rmBadge")
	public Object rmBadge(HttpServletRequest request,HttpServletResponse response) {
		
		String clientid = request.getParameter("clientid"); // 客户端标识
		if(StringUtils.isNotEmpty(clientid)){
			clientService.rmBadge(clientid);
			return buildSuccessResult("清除成功");
		}else{
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "clientid is null");
		}
		
	}

}
