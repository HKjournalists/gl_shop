/**  
 * com.appabc.quartz.tool.job.ContractEvaluationJob.java  
 *   
 * 2014年10月29日 下午3:18:37  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.evaluationcontract;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.ToolsConstant;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月29日 下午3:18:37
 */

public class ContractEvaluationJob extends BaseJob {

	/*private IXmppPush iXmppPush = (IXmppPush)ac.getBean("IXmppPush");

	private IToolUserService iToolUserService = (IToolUserService)ac.getBean("IToolUserService");*/
	
	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");
	
	private ICompanyEvaluationService iCompanyEvaluationService	 = (ICompanyEvaluationService)ac.getBean("ICompanyEvaluationService");
	
	private PrimaryKeyGenerator pkGenerator = (PrimaryKeyGenerator)ac.getBean(PrimaryKeyGenerator.class);
	
	/* (non-Javadoc)  
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS.getValue());
		List<TOrderInfo> result1 = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result1){
			int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			TCompanyEvaluation tce = new TCompanyEvaluation();
			tce.setOid(bean.getId());
			tce.setCreater(bean.getSellerid());
			List<TCompanyEvaluation> res = iCompanyEvaluationService.queryForList(tce);
			if(days>=3 && CollectionUtils.isEmpty(res)){
				Date now = DateUtil.getNowDate();
				//bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED.getValue());
				//bean.setStatus(ContractStatus.FINISHED.getValue());
				//bean.setUpdater(ToolsConstant.SCHEDULER);
				//bean.setUpdatetime(now);
				//iContractService.modify(bean);
				
				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.EVALUATION_CONTRACT.getValue());
				oper.setOrderstatus(bean.getLifecycle());
				StringBuffer sb = new StringBuffer(ToolsConstant.SCHEDULER);
				sb.append("系统自动给卖家评价合同.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				iContractOperationService.add(oper);
				
				TCompanyEvaluation t = new TCompanyEvaluation();
				t.setEvaluation(sb.toString());
				t.setOid(bean.getId());
				t.setCid(bean.getBuyerid());
				t.setCredit(5);
				t.setSatisfaction(5);
				t.setCreater(ToolsConstant.SCHEDULER);
				t.setCratedate(now);
				iCompanyEvaluationService.add(t);
				
				/*PushInfoBean piBean = new PushInfoBean();
				piBean.setContent(sb.toString());
				piBean.setBusinessType(BusinessTypeEnum.BUSINESS_TYPE_CONTRACT_ING.getVal());
				piBean.setBusinessId(bean.getId());
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getSellerid()));
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getBuyerid()));*/
			}
		}
		logUtil.info(result1);
		logUtil.info(result1.size());
		
		entity.setLifecycle(ContractLifeCycle.RECEIVED_GOODS.getValue());
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			TCompanyEvaluation tce = new TCompanyEvaluation();
			tce.setOid(bean.getId());
			tce.setCreater(bean.getBuyerid());
			List<TCompanyEvaluation> res = iCompanyEvaluationService.queryForList(tce);
			int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			if(days>=3 && CollectionUtils.isEmpty(res)){
				Date now = DateUtil.getNowDate();
				//bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED.getValue());
				//bean.setStatus(ContractStatus.FINISHED.getValue());
				/*bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(now);
				iContractService.modify(bean);*/
				
				TOrderOperations oper = new TOrderOperations();
				oper.setId(pkGenerator.generatorBusinessKeyByBid("CONTRACTOPERATIONID"));
				oper.setOid(bean.getId());
				oper.setOperator(ToolsConstant.SCHEDULER);
				oper.setOperationtime(now);
				oper.setType(ContractOperateType.EVALUATION_CONTRACT.getValue());
				oper.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED.getValue());
				StringBuffer sb = new StringBuffer(ToolsConstant.SCHEDULER);
				sb.append("系统自动给买家评价合同.");
				oper.setResult(sb.toString());
				oper.setRemark(sb.toString());
				iContractOperationService.add(oper);
				
				TCompanyEvaluation t = new TCompanyEvaluation();
				t.setEvaluation(sb.toString());
				t.setOid(bean.getId());
				t.setCid(bean.getSellerid());
				t.setCredit(5);
				t.setSatisfaction(5);
				t.setCreater(ToolsConstant.SCHEDULER);
				t.setCratedate(now);
				iCompanyEvaluationService.add(t);
				
				/*PushInfoBean piBean = new PushInfoBean();
				piBean.setContent(sb.toString());
				piBean.setBusinessType(BusinessTypeEnum.BUSINESS_TYPE_CONTRACT_ING.getVal());
				piBean.setBusinessId(bean.getId());
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getSellerid()));
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getBuyerid()));*/
			}
		}
		logUtil.info(result);
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
