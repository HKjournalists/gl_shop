//
//  OrderModel.h
//  Glshop
//
//  Created by River on 14-11-25.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  用户查看自己发布的供应或求购信息列表

#import "WXBaseModel.h"
#import "EvaluationModel.h"

@interface OrderModel : WXBaseModel

@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *area;
@property (nonatomic, copy) NSString *cid;
@property (nonatomic, copy) NSString *id;
@property (nonatomic, copy) NSString *endtime;
@property (nonatomic, copy) NSString *pname;
@property (nonatomic, strong) NSNumber *price;
@property (nonatomic, copy) NSString *ptype;
@property (nonatomic, copy) NSString *pid;
@property (nonatomic, copy) NSString *starttime;
@property (nonatomic, strong) NSDictionary *status;
@property (nonatomic, strong) NSNumber *totalnum;
@property (nonatomic, strong) NSDictionary *type;
@property (nonatomic, copy) NSString *unit;
@property (nonatomic, copy) NSString *areaFullName;

@property (nonatomic, strong) NSNumber *isApply; // 我是否已交易询盘，1代表是 0代表不是

@property (nonatomic, strong) NSDictionary *authstatus; // 认证信息
@property (nonatomic, strong) NSDictionary *bailstatus; // 保证金缴纳信息

/**
 *@brief 信用等级，最高5星
 */
@property (nonatomic, strong) NSNumber *credit;

@end
