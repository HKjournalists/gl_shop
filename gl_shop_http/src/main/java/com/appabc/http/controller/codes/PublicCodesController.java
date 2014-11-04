/**
 *
 */
package com.appabc.http.controller.codes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.service.codes.IPublicCodesService;

/**
 * @Description : 公共代码集接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午3:27:49
 */
@Controller
@RequestMapping(value = "/codes")
public class PublicCodesController extends BaseController<TPublicCodes> {
	
	@Autowired
	private IPublicCodesService publicCodesService;
	
	/**
	 * 获取长江交易分段 http://localhost:8080/gl_shop_http/codes/reverSection
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRiverSection")
	public Object getRiverSection(HttpServletRequest request,HttpServletResponse response){
		
		TPublicCodes t = new TPublicCodes();
		t.setCode(SystemConstant.CODE_RIVER_SECTION);
		
		return this.publicCodesService.queryForList(t);
	}
	
	/**
	 * 获取商品大类 http://localhost:8080/gl_shop_http/codes/goods
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGoods")
	public Object getGoods(HttpServletRequest request,HttpServletResponse response){
		
		TPublicCodes t = new TPublicCodes();
		t.setCode(SystemConstant.CODE_GOODS);
		
		return this.publicCodesService.queryForList(t);
	}
	
	
}
