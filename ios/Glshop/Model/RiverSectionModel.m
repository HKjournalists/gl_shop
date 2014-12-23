//
//  RiverSectionModel.m
//  Glshop
//
//  Created by River on 14-11-28.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RiverSectionModel.h"

@implementation RiverSectionModel

- (NSDictionary *)attributeMapDictionary {
    NSDictionary *mapAtt = @{
                             @"riverSectionCode":@"code",
                             @"riverSectionId":@"id",
                             @"riverSectionName":@"name",
                             @"riverSectionPcode":@"pcode",
                             @"riverSectionVal":@"val",
                             };
    return mapAtt;}

@end
