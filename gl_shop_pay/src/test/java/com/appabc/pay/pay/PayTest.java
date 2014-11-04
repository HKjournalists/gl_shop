/**  
 * com.appabc.pay.pay.PayTest.java  
 *   
 * 2014年10月17日 下午2:25:14  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.pay;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.common.utils.DateUtil;
import com.appabc.pay.AbstractPayTest;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.enums.PurseInfo.DeviceType;
import com.appabc.pay.enums.PurseInfo.ExtractStatus;
import com.appabc.pay.enums.PurseInfo.PayDirection;
import com.appabc.pay.enums.PurseInfo.PayWay;
import com.appabc.pay.enums.PurseInfo.TradeType;
import com.appabc.pay.service.local.IPassbookPayService;
import com.appabc.pay.util.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月17日 下午2:25:14
 */

public class PayTest extends AbstractPayTest {

	@Autowired
	private IPassbookPayService iPassbookPayService;
	@Autowired
	private PrimaryKeyGenerator PKGenerator;
	/* (non-Javadoc)  
	 * @see com.appabc.pay.AbstractPayTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		String cid = "CompanyInfoId000000811102014END";
		String passId = "PASSID2014101500005104400";
		float balance = 50000f;
		String payno = "PAYNO0000000000003";
		String oid = "201410160007816";
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(passId);
		pay.setOtype(TradeType.EXTRACT_CASH_REQUEST.getValue());
		pay.setOid(oid);
		pay.setName(cid);
		pay.setPayno(payno);
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT.getValue());
		pay.setPaytype(PayWay.BANK_DEDUCT.getValue());
		pay.setPatytime(DateUtil.getNowDate());
		pay.setStatus(String.valueOf(ExtractStatus.REQUEST.getValue()));
		pay.setCreatedate(DateUtil.getNowDate());
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
	}

}
