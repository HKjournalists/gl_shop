//
//  OrderModel.h
//  Glshop
//
//  Created by River on 14-11-25.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  用户查看自己发布的供应或求购信息列表

#import "WXBaseModel.h"

@interface OrderModel : WXBaseModel

@property (nonatomic, copy) NSString *area;
@property (nonatomic, copy) NSString *cid;
@property (nonatomic, copy) NSString *id;
@property (nonatomic, copy) NSString *limitime;
@property (nonatomic, copy) NSString *pname;
@property (nonatomic, strong) NSNumber *price;
@property (nonatomic, copy) NSString *ptype;
@property (nonatomic, copy) NSString *starttime;
@property (nonatomic, strong) NSDictionary *status;
@property (nonatomic, strong) NSNumber *totalnum;
@property (nonatomic, strong) NSDictionary *type;
@property (nonatomic, copy) NSString *unit;

@end
