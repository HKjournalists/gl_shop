/**
 *
 */
package com.appabc.tools.sms;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sun.misc.BASE64Encoder;

import com.appabc.bean.bo.MsgSendResultBean;
import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.common.utils.DateUtil;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.utils.EncryptUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Description : 云之讯短信发送
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 杨跃红
 * @version : 1.0 Create Date : 2015年3月13日 下午2:29:16
 */
@SuppressWarnings("deprecation")
@Repository
public class SmsSenderYZX extends BaseSmsSender {

	private Logger logger = Logger.getLogger(this.getClass());

//	private final String VERSION = "2014-06-30"; // 接口版本，配置在URL中了
	
	@Override
	public MsgSendResultBean sendMsg(TShortMessageConfig smc, ShortMsgInfo smi) {

		MsgSendResultBean smrb = new MsgSendResultBean();
		if(smc == null){
			smrb.setResCode("1");
			smrb.setResMessage("云之迅帐号信息未配置");
			
			return smrb;
		}
		
		String accountSid = smc.getSuser();
		String authToken = smc.getTokenurl();
		String appId = smc.getSpwd();

		// 补充模板需要的参数
		smi.getTemplate().addTemplateParam("to", smi.getTel());
		smi.getTemplate().addTemplateParam("appId", appId);
		
		String body = smi.getTemplate().toJsonStr();
		body = "{\"templateSMS\":" + body + "}";
		logger.info(body);
		
		smrb.setBusinessId(smi.getBusinessId());
		smrb.setBusinessType(smi.getBusinessType());
		smrb.setContent(body);
		smrb.setTel(smi.getTel());
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			String timeStamp = DateUtil.DateToStr(Calendar.getInstance().getTime(),
					DateUtil.FORMAT_YYYYMMDDHHMMSS);
			// 获取授权码
			String signature = getSignature(authToken, accountSid, timeStamp);
			// URL拼装
			String url = new StringBuffer(smc.getSurl())
//					.append("/").append(VERSION)
					.append("/Accounts/")
					.append(accountSid).append("/Messages/templateSMS")
					.append("?sig=").append(signature).toString();
			
			// POST请求
			HttpResponse response = post("application/json", accountSid,
					authToken, timeStamp, url, httpclient, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				logger.info("result=" + result);
				// 返回结果解析处理
				checkResult(smrb, result);
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		
		return smrb;
	}

	/**
	 * POST请求发送
	 * @param cType
	 * @param accountSid
	 * @param authToken
	 * @param url
	 * @param httpclient
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public HttpResponse post(String cType, String accountSid, String authToken, String timeStamp,
			String url, DefaultHttpClient httpclient, String body)
			throws Exception {
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");

		String auth = getAuthorization(accountSid, timeStamp);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	/**
	 * 包头验证信息
	 * 
	 * @param smc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getAuthorization(String accountSid, String timeStamp)
			throws UnsupportedEncodingException {
		BASE64Encoder encoder = new BASE64Encoder();
		String src = accountSid + ":" + timeStamp;
		String sign = encoder.encode(src.getBytes("UTF-8"));

		return sign;

	}

	/**
	 * 获取授权码
	 * @param smc
	 * @return
	 */
	private String getSignature(String authToken, String accountSid, String timeStemp) {
		EncryptUtil eu = new EncryptUtil();
		String sign = null;
		try {
			sign = eu.md5Digest(accountSid + authToken + timeStemp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sign;

	}
	
	/**
	 * 短信发送结果检查
	 * @param smrb 发送返回信息实体
	 * @param res 短信接口返回结果json
	 * @return
	 */
	private MsgSendResultBean checkResult(MsgSendResultBean smrb, String res){
		
		if(smrb != null && res != null){
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement =  jsonParser.parse(res);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			if(jsonObject != null && jsonObject.get("resp")!=null){
				JsonObject jo = jsonObject.get("resp").getAsJsonObject();
				String resCode = null;
				if(jo.get("respCode") != null){
					resCode = jo.get("respCode").getAsString();
					if("000000".equals(resCode)) resCode = "0";
				}
				
				smrb.setRemark(res);
				smrb.setResCode(resCode);
				smrb.setResMessage(null);
				
			}else{
				smrb.setResCode("10");
				smrb.setRemark(res);
				smrb.setResMessage("返回json解析错误");
			}
			
		}
		return smrb;
		
	}
	
	public static void main(String[] args) {
		
//		String res = "{\"resp\":{\"respCode\":\"000000\",\"templateSMS\":{\"createDate\":\"20150313100126\",\"smsId\":\"592a93eb8a04f0422959c850a0711633\"}}}";
//		JsonParser jsonParser = new JsonParser();
//		JsonElement jsonElement =  jsonParser.parse(res);
//		JsonObject jsonObject = jsonElement.getAsJsonObject();
//		if(jsonObject != null && jsonObject.get("resp")!=null){
//			JsonObject jo = jsonObject.get("resp").getAsJsonObject();
//			String resCode = null;
//			if(jo.get("respCode") != null){
//				resCode = jo.get("respCode").getAsString();
//				if("000000".equals(resCode)) resCode = "0";
//			}
//			System.out.println(resCode);
//		}
//		System.out.println(jsonObject.get("resp"));
//		
		
//		String timeStemp = DateUtil.DateToStr(Calendar.getInstance().getTime(),
//				DateUtil.FORMAT_YYYYMMDDHHMMSS);
//		
//		System.out.println(timeStemp);
		
	}

	
	
	
}
