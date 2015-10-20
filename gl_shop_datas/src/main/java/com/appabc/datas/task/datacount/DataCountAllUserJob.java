/**
 *
 */
package com.appabc.datas.task.datacount;

import java.util.Calendar;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TDataAllUser;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.impl.CompanyInfoServiceImpl;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.impl.ContractInfoServiceImpl;
import com.appabc.datas.service.data.IDataAllUserService;
import com.appabc.datas.service.data.impl.DataAllUserServiceImpl;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.impl.OrderFindServiceImpl;
import com.appabc.datas.service.system.ISystemLogService;
import com.appabc.datas.service.system.impl.SystemLogServiceImpl;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.service.user.impl.UserServiceImpl;
import com.appabc.datas.tool.UserLoginStatusManager;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.pay.service.local.impl.PassbookInfoServiceImpl;
import com.appabc.tools.schedule.utils.BaseJob;

/**
 * @Description : 每天进行前一天所有用户数据统计
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月14日 下午5:52:23
 */
public class DataCountAllUserJob  extends BaseJob {
	
	private IUserService userService = super.ac.getBean(UserServiceImpl.class);
	private ICompanyInfoService companyInfoService = super.ac.getBean(CompanyInfoServiceImpl.class);
	private IOrderFindService orderFindService = super.ac.getBean(OrderFindServiceImpl.class);
	private IContractInfoService contractInfoService = super.ac.getBean(ContractInfoServiceImpl.class);
	private ISystemLogService systemLogService = super.ac.getBean(SystemLogServiceImpl.class);
	private IPassbookInfoService passbookInfoService = super.ac.getBean(PassbookInfoServiceImpl.class);
	private IDataAllUserService dataAllUserService = super.ac.getBean(DataAllUserServiceImpl.class);
	private UserLoginStatusManager userLoginStatusManager = super.ac.getBean(UserLoginStatusManager.class);

	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		
		TDataAllUser dau = new TDataAllUser();
		dau.setAllUserNum(this.userService.queryCount(null));
		
		TCompanyInfo ci = new TCompanyInfo();
		ci.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);
		dau.setAuthUserNum(companyInfoService.queryCount(ci) -1 ); // 平台企业账户不计入
		
		dau.setBailUserNum(passbookInfoService.queryCountAllUsersGuaranty());
		
		TOrderFind of = new TOrderFind();
		of.setOverallstatus(OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
		of.setOveralltype(OrderOverallTypeEnum.ORDER_OVERALL_TYPE_FORMAL);
		of.setType(OrderTypeEnum.ORDER_TYPE_BUY);
		dau.setBuyGoodsNum(orderFindService.queryCount(of));
		
		of.setType(OrderTypeEnum.ORDER_TYPE_SELL);
		dau.setSellGoodsNum(orderFindService.queryCount(of));
		
		TOrderInfo oi = new TOrderInfo();
		oi.setStatus(ContractStatus.DOING);
		dau.setContractIngNum(contractInfoService.queryCount(oi));
		
		dau.setContractEndNum(contractInfoService.queryCountOfFinished());
		
		systemLogService.saveTheLogsInTheCache(); // 先保存缓存中的日志再查询。
//		dau.setLoginUserNum(systemLogService.queryCountLoginUserOfDate(Calendar.getInstance().getTime()));
		Map<byte[],byte[]> mapAllUserStatus = userLoginStatusManager.getAllUsersLogin();
		dau.setLoginUserNum(mapAllUserStatus != null ? mapAllUserStatus.size() : 0);
		userLoginStatusManager.delAll(); // 清除缓存
		
		dau.setCreateDate(Calendar.getInstance().getTime());
		
		dataAllUserService.add(dau);
		
		try {
			dataAllUserService.jobAutoSendAllUserDataWithEmail();
			logUtil.info("send the all user data with email success.");
		} catch (ServiceException e) {
			this.logUtil.error(e.getMessage(),e);
		}
		logUtil.info("DataCountAllUserJob Excecute......");
	}

}
