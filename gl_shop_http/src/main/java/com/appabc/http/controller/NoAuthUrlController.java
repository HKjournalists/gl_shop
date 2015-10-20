package com.appabc.http.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.enums.PurseInfo.RequestType;
import com.appabc.bean.pvo.TActivityJoin;
import com.appabc.bean.pvo.TMobileAppVersion;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.system.IMobileAppVersionService;
import com.appabc.datas.service.system.ISystemActivityService;
import com.appabc.datas.tool.EveryUtil;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.http.utils.HttpApplicationErrorCode.HttpAppSystemConstant;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.pay.service.local.IPayThirdInfoService;
import com.appabc.pay.service.local.IPayThirdRecordService;
import com.appabc.pay.util.UPSDKUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

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
	private IPayThirdInfoService iPayThirdInfoService;
	
	@Autowired
	private IPayThirdRecordService iPayThirdRecordService;
	
	@Autowired
	private IPassbookInfoService iPassbookInfoService;
	
	@Autowired
	private IPassPayService iPassPayService;
	
	@Autowired
	private IContractInfoService iContractInfoService;
	
	@Autowired
	private IMobileAppVersionService imavService;
	
	@Autowired
	private ISystemActivityService iSystemActivityService;
	
	@ResponseBody
	@RequestMapping(value = "/system/activity/join", method=RequestMethod.POST)
	public Object joinActivity(HttpServletRequest request, HttpServletResponse response,TActivityJoin entity){
		if(entity == null){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.ENTITY_IS_NULL));
		}
		if (StringUtils.isEmpty(entity.getPhone())) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_PHONE_NULL));
		}
		if (StringUtils.isEmpty(entity.getName())) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_NAME_NULL));
		}
		if (entity.getReqnum() <= 0) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_REQNUM_NULL));
		}
		boolean isPhoneNumber = com.appabc.http.utils.EveryUtil.isPhoneNumber(entity.getPhone());
		if(!isPhoneNumber){
			return buildFailResult(HttpApplicationErrorCode.SYSTEM_ACTIVITY_PHONENUM_FORMAT_ERROR,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_PHONENUM_FORMAT));
		}
		boolean isPhoneJoinAgain = iSystemActivityService.checkPhoneNumIsJoined(entity.getPhone());
		if(isPhoneJoinAgain){
			return buildFailResult(HttpApplicationErrorCode.SYSTEM_ACTIVITY_AGAIN_ERROR,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_REQUEST_AGAIN));
		}
		try{
			entity.setCreator(getCurrentUserId(request));
		}catch(Exception ex){
			this.log.error(ex);
		}
		entity.setCreatedate(DateUtil.getNowDate());
		iSystemActivityService.add(entity);
		return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS));
	}
	
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
			String currentLoginUserId = StringUtils.EMPTY; 
			try{				
				currentLoginUserId = this.getCurrentUserId(request);
			}catch(RuntimeException e){
				log.error(e.getMessage(), e);
			}
			entity.setCid(id);
			List<CompanyEvaluationInfo> rs = iCompanyEvaluationService.getEvaluationContractList(entity);
			EveryUtil.convertAndEncryptCompanyEvaluationInfo(rs, iContractInfoService, currentLoginUserId);
			return rs;
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
	
	@ResponseBody
	@RequestMapping(value = "/unionPaybackNotifyBusinessUrl")
	public synchronized void unionPaybackNotifyBusinessUrlResponse(HttpServletRequest request,HttpServletResponse response){
		log.info("UnionPaybackNotifyBusinessUrl receive the backend notify start.");
		
		try {
			request.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = UPSDKUtil.getAllRequestParams(request);
		Map<String, String> valideData = UPSDKUtil.encodeMapParamsFromISO88591(reqParam);
		String oid = reqParam.get("orderId");
		iPayThirdRecordService.savePayThirdOrgRecord(oid, SDKUtil.coverMap2String(reqParam),RequestType.RESPONSE);
		log.info(reqParam);
		// 验证签名
		if (SDKUtil.validate(valideData, encoding)) {
			iPassPayService.unionPaybackNotifyBusinessResp(oid,valideData);
			log.info("UnionPaybackNotifyBusinessUrl validate result success.");
		} else {
			log.info("UnionPaybackNotifyBusinessUrl validate result failure.");
		}
		log.info("UnionPaybackNotifyBusinessUrl receive the backend notify end.");
	}
	
}
