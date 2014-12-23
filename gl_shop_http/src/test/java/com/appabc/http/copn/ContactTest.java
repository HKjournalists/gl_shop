/**
 *
 */
package com.appabc.http.copn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.http.AbstractHttpControllerTest;
import com.google.gson.Gson;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月26日 下午2:51:12
 */
public class ContactTest extends AbstractHttpControllerTest {

	@Override
	@Test
	public void mainTest() {
//		loginSimulation(null, null, null);
//		save();
//		getList();
	}

	
	/**
	 * 保存企业联系人
	 */
	public void save(){
		request.setRequestURI("/copn/contact/save");
		request.setMethod("POST");
		
		List<TCompanyContact> ccList = new ArrayList<TCompanyContact>();
		TCompanyContact cc = new TCompanyContact();
		cc.setCname("黄黄");
		cc.setCphone("1234567890");
		cc.setTel("0755-88888888");
		cc.setStatus(CompanyInfo.ContactStatus.CONTACT_STATUS_DEFULT.getVal());
		cc.setId("1");
		ccList.add(cc);
		
		cc = new TCompanyContact();
		cc.setCname("红红");
		cc.setCphone("9876543210");
		cc.setTel("0755-66666666");
		cc.setStatus(CompanyInfo.ContactStatus.CONTACT_STATUS_OTHER.getVal());
		cc.setId("2");
		ccList.add(cc);
		
		Gson gson = new Gson();
		request.addParameter("cid", "CompanyInfoId000000524092014END");
		request.addParameter("contactList", gson.toJson(ccList));
		
		System.out.println(gson.toJson(gson.toJson(ccList)));
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getList(){
		request.setRequestURI("/copn/contact/getList");
		request.setMethod("GET");
		
		request.addParameter("cid", "CompanyInfoId000000524092014END");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
