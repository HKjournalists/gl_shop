//
//  ContractModel.m
//  Glshop
//
//  Created by River on 15-1-26.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ContractModel.h"

@implementation ContractModel

- (BOOL)contractValide {
    
    ContractStatus status = [self.status[DataValueKey] integerValue];
    if (status == DRAFT || status == DOING) {
        return YES;
    }else {
        return NO;
    }
}

- (FinalEstimateListModel *)findleTraeModel:(PurseTradeType)tradeType {
    for (FinalEstimateListModel *model in _businessModels) {
        if ([model.otype[DataValueKey] integerValue] == tradeType) {
            return model;
        }
    }
    return nil;
}

- (BOOL)isMeApplyArbitrate {
    BOOL isBuyer = [self.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    ContractOperateType buyOperateType = [self.buyerStatuobj.type[DataValueKey] integerValue];
    ContractOperateType sellerOperateType = [self.sellerStatuobj.type[DataValueKey] integerValue];
    if (isBuyer) {
        if (buyOperateType == APPLY_ARBITRATION) {
            return YES;
        }
    }else {
        if (sellerOperateType == APPLY_ARBITRATION) {
            return YES;
        }
    }
    return NO;
}

- (BOOL)isBuyerApplyArbitration {
    ContractOperateType buyOperateType = [self.buyerStatuobj.type[DataValueKey] integerValue];
    return buyOperateType == APPLY_ARBITRATION;
}

- (BOOL)isMeCancelContract {
    BOOL isBuyer = [self.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    ContractOperateType buyOperateType = [self.buyerStatuobj.type[DataValueKey] integerValue];
    ContractOperateType sellerOperateType = [self.sellerStatuobj.type[DataValueKey] integerValue];
    if (isBuyer) {
        if (buyOperateType == SINGLE_CANCEL) {
            return YES;
        }
    }else {
        if (sellerOperateType == SINGLE_CANCEL) {
            return YES;
        }
    }
    return NO;
}

- (BOOL)isMeEvalution {
    BOOL isBuyer = [self.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    BOOL sellerEva = [self.sellerEvaluation[DataValueKey] integerValue];
    BOOL buyerEva = [self.buyerEvaluation[DataValueKey] integerValue];
    
    if (isBuyer) {
        return buyerEva ? YES : NO;
    }else {
        return sellerEva ? YES : NO;
    }
}

- (NSDictionary *)attributeMapDictionary {
    NSDictionary *dic = @{@"contractId":@"id",
                          @"fid":@"fid",
                          @"sellerid":@"sellerid",
                          @"buyerid":@"buyerid",
                          @"limittime":@"limittime",
                          @"productId":@"productId",
                          @"productType":@"productType",
                          @"productCode":@"productCode",
                          @"totalnum":@"totalnum",
                          @"price":@"price",
                          @"totalamount":@"totalamount",
                          @"draftLimitTime":@"draftLimitTime",
                          @"buyerDraftStatus":@"buyerDraftStatus",
                          @"sellerDraftStatus":@"sellerDraftStatus",
                          @"status":@"status",
                          @"operateType":@"operateType",
                          @"otype":@"otype",
                          @"lifecycle":@"lifecycle",
                          @"saleType":@"saleType",
                          @"buyerOperatorType":@"buyerOperatorType",
                          @"buyerOperatorStatus":@"buyerOperatorStatus",
                          @"buyerOperatorOldStatus":@"buyerOperatorOldStatus",
                          @"buyerEvaluation":@"buyerEvaluation",
                          @"payGoodsLimitTime":@"payGoodsLimitTime",
                          @"sellerOperatorType":@"sellerOperatorType",
                          @"sellerOperatorStatus":@"sellerOperatorStatus",
                          @"sellerOperatorOldStatus":@"sellerOperatorOldStatus",
                          @"sellerEvaluation":@"sellerEvaluation",
                          @"agreeFinalEstimeLimitTime":@"agreeFinalEstimeLimitTime",
                          @"myContractType":@"myContractType",
                          @"buyerStatusDic":@"buyerStatus",
                          @"sellerStatusDic":@"sellerStatus",
                          @"fundGoodsDisPriceList":@"fundGoodsDisPriceList",
                          @"finalEstimateList":@"finalEstimateList",
                          @"arbitrationDisPriceList":@"arbitrationDisPriceList",
                          @"arbitrationProcessInfo":@"arbitrationProcessInfo",
                          @"updatetime":@"updatetime",
                          @"payFundsAmount":@"payFundsAmount",
                          };
    return dic;
}

- (void)setAttributes:(NSDictionary *)dataDic {
    [super setAttributes:dataDic];
    
    _buyerStatuobj = [[TraderStatusModel alloc] initWithDataDic:_buyerStatusDic];
    _sellerStatuobj = [[TraderStatusModel alloc] initWithDataDic:dataDic[@"sellerStatus"]];
    
    if (dataDic[@"fundGoodsDisPriceList"]) {
        _fundGoodsDisPriceList = [dataDic[@"fundGoodsDisPriceList"] lastObject];
    }
    
    if (dataDic[@"finalEstimateList"]) {
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in dataDic[@"finalEstimateList"]) {
            FinalEstimateListModel *model = [[FinalEstimateListModel alloc] initWithDataDic:dic];
            [temp addSafeObject:model];
        }
        _businessModels = [NSArray arrayWithArray:temp];
    }
}

@end
