package com.appabc.datas.contract;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TCalRuleDefinition;
import com.appabc.bean.pvo.TCalRuleUse;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderArbitrationResult;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderCostdetail;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.enums.ContractInfo;
import com.appabc.datas.enums.OrderFindInfo;
import com.appabc.datas.service.order.IOrderFindItemService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

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
	private com.appabc.datas.dao.contract.IContractInfoDAO IContractInfoDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.IContractOperationDAO IContractOperationDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.IContractDisPriceDAO IContractDisPriceDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.IContractCostDetailDAO IContractCostDetailDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.IContractCancelDAO IContractCancelDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.IContractArbitrationResultDAO IContractArbitrationResultDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.IContractArbitrationDAO IContractArbitrationDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.ICalRuleUseDAO ICalRuleUseDAO;
	
	@Autowired
	private com.appabc.datas.dao.contract.ICalRuleDefinitionDAO ICalRuleDefinitionDAO;
	@Autowired
	private IOrderFindItemService orderFindItemService;
	@Autowired
	private IOrderFindService orderFindService;
	
	private Map<Integer,Boolean> runningStatus = new HashMap<Integer,Boolean>();
	
	private boolean isRunning = false;
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
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
	public void testContractArbitrationSave(){
		TOrderArbitration entity = new TOrderArbitration();
		entity.setId(prkg.generatorBusinessKeyByBid("ArbitrationResultID"));
		entity.setCreater("setCreater");
		entity.setCreatetime(new Date());
		entity.setRemark("setRemark");
		entity.setDealer("setDealer");
		entity.setDealresult("setDealresult");
		entity.setDealtime(new Date());
		entity.setStatus(1);
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
		entity.setStatus(2);
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
		query.setStatus(2);
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
		entity.setCanceltype("系统取消");
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
		entity1.setCanceltype("单方取消");
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
		entity.setType(10);
		entity.setCanceler("RodJson");
		entity.setCanceltime(new Date());
		entity.setReason("各种方面不一样");
		entity.setBeginamount(200.0f);
		entity.setEndamount(180.0f);
		entity.setBeginnum(10000);
		entity.setEndnum(9999);
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
		todp.setType(todp.getType()+10);
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
		entity.setType("type");
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
		entity.setType("type_update");
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
		entity.setPrice(20.0f);
		entity.setTotalnum(10);
		entity.setCreatime(now);
		entity.setCreater("creater");
		entity.setLimittime(now);
		entity.setTotalamount(50.0f);
		entity.setAmount(10.0f);
		entity.setStatus("12");
		entity.setOtype("2");
		entity.setUpdater("updater");
		entity.setUpdatetime(now);
		entity.setRemark("remark");
		entity.setAmount(10.0f);
		IContractInfoDAO.save(entity);
	}
	
	/**
	 * 合同生成
	 * @param ofiid 交易意向记录ID
	 */
	public void testCreateContract(int ofiid){
		TOrderInfo entity = new TOrderInfo();
		
		TOrderFindItem ofi = this.orderFindItemService.query(ofiid);
		
		TOrderFind of = this.orderFindService.query(ofi.getFid());
		
		if(of.getType() == OrderFindInfo.OrderTypeEnum.ORDER_TYPE_BUY.getVal()){ // 询单发布方为买方
			entity.setBuyerid(of.getCid());
			entity.setSellerid(ofi.getUpdater()); 
		}else{ // 询单发布方为卖方
			entity.setBuyerid(ofi.getUpdater());
			entity.setSellerid(of.getCid()); 
		}
		
		Date now = new Date();
		entity.setId(prkg.generatorBusinessKeyByBid("CONTRACTINFOID"));
		entity.setFid(of.getId());
		
		entity.setPrice(20.0f);
		entity.setTotalnum(100);
		entity.setCreatime(now);
		entity.setCreater("creater");
		entity.setLimittime(now);
		entity.setTotalamount(50.0f);
		entity.setAmount(10.0f);
		entity.setStatus(ContractInfo.ContractStatus.DOING.getValue());
		entity.setOtype(ContractInfo.ContractType.DRAFT.getValue());
		entity.setUpdater("updater");
		entity.setUpdatetime(now);
		entity.setRemark("remark");
		entity.setAmount(10.3f);
		IContractInfoDAO.save(entity);
	}

}
