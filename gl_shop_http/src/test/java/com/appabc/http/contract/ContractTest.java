package com.appabc.http.contract;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
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
	@Rollback(value=true)
	public void mainTest() {
		//testGetEvaluationContractList();
		//loginSimulation("13760434638", "180", "201501270000021");
		//loginSimulation("15811822330", "167", "201501260000018");
		//loginSimulation("18613064147", "170", "000000915102014");
		//loginSimulation("15811822330", "178", "201501260000019");
		//loginSimulation("15986805626", "177", "201501260000018");
		//loginSimulation("15811822330", "178", "201501260000019");
		//testGetPayRecordList();
		//testGetContractListWitPage();
		//this.testGetMyContractListWithPaginiation();
		//this.testUnPayFundsContractList();
		//loginSimulation("13760434638", "176", "241120140000017");
		//testcontractArbitractionProcess();
		//testGetEvaluationContractList();
		//testGetContractDetailInfoEx();
	}
	
	void testGetPayRecordList(){
		request.setRequestURI("/purse/getPayRecordList");
		request.setMethod("POST");
		//request.addParameter("OID", "1020150112175");
		request.addParameter("type", "1");
		//request.addParameter("fid", "201501080047008");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testCancelDraftContract(){
		request.setRequestURI("/contract/cancelDraftContract");
		request.setMethod("POST");
		request.addParameter("OID", "1020150112175");
		request.addParameter("operateType", "23");
		request.addParameter("fid", "201501080047008");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testcontractArbitractionProcess(){
		
		//true, "201501080008408", "201411270000014", "国立123公司2014-11-27 20:35:52", 10, 10
		request.setRequestURI("/contract/contractArbitractionProcess");
		request.setMethod("POST");
		request.addParameter("aid", "201503260010226");
		request.addParameter("isTrade", "true");//20//21//22
		request.addParameter("disPrice", "2");
		request.addParameter("disNum", "2.5");/**/
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void testApplyOrAgreeOrArbitrateFinalEstimate(){
		request.setRequestURI("/contract/applyOrAgreeOrArbitrateFinalEstimate");
		request.setMethod("POST");
		request.addParameter("OID", "1020150112174");
		request.addParameter("operateType", "20");//20//21//22
		request.addParameter("disPrice", "9");
		request.addParameter("disNum", "10");
		request.addParameter("finalAmount", "85");
		//request.addParameter("fid", "201501080047008");
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetContractDetailInfoEx(){
		request.setRequestURI("/contract/getContractDetailInfoEx");
		request.setMethod("GET");
		request.addParameter("OID", "1020150403404");
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testGetMyContractListWithPaginiation(){
		request.setRequestURI("/contract/getMyContractListWithPaginiation");
		request.setMethod("GET");
		request.addParameter("status", "0");//0/1/3
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
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
	
	public static void main(String[] args) {
		String str = "PCFET0NUWVBFIGh0bWwgUFVCTElDICItLy9XM0MvL0RURCBYSFRNTCAxLjAgVHJhbnNpdGlvbmFsLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL1RSL3hodG1sMS9EVEQveGh0bWwxLXRyYW5zaXRpb25hbC5kdGQiPg0KPGh0bWw+DQo8aGVhZD4NCjxtZXRhIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCIgaHR0cC1lcXVpdj0iQ29udGVudC1UeXBlIiAvPg0KPHRpdGxlPrTWybA8L3RpdGxlPg0KPC9oZWFkPg0KPGJvZHk+DQo8ZGl2PiANCiAgICA8cCBhbGlnbj0iY2VudGVyIj48c3Ryb25nPrOkva2158nMtefX08nMzvG6z82sPC9zdHJvbmc+PC9wPiANCiAgICA8cD66z82sseC6xaO6PHU+Jm5ic3A7Jm5ic3A7MTAyMDE0MTIwNTExNyZuYnNwOyZuYnNwOzwvdT4gPC9wPiANCiAgICA8cD48YnIgLz4gIML0ILe9o7o8dT4mbmJzcDsmbmJzcDu5+sGiMTIzuavLvjIwMTQtMTEtMjcgMjA6MzU6NTImbmJzcDsmbmJzcDs8L3U+IMLyILe9o7o8dT4mbmJzcDsmbmJzcDu5+sGisuLK1Lmry74xNDE3NzYzMTQ5ODgyJm5ic3A7Jm5ic3A7PC91PjwvcD4gDQogICAgPHA+wvS3vUlEo7o8dT4mbmJzcDsmbmJzcDsyMDE0MTEyNzAwMDAwMTQmbmJzcDsmbmJzcDs8L3U+IMLyt71JRKO6PHU+Jm5ic3A7Jm5ic3A7MTgxMTIwMTQwMDAwMDEzJm5ic3A7Jm5ic3A7PC91PjwvcD4gDQogICAgPHA+PGJyIC8+ICC12tK7zPUgyczGt7v5sb7XtL/2PGJyIC8+ICAxoaLJzMa3w/uzxqO6PHU+Jm5ic3A7Jm5ic3A7tNbJsCZuYnNwOyZuYnNwOzwvdT48YnIgLz4gIDKhotbWwOCjr7nmuPGjujx1PiZuYnNwOyZuYnNwO72tybAmbmJzcDsmbmJzcDs8L3U+PGJyIC8+ICAzoaLR1cmro7o8dT4mbmJzcDsmbmJzcDvRxyZuYnNwOyZuYnNwOzwvdT48YnIgLz4gIDShorL6tdijujx1PiZuYnNwOyZuYnNwO7P2wLQmbmJzcDsmbmJzcDs8L3U+PGJyIC8+DQogICAgICA8IS0tILqsxODBvyAtLT4gIAkNCgkgIDwhLS0gxOC/6bqswb8gLS0+DQoJICA8IS0tILqsy67CyiAtLT4NCgkgIDwhLS0gse2528PctsggLS0+DQoJICA8IS0tILbRu/3D3LbIIC0tPg0KCSAgPCEtLSC84bnM0NTWuLHqIC0tPg0KICAgICAgPCEtLSC6rMTgwb8gLS0+ICAJDQoJICA8IS0tIMTgv+m6rMG/IC0tPg0KCSAgPCEtLSC6rMuuwsogLS0+DQoJICA8IS0tILHtudvD3LbIIC0tPg0KCSAgPCEtLSC20bv9w9y2yCAtLT4NCgkgIDwhLS0gvOG5zNDU1rix6iAtLT4NCiAgICAgIDwhLS0guqzE4MG/IC0tPiAgCQ0KCSAgPCEtLSDE4L/puqzBvyAtLT4NCgkgIDwhLS0guqzLrsLKIC0tPg0KCSAgPCEtLSCx7bnbw9y2yCAtLT4NCgkgIDwhLS0gttG7/cPctsggLS0+DQoJICA8IS0tILzhuczQ1Na4seogLS0+DQogICAgICA8IS0tILqsxODBvyAtLT4gIAkNCgkgIDwhLS0gxOC/6bqswb8gLS0+DQoJICA8IS0tILqsy67CyiAtLT4NCgkgIDwhLS0gse2528PctsggLS0+DQoJICA8IS0tILbRu/3D3LbIIC0tPg0KCSAgPCEtLSC84bnM0NTWuLHqIC0tPg0KICAgICAgPCEtLSC6rMTgwb8gLS0+ICAJDQoJICA8IS0tIMTgv+m6rMG/IC0tPg0KCSAgPCEtLSC6rMuuwsogLS0+DQoJICA8IS0tILHtudvD3LbIIC0tPg0KCSAgPCEtLSC20bv9w9y2yCAtLT4NCgkgIDwhLS0gvOG5zNDU1rix6iAtLT4NCiAgICA1oaK6rMTgwb+jqCWjqaO6PHU+Jm5ic3A7Jm5ic3A7MC4xJm5ic3A7Jm5ic3A7PC91PjxiciAvPiAgNqGixOC/6bqswb+jqCWjqaO6PHU+Jm5ic3A7Jm5ic3A7MC4yJm5ic3A7Jm5ic3A7PC91PjxiciAvPiAgN6GiuqzLrsLKo6glo6mjujx1PiZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwOzwvdT48YnIgLz4gIDihorHtudvD3LbIo6hrZy9tJnN1cDM7JaOpo7o8dT4mbmJzcDsmbmJzcDswLjMmbmJzcDsmbmJzcDs8L3U+PGJyIC8+OaGittG7/cPctsgoa2cvbSZzdXAzOyWjqaO6PHU+Jm5ic3A7Jm5ic3A7MyZuYnNwOyZuYnNwOzwvdT48YnIgLz4gMTChorzhuczQ1Na4seqjqCWjqaO6PHU+Jm5ic3A7Jm5ic3A7MC41Jm5ic3A7Jm5ic3A7PC91PjxiciAvPiAxMaGiubrC8sG/KLbWKaO6PHU+Jm5ic3A7Jm5ic3A7MTAwJm5ic3A7Jm5ic3A7PC91PjxiciAvPg0KICAgIDwvcD4gDQogICAgPHA+PGJyIC8+ICC12rb+zPUgyczGt7zbv+48YnIgLz4gIMnMxre1pbzbzqo8dT4mbmJzcDsmbmJzcDsyMCZuYnNwOyZuYnNwOzwvdT6jqNSqo6mjrNfcvNvOqjx1PiZuYnNwOyZuYnNwOzUwJm5ic3A7Jm5ic3A7PC91PqOo1KqjqaGjPGJyIC8+ICC5srzGo7o8dT4mbmJzcDsmbmJzcDs1MCZuYnNwOyZuYnNwOzwvdT6jqLTz0LSjqdSqoaM8L3A+IA0KICAgIDxwPjxiciAvPiAgtdrI/cz1INanuLbM9b/uPGJyIC8+ICAxoaK4tr/uwfezzKO6PGJyIC8+ICDC8sL0y6u3vdTaus/NrMi3yM+686Osus/NrL+qyrzJ+tCno6zGvcyov6rKvLazveHLq7e9saPWpL3wo6zC8re90OjU2jQ40KHKscTavau79b/u1qe4trW9s6S9rbXnyczGvcyo1cu7p6Gj0+LG2r2rytPOqrWlt73OpdS8oaM8L3A+IA0KICAgIDxwPjKhorOkva2158nMxr3MqM6qwvK80szhuam24NLUz8LWp7i2t73KvTwvcD4gDQogICAgPHA+o6gxo6nHrrD81qe4tqO6v8nS1M2ouf2zpL2ttefJzMeusPy1xLv1v+7Vy7unxNrT4Lbu1qe4tjxiciAvPiAgo6gyo6nU2s/f1qe4tqO6v8nS1M2ouf3K1rv6v827p7bLoaLK1rv60vjQ0KGizfjS+LXIyta2ztTaz9/Wp7i2oaM8YnIgLz4gIKOoM6Op0vjQ0Neq1cujur/J0tTNqLn90vjQ0Lvy1qe4trmkvt+9+NDQ16rVyyANCgk8L2JyPiZuYnNwOyZuYnNwOyZuYnNwOyZuYnNwO8P7ICAgILPGo7ogICA8dT4mbmJzcDsmbmJzcDsmbmJzcDsmbmJzcDu9rcvVufrBos34wue/";
		byte[] c;
		try {
			c = Base64Utils.decode(str.getBytes("UTF-8"));
			System.out.println(new String(c,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	void testGetContractListWitPage(){
		request.setRequestURI("/contract/getContractListWitPage");
        request.setMethod("GET");
        //request.addParameter("status", "1");
        //request.addParameter("pageIndex", "-1");
        request.addParameter("status", "1");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	void testconfirmUninstallGoods(){
		request.setRequestURI("/contract/confirmUninstallGoods");
		request.setMethod("POST");
		request.addParameter("OID", "1020141209155");
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
		request.setRequestURI("/noAuthUrl/getEvaluationContractList");
        request.setMethod("GET");
        request.addParameter("ID", "201411270000014");
        request.addParameter("type", "0");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
}
