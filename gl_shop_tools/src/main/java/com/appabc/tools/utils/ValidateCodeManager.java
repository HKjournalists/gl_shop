package com.appabc.tools.utils;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.common.utils.RandomValidateCode;
import com.appabc.common.utils.RedisHelper;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.bean.ShortMsgInfo;
import com.appabc.tools.enums.SmsInfo;
import com.appabc.tools.sms.ISmsSender;

/**
 * @Description : 验证码管理（短信验证码，图片验证码）
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 上午11:06:17
 */
@Repository
public class ValidateCodeManager {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private SystemParamsManager spm;
	@Autowired
	private ISmsSender smsSender;
	
	/**
	 * 获取短信验证码
	 * @param phone 手机号
	 * @return
	 */
	public String getSmsCode(String phone){
		return redisHelper.getString(SystemConstant.SMS_CODE_KEY + phone);
	}
	
	/**
	 * 保存短信验证码
	 * @param phone
	 * @param smsCode
	 */
	public void saveSmsCode(String phone, String smsCode){
		redisHelper.set(SystemConstant.SMS_CODE_KEY + phone, smsCode, spm.getInt("SMS_VLD_CODE_TIME_LENGTH"));
	}
	
	/**
	 * 获取图片验证码
	 * @param userName 用户名
	 * @return
	 */
	public String getImgCode(String key){
		return this.redisHelper.getString(SystemConstant.IMG_CODE_KEY + key);
	}
	
	/**
	 * 删除图片验证码
	 * @param key
	 */
	public void delImgCode(String key){
		this.redisHelper.del(SystemConstant.IMG_CODE_KEY + key);
	}
	
	/**
	 * 删除手机验证码
	 * @param phone
	 */
	public void delSmsCode(String phone){
		this.redisHelper.del(SystemConstant.SMS_CODE_KEY + phone);
	}

	/**
	 * 输出验证码图片流
	 * @param key 用于存储code的键值
	 * @param request
	 * @param response
	 * @return 验证码
	 */
	public String outputImgCode(String key, HttpServletRequest request, HttpServletResponse response){
		RandomValidateCode rvd = new RandomValidateCode();
		String code = rvd.getRandcode(request, response);
		redisHelper.set(SystemConstant.IMG_CODE_KEY + key, code, spm.getInt("IMG_VLD_CODE_TIME_LENGTH"));
		return code;
	}
	
	/**
	 * 短信验证码发送
	 * @param phone 手机号
	 * @param userName 用户称谓，userName=XX 例：尊敬的XX用户。userName length<=10
	 * @return
	 */
	public boolean sendSmsCode(String phone, String userName){
		if(StringUtils.isEmpty(userName)){
			userName=" ";
		}else if(userName.length()>10){
			userName = userName.substring(0,10);
			logger.info("用户名最长为10,userName="+userName);
		}
		ShortMsgInfo smi = new ShortMsgInfo();
		smi.setBusinessType(SmsInfo.BusinessTypeEnum.SMS_BUSINESS_TYPE_REGISTER.getVal());
		smi.setContent(getCode());
		smi.setTel(phone);
		smi.setType(SmsInfo.SmsTypeEnum.SMS_TEMP_TYPE_PIN.getVal());
		smi.setUser(userName);
		
		return smsSender.sendMsg(smi);
	}
	
	
	/**
	 * 获取一个4位字符串，内容为数字
	 * @return
	 */
	private String getCode(){
		String code = "";
		Random random = new Random();	
		for(int i=0; i<4; i++){
			code += random.nextInt(10);
		}
		return code;
	}
}
