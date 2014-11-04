/**
 *
 */
package com.appabc.http.copn;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月17日 上午11:25:43
 */
public class AcceptBankTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		this.loginSimulation(null, null, null);
//		authApply();
//		getList();
//		getInfo();
//		update();
//		setDefault();
//		del();
	}

	
	public void authApply(){

		request.setRequestURI("/copn/accept/authApply");
		request.setMethod("POST");
		
		/***企业信息*********************/
		request.addParameter("code", "3333");
		request.addParameter("cid", "CompanyInfoId000000811102014END"); // 企业ID
		request.addParameter("imgid", "4"); // 认证图片ID
		request.addParameter("carduser", "王啊啊"); // 认证图片ID
		request.addParameter("bankcard", "212121211221");
		request.addParameter("bankname", "瑞士银行");
		
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getList(){
		
		request.setRequestURI("/copn/accept/getList");
		request.setMethod("GET");
		
		/***企业信息*********************/
		request.addParameter("cid", "CompanyInfoId000000811102014END"); // 企业ID
		
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getInfo(){
		
		request.setRequestURI("/copn/accept/getInfo");
		request.setMethod("GET");
		
		request.addParameter("id", "ACCEPTBANKID201410170000007143940");
		
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(){

		request.setRequestURI("/copn/accept/mdy");
		request.setMethod("POST");
		
		/***企业信息*********************/
		request.addParameter("id", "ACCEPTBANKID201410170000006141607"); // ID
		
		request.addParameter("code", "3333");
		request.addParameter("imgid", "5"); // 认证图片ID
		request.addParameter("carduser", "王啊啊11"); // 认证图片ID
		request.addParameter("bankcard", "777888");
		request.addParameter("bankname", "瑞士银行2");
		
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDefault(){
		
		request.setRequestURI("/copn/accept/setDefault");
		request.setMethod("POST");
		
		/***企业信息*********************/
		request.addParameter("id", "ACCEPTBANKID201410170000006141607"); // ID
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void del(){
		
		request.setRequestURI("/copn/accept/del");
		request.setMethod("POST");
		
		/***企业信息*********************/
		request.addParameter("id", "ACCEPTBANKID201410170000006141607"); // ID
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
