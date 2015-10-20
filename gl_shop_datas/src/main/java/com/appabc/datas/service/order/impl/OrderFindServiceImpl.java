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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.OrderFindInsteadBean;
import com.appabc.bean.bo.OrderFindQueryParamsBean;
import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.OrderFindInfo;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.enums.SysLogEnum.LogBusinessType;
import com.appabc.bean.enums.SysLogEnum.LogLevel;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.bean.pvo.TSystemLog;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.ErrorCode;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import com.appabc.datas.dao.order.IOrderFindDao;
import com.appabc.datas.dao.order.IOrderProductPropertyDao;
import com.appabc.datas.dao.product.IProductInfoDao;
import com.appabc.datas.dao.product.IProductPropertyDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderAddressService;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.IOrderProductInfoService;
import com.appabc.datas.service.system.ISystemLogService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.datas.tool.ViewInfoEncryptUtil;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.service.codes.IPublicCodesService;
import com.appabc.tools.utils.AreaManager;
import com.appabc.tools.utils.GuarantStatusCheck;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.SystemParamsManager;

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
public class OrderFindServiceImpl extends BaseService<TOrderFind> implements IOrderFindService {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IOrderFindDao orderFindDao;
	@Autowired
	private ICompanyAddressDao companyAddressDao;
	@Autowired
	private IOrderProductInfoService orderProductInfoService;
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
	private GuarantStatusCheck guarantStatusCheck;
	@Autowired
	private IOrderFindItemService orderFindItemService;
	@Autowired
	private IPublicCodesService publicCodesService;
	@Autowired
	private SystemParamsManager spm;
	@Autowired
	private AreaManager areaManager;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private MessageSendManager mesgSender;

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

	/* (non-Javadoc)
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
			QueryContext<TOrderFind> qContext, String cid) throws ServiceException {
		if(StringUtils.isEmpty(cid)){
			throw new ServiceException("企业ID不能为空");
		}
		OrderFindQueryParamsBean ofp = new OrderFindQueryParamsBean();
		qContext.addParameter("cid", cid);// 企业ID
		qContext.addParameter("queryMethod", "getMyOrderList");// 判断条件
		qContext.addParameter("status", OrderStatusEnum.ORDER_STATUS_DELETE);
		
		if(qContext.getParameter("type") !=null) ofp.setType(OrderTypeEnum.enumOf(Integer.parseInt(qContext.getParameter("type").toString()))); // 买/卖
		
		return this.orderFindDao.queryOrderListForPagination(qContext, ofp);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#orderPublish(com.appabc.bean.pvo.TOrderFind, com.appabc.bean.pvo.TOrderProductInfo, java.util.List, com.appabc.bean.pvo.TOrderAddress)
	 */
	@Override
	public void orderPublish(TOrderFind ofBean, TOrderProductInfo opiBean,
			List<ProductPropertyContentBean> ppcList, TOrderAddress oa, String originalFid)
			throws ServiceException {
		
		publish(ofBean, opiBean, ppcList);
		if(oa != null && StringUtils.isNotEmpty(oa.getId())){ // 新发布的询单，把企业卸货地COPY到询单地址表一份(重新发布如果选择了新的卸货地址也会走这一步)
			copyBeanCompanyAddressToOrderAddress(oa.getId(), ofBean.getId());
		}else if(StringUtils.isNotEmpty(originalFid)){ // 重新发布时如果没有改动卸货地址，把原始询单卸货地址COPY一份
			TOrderAddress oaEntity = this.orderAddressService.queryByFid(originalFid); // 原询单的卸货地址
			this.orderAddressService.copyTOrderAddressRelationshipsFid(ofBean.getId(), oaEntity);
		}
	}
	
	@Override
	public void orderPublishSubstitute(TOrderFind ofBean, TOrderProductInfo opiBean,
			List<ProductPropertyContentBean> ppcList, String addressId)
					throws ServiceException {
		
		publish(ofBean, opiBean, ppcList);
		if(StringUtils.isNotEmpty(addressId)){ 
			copyBeanCompanyAddressToOrderAddress(addressId, ofBean.getId());
		}

	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#contractMatchingOfOrderFindSave(com.appabc.bean.bo.OrderFindAllBean)
	 */
	@Override
	public String contractMatchingOfOrderFindSave(OrderFindAllBean ofaBean)
			throws ServiceException {
		ofaBean.getOfBean().setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_COPY);
		return orderFindChildSave(ofaBean);
	}
	
	private String orderFindChildSave(OrderFindAllBean ofaBean) throws ServiceException{
		String parentId = null;
		
		/*****参数检查*************************************/
		if(ofaBean != null && ofaBean.getOfBean() != null){
			parentId = ofaBean.getOfBean().getId();
		}else{
			throw new ServiceException("询单信息不完整，OrderFindAllBean="+ofaBean+",ofaBean.getOfBean()="+ofaBean.getOfBean());
		}
		
		if(StringUtils.isNotEmpty(parentId)){
			TOrderFind oi = this.query(parentId);
			if(oi != null){
				if(StringUtils.isNotEmpty(oi.getContractid())){
					throw new ServiceException("已撮合过的询单不能再撮合，ofaBean.getOfBean().getId()="+parentId+",contractid="+oi.getContractid());
				}else if(SystemConstant.ORDER_FIND_OTHER_PARENTID.equals(oi.getParentid())){
					throw new ServiceException("副本询单不能进行撮合，ofaBean.getOfBean().getId()="+parentId+",contractid="+oi.getContractid());
				}else if(oi.getOverallstatus() == OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID){
					throw new ServiceException("已失效询单不能进行撮合，ofaBean.getOfBean().getId()="+parentId+",contractid="+oi.getContractid());
				}
				
			}else{
				throw new ServiceException("询单不存在，ofaBean.getOfBean().getId()="+parentId);
			}
		}
		
		if(ofaBean.getOpiBean() != null && StringUtils.isNotEmpty(ofaBean.getOpiBean().getProductImgIds())){ // 撮合时一般不会修改商品图片
			ofaBean.getOpiBean().setProductImgIds(null);
		}
		/******询单生成******************/
		if(ofaBean.getOfBean().getOveralltype() == null) { // 询单大类型未指定时默认为副本询单
			ofaBean.getOfBean().setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_COPY);
		}
		publish(ofaBean.getOfBean(), ofaBean.getOpiBean(), ofaBean.getPpcList());
		
