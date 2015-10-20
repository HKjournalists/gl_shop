package com.appabc.datas.contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.bo.ContractArbitrationBean;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.ProductPropertyContentBean;
import com.appabc.bean.enums.ContractInfo;
import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.bean.enums.ContractInfo.ContractCancelType;
import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
import com.appabc.bean.enums.ContractInfo.ContractWebCmsTradeType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TCalRuleDefinition;
import com.appabc.bean.pvo.TCalRuleUse;
import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderArbitrationResult;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderCostdetail;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.dao.contract.ICalRuleDefinitionDAO;
import com.appabc.datas.dao.contract.ICalRuleUseDAO;
import com.appabc.datas.dao.contract.IContractArbitrationDAO;
import com.appabc.datas.dao.contract.IContractArbitrationResultDAO;
import com.appabc.datas.dao.contract.IContractCancelDAO;
import com.appabc.datas.dao.contract.IContractCostDetailDAO;
import com.appabc.datas.dao.contract.IContractDisPriceDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderAddressService;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindMatchService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.IOrderProductInfoService;
import com.appabc.datas.service.order.IOrderProductPropertyService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.EveryUtil;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.ToolsConstant;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create Date  : 2014年9月2日 下午8:35:03
 */

public class ContractTest extends AbstractDatasTest {

	@Autowired
	private PrimaryKeyGenerator prkg;

	@Autowired
	private IContractInfoDAO IContractInfoDAO;

	@Autowired
	private IContractOperationDAO IContractOperationDAO;

	@Autowired
	private IContractDisPriceDAO IContractDisPriceDAO;

	@Autowired
	private IContractCostDetailDAO IContractCostDetailDAO;

	@Autowired
	private IContractCancelDAO IContractCancelDAO;

	@Autowired
	private IContractArbitrationResultDAO IContractArbitrationResultDAO;

	@Autowired
	private IContractArbitrationDAO IContractArbitrationDAO;

	@Autowired
	private ICalRuleUseDAO ICalRuleUseDAO;

	@Autowired
	private ICalRuleDefinitionDAO ICalRuleDefinitionDAO;

	@Autowired
	private IOrderFindItemService orderFindItemService;

	@Autowired
	private IOrderFindService orderFindService;

	@Autowired
	private IContractInfoService iContractInfoService;
	
	@Autowired
	private IOrderProductInfoService iOrderProductInfoService;
	
	@Autowired
	private IOrderProductPropertyService iOrderProductPropertyService;
	
	@Autowired
	private IContractCancelService iContractCancelService;
	
	@Autowired
	private IOrderAddressService iOrderAddressService;
	
	@Autowired
	private ICompanyRankingService iCompanyRankingService;
	
	@Autowired
	private IContractArbitrationService iContractArbitrationService;
	
	@Autowired
	private IOrderFindMatchService iOrderFindMatchService;

	private Map<Integer,Boolean> runningStatus = new HashMap<Integer,Boolean>();

	private boolean isRunning = false;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
		/*double totalA = 2000.0;
		double finalB = 1300.0;
		boolean b = EveryUtil.EqNumNotHalfBetweenAandB(totalA, finalB);
		log.info(b);*/
		//testInsert();
		//testUpdate("ORDERID020204702092014END");
		//testSelect();
		//testDelete();
		//testContractPayQuery();
		//testContractPayDelete();
		//testContractOperationSave();
		//testContractOperationQuery();
		//testContractOperationUpdate();
		//testContractOperationDelete();
		//testContractCostDetailDelete();
		//testContractArbitrationSave();
		/*for(int i = 0; i < 50; i ++){
			testCalRuleDefinitionQuery();
		}*/
		//testCalRuleDefinitionQuery();
		//for(int i = 0; i < 5; i ++){
			//testContractInfoInsert();
		//}
		//this.testContractInfoInsert();
//		this.testContractInfoUpdate("201409230007223");
//		testCreateContract(20);
		
		/*testCreateContractByFid("101220140000109","241120140000018");
		
		testCreateContractByFid("101220140000110","241120140000018");
		
		testCreateContractByFid("101220140000111","241120140000018");
		
		testCreateContractByFid("101220140000112","241120140000018");
		
		testCreateContractByFid("101220140000113","241120140000018");
		
		testCreateContractByFid("101220140000114","241120140000018");
		
		testCreateContractByFid("101220140000115","241120140000018");*/
		
