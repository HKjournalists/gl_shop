package com.appabc.http.contract;

import java.util.Map;

import org.junit.Test;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月12日 上午10:30:46
 */

public class ContractTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)  
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		//testGetEvaluationContractList();
		loginSimulation("00000012", "00000012", "00000012");
		testGetContractListWitPage();
	}
	void testGetPurseList(){
		request.setRequestURI("/purse/getPurseList");
        request.setMethod("GET");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testUnPayFundsContractList(){
		request.setRequestURI("/contract/unPayFundsContractList");
		request.setMethod("GET");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetContractDetailTemplate(){
		request.setRequestURI("/contract/getContractDetailTemplate");
        request.setMethod("GET");
        request.addParameter("OID", "201409230007123");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
			try{
				String result = response.getContentAsString();
				Map<String,Object> mapRes = this.gson.fromJson(result, Map.class);
				byte[] c = Base64Utils.decode(((Map<String,Object>)mapRes.get("DATA")).get("template").toString().getBytes());
				logUtil.info(new String(c));
			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetContractListWitPage(){
		request.setRequestURI("/contract/getContractListWitPage");
        request.setMethod("GET");
        //request.addParameter("status", "1");
        //request.addParameter("pageIndex", "-1");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetContractDetailInfo(){
		request.setRequestURI("/contract/getContractDetailInfo");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}

	void testToConfirmContract(){
		request.setRequestURI("/contract/toConfirmContract");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testNoticeShippingGoods(){
		request.setRequestURI("/contract/noticeShippingGoods");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testPayContractFunds(){
		request.setRequestURI("/contract/payContractFunds");
		request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testSingleCancelContract(){
		request.setRequestURI("/contract/singleCancelContract");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testMultiCancelContract(){
		request.setRequestURI("/contract/multiCancelContract");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetCancelContractListForPage(){
		request.setRequestURI("/contract/getCancelContractListForPage");
        request.setMethod("GET");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testApplyOrNotGoodsInfo(){
		request.setRequestURI("/contract/applyOrPassGoodsInfo");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        request.addParameter("operate", "7");
        request.addParameter("fid", "201409230004523");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testValidateGoodsDisPrice(){
		request.setRequestURI("/contract/validateGoodsDisPrice");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        request.addParameter("LID", "201409230004423");
        request.addParameter("disPrice", "150.0");
        request.addParameter("disNum", "300");
        request.addParameter("reason", "reason");
        request.addParameter("remark", "remark");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetGoodsDisPriceHisList(){
		request.setRequestURI("/contract/getGoodsDisPriceHisList");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testConfirmUninstallGoods(){
		request.setRequestURI("/contract/confirmUninstallGoods");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testValidateGoodsInfo(){
		request.setRequestURI("/contract/validateGoodsInfo");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testToContractArbitration(){
		request.setRequestURI("/contract/toContractArbitration");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetContractArbitrationHistroy(){
		request.setRequestURI("/contract/getContractArbitrationHistroy");
        request.setMethod("GET");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetContractChangeHistory(){
		request.setRequestURI("/contract/getContractChangeHistory");
        request.setMethod("GET");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testToEvaluateContract(){
		request.setRequestURI("/contract/toEvaluateContract");
        request.setMethod("POST");
        request.addParameter("OID", "201409230007223");
        request.addParameter("evaluation", "交易开心");
        request.addParameter("credit", "4");
        request.addParameter("satisfaction", "5");
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetEvaluationContractList(){
		request.setRequestURI("/contract/getEvaluationContractList");
        request.setMethod("GET");
        request.addParameter("OID", "201409230007223");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
}
