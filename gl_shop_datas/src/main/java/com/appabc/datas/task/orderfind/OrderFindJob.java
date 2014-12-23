/**
 * com.appabc.tools.schedule.task.orderfind.OrderFindJob.java
 *
 * 2014年10月31日 下午1:49:12
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.orderfind;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.bean.PushInfoBean;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.service.user.IToolUserService;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.ToolsConstant;
import com.appabc.tools.xmpp.IXmppPush;

import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;

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

	private MessageSendManager mesgSender = ac.getBean(MessageSendManager.class);
	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderFind entity = new TOrderFind();
		entity.setStatus(OrderStatusEnum.ORDER_STATUS_YES);
		try{
			List<TOrderFind> result = iOrderFindService.queryForList(entity);
			for(TOrderFind bean : result){
				Date now = DateUtil.getNowDate();
				int days = DateUtil.getDifferDayWithTwoDate(bean.getLimitime(), now);
				if(days>=0){
					bean.setUpdater(ToolsConstant.SCHEDULER);
					bean.setUpdatetime(DateUtil.getNowDate());
					bean.setStatus(OrderStatusEnum.ORDER_STATUS_FAILURE);
					bean.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID);
					try{
						iOrderFindService.modify(bean);
					}catch(Exception e){
						logUtil.debug(e.getMessage(), e);
					}

					String msg = "您的询单"+bean.getTitle()+"已于"+DateUtil.DateToStr(now, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)+"过期失效.";
					PushInfoBean piBean = new PushInfoBean();
					piBean.setContent(msg);
					piBean.setBusinessType(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING);
					piBean.setBusinessId(bean.getId());
					iXmppPush.pushToSingle(piBean, iToolUserService.getUserByCid(bean.getCreater()));

					MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_ORDER_FIND,
							bean.getId(),
							bean.getCid(),
							new SystemMessageContent(msg));
					mi.setSendSystemMsg(true);
					mi.setSendPushMsg(true);
					mesgSender.msgSend(mi);
				}
			}
			logUtil.info(result);
			logUtil.info(result.size());
			logUtil.info(context.getTrigger().getName());
		}catch(Exception e){
			logUtil.debug(e.getMessage(), e);
		}
	}
}
