/**  
 * com.appabc.quartz.tool.job.ContractConfirmGoodsInfoJob.java  
 *   
 * 2014年10月29日 下午2:53:37  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmgoodsinfo;

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
 * @Create_Date  : 2014年10月29日 下午2:53:37
 */

public class ContractConfirmGoodsInfoJob extends BaseJob {
	
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
		entity.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS.getValue());
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			if(days>=7){
				Date now = DateUtil.getNowDate();
				bean.setLifecycle(ContractLifeCycle.RECEIVED_GOODS.getValue());
				bean.setStatus(ContractStatus.DOING.getValue());
				bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(now);
				iContractInfoService.modify(bean);
				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.CONFIRM_RECEIVEGOODS.getValue());
				oper.setOrderstatus(ContractLifeCycle.RECEIVED_GOODS.getValue());
				StringBuffer sb = new StringBuffer(ToolsConstant.SCHEDULER);
				sb.append("买家超时未确认收货,系统自动收货.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				
				iContractOperationService.add(oper);
				PushInfoBean piBean = new PushInfoBean();
				piBean.setContent(sb.toString());
				piBean.setBusinessType(BusinessTypeEnum.BUSINESS_TYPE_ORDER_FIND.getVal());
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