		/*testCreateContractByFid("111220140000136","241120140000018");
		
		testCreateContractByFid("111220140000135","241120140000018");
		
		testCreateContractByFid("111220140000134","241120140000018");
		
		testCreateContractByFid("111220140000133","241120140000018");
		
		testCreateContractByFid("121220140000148","281120140000024");*/
		//testCreateContractByFid("251220140000180","241120140000017");
		//testContractArbitractionProcess();
//		getMatchingNum();
		//testCancelDraftContract();
		//testMakeAndMatchATOrderInfo();
		//testMakeAndMatchATOrderInfoWithCustomService();
		//testCancelDraftContract();
		//testToContractArbitration();
		//testQueryContractListToWebCms();
		//testFindOrderFindMatchEx();
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		//testCaseGetContractArbitrationInfoList();
		//testCaseGetContractArbitrationInfoListForPagination();
	}
	
	public void testFindOrderFindMatchEx(){
		String owner = "201505210000034";
		//iOrderFindMatchService.findOrderFindMatchExInfo(owner, status);
		List<TOrderFindMatchEx> result = iOrderFindMatchService.findOrderFindMatchExInfo(owner, OrderFindMatchStatusEnum.SUCCESS);
		log.info(result);
		List<TOrderFindMatch> res = iOrderFindMatchService.findOrderFindMatchInfo(owner, OrderFindMatchStatusEnum.SUCCESS);
		log.info(res);
	}
	
	public void testSaveOrderFindMatchInfo(){
		String target = "201503020000023";
		String operator = "201501270000021";
		String fid = "201506050000416";//201505270000403
		OrderFindAllBean ofabean = new OrderFindAllBean();
		
		TOrderFind orderFind = orderFindService.query(fid);
		ofabean.setOfBean(orderFind);
		
		TOrderProductInfo queryEntity = new TOrderProductInfo();
		queryEntity.setFid(fid);
		TOrderProductInfo productInfo = this.iOrderProductInfoService.query(queryEntity);
		ofabean.setOpiBean(productInfo);
		
		TOrderProductProperty propis = new TOrderProductProperty();
		propis.setPpid(Integer.valueOf(productInfo.getId()));
		List<TOrderProductProperty> result = iOrderProductPropertyService.queryForList(propis);
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		for(TOrderProductProperty t : result){
			ProductPropertyContentBean e = new ProductPropertyContentBean();
			e.setPpid(t.getPproid());
			e.setContent(t.getContent());
			ppcList.add(e);
		}
		ofabean.setPpcList(ppcList);
		
		TOrderAddress addressInfo = this.iOrderAddressService.queryByFid(fid);
		if(addressInfo == null){
			addressInfo = new TOrderAddress();
			addressInfo.setCid(orderFind.getCid());
			addressInfo.setAreacode("130524000000");
			addressInfo.setAddress("DD镇CC村");
			addressInfo.setDeep(6.51f);
			addressInfo.setShippington(2000.63f);
		}
		ofabean.setOaBean(addressInfo);
		TOrderFindMatch tofm = null;
		try {
			tofm = iOrderFindMatchService.saveOrderFindMatchInfo(ofabean, target, OrderFindMatchOpTypeEnum.RELEASEORDERFIND, OrderFindMatchStatusEnum.SAVE, operator);
			log.info(ofabean);
			log.info(tofm);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.info(e);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			tofm.setOpType(OrderFindMatchOpTypeEnum.TRADEINQUIRY);
			tofm.setStatus(OrderFindMatchStatusEnum.SUCCESS);
			ofabean.getOfBean().setUpdatetime(DateUtil.getNowDate());
			ofabean.getOfBean().setUpdater(operator);
			iOrderFindMatchService.updateFindMatchInfo(ofabean, tofm);
			log.info(ofabean);
			log.info(tofm);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.info(e);
		}
	}
	
	public void testQueryContractListToWebCms(){
		QueryContext<TOrderInfo> qContext = new QueryContext<TOrderInfo>();
		qContext.getPage().setPageIndex(1);
		qContext.addParameter("cid", "201411270000014");
		qContext.addParameter("type", ContractWebCmsTradeType.PRESENT.getVal());
		qContext = iContractInfoService.queryContractListOfMineForPaginationToWebCms(qContext);
		log.info(qContext);
		log.info(qContext.getQueryResult().getResult());
	}
	
	public void testCaseGetContractArbitrationInfoListForPagination(){
		QueryContext<ContractArbitrationBean> qContext = new QueryContext<ContractArbitrationBean>();
		qContext.addParameter("status", ContractArbitrationStatus.REQUEST.getVal());
		/*qContext.getPage().setPageSize(3);
		qContext.getPage().setPageIndex(1);*/
		qContext.getPage().setPageIndex(2);
		qContext = iContractArbitrationService.getContractArbitrationInfoListForPagination(qContext);
		log.info(qContext);
	}
	
	public void testCaseGetContractArbitrationInfoList() {
		
		List<ContractArbitrationBean> result = iContractArbitrationService.getContractArbitrationInfoForList(ContractArbitrationStatus.SUCCESS);
		log.info(result);
		
	}
	
	public void testCaseFinalEstimate() throws ServiceException{
		String oid = "1020150204203";//1020150204203
		String cid = ToolsConstant.SYSTEMCID;
		String cname = ToolsConstant.SYSTEMCNAME;
		TOrderInfo bean = iContractInfoService.query(oid);
		Date now = DateUtil.getNowDate();
		// 更新合同的状态为正常结束
		bean.setUpdater(cid);
		bean.setUpdatetime(now);
		bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		bean.setStatus(ContractStatus.FINISHED);
		iContractInfoService.modify(bean);
		log.info(" Contract is "+oid+"; finished the Contract. ");
		
		// 合同完成结束的操作记录
		TOrderOperations finishOper = new TOrderOperations();
		finishOper.setId(iContractInfoService.getKey(DataSystemConstant.CONTRACTOPERATIONID));
		finishOper.setOid(oid);
		finishOper.setOperator(cid);
		finishOper.setOperationtime(now);
		finishOper.setType(ContractOperateType.CONTRACT_FINISHED);
		finishOper.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED);
		finishOper.setOldstatus(ContractLifeCycle.NORMAL_FINISHED);
		StringBuilder finishMesg = new StringBuilder(cname);
		finishMesg.append(iContractInfoService.getMessage(DataSystemConstant.MESSAGEKEY_FINISH_CONTRACT_TIPS));
		finishOper.setResult(finishMesg.toString());
		finishOper.setRemark(finishMesg.toString());
		IContractOperationDAO.save(finishOper);
		// save or update the mine contract with cid or oid.
		iContractInfoService.contractTimeOutMoveToFinishList(bean, cid);
		log.info(" Contract is "+oid+"; do the time out move to the finish list. ");
		// 计算交易成功率.
		iCompanyRankingService.calculateTradeSuccessRate(bean.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(bean.getSellerid());
		log.info(" Contract is "+oid+"; do the rank . ");
		//买家确认收货,触发条件发送消息告诉买家可以进行评价
		iContractInfoService.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractToEvaluation(bean.getRemark()));
		iContractInfoService.sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractToEvaluation(bean.getRemark()));
	}
	
	public void testContractArbitractionProcess(){
		try {
			iContractArbitrationService.contractArbitractionProcess(true, "201501080008408", "201411270000014", "国立123公司2014-11-27 20:35:52", 10, 10);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testQueryGoodsDisPriceHistory(){
		String oid = "201411250008625";
		List<TContractDisPriceOperation> result = IContractDisPriceDAO.queryGoodsDisPriceHistroyListWithFullTakeover(oid);
		this.log.info(result);
	}
	
	public void getMatchingNum(){
		int count = this.iContractInfoService.getMatchingNumByFid("201412110000097");
		System.out.println("count="+count);
	}
	
	public void testCalRuleDefinitionSave(){
		TCalRuleDefinition entity = new TCalRuleDefinition();
		entity.setId(prkg.generatorBusinessKeyByBid("ArbitrationResultID"));
		entity.setName("规则一");
		entity.setRule("ext");
		entity.setExpression("a*b*c");
		entity.setCreator("creator");
		entity.setCreatedate(new Date());
		entity.setRemark("remark");
		ICalRuleDefinitionDAO.save(entity);
	}
	public void testCalRuleDefinitionUpdate(){
		TCalRuleDefinition entity = ICalRuleDefinitionDAO.query("ARID00066END");
		entity.setName("规则二");
		entity.setRule("xmpp");
		entity.setExpression("a+b+c");
		ICalRuleDefinitionDAO.update(entity);
	}
	public void testCalRuleDefinitionQuery(){
		/*TCalRuleDefinition entity = new TCalRuleDefinition();
		entity.setName("规则二");
		entity.setRule("xmpp");
		List<TCalRuleDefinition> result = ICalRuleDefinitionDAO.queryForList(entity);*/

		QueryContext<TCalRuleDefinition> qContext = new QueryContext<TCalRuleDefinition>();
		qContext.getPage().setPageIndex(-1);
		qContext.setOrderColumn("RULEID");
		qContext.addParameter("creator", "creator");
		qContext.addParameter("rule", "ext1");
		qContext.addParameter("name", "规则一");
		qContext.addParameter("expression", "a*b*c");
		/*qContext = ICalRuleDefinitionDAO.queryListForPaginationForSQL(qContext);*/
		qContext = ICalRuleDefinitionDAO.queryListForPagination(qContext);
		log.info(qContext.getQueryResult());
	}
	public void testCalRuleDefinitionDelete(){
		ICalRuleDefinitionDAO.delete("ARID00067END");
	}
	public void testCalRuleUseSave(){
		TCalRuleUse entity = new TCalRuleUse();
		entity.setId(prkg.generatorBusinessKeyByBid("ArbitrationResultID"));
		Date now = new Date();
		//entity.setRuleid("ruleid");
		entity.setCreatedate(now);
		entity.setOrderby("bo");
		entity.setStartdate(now);
		entity.setCreator("rotaerc");
		entity.setEnddate(now);
		entity.setRemark("kramer");
		ICalRuleUseDAO.save(entity);
	}
	public void testCalRuleUseUpdate(){
		TCalRuleUse entity = ICalRuleUseDAO.query("ARID00058END");
		Date now = new Date();
		entity.setCreatedate(now);
		entity.setOrderby("bo");
		entity.setStartdate(now);
		entity.setCreator("rotaerc");
		entity.setEnddate(now);
		entity.setRemark("kramer");
		ICalRuleUseDAO.update(entity);
	}
	public void testCalRuleUseDelete(){
		ICalRuleUseDAO.delete("ARID00058END");
	}
	public void testCalRuleUseQuery(){
		TCalRuleUse entity = new TCalRuleUse();
		entity.setId("ARID00060END");
		entity = ICalRuleUseDAO.query(entity);
		log.info(entity);
		entity = new TCalRuleUse();
		entity.setCreator("creator");
		List<TCalRuleUse> result = ICalRuleUseDAO.queryForList(entity);
		log.info(result);
	}
	
	public void testToContractArbitration(){
		String oid = "1020150331352";
		String cid = "201503300000024";
		String cname = "测试公司72";
		try {
			this.iContractArbitrationService.toContractArbitration(oid, cid, cname, StringUtils.EMPTY);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void testContractArbitrationSave(){
		TOrderArbitration entity = new TOrderArbitration();
		entity.setId(prkg.generatorBusinessKeyByBid("ArbitrationResultID"));
		entity.setCreater("setCreater");
		entity.setCreatetime(new Date());
		entity.setRemark("setRemark");
		entity.setDealer("setDealer");
		entity.setDealresult("setDealresult");
		entity.setDealtime(new Date());
		entity.setStatus(ContractArbitrationStatus.SUCCESS);
		IContractArbitrationDAO.save(entity);
	}
	public void testContractArbitrationUpdate(){
		TOrderArbitration entity = IContractArbitrationDAO.query("ARID00016END");
		entity.setCreater("123");
		entity.setCreatetime(new Date());
		entity.setRemark("321");
		entity.setDealer("456");
		entity.setDealresult("654");
		entity.setDealtime(new Date());
		entity.setStatus(ContractArbitrationStatus.FAILURE);
		IContractArbitrationDAO.update(entity);
	}
	public void testContractArbitrationDelete(){
		IContractArbitrationDAO.delete("ARID00051END");
		TOrderArbitration entity = new TOrderArbitration();
		entity.setId("ARID00050END");
		IContractArbitrationDAO.delete(entity);
	}
	public void testContractArbitrationQuery(){
		TOrderArbitration entity = IContractArbitrationDAO.query("ARID00016END");
		log.info(entity);
		TOrderArbitration query = new TOrderArbitration();
		query.setStatus(ContractArbitrationStatus.FAILURE);
		List<TOrderArbitration> result = IContractArbitrationDAO.queryForList(query);
		log.info(result);
	}
	public void testContractArbitrationResultQuery(){
		TOrderArbitrationResult entity = IContractArbitrationResultDAO.query("ARID00011END");
		log.info(entity);
		TOrderArbitrationResult entity1 = new TOrderArbitrationResult();
		entity1.setQyid("QYBH01246774");
		List<TOrderArbitrationResult> result = IContractArbitrationResultDAO.queryForList(entity1);
		log.info(result);
	}
	public void testContractArbitrationResultDelete(){
		IContractArbitrationResultDAO.delete("ARID00010END");
		TOrderArbitrationResult entity = new TOrderArbitrationResult();
		entity.setId("ARID00009END");
		IContractArbitrationResultDAO.delete(entity);
	}
	public void testContractArbitrationResultUpdate(){
		TOrderArbitrationResult entity = IContractArbitrationResultDAO.query("ARID00011END");
		entity.setRtype("解冻");
		entity.setRcontent("保证金");
		entity.setQytype("买方");
		entity.setQyid("QYBH01246774");
		entity.setAmount(10.0f);
		entity.setPayid("PAYID1234");
		IContractArbitrationResultDAO.update(entity);
	}
	public void testContractArbitrationResultSave(){
		isRunning = true;
		long start = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){
			final int index = i;
			runningStatus.put(index, false);
			Thread r = new Thread() {
				public void run() {
					TOrderArbitrationResult entity = new TOrderArbitrationResult();
					entity.setId(prkg.generatorBusinessKeyByBid("ArbitrationResultID"));
					entity.setRtype("rtype");
					entity.setRcontent("rcontent");
					entity.setQytype("qytype");
					entity.setQyid("qyid");
					entity.setAmount(90.00f);
					entity.setPayid("payid");
					IContractArbitrationResultDAO.save(entity);
					runningStatus.put(index, true);

					for(Entry<Integer, Boolean> entry : runningStatus.entrySet()){
						if(!entry.getValue()){
							return;
						}
					}
					isRunning = false;
				}
			};
			r.start();
		}
		while(isRunning){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("Use the time :  "+(end-start));
	}
	public void testContractCancelSave(){
		TOrderCancel entity = new TOrderCancel();
		entity.setId(prkg.generatorBusinessKeyByBid("ORDERID"));
		entity.setCanceler("系统");
		entity.setCanceltype(ContractCancelType.SINGLE_CANCEL);
		entity.setCanceltime(new Date());
		entity.setReason("系统原因取消了");
		entity.setRemark("系统原因取消了");
		IContractCancelDAO.save(entity);
	}
	public void testContractCancelUpdate(){
		TOrderCancel entity = IContractCancelDAO.query("ORDERID020205904092014END");
		entity.setReason("单方不想交易");
		entity.setRemark("单方不想交易");
		IContractCancelDAO.update(entity);
		TOrderCancel entity1 = IContractCancelDAO.query("ORDERID020206004092014END");
		entity1.setCanceltype(ContractCancelType.SINGLE_CANCEL);
		entity1.setReason("单方取消");
		entity1.setRemark("单方取消");
		IContractCancelDAO.update(entity1);
	}
	public void testContractCancelQuery(){
		TOrderCancel entity = new TOrderCancel();
		List<TOrderCancel> result = IContractCancelDAO.queryForList(entity);
		log.info(result);
	}
	public void testContractCancelDelete(){
		IContractCancelDAO.delete("ORDERID020206104092014END");
		TOrderCancel entity = new TOrderCancel();
		entity.setId("ORDERID020206204092014END");
		IContractCancelDAO.delete(entity);
	}
	public void testContractCostDetailSave(){
		TOrderCostdetail entity = new TOrderCostdetail();
		entity.setId(prkg.generatorBusinessKeyByBid("ORDERID"));
		entity.setName("合同费用明细");
		entity.setAmount(2000.1f);
		entity.setCreatedate(new Date());
		entity.setCreator("creator");
		entity.setRemark("remark");
		entity.setRemark("备注");
		IContractCostDetailDAO.save(entity);
	}
	public void testContractCostDetailQuery(){
		TOrderCostdetail entity = IContractCostDetailDAO.query("ORDERID020205604092014END");
		log.info(entity);
		TOrderCostdetail entity1 = new TOrderCostdetail();
		entity1.setId("ORDERID020205604092014END");
		entity1 = IContractCostDetailDAO.query(entity1);
		log.info(entity1);
		TOrderCostdetail entity2 = new TOrderCostdetail();
		entity2.setCreator("creator");
		List<TOrderCostdetail> result = IContractCostDetailDAO.queryForList(entity2);
		log.info(result);
		QueryContext<TOrderCostdetail> qContext = new QueryContext<TOrderCostdetail>();
		qContext.getPage().setPageIndex(-1);
		qContext = IContractCostDetailDAO.queryListForPagination(qContext);
		log.info(qContext.getQueryResult().getResult());
	}
	public void testContractCostDetailUpdate(){
		TOrderCostdetail entity = IContractCostDetailDAO.query("ORDERID020205604092014END");
		log.info(entity);
		entity.setName(entity.getName());
		entity.setAmount(entity.getAmount()-200.1f);
		entity.setUpdatedate(new Date());
		entity.setCreator(entity.getCreator());
		entity.setRemark(entity.getRemark());
		IContractCostDetailDAO.update(entity);
	}
	public void testContractCostDetailDelete(){
		IContractCostDetailDAO.delete("ORDERID020205604092014END");
		TOrderCostdetail entity = new TOrderCostdetail();
		entity.setId("ORDERID020205604092014END");
		IContractCostDetailDAO.delete(entity);
	}
	public void testContractDisPriceSave(){
		TOrderDisPrice entity = new TOrderDisPrice();
		entity.setId(prkg.generatorBusinessKeyByBid("ORDERID"));
		entity.setType(ContractDisPriceType.SAMPLE_CHECK);
		entity.setCanceler("RodJson");
		entity.setCanceltime(new Date());
		entity.setReason("各种方面不一样");
		entity.setBeginamount(200.0);
		entity.setEndamount(180.0);
		entity.setBeginnum(10000.0);
		entity.setEndnum(9999.0);
		entity.setPunreason("延迟到货");
		entity.setPunday(2);
		entity.setRemark("备注");
		IContractDisPriceDAO.save(entity);
	}
	public void testContractDisPriceDelete(){
		IContractDisPriceDAO.delete("id");
		TOrderDisPrice entity = new TOrderDisPrice();
		entity.setId("ORDERID020205204092014END");
		IContractDisPriceDAO.delete(entity);
	}
	public void testContractDisPriceQuery(){
		TOrderDisPrice todp = IContractDisPriceDAO.query("id");
		log.info(todp);
		TOrderDisPrice entity = new TOrderDisPrice();
		entity.setId("ORDERID020205204092014END");
		entity = IContractDisPriceDAO.query(entity);
		log.info(entity);
		QueryContext<TOrderDisPrice> qContext = new QueryContext<TOrderDisPrice>();
		qContext.getPage().setPageIndex(-1);
		qContext = IContractDisPriceDAO.queryListForPagination(qContext);
		log.info(qContext.getQueryResult().getResult());
	}
	public void testContractDisPriceUpdate(){
		TOrderDisPrice todp = IContractDisPriceDAO.query("id");
		todp.setType(ContractDisPriceType.FULL_TAKEOVER);
		todp.setCanceler(todp.getCanceler()+"_canceler");
		todp.setCanceltime(new Date());
		todp.setReason(todp.getReason()+"_reason");
		todp.setBeginamount(todp.getBeginamount()+10.0f);
		todp.setEndamount(todp.getEndamount()+10.0f);
		todp.setBeginnum(todp.getBeginnum()-10);
		todp.setEndnum(todp.getEndnum()-10);
		todp.setPunreason(todp.getPunreason()+"_punreason");
		todp.setPunday(todp.getPunday()+10);
		todp.setRemark(todp.getRemark()+"_remark");
		IContractDisPriceDAO.update(todp);
	}
	public void testContractOperationSave(){
		TOrderOperations entity = new TOrderOperations();
		entity.setId(prkg.generatorBusinessKeyByBid("ORDERID"));
		entity.setOperator("operator");
		entity.setOperationtime(new Date());
		entity.setType(ContractOperateType.APPLY_DISPRICE);
		entity.setResult("result");
		entity.setRemark("remark");
		IContractOperationDAO.save(entity);
	}
	public void testContractOperationDelete(){
		//IContractOperationDAO.delete("ORDERID020205104092014END");
		TOrderOperations entity = new TOrderOperations();
		entity.setId("ORDERID020205004092014END");
		entity.setOperator("operator_update");
		IContractOperationDAO.delete(entity);
	}
	public void testContractOperationQuery(){
		TOrderOperations entity = new TOrderOperations();
		entity.setOperator("operator");
		List<TOrderOperations> result = IContractOperationDAO.queryForList(entity);
		log.info(result);
	}
	public void testContractOperationUpdate(){
		TOrderOperations entity = new TOrderOperations();
		entity.setId("ORDERID020205004092014END");
		entity = IContractOperationDAO.query(entity);
		log.info(entity);
		entity.setOperationtime(new Date());
		entity.setType(ContractOperateType.ARBITRATION_CONTRACT);
		entity.setOperator("operator_update");
		entity.setResult("result_update");
		entity.setRemark("remark_update");
		IContractOperationDAO.update(entity);
	}
	/*public void testContractPayDelete(){
		IContractPayDAO.delete("1");
	}
	public void testContractPayUpdate(){
		TOrderPay entity = new TOrderPay();
		entity.setId("1");
		entity.setOtype("otype1");
		entity.setPtpye("ptpye1");
		entity.setStatus("status1");
		entity.setTotal(2f);
		entity.setAmount(3f);
		entity.setCreater("creater1");
		entity.setCreattime(new Date());
		IContractPayDAO.update(entity);
	}
	public void testContractPaySave(){
		TOrderPay entity = new TOrderPay();
		entity.setId("001");
		entity.setOtype("otype");
		entity.setPtpye("ptpye");
		entity.setStatus("status");
		entity.setTotal(1f);
		entity.setAmount(1f);
		entity.setCreater("creater");
		entity.setCreattime(new Date());
		IContractPayDAO.save(entity);
		log.debug(entity);
	}
	public void testContractPayQuery(){
		TOrderPay entity = new TOrderPay();
		entity.setId("1");
		entity.setTotal(1f);
		entity.setAmount(1f);
		entity.setCreattime(new Date());
		IContractPayDAO.query(entity);
		log.debug(entity);
	}*/
	public void testContractInfoDelete(){
		QueryContext<TOrderInfo> qContext = new QueryContext<TOrderInfo>();
		qContext.getPage().setPageIndex(-1);
		qContext = IContractInfoDAO.queryListForPagination(qContext);
		log.info(qContext.getQueryResult().getResult());
		TOrderInfo entity = new TOrderInfo();
		entity.setId("ORDERID020204602092014END");
		IContractInfoDAO.delete(entity);
		qContext.getPage().setPageIndex(-1);
		qContext = IContractInfoDAO.queryListForPagination(qContext);
		log.info(qContext.getQueryResult().getResult());
	}
	public void testContractInfoSelect(){
		TOrderInfo entity = new TOrderInfo();
		entity.setId("ORDERID020204702092014END");
		log.info(IContractInfoDAO.queryForList(entity));
		entity = IContractInfoDAO.query(entity);
		log.info(entity);
		List<TOrderInfo> result = IContractInfoDAO.queryForList(Collections.singletonMap("SELLERID", "sellerid"));
		log.info(result);
		QueryContext<TOrderInfo> qContext = new QueryContext<TOrderInfo>();
		qContext.getPage().setPageIndex(-1);
		qContext = IContractInfoDAO.queryListForPagination(qContext);
		log.info(qContext.getQueryResult().getResult());
	}
	public void testContractInfoUpdate(String id){
		TOrderInfo entity = IContractInfoDAO.query(id);
		System.out.println(entity);
		log.info(entity);
		entity.setBuyerid("00000012");
		entity.setSellerid("00000013");
		entity.setUpdater("00000012");
		entity.setUpdatetime(new Date());
		IContractInfoDAO.update(entity);
	}
	public void testContractInfoInsert(){
		TOrderInfo entity = new TOrderInfo();
		Date now = new Date();
		entity.setId(prkg.generatorBusinessKeyByBid("CONTRACTINFOID"));
		//entity.setFid(fid);
		entity.setSellerid("sellerid");
		entity.setBuyerid("buyerid");
		entity.setPrice(20.0);
		entity.setTotalnum(10);
		entity.setCreatime(now);
		entity.setCreater("creater");
		entity.setLimittime(now);
		entity.setTotalamount(50.0);
		entity.setAmount(10.0);
		entity.setStatus(ContractStatus.enumOf("12"));
		entity.setOtype(ContractType.enumOf("2"));
		entity.setUpdater("updater");
		entity.setUpdatetime(now);
		entity.setRemark("remark");
		entity.setAmount(10.0);
		IContractInfoDAO.save(entity);
	}

	public void testCancelDraftContract(){
		String oid = "1020150505387";
		String cid = "201503300000024";
		String cname = "admin";
		try {
			iContractCancelService.cancelDraftContract(oid, cid, cname, ContractOperateType.CANCEL_CONTRACT);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.debug(e);
		}
	}
	
	public void testMakeAndMatchATOrderInfoWithCustomService(){
		String fid = "201502040000211";
		OrderFindAllBean ofabean = new OrderFindAllBean();
		
		TOrderFind orderFind = orderFindService.query(fid);
		orderFind.setId(StringUtils.EMPTY);
		ofabean.setOfBean(orderFind);
		
		TOrderProductInfo queryEntity = new TOrderProductInfo();
		queryEntity.setFid(fid);
		TOrderProductInfo productInfo = this.iOrderProductInfoService.query(queryEntity);
		ofabean.setOpiBean(productInfo);
		
		TOrderProductProperty propis = new TOrderProductProperty();
		propis.setPpid(Integer.valueOf(productInfo.getId()));
		List<TOrderProductProperty> result = iOrderProductPropertyService.queryForList(propis);
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		for(TOrderProductProperty t : result){
			ProductPropertyContentBean e = new ProductPropertyContentBean();
			e.setPpid(t.getPproid());
			e.setContent(t.getContent());
			ppcList.add(e);
		}
		ofabean.setPpcList(ppcList);
		
		TOrderAddress addressInfo = this.iOrderAddressService.queryByFid(fid);
		if(addressInfo == null){
			addressInfo = new TOrderAddress();
			addressInfo.setCid(orderFind.getCid());
			addressInfo.setAreacode("130524000000");
			addressInfo.setAddress("21341432");
			addressInfo.setShippington(200f);
		}
		ofabean.setOaBean(addressInfo);
		
		TOrderInfo orderInfo;
		try {
			orderInfo = iContractInfoService.makeAndMatchTOrderWithCustomService(ofabean, ofabean.getOfBean().getCid(), "201501150000017" , "admin");
			//orderInfo = iContractInfoService.makeAndMatchATOrderInfo(ofabean , "201501150000017" , "admin");
			log.debug(orderInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.debug(e);
		}
	}
	
	public void testMakeAndMatchATOrderInfo(){
		String fid = "201505290000999";//201505270000403
		OrderFindAllBean ofabean = new OrderFindAllBean();
		
		TOrderFind orderFind = orderFindService.query(fid);
		ofabean.setOfBean(orderFind);
		
		TOrderProductInfo queryEntity = new TOrderProductInfo();
		queryEntity.setFid(fid);
		TOrderProductInfo productInfo = this.iOrderProductInfoService.query(queryEntity);
		ofabean.setOpiBean(productInfo);
		
		TOrderProductProperty propis = new TOrderProductProperty();
		propis.setPpid(Integer.valueOf(productInfo.getId()));
		List<TOrderProductProperty> result = iOrderProductPropertyService.queryForList(propis);
		List<ProductPropertyContentBean> ppcList = new ArrayList<ProductPropertyContentBean>();
		for(TOrderProductProperty t : result){
			ProductPropertyContentBean e = new ProductPropertyContentBean();
			e.setPpid(t.getPproid());
			e.setContent(t.getContent());
			ppcList.add(e);
		}
		ofabean.setPpcList(ppcList);
		
		TOrderAddress addressInfo = this.iOrderAddressService.queryByFid(fid);
		if(addressInfo == null){
			addressInfo = new TOrderAddress();
			addressInfo.setCid(orderFind.getCid());
			addressInfo.setAreacode("130524000000");
			addressInfo.setAddress("DD镇CC村");
			addressInfo.setDeep(6.51f);
			addressInfo.setShippington(2000.63f);
		}
		ofabean.setOaBean(addressInfo);
		
		TOrderInfo orderInfo;
		try {
			orderInfo = iContractInfoService.makeAndMatchATOrderInfo(ofabean , "281120140000026" , "admin");
			iOrderFindMatchService.vitiateOrderFindMatchInfoByFid(fid);
			log.debug(orderInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.debug(e);
		}
	}
	
	public void testCreateContractByFid(String fid,String oper){
		TOrderInfo entity = new TOrderInfo();
		TOrderFind of = this.orderFindService.query(fid);
		if(of.getType() == OrderTypeEnum.ORDER_TYPE_BUY){ // 询单发布方为买方
			entity.setBuyerid(of.getCid());
			entity.setSellerid(oper);
		} else {// 询单发布方为卖方
			entity.setBuyerid(oper);
			entity.setSellerid(of.getCid());
		}
		
		String admin = "admin";//ToolsConstant.SCHEDULER
		
		Date now = new Date();
		entity.setFid(of.getId());

		entity.setPrice(of.getPrice().doubleValue());
		entity.setTotalnum(of.getTotalnum());
		entity.setCreatime(now);
		entity.setCreater(admin);
		entity.setLimittime(of.getLimitime());
		entity.setTotalamount(RandomUtil.mulRound(of.getPrice().doubleValue(), of.getTotalnum().doubleValue()));
		entity.setAmount(0.0);
		entity.setStatus(ContractInfo.ContractStatus.DRAFT);
		entity.setOtype(ContractInfo.ContractType.DRAFT);
		entity.setUpdater(admin);
		entity.setUpdatetime(now);
		entity.setRemark("remark");
		entity.setLifecycle(ContractLifeCycle.DRAFTING);
		//IContractInfoDAO.save(entity);
		iContractInfoService.add(entity);

		TOrderOperations operator = new TOrderOperations();
		operator.setId(prkg.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
		operator.setOid(entity.getId());
		operator.setOperator(admin);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.MAKE_CONTRACT);
		operator.setOrderstatus(ContractLifeCycle.DRAFTING);
		StringBuilder result = new StringBuilder(admin);
		result.append("撮合了合同操作");
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		IContractOperationDAO.save(operator);
		
		of.setStatus(OrderStatusEnum.ORDER_STATUS_CLOSE);
		of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
		this.orderFindService.modify(of);
		
		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,entity.getId(),entity.getBuyerid(),SystemMessageContent.getMsgContentOfContractSign());
		mi.setSendSystemMsg(true);
		mi.setSendPushMsg(true);
		mesgSender.msgSend(mi);

		mi.setCid(entity.getSellerid());
		mesgSender.msgSend(mi);
	}

	/**
	 * 合同生成
	 * @param ofiid 交易意向记录ID
	 */
	public void testCreateContract(int ofiid){
		TOrderFindItem ofi = this.orderFindItemService.query(ofiid);
		this.testCreateContractByFid(ofi.getFid(),ofi.getUpdater());
	}

	@Autowired
	private MessageSendManager mesgSender;

}
