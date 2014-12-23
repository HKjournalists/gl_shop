/**
 *
 */
package com.appabc.http.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.SyncDataBean;
import com.appabc.bean.bo.SyncInfoBean;
import com.appabc.bean.bo.SysParam;
import com.appabc.bean.enums.SyncInfo.SyncType;
import com.appabc.bean.pvo.TBankInfo;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.service.codes.IPublicCodesService;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.system.IBankInfoService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.tools.utils.SystemParamsManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * @Description : 同步接口信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月29日 下午2:16:12
 */
@Controller
@RequestMapping(value="/sync")
public class SyncController extends BaseController<SyncInfoBean> {
	
	@Autowired
	private IPublicCodesService publicCodesService;
	@Autowired
	private IBankInfoService bankInfoService;
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private SystemParamsManager spm;
	
	
	/**
	 * 获取同步信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo")
	public Object getSyncInfos(HttpServletRequest request,HttpServletResponse response) {
		/**
		 * 同步业务，需要按不同业务进行分类，（1，2，3，4，5等）
		 * 同步判断，客户端提交分类和对应的时间戳，服务器更新相应数据时同时更新【系统参数表T_SYSTEM_PARAMS】中的分类时间戳，2个时间戳进行比较，不同即更新
		 */
		
		boolean isSyncRiverSection = false;
		boolean isSyncGoodsType = false;
		boolean isSyncGoods = false;
		boolean isSyncArea = false;
		boolean isSyncBanks = false;
		boolean isSyncSysParams = false;
		
		SyncInfoBean si = new SyncInfoBean();
		String typeInfo  = request.getParameter("typeInfo"); // JSON格式
		
