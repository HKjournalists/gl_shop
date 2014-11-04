package com.appabc.datas.enums;

/**
 * @Description : 合同信息相关
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月2日 上午11:22:30
 */
public interface ContractInfo {
	
	/**
	 * 功能描述:合同类型相关
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractType implements ContractInfo {
		
		/*"未签约,起草"*/
		DRAFT("0"),
		
		/*"已经签约"*/
		SIGNED("1");
		
		private String value;
		
		private ContractType(String value){
			this.value = value;
		}
		
		public String getValue(){return value;}
		
	}
	
	/**
	 * 功能描述:合同生命周期
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractLifeCycle implements ContractInfo {
		
		/*"起草中"*/
		DRAFTING("0"),
		
		/*"起草超时结束"*/
		TIMEOUT_FINISHED("1"),
		
		/*"起草手动作废（由客服）"*/
		MANUAL_RESTORE("2"),
		
		/*"已签订"*/
		SINGED("3"),
		
		/*"付款中"*/
		IN_THE_PAYMENT("4"),
		
		/*"已付款"*/
		PAYED_FUNDS("5"),
		
		/*"已发货"*/
		SENT_GOODS("6"),
		
		/*"抽样验收中"*/
		SIMPLE_CHECKING("7"),

		/*"抽样验收通过"*/
		SIMPLE_CHECKED("8"),
		
		/*"全量验收中"*/
		FULL_TAKEOVERING("9"),
		
		/*"全量验收通过"*/
		FULL_TAKEOVERED("10"),
		
		/*"已经卸货"*/
		UNINSTALLED_GOODS("11"),
		
		/*"已经收货"*/
		RECEIVED_GOODS("12"),
		
		/*"取消中"*/
		CANCELING("13"),
		
		/*"结算中"*/
		FINALESTIMATEING("14"),
		
		/*"结算完成"*/
		FINALESTIMATE_FINISHED("15"),
		
		/*"正常结束"*/
		NORMAL_FINISHED("16"),
		
		/*"单方取消结束"*/
		SINGLECANCEL_FINISHED("17"),
		
		/*"双方取消结束"*/
		DUPLEXCANCEL_FINISHED("18"),
		
		/*"终止结束"*/
		EXPIRATION_FINISHED("19"),
		
		/*"已经仲裁"*/
		ARBITRATION("20");
		
		private String value;
		
		private ContractLifeCycle(String value){
			this.value = value;
		}
		
		public String getValue(){return value;}
		
	}
	
	/**
	 * 功能描述:合同状态相关
	 * @author Bill.Huang
	 * <p>2014-09-02 上午10:21:14<p>
	 * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public enum ContractStatus implements ContractInfo {
		
		/*"起草"*/
		DRAFT("0"),
		/*"进行"*/
		DOING("1"),
		/*"暂停"*/
		PAUSE("2"),
		/*"结束"*/
		FINISHED("3");
		
		private String value;
		
		private ContractStatus(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
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
		SINGLE_CANCEL("0"),
		
		/*"双方取消"*/
		DUPLEX_CANCEL("1");
		
		private String value;
		
		private ContractCancelType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
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
		CONFRIM_CONTRACT("0"),
		
		/*"买家付款"*/
		PAYED_FUNDS("1"),
		
		/*"发货"*/
		SEND_GOODS("2"),
		
		/*"咨询客服"*/
		CONSULTING_SERVICE("3"),
		
		/*"单方取消"*/
		SINGLE_CANCEL("4"),
		
		/*"议价"*/
		DIS_PRICE("5"),
		
		/*"验收通过"*/
		VALIDATE_PASS("6"),
		
		/*"同意议价"*/
		APPLY_DISPRICE("7"),
		
		/*"确认卸货"*/
		CONFIRM_UNINSTALLGOODS("8"),
		
		/*"确认收货"*/
		CONFIRM_RECEIVEGOODS("9"),
		
		/*"评价合同"*/
		EVALUATION_CONTRACT("10"),
		
		/*"取消确认"*/
		CANCEL_CONFIRM("11"),
		
		/*"撤销取消"*/
		REPEAL_CANCEL("12"),
		
		/*"合同仲裁"*/
		ARBITRATION_CONTRACT("13"),
		
		/*"合同结算"*/
		CONTRACT_ESTIMATE("14"),
		
		/*"合同结束"*/
		CONTRACT_FINISHED("15");
		
		
		private String value;
		
		private ContractOperateType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
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
		SAMPLE_CHECK("0"),
		
		/*"全量验收"*/
		FULL_TAKEOVER("1");
		
		private String value;
		
		private ContractDisPriceType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
}
