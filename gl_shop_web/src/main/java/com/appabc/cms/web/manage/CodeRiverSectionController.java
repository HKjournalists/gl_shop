package com.appabc.cms.web.manage;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.cms.web.AbstractBaseController;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.service.codes.IPublicCodesService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Administrator
 * @version     : 1.0
 * Create Date  : 2015年8月20日 下午4:11:26
 */
@Controller
@RequestMapping("/manage/code/riversection")
public class CodeRiverSectionController extends AbstractBaseController {
	
	@Autowired
	IPublicCodesService publicCodesService;
	
	/**
	 * 获取行情地域(河流分段)
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
    public String queryRiverSectionList(ModelMap model) {
		
		List<TPublicCodes> rsList = this.publicCodesService.queryListInNoDel(SystemConstant.CODE_RIVER_SECTION);
		
		model.addAttribute("rsList", rsList);
		
		int lastNum = 0;
		if(CollectionUtils.isNotEmpty(rsList) && rsList.get(rsList.size()-1) != null){
			lastNum = rsList.get(rsList.size()-1).getOrderno();
		}
		
		model.addAttribute("lastNum", lastNum);
		return "/manage/river_section_list";
	}
	
	/**
	 * 添加
	 * @param model
	 * @param riverSectionName
	 * @param ishidden
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/add")
	public String addRiverSection(ModelMap model, String riverSectionName, int ishidden, int orderno) {
		
		TPublicCodes pc = new TPublicCodes();
		pc.setCode(SystemConstant.CODE_RIVER_SECTION);
		pc.setIshidden(ishidden);
		pc.setName(riverSectionName);
		pc.setOrderno(orderno);
		pc.setPcode("0");
		pc.setUpdater(getCurrentUser().getUserName());
		pc.setUpdatetime(Calendar.getInstance().getTime());
		pc.setVal(nextValOfRiverSection());
		
		this.publicCodesService.add(pc);
		
		return "redirect:/manage/code/riversection/list";
	}
	
	/**
	 * 排序保存
	 * @param model
	 * @param riverSectionName
	 * @param ishidden
	 * @param orderno
	 * @return
	 */
	@RequestMapping("/update_orderno")
	public String updateOrdernoRiverSection(ModelMap model, int ids[], int ordernos[]) {
		for(int i=0; i<ids.length; i++){
			TPublicCodes entity = this.publicCodesService.query(ids[i]);
			if(entity != null){
				entity.setOrderno(ordernos[i]);
				this.publicCodesService.modify(entity);
			}
		}
		return "redirect:/manage/code/riversection/list";
	}
	
	/**
	 * 状态变更
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/mdyStatus")
	public String modifyStatusRiverSection(ModelMap model, Integer id, Integer status){
		if(id != null && status != null){
			TPublicCodes entity = this.publicCodesService.query(id);
			if(entity != null && !status.equals(entity.getIshidden())){
				entity.setIshidden(status);
				this.publicCodesService.modify(entity);
			}
		}
		return "redirect:/manage/code/riversection/list";
	}
	
	/**
	 * 获取下一个值
	 * @return
	 */
	public String nextValOfRiverSection(){
		
		String prefix = "RS";
		
		TPublicCodes entity = new TPublicCodes();
		entity.setCode(SystemConstant.CODE_RIVER_SECTION);
		
		String maxVal = this.publicCodesService.getMaxValue(entity);
		
		if(StringUtils.isNotEmpty(maxVal)){
			int v = Integer.parseInt(maxVal.replace(prefix, ""));
			return prefix+(v+1);
		}else{
			return prefix+"1";
		}
	}
	
}
