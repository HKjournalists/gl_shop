package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;


/**
 * @Description : 钱包相关的枚举信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 上午10:49:03
 */

public interface PurseInfo extends IBaseEnum{
	
	/**
	 * 功能描述:钱包类型
	 * @author Bill.Huang
	 * <p>2014-09-24 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum PurseType implements PurseInfo {
		
		/*保证金*/
		GUARANTY("0","保证金"),
		
		/*货款*/
		DEPOSIT("1","货款");
		
		private String val;
		
		private String text;
		
		private PurseType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static PurseType enumOf(String value){
			for (PurseType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val , value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			PurseType pt = enumOf(value);
			if(pt != null){
				return pt.text;
			}
			return null;
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
		INPUT(0,"流入"),
		
		/*流出*/
		OUTPUT(1,"流出");
		
		private int val;
		
		private String text;
		
		private PayDirection(int value,String text){
			this.val = value;
			this.text = text;
		}
		
		public int getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static PayDirection enumOf(int value){
			for (PayDirection os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			PayDirection pd = enumOf(value);
			if(pd != null){
				return pd.text;
			}
			return null;
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
		DEPOSIT("0","汇入"),
		/**提取冻结*/
		EXTRACT_CASH_GELATION("1","提取冻结"),
		/**提取解冻*/
		EXTRACT_CASH_UNGELATION("2","提取解冻"),
		/**提取成功*/
		EXTRACT_CASH_SUCCESS("3","提取成功"),
		/**提取失败*/
		EXTRACT_CASH_FAILURE("4","提取失败"),
		/**保证金冻结*/
		GELATION_GUARANTY("5","保证金冻结"),
		/**保证金解冻*/
		UNGELATION_GUARANTY("6","保证金解冻"),
		/**货款冻结*/
		GELATION_DEPOSIT("7","买货冻结"),
		/**货款解冻*/
		UNGELATION_DEPOSIT("8","买货解冻"),
		/**违约扣除*/
		VIOLATION_DEDUCTION("9","赔付对方付出"),
		/**人工扣除*/
		MANPOWER_DEDUCTION("10","平台扣除+备注"),
		/**违约补偿*/
		VIOLATION_REPARATION("11","对方赔付"),
		/**人工补偿*/
		MANPOWER_REPARATION("12","平台汇入+备注"),
		/**货款转保证金*/
		DEPOSIT_GUARANTY("13","货款转保证金"),
		/**支付货款*/
		PAYMENT_FOR_GOODS("14","支付货款"),
		/**服务费*/
		SERVICE_CHARGE("15","交易扣除费"),
		/**平台返还*/
		PLATFORM_RETURN("16","平台返还"),
		/**平台支付*/
		PLATFORM_PAY("17","平台支付"),
		/**平台补贴*/
		PLATFORM_SUBSIDY("18","平台补贴"),
		/**其他*/
		OTHERS_TRANSFER("19","其他");
		
		private String val;
		
		private String text;
		
		private TradeType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static TradeType enumOf(String value){
			for (TradeType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			TradeType tt = enumOf(value);
			if(tt != null){
				return tt.text;
			}
			return null;
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
		NETBANK_PAY("0","在线支付"),
		/*银行转账*/
		BANK_DEDUCT("1","银行转账"),
		/*平台操作*/
		PLATFORM_DEDUCT("2","长江电商钱包"),
		/*线下付款*/
		OFFLINE_PAY("3","线下付款");
		
		private String val;
		
		private String text;
		
		private PayWay(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static PayWay enumOf(String value){
			for (PayWay os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			PayWay pw = enumOf(value);
			if(pw != null){
				return pw.text;
			}
			return null;
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
		REQUEST("0","初始化,申请"),
		/*成功*/
		SUCCESS("1","成功"),
		/*失败*/
		FAILURE("2","失败");
		
		private String val;
		
		private String text;
		
		private TradeStatus(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static TradeStatus enumOf(String value){
			for (TradeStatus os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			TradeStatus ts = enumOf(value);
			if(ts != null){
				return ts.text;
			}
			return null;
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
		REQUEST(0,"申请"),
		/*审核*/
		SUCCESS(1,"审核"),
		/*失败*/
		FAILURE(2,"失败"),
		/*扣款*/
		DEDUCT(3,"扣款");
		
		private int val;
		
		private String text;
		
		private ExtractStatus(int value,String text){
			this.val = value;
			this.text = text;
		}
		
		public int getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ExtractStatus enumOf(int value){
			for (ExtractStatus os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			ExtractStatus es = enumOf(value);
			if(es != null){
				return es.text;
			}
			return null;
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
		PAY("0","支付"),
		/**充值*/
		DEPOSIT("1","充值");
		
		private String val;
		
		private String text;
		
		private BusinessType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static BusinessType enumOf(String value){
			for (BusinessType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			BusinessType bt = enumOf(value);
			if(bt != null){
				return bt.text;
			}
			return null;
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
		MOBILE("0","手机"),
		/**电脑*/
		COMPUTER("1","电脑"),
		/**安卓手机*/
		ANDROID("2","安卓手机"),
		/**苹果手机*/
		IPHONE("3","苹果手机");
		
		private String val;
		
		private String text;
		
		private DeviceType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static DeviceType enumOf(String value){
			for (DeviceType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			DeviceType dt = enumOf(value);
			if(dt != null){
				return dt.text;
			}
			return null;
	    }
		
	}
	
	public enum OnOffLine implements PurseInfo {
		/**线上*/
		ONLINE("0","线上"),
		/**线下*/
		OFFLINE("1","线下");
		
		private String val;
		
		private String text;
		
		private OnOffLine(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static OnOffLine enumOf(String value){
			for (OnOffLine os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			OnOffLine oo = enumOf(value);
			if(oo != null){
				return oo.text;
			}
			return null;
	    }
		
	}
	
}
