/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.glshop.net.logic.pay.framework.impl.alipay.utils;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088211309944584";

	//收款支付宝账号
	//public static final String DEFAULT_SELLER = "2088211309944584";
	public static final String DEFAULT_SELLER = "admin@goldenroach.net";

	//商户私钥(PKCS8)
	public static final String PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALuTvcXWuA10NaThNNC4PEKI2k/Vd/XT+AD5wdJRtW39JmIBMoNoEukgmJjIpcHJ74M3JERTlivsTQvxUUvivXu52TD4vgaiLdadij02tFV8IuoT9TUkT7SSn8U7Dli95Frh1eZqvUypwxqS1vrieq3Gb+dMdAyyvoBuzJzQqz9VAgMBAAECgYEAuaAIIftN4oqI96SiFpbrk1BI8s4mS5C7kYOX6iFP8Que60eSDHZ2mFvYhwGw/yhuLKTp7+/j4Y2T5Wim8fjtVwujXnaheyZe7l0rly9Isq+5kqO+NPGYlJeqzzEnhob3fU/ME8QLfOCKKipch2X8UN38L69aPfAxSNYQ9b5VTnUCQQDqu5jqtOsotDnHt8ooR+FS7RFMtc4OiiI0mIRI/tUqqCRSsfZjKZ5z2ksnO2sZ0r05cLm3M/sSVDRPZtZJKEYrAkEAzJJptcBQDHqVr7QX8zEhYZSKUDPgvZd5jcXcIOhq5BRA+5UFSP90/asotp1l6L+YoM7TSYKpe/IPPhikiMlQfwJAPO59f09/S42dAg7fVx1xjAkl2HfJCVUqE+UTUa90viHva9fuUUdYblJX99Mgtg9SNzrfuoKvxc491e6n6U2fCQJBALDLP+xBHW0La92DKMeViCHKu5YAn5N6GyS5ADq4Ydhm4JoL+ZtSmoq3puHbOT1ih8dBtzvqXgqt8RpbNaa5RIUCQQDKyaql6jKoeJ+QWG1/hhB+JskHU9SwkFSkDZ2OCZw1ERxOJkutLdwYdAJ25Ilb36wtJFZp/d2NkStM1m0wkEph";

	//支付宝公钥
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
