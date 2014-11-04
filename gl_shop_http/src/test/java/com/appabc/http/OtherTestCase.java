package com.appabc.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月16日 下午4:47:32
 */

public class OtherTestCase extends TestCase{
	
	public void testExec(){
		String disNum = "300.0";
		System.out.println(NumberUtils.isNumber(disNum));
		
		String testStr = "12315<Test>show me</text>";  
		Pattern p = Pattern.compile("<Text>(.*)</Text>");  
		Matcher m = p.matcher(testStr);
		System.out.println(m.find()); 
		while(m.find()){  
			System.out.println(m.group(1)); 
		}
	}
	
}
