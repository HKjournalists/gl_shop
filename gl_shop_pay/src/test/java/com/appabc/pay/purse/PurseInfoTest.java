/**  
 * com.appabc.pay.purse.PurseInfoTest.java  
 *   
 * 2014年10月17日 下午6:10:19  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.purse;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.common.utils.DateUtil;
import com.appabc.pay.AbstractPayTest;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.bean.TPassbookThirdCheck;
import com.appabc.pay.enums.PurseInfo.BusinessType;
import com.appabc.pay.enums.PurseInfo.DeviceType;
import com.appabc.pay.enums.PurseInfo.OnOffLine;
import com.appabc.pay.enums.PurseInfo.PayDirection;
import com.appabc.pay.enums.PurseInfo.PayWay;
import com.appabc.pay.enums.PurseInfo.PurseType;
import com.appabc.pay.enums.PurseInfo.TradeStatus;
import com.appabc.pay.enums.PurseInfo.TradeType;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IAcceptBankService;
import com.appabc.pay.service.local.IOfflinePayService;
import com.appabc.pay.service.local.IPassbookDrawService;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.pay.service.local.IPassbookPayService;
import com.appabc.pay.service.local.IPassbookThirdCheckService;
import com.appabc.pay.util.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月17日 下午6:10:19
 */

public class PurseInfoTest extends AbstractPayTest {

