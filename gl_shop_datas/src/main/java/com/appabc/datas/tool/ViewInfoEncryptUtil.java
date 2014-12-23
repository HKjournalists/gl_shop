/**
 *
 */
package com.appabc.datas.tool;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.bo.OrderAllInfor;
import org.apache.commons.lang.StringUtils;

/**
 * @Description : 信息加密处理
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月20日 下午2:48:41
 */
public class ViewInfoEncryptUtil {

	/**
	 * 企业信息加密
	 * @param cai
	 */
	public static void encryptCompanyInfo(CompanyAllInfo cai){
		if(cai != null){
			cai.setCname(ViewInfoEncryptUtil.replayStringEncrypt(cai.getCname(), 2, 2));
			cai.setContact(ViewInfoEncryptUtil.replayStringEncrypt(cai.getContact(), 1, 0));
			cai.setCphone(ViewInfoEncryptUtil.replayStringEncrypt(cai.getCphone(), 3, 0));
			cai.setTel(ViewInfoEncryptUtil.replayStringEncrypt(cai.getTel(), 4, 0));
		}
	}

	/**
	 * 供求信息加密
	 * @param cai
	 */
	public static void encryptCompanyInfo(OrderAllInfor oai){
		if(oai != null){
			oai.setCname(ViewInfoEncryptUtil.replayStringEncrypt(oai.getCname(), 2, 2));
		}
	}

	/**
	 * 字符串加密替换
	 * @param str
	 * @param beginNum 前面保留位数
	 * @param endNum 后面保留位数
	 * @return
	 */
	public static String replayStringEncrypt(String str, int beginNum, int endNum){
		if(StringUtils.isEmpty(str)) return str;

		int length = str.length();
		if(length <= beginNum || length <= endNum) return str;
		if(length <= beginNum + endNum) return str;

		String strsub = str.substring(beginNum,length - endNum);

		StringBuilder estr = new StringBuilder();
		for(int i=0; i<strsub.length(); i++){
			estr.append("*");
		}

		return str.replace(strsub, estr);
	}


	public static void main(String[] args) {
		String str = ViewInfoEncryptUtil.replayStringEncrypt("深圳市国立数码动画有限公司", 2, 2);
		System.out.println(str);
		str = ViewInfoEncryptUtil.replayStringEncrypt("张三丰", 1, 0);
		System.out.println(str);
		str = ViewInfoEncryptUtil.replayStringEncrypt("13800138000", 3, 0);
		System.out.println(str);
		str = ViewInfoEncryptUtil.replayStringEncrypt("0755-22334455", 4, 0);
		System.out.println(str);
	}

}
