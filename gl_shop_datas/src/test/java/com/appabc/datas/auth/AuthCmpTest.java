/**
 *
 */
package com.appabc.datas.auth;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.bo.AuthCmpInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo.PersonalAuthSex;
import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.IAuthRecordService;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月10日 下午3:23:34
 */
public class AuthCmpTest extends AbstractDatasTest{

	@Autowired
	private IAuthRecordService authRecordService;


	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
		//authCompany(167);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		authCompany(65);
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public void authCompany(int authid) {
		AuthCmpInfo aci = new AuthCmpInfo();

		aci.setAuthid(authid+"");
		aci.setAuthor("admin");
		aci.setAuthresult("通过");
		aci.setAuthStatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);
		aci.setRemark("注：123");

		aci.setCompanyAuth(getCompanyAuthBean(authid));
		aci.setPersonalAuth(getPersonalBean(authid));
		aci.setShippingAuth(getShippingBean(authid));

		try {
			authRecordService.authCmp(aci);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 企业认证信息添加
	 */
	public TCompanyAuth getCompanyAuthBean(int authid){
		TCompanyAuth ca = new TCompanyAuth();
		ca.setAuthid(authid); // 认证记录ID
		ca.setAddress("深圳南山");
		ca.setCname("测试公司"+authid);
		ca.setCratedate(Calendar.getInstance().getTime());
		ca.setLperson("李四");
		ca.setOrgid("Orgid001");
		ca.setRdate("2005-02-02");
		ca.setCtype("私人企业");
		ca.setRegno("1212113132");
		return ca;
	}

	public TCompanyPersonal getPersonalBean(int authid){
		TCompanyPersonal cp = new TCompanyPersonal();
		cp.setAuthid(authid);
		cp.setCpname("测试个人"+authid);
		cp.setCratedate(Calendar.getInstance().getTime());
		cp.setIdentification("111222333444555666");
		cp.setAddress("北京三里");
		cp.setRemark("aabb");
		cp.setSex(PersonalAuthSex.PERSONAL_SEX_M);
		cp.setUpdatedate(Calendar.getInstance().getTime());
		cp.setIssuingauth("深圳市南山公安分局");
		cp.setBirthday(Calendar.getInstance().getTime());
		cp.setEffstarttime(Calendar.getInstance().getTime());
		cp.setEffendtime(Calendar.getInstance().getTime());

		return cp;
	}

	public TCompanyShipping getShippingBean(int authid){

		TCompanyShipping cs = new TCompanyShipping();

		cs.setAuthid(authid);
		cs.setPregistry("靖江港");
		cs.setSbusinesser("船老大");
		cs.setScreatetime("2009-01-13");
		cs.setSdeep(5.3f);
		cs.setSlength(22.3f);
		cs.setSload(5000f);
		cs.setSmateriall("钢材");
		cs.setSname("测试货轮"+authid);
		cs.setSno("DJ00334");
		cs.setSorg("深圳南山素菜批发市场检");
		cs.setSover(6.6f);
		cs.setSowner("船老大02");
		cs.setStotal(5020f);
		cs.setSwidth(6.4f);
		cs.setStype("s_0012");
		cs.setUpdatedate(Calendar.getInstance().getTime());

		return cs;
	}


}
