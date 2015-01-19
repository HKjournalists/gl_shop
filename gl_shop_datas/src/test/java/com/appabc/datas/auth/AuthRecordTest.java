/**
 *
 */
package com.appabc.datas.auth;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.company.IAuthRecordService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月29日 下午2:19:33
 */
public class AuthRecordTest extends AbstractDatasTest{
	
	@Autowired
	private IAuthRecordService authRecordService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		testQueryListForPaginationByTypeAndAuthstatus();
	}

	
	public void testQueryListForPaginationByTypeAndAuthstatus(){
		
		QueryContext<TAuthRecord> qContext = new QueryContext<TAuthRecord>();
		AuthRecordType authType = AuthRecordType.AUTH_RECORD_TYPE_BANK;
		AuthRecordStatus authStatus = AuthRecordStatus.AUTH_STATUS_CHECK_YES;
		qContext = authRecordService.queryListForPaginationByTypeAndAuthstatus(qContext, authType, authStatus);
		List<TAuthRecord> arList  = qContext.getQueryResult().getResult();
		
		System.out.println(arList);
	}
	
}
