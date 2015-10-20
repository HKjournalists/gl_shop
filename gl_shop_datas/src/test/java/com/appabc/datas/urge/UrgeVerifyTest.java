package com.appabc.datas.urge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.urge.IUrgeVerifyService;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄木俊
 * @version : 1.0
 * @Create_Date : 2015年8月28日 下午2:56:29
 */

public class UrgeVerifyTest extends AbstractDatasTest {

	@Autowired
	private IUrgeVerifyService urgeVerifyService;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private TaskService taskService;

	@Override
	@Test
	public void mainTest() {
		// TODO Auto-generated method stub
		// getUrgeVerifyByCompanyId();
		// getVerifyList();
		//getInvalidListForTask();
		//getVerifyTaskList();
		//getDepositTaskList();
	}

	public void getUrgeVerifyByCompanyId() {
		String companyId = "201501200000017";
		UrgeVerifyInfo u = urgeVerifyService.queryVerifyInfoByTaskId(companyId);
		System.out.println(u);
	}

	public void getVerifyList() {
		QueryContext<UrgeVerifyInfo> qContext = new QueryContext<UrgeVerifyInfo>();
		qContext = urgeVerifyService.getVerifyList(qContext);
		List<UrgeVerifyInfo> arList = qContext.getQueryResult().getResult();

		System.out.println(arList);
	}

	public void getInvalidListForTask() {
		List<TCompanyInfo> company = companyInfoService.queryNewListForTask();
		System.out.println("列表是" + company.size());
	}

	public void getVerifyTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("finished", false);
		List<Task> tasks = taskService
				.queryForList(params, TaskType.UrgeVerify);
		System.out.println(tasks.size());
	}
	public void getDepositTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("finished", false);
		List<Task> tasks = taskService
				.queryForList(params, TaskType.UrgeDeposit);
		System.out.println(tasks.size());
	}
}
