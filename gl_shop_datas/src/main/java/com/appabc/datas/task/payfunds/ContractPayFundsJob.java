/**  
 * com.appabc.quartz.tool.job.ContractPayFundsJob.java  
 *   
 * 2014年10月29日 上午10:59:50  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.payfunds;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.enums.ContractInfo.ContractStatus;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.enums.PushEnums.BusinessTypeEnum;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.service.user.IToolUserService;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.ToolsConstant;
import com.appabc.tools.xmpp.IXmppPush;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月29日 上午10:59:50
 */

public class ContractPayFundsJob extends BaseJob {

	private IXmppPush iXmppPush = (IXmppPush)ac.getBean("IXmppPush");

	private IToolUserService iToolUserService = (IToolUserService)ac.getBean("IToolUserService");
	
	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");
	
	private PrimaryKeyGenerator pkGenerator = (PrimaryKeyGenerator)ac.getBean(PrimaryKeyGenerator.class);
	
	private void timeoutContractPayFund(List<TOrderInfo> result){
		for(TOrderInfo bean : result){
			int hours = DateUtil.getDifferHoursWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			if(hours>=48){
				Date now = DateUtil.getNowDate();
				bean.setLifecycle(ContractLifeCycle.ARBITRATION.getValue());
				bean.setStatus(ContractStatus.FINISHED.getValue());
				bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(now);
				iContractInfoService.modify(bean);
				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.ARBITRATION_CONTRACT.getValue());
				oper.setOrderstatus(ContractLifeCycle.ARBITRATION.getValue());
				StringBuffer sb = new StringBuffer(ToolsConstant.SCHEDULER);
				sb.append("买家未付款成功终止结束");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				
				iContractOperationService.add(oper);
				PushInfoBean piBean = new PushInfoBean();
				piBean.setContent(sb.toString());
				piBean.setBusinessType(BusinessTypeEnum.BUSINESS_TYPE_CONTRACT_ING.getVal());
				piBean.setBusinessId(bean.getId());
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getSellerid()));
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getBuyerid()));
			}
		}
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.SINGED.getValue());
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		timeoutContractPayFund(result);
		entity.setLifecycle(ContractLifeCycle.IN_THE_PAYMENT.getValue());
		List<TOrderInfo> result1 = iContractInfoService.queryForList(entity);
		timeoutContractPayFund(result1);
		logUtil.info(result);
		logUtil.info(result1);
		logUtil.info(context.getTrigger().getName());
	}

}
