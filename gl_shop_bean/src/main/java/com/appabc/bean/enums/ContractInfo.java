package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 合同信息相关
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月2日 上午11:22:30
 */
public interface ContractInfo extends IBaseEnum{
	
	/**
	 * 功能描述:合同类型相关
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractType implements ContractInfo {
		
		/*"未签约,起草"*/
		DRAFT("0","未签约,起草"),
		
		/*"已经签约"*/
		SIGNED("1","已经签约");
		
		private String val;
		
		private String text;
		
		private ContractType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){return val;}
		
		public String getText(){return text;}
		
		public static ContractType enumOf(String value){
			for (ContractType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractType ct = enumOf(value);
			if(ct != null){
				return ct.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:买家或者卖家是否评价
	 * @author Bill.Huang
	 * <p>2015-01-12 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractEvaluationType implements ContractInfo {
		
		NOTHING("0","无"),
		
		EVALUATION("1","评价");
		
		private String val;
		
		private String text;
		
		private ContractEvaluationType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){return val;}
		
		public String getText(){return text;}
		
		public static ContractEvaluationType enumOf(String value){
			for (ContractEvaluationType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractEvaluationType ct = enumOf(value);
			if(ct != null){
				return ct.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同起草阶段买家和卖家的动作类型
	 * @author Bill.Huang
	 * <p>2015-01-12 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractDraftStageBuyerSellerDoType implements ContractInfo {
		
		NOTHING("0","无"),
		
		CANCEL("1","取消"),
		
		CONFIRM("2","确认");
		
		private String val;
		
		private String text;
		
		private ContractDraftStageBuyerSellerDoType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){return val;}
		
		public String getText(){return text;}
		
		public static ContractDraftStageBuyerSellerDoType enumOf(String value){
			for (ContractDraftStageBuyerSellerDoType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractDraftStageBuyerSellerDoType ct = enumOf(value);
			if(ct != null){
				return ct.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同生命周期
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractLifeCycle implements ContractInfo {
		
		/*"起草中"*/
		DRAFTING("0","起草中"),
		
		/*"起草超时结束"*/
		TIMEOUT_FINISHED("1","起草超时结束"),
		
		/*"起草手动作废（由客服）"*/
		MANUAL_RESTORE("2","起草手动作废（由客服）"),
		
		/*"已签订"*/
		SINGED("3","已签订"),
		
		/*"付款中"*/
		IN_THE_PAYMENT("4","付款中"),
		
		/*"已付款"*/
		PAYED_FUNDS("5","已付款"),
		
		/*"已发货"*/
		SENT_GOODS("6","已发货"),
		
		/*"抽样验收中"*/
		SIMPLE_CHECKING("7","抽样验收中"),

		/*"抽样验收通过"*/
		SIMPLE_CHECKED("8","抽样验收通过"),
		
		/*"全量验收中"*/
		FULL_TAKEOVERING("9","全量验收中"),
		
		/*"全量验收通过"*/
		FULL_TAKEOVERED("10","全量验收通过"),
		
		/*"已经卸货"*/
		UNINSTALLED_GOODS("11","已经卸货"),
		
		/*"已经收货"*/
		RECEIVED_GOODS("12","已经收货"),
		
		/*"取消中"*/
		CANCELING("13","取消中"),
		
		/*"结算中"*/
		FINALESTIMATEING("14","结算中"),
		
		/*"结算完成"*/
		FINALESTIMATE_FINISHED("15","结算完成"),
		
		/*"正常结束"*/
		NORMAL_FINISHED("16","正常结束"),
		
		/*"单方取消结束"*/
		SINGLECANCEL_FINISHED("17","单方取消结束"),
		
		/*"双方取消结束"*/
		DUPLEXCANCEL_FINISHED("18","双方取消结束"),
		
		/*"终止结束"*/
		EXPIRATION_FINISHED("19","终止结束"),
		
		/*"仲裁中"*/
		ARBITRATING("20","仲裁中"),
		
		/*货款确认中*/
		CONFIRMING_GOODS_FUNDS("21","货款确认中"),
		
		/*起草取消*/
		DRAFTING_CANCEL("22","起草取消"),
		
		/*"仲裁结束"*/
		ARBITRATED("23","仲裁结束"),
		
		/*"买家未付款结束"*/
		BUYER_UNPAY_FINISHED("24","买家未付款结束"),
		
		/*删除合同*/
		DELETE_CONTRACT("25","删除合同");
		
		private String val;
		
		private String text;
		
		private ContractLifeCycle(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){return val;}
		
		public String getText(){return text;}
		
		public static ContractLifeCycle enumOf(String value){
			for (ContractLifeCycle os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractLifeCycle clc = enumOf(value);
			if(clc != null){
				return clc.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同状态相关
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractStatus implements ContractInfo {
		
		/*"起草"*/
		DRAFT("0","起草"),
		/*"进行"*/
		DOING("1","进行"),
		/*"暂停"*/
		PAUSE("2","暂停"),
		/*"结束"*/
		FINISHED("3","结束"),
		/*"删除"*/
		DELETION("4","删除");
		
		private String val;
		
		private String text;
		
		private ContractStatus(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ContractStatus enumOf(String value){
			for (ContractStatus os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractStatus cs = enumOf(value);
			if(cs != null){
				return cs.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同取消类型相关
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractCancelType implements ContractInfo {
		
		/*"单方取消"*/
		SINGLE_CANCEL("0","单方取消"),
		
		/*"双方取消"*/
		DUPLEX_CANCEL("1","双方取消");
		
		private String val;
		
		private String text;
		
		private ContractCancelType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ContractCancelType enumOf(String value){
			for (ContractCancelType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractCancelType cct = enumOf(value);
			if(cct != null){
				return cct.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同操作类型
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractOperateType implements ContractInfo {
		
		/*"确认合同"*/
		CONFRIM_CONTRACT("0","确认合同"),
		
		/*"买家付款"*/
		PAYED_FUNDS("1","买家付款"),
		
		/*"发货"*/
		SEND_GOODS("2","发货"),
		
		/*"咨询客服"*/
		CONSULTING_SERVICE("3","咨询客服"),
		
		/*"单方取消"*/
		SINGLE_CANCEL("4","单方取消"),
		
		/*"议价"*/
		DIS_PRICE("5","议价"),
		
		/*"验收通过"*/
		VALIDATE_PASS("6","验收通过"),
		
		/*"同意议价"*/
		APPLY_DISPRICE("7","同意议价"),
		
		/*"确认卸货"*/
		CONFIRM_UNINSTALLGOODS("8","确认卸货"),
		
		/*"确认收货"*/
		CONFIRM_RECEIVEGOODS("9","确认收货"),
		
		/*"评价合同"*/
		EVALUATION_CONTRACT("10","评价合同"),
		
		/*"取消确认"*/
		CANCEL_CONFIRM("11","取消确认"),
		
		/*"撤销取消"*/
		REPEAL_CANCEL("12","撤销取消"),
		
		/*"合同仲裁处理"*/
		ARBITRATION_CONTRACT("13","合同仲裁处理"),
		
		/*"合同结算"*/
		CONTRACT_ESTIMATE("14","合同结算"),
		
		/*"合同结束"*/
		CONTRACT_FINISHED("15","合同结束"),
		
		/*撮合合同*/
		MAKE_CONTRACT("16","撮合合同"),
		
		/*买家未付款合同结束*/
		BUYER_UNPAY_FINISHED("17","买家未付款合同结束"),
		
		/*取消合同*/
		CANCEL_CONTRACT("18","取消合同"),
		
		/*移至已经结束合同*/
		MOVE_TO_FINISHED_CONTRACT("19","移至已经结束合同"),
		
		/*货物与货款实际确认*/
		FUNDS_GOODS_CONFIRM("20","货物与货款实际确认"),
		
		/*货物与货款实际确认*/
		AGREE_FUND_GOODS_CONFIRM("21","同意货物和货款实际确认"),
		
		/*申请仲裁*/
		APPLY_ARBITRATION("22","申请仲裁"),
		
		/*删除合同*/
		DELETE_CONTRACT("23","删除合同");
		
		private String val;
		
		private String text;
		
		private ContractOperateType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ContractOperateType enumOf(String value){
			for (ContractOperateType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractOperateType cot = enumOf(value);
			if(cot != null){
				return cot.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同仲裁的状态
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractArbitrationStatus implements ContractInfo {
		/*初始化,申请*/
		REQUEST(0,"初始化,申请"),
		/*成功*/
		SUCCESS(1,"成功"),
		/*失败*/
		FAILURE(2,"失败");
		
		private Integer val;
		
		private String text;
		
		private ContractArbitrationStatus(Integer value,String text){
			this.val = value;
			this.text = text;
		}
		
		public Integer getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ContractArbitrationStatus enumOf(Integer value){
			for (ContractArbitrationStatus os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(Integer value) {
			ContractArbitrationStatus ts = enumOf(value);
			if(ts != null){
				return ts.text;
			}
			return null;
	    }
	}
	
	public enum ContractWebCmsTradeType implements ContractInfo {
		
		HISTORY("0","历史交易状态"),PRESENT("1","当前交易状态");
		
		private String val;
		
		private String text;
		
		private ContractWebCmsTradeType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ContractWebCmsTradeType enumOf(String value){
			for (ContractWebCmsTradeType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractWebCmsTradeType cdpt = enumOf(value);
			if(cdpt != null){
				return cdpt.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * 功能描述:合同议价类型
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractDisPriceType implements ContractInfo {
		
		/*"抽样检查"*/
		SAMPLE_CHECK("0","抽样检查"),
		
		/*"全量验收"*/
		FULL_TAKEOVER("1","全量验收"),
		
		/*"货款议价"*/
		FUNDGOODS_DISPRICE("2","货款议价"),
		
		/*"仲裁议价"*/
		ARBITRATION_DISPRICE("3","仲裁议价");
		
		private String val;
		
		private String text;
		
		private ContractDisPriceType(String value,String text){
			this.val = value;
			this.text = text;
		}
		
		public String getVal(){
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static ContractDisPriceType enumOf(String value){
			for (ContractDisPriceType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ContractDisPriceType cdpt = enumOf(value);
			if(cdpt != null){
				return cdpt.text;
			}
			return null;
	    }
		
	}
}
