/**
 *
 */
package com.appabc.datas.cms.company;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.enums.AuthRecordInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.CompanyInfo.PersonalAuthSex;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.company.ICompanyShippingService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.UserTokenManager;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import com.google.gson.Gson;

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
	@Autowired
	ICompanyRankingService iCompanyRankingService;
	@Autowired
	private MessageSendManager msm;
	@Autowired
	private UserTokenManager userTokenManager;
	@Autowired
	private IUserService userService;

	private int authid = 95; // 认证记录ID 61,62

	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		companyAuthAdd(); // 企业信息
//		shippingAdd(); //　船舶信息
//		personalAdd(); // 个人信息
//
//		authStatus(); // 认证

//		batchAuth(); // 批量认证


//		queryAuthCompanyInfo();
//		queryAuthCmpInfo();
	}

	public void queryAuthCompanyInfo(){
		CompanyAllInfo ca = this.companyInfoService.queryAuthCompanyInfo("201501260000018", null);
		Gson gson = new Gson();

		System.out.println(gson.toJson(ca));
	}

	public void queryAuthCmpInfo(){
		TCompanyInfo ci = this.companyInfoService.queryAuthCmpInfo("201501260000018");
		Gson gson = new Gson();

		System.out.println(gson.toJson(ci));
	}

	/**
	 * 企业认证信息添加
	 */
	@SuppressWarnings("deprecation")
	public void companyAuthAdd(){
		TCompanyAuth ca = new TCompanyAuth();
		ca.setAuthid(authid); // 认证记录ID
		ca.setAddress("深圳南山");
		ca.setCname("XX公司"+Calendar.getInstance().getTime().toLocaleString());
		ca.setCratedate(Calendar.getInstance().getTime());
		ca.setLperson("李四");
		ca.setOrgid("Orgid001");
		ca.setRdate("2005-02-02");
		ca.setCtype("私人企业");

		this.companyAuthService.add(ca);
	}

	@SuppressWarnings("deprecation")
	public void personalAdd(){
		TCompanyPersonal cp = new TCompanyPersonal();
		cp.setAuthid(authid);
		cp.setCpname("个人类型"+Calendar.getInstance().getTime().toLocaleString());
		cp.setCratedate(Calendar.getInstance().getTime());
		cp.setIdentification("111222333444555666");
		cp.setAddress("北京三里");
		cp.setRemark("aabb");
		cp.setSex(PersonalAuthSex.PERSONAL_SEX_M);
		cp.setUpdatedate(Calendar.getInstance().getTime());
		cp.setIssuingauth("XX公安分局");
		cp.setBirthday(Calendar.getInstance().getTime());
		cp.setEffstarttime(Calendar.getInstance().getTime());
		cp.setEffendtime(Calendar.getInstance().getTime());

		this.companyPersonalService.add(cp);
	}

	@SuppressWarnings("deprecation")
	public void shippingAdd(){

		TCompanyShipping cs = new TCompanyShipping();

		cs.setAuthid(authid);
		cs.setPregistry("船籍港001");
		cs.setSbusinesser("船老大");
		cs.setScreatetime("2009-01-13");
		cs.setSdeep(5.3f);
		cs.setSlength(22.3f);
		cs.setSload(5000f);
		cs.setSmateriall("钢材");
		cs.setSname("货轮"+Calendar.getInstance().getTime().toLocaleString());
		cs.setSno("DJ00334");
		cs.setSorg("深圳南山素菜批发市场检");
		cs.setSover(6.6f);
		cs.setSowner("船老大02");
		cs.setStotal(5020f);
		cs.setSwidth(6.4f);
		cs.setStype("s_0012");
		cs.setUpdatedate(Calendar.getInstance().getTime());

		this.companyShippingService.add(cs);
	}

	/**
	 * 批量认证
	 */
	public void batchAuth(){
//		for (int i = 79; i <= 90; i++) {
//			this.authid = i;
//			authStatus();
//		}
	}

	/**
	 * 标记认证状态
	 */
	public void authStatus(){
		TAuthRecord ca = this.authRecordService.query(authid);
		ca.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_YES);
		ca.setAuthresult("通过");
		ca.setAuthor("admin");
		ca.setAuthdate(Calendar.getInstance().getTime());

		this.authRecordService.modify(ca);

		TCompanyInfo ci = this.companyInfoService.query(ca.getCid());
		TUser user = this.userService.getUserByCid(ci.getId());

		ci.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);
		ci.setUpdatedate(Calendar.getInstance().getTime());
		ci.setUpdater("admin");
		ci.setCname("国立测试公司"+user.getPhone());
		this.companyInfoService.modify(ci);

		if(ci.getCtype() == CompanyType.COMPANY_TYPE_ENTERPRISE){
			companyAuthAdd();
		}else if(ci.getCtype() == CompanyType.COMPANY_TYPE_SHIP){
			shippingAdd();
		}else if(ci.getCtype() == CompanyType.COMPANY_TYPE_PERSONAL){
			personalAdd();
		}

		UserInfoBean ut = userTokenManager.getBeanByUsername(user.getUsername());
		if(ut != null){
			ut.setCid(ci.getId());
			ut.setCname(ci.getCname());
			ut.setCtype(ci.getCtype().getVal());
			userTokenManager.updateUserInfo(ut);
		}

		MessageInfoBean mi = new  MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH, authid+"", ci.getId(), SystemMessageContent.getMsgContentOfCompanyAuthYes());
		mi.setSendPushMsg(true);
		mi.setSendSystemMsg(true);
		msm.msgSend(mi);

	}



}
