/**  
 * com.appabc.quartz.tool.job.ContractConfirmJob.java  
 *   
 * 2014年10月27日 下午6:34:14  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmcontract;


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
 * @Create_Date  : 2014年10月27日 下午6:34:14
 */

public class ContractConfirmJob extends BaseJob {

	private IXmppPush iXmppPush = (IXmppPush)ac.getBean("IXmppPush");

	private IToolUserService iToolUserService = (IToolUserService)ac.getBean("IToolUserService");
	
	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");
	
	private PrimaryKeyGenerator pkGenerator = (PrimaryKeyGenerator)ac.getBean(PrimaryKeyGenerator.class);
	
	/* (non-Javadoc)  
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.DRAFTING.getValue());
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			int hours = DateUtil.getDifferHoursWithTwoDate(bean.getCreatime(), DateUtil.getNowDate());
			if(hours>=48){
				Date now = DateUtil.getNowDate();
				bean.setLifecycle(ContractLifeCycle.TIMEOUT_FINISHED.getValue());
				bean.setStatus(ContractStatus.FINISHED.getValue());
				bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(now);
				iContractInfoService.modify(bean);
				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.CONFRIM_CONTRACT.getValue());
				oper.setOrderstatus(ContractLifeCycle.TIMEOUT_FINISHED.getValue());
				StringBuffer sb = new StringBuffer(ToolsConstant.SCHEDULER);
				sb.append("合同起草超时失败.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				
				iContractOperationService.add(oper);
				PushInfoBean piBean = new PushInfoBean();
				piBean.setContent(sb.toString());
				piBean.setBusinessType(BusinessTypeEnum.BUSINESS_TYPE_CONTRACT_SIGN.getVal());
				piBean.setBusinessId(bean.getId());
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getSellerid()));
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getBuyerid()));
			}
		}
		logUtil.info(result);
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