	@Autowired
	private IPassbookInfoService iPassbookInfoService;
	@Autowired
	private IPassbookPayService iPassbookPayService;
	@Autowired
	private IPassbookThirdCheckService iPassbookThirdCheckService;
	@Autowired
	private IPassbookDrawService iPassbookDrawService;
	@Autowired
	private IOfflinePayService iOfflinePayService;
	@Autowired
	private IAcceptBankService iAcceptBankService;
	@Autowired
	private IPassPayService iPassPayService;
	@Autowired
	private PrimaryKeyGenerator PKGenerator;
	/* (non-Javadoc)  
	 * @see com.appabc.pay.AbstractPayTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest () {
		//this.testPassPayService();
	}
	
	public void testPassPayService(){
		testInitializePurseAccount();
		testGetPurseAccountInfo();
		testSaveAcceptBank();
		testGetAcceptBankList();
		testDepositAccountOnline();
		testDepositAccountOffline();
		testPayAccountOffline();
		testDepositThirdOrgRecord();
		testPayRecordList();
		testExtractCash();
		testDepositToGuaranty();
		testTransferAccounts();
		//testGuarantyToGelationOrUnDeduct();
		testOfflinePayAction();
	}
	
	public void testOfflinePayAction(){
		log.info("===============================OfflinePayAction start===============================");
		String cid = "CompanyInfoId000000811102014END";
		TOfflinePay bean = new TOfflinePay();
		bean.setId(PKGenerator.generatorBusinessKeyByBid("PURSEOFFLINEPAYID"));
		bean.setOtype("1");
		bean.setPtype(OnOffLine.OFFLINE.getValue());
		bean.setCreater(cid);
		bean.setCreattime(DateUtil.getNowDate());
		bean.setStatus(TradeStatus.REQUEST.getValue());
		bean.setBtype(BusinessType.DEPOSIT.getValue());
		bean.setAmount(5000f);
		bean.setTotal(5000f);
		try {
			iPassPayService.saveOfflinePay(bean);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		log.info(bean);
		bean.setStatus(TradeStatus.SUCCESS.getValue());
		iPassPayService.auditeOfflinePay(bean);
		log.info(bean);
		String oid = "201410170008017";
		List<TOfflinePay> result = iPassPayService.getOfflinePayList(oid);
		log.info(result);
		log.info("===============================OfflinePayAction end===============================");
	}
	
	public void testGuarantyToGelationOrUnDeduct(){
		log.info("===============================GuarantyToGelationOrUnDeduct start===============================");
		String cid = "CompanyInfoId000000524092014END";
		float balance = 5000f;
		String oid = "201410170008017";
		String sourPassId = "PASSID2014102000007110431";
		String destPassId = "PASSID2014102000008110431";
		boolean flag = iPassPayService.guarantyToGelation(cid, balance, oid);
		log.info(flag);
		boolean flag1 = iPassPayService.guarantyToUngelation(cid, balance, oid);
		log.info(flag1);
		boolean flag2 = iPassPayService.guarantyToDeduct(sourPassId, destPassId, balance);
		log.info(flag2);
		log.info("===============================GuarantyToGelationOrUnDeduct end===============================");
	}
	
	public void testTransferAccounts(){
		log.info("===============================DepositToGuaranty start===============================");
		String sourPassId = "PASSID2014102000007110431";
		String destPassId = "PASSID2014102000008110431";
		boolean flag = iPassPayService.transferAccounts(sourPassId, destPassId, 5000f, "0");
		log.info(flag);
		log.info("===============================DepositToGuaranty end===============================");
	}
	
	public void testDepositToGuaranty(){
		log.info("===============================DepositToGuaranty start===============================");
		String cid = "CompanyInfoId000000524092014END";
		boolean flag = iPassPayService.depositToGuaranty(cid, 5000f);
		log.info(flag);
		log.info("===============================DepositToGuaranty end===============================");
	}
	
	public void testExtractCash(){
		log.info("===============================ExtractCash start===============================");
		String cid = "CompanyInfoId000000524092014END";
		String type = "0";
		String acceptId = "ACCEPTBANKID201410170000007143940";
		String tid = "PURSEDRAWID2014102000002145122";
		TPassbookDraw draw = iPassPayService.extractCashRequest(cid, type, 5000f, acceptId);
		log.info(draw);
		boolean flag = iPassPayService.extractCashAudit(tid, true, "无", cid);
		log.info(flag);
		boolean flag1 = iPassPayService.extractCashDeduct(tid);
		log.info(flag1);
		List<TPassbookDraw> result = iPassPayService.extractCashRequestList("0");
		log.info(result);
		List<TPassbookDraw> result1 = iPassPayService.extractCashRequestList(cid, type);
		log.info(result1);
		log.info("===============================ExtractCash end===============================");
	}
	
	public void testPayRecordDetail(){
		log.info("===============================PayRecordDetail start===============================");
		String pid = "CompanyInfoId000000524092014END";
		TPassbookPay entity = iPassPayService.payRecordDetail(pid);
		log.info(entity);
		log.info("===============================PayRecordDetail end===============================");
	}
	
	public void testPayRecordList(){
		log.info("===============================PayRecordList start===============================");
		String passId = "";
		String cid = "CompanyInfoId000000524092014END";
		List<TPassbookPay> result = iPassPayService.payRecordList(passId);
		log.info(result);
		List<TPassbookPay> result1 = iPassPayService.payRecordList(cid, "0");
		log.info(result1);
		log.info("===============================PayRecordList end===============================");
	}
	
	public void testDepositThirdOrgRecord(){
		log.info("===============================DepositThirdOrgRecord start===============================");
		String cid = "CompanyInfoId000000524092014END";
		String payNo = "CompanyInfoId000000524092014END";
		boolean flag = iPassPayService.depositThirdOrgRecord(cid, "0", 5000f, payNo);
		log.info(flag);
		log.info("===============================DepositThirdOrgRecord end===============================");
	}
	
	public void testPayAccountOffline(){
		log.info("===============================PayAccountOffline start===============================");
		String cid = "CompanyInfoId000000524092014END";
		OInfo oid = new OInfo();
		oid.setOid("oid123456");
		oid.setAmount(400f);
		oid.setTotal(500f);
		boolean flag = iPassPayService.payAccountOffline(cid, "0", oid);
		log.info(flag);
		log.info("===============================PayAccountOffline end===============================");
	}
	
	public void testDepositAccountOffline(){
		log.info("===============================DepositAccountOffline start===============================");
		String cid = "CompanyInfoId000000524092014END";
		boolean flag = iPassPayService.depositAccountOffline(cid, "0");
		log.info(flag);
		log.info("===============================DepositAccountOffline end===============================");
	}
	
	public void testDepositAccountOnline(){
		log.info("===============================DepositAccountOnline start===============================");
		String cid = "CompanyInfoId000000524092014END";
		String payNo = "CompanyInfoId000000524092014END";
		boolean flag = iPassPayService.depositAccountOnline(cid, "0", 500f, payNo);
		log.info(flag);
		log.info("===============================DepositAccountOnline end===============================");
	}
	
	public void testGetAcceptBankList(){
		log.info("===============================GetAcceptBankList start===============================");
		String cid = "CompanyInfoId000000524092014END";
		List<TAcceptBank> result = iPassPayService.getAcceptBankList(cid);
		log.info(result);
		log.info("===============================GetAcceptBankList end===============================");
	}
	
	public void testGetPurseAccountInfo(){
		log.info("===============================GetPurseAccountInfo start===============================");
		String cid = "CompanyInfoId000000524092014END";
		TPassbookInfo entity0 = iPassPayService.getPurseAccountInfo(cid, "0");
		log.info(entity0);
		TPassbookInfo entity1 = iPassPayService.getPurseAccountInfo(cid, "1");
		log.info(entity1);
		log.info("===============================GetPurseAccountInfo end===============================");
	}
	
	public void testSaveAcceptBank(){
		log.info("===============================SaveAcceptBank start===============================");
		String cid = "000000915102014";
		TAcceptBank entity = new TAcceptBank();
		entity.setId(PKGenerator.generatorBusinessKeyByBid("ACCEPTBANKID"));
		entity.setCid(cid);
		entity.setBankcard("7752567865473321");
		entity.setBankaccount("招商银行一卡通");
		entity.setCarduser("张三");
		entity.setCarduserid("562854199504562145");
		entity.setBanktype("0");
		entity.setBankname("高新园支行");
		entity.setAddr("广东省深圳市南山区XXXX路XXXX号XXXX座XXXX栋XXXX楼XXXX室.");
		entity.setRemark("备注");
		entity.setCreator(cid);
		entity.setCreatetime(new Date());
		entity.setStatus(0);
		entity.setAuthstatus(0);
		try {
			iPassPayService.saveAcceptBank(entity);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		log.info("===============================SaveAcceptBank end===============================");
	}
	
	public TOfflinePay testOfflinePayServiceInsert(){
		String cid = "CompanyInfoId000000811102014END";
		TOfflinePay bean = new TOfflinePay();
		bean.setId(PKGenerator.generatorBusinessKeyByBid("PURSEOFFLINEPAYID"));
		bean.setOtype("1");
		bean.setPtype(OnOffLine.OFFLINE.getValue());
		bean.setCreater(cid);
		bean.setCreattime(DateUtil.getNowDate());
		bean.setStatus(TradeStatus.REQUEST.getValue());
		bean.setBtype(BusinessType.DEPOSIT.getValue());
		bean.setAmount(5000f);
		bean.setTotal(5000f);
		iOfflinePayService.add(bean);
		return bean;
	}
	
	public void testOfflinePayServiceQuery(String id){
		TOfflinePay entity = iOfflinePayService.query(id);
		log.info(entity);
	}
	
	public void testOfflinePayServiceUpdate(String id){
		TOfflinePay entity = iOfflinePayService.query(id);
		if(entity != null){
			entity.setDealer("000000915102014");
			entity.setDealresult("3");
			entity.setDealtime(DateUtil.getNowDate());
			iOfflinePayService.modify(entity);
			log.info(entity);
		}
	}
	
	public void testOfflinePayServiceDelete(String id){
		iOfflinePayService.delete(id);
	}
	
	public Object testPassbookDrawServiceInsert(){
		TPassbookDraw entity = new TPassbookDraw();
		Date now = DateUtil.getNowDate();
		String aid = "ACCEPTBANKID201410170000009201803";
		String dealer = "000000915102014";
		int status = 1;
		entity.setId(PKGenerator.generatorBusinessKeyByBid("PURSEDRAWID"));
		entity.setAid(aid); 
		entity.setAmount(5000f);
		entity.setStatus(status);
		entity.setCreatetime(now);
		entity.setDealer(dealer);
		entity.setDealtime(now);
		entity.setDealstatus(status+"");
		entity.setMark("mark");
		entity.setPid("PURSEPAYID2014101700003143930");
		iPassbookDrawService.add(entity);
		return entity;
	}
	
	public void testPassbookDrawServiceQuery(String id){
		TPassbookDraw entity = iPassbookDrawService.query(id);
		log.info(entity);
	}
	
	public void testPassbookDrawServiceUpdate(String id){
		TPassbookDraw entity = iPassbookDrawService.query(id);
		if(entity != null){
			entity.setDealer("000000915102014");
			entity.setDealstatus("3");
			entity.setDealtime(DateUtil.getNowDate());
			iPassbookDrawService.modify(entity);
			log.info(entity);
		}
	}
	
	public void testPassbookDrawServiceDelete(String id){
		iPassbookDrawService.delete(id);
	}
	
	public Object testPassbookThirdCheckInsert(){
		//String cid = "CompanyInfoId000000524092014END";
		String oid = "201410200008220";
		String payno = "PAYNO201410200008220";
		Date now = DateUtil.getNowDate();
		TPassbookThirdCheck tptc = new TPassbookThirdCheck();
		tptc.setAmount(5000f);
		tptc.setId(PKGenerator.generatorBusinessKeyByBid("PURSETHIRDCHECKID"));
		tptc.setPassid("PASSID2014102000008110431");
		tptc.setAmount(5f);
		tptc.setOid(oid);
		tptc.setOtype(TradeType.DEPOSIT.getValue());
		tptc.setDirection(PayDirection.INPUT.getValue());
		tptc.setDevices(DeviceType.MOBILE.getValue());
		tptc.setName("test third check.");
		tptc.setPatytime(now);
		tptc.setPayno(payno);
		tptc.setPaytype(PayWay.NETBANK_PAY.getValue());
		tptc.setStatus(TradeStatus.SUCCESS.getValue());
		tptc.setRemark("test third check.");
		iPassbookThirdCheckService.add(tptc);
		return tptc;
	}
	
	public void testPassbookThirdCheckQuery(String id){
		TPassbookThirdCheck tptc = iPassbookThirdCheckService.query(id);
		log.info(tptc);
	}
	
	public void testPassbookThirdCheckUpdate(String id){
		TPassbookThirdCheck tptc = iPassbookThirdCheckService.query(id);
		if(tptc != null){
			tptc.setAmount(6000f);
			iPassbookThirdCheckService.modify(tptc);
			log.info(tptc);
		}
	}
	
	public void testPassbookThirdCheckDelete(String id){
		iPassbookThirdCheckService.delete(id);
	}
	
	public TPassbookPay testPassbookPayInsert(){
		String cid = "CompanyInfoId000000524092014END";
		String oid = "201410200008220";
		String payno = "PAYNO201410200008220";
		TPassbookPay entity = new TPassbookPay();
		Date now = DateUtil.getNowDate();
		entity.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		entity.setPassid("PASSID2014102000008110431");
		entity.setAmount(5f);
		entity.setOid(oid);
		entity.setOtype(TradeType.DEPOSIT.getValue());
		entity.setDirection(PayDirection.INPUT.getValue());
		entity.setCreator(cid);
		entity.setCreatedate(now);
		entity.setDevices(DeviceType.MOBILE.getValue());
		entity.setNeedamount(5f);
		entity.setName("test pass book name.");
		entity.setPatytime(now);
		entity.setPayno(payno);
		entity.setPaytype(PayWay.NETBANK_PAY.getValue());
		entity.setStatus(TradeStatus.SUCCESS.getValue());
		entity.setRemark("test pass book pay.");
		entity.setUpdatedate(now);
		iPassbookPayService.add(entity);
		return entity;
	}
	
	public void testPassbookPayQuery(String id){
		TPassbookPay entity = iPassbookPayService.query(id);
		log.info(entity);
	}
	
	public void testPassbookPayUpdate(String id){
		TPassbookPay entity = iPassbookPayService.query(id);
		log.info(entity);
		if (entity!=null) {
			entity.setAmount(500f);
			entity.setNeedamount(500f);
			entity.setUpdatedate(DateUtil.getNowDate());
			iPassbookPayService.modify(entity);
			log.info(entity);
		}
	}
	
	public void testPassbookPayDelete(String id){
		iPassbookPayService.delete(id);
	}
	
	public Object testPassbookInfoInsert(){
		String cid = "CompanyInfoId000000524092014END";
		TPassbookInfo entity = new TPassbookInfo();
		entity.setId(PKGenerator.generatorBusinessKeyByBid("PASSID"));
		entity.setCid(cid);
		entity.setPasstype(PurseType.GUARANTY.getValue());
		entity.setAmount(5000f);
		entity.setCreatetime(DateUtil.getNowDate());
		entity.setRemark("guaranty account.");
		iPassbookInfoService.add(entity);
		TPassbookInfo entity1 = new TPassbookInfo();
		entity1.setId(PKGenerator.generatorBusinessKeyByBid("PASSID"));
		entity1.setCid(cid);
		entity1.setPasstype(PurseType.DEPOSIT.getValue());
		entity1.setAmount(5000f);
		entity1.setCreatetime(DateUtil.getNowDate());
		entity1.setRemark("deposit account.");
		iPassbookInfoService.add(entity1);
		return entity1;
	}
	
	public void testPassbookInfoQuery(String id){
		TPassbookInfo tpbi = iPassbookInfoService.query(id);
		log.info(tpbi);
	}
	
	public void testPassbookInfoUpdate(String id){
		TPassbookInfo tpbi = iPassbookInfoService.query(id);
		log.info(tpbi);
		tpbi.setAmount(4000f);
		iPassbookInfoService.modify(tpbi);
		log.info(tpbi);
	}
	
	public void testPassbookInfoDelete(String id){
		iPassbookInfoService.delete(id);
	}

	public void testAcceptBank(){
		TAcceptBank tb = (TAcceptBank)testAcceptBankInsert();
		String id = tb.getId();
		testAcceptBankQuery(id);
		log.info("query finished.");
		testAcceptBankUpdate(id);
		log.info("update the entity info.");
		testAcceptBankDelete(id);
		log.info("delete this entity.");
	}
	
	public Object testAcceptBankInsert(){
		String cid = "000000915102014";
		TAcceptBank entity = new TAcceptBank();
		entity.setId(PKGenerator.generatorBusinessKeyByBid("ACCEPTBANKID"));
		entity.setCid(cid);
		entity.setBankcard("7752567865473321");
		entity.setBankaccount("招商银行一卡通");
		entity.setCarduser("张三");
		entity.setCarduserid("562854199504562145");
		entity.setBanktype("0");
		entity.setBankname("高新园支行");
		entity.setAddr("广东省深圳市南山区XXXX路XXXX号XXXX座XXXX栋XXXX楼XXXX室.");
		entity.setRemark("备注");
		entity.setCreator(cid);
		entity.setCreatetime(new Date());
		entity.setStatus(0);
		entity.setAuthstatus(0);
		iAcceptBankService.add(entity);
		return entity;
	}
	
	public void testAcceptBankQuery(String id){
		String cid = "000000915102014";
		TAcceptBank tab = iAcceptBankService.query(id);
		log.info(tab);
		TAcceptBank entity = new TAcceptBank();
		entity.setCid(cid);
		TAcceptBank test = iAcceptBankService.query(entity);
		log.info(test);
		List<TAcceptBank> result =iAcceptBankService.queryForList(entity);
		log.info(result);
	}
	
	public void testAcceptBankUpdate(String id){
		TAcceptBank tab = iAcceptBankService.query(id);
		tab.setUpdatetime(new Date());
		tab.setCarduser("李四");
		iAcceptBankService.modify(tab);
		log.info(tab);
	}
	
	public void testAcceptBankDelete(String id){
		iAcceptBankService.delete(id);
	}
	
	public void testInitializePurseAccount(){
		log.info("===============================InitializePurseAccount start===============================");
		String cid = "CompanyInfoId000000524092014END";
		boolean flag = iPassPayService.initializePurseAccount(cid, "");
		boolean flag0 = iPassPayService.initializePurseAccount(cid, "0");
		boolean flag1 = iPassPayService.initializePurseAccount(cid, "1");
		log.info(flag);
		log.info(flag0);
		log.info(flag1);
		log.info("===============================InitializePurseAccount end===============================");
	}
}
