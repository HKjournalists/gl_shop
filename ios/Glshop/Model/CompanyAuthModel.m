//
//  CompanyAuthModel.m
//  Glshop
//
//  Created by River on 15-1-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "CompanyAuthModel.h"

@implementation CompanyAuthModel

- (NSDictionary *)attributeMapDictionary {
    return @{@"address":@"address",
             @"authid":@"authid",
             @"cname":@"cname",
             @"cpId":@"id",
             @"lperson":@"lperson",
             @"orgid":@"orgid",
             @"rdate":@"rdate",
             };
}

@end
