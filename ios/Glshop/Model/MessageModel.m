//
//  MessageModel.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MessageModel.h"

@implementation MessageModel

- (NSDictionary *)attributeMapDictionary {
    NSDictionary *mapAtt = @{
                             @"messageId":@"id",
                             @"qyid":@"qyid",
                             @"content":@"content",
                             @"type":@"type",
                             @"businessid":@"businessid",
                             @"businesstype":@"businesstype",
                             @"status":@"status",
                             @"createtime":@"createtime",
                             };
    return mapAtt;
}

@end
