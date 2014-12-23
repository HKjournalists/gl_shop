package com.appabc.common.utils.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


/**  
 *   
 * BaseCoder.java  
 * @description BaseCoder.java base coder component (这里用一句话描述这个类的作用)  
 * @author Bill Huang  
 * @date 2014年8月22日 上午11:51:35
 * @version 1.0.0  
 */
public abstract class BaseCoder {

	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	public static final String KEY_SHA = "SHA";
	
	public static final String KEY_MD5 = "MD5";
	
	/**
	 * MAC算法可选以下多种算法 
	 * <pre>
	 * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512
	 * </pre>
	 * 
	 * */
	public static final String KEY_MAC = "HmacMD5";
	
	/** 
     * Hex解密 
     *  
     * @param key 
     * @return byte[]
     * @throws Exception
     * @since  1.0.0 
     */
	public static byte[] decryptHex(String key) throws Exception {
		return Hex.decodeHex(key.toCharArray());
	}
	
	/** 
     * Hex加密  
     *  
     * @param key 
     * @return byte[]
     * @throws Exception
     * @since  1.0.0 
     */
	public static String encryptHex(byte[] key){
		return new String(Hex.encodeHex(key));
	}
	
    /** 
     * MD5加密 
     *  
     * @param data 
     * @return byte[]
     * @throws Exception 
     */  
    public static byte[] encryptMD5(byte[] data) throws Exception{  
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);  
        return md5.digest();
    }
    
    /**
     * MD5 加密32
     * 
     * @param input
     * @return String
     * @throws Exception
     * 
     * */
    public static String encryptMD5(String input) throws Exception {
    	return encryptHex(encryptMD5(input.getBytes(UTF8_CHARSET)));
    }
    
    /** 
     * SHA加密 
     *  
     * @param data 
     * @return byte[]
     * @throws Exception 
     */  
    public static byte[] encryptSHA(byte[] data) throws Exception {  
  
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data);  
  
        return sha.digest();  
  
    }
    
    /**
     * SHA加密 
     * 
     * @param input
     * @return String
     * @throws Exception
     * 
     * */
    public static String encryptSHA(String input) throws Exception {
    	return encryptHex(encryptSHA(input.getBytes(UTF8_CHARSET)));
    }
  
    /** 
     * 初始化HMAC密钥 
     *  
     * @return String
     * @throws Exception 
     */  
    public static String initMacKey() throws Exception {  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
  
        SecretKey secretKey = keyGenerator.generateKey();  
        return encryptHex(secretKey.getEncoded());  
    }  
  
    /** 
     * HMAC加密 
     *  
     * @param data 
     * @param key 
     * @return byte[]
     * @throws Exception 
     */  
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {  
  
        SecretKey secretKey = new SecretKeySpec(decryptHex(key), KEY_MAC);  
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);  
  
        return mac.doFinal(data);  
  
    }
    
    /** 
     * HMAC加密 
     *  
     * @param input 
     * @param key 
     * @return String
     * @throws Exception 
     */
    public static String encryptHMAC(String input,String key) throws Exception {
    	return encryptHex(encryptHMAC(input.getBytes(UTF8_CHARSET),key));
    }
    
    public static void main(String[] args) throws Exception {
    	String input = "123456";
    	String key = "000000";
    	String md5output = encryptMD5(input);
		System.out.println(md5output);
		String shaoutput = encryptSHA(input);
		System.out.println(shaoutput);
		String hmacoutput = encryptHMAC(input,key);
		System.out.println(hmacoutput);
	}
}
