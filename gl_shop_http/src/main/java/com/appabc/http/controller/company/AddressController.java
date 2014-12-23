/**
 *
 */
package com.appabc.http.controller.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.company.ICompanyAddressService;
import com.appabc.http.utils.HttpApplicationErrorCode;

/**
 * @Description : 企业卸货地址CONTROLLER
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 下午6:44:42
 */
@Controller
@RequestMapping(value="/copn/address")
public class AddressController extends BaseController<TCompanyAddress> {
	
	@Autowired
	private ICompanyAddressService companyAddressService;
	
	/**
	 * 企业卸货地址添加
	 * @param request
	 * @param response
	 * @param caBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	public Object addCompanyAddress(HttpServletRequest request,HttpServletResponse response, 
			TCompanyAddress caBean) {
		
		if(StringUtils.isEmpty(caBean.getCid())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "企业编号不能为空");
		}else if(StringUtils.isEmpty(caBean.getAddress())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "地址不能为空");
		}
		
		try {
			this.companyAddressService.add(caBean);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return buildSuccessResult("添加成功", "");
		
	}
	
	/**
	 * 卸货地址图片修改
	 * @param request
	 * @param response
	 * @param caBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mdy",method=RequestMethod.POST)
	public Object modifyCompanyAddress(HttpServletRequest request,HttpServletResponse response, 
			TCompanyAddress caBean) {
		
		if(StringUtils.isEmpty(caBean.getId())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "ID不能为空");
		}else if(StringUtils.isEmpty(caBean.getAddress())){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "地址不能为空");
		}
		
		TCompanyAddress entity = this.companyAddressService.query(caBean.getId());
		if(entity != null){
			
			entity.setAddress(caBean.getAddress());
			entity.setAddressImgIds(caBean.getAddressImgIds());
			entity.setDeep(caBean.getDeep());
			entity.setLatitude(caBean.getLatitude());
			entity.setLongitude(caBean.getLongitude());
			entity.setRealdeep(caBean.getRealdeep());
			
			try {
				this.companyAddressService.modify(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
			}
			
			return buildSuccessResult("修改成功");
		}
		
		return buildSuccessResult("修改失败,数据不存在");
		
	}
	
	/**
	 * 企业卸货地址添加
	 * @param request
	 * @param response
	 * @param caBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del",method=RequestMethod.POST)
	public Object deleteCompanyAddress(HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "ID不能为空");
		}
		
		this.companyAddressService.delete(id);
		
		return buildSuccessResult("删除成功", "");
		
	}
	
	/**
	 * 获取企业卸货地址列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList",method=RequestMethod.GET)
	public Object getList(HttpServletRequest request,HttpServletResponse response) {
		
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "ID不能为空");
		}
		
		TCompanyAddress entity  = new TCompanyAddress();
		entity.setCid(cid);
		
		List<TCompanyAddress> list = this.companyAddressService.queryForListHaveImgs(entity);
		
		return buildFilterResultWithString(list);
		
	}
	
	/**
	 * 获取卸货地址详情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo",method=RequestMethod.GET)
	public Object getInfo(HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "ID不能为空");
		}
		
		TCompanyAddress ca = this.companyAddressService.query(id);
		
		return buildFilterResultWithBean(ca);
		
	}
	
	/**
	 * 设置为默认地址
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setDefault",method=RequestMethod.POST)
	public Object setDefault(HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			return buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "ID不能为空");
		}
		
		this.companyAddressService.setDefault(id);
		
		return buildSuccessResult("设置成功", "");
		
	}

}
