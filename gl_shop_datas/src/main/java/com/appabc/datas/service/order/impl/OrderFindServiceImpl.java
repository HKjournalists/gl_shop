/**
 *
 */
package com.appabc.datas.service.order.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.MoreAreaInfos;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.order.IOrderAddressDao;
import com.appabc.datas.dao.order.IOrderFindDao;
import com.appabc.datas.dao.order.IOrderProductInfoDao;
import com.appabc.datas.dao.order.IOrderProductPropertyDao;
import com.appabc.datas.enums.FileInfo;
import com.appabc.datas.enums.FileInfo.FileOType;
import com.appabc.datas.enums.OrderFindInfo;
import com.appabc.datas.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.datas.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.datas.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月14日 下午5:34:01
 */
@Service(value="IOrderFindService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderFindServiceImpl implements IOrderFindService {
	
	@Autowired
	private IOrderFindDao orderFindDao;
	@Autowired
	private IOrderAddressDao orderAddressDao;
	@Autowired
	private ICompanyAddressDao companyAddressDao;
	@Autowired
	private IOrderProductInfoDao orderProductInfoDao;
	@Autowired
	private IOrderProductPropertyDao orderProductPropertyDao;
	@Autowired
	private IContractInfoDAO contractInfoDao;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private ICompanyInfoService companyInfoService;

	public void add(TOrderFind entity) {
		this.orderFindDao.save(entity);
	}

	public void modify(TOrderFind entity) {
		this.orderFindDao.update(entity);
	}

	public void delete(TOrderFind entity) {
	}

	public void delete(Serializable id) {
		this.orderFindDao.delete(id);
	}

	public TOrderFind query(TOrderFind entity) {
		return null;
	}

	public TOrderFind query(Serializable id) {
		return this.orderFindDao.query(id);
	}

	public List<TOrderFind> queryForList(TOrderFind entity) {
		return this.orderFindDao.queryForList(entity);
	}

	public List<TOrderFind> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TOrderFind> queryListForPagination(
			QueryContext<TOrderFind> qContext) {
		return this.orderFindDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)发布询单
	 * @see com.appabc.datas.service.order.IOrderFindService#orderPublish(com.appabc.bean.pvo.TOrderFind, com.appabc.bean.pvo.TOrderProductInfo, java.lang.String)
	 */
	public void orderPublish(TOrderFind ofBean, TOrderProductInfo opiBean, String addressid) {
		
		ofBean.setStatus(OrderStatusEnum.ORDER_STATUS_YES.getVal());
		if(ofBean.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())) { // 多地域发布，删除主询单中的地域和价格
			ofBean.setArea(null);
			ofBean.setPrice(null);
		}
		ofBean.setNum(ofBean.getTotalnum()); // 新发布的信息总量等于当前量
		ofBean.setOverallstatus(OrderFindInfo.OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE.getVal());
		this.orderFindDao.save(ofBean); // 询单新增
		
		opiBean.setFid(ofBean.getId());
		this.orderProductInfoDao.save(opiBean); // 询单商品信息新增

		/*********商品属性解析****************************************/
		if(StringUtils.isNotEmpty(opiBean.getProductPropertys())){
			
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement =  jsonParser.parse(opiBean.getProductPropertys());
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			TOrderProductProperty oppBean = null;
			
			Gson gson = new Gson();
			for(JsonElement je : jsonArray){ // 商品属性保存
				oppBean = gson.fromJson(je, TOrderProductProperty.class);
				oppBean.setStatus("0");
				oppBean.setPpid(Integer.valueOf(opiBean.getId()));
				this.orderProductPropertyDao.save(oppBean); // 商品属性新增
			}
			
			TUploadImages ui = null;
			// 商品图片信息更新
			if(opiBean.getProductImgIds() != null && !opiBean.getProductImgIds().trim().equals("")){
				String[] productImgIds = opiBean.getProductImgIds().split(",");
				for(String imgid : productImgIds){
					ui = this.uploadImagesService.query(imgid);
					ui.setOid(opiBean.getId());
					ui.setOtype(FileOType.FILE_OTYPE_PRODUCT.getVal());
					this.uploadImagesService.modify(ui);
				}
			}
		
		}
		// 询单卸货地址存储
		copyBeanOfCompanyAddressToOrderAddress(addressid, ofBean.getId());
		
		// 多地发布判断
		if(ofBean.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())){
			
			/*******多地域与价格信息解析***************************/
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement =  jsonParser.parse(ofBean.getMoreAreaInfos());
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			
			String parentId = ofBean.getId();
			for(JsonElement je : jsonArray){
				ofBean.setId(null);
				ofBean.setParentid(parentId);
				ofBean.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO.getVal());
				
				JsonObject jsonObject = je.getAsJsonObject();
				ofBean.setPrice(jsonObject.get("price").getAsFloat());
				ofBean.setArea(jsonObject.get("area").getAsString());
				
				this.orderFindDao.save(ofBean); //  子询单保存
			}
		}
	}

	/**
	 * 询单详情
	 * @param fid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid) {
		OrderAllInfor oai = this.orderFindDao.queryInfoById(fid);
		if(oai != null){
			/*****商品属性查询**********************/
			TOrderProductProperty  opp = new TOrderProductProperty();
			opp.setPpid(oai.getOpiid());
			List<TOrderProductProperty> oppList = orderProductPropertyDao.queryForList(opp);
			oai.setOppList(oppList);
			/*****子询单查询************************/
			if(oai.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())) {
				List<MoreAreaInfos> moreAreaInfos = new ArrayList<MoreAreaInfos>();
				TOrderFind entity = new TOrderFind();
				entity.setParentid(oai.getId());
				List<TOrderFind> ofList = this.orderFindDao.queryForList(entity);
				for(int i=0; i<ofList.size(); i++){
					MoreAreaInfos mai = new MoreAreaInfos();
					mai.setArea(ofList.get(i).getArea());
					mai.setPrice(ofList.get(i).getPrice());
					
					moreAreaInfos.add(mai);
				}
				
				oai.setMoreAreaInfos(moreAreaInfos);
			}
			
			//[{\"price\":\"12\",\"area\":\"A_003\"},{\"price\":\"14\",\"area\":\"A_006\"}]
			
			oai.setEvaluationInfo(this.companyInfoService.getEvaluationByCid(oai.getCid()));
		}
		return oai;
	}
	
	/* (non-Javadoc)取消询单
	 * @see com.appabc.datas.service.order.IOrderFindService#cancel(java.lang.String, java.lang.String)
	 */
	public String cancel(String fid, String userid) {
		
		TOrderInfo  oi = new TOrderInfo();
		oi.setFid(fid);
		List<TOrderInfo> oiList = this.contractInfoDao.queryForList(oi);
		if(oiList!=null && oiList.size()>0 && oiList.get(0)!=null){
			return "该询单已关联合同，不能取消";
		}else{
			TOrderFind of = this.orderFindDao.query(fid);
			if(of != null && of.getStatus()==OrderStatusEnum.ORDER_STATUS_YES.getVal()){
				if(of.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())){ // 多地发布
					
					TOrderFind ofQuery = new TOrderFind();
					ofQuery.setParentid(fid);
					List<TOrderFind> ofList = this.orderFindDao.queryForList(ofQuery);
					for(TOrderFind ofcBean : ofList){ // 子询单取消
						ofcBean.setUpdater(userid);
						this.doCancel(ofcBean);
					}
				}
				// 询单或主询单取消
				of.setUpdater(userid);
				this.doCancel(of);
				
			}else{
				return "该信息不存在或已处理,fid:"+fid;
			}
			
			return "已取消";
		}
		
	}
	
	/**
	 * 执行取消操作
	 * @param entity
	 */
	private void doCancel(TOrderFind entity){
		Date now = new Date();
		
		entity.setStatus(OrderStatusEnum.ORDER_STATUS_CANCEL.getVal());
		entity.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID.getVal());
		entity.setEndtime(now);
		entity.setUpdatetime(now);
		
		this.orderFindDao.update(entity);
	}
	
	/* (non-Javadoc)询单更新
	 * @see com.appabc.datas.service.order.IOrderFindService#updateOrderAllInfo(com.appabc.bean.pvo.TOrderFind, com.appabc.bean.pvo.TOrderAddress, com.appabc.bean.pvo.TOrderProductInfo)
	 */
	public void updateOrderAllInfo(TOrderFind ofBean, TOrderAddress oaBean,
			TOrderProductInfo opiBean) {
		TOrderFind of = this.orderFindDao.query(ofBean.getId());
		if(of != null){
			of.setTitle(ofBean.getTitle());
			of.setAddresstype(ofBean.getAddresstype());
			of.setPrice(ofBean.getPrice());
			of.setTotalnum(ofBean.getTotalnum());
			of.setStarttime(ofBean.getStarttime());
			of.setMorearea(ofBean.getMorearea());
			of.setArea(ofBean.getArea());
			of.setLimitime(ofBean.getLimitime());
			of.setRemark(ofBean.getRemark());
			of.setUpdater(ofBean.getUpdater());
			of.setUpdatetime(Calendar.getInstance().getTime());
			this.orderFindDao.update(of);// 更新询单基本信息
			
			TUploadImages  ui = null;
			// 商品图片信息更新
			if(opiBean.getProductImgIds() != null && !opiBean.getProductImgIds().trim().equals("")){
				String[] productImgIds = opiBean.getProductImgIds().split(",");
				for(String imgid : productImgIds){
					ui = this.uploadImagesService.query(imgid);
					ui.setOid(opiBean.getId());
					ui.setOtype(FileOType.FILE_OTYPE_PRODUCT.getVal());
					this.uploadImagesService.modify(ui);
				}
			}
			
			/****商品信息处理****************************/
			TOrderProductInfo opi = new TOrderProductInfo();
			opi.setFid(of.getId());
			List<TOrderProductInfo> opiList = this.orderProductInfoDao.queryForList(opi);
			if(opiList != null && opiList.size()>0 && opiList.get(0) != null) {
				opi = opiList.get(0);
				opi.setPid(opiBean.getPid());
				opi.setPname(opiBean.getPname());
				opi.setPtype(opiBean.getPtype());
				opi.setPsize(opiBean.getPsize());
				opi.setPcolor(opiBean.getPcolor());
				opi.setPaddress(opiBean.getPaddress());
				opi.setUnit(opiBean.getUnit());
				opi.setPremark(opiBean.getPremark());
				
				this.orderProductInfoDao.update(opi);
				
				/*****商品属性处理**********************/
				TOrderProductProperty entity = new TOrderProductProperty();
				entity.setPpid(Integer.parseInt(opi.getId()));
				this.orderProductPropertyDao.delete(entity); // 删除旧属性
				
				if(StringUtils.isNotEmpty(opiBean.getProductPropertys())){
					JsonParser jsonParser = new JsonParser();
					JsonElement jsonElement =  jsonParser.parse(opiBean.getProductPropertys());
					JsonArray jsonArray = jsonElement.getAsJsonArray();
					TOrderProductProperty oppBean = null;
					
					Gson gson = new Gson();
					for(JsonElement je : jsonArray){ // 商品属性保存
						oppBean = gson.fromJson(je, TOrderProductProperty.class);
						oppBean.setStatus("0");
						oppBean.setPpid(Integer.valueOf(opi.getId()));
						this.orderProductPropertyDao.save(oppBean); // 商品属性新增
					}
					
				}
				
			}
			
			
		}
		
		
	}
	
	/**
	 * 将企业卸货地址信息COPY一份到询单卸货地址BEAN中,包括卸货地址的图片信息
	 * @param caid 企业卸货地址ID
	 * @param caid 询单ID
	 * @return
	 */
	private TOrderAddress copyBeanOfCompanyAddressToOrderAddress(String caid, String fid){
		TOrderAddress oa = null;
		TCompanyAddress ca = this.companyAddressDao.query(caid);
		Date now = new Date();
		if(ca != null){
			oa = new TOrderAddress();
			oa.setAddress(ca.getAddress());
			oa.setAreacode(ca.getAreacode());
			oa.setCid(ca.getCid());
			oa.setCreatime(now);
			oa.setDeep(ca.getDeep());
			oa.setFid(fid);
			oa.setLatitude(ca.getLatitude());
			oa.setLongitude(ca.getLongitude());
			oa.setRealdeep(ca.getRealdeep());
//			oa.setType(type);
			this.orderAddressDao.save(oa);
			
			// 图片信息复制保存
			List<TUploadImages> uiList = this.uploadImagesService.getListByOidAndOtype(caid, FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal());
			for(TUploadImages ui : uiList){
				ui.setOid(oa.getId());
				ui.setOtype(FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER.getVal());
				ui.setId(null);
				ui.setCreatedate(now);
				
				this.uploadImagesService.add(ui);
			}
		}
		return oa;
	}

	public static void main(String[] args) {
		String str = "[{\"pid\":\"862f32fe-025f-4c88-8c9e-80e831037794\",\"name\":\"含泥量\",\"types\":\"\",\"maxv\":2.1,\"minv\":0.1,\"content\":\"2.3\"},{\"pid\":\"862f32fe-025f-4c88-8c9e-80e831037794\",\"name\":\"压碎值指标\",\"types\":\"\",\"maxv\":2.2,\"minv\":1.1,\"content\":\"2.5\"}]";
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement =  jsonParser.parse(str);
		JsonArray jsonArray = jsonElement.getAsJsonArray();
		
		for(JsonElement je : jsonArray){
			System.out.println(je);
			JsonObject jsonObject = je.getAsJsonObject();
			System.out.println(jsonObject.get("pid").getAsString());
		}
	}

	

}
