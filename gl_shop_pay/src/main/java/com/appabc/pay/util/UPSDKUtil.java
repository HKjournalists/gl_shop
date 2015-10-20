/**  
 * com.appabc.pay.util.UPSDKUtil.java  
 *   
 * 2015年3月3日 下午2:55:51  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import com.appabc.common.utils.LogUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.ObjectUtil;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 下午2:55:51
 */

public class UPSDKUtil {
	
	static {
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
	}
	
	private static LogUtil log = LogUtil.getLogUtil(UPSDKUtil.class);
	
	public static String encoding = "UTF-8";

	public static String version = "5.0.0";
	
	public static String merId = "898440372990551";
	
	public static SDKConfig sdkConfig = SDKConfig.getConfig();
	
	/**
	 * http://www.916816.com:8080/gl_pay/pay/payfrontRcvServlet.do
	 */
	//后台服务对应的写法参照 FrontRcvResponse.java
	public static String frontUrl = MessagesUtil.getMessage("applicationContext") + "/noAuthUrl/unionPaybackNotifyBusinessUrl";

	/**
	 * http://www.916816.com:8080/gl_pay/pay/payBackRcvServlet.do
	 */
	//后台服务对应的写法参照 BackRcvResponse.java
	public static String backUrl = MessagesUtil.getMessage("applicationContext") + "/noAuthUrl/unionPaybackNotifyBusinessUrl";
	//受理方和发卡方自选填写的域[O]--后台通知地址
	
	public static Map<String, String> setUpTradeQueryParams(String orderId,String txnTime){
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(txnTime)){
			return null;
		}
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", version);
		// 字符集编码 默认"UTF-8"
		data.put("encoding", encoding);
		// //通过MPI插件获取
		// data.put("certId", certId);//M
		// 填写对报文摘要的签名
		// data.put("signature", signature);//M
		// 01RSA02 MD5 (暂不支持)
		data.put("signMethod", "01");// M
		// 交易类型 00
		data.put("txnType", "00");// M
		// 默认00
		data.put("txnSubType", "00");// M
		// 默认:000000
		data.put("bizType", "000000");// M
		// 0：普通商户直连接入2：平台类商户接入
		data.put("accessType", "0");
		data.put("merId", merId);// M
		// 被查询交易的交易时间
		data.put("txnTime", txnTime);// M
		// 被查询交易的订单号
		data.put("orderId", orderId);// M
		// 待查询交易的流水号
		//data.put("queryId", queryId);// C
		// 格式如下：{子域名1=值&子域名2=值&子域名3=值} 子域： origTxnType N2原交易类型余额查询时必送
		//data.put("reserved", "cid=201411270000014&type=1");// O
		
