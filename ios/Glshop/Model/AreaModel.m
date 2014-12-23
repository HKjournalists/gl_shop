//
//  AreaModel.m
//  Glshop
//
//  Created by River on 14-11-28.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "AreaModel.h"

@implementation AreaModel

- (NSDictionary *)attributeMapDictionary
{
    NSDictionary *mapAtt = @{
                             @"areaCode":@"code",
                             @"areaId":@"id",
                             @"areaName":@"name",
                             @"areaPcode":@"pcode",
                             @"areaVal":@"val",
                             };
    return mapAtt;
}

@end
