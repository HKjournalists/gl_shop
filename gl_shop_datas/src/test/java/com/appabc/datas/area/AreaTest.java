/**
 *
 */
package com.appabc.datas.area;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.utils.AreaManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月30日 下午2:24:36
 */
public class AreaTest extends AbstractDatasTest{
	
	@Autowired
	private AreaManager areaManager;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		testAreaGetName();
	}
	
	public void testAreaGetName(){
		String areaVal = "654226000000";
		String areaName  = areaManager.getAreaName(areaVal);
		String areaFullName  = areaManager.getFullAreaName(areaVal);
		
		System.out.println(areaName);
		System.out.println(areaFullName);
	}

}
