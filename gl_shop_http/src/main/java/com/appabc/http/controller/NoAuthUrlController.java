package com.appabc.http.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.pvo.TMobileAppVersion;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.system.IMobileAppVersionService;
import com.appabc.http.utils.HttpApplicationErrorCode;

/**
 * @Description : 不需要登录的接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月8日 下午5:14:07
 */

@Controller
@RequestMapping(value="/noAuthUrl")
public class NoAuthUrlController extends BaseController<BaseBean>{

	@Autowired
	private ICompanyEvaluationService iCompanyEvaluationService;
	
	@Autowired
	private IMobileAppVersionService imavService;
	
	/**
	 * getEvaluationContractList(评价合同接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getEvaluationContractList",method={RequestMethod.POST,RequestMethod.GET})
	public Object getEvaluationContractList(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("ID");
		if (StringUtils.isEmpty(id)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		String type = request.getParameter("type");
		boolean f = !StringUtils.equalsIgnoreCase("0", type) && !StringUtils.equalsIgnoreCase("1", type);
		if (StringUtils.isEmpty(type) || f) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"类型无效.");
		}
		//type is 0 is company, 1 is contract
		CompanyEvaluationInfo entity = new CompanyEvaluationInfo();
		if(StringUtils.equalsIgnoreCase("0", type)){
			entity.setCid(id);
			return iCompanyEvaluationService.getEvaluationContractList(entity);
		}else if(StringUtils.equalsIgnoreCase("1", type)){
			entity.setOid(id);
			return iCompanyEvaluationService.getEvaluationContractList(entity);
		}else{
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,"没有数据.");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkVersion")
	public Object checkVersion(HttpServletRequest request,HttpServletResponse response) {
		String devices = request.getParameter("devices");
		String lastNo = request.getParameter("lastNo");
		if(StringUtils.isEmpty(devices)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"devices is not allow null.");
		}
		if(StringUtils.isEmpty(lastNo)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"lastNo is not allow null.");
		}
		try {
			TMobileAppVersion obj = imavService.getMobileAppUpdateInfo(devices, lastNo);
			if(obj == null){
				//return buildEmptyResult();//"You version is the newest version !"
				return new TMobileAppVersion();
			}else{
				obj.setId(StringUtils.EMPTY);
				obj.setUpdatetime(null);
				return obj;
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			return this.buildFailResult(HttpApplicationErrorCode.MOBILE_APP_VERSION_ERROR, e.getMessage());
		}
	}
	
}
