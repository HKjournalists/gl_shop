//
//  ProductTodayModel.h
//  Glshop
//
//  Created by River on 14-11-21.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  3.2.1.	商品今日价格列表数据

#import "WXBaseModel.h"

@interface ProductTodayModel : WXBaseModel

@property (nonatomic, copy) NSString *pname;
@property (nonatomic, copy) NSString *ptype;
@property (nonatomic, strong) NSNumber *todayPrice;
@property (nonatomic, strong) NSNumber *yesterdayPrice;

@end
