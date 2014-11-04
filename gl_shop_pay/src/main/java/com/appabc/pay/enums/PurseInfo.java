package com.appabc.pay.enums;

/**
 * @Description : 钱包相关的枚举信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 上午10:49:03
 */

public interface PurseInfo {
	
	/**
	 * 功能描述:钱包类型
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum PurseType implements PurseInfo {
		
		/*保证金*/
		GUARANTY("0"),
		
		/*货款*/
		DEPOSIT("1");
		
		private String value;
		
		private PurseType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:流水方向
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum PayDirection implements PurseInfo {
		
		/*流入*/
		INPUT(0),
		
		/*流出*/
		OUTPUT(1);
		
		private int value;
		
		private PayDirection(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:交易类型
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum TradeType implements PurseInfo {
		/**充值*/
		DEPOSIT("0"),
		/**提取申请*/
		EXTRACT_CASH_REQUEST("1"),
		/**提取成功*/
		EXTRACT_CASH_SUCCESS("2"),
		/**提取失败*/
		EXTRACT_CASH_FAILURE("3"),
		/**保证金冻结*/
		GELATION_GUARANTY("4"),
		/**保证金解冻*/
		UNGELATION_GUARANTY("5"),
		/**违约扣除*/
		VIOLATION_DEDUCTION("6"),
		/**人工扣除*/
		MANPOWER_DEDUCTION("7"),
		/**违约补偿*/
		VIOLATION_REPARATION("8"),
		/**人工补偿*/
		MANPOWER_REPARATION("9"),
		/**货款转保证金*/
		DEPOSIT_GUARANTY("10"),
		/**支付货款*/
		PAYMENT_FOR_GOODS("11"),
		/**服务费*/
		SERVICE_CHARGE("12"),
		/**平台返还*/
		PLATFORM_RETURN("13"),
		/**平台支付*/
		PLATFORM_PAY("14"),
		/**平台补贴*/
		PLATFORM_SUBSIDY("15"),
		/**其他*/
		OTHERS_TRANSFER("16");
		
		private String value;
		
		private TradeType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:支付方式
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum PayWay implements PurseInfo {
		/*网银支付*/
		NETBANK_PAY("0"),
		/*银行扣款*/
		BANK_DEDUCT("1"),
		/*平台扣费*/
		PLATFORM_DEDUCT("2");
		
		private String value;
		
		private PayWay(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:交易状态
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum TradeStatus implements PurseInfo {
		/*初始化,申请*/
		REQUEST("0"),
		/*成功*/
		SUCCESS("1"),
		/*失败*/
		FAILURE("2");
		
		private String value;
		
		private TradeStatus(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:提取状态
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ExtractStatus implements PurseInfo {
		/*申请*/
		REQUEST(0),
		/*审核*/
		SUCCESS(1),
		/*失败*/
		FAILURE(2),
		/*扣款*/
		DEDUCT(3);
		
		private int value;
		
		private ExtractStatus(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:线下收款的业务类型
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum BusinessType implements PurseInfo {
		/**支付*/
		PAY("0"),
		/**充值*/
		DEPOSIT("1");
		
		private String value;
		
		private BusinessType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	/**
	 * 功能描述:设备类型
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum DeviceType implements PurseInfo {
		/**手机*/
		MOBILE("0"),
		/**电脑*/
		COMPUTER("1");
		
		private String value;
		
		private DeviceType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	public enum OnOffLine implements PurseInfo {
		/**线上*/
		ONLINE("0"),
		/**线下*/
		OFFLINE("1");
		
		private String value;
		
		private OnOffLine(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
}
