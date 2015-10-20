/**
 *
 */
package com.appabc.http.controller.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.enums.AcceptBankInfo.AcceptBankStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description :企业提款人接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月13日 下午8:43:38
 */
@Controller
@RequestMapping(value="/copn/accept")
public class AcceptBankController extends BaseController<TAcceptBank> {
	
	@Autowired
	private IAcceptBankService acceptBankService;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private ValidateCodeManager vcm;
	
	/**
	 * 企业提款人认证申请
	 * @param request
	 * @param response
	 * @param abBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authApply",method=RequestMethod.POST)
	public Object authApply(HttpServletRequest request,HttpServletResponse response, 
			TAcceptBank abBean) {
		
		String imgid = request.getParameter("imgid");
		String code = request.getParameter("code"); // 短信验证码
		if(StringUtils.isEmpty(code)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "验证不能为空");
		}else{
			String smsCode = vcm.getSmsCode(this.getCurrentUser(request).getPhone());
			if(smsCode == null){
				return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码不存在或已过期");
			}else if(!code.equals(smsCode)){
				return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码错误");
			}
		}
		
		if(StringUtils.isEmpty(abBean.getCid())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}else if(StringUtils.isEmpty(abBean.getCarduser())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "提款人姓名不能为空");
		}else if(StringUtils.isEmpty(abBean.getBankcard())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "银行卡号不能为空");
		}else if(StringUtils.isEmpty(abBean.getBanktype())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "银行类型不能为空");
		}
		
		// 图片是否需要验证，个人身份不需要上传图片
		TCompanyInfo ci = this.companyInfoService.query(abBean.getCid());
		CompanyType ctype = null;
		if(ci != null) ctype = ci.getCtype();
		
		if(StringUtils.isEmpty(imgid) && ctype != CompanyType.COMPANY_TYPE_PERSONAL){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "认证图片ID不能为空");
		}
		
		abBean.setCarduserid(this.getCurrentUserId(request));
		abBean.setStatus(AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER);
		try {
			this.acceptBankService.authApply(abBean);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("添加成功", "");
		
	}
	
	/**
	 * 修改企业提款人，将会重新认证申请
	 * @param request
	 * @param response
	 * @param abBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mdy",method=RequestMethod.POST)
	public Object reAuthApply(HttpServletRequest request,HttpServletResponse response, 
			TAcceptBank abBean) {
		
		if(StringUtils.isEmpty(abBean.getId())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "提款人ID不能为空");
		}else if(StringUtils.isEmpty(abBean.getCarduser())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "提款人姓名不能为空");
		}else if(StringUtils.isEmpty(abBean.getImgid())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "认证图片ID不能为空");
		}else if(StringUtils.isEmpty(abBean.getBankcard())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "银行卡号不能为空");
		}else if(StringUtils.isEmpty(abBean.getBankname())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "开户行不能为空");
		}
		
		abBean.setCarduserid(this.getCurrentUserId(request));
		try {
			this.acceptBankService.reAuthApply(abBean);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("修改成功", "");
		
	}
	
	/**
	 * 获取企业提款人列表
	 * @param request
	 * @param response
	 * @param abBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList",method=RequestMethod.GET)
	public Object getList(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid");
		String authStatus = request.getParameter("authStatus"); // 是否认证通过
		
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}
		
		TAcceptBank ab = new TAcceptBank();
		ab.setCid(cid);
		if(StringUtils.isNotEmpty(authStatus)){ // 添加过滤条件，在管理页面可以查看审核中的提款人
			ab.setAuthstatus(AuthRecordStatus.enumOf(authStatus));
		}
		List<TAcceptBank> list = this.acceptBankService.queryForList(ab);
		
		for (int i=0; i<list.size(); i++){// 图片获取
			list.get(i).setvImgList(uploadImagesService.getViewImgsByOidAndOtype(list.get(i).getId(), FileInfo.FileOType.FILE_OTYPE_BANK.getVal()));;
		}
		
		String[] filterPropertyNames = {"createtime","updatetime","creator"};
		
		return this.buildFilterResultWithArray(list, filterPropertyNames);
		
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public Object del(HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		if(StringUtils.isEmpty(id)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "收款人ID不能为空");
		}
		
		this.acceptBankService.delete(id);
		
		return this.buildSuccessResult("删除成功", "");
		
	}
	
	/**
	 * 设置默认
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setDefault")
	public Object setDefault(HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		if(StringUtils.isEmpty(id)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "收款人ID不能为空");
		}
		
		try {
			this.acceptBankService.setDefault(id);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		return buildSuccessResult("设置成功", "");
		
	}
	
	/**
	 * 获取提款人详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo",method=RequestMethod.GET)
	public Object getInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		if(StringUtils.isEmpty(id)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "收款人ID不能为空");
		}
		
		try {
			return this.acceptBankService.query(id);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		
	}

}
