//
//  GoodsModel.m
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "GoodsModel.h"

@implementation GoodsModel

- (NSDictionary *)attributeMapDictionary
{
    NSDictionary *mapAtt = @{
                             @"goodsCode":@"code",
                             @"goodsId":@"id",
                             @"goodsName":@"name",
                             @"goodsPcode":@"pcode",
                             @"goodsVal":@"val",
                             @"orderNumber":@"orderno",
                             };
    return mapAtt;
}

@end
