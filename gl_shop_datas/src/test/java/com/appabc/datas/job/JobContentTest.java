/**
 *
 */
package com.appabc.datas.job;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.task.company.CompanyAuthApplyJob;
import com.appabc.datas.task.contractmatch.ContractMatchJob;
import com.appabc.datas.task.datacount.DataCountAllUserJob;
import com.appabc.datas.task.invalidverify.InvalidUrgeVerifyJob;
import com.appabc.datas.task.urgeverify.UrgeVerifyJob;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月2日 下午8:07:29
 */
public class JobContentTest extends AbstractDatasTest {

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		doCompanyAuthApplyJob();
//		doContractMatchJob();
//		doDataCountAllUserJob();
//		doUrgeVerifyJob();
//		doInvalidUrgeVerifyJob();
	}
	
	public void doCompanyAuthApplyJob(){ 
		
		CompanyAuthApplyJob job = new CompanyAuthApplyJob();
		job.doExecutionJob(null);
		
	}
	
	public void doContractMatchJob(){
		
		ContractMatchJob job = new ContractMatchJob();
		job.doExecutionJob(null);
		
	}
	
	public void doDataCountAllUserJob(){
		DataCountAllUserJob job = new DataCountAllUserJob();
		job.doExecutionJob(null);
	}

	public void doUrgeVerifyJob()
	{
		UrgeVerifyJob job=new UrgeVerifyJob();
		job.doExecutionJob(null);
	}
	
	public void doInvalidUrgeVerifyJob()
	{
		InvalidUrgeVerifyJob job=new InvalidUrgeVerifyJob();
		job.doExecutionJob(null);
	}
}
