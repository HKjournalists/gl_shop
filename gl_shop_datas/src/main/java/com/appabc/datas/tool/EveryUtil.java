package com.appabc.datas.tool;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.service.contract.IContractInfoService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月12日 下午3:40:05
 */

public class EveryUtil {
	
	/**
	 * 克隆了询单信息 TOrderFind
	 * */
	public static TOrderFind cloneOrderFindInfo(TOrderFind tof){
		if(tof == null){
			return tof;
		}
		TOrderFind clone = new TOrderFind();
		clone.setId(tof.getId());
		clone.setCid(tof.getCid());
		clone.setTitle(tof.getTitle());
		clone.setType(tof.getType());
		clone.setAddresstype(tof.getAddresstype());
		clone.setPrice(tof.getPrice());
		clone.setTotalnum(tof.getTotalnum());
		clone.setNum(tof.getNum());
		clone.setStarttime(tof.getStarttime());
		clone.setEndtime(tof.getEndtime());
		clone.setMorearea(tof.getMorearea());
		clone.setArea(tof.getArea());
		clone.setCreater(tof.getCreater());
		clone.setCreatime(tof.getCreatime());
		clone.setLimitime(tof.getLimitime());
		clone.setStatus(tof.getStatus());
		clone.setRemark(tof.getRemark());
		clone.setParentid(tof.getParentid());
		clone.setUpdater(tof.getUpdater());
		clone.setUpdatetime(tof.getUpdatetime());
		clone.setOverallstatus(tof.getOverallstatus());
		clone.setPname(tof.getPname());
		clone.setPcode(tof.getPcode());
		clone.setPtype(tof.getPtype());
		clone.setUnit(tof.getUnit());
		clone.setMoreAreaInfos(tof.getMoreAreaInfos());
		clone.setMoreAreaList(tof.getMoreAreaList());
		return clone;
	}
	
	public static void convertAndEncryptCompanyEvaluationInfo(List<CompanyEvaluationInfo> result,IContractInfoService iContractInfoService,String loginUserId){
		String evaluationTips = MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_DEFAULT_EVALUATION_TIPS, Locale.forLanguageTag("datas"));
		String evaluationTipsStand = MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_MINI_EVALUATION_TIPS, Locale.forLanguageTag("datas"));
		String anonymityUName = MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_EVALUATION_ANONYMITYUNAME_TIPS, Locale.forLanguageTag("datas"));
		for(CompanyEvaluationInfo bean : result){
			if((!StringUtils.isEmpty(bean.getEvaluation())) && bean.getEvaluation().contains(evaluationTips)){
				bean.setEvaluation(evaluationTipsStand);
			}
			if(StringUtils.isEmpty(loginUserId)){
				bean.setCname(anonymityUName);
				continue;
			}
			boolean bool = iContractInfoService.isOldCustomer(bean.getCid(), loginUserId);
			if(!bool){
				bean.setCname(anonymityUName);
				continue;
			}
		}
			
	}
	
	public static boolean EqNumNotHalfBetweenAandB(double totalA,double finalB){
		if(totalA > 0.0 && finalB > 0.0){
			boolean f = finalB < RandomUtil.mulRound(totalA, 0.5);
			boolean f1 = finalB > totalA;
			return f || f1;
		}else{
			return false;
		}
	}
	
}
