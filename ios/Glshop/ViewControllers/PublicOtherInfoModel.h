//
//  PublicOtherInfoModel.h
//  Glshop
//
//  Created by River on 14-12-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface PublicOtherInfoModel : WXBaseModel

@property (nonatomic, copy) NSString *bussinessStartTime; // 交易开始时间
@property (nonatomic, copy) NSString *bussinessEndTime; // 交易结束时间
@property (nonatomic, copy) NSString *bussinessPlace; // 交易地域
@property (nonatomic, assign) float unitPrice;      // 到港单价
@property (nonatomic, copy) NSString *pubWay;       // 指定方式
@property (nonatomic, copy) NSString *addressInfo; // 地址信息
@property (nonatomic, strong) NSNumber *sellAmount; // 销售量
@property (nonatomic, copy) NSString *sellRemark; // 备注

@end
