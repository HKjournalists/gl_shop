/**
 *
 */
package com.appabc.tools.sms;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.bo.MsgSendResultBean;
import com.appabc.bean.pvo.TShortMessageConfig;
import com.appabc.bean.pvo.TShortMessageUsed;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.service.sms.IToolShortMessageConfigService;
import com.appabc.tools.service.sms.IToolShortMessageUsedService;

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
	private IToolShortMessageUsedService shortMessageUsedService;
	@Autowired
	private IToolShortMessageConfigService shortMessageConfigService;
	
	
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
		
		MsgSendResultBean msr = sendMsg(getMsgConfig(189, smi.getType()), smi);
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
		smc.setTemplatetype(templatetype);
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
	
}
