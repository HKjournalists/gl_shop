/**  
 * com.appabc.pay.urge.UrgeDepositTest.java  
 *   
 * 2015年9月11日 上午11:21:01  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.urge;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.pay.AbstractPayTest;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.local.IPassbookInfoService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月11日 上午11:21:01
 */

public class UrgeDepositTest  extends AbstractPayTest {

	@Autowired
	private IPassbookInfoService iPassbookInfoService;
	@Override
	@Test
	public void mainTest() {
//		getNewListForTask();
//		getInvalidListForTask();
	}

	public void getNewListForTask()
	{
		List<TPassbookInfo> passbookinfo=iPassbookInfoService.queryNewListForTask();
		System.out.println("NewListForTask列表是"+passbookinfo.size());	
	}
	public void getInvalidListForTask()
	{
		List<TPassbookInfo> passbookinfo=iPassbookInfoService.queryInvalidListForTask();
		System.out.println("InvalidListForTask列表是"+passbookinfo.size());	
	}

}
