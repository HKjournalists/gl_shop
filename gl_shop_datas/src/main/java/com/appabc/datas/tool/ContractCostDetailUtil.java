package com.appabc.datas.tool;

import java.util.Calendar;
import java.util.Date;

import com.appabc.common.spring.BeanLocator;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
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
	
	/**
	 * @Description : 获取合同保证金费用
	 * @param cost
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static float getGuarantyCost(Float cost){
		float gp = getSystemParamsManager().getFloat(SystemConstant.GUARANTY_PERCENT);
		if(gp>0.0f){
			return RandomUtil.mulRound(cost, gp);
		}else{
			return RandomUtil.mulRound(cost, 0.05f);
		}
	}
	
	public static double getGuarantyCost(Double cost){
		double gp = getSystemParamsManager().getDouble(SystemConstant.GUARANTY_PERCENT);
		double guarantyCost = 0;
		if(gp>0.0){
			guarantyCost = RandomUtil.mulRound(cost, gp);
		}else{
			guarantyCost = RandomUtil.mulRound(cost, 0.05);
		}
		if(guarantyCost>3000d){
			return 3000d;
		} else {			
			return guarantyCost;
		}
	}
	
	/**
	 * @Description : 获取合同服务费
	 * @param cost
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static float getServiceCost(Float cost){
		float sp = getSystemParamsManager().getFloat(SystemConstant.SERVICE_PERCENT);
		if(sp>=0.0f){
			return RandomUtil.mulRound(cost, sp);
		}else{			
			return RandomUtil.mulRound(cost, 0.03f);
		}
	}
	
	public static double getServiceCost(Double cost){
		float sp = getSystemParamsManager().getFloat(SystemConstant.SERVICE_PERCENT);
		if(sp>=0.0f){
			return RandomUtil.mulRound(cost, sp);
		}else{			
			return RandomUtil.mulRound(cost, 0.03);
		}
	}
	
	public static void calcaulteMultiCost(Float s,Float z){
		
	}
	
	/**
	 * @Description : 获取合同起草确认的时限
	 * @param null
	 * @return int
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static int getContractDraftConfirmLimitNum(){
		int limitNum = getSystemParamsManager().getInt(SystemConstant.CONTRACT_DRAFR_LIMIT_TIME);
		if(limitNum <= 0){
			limitNum = 3;
		}
		return limitNum;
	}
	
	/**
	 * @Description : 获取合同起草确认的时限
	 * @param null
	 * @return double
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static double getContractDraftConfirmLimitNumWD(){
		double limitNum = getSystemParamsManager().getDouble(SystemConstant.CONTRACT_DRAFR_LIMIT_TIME);
		return limitNum;
	}
	
	/**
	 * @Description : 获取合同支付货款的时限
	 * @param null
	 * @return int
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static int getContractPayGoodsLimitNum(){
		int limitNum = getSystemParamsManager().getInt(SystemConstant.CONTRACT_PAY_GOODS_LIMIT_TIME);
		if(limitNum <= 0){
			limitNum = 48;
		}
		return limitNum;
	}
	
	/**
	 * @Description : 获取合同双方取消的时限
	 * @param null
	 * @return int
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static int getContractDuplexCancelLimitNum(){
		int limitNum = getSystemParamsManager().getInt(SystemConstant.CONTRACT_DUPLEX_CANCEL_LIMIT_TIME);
		if(limitNum <= 0){
			limitNum = 24;
		}
		return limitNum;
	}
	
	/**
	 * @Description : 获取合同确认收货的时限
	 * @param null
	 * @return int
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static int getContractConfirmReceiveGoodsLimitNum(){
		int limitNum = getSystemParamsManager().getInt(SystemConstant.CONTRACT_CONFIRMRECEIVEGOODS_LIMIT_TIME);
		if(limitNum <= 0){
			limitNum = 48;
		}
		return limitNum;
	}
	
	/**
	 * @Description : 获取合同评价的时限
	 * @param null
	 * @return int
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static int getContractEvaluatioinLimitNum(){
		int limitNum = getSystemParamsManager().getInt(SystemConstant.CONTRACT_EVALUATIOIN_LIMIT_TIME);
		if(limitNum <= 0){
			limitNum = 168;//7 Days
		}
		return limitNum;
	}
	
	/**
	 * @Description : //合同买家申请确认货款和货物后,卖家同意的时限
	 * @param null
	 * @return int
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static int getContractAgreeFinalEstimateLimitNum(){
		int limitNum = getSystemParamsManager().getInt(SystemConstant.CONTRACT_AGREEFINALESTIME_LIMIT_TIME);
		if(limitNum <= 0){
			limitNum = 72;
		}
		return limitNum;
	}
	
	/**
	 * @Description : 获取合同支付货款的截止时间(合同的买家付款期限由原来的48小时，改成倒前推1/5的时间，如5月1日—5月8日，取5月1日的时间1/5)
	 * @param source,target
	 * @return Date
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public static Date getPayGoodsLimitTime(Date source,Date target){
		if(source == null || target == null){
			return null;
		}
		long s = source.getTime();
		long t = target.getTime();
		if(t-s<0){
			return target;
		}
		long gap = (long)RandomUtil.divRound((t - s),5);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(s + gap);
		return calendar.getTime();
	}
	
	public static void main(String[] args) {
		Date source = DateUtil.getNowDate();
		Date target = DateUtil.getDateMoveByHours(source, 48);
		Date dest = getPayGoodsLimitTime(source,target);
		System.out.println(DateUtil.DateToStr(source, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		System.out.println(DateUtil.DateToStr(target, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		System.out.println(DateUtil.DateToStr(dest, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		Date d = DateUtil.getDateMoveByHours(source, 9.6);
		System.out.println(DateUtil.DateToStr(d, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		
		
		Date s = DateUtil.strToDate("2015-06-01 13:51:39", DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
		Date t = DateUtil.strToDate("2015-06-01 00:00:00", DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
		Date dt = getPayGoodsLimitTime(s, t);
		System.out.println(DateUtil.DateToStr(dt, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
	}
	
}
