/**
 *
 */
package com.appabc.datas.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.common.utils.RedisHelper;
import com.appabc.datas.AbstractDatasTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月1日 下午7:48:21
 */

public class redisTest extends AbstractDatasTest{
	
	@Autowired
	RedisHelper redisHelper;
	
	@Test
	public void test(){
		String test = this.redisHelper.getString("rr");
		this.redisHelper.getString("aa");
		this.redisHelper.getString("bb");
		System.out.println(0);
		this.redisHelper.set("cc", "1009");
		System.out.println(1);
		System.out.println(2);
		System.out.println(test);
		
		String aa = this.redisHelper.set("aa", "fasdfsdf");
		this.redisHelper.set("fff", "GGG", 300);
		System.out.println(">>>>>>");
		System.out.println("------------");
		this.redisHelper.set("GGG", "fff", 300);
		
		System.out.println(aa);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	public void mainTest() {
		// TODO Auto-generated method stub
		
	}

}
