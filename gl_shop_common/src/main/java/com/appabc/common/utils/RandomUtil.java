package com.appabc.common.utils;

import java.util.UUID;


/**
 * @Description : some random operator
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午6:34:17
 */

public class RandomUtil {
	
	public static final LogUtil log = LogUtil.getLogUtil(RandomUtil.class);
	
	/**
     * 
     * @description 生成UUID
     * @author  Bill Huang
     * @createDate 2014-03-24 17:22:34
     * @param null
     * @return String 
     * 
     */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		str = str.replaceAll("-", "");
	    return str.toUpperCase(); 
	}
	
	/**
     * 
     * @description 获得指定数量的UUID
     * @author  Bill Huang
     * @createDate 2014-03-24 17:22:34
     * @param number
     * @return String[] 
     * 
     */
    public static String[] getUUID(int number) {   
        if (number < 1) {   
            return null;   
        }   
        String[] sArr = new String[number];   
        for (int i = 0; i < number; i++) {   
        	sArr[i] = getUUID();   
        }   
        return sArr;   
    }
    
    public static int str2int(String str){
    	try{
    		return Integer.parseInt(str);
    	}catch(NumberFormatException e){
    		e.printStackTrace();
    		log.debug(e.getMessage(), e);
    		return 0;
    	}
    }
    
    public static Float str2float(String str){
    	try{
    		return Float.parseFloat(str);
    	}catch(NumberFormatException e){
    		e.printStackTrace();
    		log.debug(e.getMessage(), e);
    		return 0.0f;
    	}
    }
    
    /**  
	 * clean (清除StringBuffer里面的内容)  
	 * @param   
	 * @author Bill Huang 
	 * @return void  
	 * @exception null
	 * @since  1.0.0  
	 */
    public static void clean(StringBuffer sb){
    	if(sb==null||sb.length()<=0){
    		return ;
    	}
    	sb.delete(0, sb.length());
    }
    
	public static void main(String[] args) {
		System.out.println(getUUID());

		String[] ss = getUUID(10);
		for (int i = 0; i < ss.length; i++) {
			System.out.println("ss[" + i + "]=====" + ss[i] + " ==== " + ss[i].length());
		}
	}
}
