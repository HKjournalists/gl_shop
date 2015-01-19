//
//  MyPurseInfoModel.h
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface MyPurseInfoModel : WXBaseModel

/**
 *@brief 总金额 NSNumber
 */
@property (nonatomic, strong) NSNumber *amount;

@property (nonatomic, copy) NSString *cid;

@property (nonatomic, copy) NSString *purseId;

/**
 *@brief 保证金是否足够 0：不足 1：足够
 */
@property (nonatomic, strong) NSNumber *guarantyEnough;

/**
 *@brief 1:货款账户 2:保证金账户
 */
@property (nonatomic, strong) NSDictionary *passtype;

@end
