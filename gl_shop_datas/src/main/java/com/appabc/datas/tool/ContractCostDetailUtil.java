package com.appabc.datas.tool;

import com.appabc.common.spring.BeanLocator;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.tools.utils.SystemParamsManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月8日 下午4:49:53
 */

public class ContractCostDetailUtil {
	
	public static SystemParamsManager getSystemParamsManager(){
		return (SystemParamsManager)BeanLocator.getBean("SystemParamsManager");
	}
	
	public static float getGuarantyCost(String cid){
		ICompanyInfoService i = (ICompanyInfoService) BeanLocator.getBean("ICompanyInfoService");
		return i.getShouldDepositAmountByCid(cid);
	}
	
	public static float getGuarantyCost(Float cost){
		float gp = getSystemParamsManager().getFloat(SystemConstant.GUARANTY_PERCENT);
		if(gp>0.0f){
			return cost*gp;
		}else{			
			return cost*0.05f;
		}
	}
	
	public static float getServiceCost(Float cost){
		float sp = getSystemParamsManager().getFloat(SystemConstant.SERVICE_PERCENT);
		if(sp>0.0f){
			return cost*sp;
		}else{			
			return cost*0.03f;
		}
	}
	
	public static void calcaulteMultiCost(Float s,Float z){
		
	}
	
	public static void main(String[] args) {
		float f = 44321.123f;
		System.out.println(getGuarantyCost(f));
		System.out.println(getServiceCost(f));
	}
	
}
