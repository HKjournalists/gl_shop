//
//  ContactModel.m
//  Glshop
//
//  Created by River on 15-1-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ContactModel.h"

@implementation ContactModel

- (NSDictionary *)attributeMapDictionary {
    NSDictionary *dic = @{@"compId":@"id",
                          @"cname":@"cname",
                          @"cphone":@"cphone",
                          @"tel":@"tel",
                          @"status":@"status",
                          };
    return dic;
}

@end
