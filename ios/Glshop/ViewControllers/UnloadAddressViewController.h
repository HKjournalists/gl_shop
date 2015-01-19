//
//  UnloadAddressViewController.h
//  Glshop
//
//  Created by River on 14-12-15.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  卸货地址管理列表

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, BusinessAddressType) {
    /**
     *@brief 选择卸货地址来发布信息
     */
    Address_Public,
    /**
     *@brief 选择发布地址来认证
     */
    Address_Auth,
};

@interface UnloadAddressViewController : BaseViewController

/**
 *@brief 选择地址的目标（发布、认证。。。）
 */
@property (nonatomic, assign) BusinessAddressType type;

@end
