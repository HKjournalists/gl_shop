package com.appabc.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


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
	
	public static final String TWO_NUMBER_FORMAT = "0.00";
	
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
    
    public static Double str2double(String str){
    	try{
    		return Double.parseDouble(str);
    	}catch(NumberFormatException e){
    		e.printStackTrace();
    		log.debug(e.getMessage(), e);
    		return 0.0;
    	}
    }
    
    /**  
	 * matchAndReplace (利用java的正则表达式匹配和替换相关信息)  
	 * @param String ; String ; String
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public static String matchAndReplace(String preFlag,String sourceStr,String destStr){
		if(StringUtils.isEmpty(preFlag) ||  StringUtils.isEmpty(sourceStr) || StringUtils.isEmpty(destStr)){
			return sourceStr;
		}
		Pattern p = Pattern.compile(preFlag);
		Matcher m = p.matcher(sourceStr);
		while(m.find()){
			sourceStr = sourceStr.replaceAll(preFlag,destStr);
		}
		return sourceStr;
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
     * @return   BigDecimal 型结果 
     */  
    public static BigDecimal add(double d1,double d2){  
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
         BigDecimal b2=new BigDecimal(Double.toString(d2));  
        return b1.add(b2);  
    }
    
    public static double addRound(double d1,double d2){
    	log.debug(" The AddRound num is : "+d1+"  <======>  "+d2);
        BigDecimal b1=new BigDecimal(Double.toString(d1));  
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        double result = b1.add(b2).doubleValue();
        log.debug(" The AddRound result is : "+result);
        return result;
    }
  
    /** 
     * 精确的加法运算 
     * @param f1    第一个加数 
     * @param f2    第二个加数 
     * @return   BigDecimal 型结果 
     */ 
    public static BigDecimal add(float f1,float f2){
    	BigDecimal b1 = new BigDecimal(Float.toString(f1));
    	BigDecimal b2 = new BigDecimal(Float.toString(f2));
    	return b1.add(b2);
    }
    
    public static float addRound(float f1,float f2){
    	BigDecimal b1 = new BigDecimal(Float.toString(f1));
    	BigDecimal b2 = new BigDecimal(Float.toString(f2));
    	return round(b1.add(b2).floatValue()).floatValue();
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
    
    public static double subRound(double d1,double d2){
       log.debug(" The SubRound num is : "+d1+"  <======>  "+d2);
       BigDecimal b1=new BigDecimal(Double.toString(d1));  
       BigDecimal b2=new BigDecimal(Double.toString(d2));
       double result = b1.subtract(b2).doubleValue(); 
       log.debug(" The SubRound result is : "+result);
       return result;  
   }
  
    /** 
     * 精确的减法运算 
     * @param f1 被减数 
     * @param f2 减数 
     * @return float 型结果 
     */
    public static BigDecimal sub(float f1,float f2){
    	BigDecimal b1 = new BigDecimal(Float.toString(f1));
    	BigDecimal b2 = new BigDecimal(Float.toString(f2));
    	return b1.subtract(b2);
    }
    
    public static float subRound(float f1,float f2){
    	BigDecimal b1 = new BigDecimal(Float.toString(f1));
    	BigDecimal b2 = new BigDecimal(Float.toString(f2));
    	return round(b1.subtract(b2).floatValue()).floatValue();
    	
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
    
    public static double mulRound(double d1,double d2){
		log.debug(" The MulRound num is : "+d1+"  <======>  "+d2);
	    BigDecimal b1=new BigDecimal(Double.toString(d1));  
	    BigDecimal b2=new BigDecimal(Double.toString(d2));
	    double result = b1.multiply(b2).doubleValue();
	    log.debug(" The MulRound result is : "+result);
       return result;  
   }
  
    /** 
     * 精确的乘法运算 
     * @param f1 因数一 
     * @param f2 因数二 
     * @return float 型结果 
     */
    public static BigDecimal mul(float f1,float f2){
    	BigDecimal b1 = new BigDecimal(Float.toString(f1));
    	BigDecimal b2 = new BigDecimal(Float.toString(f2));
    	return b1.multiply(b2);
    }
    
    public static float mulRound(float f1,float f2){
    	BigDecimal b1 = new BigDecimal(Float.toString(f1));
    	BigDecimal b2 = new BigDecimal(Float.toString(f2));
    	return round(b1.multiply(b2).floatValue()).floatValue();
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
     * 精确的除法运算，默认保留两位小数，指定小数精确4位数如：div(65.22,2.13,4);  div(String d1,String d2,int scale) 
     * @param f1 被除数 
     * @param f2 除数 
     * @return  float 型结果 
     */
    public static BigDecimal div(float f1,float f2){
    	return div(Float.toString(f1),Float.toString(f2),DEF_DIV_SCALE);
    }
    
    public static double divRound(double d1,double d2){
		log.debug(" The MulRound num is : "+d1+"  <======>  "+d2);
	    BigDecimal b1=new BigDecimal(Double.toString(d1));  
	    BigDecimal b2=new BigDecimal(Double.toString(d2));
	    double result = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	    log.debug(" The MulRound result is : "+result);
        return result;  
    }
    
    public static float divRound(float f1,float f2){
    	return round(div(f1,f2).floatValue()).floatValue();
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
	 * round(这里用一句话描述这个方法的作用)  
	 * (这里描述这个方法适用条件 – 可选)     
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public static BigDecimal round(float f) {
		return round(Float.toString(f), 2);
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
         return  format(number,TWO_NUMBER_FORMAT);  
    }  
  
    /** 
     * 格式化数字,默认格式为0.00 
     * @param number 
     * @return 
     */  
    public static String format(double number){  
         return  format(number,TWO_NUMBER_FORMAT);  
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
         return  format(number,TWO_NUMBER_FORMAT);  
    }  
  
     public static void main(String[] args) {
 		/*System.out.println(getUUID());

 		String[] ss = getUUID(10);
 		for (int i = 0; i < ss.length; i++) {
 			System.out.println("ss[" + i + "]=====" + ss[i] + " ==== " + ss[i].length());
 		}*/
 		/*double d1 = 11.45678;
 		double d2 = 2.0014;
 		double d3 = addRound(d1, d2);
 		BigDecimal d4 = add(d1,d2);
 		System.out.println(d3);
 		System.out.println(d4);*/
 	}
     
}
