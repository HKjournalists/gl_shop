package com.appabc.datas.contract;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.task.cancelcontract.CancelContractJob;
import com.appabc.datas.task.cancelcontract.CancelContractTrigger;
import com.appabc.datas.task.confirmcontract.ContractConfirmJob;
import com.appabc.datas.task.confirmcontract.ContractConfirmTrigger;
import com.appabc.datas.task.confirmgoodsinfo.ContractConfirmGoodsInfoJob;
import com.appabc.datas.task.confirmgoodsinfo.ContractConfirmGoodsInfoTrigger;
import com.appabc.datas.task.evaluationcontract.ContractEvaluationJob;
import com.appabc.datas.task.evaluationcontract.ContractEvaluationTrigger;
import com.appabc.datas.task.orderfind.OrderFindJob;
import com.appabc.datas.task.orderfind.OrderFindTrigger;
import com.appabc.datas.task.payfunds.ContractPayFundsJob;
import com.appabc.datas.task.payfunds.ContractPayFundsTrigger;
import com.appabc.tools.schedule.ScheduleInfoManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月3日 下午4:48:16
 */

public class IScheduleTest extends AbstractDatasTest {

	@Autowired
	private ScheduleInfoManager scheduManager;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		scheduManager.registerScheduleInfo(CancelContractJob.class, CancelContractTrigger.class);
		scheduManager.registerScheduleInfo(ContractConfirmJob.class, ContractConfirmTrigger.class);
		scheduManager.registerScheduleInfo(ContractConfirmGoodsInfoJob.class, ContractConfirmGoodsInfoTrigger.class);
		scheduManager.registerScheduleInfo(ContractEvaluationJob.class, ContractEvaluationTrigger.class);
		scheduManager.registerScheduleInfo(OrderFindJob.class, OrderFindTrigger.class);
		scheduManager.registerScheduleInfo(ContractPayFundsJob.class, ContractPayFundsTrigger.class);
	}

}
