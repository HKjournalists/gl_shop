//
//  ProModel.h
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

//@"proCode":@"code",
//@"proid":@"id",
//@"proPid":@"pid",
//@"proname":@"name",
//@"proTypes":@"types",
//@"proMaxv":@"maxv",
//@"proMinv":@"minv",
//@"proContent":@"content",
//@"orderno":@"orderno",
//@"proStatus":@"status"

@interface ProModel : WXBaseModel <NSMutableCopying>

@property (nonatomic, copy) NSString *proCode;
@property (nonatomic, copy) NSString *proid;
@property (nonatomic, copy) NSString *proPid;
/**
 *@brief e.g 堆积密度、坚固性指标、泥块含量
 */
@property (nonatomic, copy) NSString *pname;
@property (nonatomic, copy) NSString *proTypes;
@property (nonatomic, strong) NSNumber *proMaxv;
@property (nonatomic, strong) NSNumber *proMinv;
@property (nonatomic, copy) NSString *proContent;
@property (nonatomic, strong) NSNumber *orderno;
@property (nonatomic, strong) NSDictionary *proStatus;

/**
 *@brief 不同的属性连接不同的单位 e.g 泥块含量
 */
- (NSString *)combinePnameWithUnit;

@end
