//
//  AddressPublicModel.m
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "AddressPublicModel.h"

@implementation AddressPublicModel

- (void)setAttributes:(NSDictionary *)dataDic
{
    [super setAttributes:dataDic];
    
    NSArray *userDics = [dataDic objectForKey:@"vImgList"];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in userDics) {
        AddressImgModel *model = [[AddressImgModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    self.addressImgModels = [NSArray arrayWithArray:temp];
    
    
}

@end
