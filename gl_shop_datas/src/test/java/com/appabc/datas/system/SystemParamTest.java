/**
 *
 */
package com.appabc.datas.system;

import com.appabc.bean.pvo.TSystemParams;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.service.system.ISystemParamsService;
import com.appabc.tools.utils.SystemParamsManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月3日 上午10:53:01
 */
public class SystemParamTest extends AbstractDatasTest {

	@Autowired
	private ISystemParamsService systemParamServices;
	@Autowired
	private SystemParamsManager systemParamsManager;

	public void testAdd(){

		TSystemParams t = new TSystemParams();
		t.setDefaultvalue("111");
		t.setDescription("测试");
		t.setPname("TEST");
		t.setPtype("string");
		t.setPvalue("100");
		t.setUpdater("李四");
		t.setUpdatetime(Calendar.getInstance().getTime());

		this.systemParamServices.add(t);
	}

	public void testQueryByEntity(){
		TSystemParams t = new TSystemParams();
		t.setPname("TEST");
		TSystemParams tsp = this.systemParamServices.query(t);

		System.out.println(tsp);
	}

	public void testQuery(){

		String b = systemParamsManager.getString(SystemConstant.USERTOKEN_EFF_TIME_LENGTH);
		System.out.println(b);
		int a = systemParamsManager.getInt("TEST");
		System.out.println(a);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
		// TODO Auto-generated method stub

	}

}
