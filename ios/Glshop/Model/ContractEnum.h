//
//  ContractEnum.h
//  Glshop
//
//  Created by River on 15-1-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#ifndef Glshop_ContractEnum_h
#define Glshop_ContractEnum_h

#import "PurseEnum.h"

typedef NS_ENUM(NSInteger, ContractSaleType) {
    
    Contract_Type_Unknow,
    /**
     *@brief 购买
     */
    ORDER_TYPE_BUY,
    /**
     * 出售
     */
    ORDER_TYPE_SELL,
};

typedef NS_ENUM(NSInteger, ContractBigStatus) {
    /**
     *@brief 待确认的合同
     */
    contract_waite_sure,
    /**
     *@brief 进行中的合同
     */
    contract_procressing,
    /**
     *@brief 已结束的合同
     */
    contract_over,
};

typedef NS_ENUM(NSInteger, ContractStatus) {
    /*"起草"*/
    DRAFT,
    /*"进行"*/
    DOING,
    /*"暂停"*/
    PAUSE,
    /*"结束"*/
    FINISHED,
    /*"删除"*/
    DELETION,
};

typedef NS_ENUM(NSInteger, ContractDraftStageBuyerSellerDoType) {
    /**
     *@brief 无操作
     */
    NOTHING,
    /**
     *@brief 取消
     */
    CANCEL,
    /**
     *@brief 确认
     */
    CONFIRM,
};

typedef NS_ENUM(NSInteger, ContractLifeCycle) {
    /*"起草中"*/
    DRAFTING,
    
    /*"起草超时结束"*/
    TIMEOUT_FINISHED,
    
    /*"起草手动作废（由客服）"*/
    MANUAL_RESTORE,
    
    /**
     *@brief 已签订
     */
    SINGED,
    
    /*"付款中"*/
    IN_THE_PAYMENT,
    
    /*"已付款"*/
    PAYED_FUNDS,
    
    /*"已发货"*/
    SENT_GOODS,
    
    /*"抽样验收中"*/
    SIMPLE_CHECKING,
    
    /*"抽样验收通过"*/
    SIMPLE_CHECKED,
    
    /*"全量验收中"*/
    FULL_TAKEOVERING,
    
    /*"全量验收通过"*/
    FULL_TAKEOVERED,
    
    /*"已经卸货"*/
    UNINSTALLED_GOODS,
    
    /*"已经收货"*/
    RECEIVED_GOODS,
    
    /*"取消中"*/
    CANCELING,
    
    /*"结算中"*/
    FINALESTIMATEING,
    
    /*"结算完成"*/
    FINALESTIMATE_FINISHED,
    
    /*"正常结束"*/
    NORMAL_FINISHED,
    
    /**
     *@brief 单方取消结束
     */
    SINGLECANCEL_FINISHED,
    
    /*"双方取消结束"*/
    DUPLEXCANCEL_FINISHED,
    
    /*"终止结束"*/
    EXPIRATION_FINISHED,
    
    /**
     *@brief 仲裁中
     */
    ARBITRATING,
    
    /*货款确认中*/
    CONFIRMING_GOODS_FUNDS,
    
    /*起草取消*/
    DRAFTING_CANCEL,
    
    /**
     *@brief 仲裁结束
     */
    ARBITRATED,
    
    /**
     *@brief 买家未付款结束
     */
    BUYER_UNPAY_FINISH,
    
    /*删除合同*/
    DELETE_CONTRAC,
};

typedef NS_ENUM(NSInteger, ContractOperateType) {
    /*"确认合同"*/
    CONFRIM_CONTRACT,
    
    /*"买家付款"*/
    PAYED_Buyer_FUNDS,
    
    /*"发货"*/
    SEND_GOODS,
    
    /*"咨询客服"*/
    CONSULTING_SERVICE,
    
    /**
     *@brief 单方取消
     */
    SINGLE_CANCEL,
    
    /*"议价"*/
    DIS_PRICE,
    
    /*"验收通过"*/
    VALIDATE_PASS,
    
    /*"同意议价"*/
    APPLY_DISPRICE,
    
    /*"确认卸货"*/
    CONFIRM_UNINSTALLGOODS,
    
    /*"确认收货"*/
    CONFIRM_RECEIVEGOODS,
    
    /*"评价合同"*/
    EVALUATION_CONTRACT,
    
    /*"取消确认"*/
    CANCEL_CONFIRM,
    
    /*"撤销取消"*/
    REPEAL_CANCEL,
    
    /*"合同仲裁处理"*/
    ARBITRATION_CONTRACT,
    
    /*"合同结算"*/
    CONTRACT_ESTIMATE,
    
    /*"合同结束"*/
    CONTRACT_FINISHED,
    
    /*撮合合同*/
    MAKE_CONTRACT,
    
    /*买家未付款合同结束*/
    BUYER_UNPAY_FINISHED,
    
    /*取消合同*/
    CANCEL_CONTRACT,
    
    /*移至已经结束合同*/
    MOVE_TO_FINISHED_CONTRACT,
    
    /*货物与货款实际确认*/
    FUNDS_GOODS_CONFIRM,
    
    /*同意货物与货款实际确认*/
    AGREE_FUND_GOODS_CONFIRM,
    
    /*申请仲裁*/
    APPLY_ARBITRATION,
    
    /*删除合同*/
    DELETE_CONTRACT,
};

typedef NS_ENUM(NSInteger, ContractEvaluationType) {
    /**
     *@brief 未评价
     */
    EVALUATION_NO,
    /**
     *@brief 已评价
     */
    EVALUATION_YES,
};

typedef NS_ENUM(NSInteger, ContractBusinessStatusType) {
    /**
     *@brief 买家还未付款
     */
    statusBuyerUnPay,
    
    
};

#endif
