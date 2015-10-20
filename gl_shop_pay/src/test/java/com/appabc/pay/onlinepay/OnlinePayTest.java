/**  
 * com.appabc.pay.onlinepay.OnlinePayTest.java  
 *   
 * 2015年3月3日 上午11:15:46  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.onlinepay;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayInstitution;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.RequestType;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.utils.DateUtil;
import com.appabc.pay.AbstractPayTest;
import com.appabc.pay.bean.TPayThirdOrgInfo;
import com.appabc.pay.bean.TPayThirdOrgRecord;
import com.appabc.pay.service.local.IPayThirdInfoService;
import com.appabc.pay.service.local.IPayThirdRecordService;
import com.appabc.pay.util.PaySystemConstant;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 上午11:15:46
 */

public class OnlinePayTest extends AbstractPayTest {

	@Autowired
	private IPayThirdInfoService iPayThirdInfoService;
	
	@Autowired
	private IPayThirdRecordService iPayThirdRecordService;
	
	@Autowired
	private PrimaryKeyGenerator PKGenerator;
	/* (non-Javadoc)  
	 * @see com.appabc.pay.AbstractPayTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		
	}
	
	public void testPayThirdOrgInfo(){
		double balance = 0.0f;
		Date now = DateUtil.getNowDate();
		
		TPayThirdOrgInfo entity = new TPayThirdOrgInfo();
		entity.setId(PKGenerator.getPKey(PaySystemConstant.PURSETHIRDCHECKID));
		entity.setPassid("Passid");
		entity.setOid("Oid");
		entity.setOtype(TradeType.DEPOSIT);
		entity.setPayno("payNo");
		entity.setName("name");
		entity.setAmount(balance);
		entity.setDirection(PayDirection.INPUT);
		entity.setPaytype(PayWay.BANK_DEDUCT);
		entity.setPatytime(now);
		entity.setStatus(TradeStatus.SUCCESS);
		entity.setDevices(DeviceType.MOBILE);
		entity.setRemark("reMark");
		entity.setTn("tn");
		entity.setTnTime("tnTime");
		entity.setQueryId("queryId");
		entity.setPayAccountNo("6225886556159902");
		entity.setPayInstitution(PayInstitution.ZHONGJINPAY);
		
		iPayThirdInfoService.add(entity);
	}
	
	public void testQueryPayThirdOrgInfo(){
		
		String id = "PURSETHIRDCHECKID2015042700000032133537";
		TPayThirdOrgInfo bean = iPayThirdInfoService.query(id);
		bean.setPayInstitution(PayInstitution.ZHONGJINPAY);
		bean.setPayAccountNo("6552778957858803");
		iPayThirdInfoService.modify(bean);
		iPayThirdInfoService.delete(id);
		
		String cid = "PURSETHIRDCHECKID2015042700000033133739";
		TPayThirdOrgInfo cbean = iPayThirdInfoService.query(cid);
		cbean.setPayInstitution(PayInstitution.UNIONPAY);
		cbean.setPayAccountNo("6552778957858803");
		iPayThirdInfoService.modify(cbean);
		iPayThirdInfoService.delete(cid);
	}
	
	public void testPayThirdOrgRecord(){
		Date now = DateUtil.getNowDate();
		TPayThirdOrgRecord bean = new TPayThirdOrgRecord();
		bean.setOid("2015030301594");
		bean.setParamsContent("2015-03-03 11:52:54 [INFO ] [main] [org.springframework.test.context.transaction.TransactionContext] - Began transaction (1) for test context [DefaultTestContext@4810881c testClass = OnlinePayTest, testInstance = com.appabc.pay.onlinepay.OnlinePayTest@35580b3a, testMethod = mainTest@OnlinePayTest, testException = [null], mergedContextConfiguration = [MergedContextConfiguration@27492832 testClass = OnlinePayTest, locations = '{classpath:/applicationContext-redis.xml, classpath:/applicationContext-datasource.xml, classpath:/applicationContext-tools.xml, classpath:/applicationContext-pay.xml, classpath*:spring-mvc.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{develop}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]; transaction manager [org.springframework.jdbc.datasource.DataSourceTransactionManager@33edfec4]; rollback [false]");
		bean.setType(RequestType.REQUEST.getVal());
		bean.setTradeTime(now);
		bean.setCreateTime(now);
		bean.setUpdateTime(now);
		bean.setCreator("creator");
		
		iPayThirdRecordService.add(bean);
	}

}
