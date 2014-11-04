/**  
 * com.appabc.pay.enums.WalletInfo.java  
 *   
 * 2014年9月18日 下午4:40:30  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.enums;

/**
 * @Description : 钱包枚举相关
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华[码农华]
 * @version     : 1.0
 * @Create_Date  : 2014年9月18日 下午4:40:30
 */

public interface WalletInfo {
	
	/**
	 * 功能描述:钱包类型
	 * @author 码农华 
	 * <p>2014年9月18日 下午4:40:30<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum WalletType implements WalletInfo {
		
		/*保证金*/
		CASH_DEPOSIT("0"),
		
		/*货物款项：货款*/
		PAYMENT_FOR_GOODS("1");
		
		private String value;
		
		private WalletType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:支付明细的资金走向
	 * @author 码农华 
	 * <p>2014年9月18日 下午4:40:30<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum PayDirection implements WalletInfo {
		
		/*支付流向：流入*/
		INPUT("0"),
		
		/*支付流向：流出*/
		OUTPUT("1");
		
		private String value;
		
		private PayDirection(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:交易类型
	 * @author 码农华 
	 * <p>2014年9月18日 下午4:40:30<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum TradeType implements WalletInfo {
		/*充值*/
		DEPOSIT("0"),
		
		/*提取*/
		EXTRACT("1");
		
		private String value;
		
		private TradeType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
}
