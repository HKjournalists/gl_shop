package com.appabc.datas.cms.vo.task;

import com.appabc.datas.cms.dao.tasks.ITaskDaoMeta;
import com.appabc.datas.cms.dao.tasks.OrderRequestMeta;
import com.appabc.datas.cms.dao.tasks.UrgeDepositMeta;
import com.appabc.datas.cms.dao.tasks.UrgeVerifyMeta;
import com.appabc.datas.cms.dao.tasks.VerifyInfoMeta;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 4:10:55 PM
 */
public enum TaskType {

	Unknown(0, null),

    // 供求询单
    MatchOrderRequest(TaskType.MATCH_ORDER_REQUEST, OrderRequestMeta.INSTANCE),

    ContractConfirm(TaskType.CONTRACT_CONFIRM,null),
    
    ContractArbitrate(TaskType.CONTRACT_ARBITRATE,null),

    // 认证信息审核
	VerifyInfo(TaskType.VERIFY_INFO_AUDIT, VerifyInfoMeta.INSTANCE),
	
	// 供求代发
	OrderFindPublish(TaskType.PUBLISH_INFO,null),
	
	//催促认证
	UrgeVerify(TaskType.PROMPT_VERIFY,UrgeVerifyMeta.INSTANCE),
	
	//催促保证金
	UrgeDeposit(TaskType.PROMPT_DEPOSIT,UrgeDepositMeta.INSTANCE);

    // Customer Service
    public final static int PHONE_DIAl_IN = 1;
    public final static int PHONE_DIAl_OUT = 2;

    // Site Sales
    public final static int PROMPT_VERIFY = 21;
    public final static int PROMPT_DEPOSIT = 22;
    public final static int PUBLISH_INFO = 23;
    public final static int PROMPT_PAYMENT = 24;
    public final static int PROMPT_REAL_PAY = 25;

    // Contract
    public final static int MATCH_ORDER_REQUEST = 41;
    public final static int CONTRACT_ARBITRATE = 42;
    public final static int CONTRACT_EXPIRE = 43;
    public final static int CONTRACT_CONFIRM = 44;

    // Editor
    public final static int MARKET_INFO_EDIT = 61;
    public final static int VERIFY_INFO_AUDIT = 62;

    // Financial Process
    public final static int REMITTANCE_INPUT = 81;
    public final static int BANK_ACCOUNT_AUDIT = 82;
    public final static int REMITTANCE_PROCESS = 83;
    public final static int WITHDRAW_PROCESS = 84;

    // Financial Audit
    public final static int REMITTANCE_AUDIT = 101;
    public final static int WITHDRAW_CASHOUT = 102;
    public final static int SPECIAL_FUND_PROCESS = 103;
    
    // System management
    public final static int ACCOUNT_MANAGER = 10001;
    public final static int SPECIAL_CONDITION_PROCESS = 10002;
    public final static int BANNERADVERTISEMENT_CONFIGURE = 10003;
    public final static int CODE_RIVERSECTION = 10004;
    
    // ofter tool
    public final static int SHORTMESSAGE_SEND = 104;
    public final static int SYSTEMMESSAGE_SEND = 105;
    public final static int VALIDATECODE_MANAGE = 106;

	private int value;
	private ITaskDaoMeta daoMeta;

	private TaskType(int value, ITaskDaoMeta meta) {
		this.value = value;
        this.daoMeta = meta;
	}

	public int getValue() {
		return this.value;
	}

    public ITaskDaoMeta getDaoMeta() {
        return daoMeta;
    }

    public static TaskType valueOf(int type) {
		TaskType tt = Unknown;

		for (TaskType taskType : TaskType.values()) {
			if (taskType.getValue() == type) {
				tt = taskType;
				break;
			}
		}

		return tt;
	}

}
