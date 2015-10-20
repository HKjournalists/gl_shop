//
//  PushInstance.h
//  Glshop
//
//  Created by River on 15-3-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  消息推送

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, PushMessageType) {
    /**默认消息**/
    TYPE_DEFAULT,
    /**用户注册**/
    TYPE_REGISTER,
    /**业务类型：用户下线**/
    TYPE_USER_LOGIN_OTHER_DEVICE = 101,
    /**企业资料认证**/
    TYPE_COMPANY_AUTH = 200,
    /**企业收款人认证**/
    TYPE_COMPANY_PAYEE_AUTH = 201,
    /**询单，供求信息**/
    TYPE_ORDER_FIND,
    /**合同签订**/
    TYPE_CONTRACT_SIGN = 400, // 双方确认完起草合同，合同进入进行中。跳转到我的合同详情
    /**合同进行中**/
    TYPE_CONTRACT_ING = 401,
    /**合同评价**/
    TYPE_CONTRACT_EVALUATION = 402,
    /**合同取消**/
    TYPE_CONTRACT_CANCEL = 403, // 跳转到我的合同详情. 未取消方收到推送
    /**撮合成功**/
    TYPE_CONTRACT_MAKE_MATCH = 404, // 跳转到起草合同详情
    /**合同单方起草确认**/
    TYPE_CONTRACT_SINGLE_DAF_CONFIRM = 405, // 跳转到起草合同详情
    /**合同起草取消**/
    TYPE_CONTRACT_DAF_CANCEL = 406, // 跳转到起草合同详情
    /**合同起草超时**/
    TYPE_CONTRACT_DAF_TIMEOUT= 407, // 跳转到起草合同详情
    /**买方付款**/
    TYPE_CONTRACT_BUYER_PAYFUNDS = 408,  // 跳转到我的合同详情
    /**买家申请合同货物和货款结算**/
    TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE = 409, // 跳转到我的合同详情
    /**卖家同意合同货物和货款结算确认**/
    TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE = 410, // 跳转到我的合同详情
    /**申请仲裁**/
    TYPE_CONTRACT_APPLY_ARBITRATION, // 跳转到我的合同详情
    /**仲裁结算**/
    TYPE_CONTRACT_ARBITRATED_FINALESTIMATE, // 跳转到我的合同详情
    /**买家预期未付款**/
    TYPE_CONTRACT_PAYFUNDS_TIMEOUT, // 跳转到我的合同详情
    /**合同其他情况**/
    TYPE_CONTRACT_OTHERS,
    /**保证金金额变动**/
    TYPE_MONEY_CHANG_GUARANTY = 500,
    /**货款金额变动**/
    TYPE_MONEY_CHANG_DEPOSIT = 501,
    /**钱包金额变动**/
    TYPE_MONEY_CHANG = 502,
    /**业务类型：APP下载URL**/
    TYPE_APP_DOWNLOAD = 900,
};

@interface PushInstance : NSObject <UIAlertViewDelegate>

+ (instancetype)sharedInstance;

/**
 *@brief 消息推送处理
 */
- (void)handlePushMessage;

- (PushMessageType)pushType;

- (void)handleUserLoginPush;

@end
