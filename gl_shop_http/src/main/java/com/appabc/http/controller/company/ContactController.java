/**
 *
 */
package com.appabc.http.controller.company;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.enums.CompanyInfo;
import com.appabc.datas.service.company.ICompanyContactService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @Description : 企业联系人CONTROLLER
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月26日 上午10:35:02
 */
@Controller
@RequestMapping(value="/copn/contact")
public class ContactController extends BaseController<TCompanyContact> {
	
	@Autowired
	private ICompanyContactService companyContactService;
	
	/**
	 * 获取企业联系人
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList",method=RequestMethod.GET)
	public Object getContactList(HttpServletRequest request,HttpServletResponse response) {
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业ID不能为空");
		}
		
		TCompanyContact cc = new TCompanyContact();
		cc.setCid(cid);
		
		List<TCompanyContact> ccList = companyContactService.queryForList(cc);
		
		return this.buildFilterResultWithString(ccList, "cid","createtime", "creater");
	}
	
	/**
	 * 企业联系人保存
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public Object saveContact(HttpServletRequest request,HttpServletResponse response) {
		
		String contactList = request.getParameter("contactList"); // 企业联系人信息，json格式
		String cid = request.getParameter("cid"); // 企业ID
		if(StringUtils.isEmpty(contactList)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "联系人信息不能为空");
		}else if(StringUtils.isEmpty(cid)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业ID不能为空");
		}
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement =  jsonParser.parse(contactList);
		JsonArray jsonArray = jsonElement.getAsJsonArray();
		TCompanyContact ccBean = null;
		
		Gson gson = new Gson();
		for(JsonElement je : jsonArray){ // 企业联系人信息
			ccBean = gson.fromJson(je, TCompanyContact.class);
			if(ccBean.getStatus() == CompanyInfo.ContactStatus.CONTACT_STATUS_DEFULT.getVal()){ // 默认联系人信息检查
				if(StringUtils.isEmpty(ccBean.getCname())){
					this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "默认联系人姓名不能为空");
				}else if(StringUtils.isEmpty(ccBean.getCphone())){
					this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "默认联系人手机不能为空");
				}
			}
			if(!StringUtils.isEmpty(ccBean.getId())){
				this.companyContactService.modify(ccBean);
			}else{
				ccBean.setCid(cid);
				ccBean.setCreater(this.getCurrentUserId(request));
				ccBean.setCreatetime(Calendar.getInstance().getTime());
				this.companyContactService.add(ccBean);
			}
		}
		
		return this.buildSuccessRetJson("联系人保存成功", "");
	}

}
