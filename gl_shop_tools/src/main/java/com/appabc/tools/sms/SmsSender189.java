/**
 *
 */
package com.appabc.tools.sms;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.appabc.bean.bo.MsgSendResultBean;
import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.common.utils.HttpXmlClient;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.enums.SmsInfo.SmsTypeEnum;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Description : 电信189短信发送
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月9日 上午10:06:04
 */
@Repository
public class SmsSender189 extends BaseSmsSender {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private final String EFF_TIME = "5分钟";
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.tool.sms.IMsgSender#sendMsg(com.appabc.bean.bo.ShortMesInfo)
	 */
	public MsgSendResultBean sendMsg(TShortMessageConfig smc, ShortMsgInfo smi) {
		if(smi.getType().equals(SmsTypeEnum.SMS_TEMP_TYPE_PIN.getVal())){
			return sendPin(smc, smi);
		}else if(smi.getType().equals(SmsTypeEnum.SMS_TEMP_TYPE_INFORM.getVal())){
			return sendInform(smc, smi);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 验证码发送
	 * @param smi 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public MsgSendResultBean sendPin(TShortMessageConfig smc, ShortMsgInfo smi){
		
		MsgSendResultBean smrb = new MsgSendResultBean();
		smrb.setBusinessId(smi.getBusinessId());
		smrb.setBusinessType(smi.getBusinessType());
		smrb.setContent(smi.getContent());
		smrb.setTel(smi.getTel());
		
		if(smc != null){
//			String templateParam2 = "{\"param1\":\""+smi.getUser()+"\",\"param2\":\""+smi.getContent()+"\",\"param3\":\""+EFF_TIME+"\"}";
			String templateParam = smc.getTemplateparam().replace("$param1", smi.getUser()).replace("$param2", smi.getContent()).replace("$param3", EFF_TIME);
			String accessToken = getToken(smc);
			
			if(accessToken == null){
				accessToken = getToken(smc);
				if(accessToken == null){
					smrb.setResCode("1");
					smrb.setResMessage("189Token获取失败");
					return smrb;
				}
			}
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("acceptor_tel", smi.getTel());
			params.put("template_id", smc.getTemplateid());
			params.put("template_param", templateParam);
			params.put("app_id", smc.getSuser());
			params.put("access_token", accessToken);
			params.put("timestamp", Calendar.getInstance().getTime().toLocaleString());
			
			String res = HttpXmlClient.post(smc.getSurl(), params); 
			checkResult(smrb, res);
			
		}else{
			smrb.setResCode("1");
			smrb.setResMessage("189帐号或验证码模板信息未配置");
		}
		
		return smrb;
		
	}
	
	/**
	 * 通知发送
	 * @param smi
	 * @return
	 */
	public MsgSendResultBean sendInform(TShortMessageConfig smc, ShortMsgInfo smi){
		
		return null;
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
			
			if(jsonObject != null && jsonObject.get("res_code")!=null){
				String resCode = jsonObject.get("res_code").getAsString();
				String resMessage = jsonObject.get("res_message").getAsString();
				
				smrb.setRemark(res);
				smrb.setResCode(resCode);
				smrb.setResMessage(resMessage);
				
			}else{
				smrb.setResCode("10");
				smrb.setRemark(res);
				smrb.setResMessage("返回json解析错误");
			}
			
		}
		return smrb;
		
	}

	/**
	 * 获取 189 TOKEN
	 * @param t
	 * @return
	 */
	protected String getToken(TShortMessageConfig t) {
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("grant_type", "client_credentials");
		params.put("app_id", t.getSuser());
		params.put("app_secret", t.getSpwd());
		
		String res = HttpXmlClient.post(t.getTokenurl(), params);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement =  jsonParser.parse(res);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		if(jsonObject != null && jsonObject.get("res_code")!=null && jsonObject.get("res_message").getAsString().equals("Success")){
			String token = jsonObject.get("access_token").getAsString();
			return token;
		}else{
			logger.error("============");
			logger.error("189Token获取失败："+res);
			logger.error("============");
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		SmsSender189 m = new SmsSender189();
		
		TShortMessageConfig t = new TShortMessageConfig();
		t.setSuser("350851340000036712");
		t.setSpwd("bc9e37087f48b71842669348fa4d59b5");
		t.setTokenurl("https://oauth.api.189.cn/emp/oauth2/v3/access_token");
		
		m.getToken(t);
		
	}

}
