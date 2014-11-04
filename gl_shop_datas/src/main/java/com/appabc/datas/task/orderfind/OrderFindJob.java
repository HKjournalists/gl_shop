/**  
 * com.appabc.tools.schedule.task.orderfind.OrderFindJob.java  
 *   
 * 2014年10月31日 下午1:49:12  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.orderfind;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.enums.PushEnums.BusinessTypeEnum;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.service.user.IToolUserService;
import com.appabc.tools.utils.ToolsConstant;
import com.appabc.tools.xmpp.IXmppPush;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月31日 下午1:49:12
 */

public class OrderFindJob extends BaseJob {

	private IXmppPush iXmppPush = (IXmppPush)ac.getBean("IXmppPush");
	
	private IToolUserService iToolUserService = (IToolUserService)ac.getBean("IToolUserService");
	
	private IOrderFindService iOrderFindService = (IOrderFindService)ac.getBean("IOrderFindService");
	/* (non-Javadoc)  
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderFind entity = new TOrderFind();
		entity.setStatus(OrderStatusEnum.ORDER_STATUS_YES.getVal());
		List<TOrderFind> result = iOrderFindService.queryForList(entity);
		for(TOrderFind bean : result){
			Date now = DateUtil.getNowDate();
			int days = DateUtil.getDifferDayWithTwoDate(bean.getLimitime(), now);
			if(days>=0){
				bean.setUpdater(ToolsConstant.SCHEDULER);
				bean.setUpdatetime(DateUtil.getNowDate());
				bean.setStatus(OrderStatusEnum.ORDER_STATUS_FAILURE.getVal());
				iOrderFindService.modify(bean);
				
				PushInfoBean piBean = new PushInfoBean();
				piBean.setContent(bean.getTitle()+" 过期下架.");
				piBean.setBusinessType(BusinessTypeEnum.BUSINESS_TYPE_CONTRACT_ING.getVal());
				piBean.setBusinessId(bean.getId());
				iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getCreater()));
			}
		}
		logUtil.info(result);
		logUtil.info(result.size());
		logUtil.info(context.getTrigger().getName());
	}

}
