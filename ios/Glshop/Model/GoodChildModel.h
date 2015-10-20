//
//  GoodChildModel.h
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "PsizeModel.h"
#import "ProModel.h"

@interface GoodChildModel : WXBaseModel

@property (nonatomic, copy) NSString *goodChildId;
@property (nonatomic, copy) NSString *goodChildPname;
@property (nonatomic, copy) NSString *goodChildPcode;
@property (nonatomic, copy) NSString *goodChildPtype; // 商品沙子有此字段，石子没有
@property (nonatomic, copy) NSString *goodChildPcolor;
@property (nonatomic, copy) NSString *goodChildPaddress;
@property (nonatomic, copy) NSString *goodChildUnit;
@property (nonatomic, copy) NSString *goodChildRemark;
@property (nonatomic, strong) PsizeModel *sizeModel;
@property (nonatomic, strong) NSArray *propretyArray;
@property (nonatomic, strong) NSArray *propreDicArray;
@property (nonatomic, strong) NSNumber *goodOrderno;

@property (nonatomic, assign, getter=isSelected) BOOL selected; // 自加字段，用于筛选

/**
 *@brief 拼接名字和单位
 */
- (NSString *)combineNameWithUnit;

/**
 *@brief e.g 
 */
- (NSString *)nameWithUnit;

/**
 *@brief 返回产品的最小最大值，并拼接
 */
- (NSString *)productUnit;

@end
