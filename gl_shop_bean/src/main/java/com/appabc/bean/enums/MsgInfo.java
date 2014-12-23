/**
 *
 */
package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 系统消息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月16日 下午2:45:07
 */
public interface MsgInfo extends IBaseEnum {
	
	/**
	 * @Description : 消息状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月16日 下午3:33:24
	 */
	public enum MsgStatus implements MsgInfo {
		
		/**
		 * 未读
		 */
		STATUS_IS_READ_NO(0,"未读"),
		/**
		 * 已读
		 */
		STATUS_IS_READ_YES(1,"已读");
		
		private int val;
		
		private String text;

		private MsgStatus(int val,String text){
			this.val = val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static MsgStatus enumOf(int value){
			for (MsgStatus os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			MsgStatus ms = enumOf(value);
			if(ms != null){
				return ms.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 消息类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月16日 下午3:33:24
	 */
	public enum MsgType implements MsgInfo{
		
		/**
		 * 系统消息
		 */
		MSG_TYPE_001(1,"系统消息"),
		/**
		 * 活动消息
		 */
		MSG_TYPE_002(2,"活动消息");
		
		private int val;
		
		private String text;
		
		private MsgType(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static MsgType enumOf(int value){
			for (MsgType os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			MsgType mt = enumOf(value);
			if(mt != null){
				return mt.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 消息业务类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月16日 下午3:33:24
	 */
	public enum MsgBusinessType implements MsgInfo {
		/**
		 * 用户注册
		 */
		BUSINESS_TYPE_USER_REGISTER("100","用户注册"),
		/**
		 * 用户下线
		 */
		BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE("101","用户在其它设备登录"),
		/**
		 * 企业资料认证
		 */
		BUSINESS_TYPE_COMPANY_AUTH("200","企业资料认证"), 
		/**
		 * 企业收款人认证
		 */
		BUSINESS_TYPE_COMPANY_PAYEE_AUTH("201","企业收款人认证"), 
		/**
		 * 询单，供求信息
		 */
		BUSINESS_TYPE_ORDER_FIND("300","询单，供求信息"), 
		/**
		 * 合同签订
		 */
		BUSINESS_TYPE_CONTRACT_SIGN("400","合同签订"), 
		/**
		 * 合同进行中
		 */
		BUSINESS_TYPE_CONTRACT_ING("401","合同进行中"), 
		/**
		 * 合同评价
		 */
		BUSINESS_TYPE_CONTRACT_EVALUATION("402","合同评价"), 
		/**
		 * 合同取消
		 */
		BUSINESS_TYPE_CONTRACT_CANCEL("403","合同取消"),
		/**
		 * 保证金金额变动
		 */
		BUSINESS_TYPE_MONEY_CHANG_GUARANTY("500","保证金金额变动"),
		/**
		 * 钱包货款金额变动
		 */
		BUSINESS_TYPE_MONEY_CHANG_DEPOSIT("501","货款金额变动"),
		/**
		 * 钱包金额变动
		 */
		BUSINESS_TYPE_MONEY_CHANGE("502","钱包金额变动"),
		/**
		 * 业务类型：APP下载URL
		 */
		BUSINESS_TYPE_APP_DOWNLOAD("900","APP下载URL");
		
		private String val;
		
		private String text;
		
		private MsgBusinessType(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static MsgBusinessType enumOf(String value){
			for (MsgBusinessType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			MsgBusinessType mbt = enumOf(value);
			if(mbt != null){
				return mbt.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 消息内容定义
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年11月13日 下午4:46:17
	 */
	public enum MsgContent implements MsgInfo {
		
		MSG_CONTENT_COMPANY_000("您的帐号已在#Client#客户端登录，您被迫下线。如果这不是您本人操作，那么您的密码很可能已经泄露，建议您修改密码。"),
		MSG_CONTENT_COMPANY_001("您已注册成功，欢迎使用长江电商电子交易平台，我们将竭诚为您服务。客服电话：#TEL#"),
		MSG_CONTENT_COMPANY_002("您的长江电商平台认证已提交成功，我们将在48小时内为您进行审核，敬请留意。"),
		MSG_CONTENT_COMPANY_003("您的长江电商平台认证已通过，现在赶紧开启您的商道传奇吧，无限商机正在等着您。"),
		MSG_CONTENT_COMPANY_004("您的长江电商平台认证审核未能通过，请提交真实规范的认证资料进行再次尝试。"),
		
		MSG_CONTENT_CONTRACT_001("您有一个待确认的合同，请您及时确认。"),
		MSG_CONTENT_CONTRACT_002("您的合同：#Number#正在进行中，请您在#Num#小时内将货款支付到平台，逾期将被视为违约。"),
		MSG_CONTENT_CONTRACT_003("您的合同：#Number#需您验收，在您收货之前，请您详细检验货物，确认实际货物与合同中货物描述是否一致。"),
		MSG_CONTENT_CONTRACT_004("您的合同：#Number#议价对方有回复了，请尽快查看并与对方协商一致。否则合同将不能如期完成交易。"),
		MSG_CONTENT_CONTRACT_005("您的合同：#Number#需您确认收货，请您再次确认货物全体是否和合同描述一致，确认收货后平台将立即向卖方支付货款。"),
		MSG_CONTENT_CONTRACT_006("您的合同：#Number#需您确认收货，卖方已经确认卸货，如果您在#Num#小时内不确认收货，平台将视您已经收货，并向卖方支付货款。"),
		MSG_CONTENT_CONTRACT_007("您的合同：#Number#已完成。请您给对方评价，您的评价将会是其他人的重要参考。如果您不评价，7天后系统会进行默认好评。"),
		MSG_CONTENT_CONTRACT_008("您的合同：#Number#，买方将在#Num#小时内向平台支付货款，平台会冻结货款并在交易完成时支付给您。如买方不按期向平台支付货款，平台视为买方违约，将违约金赔付给您。您可以按计划发货了。"),
		MSG_CONTENT_CONTRACT_009("您的合同：#Number#，对方已确认可以收取货物，平台在您确认卸货完毕与买方确认实际收货完毕后，平台将向您支付货款。"),
		MSG_CONTENT_CONTRACT_010("您的合同：#Number#，对方验收认为货物与合同记载有出入，提出了议价建议，希望与您进行协商。"),
		MSG_CONTENT_CONTRACT_011("您的合同：#Number#，买卖双方已协商一致想取消，客服已发起取消合同确认，请您在24小时内尽快确认取消合同，否则合同将取消失败。如有疑问，请联系客服#TEL#"),
		MSG_CONTENT_CONTRACT_012("您的合同：#Number#，买卖双方已协商一致想取消，由于其中一方未能在24小时内进行确认取消，该合同本次取消失败。合同流程将继续进行，如有疑问，请联系客服#TEL#"),
		MSG_CONTENT_CONTRACT_013("您的合同：#Number#，已被对方单方面取消成功。接下来系统将按平台规则将对方的违约金赔付给您，敬请留意。如有疑问，请联系客服#TEL#"),
		MSG_CONTENT_CONTRACT_014("您的合同：#Number#，您已单方面取消成功。接下来系统将按平台规则将扣除您的违约金赔付给对方，敬请留意。如有疑问，请联系客服#TEL#"),
		MSG_CONTENT_CONTRACT_015("您的合同：#Number#已发起，对方已于{time}确认成功，现在正在等待您的确认，若您确认成功后合同将即刻正式生效，请尽快处理。"),
		MSG_CONTENT_CONTRACT_016("您的合同：#Number#正在申请双方协商取消，对方已于{time}确认取消成功，现在正在等待您的确认，请尽快处理。"),
		
		MSG_CONTENT_MONEY_001("您的长江电商钱包-{accountName}账户于{time}发生【{transactionType}】金额{money}元"),
		MSG_CONTENT_MONEY_002("尊敬的用户，您的长江电商钱包-货款账户于{time}向您的保证金账户发起{money}元的转款。"),
		
		MSG_CONTENT_022("");
		
		private String val;
		
		private MsgContent(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
		
	}

}
