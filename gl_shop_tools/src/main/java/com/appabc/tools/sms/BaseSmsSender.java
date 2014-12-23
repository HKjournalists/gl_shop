/**
 *
 */
package com.appabc.tools.sms;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.bo.MsgSendResultBean;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.bean.pvo.TShortMessageUsed;
import com.appabc.tools.bean.SMSTemplate;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.service.sms.IShortMessageConfigService;
import com.appabc.tools.service.sms.IShortMessageUsedService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月30日 下午4:12:56
 */
public abstract class BaseSmsSender implements ISmsSender {

	@Autowired
	private IShortMessageUsedService shortMessageUsedService;
	@Autowired
	private IShortMessageConfigService shortMessageConfigService;
	
	
	/**
	 * 发送接口
	 * @param smc
	 * @param smi
	 * @return
	 */
	public abstract MsgSendResultBean sendMsg(TShortMessageConfig smc, ShortMsgInfo smi);
	
	@Override
	public boolean sendMsg(ShortMsgInfo smi) {
		
		boolean btf = false; 
		
		MsgSendResultBean msr = sendMsg(getMsgConfig(189, null), smi);
		if(msr.getResCode().equals("0")){
			btf = true;
		}
		
		saveSendResult(msr); // 发送结果存储
		return btf;
	}
	
	
	/**
	 * 获取短信帐号配置
	 * @param type
	 * @param templatetype
	 * @return
	 */
	protected TShortMessageConfig getMsgConfig(int type, String templatetype) {
		
		TShortMessageConfig smc = new TShortMessageConfig();
		smc.setType(type); // 189
//		smc.setTemplatetype(templatetype);
		smc.setStatus(0);
		
		return this.shortMessageConfigService.query(smc);
		
	}

	/**
	 * 短信发送返回结果处理
	 * @param msrb
	 * @return
	 */
	protected void saveSendResult(MsgSendResultBean msr) {
		
		TShortMessageUsed smu = new TShortMessageUsed();
		smu.setBusinessid(msr.getBusinessId());
		smu.setBusinesstype(msr.getBusinessType());
		smu.setPhonenumber(msr.getTel());
		smu.setRemark(msr.getRemark());
		smu.setSendstatus(Integer.valueOf(msr.getResCode()));
		smu.setSendtime(Calendar.getInstance().getTime());
		smu.setSmcontent(msr.getContent());
		
		shortMessageUsedService.add(smu);
		
	}
	
	public static void main(String[] args) {
		ShortMsgInfo smi = new ShortMsgInfo();
		smi.setBusinessType(MsgBusinessType.BUSINESS_TYPE_USER_REGISTER);
		smi.setTel("1234565");
//		smi.setType(SmsTypeEnum.SMS_TEMP_TYPE_PIN);
		
		SMSTemplate template = SMSTemplate.getTemplatePin("123", "5","400-9616-816");
		smi.setTemplate(template);
		
		System.out.println(smi.getTemplate().toString());
		System.out.println(smi.getTemplate().toJsonStr());
	}
	
}
