//
//  PurseEnum.h
//  Glshop
//
//  Created by River on 15-2-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#ifndef Glshop_PurseEnum_h
#define Glshop_PurseEnum_h

typedef NS_ENUM (NSInteger, PayWayType) {
    /*网银支付*/
    NETBANK_PAY,
    /*银行转账*/
    BANK_DEDUCT,
    /*平台操作*/
    PLATFORM_DEDUCT,
    /*线下付款*/
    OFFLINE_PAY,
};

typedef NS_ENUM (NSInteger, PursePayDirection) {
    /*流入*/
    purseINPUT,
    
    /*流出*/
    purseOUTPUT,
};

typedef NS_ENUM(NSInteger, PurseTradeType) {
    /**充值*/
    DEPOSIT,
    /**提取冻结*/
    EXTRACT_CASH_GELATION,
    /**提取解冻*/
    EXTRACT_CASH_UNGELATION,
    /**提取成功*/
    EXTRACT_CASH_SUCCESS,
    /**提取失败*/
    EXTRACT_CASH_FAILURE,
    /**保证金冻结*/
    GELATION_GUARANTY,
    /**保证金解冻*/
    UNGELATION_GUARANTY,
    /**货款冻结*/
    GELATION_DEPOSIT,
    /**货款解冻*/
    UNGELATION_DEPOSIT,
    /**违约扣除*/
    VIOLATION_DEDUCTION,
    /**人工扣除*/
    MANPOWER_DEDUCTION, // 10
    /**违约补偿*/
    VIOLATION_REPARATION,
    /**人工补偿*/
    MANPOWER_REPARATION,
    /**货款转保证金*/
    DEPOSIT_GUARANTY,
    /**支付货款*/
    PAYMENT_FOR_GOODS,
    /**服务费*/
    SERVICE_CHARGE,
    /**平台返还*/
    PLATFORM_RETURN,
    /**平台支付*/
    PLATFORM_PAY,
    /**平台补贴*/
    PLATFORM_SUBSIDY,
    /**其他*/
    OTHERS_TRANSFER,
};


#endif
