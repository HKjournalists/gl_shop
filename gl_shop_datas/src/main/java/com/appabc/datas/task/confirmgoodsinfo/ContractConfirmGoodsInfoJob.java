/**
 * com.appabc.quartz.tool.job.ContractConfirmGoodsInfoJob.java
 *
 * 2014年10月29日 下午2:53:37
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmgoodsinfo;

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
 * @Create_Date  : 2014年10月29日 下午2:53:37
 */

public class ContractConfirmGoodsInfoJob extends BaseJob {

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
		entity.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			//int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			int hours = DateUtil.getDifferHoursWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			if(hours>=48){
				Date now = DateUtil.getNowDate();
				bean.setLifecycle(ContractLifeCycle.RECEIVED_GOODS);
				bean.setStatus(ContractStatus.DOING);
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
				oper.setType(ContractOperateType.CONFIRM_RECEIVEGOODS);
				oper.setOrderstatus(ContractLifeCycle.RECEIVED_GOODS);
				StringBuilder sb = new StringBuilder("由于买家超过72小时未确认收货，系统已黙认收货。接下来请给评价一下交易中的对方.");
				//sb.append("买家超时未确认收货,系统自动收货.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				try{
					iContractOperationService.add(oper);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}

				//send the system message and xmpp message
				MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,bean.getId(),bean.getSellerid(),new SystemMessageContent(sb.toString()));
				mi.setSendSystemMsg(true);
				mi.setSendPushMsg(true);
				mesgSender.msgSend(mi);

				mi.setCid(bean.getBuyerid());
				mesgSender.msgSend(mi);
			}
		}
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
