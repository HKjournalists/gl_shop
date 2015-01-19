package com.appabc.datas.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderMine;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractMineService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月25日 上午11:44:52
 */

public class ContractMineTest extends AbstractDatasTest {

	@Autowired
	private IContractMineService iContractMineService;
	
	@Autowired
	private IContractInfoService iContractInfoService;
	
	private String oper = "tester";
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		//this.testSave();
		//this.testModify();
		
	}
	
	private TOrderInfo getOrderInfo(){
		String oid = "1020141205117";
		TOrderInfo bean = iContractInfoService.query(oid);
		return bean;
	}
	
	public void testModify(){
		TOrderInfo bean = getOrderInfo();
		try {
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), ContractStatus.DOING, ContractLifeCycle.SINGED, oper);
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), ContractStatus.DOING, ContractLifeCycle.SINGED, oper);
		} catch (ServiceException e) {
			log.error(e);
			e.printStackTrace();
		}
		TOrderMine entity = new TOrderMine();
		entity.setOid(bean.getId());
		java.util.List<TOrderMine> res = iContractMineService.queryForList(entity);
		log.info(res);
	}
	
	public void testSave(){
		
		TOrderInfo bean = getOrderInfo();
		try {
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), ContractStatus.DRAFT, ContractLifeCycle.DRAFTING, oper);
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), ContractStatus.DRAFT, ContractLifeCycle.DRAFTING, oper);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	
	
	

}
