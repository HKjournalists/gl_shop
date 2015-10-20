/**
 *
 */
package com.appabc.datas.auth;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyShippingService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月10日 下午4:23:50
 */
public class CmpAuthInfo extends AbstractDatasTest{
	
	@Autowired
	private ICompanyPersonalService companyPersonalService;
	@Autowired
	private ICompanyAuthService companyAuthService;
	@Autowired
	private ICompanyShippingService companyShippingService;
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
//		queryPersonal();
//		queryCmpAuth();
//		queryShipping();		
	}
	
	public void queryPersonal(){
		TCompanyPersonal entity = this.companyPersonalService.queryByAuthid(77);
		System.out.println(entity);
	}
	
	public void queryCmpAuth(){
		TCompanyAuth entity = this.companyAuthService.queryByAuthid(34);
		System.out.println(entity);
	}
	
	public void queryShipping(){
		TCompanyShipping entity = this.companyShippingService.queryByAuthid(71);
		System.out.println(entity);
	}

}
