//
//  GatherModel.h
//  Glshop
//
//  Created by River on 15-1-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface GatherModel : WXBaseModel

/**
 *@brief 认证状态
 */
@property (nonatomic, strong) NSDictionary *authstatus;
/**
 *@brief 提款人类别（默认or其它）
 */
@property (nonatomic, strong) NSDictionary *status;

@property (nonatomic, copy) NSString *gatherId;

@property (nonatomic, copy) NSString *carduserid;

/**
 *@brief 银行卡号
 */
@property (nonatomic, copy) NSString *bankcard;
/**
 *@brief 支行名称
 */
@property (nonatomic, copy) NSString *bankname;
/**
 *@brief 银行类型
 */
@property (nonatomic, copy) NSString *banktype;
/**
 *@brief 银行卡姓名
 */
@property (nonatomic, copy) NSString *carduser;


@end
