package com.appabc.datas.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.task.agreefinalestimate.AgreeFinalEstimateJob;
import com.appabc.datas.task.agreefinalestimate.AgreeFinalEstimateTrigger;
import com.appabc.datas.task.cancelcontract.CancelContractJob;
import com.appabc.datas.task.cancelcontract.CancelContractTrigger;
import com.appabc.datas.task.company.CompanyAuthApplyJob;
import com.appabc.datas.task.company.CompanyAuthApplyTrigger;
import com.appabc.datas.task.confirmcontract.ContractConfirmJob;
import com.appabc.datas.task.confirmcontract.ContractConfirmTrigger;
import com.appabc.datas.task.confirmgoodsinfo.ContractConfirmGoodsInfoJob;
import com.appabc.datas.task.confirmgoodsinfo.ContractConfirmGoodsInfoTrigger;
import com.appabc.datas.task.contractmatch.ContractMatchJob;
import com.appabc.datas.task.contractmatch.ContractMatchTrigger;
import com.appabc.datas.task.datacount.DataCountAllUserJob;
import com.appabc.datas.task.datacount.DataCountAllUserTrigger;
import com.appabc.datas.task.evaluationcontract.ContractEvaluationJob;
import com.appabc.datas.task.evaluationcontract.ContractEvaluationTrigger;
import com.appabc.datas.task.expirecontract.ExpireContractJob;
import com.appabc.datas.task.expirecontract.ExpireContractTrigger;
import com.appabc.datas.task.invalidverify.InvalidUrgeVerifyJob;
import com.appabc.datas.task.invalidverify.InvalidUrgeVerifyTrigger;
import com.appabc.datas.task.onlinePay.OnlinePayJob;
import com.appabc.datas.task.onlinePay.OnlinePayTrigger;
import com.appabc.datas.task.orderfind.OrderFindJob;
import com.appabc.datas.task.orderfind.OrderFindTrigger;
import com.appabc.datas.task.payfunds.ContractPayFundsJob;
import com.appabc.datas.task.payfunds.ContractPayFundsTrigger;
import com.appabc.datas.task.urgeverify.UrgeVerifyJob;
import com.appabc.datas.task.urgeverify.UrgeVerifyTrigger;
import com.appabc.tools.schedule.ScheduleInfoManager;

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
		scheduManager.registerScheduleInfo(ExpireContractJob.class, ExpireContractTrigger.class);
		scheduManager.registerScheduleInfo(OrderFindJob.class, OrderFindTrigger.class);
		scheduManager.registerScheduleInfo(ContractPayFundsJob.class, ContractPayFundsTrigger.class);
		scheduManager.registerScheduleInfo(AgreeFinalEstimateJob.class, AgreeFinalEstimateTrigger.class);
		scheduManager.registerScheduleInfo(CompanyAuthApplyJob.class, CompanyAuthApplyTrigger.class);
		scheduManager.registerScheduleInfo(ContractMatchJob.class, ContractMatchTrigger.class);
		scheduManager.registerScheduleInfo(OnlinePayJob.class, OnlinePayTrigger.class);
		scheduManager.registerScheduleInfo(DataCountAllUserJob.class, DataCountAllUserTrigger.class);
		scheduManager.registerScheduleInfo(InvalidUrgeVerifyJob.class, InvalidUrgeVerifyTrigger.class);
		scheduManager.registerScheduleInfo(UrgeVerifyJob.class, UrgeVerifyTrigger.class);
	}

}
