/**  
 * com.appabc.pay.pay.PayTest.java  
 *   
 * 2014年10月17日 下午2:25:14  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.pay;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.utils.DateUtil;
import com.appabc.pay.AbstractPayTest;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.pay.service.local.IPassbookPayService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

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
	private IPassbookInfoService iPassbookInfoService;
	@Autowired
	private IPassbookPayService iPassbookPayService;
	@Autowired
	private IPassPayService iPassPayService;
	@Autowired
	private PrimaryKeyGenerator PKGenerator;
	/* (non-Javadoc)  
	 * @see com.appabc.pay.AbstractPayTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		//testGetPayRecordListWithOid();
	}
	
	public void testGetPayRecordListWithOid(){
		String oid = "201412010010311";
		List<TPassbookPay> result = iPassPayService.getPayRecordListWithOid(StringUtils.EMPTY, null, oid, null);
		log.info(result);
	}
	
	public void testCase(){
		String cid = "181120140000013";
		String passId = "PASSID2014111800014154246";//PASSID2014111800014154246 //PASSID2014111800013154246
		double balance = 1000000.0;
		String payno = "PAYNO0000000000003";
		String oid = "";
		
		TPassbookInfo info = iPassbookInfoService.query(passId);
		info.setAmount(info.getAmount()+balance);
		iPassbookInfoService.modify(info);
		
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(passId);
		pay.setOtype(TradeType.DEPOSIT);
		pay.setOid(oid);
		pay.setName(cid);
		pay.setPayno(payno);
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT);
		pay.setPaytype(PayWay.BANK_DEDUCT);
		pay.setPaytime(DateUtil.getNowDate());
		pay.setStatus(TradeStatus.SUCCESS.getVal());
		pay.setCreatedate(DateUtil.getNowDate());
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE);
		iPassbookPayService.add(pay);
	}

}