		/******建立询单的父子关系******************/
		String childId = ofaBean.getOfBean().getId();
		if(StringUtils.isNotEmpty(parentId)){
			buildRelationshipsOfOrderFind(parentId, childId);
		}else{
			buildRelationshipsOfOrderFind(SystemConstant.ORDER_FIND_OTHER_PARENTID, childId);
		}
		
		/******卸货地址处理******************/
		if(ofaBean.getOaBean() != null && StringUtils.isNotEmpty(ofaBean.getOaBean().getAddress())){
			String orgAddressId = ofaBean.getOaBean().getId(); // 原始询单的卸货地址ID;
			
			ofaBean.getOaBean().setId(null);
			ofaBean.getOaBean().setFid(childId);
			this.orderAddressService.add(ofaBean.getOaBean());
			 
			/******卸货地址图片处理******************/
			if(StringUtils.isNotEmpty(orgAddressId)){ // 使用了原始卸货地址，把原图片复制一份
				TUploadImages entityImg = new TUploadImages();
				entityImg.setOid(orgAddressId);
				entityImg.setOtype(FileOType.FILE_OTYPE_ADDRESS_ORDER);
				List<TUploadImages> uiList = uploadImagesService.queryForList(entityImg); // 原始询单的卸货地址
				
				this.uploadImagesService.copyUploadImagesRelationshipsObject(ofaBean.getOaBean().getId(), FileOType.FILE_OTYPE_ADDRESS_ORDER, uiList);
			}
			
		}else{
			throw new ServiceException("address 字段不能为空");
		}
		
		/*****询单货物图片复制**********************************/
		if(ofaBean.getOpiBean() != null && StringUtils.isEmpty(ofaBean.getOpiBean().getId())){ // 保存成功后直接复制货物图片
			TOrderProductInfo opi = new TOrderProductInfo();
			opi.setFid(parentId);
			opi = this.orderProductInfoService.query(opi); // 原询单货物基本信息
			
			if(opi != null){
				TUploadImages entityImg = new TUploadImages();
				entityImg.setOid(opi.getId());
				entityImg.setOtype(FileOType.FILE_OTYPE_PRODUCT_ORDER);
				List<TUploadImages> uiList = uploadImagesService.queryForList(entityImg); // 原始询单的货物图片
				
				this.uploadImagesService.copyUploadImagesRelationshipsObject(ofaBean.getOaBean().getId(), FileOType.FILE_OTYPE_PRODUCT_ORDER, uiList);
			}
		}
		
		return childId;
	}
	
	@Override
	public String contractMatchingOfOrderFindSaveDraft(OrderFindAllBean ofaBean)
			throws ServiceException{
		
		/*****参数检查*************************************/
		if(ofaBean == null || ofaBean.getOfBean() == null){
			throw new ServiceException("询单信息不完整，OrderFindAllBean="+ofaBean+",ofaBean.getOfBean()="+ofaBean.getOfBean());
		}
		ofaBean.getOfBean().setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_DRAFT);
		return orderFindChildSave(ofaBean);
	}
			
	
	/**
	 * 发布询单
	 * @param ofBean
	 * @param opiBean
	 * @param ppcList
	 * @throws ServiceException
	 */
	private void publish(TOrderFind ofBean, TOrderProductInfo opiBean,List<ProductPropertyContentBean> ppcList) throws ServiceException {
		
		if(ofBean == null || opiBean == null){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_EMPTY_ERROR,"询单信息不全");
		}
		if(StringUtils.isEmpty(opiBean.getPid())){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_EMPTY_ERROR,"未选择商品,opiBean.getPid()="+opiBean.getPid());
		}
		if(StringUtils.isEmpty(ofBean.getCid())){
			throw new ServiceException(ServiceErrorCode.ORDER_FIND_EMPTY_ERROR,"企业ID不能为空");
		}
		if(opiBean.getUnit() == null){
			throw new ServiceException(ErrorCode.DATA_IS_NOT_COMPLETE,"未选择单位,opiBean.getUnit()=null");
		}
		TCompanyInfo ci = companyInfoService.queryAuthCmpInfo(ofBean.getCid());
		
//		if(ci != null){
			// 判断用户是否已认证通过
//			if(ci.getAuthstatus() == null || !ci.getAuthstatus().equals(AuthRecordStatus.AUTH_STATUS_CHECK_YES)){
//				throw new ServiceException("用户未认证");
//			}
			// 判断保证金状态是否为已缴纳
