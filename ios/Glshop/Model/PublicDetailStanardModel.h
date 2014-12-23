//
//  PublicDetailStanardModel.h
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface PublicDetailStanardModel : WXBaseModel

@property (nonatomic, strong) NSNumber *sedimentPercent; // 含泥量
@property (nonatomic, strong) NSNumber *clodPercent;  // 泥块含量
@property (nonatomic, strong) NSNumber *apparentDensityPercent; // 表观密度
@property (nonatomic, strong) NSNumber *cumuloseDensityPercent; // 堆积密度
@property (nonatomic, strong) NSNumber *strongPercent;  // 坚固性指标
@property (nonatomic, strong) NSNumber *moistureContent; // 含水率

@end
