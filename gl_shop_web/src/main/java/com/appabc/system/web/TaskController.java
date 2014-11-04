package com.appabc.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Zou Xifeng 
 * @version     : 1.0
 * Create Date  : Oct 22, 2014 2:49:25 PM
 */
@Controller
@RequestMapping("/task")
public class TaskController {
	
	@RequestMapping("/")
	public String index() {
		return "task/index";
	}


}