		if(StringUtils.isNotEmpty(typeInfo)){
			try {
				JsonParser jsonParser = new JsonParser();
				JsonElement jsonElement =  jsonParser.parse(typeInfo);
				JsonArray jsonArray = jsonElement.getAsJsonArray();
				JsonObject jsonObject;
				
				for(JsonElement je : jsonArray){
					jsonObject = je.getAsJsonObject();
					JsonElement jeType = jsonObject.get("type");
					if(jeType.isJsonNull()){
						return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL, "sync type is null");
					}
					SyncType syncType = SyncType.enumOf(jsonObject.get("type").getAsInt());
					if(syncType == null){
						return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL, "sync type error；type="+jeType);
					}
					JsonElement jeTimeStamp = jsonObject.get("timeStamp");
					String timeStamp = "";
					if(!jeTimeStamp.isJsonNull()){
						timeStamp = jeTimeStamp.getAsString();
					}
					
					if(syncType.equals(SyncType.SYNC_TYPE_RIVER_SECTION)
							&& !timeStamp.equals(spm.getString(SystemConstant.SYNC_RIVER_SECTION_TIME))){ // 港口列表
						isSyncRiverSection = true;
					}else if(syncType.equals(SyncType.SYNC_TYPE_GOOD_TYPE)
							&& !timeStamp.equals(spm.getString(SystemConstant.SYNC_GOODS_TYPE_TIME))){ // 商品类型
						isSyncGoodsType = true;
					}else if(syncType.equals(SyncType.SYNC_TYPE_GOODS)
							&& !timeStamp.equals(spm.getString(SystemConstant.SYNC_GOODS_TIME))){ // 商品
						isSyncGoods = true;
					}else if(syncType.equals(SyncType.SYNC_TYPE_AREA)
							&& !timeStamp.equals(spm.getString(SystemConstant.SYNC_AREA_TIME))){ // 交易地域
						isSyncArea = true;
					}else if(syncType.equals(SyncType.SYNC_TYPE_BANK)
							&& !timeStamp.equals(spm.getString(SystemConstant.SYNC_BANKS_TIME))){ // 平台支持银行
						isSyncBanks = true;
					}else if(syncType.equals(SyncType.SYNC_TYPE_SYS_PARAM)
							&& !timeStamp.equals(spm.getString(SystemConstant.SYNC_SYS_PARAM_TIME))){ // 系统参数
						isSyncSysParams = true;
					}
						
				}
					
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
			}
		}else{
			isSyncRiverSection = true;
			isSyncGoodsType = true;
			isSyncGoods = true;
			isSyncArea = true;
			isSyncBanks = true;
			isSyncSysParams = true;
		}
		
		SyncDataBean sd;
		
		if(isSyncRiverSection){
			sd = new SyncDataBean();
			sd.setData(publicCodesService.queryListByCode(SystemConstant.CODE_RIVER_SECTION));
			sd.setTimeStamp(spm.getString(SystemConstant.SYNC_RIVER_SECTION_TIME)); // 更新时间
			si.setRiverSection(sd);
		}
		
		if(isSyncGoodsType){
			sd = new SyncDataBean();
			sd.setData(publicCodesService.queryListByCode(SystemConstant.CODE_GOODS_TYPE));
			sd.setTimeStamp(spm.getString(SystemConstant.SYNC_GOODS_TYPE_TIME)); // 更新时间
			si.setGoods(sd);
		}
		
		if(isSyncGoods){
			sd = new SyncDataBean();
			sd.setData(this.productInfoService.queryProductAllInfoForList());
			sd.setTimeStamp(spm.getString(SystemConstant.SYNC_GOODS_TIME)); // 更新时间
			si.setGoodChild(sd);
		}
		
		if(isSyncArea){
			sd = new SyncDataBean();
			sd.setData(publicCodesService.queryListByCode(SystemConstant.CODE_AREA));
			sd.setTimeStamp(spm.getString(SystemConstant.SYNC_AREA_TIME)); // 更新时间
			si.setArea(sd);
		}
		
		if(isSyncBanks){
			sd = new SyncDataBean();
			sd.setData(bankInfoService.queryForList(new TBankInfo()));
			sd.setTimeStamp(spm.getString(SystemConstant.SYNC_BANKS_TIME)); // 更新时间
			si.setBanks(sd);
		}
		
		if(isSyncSysParams){
			sd = new SyncDataBean();
			sd.setData(getSysParam());
			sd.setTimeStamp(spm.getString(SystemConstant.SYNC_SYS_PARAM_TIME)); // 更新时间
			si.setSysParam(sd);
		}
		
		return si;
	}
	
	
	/**  
	 * 企业应缴纳保证金参数配置信息
	 * @exception   
	 * @since  1.0.0  
	*/
	private List<SysParam> getSysParam(){
		
		List<SysParam> biList = new ArrayList<SysParam>();
		SysParam bi;
		bi = new SysParam();
		bi.setPname(SystemConstant.BOND_ENTERPRISE);
		bi.setPvalue(this.spm.getString(SystemConstant.BOND_ENTERPRISE));
		biList.add(bi);

		bi = new SysParam();
		bi.setPname(SystemConstant.BOND_PERSONAL);
		bi.setPvalue(this.spm.getString(SystemConstant.BOND_PERSONAL));
		biList.add(bi);
		
		bi = new SysParam();
		bi.setPname(SystemConstant.BOND_SHIP_0_1000);
		bi.setPvalue(this.spm.getString(SystemConstant.BOND_SHIP_0_1000));
		biList.add(bi);
		
		bi = new SysParam();
		bi.setPname(SystemConstant.BOND_SHIP_1001_5000);
		bi.setPvalue(this.spm.getString(SystemConstant.BOND_SHIP_1001_5000));
		biList.add(bi);
		
		bi = new SysParam();
		bi.setPname(SystemConstant.BOND_SHIP_5001_10000);
		bi.setPvalue(this.spm.getString(SystemConstant.BOND_SHIP_5001_10000));
		biList.add(bi);
		
		bi = new SysParam();
		bi.setPname(SystemConstant.BOND_SHIP_10001_15000);
		bi.setPvalue(this.spm.getString(SystemConstant.BOND_SHIP_10001_15000));
		biList.add(bi);
		
		bi = new SysParam();
		bi.setPname(SystemConstant.GUARANTY_PERCENT);
		bi.setPvalue(this.spm.getString(SystemConstant.GUARANTY_PERCENT));
		biList.add(bi);
		
		return biList;
	}

}
