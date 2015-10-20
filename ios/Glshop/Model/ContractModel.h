//
//  ContractModel.h
//  Glshop
//
//  Created by River on 15-1-26.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "ContractEnum.h"
#import "TraderStatusModel.h"
#import "FinalEstimateListModel.h"

@interface ContractModel : WXBaseModel

#pragma mark - 合同基本信息
/**
 *@brief 合同id
 */
@property (nonatomic, copy) NSString *contractId;
/**
 *@brief 询单id
 */
@property (nonatomic, copy) NSString *fid;
/**
 *@brief 卖家id
 */
@property (nonatomic, copy) NSString *sellerid;

/**
 *@brief 买家id
 */
@property (nonatomic, copy) NSString *buyerid;

/**
 *@brief 交易截止日期
 */
@property (nonatomic, copy) NSString *limittime;

/**
 *@brief 合同更新时间
 */
@property (nonatomic, copy) NSString *updatetime;

#pragma mark - 合同商品信息
@property (nonatomic, copy) NSString *productId;
@property (nonatomic, copy) NSString *productType;
@property (nonatomic, copy) NSString *productCode;
/**
 *@brief 数量
 */
@property (nonatomic, strong) NSNumber *totalnum;

/**
 *@brief 价格
 */
@property (nonatomic, strong) NSNumber *price;

/**
 *@brief 总金额（可能与单价乘以数量的总和不一样）
 */
@property (nonatomic, strong) NSNumber *totalamount;
/**
 *@brief 数量
 */
@property (nonatomic, strong) NSNumber *payFundsAmount;

#pragma mark - 起草阶段
/**
 *@brief 起草确认期限
 */
@property (nonatomic, copy) NSString *draftLimitTime;

/**
 *@brief 功能描述:合同起草阶段买家动作类型
 */
@property (nonatomic, strong) NSDictionary *buyerDraftStatus;

/**
 *@brief 功能描述:合同起草阶段卖家动作类型
 */
@property (nonatomic, strong) NSDictionary *sellerDraftStatus;


#pragma mark - 枚举合同状态
/**
 *@brief 订单状态(订单的进行中的状态) 枚举类型：ContractStatus
 */
@property (nonatomic, strong) NSDictionary *status;

/**
 *@brief 当前生命周期的操作类型 枚举类型：ContractOperateType
 */
@property (nonatomic, strong) NSDictionary *operateType;

/**
 *@brief 订单的类型（未签订，已经签订）
 */
@property (nonatomic, strong) NSDictionary *otype;

/**
 *@brief 合同生命周期
 */
@property (nonatomic, strong) NSDictionary *lifecycle;

/**
 *@brief  1: 购买 2:出售
 */
@property (nonatomic, strong) NSDictionary *saleType;

#pragma mark - 买家信息
/**
 *@brief ContractOperateType 合同操作类型
 */
@property (nonatomic, strong) NSDictionary *buyerOperatorType;
/**
 *@brief 买家合同生命周期
 */
@property (nonatomic, strong) NSDictionary *buyerOperatorStatus;
/**
 *@brief 买家上次生命周期
 */
@property (nonatomic, strong) NSDictionary *buyerOperatorOldStatus;
/**
 *@brief 买家是否评价
 */
@property (nonatomic, strong) NSDictionary *buyerEvaluation;
/**
 *@brief 买家付款期限
 */
@property (nonatomic, copy) NSString *payGoodsLimitTime;

#pragma mark - 卖家信息

/**
 *@brief ContractOperateType 合同操作类型
 */
@property (nonatomic, strong) NSDictionary *sellerOperatorType;

/**
 *@brief 卖家合同生命周期
 */
@property (nonatomic, strong) NSDictionary *sellerOperatorStatus;
/**
 *@brief 卖家上次生命周期
 */
@property (nonatomic, strong) NSDictionary *sellerOperatorOldStatus;
/**
 *@brief 卖家是否评价
 */
@property (nonatomic, strong) NSDictionary *sellerEvaluation;
/**
 *@brief 卖家同意最终确认期限
 */
@property (nonatomic, copy) NSString *agreeFinalEstimeLimitTime;

#pragma mark - 交易双方
/**
 *@brief 我的合同状态(类型) ContractStatus
 */
@property (nonatomic, strong) NSDictionary *myContractType;

/**
 *@brief 议价数据
 */
@property (nonatomic, strong) NSDictionary *fundGoodsDisPriceList;

/**
 *@brief 仲裁数据
 */
@property (nonatomic, strong) NSArray *arbitrationDisPriceList;

@property (nonatomic, strong) NSDictionary *arbitrationProcessInfo;

@property (nonatomic, strong) NSDictionary *buyerStatusDic;
@property (nonatomic, strong) NSDictionary *sellerStatusDic;

@property (nonatomic, strong) TraderStatusModel *buyerStatuobj;
@property (nonatomic, strong) TraderStatusModel *sellerStatuobj;
@property (nonatomic, strong) NSArray *businessModels;

#pragma mark - Methods
/**
 *@brief 判断合同是否有效
 */
- (BOOL)contractValide;

/**
 *@brief 根据货款操作类型查找交易数据
 */
- (FinalEstimateListModel *)findleTraeModel:(PurseTradeType)tradeType;

/**
 *@brief 是我提出的仲裁还是对方
 *@return YES 是 NO 不是
 */
- (BOOL)isMeApplyArbitrate;

/**
 *@brief 是不是买家申请了仲裁 
 *@return YES: 买家提出 NO：卖家提出
 */
- (BOOL)isBuyerApplyArbitration;

/**
 *@brief 是我单方取消合同还是对方单方取消合同
 */
- (BOOL)isMeCancelContract;

/**
 *@brief 我是否已经评价
 */
- (BOOL)isMeEvalution;

@end
