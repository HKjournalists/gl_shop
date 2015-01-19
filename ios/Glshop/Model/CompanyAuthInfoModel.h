//
//  CompanyAuthInfoModel.h
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface CompanyAuthInfoModel : WXBaseModel

/**
 *@brief 注册地址
 */
@property (nonatomic, copy) NSString *address;
/**
 *@brief 认证记录ID
 */
@property (nonatomic, copy) NSString *authid;
/**
 *@brief 公司名称
 */
@property (nonatomic, copy) NSString *cname;
/**
 *@brief 注册号
 */
@property (nonatomic, copy) NSString *regno;
/**
 *@brief 创建时间
 */
@property (nonatomic, copy) NSString *cratedate;
/**
 *@brief 更新时间
 */
@property (nonatomic, copy) NSString *updatedate;
/**
 *@brief 企业类型
 */
@property (nonatomic, copy) NSString *ctype;
/**
 *@brief 法定代表人
 */
@property (nonatomic, copy) NSString *lperson;
/**
 *@brief 登记机构
 */
@property (nonatomic, copy) NSString *orgid;
/**
 *@brief 成立日期
 */
@property (nonatomic, copy) NSString *rdate;

@end
