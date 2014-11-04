package com.appabc.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 2:33:07 PM
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

	@RequestMapping("/verify/")
	public String salesPromptVerify() {
		return "task/sales_prompt_verify";
	}

	@RequestMapping("/deposit/")
	public String salesPromptDeposit() {
		return "task/sales_prompt_deposit";
	}

	@RequestMapping("/pubinfo/")
	public String salesPublishInfoRequest() {
		return "task/sales_pub_info_request";
	}

	@RequestMapping("/payment/")
	public String salesPromptPayment() {
		return "task/sales_prompt_payment";
	}

	@RequestMapping("/realpay/")
	public String salesPromptRealPay() {
		return "task/sales_prompt_real_pay";
	}
}
