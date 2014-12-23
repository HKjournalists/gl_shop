/**
 *
 */
package com.appabc.tools.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.utils.DateUtil;
import com.google.gson.Gson;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 杨跃红
 * @version : 1.0 Create Date : 2014年11月26日 下午5:26:05
 */
public class SMSTemplate {
	
	private static Logger logger = Logger.getLogger(SMSTemplate.class);

	/**
	 * 模板ID
	 */
	private String templateId;
	
	/**
	 * 模板参数
	 */
	private Map<String,Object> templateParams = new HashMap<String, Object>();

	public SMSTemplate(String templateId) {
		this.templateId = templateId;
	}
	
	public String getTemplateId() {
		return templateId;
	}
	
	private void addTemplateParam(String key, Object value) {
		this.templateParams.put(key, value);
	}
	
	/**
	 * 获取验证码短信模板
	 * @param code 验证码；例：1234
	 * @param timestr 有效时长,单位分钟；例:5
	 * @param csphone 客服电话
	 * @return
	 */
	public static SMSTemplate getTemplatePin(String code,String min, String csphone) {
		SMSTemplate st = new SMSTemplate("91001914");
		if(StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(min)){
			st.addTemplateParam("code", code);
			st.addTemplateParam("min", min);
			st.addTemplateParam("csphone", csphone);
			return st;
		}else{
			logger.error("参数不能为空；code="+code+"\tmin="+min);
			return null;
		}
	}

	/**
	 * 获取金额变动短信模板
	 * @param purseType 账户枚举；例：保证金
	 * @param time 时间
	 * @param tradeType 交易类型枚举; 例：扣除
	 * @param money 金额；例：50000
	 * @return
	 */
	public static SMSTemplate getTemplateWallet(PurseType purseType, Date time,TradeType tradeType, float money) {
		if(purseType != null && tradeType != null && time != null && money>0){
			SMSTemplate st = new SMSTemplate("91003115");
			st.addTemplateParam("accountName", purseType.getText());
			st.addTemplateParam("time", DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
			st.addTemplateParam("transactionType", tradeType.getText());
			st.addTemplateParam("money", money);
			return st;
		}else{
			logger.error("参数不能为空；purseType=" + purseType + "\ttime=" + time+ "\ttradeType=" + tradeType+ "\tmoney=" + money);
			return null;
		}
	}
	
	/**
	 * 获取金额变动短信模板
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 * @return
	 */
	public static SMSTemplate getTemplateWallet(String accountName, Date time,String transactionType, float money) {
		if(StringUtils.isNotEmpty(accountName) && StringUtils.isNotEmpty(transactionType) && time != null && money>0){
			/*SMSTemplate st = new SMSTemplate("91003115");
			st.addTemplateParam("accountName", accountName);
			st.addTemplateParam("time", DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
			st.addTemplateParam("transactionType", transactionType);
			st.addTemplateParam("money", money);
			return st;*/
			return getTemplateWallet(accountName, time, transactionType, String.valueOf(money));
		}else{
			logger.error("参数不能为空; accountName=" + accountName + "\ttime=" + time+ "\ttransactionType=" + transactionType+ "\tmoney=" + money);
			return null;
		}
		
	}
	
	/**
	 * 获取金额变动短信模板
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 * @return
	 */
	public static SMSTemplate getTemplateWallet(String accountName, Date time,String transactionType, String money) {
		if(StringUtils.isNotEmpty(accountName) && StringUtils.isNotEmpty(transactionType) && time != null && StringUtils.isNotEmpty(money)){
			SMSTemplate st = new SMSTemplate("91003115");
			st.addTemplateParam("accountName", accountName);
			st.addTemplateParam("time", DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
			st.addTemplateParam("transactionType", transactionType);
			st.addTemplateParam("money", money);
			return st;
		}else{
			logger.error("参数不能为空; accountName=" + accountName + "\ttime=" + time+ "\ttransactionType=" + transactionType+ "\tmoney=" + money);
			return null;
		}
	}
	
	/**
	 * 获取金额变动短信模板
	 * @param accountName 账户；例：保证金
	 * @param time 时间
	 * @param transactionType 交易类型; 例：扣除
	 * @param money 金额；例：50000
	 * @return
	 */
	public static SMSTemplate getTemplateWalletDespositToGuaranty(Date time, float money){
		if(time != null && money>0){
			SMSTemplate st = new SMSTemplate("91003546");
			st.addTemplateParam("time", DateUtil.DateToStr(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
			st.addTemplateParam("money", money);
			return st;
		}else{
			logger.error("参数不能为空; time=" + time+ "\tmoney=" + money);
			return null;
		}
	}
	
	/**
	 * 获取APP下载短信模板 
	 * @param url 下载地址
	 * @return
	 */
	public static SMSTemplate getTemplateDownApp(String url) {
		if(StringUtils.isNotEmpty(url)){
			SMSTemplate st = new SMSTemplate("91003115");
			st.addTemplateParam("url", url);
			return st;
		}else{
			logger.error("参数不能为空; url=" + url );
			return null;
		}
	}
	
	public String toJsonStr() {
		return new Gson().toJsonTree(this.templateParams).getAsJsonObject().toString();
	}

}
