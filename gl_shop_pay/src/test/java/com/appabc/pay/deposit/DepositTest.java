/**  
 * com.appabc.pay.deposit.DepositTest.java  
 *   
 * 2014年10月15日 下午2:30:00  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.deposit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.common.utils.DateUtil;
import com.appabc.pay.AbstractPayTest;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.enums.PurseInfo.DeviceType;
import com.appabc.pay.enums.PurseInfo.PayDirection;
import com.appabc.pay.enums.PurseInfo.PayWay;
import com.appabc.pay.enums.PurseInfo.TradeStatus;
import com.appabc.pay.enums.PurseInfo.TradeType;
import com.appabc.pay.service.local.IOfflinePayService;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.pay.service.local.IPassbookPayService;
import com.appabc.pay.util.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月15日 下午2:30:00
 */

public class DepositTest extends AbstractPayTest {

	@Autowired
	private IPassbookPayService iPassbookPayService;
	@Autowired
	private IOfflinePayService iOfflinePayService;
	@Autowired
	private IPassbookInfoService iPassbookInfoService;
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
		String topId = "PURSEOFFLINEPAYID2014101500001120650";
		String passId = "PASSID2014101500005104400";
		String payNo = "PAYNO0000000000001";
		float balance = 50000f;
		String payId = PKGenerator.generatorBusinessKeyByBid("PURSEPAYID");
		TOfflinePay bean = iOfflinePayService.query(topId);

		TPassbookPay entity = new TPassbookPay();
		entity.setId(payId);
		entity.setPassid(passId);
		entity.setOid(bean.getId());
		entity.setOtype(TradeType.DEPOSIT.getValue());
		entity.setPayno(payNo);
		//payEntity.setName("");
		entity.setAmount(balance);
		entity.setNeedamount(balance);
		entity.setDirection(PayDirection.INPUT.getValue());
		entity.setPaytype(PayWay.BANK_DEDUCT.getValue());
		entity.setPatytime(DateUtil.getNowDate());
		entity.setStatus(TradeStatus.SUCCESS.getValue());
		entity.setCreatedate(DateUtil.getNowDate());
		entity.setCreator(cid);
		entity.setDevices(DeviceType.COMPUTER.getValue());
		entity.setRemark("remark");
		iPassbookPayService.add(entity);
		
		bean.setAmount(balance);
		bean.setTotal(balance);
		bean.setDealer(cid);
		bean.setDealresult("success");
		bean.setPid(payId);
		bean.setDealtime(DateUtil.getNowDate());
		iOfflinePayService.modify(bean);
		
		TPassbookInfo t = iPassbookInfoService.query(passId);
		t.setAmount(balance);
		t.setRemark("deposit success.");
		iPassbookInfoService.modify(t);
		log.info(bean);
	}

}
