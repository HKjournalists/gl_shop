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
import com.appabc.bean.pvo.TBankInfo;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.enums.SyncInfo;
import com.appabc.datas.service.codes.IPublicCodesService;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.system.IBankInfoService;
import com.appabc.tools.utils.SystemParamsManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		
		SyncInfoBean si = new SyncInfoBean();
		
		String typeInfo  = request.getParameter("typeInfo"); // JSON格式
		
		if(StringUtils.isNotEmpty(typeInfo)){
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement =  jsonParser.parse(typeInfo);
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			
			JsonObject jsonObject;
			
			for(JsonElement je : jsonArray){
				jsonObject = je.getAsJsonObject();
				SyncDataBean sd = new SyncDataBean();
				
				if(jsonObject.get("type").getAsInt() == SyncInfo.SyncType.SYNC_TYPE_RIVER_SECTION.getVal()){ // 港口列表
					List<TPublicCodes> riverSection = publicCodesService.queryListByCode(SystemConstant.CODE_RIVER_SECTION);
					String time = spm.getString("RIVER_SECTION_TIME");  // 更新时间
					if(!jsonObject.get("timeStamp").getAsString().equals(time)){
						sd.setData(riverSection);
						sd.setTimeStamp(time);
						si.setRiverSection(sd);
					}
				}else if(jsonObject.get("type").getAsInt() == SyncInfo.SyncType.SYNC_TYPE_GOOD.getVal()){ // 商品大类
					List<TPublicCodes> goods = publicCodesService.queryListByCode(SystemConstant.CODE_GOODS);
					String time = spm.getString("GOODS_TIME");  // 更新时间
					if(!jsonObject.get("timeStamp").getAsString().equals(time)){
						sd.setData(goods);
						sd.setTimeStamp(time);
						si.setGoods(sd);
					}
				}else if(jsonObject.get("type").getAsInt() == SyncInfo.SyncType.SYNC_TYPE_GOOD_CHILDREN.getVal()){ // 商品子类
					List<TProductInfo> goodChild = this.productInfoService.queryForList(new TProductInfo());
					String time = spm.getString("GOOD_CHILD_TIME");  // 更新时间
					if(!jsonObject.get("timeStamp").getAsString().equals(time)){
						sd.setData(goodChild);
						sd.setTimeStamp(time);
						si.setGoodChild(sd);
					}
				}else if(jsonObject.get("type").getAsInt() == SyncInfo.SyncType.SYNC_TYPE_AREA.getVal()){ // 交易地域
					List<TPublicCodes> area = publicCodesService.queryListByCode(SystemConstant.CODE_AREA);
					String time = spm.getString("AREA_TIME");  // 更新时间
					if(!jsonObject.get("timeStamp").getAsString().equals(time)){
						sd.setData(area);
						sd.setTimeStamp(time);
						si.setArea(sd);
					}
				}else if(jsonObject.get("type").getAsInt() == SyncInfo.SyncType.SYNC_TYPE_BANK.getVal()){ // 平台支持银行
					List<TBankInfo> banks = bankInfoService.queryForList(new TBankInfo());
					String time = spm.getString("BANKS_TIME");  // 更新时间
					if(!jsonObject.get("timeStamp").getAsString().equals(time)){
						sd.setData(banks);
						sd.setTimeStamp(time);
						si.setBanks(sd);
					}
				}else if(jsonObject.get("type").getAsInt() == SyncInfo.SyncType.SYNC_TYPE_SYS_PARAM.getVal()){ // 保证金额度信息
					String time = spm.getString("SYS_PARAM_TIME");  // 更新时间
					if(!jsonObject.get("timeStamp").getAsString().equals(time)){
						sd.setData(getSysParam());
						sd.setTimeStamp(time);
						si.setSysParam(sd);
					}
				}
				
				
			}
		}else{
			List<TPublicCodes> riverSection = publicCodesService.queryListByCode(SystemConstant.CODE_RIVER_SECTION);
			String time = spm.getString("RIVER_SECTION_TIME");  // 更新时间
			SyncDataBean sd = new SyncDataBean();
			sd.setData(riverSection);
			sd.setTimeStamp(time);
			si.setRiverSection(sd);
			
			sd = new SyncDataBean();
			List<TPublicCodes> goods = publicCodesService.queryListByCode(SystemConstant.CODE_GOODS);
			time = spm.getString("GOODS_TIME");  // 更新时间
			sd.setData(goods);
			sd.setTimeStamp(time);
			si.setGoods(sd);
			
			sd = new SyncDataBean();
			List<TProductInfo> goodChild = this.productInfoService.queryForList(new TProductInfo());
			time = spm.getString("GOOD_CHILD_TIME");  // 更新时间
			sd.setData(goodChild);
			sd.setTimeStamp(time);
			si.setGoodChild(sd);
			
			sd = new SyncDataBean();
			List<TPublicCodes> area = publicCodesService.queryListByCode(SystemConstant.CODE_AREA);
			time = spm.getString("AREA_TIME");  // 更新时间
			sd.setData(area);
			sd.setTimeStamp(time);
			si.setArea(sd);
			
			sd = new SyncDataBean();
			List<TBankInfo> banks = bankInfoService.queryForList(new TBankInfo());
			time = spm.getString("BANKS_TIME");  // 更新时间
			sd.setData(banks);
			sd.setTimeStamp(time);
			si.setBanks(sd);
		
			sd = new SyncDataBean();
			time = spm.getString("SYS_PARAM_TIME");  // 更新时间
			sd.setData(getSysParam());
			sd.setTimeStamp(time);
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
		String pname = "BOND_ENTERPRISE";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);

		bi = new SysParam();
		pname = "BOND_PERSONAL";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		bi = new SysParam();
		pname = "BOND_SHIP_0_1000";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		bi = new SysParam();
		pname = "BOND_SHIP_1001_5000";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		bi = new SysParam();
		pname = "BOND_SHIP_5001_10000";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		bi = new SysParam();
		pname = "BOND_SHIP_10001_15000";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		/*bi = new SysParam();
		pname = "DISCOUNT_PERCENT";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		bi = new SysParam();
		pname = "SERVICE_PERCENT";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);*/
		
		bi = new SysParam();
		pname = "GUARANTY_PERCENT";
		bi.setPname(pname);
		bi.setPvalue(this.spm.getString(pname));
		biList.add(bi);
		
		return biList;
	}

}
