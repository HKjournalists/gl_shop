/**
 *
 */
package com.appabc.http.controller.company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.http.utils.HttpApplicationErrorCode;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 上午10:42:41
 */
@Controller
@RequestMapping(value="/copn")
public class CompanyController extends BaseController<TCompanyInfo> {
	
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private IAuthRecordService authRecordService;
	@Autowired
	private IContractInfoService contractInfoService;

	/**
	 * 公司认证
	 * @param request
	 * @param response
	 * @param ciBean
	 * @param arBean
	 * @param caBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authApply",method=RequestMethod.POST)
	public Object authApply(HttpServletRequest request,HttpServletResponse response, 
			TCompanyInfo ciBean, TAuthRecord arBean) {
		
		String ctypeValue = request.getParameter("ctypeValue");
		String addressid = request.getParameter("addressid"); // 默认卸货地址ID
		String imgid = request.getParameter("imgid"); // 图片ID
		
		if(StringUtils.isEmpty(imgid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "认证图片信息不能为空");
		}else if(StringUtils.isEmpty(ctypeValue)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "未选择认证类型");
		}else{
			ciBean.setCtype(CompanyType.enumOf(ctypeValue));
		}
		if(StringUtils.isNotEmpty(ciBean.getMark()) && ciBean.getMark().length()>3999){
			return buildFailResult(ErrorCode.GENERIC_ERROR_CODE, "企业介绍不能超过4000字");
		}
		ciBean.setId(getCurrentUserCid(request)); // 获取用户企业ID
		try {
			this.companyInfoService.authApply(ciBean, arBean, addressid,
					getCurrentUserId(request));
		} catch (Exception e) {
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		return buildSuccessRetJson("认证申请已发送", "");
		
	}
	
	/**
	 * 获取企业资料(非当前登录用户查询)
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCompanyInfo",method=RequestMethod.GET)
	public Object getCompanyInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid"); // 企业ID
		
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业ID不能为空");
		}
		String requestCid=null;
		try {
			requestCid = this.getCurrentUserCid(request);
		} catch (Exception e) {
			requestCid = "otherid";
		}
		CompanyAllInfo cai = companyInfoService.queryAuthCompanyInfo(cid, requestCid);
		
		return buildFilterResultWithBean(cai);
	}
	
	/**
	 * 获取我的企业资料
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyCompanyInfo",method=RequestMethod.GET)
	public Object testGetCompanyInfo(HttpServletRequest request,HttpServletResponse response){
		String cid = this.getCurrentUserCid(request); // 企业ID
		
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "用户资料不完整");
		}
		CompanyAllInfo cai = companyInfoService.queryAuthCompanyInfo(cid, null);
		
		return buildFilterResultWithBean(cai);
	}
	
	/**
	 * 更新企业简介
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIntroduction",method=RequestMethod.POST)
	public Object updateIntroduction(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid");
		String mark = request.getParameter("mark");
		String companyImgIds = request.getParameter("companyImgIds");
		
		if(StringUtils.isEmpty(cid)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业ID不能为空");
		}
		if(StringUtils.isNotEmpty(mark) && mark.length()>3999){
			return buildFailResult(ErrorCode.GENERIC_ERROR_CODE, "企业介绍不能超过4000字");
		}
		try {
			this.companyInfoService.updateIntroduction(cid, mark, companyImgIds);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		return this.buildSuccessResult("更新成功", "");
	}
	
}
