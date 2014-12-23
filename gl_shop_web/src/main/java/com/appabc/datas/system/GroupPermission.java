package com.appabc.datas.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Nov 1, 2014 3:34:50 PM
 */
public enum GroupPermission {

    CustomerService(1, Permission.PhoneDialIn, Permission.PhoneDialOUt),
    SiteSales(2,
            Permission.PromptVerify, Permission.PromptDeposit,
            Permission.PublishInfo, Permission.PromptPayment,
            Permission.PromptRealPay),
    ContractManage(3, Permission.ContractMatch, Permission.ContractGenerateConfirm),
    Editor(4, Permission.MarketInfoEdit, Permission.VerifyInfoAudit),
    FinanceProcess(5,
            Permission.AccountAudit, Permission.RemittanceInput,
            Permission.RemittanceProcess, Permission.PayProcess),
    FinanceAudit(6,
            Permission.RemittanceAudit, Permission.PayAudit, Permission.SpecialFundProcess),
    SystemManage(7, Permission.AccountManager, Permission.SpecialConditionProcess);

    private int id;
    private List<Permission> permissions;

    private GroupPermission(int id, Permission...perm) {
        permissions = Collections.unmodifiableList(Arrays.asList(perm));
    }

    public int getId() {

        return id;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public boolean isGroupMember(Permission perm) {
        return permissions.contains(perm);
    }
}
