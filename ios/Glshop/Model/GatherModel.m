//
//  GatherModel.m
//  Glshop
//
//  Created by River on 15-1-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "GatherModel.h"

@implementation GatherModel

- (NSDictionary *)attributeMapDictionary {
    return @{@"authstatus":@"authstatus",
             @"status":@"status",
             @"gatherId":@"id",
             @"bankcard":@"bankcard",
             @"bankname":@"bankname",
             @"banktype":@"banktype",
             @"carduser":@"carduser",
             @"carduserid":@"carduserid",
             };
}

- (void)setAttributes:(NSDictionary *)dataDic {
    [super setAttributes:dataDic];
    
    NSArray *imgDics = [dataDic objectForKey:@"vImgList"];
    if (imgDics.count) {
        _imgModel = [[AddressImgModel alloc] initWithDataDic:imgDics.firstObject];
    }
}

- (BOOL)isAutush {
    return [self.authstatus[DataValueKey] integerValue] == 1;
}

@end
