//
//  GoodsModel.h
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface GoodsModel : WXBaseModel

@property (nonatomic, copy) NSString *goodsCode;
@property (nonatomic, copy) NSString *goodsId;
@property (nonatomic, copy) NSString *goodsName;
@property (nonatomic, copy) NSString *goodsPcode;
@property (nonatomic, copy) NSString *goodsVal;
@property (nonatomic, strong) NSNumber *orderNumber;

@end
