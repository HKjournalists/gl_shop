package com.appabc.datas.cms.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zouxifeng on 3/25/15.
 */
public enum ServiceLogType {
    Unknown(0),
    // Sales

    // Contract
    MatchOrder(1),
    ContractArbitration(2),

    // Editor
    VerifyAuthInfo(20),

    // Finance
    RemittanceInput(21),
    RemittanceProcess(22),
    RemittanceAudit(23),
    BankAccountAudit(24),
    Withdraw(25);

    private static Map<Integer, ServiceLogType> mappings;

    static {
        mappings = new HashMap<>();
        for (ServiceLogType t : values()) {
            mappings.put(t.getType(), t);
        }
    }

    private int type;

    public static ServiceLogType valueOf(int type) {
        return mappings.containsKey(type) ? mappings.get(type) : Unknown;
    }

    ServiceLogType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
