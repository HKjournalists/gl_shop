package com.appabc.datas.task.cancelcontract;


import com.appabc.bean.enums.ContractInfo.ContractCancelType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.ToolsConstant;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月13日 上午11:59:54
 */

public class CancelContractJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");

	private IContractCancelService iContractCancelService = (IContractCancelService)ac.getBean("IContractCancelService");

	private PrimaryKeyGenerator pkGenerator = ac.getBean(PrimaryKeyGenerator.class);

	private MessageSendManager mesgSender = ac.getBean(MessageSendManager.class);
	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.CANCELING);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			TOrderOperations operator = new TOrderOperations();
			operator.setOid(bean.getId());
			operator.setOperator(bean.getSellerid());
			operator.setType(ContractOperateType.CANCEL_CONFIRM);
			operator.setOrderstatus(ContractLifeCycle.CANCELING);
			List<TOrderOperations> sellerResult = iContractOperationService.queryForList(operator);
			operator.setOperator(bean.getBuyerid());
			List<TOrderOperations> buyerResult = iContractOperationService.queryForList(operator);

			TOrderCancel cancel = new TOrderCancel();
			if(CollectionUtils.isNotEmpty(sellerResult)){
				cancel.setLid(sellerResult.get(0).getId());
			}
			cancel.setCanceltype(ContractCancelType.DUPLEX_CANCEL);
			List<TOrderCancel> cancelResult = iContractCancelService.queryForList(cancel);
			if(CollectionUtils.isEmpty(cancelResult) && CollectionUtils.isNotEmpty(buyerResult)){
				cancel.setLid(buyerResult.get(0).getId());
				cancelResult = iContractCancelService.queryForList(cancel);
			}
			if(DateUtil.getDifferHoursWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate())>=24 && CollectionUtils.isEmpty(cancelResult)){
				List<TOrderOperations> operationLists = iContractOperationService.queryForList(bean.getId());
				if(CollectionUtils.isNotEmpty(operationLists)){
					Date now = DateUtil.getNowDate();

					bean.setLifecycle(operationLists.get(operationLists.size()-2).getOrderstatus());
					bean.setStatus(ContractStatus.DOING);
					bean.setUpdatetime(now);
					bean.setUpdater(ToolsConstant.SCHEDULER);
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
					oper.setType(ContractOperateType.REPEAL_CANCEL);
					oper.setOrderstatus(ContractLifeCycle.DUPLEXCANCEL_FINISHED);
					StringBuilder sb = new StringBuilder("由于买卖双方其中一方未能在24小时确认取消合同，该合同取消失败。合同已进入正常状态，请知悉.");
					//sb.append("由于买方双方其中一方未能在24小时确认取消合同，该合同取消失败。合同已进入正常状态，请知悉。");
					oper.setResult(sb.toString());
					oper.setRemark(sb.toString());
					try{
						iContractOperationService.add(oper);
					}catch(Exception e){
						logUtil.debug(e.getMessage(), e);
					}
					//send system message and xmpp message
					MessageInfoBean mi = new MessageInfoBean(
							MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,
							bean.getId(),
							bean.getSellerid(),
							SystemMessageContent.getMsgContentOfContractCancelFailure(bean.getId()));
					mi.setSendSystemMsg(true);
					mi.setSendPushMsg(true);
					mesgSender.msgSend(mi);

					mi.setCid(bean.getBuyerid());
					mesgSender.msgSend(mi);
				}
			}
		}
		logUtil.info(result);
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
