//
//  PayListModel.m
//  Glshop
//
//  Created by River on 15-1-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PayListModel.h"

@implementation PayListModel

- (NSDictionary *)attributeMapDictionary {
    return @{@"amount":@"amount",
             @"balance":@"balance",
             @"creator":@"creator",
             @"devices":@"devices",
             @"direction":@"direction",
             @"oid":@"oid",
             @"name":@"name",
             @"needamount":@"needamount",
             @"payId":@"id",
             @"otype":@"otype",
             @"passid":@"passid",
             @"payno":@"payno",
             @"paytype":@"paytype",
             @"status":@"status",
             @"createdate":@"createdate",
             @"paytime":@"paytime",
             };
}

@end
