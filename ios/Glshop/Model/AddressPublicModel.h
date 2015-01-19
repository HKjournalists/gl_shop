//
//  AddressPublicModel.h
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "AddressImgModel.h"

@interface AddressPublicModel : WXBaseModel

/**
 *@brief 区域编码
 */
@property (nonatomic, copy) NSString *areacode;
/**
 *@brief 详细地址
 */
@property (nonatomic, copy) NSString *address;
/**
 *@brief 区域全称（中文）
 */
@property (nonatomic, copy) NSString *areaFullName;
@property (nonatomic, copy) NSString *cid;
@property (nonatomic, copy) NSString *id;
@property (nonatomic, strong) NSNumber *realdeep;
/**
 *@brief 水深度
 */
@property (nonatomic, strong) NSNumber *deep;
/**
 *@brief 可停泊船吨位
 */
@property (nonatomic, strong) NSNumber *shippington;
/**
 *@brief 1为默认卸货地址 0为其他卸货地址
 */
@property (nonatomic, strong) NSDictionary *status;
@property (nonatomic, strong) NSArray *addressImgModels;
@property (nonatomic, strong) NSMutableArray *imageArray;

@end
