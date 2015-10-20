//
//  CopyRightModel.m
//  Glshop
//
//  Created by River on 15-1-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "CopyRightModel.h"
#import "AddressImgModel.h"

@implementation CopyRightModel

- (NSDictionary *)attributeMapDictionary {
    NSDictionary *diconary = @{@"authstatus":@"authstatus",
                               @"bailstatus":@"bailstatus",
                               @"cname":@"cname",
                               @"cphone":@"cphone",
                               @"rdate":@"rdate",
                               @"evaluationInfo":@"evaluationInfo",
                               @"companyId":@"id",
                               @"ctype":@"ctype",
                               @"mark":@"mark",
                               @"address":@"address",
                               @"realdeep":@"realdeep",
                               @"deep":@"deep",
                               @"tel":@"tel",
                               @"contact":@"contact",
                               @"companyImgList":@"companyImgList",
                               @"authImgList":@"authImgList",
                               @"addressImgList":@"addressImgList",
                               @"addrAreaFullName":@"addrAreaFullName",
                               @"shippington":@"shippington",
                                                              
    };
    
    return diconary;
}

- (void)setAttributes:(NSDictionary *)dataDic
{
    [super setAttributes:dataDic];
    
    NSDictionary *comDic = dataDic[@"companyAuth"];
    if (comDic != nil) {
        self.authCompanyModel = [[CompanyAuthInfoModel alloc] initWithDataDic:comDic];
    }
    
    NSDictionary *shipDic = dataDic[@"shippingAuth"];
    if (shipDic != nil) {
        self.authShipModel = [[ShipAuthInfoModel alloc] initWithDataDic:shipDic];
    }
    
    NSDictionary *psDic = dataDic[@"personalAuth"];
    if (psDic != nil) {
        self.authPsModel = [[PersonalAuthInfoModel alloc] initWithDataDic:psDic];
    }
    
    // 企业照片
    NSArray *cImgArr = [dataDic objectForKey:@"companyImgList"];
    NSMutableArray *cImgTemp = [NSMutableArray arrayWithCapacity:cImgArr.count];
    for (NSDictionary *dic in cImgArr) {
        AddressImgModel *model = [[AddressImgModel alloc] initWithDataDic:dic];
        [cImgTemp addObject:model];
    }
    self.companyImgList = [NSArray arrayWithArray:cImgTemp];
    
    // 卸货地址照片
    NSArray *aImgArr = [dataDic objectForKey:@"addressImgList"];
    NSMutableArray *aImgTemp = [NSMutableArray arrayWithCapacity:cImgArr.count];
    for (NSDictionary *dic in aImgArr) {
        AddressImgModel *model = [[AddressImgModel alloc] initWithDataDic:dic];
        [aImgTemp addObject:model];
    }
    self.addressImgList = [NSArray arrayWithArray:aImgTemp];
    
    NSDictionary *evaDic = [dataDic objectForKey:@"evaluationInfo"];
    if (evaDic) {
        _evaluatModel = [[EvaluationModel alloc] initWithDataDic:evaDic];
    }
}

- (BOOL)isUserAuthed {
    return [self.authstatus[DataValueKey] integerValue] == 1;
}

- (BOOL)isPaymentMargin {
    if ([self.bailstatus[DataValueKey] integerValue] == 1) {
        return YES;
    }
    return NO;
}

@end
