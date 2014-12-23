package com.appabc.datas.tool;

import com.appabc.bean.pvo.TOrderFind;

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
	
}
