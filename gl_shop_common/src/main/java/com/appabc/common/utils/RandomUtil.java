package com.appabc.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
	
	/** 
     * 默认的除法精度为两位 
     */  
    public static final int DEF_DIV_SCALE=2;  
	
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
    
    /** 
     * 精确的加法运算 
     * @param d1    第一个加数 
     * @param d2    第二个加数 
     * @return   double 型结果 
     */  
    public static BigDecimal add(double d1,double d2){  
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
         BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.add(b2);  
    }  
  
     /** 
     * 精确的加法运算 
     * @param d1    第一个加数 
     * @param d2    第二个加数 
     * @return   double 型结果 
     */  
    public static BigDecimal add(int d1,int d2){  
        BigDecimal b1=new BigDecimal(Integer.toString(d1));  
         BigDecimal b2=new BigDecimal(Integer.toString(d2));  
        return b1.add(b2);  
    }  
  
     /** 
     * 精确的加法运算 
     * @param d1    第一个加数 
     * @param d2    第二个加数 
     * @return   double 型结果 
     */  
     public static BigDecimal add(String d1,String d2){  
        BigDecimal b1=new BigDecimal(d1);  
         BigDecimal b2=new BigDecimal(d2);  
        return b1.add(b2);  
    }  
  
  
    /** 
     * 精确的减法运算 
     * @param d1 被减数 
     * @param d2 减数 
     * @return double 型结果 
     */  
    public static BigDecimal sub(double d1,double d2){  
         BigDecimal b1=new BigDecimal(Double.toString(d1));  
         BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.subtract(b2);  
    }  
  
    /** 
     * 精确的减法运算 
     * @param d1 被减数 
     * @param d2 减数 
     * @return double 型结果 
     */  
    public static BigDecimal sub(int d1,int d2){  
         BigDecimal b1=new BigDecimal(Integer.toString(d1));  
         BigDecimal b2=new BigDecimal(Integer.toString(d2));  
        return b1.subtract(b2);  
    }  
  
     /** 
     * 精确的减法运算 
     * @param d1 被减数 
     * @param d2 减数 
     * @return double 型结果 
     */  
    public static BigDecimal sub(String d1,String d2){  
         BigDecimal b1=new BigDecimal(d1);  
         BigDecimal b2=new BigDecimal(d2);  
        return b1.subtract(b2);  
    }  
  
  
  
  
    /** 
     * 精确的乘法运算 
     * @param d1 因数一 
     * @param d2 因数二 
     * @return double 型结果 
     */  
    public static BigDecimal mul(double d1,double d2){  
         BigDecimal b1=new BigDecimal(Double.toString(d1));  
         BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.multiply(b2);  
    }  
  
     /** 
     * 精确的乘法运算 
     * @param d1 因数一 
     * @param d2 因数二 
     * @return double 型结果 
     */  
    public static BigDecimal mul(int d1,int d2){  
         BigDecimal b1=new BigDecimal(Integer.toString(d1));  
         BigDecimal b2=new BigDecimal(Integer.toString(d2));  
        return b1.multiply(b2);  
    }  
  
    /** 
        * 精确的乘法运算 
        * @param d1 因数一 
        * @param d2 因数二 
        * @return double 型结果 
        */  
       public static BigDecimal mul(String d1,String d2){  
            BigDecimal b1=new BigDecimal(d1);  
            BigDecimal b2=new BigDecimal(d2);  
           return b1.multiply(b2);  
       }  
  
  
    /** 
     * 精确的除法运算，默认保留两位小数，指定小数精确4位数如：div(65.22,2.13,4);  div(double d1,double d2,int scale) 
     * @param d1 被除数 
     * @param d2 除数 
     * @return  double 型结果 
     */  
    public static BigDecimal div(double d1,double d2){  
        return div( d1, d2,DEF_DIV_SCALE);  
    }  
  
     /** 
     * 精确的除法运算，默认保留两位小数，指定小数精确4位数如：div(65.22,2.13,4);  div(double d1,double d2,int scale) 
     * @param d1 被除数 
     * @param d2 除数 
     * @return  double 型结果 
     */  
    public static BigDecimal div(int d1,int d2){  
        return div( d1, d2,DEF_DIV_SCALE);  
    }  
  
     /** 
     * 精确的除法运算，默认保留两位小数，指定小数精确4位数如：div(65.22,2.13,4);  div(double d1,double d2,int scale) 
     * @param d1 被除数 
     * @param d2 除数 
     * @return  double 型结果 
     */  
    public static BigDecimal div(String d1,String d2){  
        return div( d1, d2,DEF_DIV_SCALE);  
    }  
  
    /** 
     * 精确的除法运算 
     * @param d1 被除数 
     * @param d2 除数 
     * @param scale  小数点精确的位数 
     * @return  double 型结果 
 
     */  
     public static BigDecimal div(double d1,double d2,int scale){  
         if(scale<0){  
             throw new IllegalArgumentException("The scale must be positive integer or zero");  
         }  
         BigDecimal b1=new BigDecimal(Double.toString(d1));  
         BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);  
    }  
  
      /** 
     * 精确的除法运算 
     * @param d1 被除数 
     * @param d2 除数 
     * @param scale  小数点精确的位数 
     * @return  double 型结果 
 
     */  
     public static BigDecimal div(int d1,int d2,int scale){  
         if(scale<0){  
             throw new IllegalArgumentException("The scale must be positive integer or zero");  
         }  
         BigDecimal b1=new BigDecimal(Integer.toString(d1));  
         BigDecimal b2=new BigDecimal(Integer.toString(d2));  
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);  
    }  
  
     /** 
     * 精确的除法运算 
     * @param d1 被除数 
     * @param d2 除数 
     * @param scale  小数点精确的位数 
     * @return  double 型结果 
 
     */  
     public static BigDecimal div(String d1,String d2,int scale){  
         if(scale<0){  
             throw new IllegalArgumentException("The scale must be positive integer or zero");  
         }  
         BigDecimal b1=new BigDecimal(d1);  
         BigDecimal b2=new BigDecimal(d2);  
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);  
    }  
  
    /** 
     * 精确的四舍五入 
     * @param d 需要精确的数据 
     * @param scale  精确的精度 
     * @return  double 型结果 
     */  
    public static BigDecimal round(double d,int scale){  
         BigDecimal b1=new BigDecimal(Double.toString(d));  
         BigDecimal one=new BigDecimal("1");  
        return b1.divide(one,scale,BigDecimal.ROUND_HALF_UP);  
    }  
  
     /** 
     * 精确的四舍五入 
     * @param d 需要精确的数据 
     * @param scale  精确的精度 
     * @return  double 型结果 
     */  
    public static BigDecimal round(int d,int scale){  
         BigDecimal b1=new BigDecimal(Integer.toString(d));  
         BigDecimal one=new BigDecimal("1");  
        return b1.divide(one,scale,BigDecimal.ROUND_HALF_UP);  
    }  
  
      /** 
     * 精确的四舍五入 
     * @param d 需要精确的数据 
     * @param scale  精确的精度 
     * @return  double 型结果 
     */  
    public static BigDecimal round(String d,int scale){  
         BigDecimal b1=new BigDecimal(d);  
         BigDecimal one=new BigDecimal("1");  
        return b1.divide(one,scale,BigDecimal.ROUND_HALF_UP);  
    }  
  
    /** 
     * 精确的四舍五入，默认为了位小数 
     * @param d 需要精确的数据 
     * @return double 型结果 
     */  
     public static BigDecimal round(double d){  
        return round(d,2);  
    }  
  
    /** 
       * 精确的四舍五入，默认为了位小数 
       * @param d 需要精确的数据 
       * @return double 型结果 
       */  
       public static BigDecimal round(int d){  
          return round(d,2);  
      }  
  
  
    /** 
     * 精确的四舍五入，默认为了位小数 
     * @param d 需要精确的数据 
     * @return double 型结果 
     */  
     public static BigDecimal round(String d){  
        return round(d,2);  
    }  
  
    /** 
     * 格式化数字 
     * @param number  需要格式化的数字 
     * @param formatStr  字符串格式 如：0.00 
     * @return 
     */  
    public static String format(long number,String formatStr){  
         DecimalFormat df = new DecimalFormat(formatStr);  
         return df.format(number);  
    }  
  
    /** 
     * 格式化数字,默认格式为0.00 
     * @param number 
     * @return 
     */  
     public static String format(long number){  
         return  format(number,"0.00");  
    }  
  
    /** 
     * 格式化数字,默认格式为0.00 
     * @param number 
     * @return 
     */  
    public static String format(double number){  
         return  format(number,"0.00");  
    }  
  
      /** 
     * 格式化数字 
     * @param number  需要格式化的数字 
     * @param formatStr  字符串格式 如：0.00 
     * @return 
     */  
     public static String format(double number,String formatStr){  
         DecimalFormat df = new DecimalFormat(formatStr);  
         return df.format(number);  
    }  
  
     /** 
     * 格式化数字 
     * @param number  需要格式化的数字 
     * @param formatStr  字符串格式 如：0.00 
     * @return 
     */  
     public static String format(Object number,String formatStr){  
  
         DecimalFormat df = new DecimalFormat(formatStr);  
         return df.format(Double.valueOf(String.valueOf(number)));  
    }  
  
      /** 
     * 格式化数字,默认格式为0.00 
     * @param number 
     * @return 
     */  
     public static String format(Object number){  
         return  format(Double.valueOf(String.valueOf(number)),"0.00");  
    }  
  
     public static void main(String[] args) {
 		System.out.println(getUUID());

 		String[] ss = getUUID(10);
 		for (int i = 0; i < ss.length; i++) {
 			System.out.println("ss[" + i + "]=====" + ss[i] + " ==== " + ss[i].length());
 		}
// 		double d1 = 10.010d;
// 		double d2 = 5.001d;
 		float s2=50.5555f;
 		String s3 = String.valueOf(s2);
// 		float s1=99.0009f;
// 		String s4 = String.valueOf(s1);
// 		BigDecimal d3 = sub(d1, d2);
// 		BigDecimal s5 = sub(s4,s3);
 		System.out.println(s3);
// 		System.out.println((d3));
// 		System.out.println(format(d3,"0.00000"));
 	}
     
}