//			float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
//			if(CompanyBailStatus.BAIL_STATUS_NO.equals(guarantStatusCheck.checkCashDeposit(ci.getId(), monay))){
//				throw new ServiceException(ServiceErrorCode.ORDER_FIND_NOENOUGHGUANT_ERROR,"保证金余额不足");
//			}
//		}else{
//			throw new ServiceException("企业不存在,cid="+ofBean.getCid());
//		}
		if(ci == null){
			throw new ServiceException("企业不存在,cid="+ofBean.getCid());
		}
		
		if(ofBean.getStarttime() != null && ofBean.getEndtime() != null){
			if(ofBean.getEndtime().before(DateUtil.getTodayDateOfYMD())){
				throw new ServiceException(ErrorCode.DATE_NOWTIME_AFTER_ENDTIME, "结束日期不能在今天之前，ofBean.getEndtime()="+ofBean.getEndtime());
			}else if(ofBean.getEndtime().before(ofBean.getStarttime())){
				throw new ServiceException(ErrorCode.DATE_STARTTIME_AFTER_ENDTIME, "开始日期不能大于结束日期，ofBean.getEndtime()="+ofBean.getEndtime());
			}
		}else{
			throw new ServiceException(ErrorCode.DATA_IS_NOT_COMPLETE,"开始时间和结束时间不能为空，ofBean.getStarttime()="+ofBean.getStarttime()+",ofBean.getEndtime()="+ofBean.getEndtime());
		}
		
		/***询单基本信息************************************/
		if(ofBean.getTotalnum() == null || ofBean.getTotalnum()<=0){
			throw new ServiceException("成交量错误，totalnum="+ofBean.getTotalnum());
		}else if(ofBean.getTotalnum() > SystemConstant.MAX_TOTAL_AMOUNT){
			throw new ServiceException(ServiceErrorCode.EXCEEDS_MAX_TOTAL_AMOUNT, "超过了最大量,maxTotalNum="+SystemConstant.MAX_TOTAL_AMOUNT+",this totalNum="+ofBean.getTotalnum());
		}
		
		if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			throw new ServiceException("单价错误，price="+ofBean.getTotalnum());
		}else if(ofBean.getPrice() > SystemConstant.MAX_UNIT_PRICE){
			throw new ServiceException(ServiceErrorCode.EXCEEDS_MAX_UNIT_PRICE, "超过了最大单价,max price="+SystemConstant.MAX_UNIT_PRICE+",this price="+ofBean.getPrice());
		}
		
		ofBean.setNum(ofBean.getTotalnum()); // 新发布的信息总量等于当前量
		ofBean.setOverallstatus(OrderFindInfo.OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
		ofBean.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
		ofBean.setId(null);
		if(ofBean.getOveralltype() == null ) { // 未指定时为正式询单
			ofBean.setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_FORMAL);
		}
		this.orderFindDao.save(ofBean); // 询单新增
		
		/***商品基本信息************************************/
		TOrderProductInfo opiEntity = copyBeanProductInfoToOrderProductInfo(opiBean.getPid());
		opiEntity.setPcolor(opiBean.getPcolor());
		opiEntity.setPremark(opiBean.getPremark());
		opiEntity.setPaddress(opiBean.getPaddress());
		opiEntity.setUnit(opiBean.getUnit());
		opiEntity.setFid(ofBean.getId());
		
		OrderAllInfor oai = new OrderAllInfor(); // 仅作属性存储，用于生成询单标题
		oai.setPcode(opiEntity.getPcode());
		oai.setPtype(opiEntity.getPtype());
		oai.setUnit(opiBean.getUnit());
		oai.setTotalnum(ofBean.getTotalnum());
		oai.setPrice(ofBean.getPrice());
		if(ofBean.getType() != null){
			oai.setType(ofBean.getType().getVal());
		}
		
		this.orderProductInfoService.add(opiEntity); // 询单商品信息新增
		
		/*********商品属性存储****************************************/
		for(ProductPropertyContentBean ppc : ppcList){ // 商品属性保存
			if(StringUtils.isNotEmpty(ppc.getPpid()) && StringUtils.isNotEmpty(ppc.getContent())){
				TOrderProductProperty oppBean = copyBeanProductPropertyToOrderProductProperty(ppc.getPpid());
				oppBean.setPpid(Integer.valueOf(opiEntity.getId()));
				oppBean.setContent(ppc.getContent());
				this.orderProductPropertyDao.save(oppBean); // 询单商品属性新增
			}else{
				logger.info("商品发布，商品属性对应信息为空ppid="+ppc.getPpid()+",content="+ppc.getContent());
			}
		}
		
		/*********商品规格存储****************************************/
		TOrderProductProperty psize = copyBeanProductPropertyToOrderProductProperty(opiEntity.getPsize()); // 将商品规格属性复制
		psize.setPpid(Integer.valueOf(opiEntity.getId()));
		this.orderProductPropertyDao.save(psize); // 商品规格存储
		oai.setPsize(psize);
		
		// 更新交易中的商品规格ID到交易中的商品表
		opiEntity.setPsize(psize.getId());
		this.orderProductInfoService.modify(opiEntity);
		
		/*********商品图片信息更新****************************************/
		if(opiBean.getProductImgIds() != null && !opiBean.getProductImgIds().trim().equals("")){
			String[] productImgIds = opiBean.getProductImgIds().split(",");
			for(String imgid : productImgIds){
				this.uploadImagesService.updateOtypeAndOid(opiEntity.getId(), FileOType.FILE_OTYPE_PRODUCT_ORDER, imgid);
			}
		}
		
		// 询单标题拼写
		ofBean.setTitle(spellOrderFindTitle(oai));
		this.orderFindDao.update(ofBean); // 更新标题
	}

	/* (non-Javadoc) 询单详情
	 * @see com.appabc.datas.service.order.IOrderFindService#queryInfoById(java.lang.String, java.lang.String)
	 */
	public OrderAllInfor queryInfoById(String fid, String requestCid) {

		OrderAllInfor oai = this.orderFindDao.queryInfoById(fid);
		if(oai != null){
			/*****商品属性查询**********************/
			TOrderProductProperty  opp = new TOrderProductProperty();
			opp.setPpid(oai.getOpiid());
			opp.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
			List<TOrderProductProperty> oppList = orderProductPropertyDao.queryForList(opp);
			for(TOrderProductProperty oppBean : oppList){ // 将商品Pproid set为ID 方便客户端用
				oppBean.setId(oppBean.getPproid());
			}
			oai.setOppList(oppList);

			oai.setPsize(orderProductPropertyDao.query(oai.getPsizeid())); // 查询商品规格信息
			oai.setPsizeid(null);

			// 卸货地址信息
			TOrderAddress caEntity  = new TOrderAddress();
			caEntity.setFid(fid);
			List<TOrderAddress> caList = this.orderAddressService.queryForList(caEntity);
			if(caList != null && caList.size()>0 && caList.get(0) != null){
				oai.setAddress(caList.get(0).getAddress());
				oai.setAreacode(caList.get(0).getAreacode());
				oai.setDeep(caList.get(0).getDeep());
				oai.setRealdeep(caList.get(0).getRealdeep());
				oai.setShippington(caList.get(0).getShippington());
				oai.setAddrAreaFullName(areaManager.getFullAreaName(caList.get(0).getAreacode()));

				// 卸货地址图片URL
				oai.setAddressImgList(this.uploadImagesService.getViewImgsByOidAndOtype(caList.get(0).getId(), FileInfo.FileOType.FILE_OTYPE_ADDRESS_ORDER.getVal()));
			}

			// 企业评价信息
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
				
			}else if(StringUtils.isNotEmpty(oai.getContractid())){ // 自己查看自己的询单时，给出该询单的合同状态
				TOrderInfo oi = contractInfoService.getOrderDetailInfo(oai.getContractid());
				if(oi != null && oi.getStatus() != null){
					oai.setContractStatus(oi.getStatus());
					if(ContractStatus.FINISHED.equals(oi.getStatus())){ // 如果合同已结束，给出结束时间,结束时间默认为合同最后一 次更新时间
						oai.setContractendtime(oi.getUpdatetime());
					}
				}
			}
			// 企业保证金缴纳状态，时时查询，不以企业表中的为准
			float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
			oai.setBailstatus(guarantStatusCheck.checkCashDeposit(ci.getId(), monay));
			oai.setGuaranty(monay);
			oai.setCtype(ci.getCtype());
			oai.setAuthstatus(ci.getAuthstatus());
			
			int isApply = this.orderFindItemService.getIsApplyByCid(requestCid, fid);
			oai.setIsApply(isApply);
			oai.setId(fid);
			
			List<TOrderFindItem> ret = this.orderFindItemService.queryOrderFindItemListByFid(fid);
			oai.setApplyNum(ret != null ? ret.size() : 0);
			oai.setApplyDate(ret != null && ret.size() > 0 ? ret.get(0).getCreatetime() : null);
//			oai.setMatchingnum(this.contractInfoService.getMatchingNumByFid(fid));
			oai.setTitle(spellOrderFindTitle(oai));
		}
		return oai;
	}

	/* (non-Javadoc)取消询单
	 * @see com.appabc.datas.service.order.IOrderFindService#cancel(java.lang.String, java.lang.String)
	 */
	public String cancel(String fid, String userid) throws ServiceException {

		TOrderFind of = this.orderFindDao.query(fid);

		if(of!=null && OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE.equals(of.getOverallstatus())){
			if(OrderStatusEnum.ORDER_STATUS_YES.equals(of.getStatus())){
				of.setUpdater(userid);
				of.setStatus(OrderStatusEnum.ORDER_STATUS_CANCEL);
				of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);

				this.orderFindDao.update(of);
				
				/******日志************************/
				TSystemLog log = new TSystemLog();
				log.setBusinessid(fid);
				log.setBusinesstype(LogBusinessType.BUSINESS_TYPE_ORDER_FIND_CANCEL);
				log.setCreater(userid);
				log.setLoglevel(LogLevel.LOG_LEVEL_INFO);
				log.setLogcontent("询单取消发布");
				systemLogService.addToCache(log);
				
				return "已取消";
			}else{
				return "询单状态异常,fid:"+fid+",OrderStatusEnum="+of.getStatus();
			}
			
		}else{
			throw new ServiceException(ErrorCode.OPERATING_RESTRICTIONS,"无效询单，不能取消操作");
		}

	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindService#updateOrderAllInfo(com.appabc.bean.bo.OrderFindAllBean)  
	 */
	@Override
	public void updateOrderAllInfo(OrderFindAllBean orderAllInfo)
			throws ServiceException {
		updateOrderAllInfo(orderAllInfo.getOfBean(), orderAllInfo.getOpiBean(), orderAllInfo.getPpcList(), StringUtils.EMPTY);
		TOrderAddress orderAddressInfo = orderAddressService.queryByFid(orderAllInfo.getOfBean().getId());
		TOrderAddress newOrderAddressInfo = orderAllInfo.getOaBean();
		String oid = StringUtils.EMPTY;
		if(orderAddressInfo != null){
			orderAddressInfo.setAddress(newOrderAddressInfo.getAddress());
			orderAddressInfo.setAreacode(newOrderAddressInfo.getAreacode());
			orderAddressInfo.setCid(newOrderAddressInfo.getCid());
			orderAddressInfo.setDeep(newOrderAddressInfo.getDeep());
			orderAddressInfo.setFid(orderAllInfo.getOfBean().getId());
			orderAddressInfo.setRealdeep(newOrderAddressInfo.getRealdeep());
			orderAddressInfo.setOid(newOrderAddressInfo.getOid());
			orderAddressInfo.setShippington(newOrderAddressInfo.getShippington());
			orderAddressInfo.setType(newOrderAddressInfo.getType());
			orderAddressService.modify(orderAddressInfo);
			oid = orderAddressInfo.getId();
		} else {
			orderAddressService.add(newOrderAddressInfo);
			oid = newOrderAddressInfo.getId();
		}
		String addressImgIds = newOrderAddressInfo.getAddressImgIds();
		uploadImagesService.updateOtypeAndOidBatch(oid, FileOType.FILE_OTYPE_ADDRESS_ORDER, StringUtils.isEmpty(addressImgIds) ? null : addressImgIds.split(","));
	}

	/* (non-Javadoc)询单更新
	 * @see com.appabc.datas.service.order.IOrderFindService#updateOrderAllInfo(com.appabc.bean.pvo.TOrderFind, com.appabc.bean.pvo.TOrderProductInfo, java.util.List, java.lang.String)
	 */
	public void updateOrderAllInfo(TOrderFind ofBean, TOrderProductInfo opiBean,List<ProductPropertyContentBean> ppcList, String addressid) throws ServiceException {
		
		TOrderInfo queryEntity = new TOrderInfo();
		queryEntity.setFid(ofBean.getId());
		if(ofBean.getStarttime() != null && ofBean.getEndtime() != null){
			if(ofBean.getEndtime().before(DateUtil.getTodayDateOfYMD())){
				throw new ServiceException(ErrorCode.DATE_NOWTIME_AFTER_ENDTIME, "结束日期不能在今天之前，ofBean.getEndtime()="+ofBean.getEndtime());
			}else if(ofBean.getEndtime().before(ofBean.getStarttime())){
				throw new ServiceException(ErrorCode.DATE_STARTTIME_AFTER_ENDTIME, "开始日期不能大于结束日期，ofBean.getEndtime()="+ofBean.getEndtime());
			}
		}else{
			throw new ServiceException(ErrorCode.DATA_IS_NOT_COMPLETE,"开始时间和结束时间不能为空，ofBean.getStarttime()="+ofBean.getStarttime()+",ofBean.getEndtime()="+ofBean.getEndtime());
		}
		
		if(ofBean.getTotalnum() == null || ofBean.getTotalnum()<=0){
			throw new ServiceException("成交量错误，totalnum="+ofBean.getTotalnum());
		}else if(ofBean.getTotalnum() > SystemConstant.MAX_TOTAL_AMOUNT){
			throw new ServiceException(ServiceErrorCode.EXCEEDS_MAX_TOTAL_AMOUNT, "超过了最大量,maxTotalNum="+SystemConstant.MAX_TOTAL_AMOUNT+",this totalNum="+ofBean.getTotalnum());
		}
		
		if(ofBean.getPrice() == null || ofBean.getPrice()<=0){
			throw new ServiceException("单价错误，price="+ofBean.getTotalnum());
		}else if(ofBean.getPrice() > SystemConstant.MAX_UNIT_PRICE){
			throw new ServiceException(ServiceErrorCode.EXCEEDS_MAX_UNIT_PRICE, "超过了最大单价,max price="+SystemConstant.MAX_UNIT_PRICE+",this price="+ofBean.getPrice());
		}
		
		TOrderFind of = this.orderFindDao.query(ofBean.getId());
		if(of != null){
			
			if(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID.equals(of.getOverallstatus())){
				throw new ServiceException(ErrorCode.OPERATING_RESTRICTIONS, "操作受限制，已失效的询单不能修改");
			}
			
			OrderAllInfor oai = new OrderAllInfor(); // 仅作属性存储，用于生成询单标题
			
			of.setTitle(ofBean.getTitle());
			of.setAddresstype(ofBean.getAddresstype());
			of.setPrice(ofBean.getPrice());
			of.setTotalnum(ofBean.getTotalnum());
			of.setStarttime(ofBean.getStarttime());
			of.setEndtime(ofBean.getEndtime());
			of.setMorearea(OrderMoreAreaEnum.ORDER_MORE_AREA_NO);
			of.setArea(ofBean.getArea());
			of.setLimitime(ofBean.getLimitime());
			of.setRemark(ofBean.getRemark());
			of.setUpdater(ofBean.getUpdater());
			
			/****商品处理****************************/
			TOrderProductInfo opiEntity = copyBeanProductInfoToOrderProductInfo(opiBean.getPid()); //复制部分属性
			TOrderProductInfo opiQuery = new TOrderProductInfo();
			opiQuery.setFid(of.getId());
			List<TOrderProductInfo> opiList = this.orderProductInfoService.queryForList(opiQuery); // 查询原关联商品，正常情况只有一个

			if(opiList != null && opiList.size()>0 && opiList.get(0) != null) { // 原商品信息
				opiEntity.setId(opiList.get(0).getId()); // 将原属性ID存放在将要更新的BEAN中
				/*****商品属性处理**********************/
				TOrderProductProperty entity = new TOrderProductProperty();
				entity.setPpid(Integer.parseInt(opiEntity.getId()));
				this.orderProductPropertyDao.delete(entity); // 删除原商品的属性,包括商品规格

				for(ProductPropertyContentBean ppc : ppcList){ // 商品属性保存
					if(StringUtils.isNotEmpty(ppc.getPpid()) && StringUtils.isNotEmpty(ppc.getContent())){
						TOrderProductProperty oppBean = copyBeanProductPropertyToOrderProductProperty(ppc.getPpid());
						oppBean.setPpid(Integer.valueOf(opiEntity.getId()));
						oppBean.setContent(ppc.getContent());
						this.orderProductPropertyDao.save(oppBean); // 询单商品属性新增
					}else{
						logger.info("商品发布，商品属性对应信息为空ppid="+ppc.getPpid()+",content="+ppc.getContent());
					}
				}

				/*********商品规格存储********************/
				TOrderProductProperty psize = copyBeanProductPropertyToOrderProductProperty(opiEntity.getPsize()); // 将商品规格属性复制
				psize.setPpid(Integer.valueOf(opiEntity.getId()));
				this.orderProductPropertyDao.save(psize); // 商品规格存储
				oai.setPsize(psize);
				
				// 更新交易中的商品规格ID到交易中的商品表
				opiEntity.setPsize(psize.getId());

				/****商品基本信息*************/
				opiEntity.setPcolor(opiBean.getPcolor());
				opiEntity.setPremark(opiBean.getPremark());
				opiEntity.setPaddress(opiBean.getPaddress());
				opiEntity.setFid(ofBean.getId());

				opiEntity.setUnit(opiBean.getUnit());
				opiEntity.setPremark(opiBean.getPremark());

				this.orderProductInfoService.modify(opiEntity);
			}

			// 商品图片信息更新
			String[] imgIds = null;
			if(StringUtils.isNotEmpty( opiBean.getProductImgIds())){
				imgIds = opiBean.getProductImgIds().split(",");
			}
			this.uploadImagesService.updateOtypeAndOidBatch(opiEntity.getId(), FileOType.FILE_OTYPE_PRODUCT_ORDER, imgIds);

			/****卸货地址图片更新*******/
			if(OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_OWN.equals(ofBean.getAddresstype()) && StringUtils.isNotEmpty(addressid)){ // 己文指定，并且卸货地址做了修改
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
			}else if(OrderAddressTypeEnum.ORDER_ADDRESS_TYPE_OTHER.equals(ofBean.getAddresstype())){ // 对方指定时删除卸货地址
				TOrderAddress caEntity  = new TOrderAddress();
				caEntity.setFid(ofBean.getId());
				List<TOrderAddress> caList = this.orderAddressService.queryForList(caEntity);
				if(caList != null && caList.size()>0 && caList.get(0) != null){
					this.orderAddressService.delete(caList.get(0).getId());
				}
			}
			
			/****用户拼写商品TITLE********************************/
			oai.setPcode(opiEntity.getPcode());
			oai.setPtype(opiEntity.getPtype());
			oai.setUnit(opiBean.getUnit());
			oai.setTotalnum(ofBean.getTotalnum());
			oai.setPrice(ofBean.getPrice());
			if(of.getType() != null){
				oai.setType(of.getType().getVal());
			}
			// 询单标题拼写
			of.setTitle(spellOrderFindTitle(oai));
			
			this.orderFindDao.update(of);// 更新询单基本信息
			
			/******日志************************/
			TSystemLog log = new TSystemLog();
			log.setBusinessid(ofBean.getId());
			log.setBusinesstype(LogBusinessType.BUSINESS_TYPE_ORDER_FIND_UPDATE);
			log.setCreater(ofBean.getUpdater());
			log.setLoglevel(LogLevel.LOG_LEVEL_INFO);
			log.setLogcontent("询单修改");
			systemLogService.addToCache(log);
			
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
		if(ca != null){
			oa = new TOrderAddress();
			oa.setAddress(ca.getAddress());
			oa.setAreacode(ca.getAreacode());
			oa.setCid(ca.getCid());
			oa.setCreatime(Calendar.getInstance().getTime());
			oa.setDeep(ca.getDeep());
			oa.setFid(fid);
			oa.setLatitude(ca.getLatitude());
			oa.setLongitude(ca.getLongitude());
			oa.setRealdeep(ca.getRealdeep());
			oa.setShippington(ca.getShippington());
//			oa.setType(type);
			this.orderAddressService.add(oa);

			// 图片信息复制保存
			List<TUploadImages> uiList = this.uploadImagesService.getListByOidAndOtype(caid, FileInfo.FileOType.FILE_OTYPE_ADDRESS.getVal());
			if(CollectionUtils.isNotEmpty(uiList)){
				this.uploadImagesService.copyUploadImagesRelationshipsObject(oa.getId(), FileOType.FILE_OTYPE_ADDRESS_ORDER, uiList);
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
			List<TPublicCodes> codeList = publicCodesService.queryListByCode(SystemConstant.CODE_GOODS_TYPE, null);

			title.append(OrderTypeEnum.enumOf(oai.getType()).getText());
			
			
			StringBuilder titleP = new StringBuilder();
			if(codeList !=  null && StringUtils.isNotEmpty(oai.getPcode())){ // 商品大类名称
				for(TPublicCodes code : codeList){
					if(code.getVal() != null && code.getVal().equals(oai.getPcode())){
						titleP.append(code.getName());
						break;
					}
				}
			}
			
			if(codeList !=  null && StringUtils.isNotEmpty(oai.getPtype())){ // 商品子类名称（黄砂）
				for(TPublicCodes code : codeList){
					if(code.getVal() != null && code.getVal().equals(oai.getPtype())){
						titleP.append("·").append(code.getName());
						break;
					}
				}
			}
			
			if(oai.getPsize() != null && StringUtils.isNotEmpty(oai.getPsize().getName())){ // 规格
				titleP.append("·").append(oai.getPsize().getName());
				String spec = "("+oai.getPsize().getMinv()+"-"+oai.getPsize().getMaxv()+")";
				if(oai.getPsize().getUnit() != null){// 规格单位
					spec += oai.getPsize().getUnit().getText();
				}
				spec = spec.replace("(0.0-0.0)mm", "").replace("(46.0-0.0)mm", "(≥46.0)mm"); // 替换卵石和片石正确规格
				titleP.append(spec);
			}
			
			oai.setpTypeName(titleP.toString());
			title.append(titleP);
			
			String unit = "";
			if(oai.getUnit() != null){
				unit = oai.getUnit().getText();
			}
			
			if(oai.getTotalnum() != null){ // 总量
				title.append(" ")
				.append(oai.getTotalnum()).append(unit);
			}
			
			if(oai.getPrice() != null){ // 单价
				title.append("，")
				.append(oai.getPrice()).append("元/").append(unit);
			}
			
			return title.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 赋值
	 * @param fromQueryContent
	 * @param toQueryContent
	 */
	private void assignmentQueryContent(QueryContext<?> fromQueryContent, QueryContext<?> toQueryContent){
		toQueryContent.setOrder(fromQueryContent.getOrder());
		toQueryContent.setOrderColumn(fromQueryContent.getOrderColumn());
		toQueryContent.setPage(fromQueryContent.getPage());
		toQueryContent.setParameters(fromQueryContent.getParameters());
		toQueryContent.setParamList(fromQueryContent.getParamList());
	}

	/* (non-Javadoc)询单自动匹配列表
	 * @see com.appabc.datas.service.order.IOrderFindService#queryMatchingObjectByFidForPagination(com.appabc.common.base.QueryContext, java.lang.String, boolean, boolean, boolean)
	 */
	@Override
	public QueryContext<MatchingBean> queryMatchingObjectByFidForPagination(
			QueryContext<MatchingBean> qContext, String fid, boolean isApply, boolean isOrderFid, boolean isIdentity) throws ServiceException {
		
		if(StringUtils.isNotEmpty(fid)){
			qContext.addParameter("fid", fid);
		}
		
		if(qContext.getParameter("fid") == null){
			throw new ServiceException("query Failure，fid is null。");
		}
		if(isApply==false && isOrderFid==false && isIdentity==false ){
			throw new ServiceException("未设置撮合条件，不能进行匹配！");
		}
		
		QueryContext<TOrderFind> qContextOrderFind = new QueryContext<TOrderFind>();
		assignmentQueryContent(qContext, qContextOrderFind);
		qContextOrderFind = this.orderFindDao.queryMatchingObjectByCidForPagination(qContextOrderFind, isApply, isOrderFid, isIdentity);
		assignmentQueryContent(qContextOrderFind, qContext);
		
		QueryResult<MatchingBean> queryResult = new QueryResult<MatchingBean>();
		if(qContextOrderFind.getQueryResult() != null && qContextOrderFind.getQueryResult().getResult() != null){
			List<TOrderFind> ofList = qContextOrderFind.getQueryResult().getResult();
			if(ofList != null){
				List<MatchingBean> result = new ArrayList<MatchingBean>();
				for(TOrderFind of : ofList){
					result.add(of.getMatchingBean());
				}
				queryResult.setResult(result);
				queryResult.setResultParam(qContextOrderFind.getQueryResult().getResultParam());
				queryResult.setTotalSize(qContextOrderFind.getQueryResult().getTotalSize());
			}
		}
		
		qContext.setQueryResult(queryResult);
		
		return qContext;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#copyOrderAllInforByFid(java.lang.String)
	 */
	@Override
	@Deprecated
	public String copyOrderAllInforByFid(String fid) {
		logger.debug("询单信息复制 begin，fid="+fid);
		TOrderFind of = this.orderFindDao.query(fid);
		if(of != null){
			try {
				/*****复制询单的基本信息*************************/
				TOrderFind ofNew = (TOrderFind) of.clone();
				ofNew.setId(null);
				this.orderFindDao.save(ofNew);
				
				of.setParentid(ofNew.getId());
				this.orderFindDao.update(of); // 将询单副本与原询单建立父子关系（原为父，新为子）
				
				/*****复制询单的商品信息*************************/
				TOrderProductInfo opiEntity = new TOrderProductInfo();
				opiEntity.setFid(fid);
				TOrderProductInfo opi = this.orderProductInfoService.query(opiEntity);
				if(opi != null){
					this.orderProductInfoService.copyOrderProductAllInfo(Integer.parseInt(opi.getId()));
				}else{
					logger.error("该询单无商品信息，fid=" + fid);
				}
				
				/*****复制询单的卸货地址信息*********************/
				TOrderAddress oaEntity = new TOrderAddress();
				oaEntity.setFid(fid);
				TOrderAddress oa = orderAddressService.query(oaEntity);
				this.orderAddressService.copyTOrderAddressRelationshipsFid(ofNew.getId(), oa);
				
				logger.debug("询单信息复制 end，newFid="+ofNew.getId());
				return ofNew.getId();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#buildRelationshipsOfOrderFind(java.lang.String, java.lang.String)
	 */
	@Override
	public void buildRelationshipsOfOrderFind(String parentId, String childId) throws ServiceException {
		if(StringUtils.isNotEmpty(parentId) && StringUtils.isNotEmpty(childId)){
			TOrderFind of = this.orderFindDao.query(childId);
			if(of != null){
				of.setParentid(parentId);
				this.orderFindDao.update(of);
			}else{
				throw new ServiceException("子询单不存在，childId="+childId);
			}
		}else{
			throw new ServiceException("询单ID为空，parentId="+parentId+",childId="+childId);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#rollbackOrderFindByContractid(java.lang.String)
	 */
	@Override
	public void rollbackOrderFindByContractid(String contractId) {
		if(StringUtils.isNotEmpty(contractId)){
			TOrderFind entity = new TOrderFind();
			entity.setContractid(contractId);
			
			List<TOrderFind> ofList = this.orderFindDao.queryForList(entity);
			if(CollectionUtils.isNotEmpty(ofList)){
				for (TOrderFind of: ofList) {
					
					// 将原询单与合同解除绑定，并进行状态回滚
					if(OrderStatusEnum.ORDER_STATUS_DELETE != of.getStatus()){ // 已删除的询单不回滚
						of.setContractid(null);  // 取消询单与合同的关系
//						of.setNum(of.getTotalnum()); // 恢复量
						of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
						of.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
						
						this.orderFindDao.update(of);
					}
					
					// 将副本询单设置为无效
					TOrderFind fb = new TOrderFind();
					fb.setParentid(of.getId());
					fb.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
					fb.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
					
					fb = this.orderFindDao.query(fb);
					if(fb != null){
						fb.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
						fb.setStatus(OrderStatusEnum.ORDER_STATUS_COPY_FAILURE);
						this.orderFindDao.update(fb);
					}else{
						/******日志************************/
						TSystemLog log = new TSystemLog();
						log.setBusinessid(contractId);
						log.setBusinesstype(LogBusinessType.BUSINESS_TYPE_ORDER_FIND_ROLLBACK);
						log.setCreater(null);
						log.setLoglevel(LogLevel.LOG_LEVEL_WARN);
						log.setLogcontent("合同取消起草--询单回滚时，无子询单。contractId="+contractId+",fb.setParentid="+of.getId());
						systemLogService.addToCache(log);
					}
				}
			}
			
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#buildRelationshipsOfContractAndOrderFind(java.lang.String, java.lang.String)
	 */
	@Override
	public void buildRelationshipsOfContractAndOrderFind(String contractId,
			String fid) throws ServiceException {
		
		if(StringUtils.isNotEmpty(contractId) && StringUtils.isNotEmpty(fid)){
			TOrderFind of = this.orderFindDao.query(fid);
			if(of != null){
				of.setContractid(contractId);
				of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
				of.setStatus(OrderStatusEnum.ORDER_STATUS_CLOSE);
				
				// 询单被撮合次数+1
				if (of.getMatchingnum() == null){ 
					of.setMatchingnum(1);
				}else{ 
					of.setMatchingnum(of.getMatchingnum() + 1);
				}
				
				this.orderFindDao.update(of);
			}else{
				throw new ServiceException("不存在的询单fid="+fid);
			}
			
		}else{
			throw new ServiceException("ID为空，contractId="+contractId+",fid="+fid);
		}
		
		
	}
	
	/* (non-Javadoc)找买找卖列表
	 * @see com.appabc.datas.service.order.IOrderFindService#queryOrderListForPagination(com.appabc.common.base.QueryContext, com.appabc.bean.bo.OrderFindQueryParamsBean, java.lang.String)
	 */
	@Override
	public QueryContext<TOrderFind> queryOrderListForPagination(
			QueryContext<TOrderFind> qContext, OrderFindQueryParamsBean ofqParam, String requestCid) {
		
		qContext.addParameter("overallstatus", OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE.getVal());// 有效询单
		qContext.addParameter("queryMethod", "getOrderList");// 判断条件
		
		QueryContext<TOrderFind> qc = this.orderFindDao.queryOrderListForPagination(qContext, ofqParam);
		
		if(qc.getQueryResult() != null && qc.getQueryResult().getResult() != null){
			List<TOrderFind> resultList = qc.getQueryResult().getResult();
			for(TOrderFind of : resultList){
				// 添加是否已交易询盘标致
				if(StringUtils.isNotEmpty(requestCid)){
					int isApply = this.orderFindItemService.getIsApplyByCid(requestCid, of.getId());
					of.setIsApply(isApply);
				}
				
				// 添加询单发布者的认证状态
				TCompanyInfo ci = this.companyInfoService.queryAuthCmpInfo(of.getCid());
				if(ci != null){
					of.setAuthstatus(ci.getAuthstatus());
				}
				
				// 添加询单发布者的保证金缴纳状态
				float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
				of.setBailstatus(guarantStatusCheck.checkCashDeposit(ci.getId(), monay));
			}
			
		}
		
		return qc;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#delMark(java.lang.String, java.lang.String)
	 */
	@Override
	public void delMark(String fid, String userid) throws ServiceException {
		
		TOrderFind of = this.orderFindDao.query(fid);
		if(of != null){
			if(of.getOverallstatus() != null){
				if(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID.equals(of.getOverallstatus())){ // 询单更新为删除状态
					of.setStatus(OrderStatusEnum.ORDER_STATUS_DELETE); 
					of.setUpdater(userid);
					this.orderFindDao.update(of);
				}else{
					throw new ServiceException("有效询单不能删除,fid="+fid+",overallstatus="+of.getOverallstatus());
				}
			}else{
				throw new ServiceException("询单状态异常,fid="+fid+",overallstatus="+of.getOverallstatus());
			}
		}else{
			throw new ServiceException("询单不存在,fid="+fid);
		}
				
		
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#queryNewListForTask()
	 */
	@Override
	public List<TOrderFind> queryNewListForTask() {
		return this.orderFindDao.queryNewListForTask();
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#queryInvalidListForTask()
	 */
	@Override
	public List<TOrderFind> queryInvalidListForTask() {
		return this.orderFindDao.queryInvalidListForTask();
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindService#getParentOrderFindByStatusAndOverallStatus(com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum, com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum)  
	 */
	@Override
	public List<TOrderFind> getParentOrderFindByStatusAndOverallStatus(OrderStatusEnum ose, OrderOverallStatusEnum oose) {
		return orderFindDao.queryParentOrderFindByStatusAndOverallStatus(ose, oose);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.order.IOrderFindService#jobAutoTimeoutOrderFindWithSystem(com.appabc.bean.pvo.TOrderFind)  
	 */
	@Override
	public void jobAutoTimeoutOrderFindWithSystem(TOrderFind bean,String cid) throws ServiceException{
		if(bean == null || StringUtils.isEmpty(cid)){
			return ;
		}
		Date now = DateUtil.getNowDate();
		log.info(" set the order find id is : "+bean.getId()+" to the order status invalid and failure. ");
		bean.setUpdater(cid);
		bean.setUpdatetime(now);
		bean.setStatus(OrderStatusEnum.ORDER_STATUS_FAILURE);
		bean.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		modify(bean);
		SystemMessageContent smc = SystemMessageContent.getMsgContentOfOrderFindTimeout(bean.getTitle(),now);
		MessageInfoBean mi = new MessageInfoBean(
				MsgBusinessType.BUSINESS_TYPE_ORDER_FIND,
				bean.getId(),
				bean.getCid(),
				smc);
		mi.setSendSystemMsg(true);
		mi.setSendPushMsg(true);
		mesgSender.msgSend(mi);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#getFidByContractId(java.lang.String)
	 */
	@Override
	public String getFidByContractId(String contractId) throws ServiceException {
		if(StringUtils.isEmpty(contractId)) throw new ServiceException("合同ID不能为空");
		TOrderFind of = new TOrderFind();
		of.setContractid(contractId);
		of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		of = this.orderFindDao.query(of);
		if(of != null){
			return of.getId();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#getFidByContractId(java.lang.String)
	 */
	@Override
	public String getParentFidByChildFid(String childFid) throws ServiceException {
		
		if(StringUtils.isEmpty(childFid)) throw new ServiceException("子询单ID不能为空");
		
		TOrderFind of = this.query(childFid);
		
		if(of ==  null) throw new ServiceException("子询单不存在,childFid="+childFid);
		
		return of.getParentid();
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#addMatchingNum(java.lang.String)
	 */
	@Override
	public int addMatchingNum(String fid) {
		return this.orderFindDao.addMatchingNum(fid);
	}
	
	/* (non-Javadoc)批量取消所有草稿
	 * @see com.appabc.datas.service.order.IOrderFindService#cancelAllDraftsByParentid(java.lang.String)
	 */
	@Override
	public void cancelAllDraftsByParentid(String fid) throws ServiceException {
		if(StringUtils.isEmpty(fid) || "0".equals(fid)){
			throw new ServiceException("询单ID不能为空");
		}
		TOrderFind entity = new TOrderFind();
		entity.setParentid(fid);
		entity.setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_DRAFT);
		
		List<TOrderFind> ofList = this.queryForList(entity);
		
		for(TOrderFind of : ofList){
			of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
			of.setStatus(OrderStatusEnum.ORDER_STATUS_CLOSE);
			
			this.modify(of);
		}
		
	}
	
	/* (non-Javadoc)批量恢复所有草稿
	 * @see com.appabc.datas.service.order.IOrderFindService#recoverAllDraftsByParentid(java.lang.String)
	 */
	@Override
	public void recoverAllDraftsByParentid(String fid) throws ServiceException {
		if(StringUtils.isEmpty(fid) || "0".equals(fid)){
			throw new ServiceException("询单ID不能为空");
		}
		
		TOrderFind entity = new TOrderFind();
		entity.setParentid(fid);
		entity.setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_DRAFT);
		
		List<TOrderFind> ofList = this.queryForList(entity);
		
		for(TOrderFind of : ofList){
			of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
			of.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
			
			this.modify(of);
		}
	}
	
	/* (non-Javadoc)失效单个草稿询单
	 * @see com.appabc.datas.service.order.IOrderFindService#cancelDraftsById(java.lang.String)
	 */
	@Override
	public void cancelDraftsById(String fid) throws ServiceException {
		if(StringUtils.isEmpty(fid)){
			throw new ServiceException("草稿询单ID不能为空");
		}
		
		TOrderFind of = this.query(fid);
		if(of == null) throw new ServiceException("草稿询单不存在,fid="+fid);
		if(of.getOveralltype() != OrderOverallTypeEnum.ORDER_OVERALL_TYPE_DRAFT) throw new ServiceException("该询单不是草稿询单,fid="+fid);
		
		of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		of.setStatus(OrderStatusEnum.ORDER_STATUS_CANCEL);
		
		this.modify(of);
		
	}
	
	/* (non-Javadoc)恢复单个草稿询单
	 * @see com.appabc.datas.service.order.IOrderFindService#recoverDraftsById(java.lang.String)
	 */
	@Override
	public void recoverDraftsById(String fid) throws ServiceException {
		if(StringUtils.isEmpty(fid)){
			throw new ServiceException("草稿询单ID不能为空");
		}
		
		TOrderFind of = this.query(fid);
		if(of == null) throw new ServiceException("草稿询单不存在,fid="+fid);
		if(of.getOveralltype() != OrderOverallTypeEnum.ORDER_OVERALL_TYPE_DRAFT) throw new ServiceException("该询单不是草稿询单,fid="+fid);
		
		of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
		of.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
		
		this.modify(of);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#queryParentOrderFindOfInstead(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<OrderFindInsteadBean> queryParentOrderFindOfInsteadListForPagination(
			QueryContext<OrderFindInsteadBean> qContext) {
		if(qContext == null) qContext = new QueryContext<OrderFindInsteadBean>();
		
		QueryContext<TOrderFind> qContextOrderFind = new QueryContext<TOrderFind>();
		assignmentQueryContent(qContext, qContextOrderFind);
		qContextOrderFind = this.orderFindDao.queryParentOrderFindOfInsteadListForPagination(qContextOrderFind);
		assignmentQueryContent(qContextOrderFind, qContext);
		
		QueryResult<OrderFindInsteadBean> queryResult = new QueryResult<OrderFindInsteadBean>();
		if(qContextOrderFind.getQueryResult() != null && qContextOrderFind.getQueryResult().getResult() != null){
			List<TOrderFind> ofList = qContextOrderFind.getQueryResult().getResult();
			if(ofList != null){
				List<OrderFindInsteadBean> result = new ArrayList<OrderFindInsteadBean>();
				for(TOrderFind of : ofList){
					result.add(of.getOrderFindInsteadBean());
				}
				queryResult.setResult(result);
				queryResult.setResultParam(qContextOrderFind.getQueryResult().getResultParam());
				queryResult.setTotalSize(qContextOrderFind.getQueryResult().getTotalSize());
			}
		}
		
		qContext.setQueryResult(queryResult);
		
		return qContext;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.order.IOrderFindService#queryCount(com.appabc.bean.pvo.TCompanyInfo)
	 */
	@Override
	public int queryCount(TOrderFind entity) {
		if(entity == null) entity = new TOrderFind();
		return this.orderFindDao.queryCount(entity);
	}

}
