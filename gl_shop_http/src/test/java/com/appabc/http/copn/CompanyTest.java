/**
 *
 */
package com.appabc.http.copn;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.bean.enums.CompanyInfo;
import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 下午4:39:43
 */
public class CompanyTest extends AbstractHttpControllerTest {

	@Override
	@Test
	public void mainTest() {
		loginSimulation(null, null, "CompanyInfoId000000524092014END");
//		loginSimulation("aa", "1", "000000915102014");
//		authApply();
//		testQuerCompanyAllInfo();
	}
	
	/**
	 * 认证申请
	 */
	public void authApply(){
		request.setRequestURI("/copn/authApply");
		request.setMethod("POST");
		
		/***企业信息*********************/
		request.addParameter("ctypeValue", CompanyInfo.CompanyType.COMPANY_TYPE_PERSONAL.getVal());
		request.addParameter("mark", "企业介绍000");
		request.addParameter("companyImgIds", "4,5"); // 企业照片ID
		request.addParameter("imgid", "8"); // 认证图片ID
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询企业信息
	 */
	public void testQuerCompanyAllInfo(){
		
		request.setRequestURI("/copn/getCompanyInfo");
		request.setMethod("GET");
		
		request.addParameter("cid", "CompanyInfoId000000811102014END");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
