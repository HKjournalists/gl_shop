//
//  ProductWeekModel.h
//  Glshop
//
//  Created by River on 14-11-24.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  黄砂或石子未来一到二周的价格

#import "WXBaseModel.h"

@interface ProductWeekModel : WXBaseModel

@property (nonatomic, copy) NSString *pname;
@property (nonatomic, copy) NSString *ptype;
@property (nonatomic, strong) NSNumber *todayPrice;
@property (nonatomic, strong) NSNumber *basePrice1;
@property (nonatomic, strong) NSNumber *basePrice2;

@end
