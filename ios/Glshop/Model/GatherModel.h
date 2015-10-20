//
//  GatherModel.h
//  Glshop
//
//  Created by River on 15-1-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "AddressImgModel.h"

@interface GatherModel : WXBaseModel

/**
 *@brief 认证状态 0代表审核不通过 1代表审核通过 2代表审核中
 */
@property (nonatomic, strong) NSDictionary *authstatus;
/**
 *@brief 提款人类别（默认or其它） 0：其它收款人 1：默认收款人
 */
@property (nonatomic, strong) NSDictionary *status;

@property (nonatomic, copy) NSString *gatherId;

@property (nonatomic, copy) NSString *carduserid;

/**
 *@brief 银行卡号
 */
@property (nonatomic, copy) NSString *bankcard;
/**
 *@brief 支行名称
 */
@property (nonatomic, copy) NSString *bankname;
/**
 *@brief 银行类型
 */
@property (nonatomic, copy) NSString *banktype;
/**
 *@brief 银行卡姓名
 */
@property (nonatomic, copy) NSString *carduser;

@property (nonatomic, strong) AddressImgModel *imgModel;

- (BOOL)isAutush;


@end
