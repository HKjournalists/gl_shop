/**
 * com.appabc.quartz.tool.job.ContractEvaluationJob.java
 *
 * 2014年10月29日 下午3:18:37
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.evaluationcontract;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyRankingService;
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
 * @Create_Date  : 2014年10月29日 下午3:18:37
 */

public class ContractEvaluationJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");

	private ICompanyEvaluationService iCompanyEvaluationService	 = (ICompanyEvaluationService)ac.getBean("ICompanyEvaluationService");

	private ICompanyRankingService iCompanyRankingService = (ICompanyRankingService)ac.getBean("ICompanyRankingService");

	private PrimaryKeyGenerator pkGenerator = ac.getBean(PrimaryKeyGenerator.class);

	private MessageSendManager mesgSender = ac.getBean(MessageSendManager.class);

	/* (non-Javadoc)
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS);
		List<TOrderInfo> result1 = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result1){
			int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			TCompanyEvaluation tce = new TCompanyEvaluation();
			tce.setOid(bean.getId());
			tce.setCreater(bean.getBuyerid());
			List<TCompanyEvaluation> res = iCompanyEvaluationService.queryForList(tce);
			if(days>=7 && CollectionUtils.isEmpty(res)){
				Date now = DateUtil.getNowDate();
				//bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED.getValue());
				//bean.setStatus(ContractStatus.FINISHED.getValue());
				//bean.setUpdater(ToolsConstant.SCHEDULER);
				//bean.setUpdatetime(now);
				//iContractService.modify(bean);

				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.EVALUATION_CONTRACT);
				oper.setOrderstatus(bean.getLifecycle());
				StringBuilder sb = new StringBuilder("由于您7天内未评价对方，系统已默认设置为好评.");
				//sb.append("系统自动给卖家评价合同.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				try{
					iContractOperationService.add(oper);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}

				TCompanyEvaluation t = new TCompanyEvaluation();
				t.setEvaluation(sb.toString());
				t.setOid(bean.getId());
				t.setCid(bean.getBuyerid());
				t.setCredit(5);
				t.setSatisfaction(5);
				t.setCreater(ToolsConstant.SCHEDULER);
				t.setCratedate(now);
				try{
					iCompanyEvaluationService.add(t);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}

				//计算评价相关的交易满意度,交易诚信度
				try {
					iCompanyRankingService.calculateTradeEvaluationRate(t.getCid());
				} catch (ServiceException e) {
					e.printStackTrace();
					logUtil.debug(e);
				}

				MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION,
						bean.getId(),
						bean.getSellerid(),
						new SystemMessageContent(sb.toString()));
				mi.setSendSystemMsg(true);
				mi.setSendPushMsg(true);
				mesgSender.msgSend(mi);

				/*mi.setCid(bean.getBuyerid());
				mesgSender.msgSend(mi);*/
			}
		}
		logUtil.info(result1);
		logUtil.info(result1.size());

		entity.setLifecycle(ContractLifeCycle.RECEIVED_GOODS);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			TCompanyEvaluation tce = new TCompanyEvaluation();
			tce.setOid(bean.getId());
			tce.setCreater(bean.getSellerid());
			List<TCompanyEvaluation> res = iCompanyEvaluationService.queryForList(tce);
			int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			if(days>=7 && CollectionUtils.isEmpty(res)){
				Date now = DateUtil.getNowDate();
				//bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED.getValue());
				//bean.setStatus(ContractStatus.FINISHED.getValue());
				/*bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(now);
				iContractService.modify(bean);*/

				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.EVALUATION_CONTRACT);
				oper.setOrderstatus(bean.getLifecycle());
				StringBuffer sb = new StringBuffer("由于您7天内未评价对方，系统已默认设置为好评.");
				//sb.append("由于您7天内未评价对方，系统已默认设置为好评.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				try{
					iContractOperationService.add(oper);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}

				TCompanyEvaluation t = new TCompanyEvaluation();
				t.setEvaluation(sb.toString());
				t.setOid(bean.getId());
				t.setCid(bean.getSellerid());
				t.setCredit(5);
				t.setSatisfaction(5);
				t.setCreater(ToolsConstant.SCHEDULER);
				t.setCratedate(now);
				try{
					iCompanyEvaluationService.add(t);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}

				//计算评价相关的交易满意度,交易诚信度
				try {
					iCompanyRankingService.calculateTradeEvaluationRate(t.getCid());
				} catch (ServiceException e) {
					e.printStackTrace();
					logUtil.debug(e);
				}

				MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION,
						bean.getId(),
						bean.getBuyerid(),
						new SystemMessageContent(sb.toString()));
				mi.setSendSystemMsg(true);
				mi.setSendPushMsg(true);
				mesgSender.msgSend(mi);

				/*mi.setCid(bean.getBuyerid());
				mesgSender.msgSend(mi);*/
			}
		}
		logUtil.info(result);
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
