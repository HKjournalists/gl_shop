//
//  PublicInfoModel.m
//  Glshop
//
//  Created by River on 14-12-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PublicInfoModel.h"

@implementation PublicInfoModel

- (void)setAttributes:(NSDictionary *)dataDic {
    [super setAttributes:dataDic];
    
    // proList
    NSArray *proDics = [dataDic objectForKey:@"oppList"];
    NSMutableArray *proTemp = [NSMutableArray arrayWithCapacity:proDics.count];
    for (NSDictionary *dic in proDics) {
        ProModel *model = [[ProModel alloc] initWithDataDic:dic];
        [proTemp addObject:model];
    }
    self.proList = [NSArray arrayWithArray:proTemp];
    
    NSDictionary *sizeDic = [dataDic objectForKey:@"psize"];
    _sizeMode = [[PsizeModel alloc] initWithDataDic:sizeDic];
    
    NSArray *imgDics = [dataDic objectForKey:@"addressImgList"];
    NSMutableArray *imgTemp = [NSMutableArray array];
    for (NSDictionary *imgDic in imgDics) {
        AddressImgModel *model = [[AddressImgModel alloc] initWithDataDic:imgDic];
        [imgTemp addObject:model];
    }
    self.addressImgModels = [NSArray arrayWithArray:imgTemp];
    
    NSArray *proImgDics = [dataDic objectForKey:@"productImgList"];
    NSMutableArray *proImgArr = [NSMutableArray array];
    for (NSDictionary *pImgDic in proImgDics) {
        AddressImgModel *model = [[AddressImgModel alloc] initWithDataDic:pImgDic];
        [proImgArr addObject:model];
    }
    self.productImgList = [NSArray arrayWithArray:proImgArr];
}

@end
