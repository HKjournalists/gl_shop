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
 * http://snowolf.iteye.com/blog/379860 
 * @description BaseCoder.java base coder component (这里用一句话描述这个类的作用)  
 * @author Bill Huang  
 * @date 2014年8月22日 上午11:51:35
 * @version 1.0.0  
 *  加密解密，曾经是我一个毕业设计的重要组件。在工作了多年以后回想当时那个加密、解密算法，实在是太单纯了。 
 *  言归正传，这里我们主要描述Java已经实现的一些加密解密算法，最后介绍数字证书。 
 *	    如基本的单向加密算法： 
 *	BASE64 严格地说，属于编码格式，而非加密算法
 *	MD5(Message Digest algorithm 5，信息摘要算法)
 *	SHA(Secure Hash Algorithm，安全散列算法)
 *	HMAC(Hash Message Authentication Code，散列消息鉴别码)
 *	
 *	    复杂的对称加密（DES、PBE）、非对称加密算法： 
 *	DES(Data Encryption Standard，数据加密算法)
 *	PBE(Password-based encryption，基于密码验证)
 *	RSA(算法的名字以发明者的名字命名：Ron Rivest, AdiShamir 和Leonard Adleman)
 *	DH(Diffie-Hellman算法，密钥一致协议)
 *	DSA(Digital Signature Algorithm，数字签名)
 *	ECC(Elliptic Curves Cryptography，椭圆曲线密码编码学)
 *  本篇内容简要介绍BASE64、MD5、SHA、HMAC几种方法。 
 *  MD5、SHA、HMAC这三种加密算法，可谓是非可逆加密，就是不可解密的加密方法。我们通常只把他们作为加密的基础。单纯的以上三种的加密并不可靠。   
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
