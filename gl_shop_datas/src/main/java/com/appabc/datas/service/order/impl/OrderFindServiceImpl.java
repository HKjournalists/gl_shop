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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.MoreAreaInfos;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.CompanyInfo.CompanyAuthStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.FileInfo.FileStyle;
import com.appabc.bean.enums.OrderFindInfo;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import com.appabc.datas.dao.order.IOrderAddressDao;
import com.appabc.datas.dao.order.IOrderFindDao;
import com.appabc.datas.dao.order.IOrderProductInfoDao;
import com.appabc.datas.dao.order.IOrderProductPropertyDao;
import com.appabc.datas.dao.product.IProductInfoDao;
import com.appabc.datas.dao.product.IProductPropertyDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.codes.IPublicCodesService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderAddressService;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.tool.CompanyUtil;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.datas.tool.ViewInfoEncryptUtil;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.utils.SystemParamsManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
	private IProductInfoDao productInfoDao;
	@Autowired
	private IProductPropertyDao productPropertyDao;
	@Autowired
	private IUploadImagesService uploadImagesService;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private IOrderAddressService orderAddressService;
	@Autowired
	private IContractInfoService contractInfoService;
	@Autowired
	private IPassPayService passPayLocalService;
	@Autowired
	private CompanyUtil companyUtil;
	@Autowired
	private IOrderFindItemService orderFindItemService;
	@Autowired
	private IPublicCodesService publicCodesService;
	@Autowired
	private SystemParamsManager spm;

	public void add(TOrderFind entity) {
		this.orderFindDao.save(entity);
	}

	public void modify(TOrderFind entity) {
		this.orderFindDao.update(entity);
	}

	public void delete(TOrderFind entity) {
		this.orderFindDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.orderFindDao.delete(id);
	}

	public TOrderFind query(TOrderFind entity) {
		return this.orderFindDao.query(entity);
	}

	public TOrderFind query(Serializable id) {
		return this.orderFindDao.query(id);
	}

	public List<TOrderFind> queryForList(TOrderFind entity) {
		return this.orderFindDao.queryForList(entity);
	}

	public List<TOrderFind> queryForList(Map<String, ?> args) {
		return this.orderFindDao.queryForList(args);
	}

	/* (non-Javadoc)找买找卖信息列表
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderFind> queryListForPagination(
			QueryContext<TOrderFind> qContext) {
		return this.orderFindDao.queryListForPagination(qContext);
	}
	
	/* (non-Javadoc)我的供求信息列表
	 * @see com.appabc.datas.service.order.IOrderFindService#queryMyListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderFind> queryMyListForPagination(
			QueryContext<TOrderFind> qContext) {
		qContext = this.orderFindDao.queryListForPagination(qContext);
		List<TOrderFind> list = qContext.getQueryResult().getResult();
		if(CollectionUtils.isNotEmpty(list)){
			for(int j=0; j<list.size(); j++){
				if(list.get(j).getMorearea() != null && list.get(j).getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES)){ // 多地发布列表中需要把多地信息加入
					List<MoreAreaInfos> moreAreaInfos = new ArrayList<MoreAreaInfos>();
					TOrderFind entity = new TOrderFind();
					entity.setParentid(list.get(j).getId());
					List<TOrderFind> ofList = this.orderFindDao.queryForList(entity);
					for(int i=0; i<ofList.size(); i++){
						MoreAreaInfos mai = new MoreAreaInfos();
						mai.setArea(ofList.get(i).getArea());
						mai.setPrice(ofList.get(i).getPrice());

						moreAreaInfos.add(mai);
					}

					list.get(j).setMoreAreaList(moreAreaInfos);
				}
			}
		}
		
		return qContext;
	}
	
	/* (non-Javadoc)发布询单
	 * @see com.appabc.datas.service.order.IOrderFindService#orderPublish(com.appabc.bean.pvo.TOrderFind, com.appabc.bean.pvo.TOrderProductInfo, java.lang.String)
	 */
	public void orderPublish(TOrderFind ofBean, TOrderProductInfo opiBean, String addressid) throws ServiceException {

		if(ofBean == null|| opiBean == null){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_EMPTY_ERROR,"询单信息不全");
		}
		TCompanyInfo ci = companyInfoService.query(ofBean.getCid());
		
		if(ci != null){
			// 判断用户是否已认证通过
			if(ci.getAuthstatus() == null || !ci.getAuthstatus().equals(CompanyAuthStatus.AUTH_STATUS_YES)){
				throw new ServiceException("用户未认证");
			}
			// 判断保证金状态是否为已缴纳
			float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
			if(CompanyBailStatus.BAIL_STATUS_NO.equals(companyUtil.checkCashDeposit(ci.getCtype(), 0, monay))){
				throw new ServiceException(ServiceErrorCode.ORDER_FIND_NOENOUGHGUANT_ERROR,"保证金余额不足");
			}
		}else{
			throw new ServiceException("企业不存在");
		}
		
		ofBean.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
		ofBean.setNum(ofBean.getTotalnum()); // 新发布的信息总量等于当前量
		ofBean.setOverallstatus(OrderFindInfo.OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
		
		this.orderFindDao.save(ofBean); // 询单新增
		OrderAllInfor oai = new OrderAllInfor(); // 仅作属性存储，用于生成询单标题

		TOrderProductInfo opiEntity = copyBeanProductInfoToOrderProductInfo(opiBean.getPid());
		oai.setPcode(opiEntity.getPcode());
		oai.setPtype(opiEntity.getPtype());
		oai.setUnit(opiBean.getUnit());
		oai.setNum(ofBean.getNum());
		oai.setPrice(ofBean.getPrice());
		if(ofBean.getType() != null){
			oai.setType(ofBean.getType().getVal());
		}

		opiEntity.setPcolor(opiBean.getPcolor());
		opiEntity.setPremark(opiBean.getPremark());
		opiEntity.setPaddress(opiBean.getPaddress());
		opiEntity.setUnit(opiBean.getUnit());
		opiEntity.setFid(ofBean.getId());

		this.orderProductInfoDao.save(opiEntity); // 询单商品信息新增

		/*********商品属性解析****************************************/
		Gson gson = new Gson();

		try {
			if(StringUtils.isNotEmpty(opiBean.getProductPropertys())){
				JsonParser jsonParser = new JsonParser();
				JsonElement jsonElement =  jsonParser.parse(opiBean.getProductPropertys());
				JsonArray jsonArray = jsonElement.getAsJsonArray();

				for(JsonElement je : jsonArray){ // 商品属性保存
					TOrderProductProperty opp = gson.fromJson(je, TOrderProductProperty.class);
					TOrderProductProperty oppBean = copyBeanProductPropertyToOrderProductProperty(opp.getId());

					oppBean.setPpid(Integer.valueOf(opiEntity.getId()));
					oppBean.setContent(opp.getContent());

					this.orderProductPropertyDao.save(oppBean); // 商品属性新增
				}

				// 商品图片信息更新
				if(opiBean.getProductImgIds() != null && !opiBean.getProductImgIds().trim().equals("")){
					String[] productImgIds = opiBean.getProductImgIds().split(",");
					for(String imgid : productImgIds){
						this.uploadImagesService.updateOtypeAndOid(opiEntity.getId(), FileOType.FILE_OTYPE_PRODUCT_ORDER, imgid);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("商品属性解析异常");
		}

		// 商品规格存储
		TOrderProductProperty psize = copyBeanProductPropertyToOrderProductProperty(opiEntity.getPsize()); // 将商品规格属性复制
		psize.setPpid(Integer.valueOf(opiEntity.getId()));
		this.orderProductPropertyDao.save(psize); // 商品规格存储
		oai.setPsize(psize);

		// 更新交易中的商品规格ID到交易中的商品表
		opiEntity.setPsize(psize.getId());
		this.orderProductInfoDao.update(opiEntity);

		// 询单卸货地址存储
		if(StringUtils.isNotEmpty(addressid)){
			copyBeanCompanyAddressToOrderAddress(addressid, ofBean.getId());
		}

		// 多地发布判断
		ofBean.setTitle(spellOrderFindTitle(oai));
		this.orderFindDao.update(ofBean); // 更新标题
	}

	/* (non-Javadoc) 询单详情
	 * @see com.appabc.datas.service.order.IOrderFindService#queryInfoById(java.lang.String, java.lang.String)
	 */
	public OrderAllInfor queryInfoById(String fid, String requestCid) {
		String originalFid = fid; // 原始询单ID,子询单时有用
		TOrderFind ofBean = this.orderFindDao.query(fid);
		if(ofBean != null && StringUtils.isNotEmpty(ofBean.getParentid())){ // 说明是个子询单，关联的时候查询父询单信息
			fid = ofBean.getParentid();
		}

		OrderAllInfor oai = this.orderFindDao.queryInfoById(fid);
		if(oai != null){
			if(ofBean != null && StringUtils.isNotEmpty(ofBean.getParentid())){ // 说明是个子询单，关联的时候查询父询单信息，查询结果将子询单价格和地域换成子询单的信息
				oai.setPrice(ofBean.getPrice());
				oai.setArea(ofBean.getArea());

				oai.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO.getVal()); // 将父询单中的多地改为单地
				oai.setMoreAreaInfos(null);
			}

			/*****商品属性查询**********************/
			TOrderProductProperty  opp = new TOrderProductProperty();
			opp.setPpid(oai.getOpiid());
			List<TOrderProductProperty> oppList = orderProductPropertyDao.queryForList(opp);
			for(TOrderProductProperty oppBean : oppList){ // 将商品Pproid set为ID 方便客户端用
				oppBean.setId(oppBean.getPproid());
			}
			oai.setOppList(oppList);

			oai.setPsize(orderProductPropertyDao.query(oai.getPsizeid())); // 查询商品规格信息
			oai.setPsizeid(null);
			/*****子询单查询************************/
			if(oai.getMorearea() != null && oai.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES.getVal())) {
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

			// 卸货地址信息
			TOrderAddress caEntity  = new TOrderAddress();
			caEntity.setFid(fid);
			List<TOrderAddress> caList = this.orderAddressService.queryForList(caEntity);
			if(caList != null && caList.size()>0 && caList.get(0) != null){
				oai.setAddress(caList.get(0).getAddress());
				oai.setAreacode(caList.get(0).getAreacode());
				oai.setDeep(caList.get(0).getDeep());
				oai.setRealdeep(caList.get(0).getRealdeep());

				// 卸货地址图片URL
				oai.setAddressImgList(this.uploadImagesService.getViewImgsByOidAndOtype(caList.get(0).getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER.getVal()));
			}

			oai.setEvaluationInfo(this.companyInfoService.getEvaluationByCid(oai.getCid()));

			TCompanyInfo ci = this.companyInfoService.query(oai.getCid());
			oai.setCname(ci.getCname());
			if(oai.getOpiid() !=  null){ // 商品图片
				oai.setProductImgList(this.uploadImagesService.getViewImgsByOidAndOtype(oai.getOpiid()+"", FileInfo.FileOType.FILE_OTYPE_PRODUCT_ORDER.getVal()));
			}

			if(StringUtils.isNotEmpty(requestCid) && !oai.getCid().equals(requestCid)){ // 其它用户查看
				if(!contractInfoService.isOldCustomer(requestCid, oai.getCid())){ // 2个企业未发生过交易，进行企业加密处理
					ViewInfoEncryptUtil.encryptCompanyInfo(oai);
				}
			}
			int isApply = this.orderFindItemService.getIsApplyByCid(requestCid, fid);
			oai.setIsApply(isApply);
			oai.setId(originalFid);
			
			oai.setMatchingnum(this.contractInfoService.getMatchingNumByFid(originalFid));
			oai.setTitle(spellOrderFindTitle(oai));

		}
		return oai;
	}

	/* (non-Javadoc)取消询单
	 * @see com.appabc.datas.service.order.IOrderFindService#cancel(java.lang.String, java.lang.String)
	 */
	public String cancel(String fid, String userid) {

		TOrderInfo  oi = new TOrderInfo();
		oi.setFid(fid);
		List<TOrderInfo> oiList = this.contractInfoService.queryForList(oi);
		if(oiList!=null && oiList.size()>0 && oiList.get(0)!=null){
			return "该询单已关联合同，不能取消";
		}else{
			TOrderFind of = this.orderFindDao.query(fid);
			if(of != null && of.getStatus().equals(OrderStatusEnum.ORDER_STATUS_YES)){
				if(of.getMorearea().equals(OrderMoreAreaEnum.ORDER_MORE_AREA_YES)){ // 多地发布

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

		entity.setStatus(OrderStatusEnum.ORDER_STATUS_CANCEL);
		entity.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		entity.setEndtime(now);
		entity.setUpdatetime(now);

		this.orderFindDao.update(entity);
	}

	/* (non-Javadoc)询单更新
	 * @see com.appabc.datas.service.order.IOrderFindService#updateOrderAllInfo(com.appabc.bean.pvo.TOrderFind, com.appabc.bean.pvo.TOrderAddress, com.appabc.bean.pvo.TOrderProductInfo)
	 */
	public void updateOrderAllInfo(TOrderFind ofBean, TOrderProductInfo opiBean, String addressid) {
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

			/****商品信息处理****************************/
			TOrderProductInfo opiEntity = copyBeanProductInfoToOrderProductInfo(opiBean.getPid()); //复制部分属性
			TOrderProductInfo opiQuery = new TOrderProductInfo();
			opiQuery.setFid(of.getId());
			List<TOrderProductInfo> opiList = this.orderProductInfoDao.queryForList(opiQuery); // 查询原关联商品，正常情况只有一个
			Gson gson = new Gson();

			if(opiList != null && opiList.size()>0 && opiList.get(0) != null) { // 原商品信息
				opiEntity.setId(opiList.get(0).getId()); // 将原属性ID存放在将要更新的BEAN中
				/*****商品属性处理**********************/
				TOrderProductProperty entity = new TOrderProductProperty();
				entity.setPpid(Integer.parseInt(opiEntity.getId()));
				this.orderProductPropertyDao.delete(entity); // 删除原商品的属性,包括商品规格

				if(StringUtils.isNotEmpty(opiBean.getProductPropertys())){ // 新商品属性处理
					JsonParser jsonParser = new JsonParser();
					JsonElement jsonElement =  jsonParser.parse(opiBean.getProductPropertys());
					JsonArray jsonArray = jsonElement.getAsJsonArray();

					for(JsonElement je : jsonArray){ // 商品属性保存
						TOrderProductProperty opp = gson.fromJson(je, TOrderProductProperty.class);
						TOrderProductProperty oppBean = copyBeanProductPropertyToOrderProductProperty(opp.getId());

						oppBean.setPpid(Integer.valueOf(opiEntity.getId()));
						oppBean.setContent(opp.getContent());
						this.orderProductPropertyDao.save(oppBean); // 商品属性新增
					}
				}

				String psizeJson = opiBean.getPsize(); // 商品规格JSON
				if(StringUtils.isNotEmpty(psizeJson)){ // 商品规格存储
					TOrderProductProperty psize = gson.fromJson(psizeJson, TOrderProductProperty.class);
					psize.setPpid(Integer.valueOf(opiEntity.getId()));
					psize.setStatus(PropertyStatusEnum.PROPERTY_STATUS_1);
					this.orderProductPropertyDao.save(psize); // 商品规格存储

					// 更新交易中的商品规格ID到交易中的商品表
					opiEntity.setPsize(psize.getId());
				}

				/****商品基本信息*************/
				opiEntity.setPcolor(opiBean.getPcolor());
				opiEntity.setPremark(opiBean.getPremark());
				opiEntity.setPaddress(opiBean.getPaddress());
				opiEntity.setFid(ofBean.getId());

				opiEntity.setUnit(opiBean.getUnit());
				opiEntity.setPremark(opiBean.getPremark());

				this.orderProductInfoDao.update(opiEntity);

			}

			// 商品图片信息更新
			String[] imgIds = null;
			if(StringUtils.isNotEmpty( opiBean.getProductImgIds())){
				imgIds = opiBean.getProductImgIds().split(",");
			}
			this.uploadImagesService.updateOtypeAndOidBatch(opiEntity.getId(), FileOType.FILE_OTYPE_PRODUCT_ORDER, imgIds);

			/****卸货地址图片更新*******/

			TOrderAddress caEntity  = new TOrderAddress();
			caEntity.setFid(ofBean.getId());
			List<TOrderAddress> caList = this.orderAddressService.queryForList(caEntity);
			if(caList != null && caList.size()>0 && caList.get(0) != null){
				this.orderAddressService.delete(caList.get(0).getId());
			}
			// 询单新卸货地址存储
			if(StringUtils.isNotEmpty(addressid)){
				copyBeanCompanyAddressToOrderAddress(addressid, ofBean.getId());
			}

		}


	}

	/**
	 * 将企业卸货地址信息COPY一份到询单卸货地址BEAN中,包括卸货地址的图片信息
	 * @param caid 企业卸货地址ID
	 * @param fid 询单ID
	 * @return
	 */
	private TOrderAddress copyBeanCompanyAddressToOrderAddress(String caid, String fid){
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
			
			String newParentId = null ;
			for(TUploadImages ui : uiList){
				ui.setOid(oa.getId());
				ui.setOtype(FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER);
				ui.setId(null);
				ui.setCreatedate(now);
				if(FileStyle.FILE_STYLE_SMALL.equals(ui.getFstyle())) { // 略图
					ui.setPid(Integer.parseInt(newParentId));  // 父ID为上一条原始图片ID
				}

				this.uploadImagesService.add(ui);
				if(FileStyle.FILE_STYLE_ORIGINAL.equals(ui.getFstyle())){ // 原始图片
					newParentId = ui.getId();
				}else{
					newParentId = null;
				}
			}
		}
		return oa;
	}

	/**
	 * 复制商品信息到交易中的商品BEAN中(有Pname，Ptype，Pcode，Pid,psize)
	 * @param productInfoId 商品ID
	 * @return
	 */
	private TOrderProductInfo copyBeanProductInfoToOrderProductInfo(String productInfoId) {

		TProductInfo pi = this.productInfoDao.query(productInfoId);
		TOrderProductInfo opi = new TOrderProductInfo();
		opi.setPname(pi.getPname());
		opi.setPtype(pi.getPtype());
//		opi.setUnit(pi.getUnit());;
		opi.setPcode(pi.getPcode());
		opi.setPid(pi.getId()); // 引用商品ID
		opi.setPsize(pi.getPsize());

		return opi;
	}

	/**
	 * 复制商品属性信息到交易中的商品属性BEAN中(不包括ppid)
	 * @param productPropertyId 商品属性ID
	 * @return
	 */
	private TOrderProductProperty copyBeanProductPropertyToOrderProductProperty(String productPropertyId) {

		TProductProperty pp = this.productPropertyDao.query(productPropertyId);
		TOrderProductProperty opp = new TOrderProductProperty();
		opp.setCode(pp.getCode());
		opp.setMaxv(pp.getMaxv());
		opp.setMinv(pp.getMinv());
		opp.setName(pp.getName());
		opp.setOrderno(pp.getOrderno());
		opp.setPproid(pp.getId());
		opp.setStatus(pp.getStatus());
		opp.setTypes(pp.getTypes());
		opp.setContent(pp.getContent());
		opp.setUnit(pp.getUnit());

		return opp;
	}

	public int getTotalByCid(String cid) {
		return this.orderFindDao.countByCid(cid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindService#updateChildOrderFindCloseInvalidByParentId(java.lang.String, java.lang.String)  
	 */
	@Override
	public void updateChildOrderFindCloseInvalidByParentId(String parentId,
			String operator) throws ServiceException {
		if(StringUtils.isEmpty(parentId) || StringUtils.isNotEmpty(operator)){
			throw new ServiceException("参数不能为空.");
		}
		boolean u = this.orderFindDao.updateChildOrderFindCloseInvalidByParentId(parentId, operator);
		if(!u){
			throw new ServiceException("更新失败");
		};
	}
	
	/**
	 * 商品标题拼组
	 * @param oai
	 * @return
	 */
	public String spellOrderFindTitle(OrderAllInfor oai){
		if(oai == null) return null;
		try {
			StringBuilder title = new StringBuilder();
			List<TPublicCodes> codeList = publicCodesService.queryListByCode(SystemConstant.CODE_GOODS_TYPE);

			title.append(OrderFindInfo.OrderTypeEnum.enumOf(oai.getType()).getText());
			
			if(codeList !=  null && StringUtils.isNotEmpty(oai.getPcode())){ // 商品大类名称
				for(TPublicCodes code : codeList){
					if(code.getVal() != null && code.getVal().equals(oai.getPcode())){
						title.append(code.getName());
						break;
					}
				}
			}
			
			if(codeList !=  null && StringUtils.isNotEmpty(oai.getPtype())){ // 商品子类名称（黄砂）
				for(TPublicCodes code : codeList){
					if(code.getVal() != null && code.getVal().equals(oai.getPtype())){
						title.append("·").append(code.getName());
						break;
					}
				}
			}
			
			if(oai.getPsize() != null && StringUtils.isNotEmpty(oai.getPsize().getName())){ // 规格
				title.append("·")
				.append(oai.getPsize().getName())
				.append("(").append(oai.getPsize().getMinv()).append("-").append(oai.getPsize().getMaxv()).append(")");
				if(oai.getPsize().getUnit() != null){// 规格单位
					title.append(oai.getPsize().getUnit().getText());
				}
			}
			String unit = "";
			if(oai.getUnit() != null){
				unit = oai.getUnit().getText();
			}
			
			if(oai.getNum() != null){ // 总量
				title.append(" ")
				.append(oai.getNum()).append(unit);
			}
			
			if(oai.getPrice() != null){ // 单价
				title.append(" ")
				.append(oai.getPrice()).append("元/").append(unit);
			}
			
			return title.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)询单自动匹配列表
	 * @see com.appabc.datas.service.order.IOrderFindService#queryMatchingObjectByCidForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<MatchingBean> queryMatchingObjectByCidForPagination(
			QueryContext<MatchingBean> qContext, String fid) throws ServiceException {
		
		if(StringUtils.isNotEmpty(fid)){
			qContext.addParameter("fid", fid);
		}
		
		if(qContext.getParameter("fid") == null){
			throw new ServiceException("query Failure，fid is null。");
		}
		
		QueryContext<TOrderFind> qContextOrderFind = new QueryContext<TOrderFind>();
		qContextOrderFind.setOrder(qContext.getOrder());
		qContextOrderFind.setOrderColumn(qContext.getOrderColumn());
		qContextOrderFind.setPage(qContext.getPage());
		qContextOrderFind.setParameters(qContext.getParameters());
		qContextOrderFind.setParamList(qContext.getParamList());
		
		qContextOrderFind = this.orderFindDao.queryMatchingObjectByCidForPagination(qContextOrderFind);
		
		qContext.setOrder(qContextOrderFind.getOrder());
		qContext.setOrderColumn(qContextOrderFind.getOrderColumn());
		qContext.setPage(qContextOrderFind.getPage());
		qContext.setParameters(qContextOrderFind.getParameters());
		qContext.setParamList(qContextOrderFind.getParamList());
		
		QueryResult<MatchingBean> queryResultMatching = new QueryResult<MatchingBean>();
		if(qContextOrderFind.getQueryResult() != null && qContextOrderFind.getQueryResult().getResult() != null){
			List<TOrderFind> ofList = qContextOrderFind.getQueryResult().getResult();
			if(ofList != null){
				List<MatchingBean> matchList = new ArrayList<MatchingBean>();
				for(TOrderFind of : ofList){
					matchList.add(of.getMatchingBean());
				}
				queryResultMatching.setResult(matchList);
				queryResultMatching.setResultParam(qContextOrderFind.getQueryResult().getResultParam());
				queryResultMatching.setTotalSize(qContextOrderFind.getQueryResult().getTotalSize());
			}
		}
		
		
		qContext.setQueryResult(queryResultMatching);
		
		return qContext;
	}
	
	
}
