package com.appabc.datas.task.expirecontract;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderMine;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月5日 上午11:59:50
 */

public class ExpireContractJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");
	
	private IContractMineService iContractMineService = (IContractMineService)ac.getBean("IContractMineService");
	
	private PrimaryKeyGenerator pkGenerator = ac.getBean(PrimaryKeyGenerator.class);

	private MessageSendManager mesgSender = ac.getBean(MessageSendManager.class);
	
	private void setContractExpireDayFinish(TOrderInfo bean){
		TOrderMine t = new TOrderMine();
		t.setCid(bean.getId());
		t.setOid(bean.getBuyerid());
		t.setStatus(ContractStatus.FINISHED);
		TOrderMine btom = iContractMineService.query(t);
		if(btom!=null){
			return ;
		}
		
		t.setCid(bean.getId());
		t.setOid(bean.getSellerid());
		t.setStatus(ContractStatus.FINISHED);
		TOrderMine stom = iContractMineService.query(t);
		if(stom!=null){
			return ;
		}
		
		Date now = DateUtil.getNowDate();
		StringBuilder sbm = new StringBuilder(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_OUT_OF_LIMITTIMETIPS, Locale.forLanguageTag("datas")));
		try {
			TOrderOperations oper = new TOrderOperations();
			oper.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
			oper.setOid(bean.getId());
			oper.setOperator(bean.getBuyerid());
			oper.setOperationtime(now);
			oper.setType(ContractOperateType.MOVE_TO_FINISHED_CONTRACT);
			oper.setOrderstatus(bean.getLifecycle());
			oper.setResult(sbm.toString());
			oper.setRemark(sbm.toString());
			
			TOrderOperations oper1 = new TOrderOperations();
			oper1.setId(pkGenerator.getPKey(DataSystemConstant.CONTRACTOPERATIONID));
			oper1.setOid(bean.getId());
			oper1.setOperator(bean.getSellerid());
			oper1.setOperationtime(now);
			oper1.setType(ContractOperateType.MOVE_TO_FINISHED_CONTRACT);
			oper1.setOrderstatus(bean.getLifecycle());
			oper1.setResult(sbm.toString());
			oper1.setRemark(sbm.toString());
			try{
				iContractOperationService.add(oper);
				iContractOperationService.add(oper1);
			}catch(Exception e){
				logUtil.debug(e);
			}
			//send the system message and xmpp message
			MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SIGN,bean.getId(),bean.getSellerid(),new SystemMessageContent(sbm.toString()));
			mi.setSendSystemMsg(true);
			mi.setSendPushMsg(true);
			mesgSender.msgSend(mi);

			mi.setCid(bean.getBuyerid());
			mesgSender.msgSend(mi);
			
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), ContractStatus.FINISHED, bean.getLifecycle(), bean.getBuyerid());
			iContractMineService.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), ContractStatus.FINISHED, bean.getLifecycle(), bean.getSellerid());
		} catch (ServiceException e) {
			e.printStackTrace();
			logUtil.error(e);
		}
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		Date now = DateUtil.getNowDate();
		//过滤为未付货款结束的合同的有效期.
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.BUYER_UNPAY_FINISHED);
		entity.setStatus(ContractStatus.FINISHED);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		logUtil.info("Enter the Contract Life Cycle with the Buyer unpay Finished to finish the Contract.");
		for(TOrderInfo bean : result){
			int diffDays = DateUtil.getDifferDayWithTwoDate(now, bean.getLimittime());
			if(diffDays <= 0){
				logUtil.info(" set the Contract id is : "+bean.getId()+" and the Contract Life Cycle is "+bean.getLifecycle()+" to the Contract Expire Day List. ");
				this.setContractExpireDayFinish(bean);
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
		//过滤单方取消结束合同的有效期
		entity.setStatus(ContractStatus.FINISHED);
		entity.setLifecycle(ContractLifeCycle.SINGLECANCEL_FINISHED);
		logUtil.info("Enter the Contract Life Cycle with the Single Cancel Finished to finish the Contract.");
		List<TOrderInfo> result1 = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result1){
			int diffDays = DateUtil.getDifferDayWithTwoDate(now, bean.getLimittime());
			if(diffDays <= 0){
				logUtil.info(" set the Contract id is : "+bean.getId()+" and the Contract Life Cycle is "+bean.getLifecycle()+" to the Contract Expire Day List. ");
				this.setContractExpireDayFinish(bean);
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result1) ? result1.size() : result1);
		//过滤合同的正常结束的有效期
		entity.setStatus(ContractStatus.FINISHED);
		entity.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		logUtil.info("Enter the Contract Life Cycle with the Normal Finished to finish the Contract.");
		List<TOrderInfo> result2 = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result2){
			int diffDays = DateUtil.getDifferDayWithTwoDate(now, bean.getLimittime());
			if(diffDays <= 0){
				logUtil.info(" set the Contract id is : "+bean.getId()+" and the Contract Life Cycle is "+bean.getLifecycle()+" to the Contract Expire Day List. ");
				this.setContractExpireDayFinish(bean);
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result2) ? result2.size() : result2);
	}

}
