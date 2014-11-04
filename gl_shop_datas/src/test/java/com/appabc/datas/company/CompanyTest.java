/**
 *
 */
package com.appabc.datas.company;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.enums.AuthRecordInfo;
import com.appabc.datas.enums.CompanyInfo;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyShippingService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 上午9:55:01
 */
public class CompanyTest extends AbstractDatasTest{
	
	@Autowired
	private ICompanyAuthService companyAuthService;
	@Autowired
	private IAuthRecordService authRecordService;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private ICompanyShippingService companyShippingService;
	@Autowired
	ICompanyPersonalService companyPersonalService;
	
	private int authid = 13; // 认证记录ID
	
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		companyAuthAdd();
//		shippingAdd();
//		personalAdd();
//		
//		authStatus();
	}
	
	/**
	 * 企业认证信息添加
	 */
	public void companyAuthAdd(){
		TCompanyAuth ca = new TCompanyAuth();
		ca.setAuthid(authid); // 认证记录ID
		ca.setAddress("深圳南山");
		ca.setCname("XX公司");
		ca.setCratedate(Calendar.getInstance().getTime());
		ca.setLperson("法人李四");
		ca.setOrgid("oggogg111");
		ca.setRdate("2005-2-2");
		
		this.companyAuthService.add(ca);
	}
	
	public void personalAdd(){
		TCompanyPersonal cp = new TCompanyPersonal();
		cp.setAuthid(authid);
		cp.setCpname("孙六");
		cp.setCratedate(Calendar.getInstance().getTime());
		cp.setIdentification("111222333444555666");
		cp.setOrigo("xx公安局");
		cp.setRemark("aabb");
		cp.setSex(1);
		this.companyPersonalService.add(cp);
	}
	
	public void shippingAdd(){
		
		TCompanyShipping cs = new TCompanyShipping();
		
		cs.setAuthid(authid);
		cs.setPregistry("船籍港001");
		cs.setSbusinesser("船老大");
		cs.setScreatetime("2009-01-13");
		cs.setSdeep(5.3f);
		cs.setSlength(22.3f);
		cs.setSload(5000f);
		cs.setSmateriall(3.3f);
		cs.setSname("加大轮");
		cs.setSno("DJ00334");
		cs.setSorg("深圳南山素菜批发市场检");
		cs.setSover(6.6f);
		cs.setSowner("船老大02");
		cs.setStotal(5020f);
		cs.setSwidth(6.4f);
		cs.setStype("s_0012");
		
		this.companyShippingService.add(cs);
	}

	/**
	 * 标记认证状态
	 */
	public void authStatus(){
		TAuthRecord ca = this.authRecordService.query(authid);
		ca.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_YES.getVal());
		ca.setAuthresult("通过");
		ca.setAuthor("user000003");
		ca.setAuthdate(Calendar.getInstance().getTime());
		
		this.authRecordService.modify(ca);
		
		TCompanyInfo ci = this.companyInfoService.query(ca.getCid());
		ci.setAuthstatus(CompanyInfo.CompanyAuthStatus.AUTH_STATUS_YES.getVal());
		ci.setUpdatedate(Calendar.getInstance().getTime());
		ci.setUpdater("user000003");
		ci.setCname("国立123公司");
		this.companyInfoService.modify(ci);
	}
	
	

}
