package com.appabc.system.web;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.datas.service.company.ICompanyContactService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Nov 3, 2014 7:50:43 PM
 */
@Controller
@RequestMapping("/company/{cid}")
public class CompanyInfoController {

	@Autowired
	private ICompanyInfoService companyInfoService;

	@Autowired
	private ICompanyContactService companyContactService;

	@Autowired
	private IPassPayService passPayService;

	@ModelAttribute
	private void loadCompanyInfo(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		CompanyAllInfo companyInfo = companyInfoService.queryAuthCompanyInfo(cid, null);
		model.addAttribute("company", companyInfo);
	}

	@RequestMapping("/info/")
	public String info(@PathVariable String cid, Model model) {
        TCompanyContact qContact = new TCompanyContact();
        qContact.setCid(cid);
		List<TCompanyContact> contacts = companyContactService.queryForList(qContact);
		model.addAttribute("contacts", contacts);
		return "company/basic_info";
	}

	@RequestMapping("/auth/")
	public String auth(@PathVariable String cid, Model model) {
		return "company/auth";
	}

	@RequestMapping("/wallet/balance/")
	public String walletBalance(@PathVariable String cid, Model model) {
		TPassbookInfo balance = passPayService.getPurseAccountInfo(cid, PurseType.DEPOSIT);
		TPassbookInfo guaranty = passPayService.getPurseAccountInfo(cid, PurseType.GUARANTY);
		model.addAttribute("balance", balance);
		model.addAttribute("guaranty", guaranty);
		return "company/wallet_balance";
	}

	@RequestMapping("/wallet/trans/")
	public String walletTrans(@PathVariable String cid, Model model) {
		walletBalance(cid, model);
		return "company/trans_history";
	}

	@RequestMapping("/trans/history/")
	public String transHistory(@PathVariable String cid, Model model) {
		return "company/trans_history";
	}

	@RequestMapping("/trans/pending/")
	public String transPending(@PathVariable String cid, Model model) {
		return "company/trans_pending";
	}
}
