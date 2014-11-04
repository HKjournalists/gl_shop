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

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.enums.AuthRecordInfo;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.http.utils.ViewInfoEncryptUtil;

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
			TCompanyInfo ciBean, TAuthRecord arBean, TCompanyAddress caBean) {
		
		if(arBean.getImgid() == null || arBean.getImgid() < 1){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "认证图片信息不能为空");
		}else if(StringUtils.isEmpty(ciBean.getCtype())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "未选择认证类型");
//		}else if(StringUtils.isEmpty(ciBean.getContact())){
//			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "联系人不能为空");
//		}else if(StringUtils.isEmpty(ciBean.getCphone())){
//			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "联系人手机不能空");
		}
		TAuthRecord entity = new TAuthRecord();
		entity.setCid(getCurrentUserCid(request));
		entity.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_ING.getVal());
		List<TAuthRecord> arList = this.authRecordService.queryForList(entity); // 查询是否有认证中的记录
		if(arList != null && arList.size()>0){
			return buildFailResult(ErrorCode.GENERICERRORCODE, "请不要重复提交认证信息");
		}
		
		ciBean.setId(getCurrentUserCid(request)); // 获取用户企业ID
		this.companyInfoService.authApply(ciBean, arBean, caBean,getCurrentUserId(request));
		return buildSuccessRetJson("认证申请已发送", "");
		
	}
	
	/**
	 * 获取企业资料
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
		CompanyAllInfo cai = companyInfoService.queryAuthCompanyInfo(cid);
		
		String requestCid=null;
		try {
			requestCid = this.getCurrentUserCid(request);
		} catch (Exception e) {
		}
		if(!cid.equals(requestCid)){ // 其它用户查看
			if(!contractInfoService.isOldCustomer(requestCid, cid)){ // 2个企业未发生过交易，进行企业加密处理
				ViewInfoEncryptUtil.encryptCompanyInfo(cai);
			}
		}
		
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
		this.companyInfoService.updateIntroduction(cid, mark, companyImgIds);
		return this.buildSuccessResult("更新成功", "");
	}
	
}
