//
//  FinalEstimateListModel.h
//  Glshop
//
//  Created by River on 15-2-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface FinalEstimateListModel : WXBaseModel

/**
 *@brief 交易类型 PurseTradeType
 */
@property (nonatomic, strong) NSDictionary *otype;
/**
 *@brief 收入还是支出 PursePayDirection
 */
@property (nonatomic, strong) NSDictionary *direction;
/**
 *@brief 支付方式 PayWayType
 */
@property (nonatomic, strong) NSDictionary *paytype;
/**
 *@brief 支付、收入金额
 */
@property (nonatomic, strong) NSNumber *amount;
/**
 *@brief 余额
 */
@property (nonatomic, strong) NSNumber *balance;
/**
 *@brief 结算时间
 */
@property (nonatomic, strong) NSString *paytime;

@end