		return data;
	}
	
	public static Map<String, String> setUpTradeRequestParams(String orderId,String moneyPerPenny){
		if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(moneyPerPenny)){
			return null;
		}
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", version);
		// 字符集编码 默认"UTF-8"
		data.put("encoding", encoding);
		// 证书ID 调用 SDK 可自动获取并赋值
		// data.put("certId", "31692114440333550101559775639582427889");
		// 签名 调用 SDK 可自动运算签名并赋值
		// data.put("signature", "");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型 000202 B2B业务
		data.put("bizType", "000202");
		// 渠道类型 07-互联网渠道
		data.put("channelType", "07");
		// 商户/收单后台接收地址 必送
		data.put("backUrl", UPSDKUtil.backUrl);
		// 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码
		data.put("merId", merId);
		// 订单号 商户根据自己规则定义生成，每订单日期内不重复
		data.put("orderId", orderId);
		// 订单发送时间 格式： YYYYMMDDhhmmss 商户发送交易时间，根据自己系统或平台生成
		//String _txnTime = DateUtil.formatDate(com.appabc.common.utils.DateUtil.getNowDate(), "YYYYMMddHHmmss");
		String _txnTime = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date());
		data.put("txnTime", _txnTime);
		// 交易金额 分
		data.put("txnAmt", moneyPerPenny);
		// 交易币种
		data.put("currencyCode", "156");
		// 持卡人ip 根据需求选送 参考接口规范 防钓鱼用
		data.put("customerIp", "121.40.90.241");

		return data;
	}
	
	/**
	 * java main方法 数据提交 　　 对数据进行签名
	 * 
	 * @param contentData
	 * @return　签名后的map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> signData(Map<String, ?> contentData) {
		if(ObjectUtil.isEmpty(contentData)){
			return null;
		}
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		Set<?> set = contentData.entrySet();
		Iterator<?> it = set.iterator();
		while(it.hasNext()){
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
				log.info(obj.getKey() + "-->" + String.valueOf(value));
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(submitFromData, encoding);

		return submitFromData;
	}
	
	/**
	 * java main方法 数据提交 提交到后台
	 * 
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitUrl(Map<String, String> submitFromData,String requestUrl) {
		if(StringUtils.isEmpty(requestUrl) || ObjectUtil.isEmpty(submitFromData)){
			log.info("the requestUrl or submitFromData parameters is null.");
			return null;
		}
		String resultString = StringUtils.EMPTY;
		log.info("requestUrl=[" + requestUrl + "]");
		log.info("submitFromData=[" + submitFromData.toString() + "]");
		
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, encoding);
			if (HttpStatus.OK.value() == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (StringUtils.isNotEmpty(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, encoding)) {
				log.info("validate the sign success.");
			} else {
				log.info("validate the sign failure.");
			}
			// 打印返回报文
			log.info("print the return report content=[" + resultString + "]");
		}
		return resData;
	}
	
	/**
	 * java main方法 数据提交　 数据组装进行提交 包含签名
	 * 
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitData(Map<String, ?> contentData,String requestUrl) {

		Map<String, String> submitFromData = (Map<String, String>) signData(contentData);

		return submitUrl(submitFromData,requestUrl);
	}
	
	
	public static Map<String, String> encodeMapParamsFromISO88591(Map<String, String> reqParam){
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes("ISO-8859-1"), encoding);
				} catch (UnsupportedEncodingException e1) {
					log.error(e);
				}
				valideData.put(key, value);
			}
		}
		return valideData;
	}
	
	public static Map<String, String> getAllRequestParams(final HttpServletRequest request){
		if( request == null){
			return null;
		}
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				// System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if(StringUtils.isEmpty(res.get(en))){
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	public static enum responseStatus {
		//00	成功
		ZEROZERO("00","成功"),
		
		//01	交易失败。详情请咨询95516
		ZEROONE("01","交易失败"),
		
		//02	系统未开放或暂时关闭，请稍后再试
		ZEROTWO("02","系统未开放或暂时关闭，请稍后再试"),
		
		//03	交易通讯超时，请发起查询交易
		ZEROTHREE("03","交易通讯超时，请发起查询交易"),
		
		//04	交易状态未明，请查询对账结果
		ZEROFOUR("04","交易状态未明，请查询对账结果"),
		
		//05	交易已受理，请稍后查询交易结果
		ZEROFIVE("05","交易已受理，请稍后查询交易结果"),
		
		//10	报文格式错误
		ONEZERO("10","报文格式错误"),
		
		//11	验证签名失败
		ONEONE("11","验证签名失败"),
		
		//12	重复交易
		ONETWO("12","重复交易"),
		
		//13	报文交易要素缺失
		ONETHREE("13","报文交易要素缺失"),
		
		//14	批量文件格式错误
		ONEFOUR("14","批量文件格式错误"),
		
		//30	交易未通过，请尝试使用其他银联卡支付或联系95516
		THREEZERO("30","交易未通过，请尝试使用其他银联卡支付或联系95516"),
		
		//31	商户状态不正确
		THREEONE("31","商户状态不正确"),
		
		//32	无此交易权限
		THREETWO("32","无此交易权限"),
		
		//33	交易金额超限
		THREETHREE("33","交易金额超限"),
		
		//34	查无此交易
		THREEFOUR("34","查无此交易"),
		
		//35	原交易不存在或状态不正确
		THREEFIVE("35","原交易不存在或状态不正确"),
		
		//36	与原交易信息不符
		THREESIX("36","与原交易信息不符"),
		
		//37	已超过最大查询次数或操作过于频繁
		THREESEVEN("37","已超过最大查询次数或操作过于频繁"),
		
		//38	银联风险受限
		THREEEIGHT("38","银联风险受限"),
		
		//39	交易不在受理时间范围内
		THREENINE("39","交易不在受理时间范围内"),
		
		//40	绑定关系检查失败
		FOURZERO("40","绑定关系检查失败"),
		
		//41	批量状态不正确，无法下载
		FOURONE("41","批量状态不正确，无法下载"),
		
		//42	扣款成功但交易超过规定支付时间
		FOURTWO("42","扣款成功但交易超过规定支付时间"),
		
		//60	交易失败，详情请咨询您的发卡行
		SIXZERO("60","交易失败，详情请咨询您的发卡行"),
		
		//61	输入的卡号无效，请确认后输入
		SIXONE("61","输入的卡号无效，请确认后输入"),
		
		//62	交易失败，发卡银行不支持该商户，请更换其他银行卡
		SIXTWO("62","交易失败，发卡银行不支持该商户，请更换其他银行卡"),
		
		//63	卡状态不正确
		SIXTHREE("63","卡状态不正确"),
		
		//64	卡上的余额不足
		SIXFOUR("64","卡上的余额不足"),
		
		//65	输入的密码、有效期或CVN2有误，交易失败
		SIXFIVE("65","输入的密码、有效期或CVN2有误，交易失败"),
		
		//66	持卡人身份信息或手机号输入不正确，验证失败
		SIXSIX("66","持卡人身份信息或手机号输入不正确，验证失败"),
		
		//67	密码输入次数超限
		SIXSEVEN("67","密码输入次数超限"),
		
		//68	您的银行卡暂不支持该业务，请向您的银行或95516咨询
		SIXEIGHT("68","您的银行卡暂不支持该业务，请向您的银行或95516咨询"),
		
		//69	您的输入超时，交易失败
		SIXNINE("69","您的输入超时，交易失败"),
		
		//70	交易已跳转，等待持卡人输入
		SEVENZERO("70","交易已跳转，等待持卡人输入"),
		
		//71	动态口令或短信验证码校验失败
		SEVENONE("71","动态口令或短信验证码校验失败"),
		
		//72	您尚未在{}银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通或拨打{}
		SEVENTWO("72","您尚未在{}银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通或拨打{}"),
		
		//73	支付卡已超过有效期
		SEVENTHREE("73","支付卡已超过有效期"),
		
		//74	扣款成功，销账未知
		SEVENFOUR("74","扣款成功，销账未知"),
		
		//75	扣款成功，销账失败
		SEVENFIVE("75","扣款成功，销账失败"),
		
		//76	需要验密开通
		SEVENSIX("76","需要验密开通"),
		
		//77	银行卡未开通认证支付
		SEVENSEVEN("77","银行卡未开通认证支付"),
		
		//78	发卡行交易权限受限，详情请咨询您的发卡行
		SEVENEIGHT("78","发卡行交易权限受限，详情请咨询您的发卡行"),
		
		//98	文件不存在
		NINEEIGHT("98","文件不存在"),
		
		//99	通用错误
		NINENINE("99","通用错误"),
		
		//A6	有缺陷的成功
		ASIX("A6","有缺陷的成功");
		
		private String val;
		
		private String text;
		
		private responseStatus(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal(){return val;}
		
		public String getText(){return text;}
		
		public static responseStatus enumOf(String value){
			for (responseStatus os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			responseStatus ct = enumOf(value);
			if(ct != null){
				return ct.text;
			}
			return null;
	    }
		
	}
}
