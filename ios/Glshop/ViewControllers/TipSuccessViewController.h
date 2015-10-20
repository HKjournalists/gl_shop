//
//  TipSuccessViewController.h
//  Glshop
//
//  Created by River on 15-1-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, TipOperationType) {
    tip_unknow,
    /**
     *@brief 添加收款人成功
     */
    tip_add_payee_success,
    /**
     *@brief 提现成功
     */
    tip_rollout_success,
    /**
     *@brief 货款转保证金成功
     */
    tip_to_margin_success,
    /**
     *@brief 交易询盘成功
     */
    tip_inquiry_success,
    /**
     *@brief 发布（出售、求购）信息成功
     */
    tip_public_success,
    /**
     *@brief 取消发布（出售、求购）信息成功
     */
    tip_cancel_public_success,
    /**
     *@brief 删除发布（出售、求购）信息成功
     */
    tip_delete_public,
    /**
     *@brief 取消起草合同成功
     */
    tip_cancel_contract_success,
    /**
     *@brief 取消进行中的合同成功
     */
    tip_cancel_contracting_success,
    /**
     *@brief 双方确认合同成功
     */
    tip_both_sure_contract_success,
    /**
     *@brief 只有自己确认了合同，对方未确认
     */
    tip_my_sure_contract_success,
    /**
     *@brief 合同买方支付货款成功
     */
    tip_contract_pay_success,
    /**
     *@brief 货物与货款实际确认成功
     */
    tip_buyer_sure_success,
    /**
     *@brief 卖家同意买家的货物与货款数
     */
    tip_seller_sure_payment,
    /**
     *@brief 申请仲裁成功
     */
    tip_arbitrate_success,
    /**
     *@brief 合同移至已结束成功
     */
    tip_moveContract,
    /**
     *@brief 删除合同成功
     */
    tip_deleteContract,
    /**
     *@brief 评价合同成功
     */
    tip_reviewContract,
    /**
     *@brief 充值交易保证金成功
     */
    tip_chargeMarginSuccess,
    /**
     *@brief 充值货款成功
     */
    tip_chargePaymentSuccess,
};

@interface TipSuccessViewController : BaseViewController

@property (nonatomic, assign) TipOperationType operationType;

@property (nonatomic, weak) NSString *contractId;

/**
 *@brief 货款转保证金金额。外部获取
 */
@property (nonatomic, assign) double margin;

/**
 *@brief 充值金额
 */
@property (nonatomic, assign) double chargeMoney;

@end
