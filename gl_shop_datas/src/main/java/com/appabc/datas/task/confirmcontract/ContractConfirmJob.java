/**
 * com.appabc.quartz.tool.job.ContractConfirmJob.java
 *
 * 2014年10月27日 下午6:34:14
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmcontract;


import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.ToolsConstant;

import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;


/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月27日 下午6:34:14
 */

public class ContractConfirmJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");

	private PrimaryKeyGenerator pkGenerator = ac.getBean(PrimaryKeyGenerator.class);

	private MessageSendManager mesgSender = ac.getBean(MessageSendManager.class);
	/* (non-Javadoc)
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.DRAFTING);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			int hours = DateUtil.getDifferHoursWithTwoDate(bean.getCreatime(), DateUtil.getNowDate());
			if(hours>=24){
				Date now = DateUtil.getNowDate();
				bean.setLifecycle(ContractLifeCycle.TIMEOUT_FINISHED);
				bean.setStatus(ContractStatus.FINISHED);
				bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(now);
				try{
					iContractInfoService.modify(bean);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}
				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.CONFRIM_CONTRACT);
				oper.setOrderstatus(ContractLifeCycle.TIMEOUT_FINISHED);
				StringBuilder sb = new StringBuilder("由于买卖双方在24小时内未一致同意确认该合同，该合同未正式生效.");
				//sb.append("由于买卖双方其中一方未能在24小时内及时确认合同，该合同未正式生效。");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				try{
					iContractOperationService.add(oper);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}
				//send the system message and xmpp message
				MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,bean.getId(),bean.getSellerid(),new SystemMessageContent(sb.toString()));
				mi.setSendSystemMsg(true);
				mi.setSendPushMsg(true);
				mesgSender.msgSend(mi);

				mi.setCid(bean.getBuyerid());
				mesgSender.msgSend(mi);
			}
		}
		logUtil.info(result);
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
